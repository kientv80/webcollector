package com.xyz.hayhay.website.collector;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
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

	private List<ArticleCollector> collector = null;
	private static CollectorManager instance;
	private boolean processing;

	long latestCollectTime;
	long latestFinishedCollectTime;

	public static CollectorManager getInstance() {
		if (instance == null)
			instance = new CollectorManager();
		return instance;
	}

	private CollectorManager() {
		setCollector(new ArrayList<ArticleCollector>());
	}

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
		if (processing)
			return;
		System.out.println("start processing");
		setProcessing(true);
		try {
			try (Connection con = JDBCConnection.getInstance().getConnection()) {
				Map<Integer, Set<String>> allTiles = loadAllNewsTitles(con);
				for (ArticleCollector websiteCollector : getCollector()) {
					try {
						String website = null;
						for (String url : websiteCollector.collectedUrls()) {
							try {
								website = getfromWeb(url);
								Source s = getSource(url);
								List<News> articles = websiteCollector.collectArticle(s, url, website);
								if (s != null) {
									s.clearCache();
									s = null;
								}
								Website w = new Website();
								w.setOverwrite(websiteCollector.overwrite());
								w.setName(website);
								w.setNews(articles);

								if (w.getNews() != null) {
									System.out.println(
											"Collect " + w.getNews().size() + " news for website " + w.getName());
									
									List<News> removedNews = new ArrayList<>();
									for (News n : w.getNews()) {
										int partition = Math.abs(n.getTitle().hashCode() % 10);
										Set<String> titles = allTiles.get(partition);
										if(titles == null){
											titles = new HashSet<>();
											allTiles.put(partition, titles);
										}
										if (titles != null && titles.contains(n.getTitle())) {
											removedNews.add(n);
										}else {
											titles.add(n.getTitle());
										}
									}
									w.getNews().removeAll(removedNews);
									storeNews(w, con);
									w.getNews().clear();
									w = null;
								}

							} catch (Exception e) {
								log.error("URL=" + url, e);
								System.out.println(e.getMessage() + websiteCollector.getClass().getSimpleName()
										+ "  url = " + url);
								e.printStackTrace();
							}
						}

					} finally {
						websiteCollector.setLastTimeProcessed(System.currentTimeMillis());
						websiteCollector.setCollecting(false);
					}
				}
				allTiles.clear();
				allTiles = null;

				// Remove duplicated and old news
				if (currentDay != Calendar.getInstance().get(Calendar.DAY_OF_MONTH)) {
					currentDay = Calendar.getInstance().get(Calendar.DAY_OF_MONTH);
					try (Statement stm = con.createStatement()) {
						// stm.execute("DELETE n1 FROM news n1, news n2 WHERE
						// n1.id > n2.id AND n1.title = n2.title");
						stm.execute("delete from news where collectedtime < "
								+ (System.currentTimeMillis() - 12 * 30 * 24 * 60 * 60 * 1000));// 12
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		} finally {
			setProcessing(false);
			System.out.println("End processing");
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
		BufferedReader input = new BufferedReader(new InputStreamReader(process.getInputStream()));

		String line = null;

		while ((line = input.readLine()) != null) {
			content.append(line).append("\n");
		}
		// System.out.println(content);
		return content;
	}

	protected void storeNews(Website website, Connection con) throws SQLException {

		if (website.getNews() != null && website.getNews().size() > 0) {
			removeDuplicatedNews(website);

			if (website.getNews().size() == 0)
				return;
			try {

				List<News> newNews = new ArrayList<News>();
				try (PreparedStatement stm2 = con.prepareStatement(
						"select id,url,title,fromwebsite from news where fromwebsite=? and collectedtime > ?")) {
					if (!website.isOverwrite()) {

						stm2.setString(1, website.getNews().get(0).getFromWebSite());
						stm2.setTimestamp(2, new Timestamp(System.currentTimeMillis() - 2 * 60 * 60 * 1000));
						List<News> oldNews = new ArrayList<>();
						try (ResultSet rs = stm2.executeQuery()) {
							while (rs.next()) {
								News on = new News();
								on.setId(rs.getInt("id"));
								on.setUrl(rs.getString("url"));
								on.setTitle(rs.getString("title"));
								on.setFromWebSite(rs.getString("fromwebsite"));
								if (!oldNews.contains(on))
									oldNews.add(on);
							}
						}

						if (oldNews.size() > 0) {
							List<News> updateList = new ArrayList<News>();
							for (News n : website.getNews()) {
								if (oldNews.contains(n)) {
									News ons = oldNews.get(oldNews.indexOf(n));
									if (!ons.getTitle().equals(n.getTitle())) {// similar
										ons.setUrl(n.getUrl());
										ons.setImageUrl(n.getImageUrl());
										ons.setTitle(n.getTitle());
										ons.setShotDesc(n.getShotDesc());
										updateList.add(ons);
									}
								} else {
									newNews.add(n);
								}
							}

							if (updateList.size() > 0) {
								try (PreparedStatement st = con.prepareStatement(
										"update news set title=?,shotdesc=?,url=?, imageurl=? where id=?")) {
									for (News on : updateList) {
										st.setString(1, on.getTitle());
										st.setString(2, on.getShotDesc());
										st.setString(3, on.getUrl());
										st.setString(4, on.getImageUrl());
										st.setInt(5, on.getId());
										st.addBatch();
									}
									st.executeBatch();
								}
								updateList.clear();
								updateList = null;
							}
							oldNews.clear();
							oldNews = null;
						} else {
							newNews = website.getNews();
						}
					}

					if (newNews != null && newNews.size() > 0) {
						System.out.println("Store " + newNews.size() + " news for website " + website.getName());
						try (PreparedStatement stm = con.prepareStatement(
								"insert into news(title,shotdesc,url,fromwebsite,imageurl,type,ishotnews,newsorder,collectedtime,title_id,parent_catename,country,language)values(?,?,?,?,?,?,?,?,?,?,?,?,?)")) {
							int count = 0;
							for (News n : newNews) {
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
								count++;
								if (count >= 10) {
									stm.executeBatch();
									stm.clearBatch();
									count = 0;
								}

							}
							if (count != 0) {
								stm.executeBatch();
							}
						}
						newNews.clear();
						newNews = null;
					}
				}

			} catch (Exception e) {
				log.error("", e);
				e.printStackTrace();
			}

		} else {
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + website.getName() + " collect " + "0 news ");
		}
	}

	private Map<Integer, Set<String>> loadAllNewsTitles(Connection con) throws SQLException {
		Map<Integer, Set<String>> titles = new HashMap<>();
		try {

			try (PreparedStatement stm2 = con.prepareStatement("select distinct title  from news")) {
				try (ResultSet rs = stm2.executeQuery()) {
					while (rs.next()) {
						String title = rs.getString("title");
						Integer partition = Math.abs(title.hashCode() % 10);
						if (!titles.containsKey(partition)) {
							titles.put(partition, new HashSet<String>());
						}
						titles.get(partition).add(title);
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return titles;
	}

	private void removeDuplicatedNews(Website website) {
		Set<News> uniqueNews = new HashSet<>();
		for (News n : website.getNews()) {
			uniqueNews.add(n);
		}
		website.setNews(new ArrayList<>(uniqueNews));
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
