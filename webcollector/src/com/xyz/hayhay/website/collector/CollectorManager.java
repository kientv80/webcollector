package com.xyz.hayhay.website.collector;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;

import org.apache.log4j.Logger;
import org.htmlparser.Parser;
import org.htmlparser.util.NodeIterator;

import com.xyz.hayhay.db.JDBCConnection;
import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.entirty.UrlCollectionInfo;
import com.xyz.hayhay.entirty.Website;
import com.xyz.hayhay.entirty.WebsiteCollectionInfo;

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
		collector = new ArrayList<>();
	}

	public void register(ArticleCollector wcollector) {
		collector.add(wcollector);
	}

	boolean run = true;
	public static long lastTimeCollected;

	public void startCollectorManager() {
		if (!run)
			return;
		Timer wnewsTimer = new Timer();
		wnewsTimer.schedule(new TimerTask() {
			@Override
			public void run() {
				lastTimeCollected = System.currentTimeMillis();
				if (!isProcessing()) {
					start();
				}
			}
		}, 0, COLLECTING_PERIOD);// run every 5 minutes
	}

	public void start() {
		setProcessing(true);
		latestCollectTime = System.currentTimeMillis();
		Map<String, WebsiteCollectionInfo> collectorInfo = new LinkedHashMap<>();

		List<News> allNews = new ArrayList<>();
		try {
			for (ArticleCollector websiteCollector : collector) {
				if ((System.currentTimeMillis() - websiteCollector.getLastTimeProcessed()) > websiteCollector
						.getRepeatTime()) {
					allNews.clear();
					System.out.println("====start collecting " + websiteCollector.getClass().getSimpleName());
					WebsiteCollectionInfo websiteCollectionInfo = new WebsiteCollectionInfo();
					websiteCollectionInfo.setCollectorStartTime(System.currentTimeMillis());
					collectorInfo.put(websiteCollector.getClass().getSimpleName(), websiteCollectionInfo);
					String website = null;
					for (String url : websiteCollector.collectedUrls()) {
						UrlCollectionInfo urlCollectionInfo = websiteCollectionInfo.getUrlCollectorInfo().get(url);
						if (urlCollectionInfo == null) {
							urlCollectionInfo = new UrlCollectionInfo(url, 0, 0, System.currentTimeMillis(),
									"SUCCESSED");
							websiteCollectionInfo.getUrlCollectorInfo().put(url, urlCollectionInfo);
						} else {
							urlCollectionInfo.setCollectedCount(0);
							urlCollectionInfo.setStoredCount(0);
							urlCollectionInfo.setStatus("SUCCESSED");
						}
						try {
							urlCollectionInfo.setCollectedTime(System.currentTimeMillis());
							website = getfromWeb(url);
							Source s = getSource(url);
							List<News> articles = websiteCollector.collectArticle(s, url, website);
							if (s != null) {
								s.clearCache();
								s = null;
							}
							if (articles == null || articles.isEmpty()) {
								urlCollectionInfo.setStatus("ERROR");
								urlCollectionInfo.setErrorMsg("Can NOT parse the url");
							} else {
								urlCollectionInfo.setCollectedCount(articles.size());
								articles.removeAll(allNews);
								Website w = new Website();
								w.setOverwrite(websiteCollector.overwrite());
								w.setName(website);
								w.setNews(articles);
								storeNews(w);
								urlCollectionInfo.setStoredCount(w.getNews().size());
								allNews.addAll(articles);
								w.getNews().clear();
								w = null;
							}

						} catch (Exception e) {
							log.error("URL=" + url, e);
							System.out.println(
									e.getMessage() + websiteCollector.getClass().getSimpleName() + "  url = " + url);
							// e.printStackTrace();
							urlCollectionInfo.setStatus("FAILED");
							urlCollectionInfo.setErrorMsg(e.getMessage());
						}
					}
					allNews.clear();
					websiteCollector.setLastTimeProcessed(System.currentTimeMillis());
					System.out.println("====end collecting " + websiteCollector.getClass().getSimpleName());

				}
			}
			// Remove duplicated and old news
			try (Connection conn = JDBCConnection.getInstance().getConnection()) {
				try (Statement stm = conn.createStatement()) {
					stm.execute("DELETE n1 FROM news n1, news n2 WHERE n1.id > n2.id AND n1.title = n2.title");
					stm.execute("delete from news where collectedtime < "
							+ (System.currentTimeMillis() - 60 * 24 * 60 * 60 * 1000));// 60days
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} finally {
			setProcessing(false);
		}

		latestFinishedCollectTime = System.currentTimeMillis();
		printoutcollectionInfo(collectorInfo);
	}

	SimpleDateFormat s = new SimpleDateFormat("dd/MM/YYYY hh:mm:ss");

	private void printoutcollectionInfo(Map<String, WebsiteCollectionInfo> collectorInfo) {
		if (collectorInfo.isEmpty())
			return;
		System.out.println("Collector Manager start time: " + s.format(new Date(latestCollectTime)));
		for (WebsiteCollectionInfo wc : collectorInfo.values()) {
			for (UrlCollectionInfo ci : wc.getUrlCollectorInfo().values())
				if (ci.getCollectedCount() == 0) {
					System.out.println("[" + s.format(new Date(ci.getCollectedTime())) + "]WARNING " + ci.getUrl()
							+ " collected " + ci.getCollectedCount() + " store " + ci.getStoredCount() + " status "
							+ ci.getStatus() + " errorMsg " + ci.getErrorMsg());
				}
		}
		System.out.println("Collector Manager end time: " + s.format(new Date(latestFinishedCollectTime)));
	}

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
		Parser pa = new Parser(url);
		NodeIterator no = pa.elements();
		StringBuilder content = new StringBuilder();
		while (no.hasMoreNodes()) {
			content.append(no.nextNode().toHtml());
		}
		Source s = new Source(content);
		return s;
	}

	protected void storeNews(Website website) {

		if (website.getNews().size() > 0) {
			removeDuplicatedNews(website);
			Connection con = JDBCConnection.getInstance().getConnection();
			if (website.getNews().size() > 30) {
				website.setNews(website.getNews().subList(0, 30));
			}
			if (website.getNews().size() == 0)
				return;
			try {
				List<News> newNews = new ArrayList<News>();
				try (PreparedStatement stm2 = con
						.prepareStatement("select id,url,title,fromwebsite from news where type=?")) {
					if (!website.isOverwrite()) {
						stm2.setString(1, website.getNews().get(0).getType());
						Map<News, News> oldNews = new HashMap<>();
						try (ResultSet rs = stm2.executeQuery()) {
							while (rs.next()) {
								News on = new News();
								on.setId(rs.getInt("id"));
								on.setUrl(rs.getString("url"));
								on.setTitle(rs.getString("title"));
								on.setFromWebSite(rs.getString("fromwebsite"));
								oldNews.put(on, on);
							}
						}

						if (oldNews.size() > 0) {
							List<News> updateList = new ArrayList<News>();
							StringBuilder dupNews = new StringBuilder();
							for (News n : website.getNews()) {
								News ons = oldNews.get(n);
								if (ons != null) {
									if (!ons.getTitle().equals(n.getTitle())) {
										dupNews.append("old=").append(ons.getTitle()).append(" News=" + n.getTitle())
												.append("\n");
										ons.setUrl(n.getUrl());
										ons.setImageUrl(n.getImageUrl());
										ons.setTitle(n.getTitle());
										ons.setShotDesc(n.getShotDesc());
										ons.setDate(new Timestamp(System.currentTimeMillis()));
										updateList.add(ons);
									}
								} else {
									newNews.add(n);
								}
							}

							if (updateList.size() > 0) {
								try (PreparedStatement st = con.prepareStatement(
										"update news set title=?,shotdesc=?,url=?, imageurl=?,collectedtime=? where id=?")) {
									for (News on : updateList) {
										st.setString(1, on.getTitle());
										st.setString(2, on.getShotDesc());
										st.setString(3, on.getUrl());
										st.setString(4, on.getImageUrl());
										st.setTimestamp(5, on.getDate());
										st.setInt(6, on.getId());
										st.addBatch();
									}
									st.executeBatch();
								}
							}
						} else {
							newNews = website.getNews();
						}
					}

					Collections.reverse(newNews);

					if (newNews.size() > 0) {
						try (PreparedStatement stm = con.prepareStatement(
								"insert into news(title,shotdesc,url,fromwebsite,imageurl,type,ishotnews,newsorder,collectedtime,title_id,parent_catename,country)values(?,?,?,?,?,?,?,?,?,?,?,?)")) {
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
								stm.addBatch();
							}
							stm.executeBatch();
						}

					}
				}
			} catch (Exception e) {
				log.error("", e);
				e.printStackTrace();
			} finally {
				try {
					con.close();
				} catch (SQLException e) {
					log.error("", e);
				}
			}

		} else {
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + website.getName() + " collect " + "0 news ");
		}
	}

	private void removeDuplicatedNews(Website website) {
		List<News> removeIds = new ArrayList<>();
		Set<String> titles = new HashSet<>();
		for (News n : website.getNews()) {
			if (titles.contains(n.getTitle())) {
				removeIds.add(n);
			} else {
				titles.add(n.getTitle());
			}
		}
		if (!removeIds.isEmpty())
			website.getNews().removeAll(removeIds);
	}

	public synchronized boolean isProcessing() {
		return processing;
	}

	public synchronized void setProcessing(boolean processing) {
		this.processing = processing;
	}

}
