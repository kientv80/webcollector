package com.xyz.hayhay.website.collector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeIterator;

import com.xyz.hayhay.db.JDBCConnection;
import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.entirty.Website;

import net.htmlparser.jericho.Source;

public class CollectorManager {
	public static final int COLLECTING_PERIOD = 1 * 5 * 60 * 1000;
	public Logger log = Logger.getLogger(CollectorManager.class);

	private List<ArticleCollector> collector = new ArrayList<ArticleCollector>();
//	private static CollectorManager instance;
	private boolean processing;
	Set<String> badUrls = new HashSet<>();
	long latestCollectTime;
	long latestFinishedCollectTime;

//	public static CollectorManager getInstance() {
//		if (instance == null)
//			instance = new CollectorManager();
//		return instance;
//	}
//
//	private CollectorManager() {
//		setCollector(new ArrayList<ArticleCollector>());
//	}

	public void register(ArticleCollector wcollector) {
		getCollector().add(wcollector);
	}

	boolean run = true;
	public static long lastTimeCollected;
	int currentDay = 0;

	public void startCollectorManager() {
		start();
	}

	public void start() {
		if (processing) {
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>Is processing so skip");
			return;
		}
		long startTime = System.currentTimeMillis();
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		System.out.println(">>>>>>>>>>>>>>>>>>>>> start processing at " + df.format(new Date(startTime)));
		setProcessing(true);
		
		try {
			try (Connection con = JDBCConnection.getInstance().getConnection()) {
				Set<News> allNews = loadAllNews(con);
				for (ArticleCollector websiteCollector : getCollector()) {
					try {
						String website = null;
						for (String url : websiteCollector.collectedUrls()) {
							if(badUrls.contains(url))
								continue;
							try {
								website = getfromWeb(url);
								Source s = getSource(url);
								List<News> articles = websiteCollector.collectArticle(s, url, website);
								if (articles == null || articles.isEmpty())
									continue;

								Set<News> uniquenews = new HashSet<>(articles);
								Website w = new Website();
								w.setOverwrite(websiteCollector.overwrite());
								w.setName(website);
								w.setNews(new ArrayList<News>(uniquenews));
								if (w.getNews() != null) {
									System.out.println(
											"Collect " + w.getNews().size() + " news for website " + w.getName());
									List<News> removedNews = new ArrayList<>();
									for (News n : w.getNews()) {
										if (allNews.contains(n)) {// contain title
											removedNews.add(n);
										} else {
											for (News on : allNews) {
												if (on.getUrl().equals(n.getUrl())) {
													n.setId(on.getId());
													n.setNeedUpdate(true);
													break;
												}
											}
											allNews.add(n);
										}
									}
									w.getNews().removeAll(removedNews);

									storeNews(w, con);
									w.getNews().clear();
									removedNews.clear();
									articles.clear();
									w = null;
								}
								s.clearCache();
								s = null;
							} catch (Exception e) {
								badUrls.add(url);
								log.error("URL=" + url, e);
								System.out.println(e.getMessage() + websiteCollector.getClass().getSimpleName()
										+ "  url = " + url);
								e.printStackTrace();
							}finally {
								System.gc();
							}
						}
					} catch (Exception e) {
						e.printStackTrace();
					} finally {
						websiteCollector.setLastTimeProcessed(System.currentTimeMillis());
						websiteCollector.setCollecting(false);
						System.gc();
					}
				}
				allNews.clear();
				// Remove duplicated and old news
				if (currentDay != Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
					currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
					try (PreparedStatement stm = con.prepareStatement("delete from news where collectedtime < ?")) {
						// stm.execute("DELETE n1 FROM news n1, news n2 WHERE
						// n1.id > n2.id AND n1.title = n2.title");
						Calendar c = Calendar.getInstance();
						c.add(Calendar.DAY_OF_YEAR, -6*30);
						stm.setTimestamp(1, new Timestamp(c.getTimeInMillis()));
						stm.execute();// 12
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			setProcessing(false);
			System.gc();
			System.out.println(">>>>>>>>>>>>>>>>>>>>> End processing at " + df.format(new Date(startTime)));
			System.out.println("End processing. Execution time = " + ((System.currentTimeMillis() - startTime) / 1000)
					+ " seconds");
		}

	}

	SimpleDateFormat s = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");

	private String getfromWeb(String url) {
		String website;
		String http = "http://";
		if (url.startsWith("https://"))
			http = "https://";
		if (url.indexOf("/", http.length()) > 0)
			website = url.substring(http.length(), url.indexOf("/", http.length()));
		else
			website = url.substring(http.length());
		if (website.startsWith("m.")) {
			website = website.substring("m.".length());
		} else if (website.startsWith("www.")) {
			website = website.substring("www.".length());
		}
		return website;
	}

	private Source getSource(String url) throws Exception {
		StringBuilder content = new StringBuilder();
		try {
			Parser pa = new Parser(url);
			NodeIterator no = pa.elements();
			while (no.hasMoreNodes()) {
				content.append(no.nextNode().toHtml());
			}
		} catch (Exception e) {
			content = loadContentByCurl(url);
		}
		Source s = new Source(content);
		return s;
	}

	private StringBuilder loadContentByCurl(String url) throws Exception {
		Process process = Runtime.getRuntime().exec("curl " + url);
		StringBuilder content = new StringBuilder();

		try (BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()))) {
			String line = null;
			while ((line = input.readLine()) != null) {
				content.append(line).append("\n");
			}
		}
		process.waitFor();

		return content;
	}

	protected void storeNews(Website website, Connection con) throws SQLException {

		if (website.getNews() != null && website.getNews().size() > 0) {
			try {
				System.out.println("Store " + website.getNews().size() + " news for website " + website.getName());
				boolean execute = false;
				try (PreparedStatement stm = con.prepareStatement(
						"insert into news(title,shotdesc,url,fromwebsite,imageurl,type,ishotnews,newsorder,collectedtime,title_id,parent_catename,country,language)values(?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
					for (News n : website.getNews()) {
						if (!n.isNeedUpdate()) {
							execute = true;
							stm.clearParameters();
							stm.setString(1, n.getTitle());
							stm.setString(2, n.getShotDesc());
							stm.setString(3, n.getUrl());
							stm.setString(4, n.getFromWebSite());
							stm.setString(5, n.getImageUrl());
							stm.setString(6, n.getType());
							stm.setBoolean(7, n.isHotNews());
							stm.setString(8, n.getNewsOrder());
							stm.setTimestamp(9, new Timestamp(System.currentTimeMillis()));
							stm.setString(10, n.getUniqueName());
							stm.setString(11, n.getParentCateName());
							stm.setString(12, n.getCountry());
							if (n.getLang() == null || n.getLang().isEmpty())
								stm.setString(13, News.LANGUAGE.VIETNAMESE.name());
							else
								stm.setString(13, n.getLang());
							stm.addBatch();
						}
					}
					if (execute)
						stm.executeBatch();
				}
				execute = false;
				try (PreparedStatement st = con
						.prepareStatement("update news set title=?,shotdesc=?,url=?, imageurl=? where id=?")) {
					for (News on : website.getNews()) {
						if (on.isNeedUpdate()) {
							execute = true;
							st.setString(1, on.getTitle());
							st.setString(2, on.getShotDesc());
							st.setString(3, on.getUrl());
							st.setString(4, on.getImageUrl());
							st.setInt(5, on.getId());
							st.addBatch();
						}
					}
					if (execute)
						st.executeBatch();
				}
			} catch (Exception e) {
				e.printStackTrace();
			}

		} else {
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + website.getName() + " collect " + "0 news ");
		}
	}

	private Set<News> loadAllNews(Connection con) throws SQLException {
		Set<News> news = new HashSet<>();
		try {
			try (PreparedStatement stm2 = con.prepareStatement("select id,title,url,fromwebsite from news")) {
				try (ResultSet rs = stm2.executeQuery()) {
					while (rs.next()) {
						News n = new News();
						n.setId(rs.getInt("id"));
						n.setTitle(rs.getString("title"));
						n.setUrl(rs.getString("url"));
						n.setFromWebSite(rs.getString("fromwebsite"));
						news.add(n);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return news;
	}

	public synchronized boolean isProcessing() {
		return processing;
	}

	public synchronized void setProcessing(boolean processing) {
		this.processing = processing;
	}

	public List<ArticleCollector> getCollector() {
		return collector;
	}

	public void setCollector(List<ArticleCollector> collector) {
		this.collector = collector;
	}

}
