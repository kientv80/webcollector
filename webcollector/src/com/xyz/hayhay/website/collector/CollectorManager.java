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
import com.xyz.hayhay.entirty.CollectorLog;
import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.entirty.UrlCollectionInfo;
import com.xyz.hayhay.entirty.Website;
import com.xyz.hayhay.entirty.WebsiteCollectionInfo;

import net.htmlparser.jericho.Source;

public class CollectorManager {
	public static final int COLLECTING_PERIOD = 1 * 5 * 60 * 1000;
	public Logger log = Logger.getLogger(CollectorManager.class);
	private static final int MAX_OLD_NEWS = 100;
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
							e.printStackTrace();
							urlCollectionInfo.setStatus("FAILED");
							urlCollectionInfo.setErrorMsg(e.getMessage());
						}
					}
					allNews.clear();
					websiteCollector.setLastTimeProcessed(System.currentTimeMillis());
					System.out.println("====end collecting " + websiteCollector.getClass().getSimpleName());
				}
			}
		} finally {
		}
		setProcessing(false);
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
				System.out.println(ci.getUrl() + " collect  at " + s.format(new Date(ci.getCollectedTime())) + " found "
						+ ci.getCollectedCount() + " store " + ci.getStoredCount() + " status " + ci.getStatus()
						+ " errorMsg " + ci.getErrorMsg());
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
			PreparedStatement stm = null;

			System.out.println(">>>>>>>>>>>>>>>>>>> news website = " + website.getName() + " found count"
					+ website.getNews().size());

			if (website.getNews().size() > 30) {
				website.setNews(website.getNews().subList(0, 30));
			}
			
			try {
				List<News> newNews = new ArrayList<News>();
				PreparedStatement stm2 = con.prepareStatement("select id,url,title,fromwebsite  from news where fromwebsite=?");
				if (!website.isOverwrite()) {
					stm2.setString(1, website.getName());
					ResultSet rs = stm2.executeQuery();
					Map<News, News> oldNews = new HashMap<>();
					while (rs.next()) {
						News on = new News();
						on.setId(rs.getInt("id"));
						on.setUrl(rs.getString("url"));
						on.setTitle(rs.getString("title"));
						on.setFromWebSite(rs.getString("fromwebsite"));
						oldNews.put(on, on);
					}
					rs.close();
					if (oldNews.size() > 0) {
						List<News> updateList = new ArrayList<News>();
						
						StringBuilder dupNews = new StringBuilder();
						for (News n : website.getNews()) {
							News ons = oldNews.get(n);
							if (ons != null) {
								if (!ons.getTitle().equals(n.getTitle())) {
									dupNews.append("old=").append(ons.getTitle()).append(" News=" + n.getTitle()).append("\n");
									ons.setUrl(n.getUrl());
									ons.setImageUrl(n.getImageUrl());
									ons.setTitle(n.getTitle());
									ons.setShotDesc(n.getShotDesc());
									ons.setDate(new Timestamp(System.currentTimeMillis()));
									updateList.add(ons);
								}
							}else{
								newNews.add(n);
							}
						}

						if (updateList.size() > 0) {
							System.out.println(">>>>>>>>>>>>>>>>> duplicate and update :" + dupNews.toString());
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
						stm2.execute("delete from news where fromwebsite='" + website.getName() + "' and type='"
								+ website.getNews().get(0).getType() + "'");
						newNews=website.getNews();
					}
					stm2.close();
					Collections.reverse(newNews);
					log.info(">>>>>>>>>>>>>>>>>>> news website = " + website.getName() + " count" + newNews.size());
					if (newNews.size() > 0) {
						stm = con.prepareStatement(
								"insert into news(title,shotdesc,url,fromwebsite,imageurl,type,ishotnews,newsorder,collectedtime,title_id,parent_catename)values(?,?,?,?,?,?,?,?,?,?,?)");
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
							stm.addBatch();
						}
						stm.executeBatch();
						removeOldNewsAndKeep30DaysLatestNews(newNews.get(0).getType(), website.getName(),con);
					}
				}
			} catch (Exception e) {
				log.error("", e);
				e.printStackTrace();
			} finally {
				try {
					if (stm != null)
						stm.close();

					con.close();
				} catch (SQLException e) {
					log.error("", e);
				}
			}

		} else {
			log.info(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>" + website.getName() + " collect " + "0 news ");
		}
	}

	protected void storeNewsBK(Website website, Connection con) {
		if (website.getNews().size() > 0) {
			removeDuplicatedNews(website);
			PreparedStatement stm = null;
			if (website.getNews().size() > 20) {
				website.setNews(website.getNews().subList(0, 20));
			}
			try {
				Statement stm2 = con.createStatement();
				if (!website.isOverwrite()) {
					ResultSet rs = stm2
							.executeQuery("select title_id  from news where fromwebsite='" + website.getName()
									+ "' and parent_catename='" + website.getNews().get(0).getParentCateName() + "'");
					Set<String> keepIds = new HashSet<>();
					while (rs.next()) {
						if (rs.getString("title_id") != null && !rs.getString("title_id").isEmpty()) {
							keepIds.add(rs.getString("title_id"));
						}
					}
					rs.close();
					if (!keepIds.isEmpty()) {
						List<News> removeList = new ArrayList<News>();
						for (News n : website.getNews()) {
							if (keepIds.contains(n.getUniqueName())) {
								removeList.add(n);
							}
						}
						website.getNews().removeAll(removeList);
						keepIds.clear();
						keepIds = null;
					} else {// only happen when dev change the type
						stm2.execute("delete from news where fromwebsite='" + website.getName() + "' and type='"
								+ website.getNews().get(0).getType() + "'");
					}
				} else {
					stm2.execute("delete from news where fromwebsite='" + website.getName() + "' and type='"
							+ website.getNews().get(0).getType() + "'");
				}
				stm2.close();
				Collections.reverse(website.getNews());
				if (website.getNews().size() > 0) {
					stm = con.prepareStatement(
							"insert into news(title,shotdesc,url,fromwebsite,imageurl,type,ishotnews,newsorder,collectedtime,title_id,parent_catename)values(?,?,?,?,?,?,?,?,?,?,?)");
					for (News n : website.getNews()) {
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
						stm.addBatch();
					}
					stm.executeBatch();
					removeOldNewsAndKeep30DaysLatestNews(website.getNews().get(0).getType(), website.getName(), con);
				}
			} catch (Exception e) {
				log.error("", e);
				e.printStackTrace();
			} finally {
				try {
					if (stm != null)
						stm.close();
				} catch (SQLException e) {
					log.error("", e);
				}
			}
		}
	}

	private void removeDuplicatedNews(Website website) {
		List<News> removeIds = new ArrayList<>();
		for (News n : website.getNews()) {
			if (website.getNews().contains(n)) {
				removeIds.add(n);
			}
		}
		website.getNews().removeAll(removeIds);
	}

	private void removeOldNewsAndKeep30DaysLatestNews(String type, String website, Connection con) throws SQLException {
		Statement stm = con.createStatement();
		stm.execute("delete from news where type='" + type + "' and fromwebsite='" + website + "' AND collectedtime > " + (System.currentTimeMillis() - 30*24*60*60*1000));//30days
		stm.close();
	}

	public synchronized boolean isProcessing() {
		return processing;
	}

	public synchronized void setProcessing(boolean processing) {
		this.processing = processing;
	}

}
