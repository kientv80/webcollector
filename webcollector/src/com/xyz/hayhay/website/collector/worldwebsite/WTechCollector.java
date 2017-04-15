package com.xyz.hayhay.website.collector.worldwebsite;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.entirty.NewsTypes;
import com.xyz.hayhay.entirty.Website;
import com.xyz.hayhay.entirty.webcollection.A;
import com.xyz.hayhay.entirty.webcollection.Image;
import com.xyz.hayhay.entirty.webcollection.ShotDescription;
import com.xyz.hayhay.entirty.webcollection.Title;
import com.xyz.hayhay.website.collector.BasedCollector;

public class WTechCollector extends BasedCollector {
	private static final String CNNNEWS = "cnn.com";

	@Override
	public void processWebsiteContent() {
		try {
			collectTechFox();
			collectTechWSJ();
			collectTechInCnn();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void collectTechInCnn() throws IOException, MalformedURLException {
		Website nytimes = new Website();
		nytimes.setName(CNNNEWS);
		List<News> news = new ArrayList<>();
		String[] urls = new String[] { "http://money.cnn.com/technology/connect/?iid=H_T_QL","http://money.cnn.com/technology/best-in-tech/?iid=H_T_QL","http://money.cnn.com/technology/innovate/?iid=H_T_QL" };
		A a = new A("a", "class", "summaryImg", false);
		a.setValueFromAtttributeName("href");
		a.setDomain("http://money.cnn.com/");
		Title t = new Title("a", null, null, true);
		Image i = new Image();
		ShotDescription d = new ShotDescription();
		for (String url : urls) {
			Source s = new Source(new URL(url));
			for (Element e : s.getElementById("mod-river").getChildElements().get(0).getChildElements()) {
				News n = new News();
				n.setFromWebSite(CNNNEWS);
				n.setHotNews(true);
				n.setType(NewsTypes.WN_TECH);
				parseElementToNews(e, n, a, t, i, d);
				if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty() && n.getImageUrl() != null && !n.getImageUrl().isEmpty()) {
					if (!news.contains(n)) {
						news.add(n);
					}
				}
			}
		}
		nytimes.setNews(news);
		storeNews(nytimes);
	}
	private void collectTechWSJ() throws IOException, MalformedURLException {
		Website wjs = new Website();
		wjs.setName("wsj.com");
		List<News> news = new ArrayList<>();
		String[] urls = new String[] { "http://www.wsj.com/news/technology"};
		A a = new A("a", null, null, false);
		a.setValueFromAtttributeName("href");
		Title t = new Title("a", null, null, true);
		Image i = new Image();
		i.setValueFromAtttributeName("data-src");
		ShotDescription d = new ShotDescription("a",null,null,true);
		for (String url : urls) {
			Source s = new Source(new URL(url));
			for (Element e : s.getAllElementsByClass("headlines hedSumm").get(0).getChildElements()) {
				News n = new News();
				n.setFromWebSite("wsj.com");
				n.setType(NewsTypes.WN_TECH);
				parseElementToNews(e, n, a, t, i, d);
				if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty() && n.getImageUrl() != null && !n.getImageUrl().isEmpty()) {
					if (!news.contains(n)) {
						news.add(n);
					}
				}
			}
		}
		wjs.setNews(news);
		storeNews(wjs);
	}
	private void collectTechFox() throws IOException, MalformedURLException {
		Website wjs = new Website();
		wjs.setName("pewinternet.org");
		List<News> news = new ArrayList<>();
		String[] urls = new String[] { "http://www.pewinternet.org/"};
		A a = new A();
		Title t = new Title("a", null, null, false);
		t.setValueFromAtttributeName("title");
		Image i = new Image();
		ShotDescription d = new ShotDescription("a",null,null,false);
		for (String url : urls) {
			Source s = new Source(new URL(url));
			for (Element e : s.getElementById("special-projects").getChildElements().get(1).getChildElements()) {
				News n = new News();
				n.setFromWebSite("pewinternet.org");
				n.setType(NewsTypes.WN_TECH);
				parseElementToNews(e, n, a, t, i, d);
				if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty() && n.getImageUrl() != null && !n.getImageUrl().isEmpty()) {
					if (!news.contains(n)) {
						news.add(n);
					}
				}
			}
		}
		wjs.setNews(news);
		storeNews(wjs);
	}
	public static void main(String[] args) {
		WTechCollector t  = new WTechCollector();
		t.run();
	}

	@Override
	public String getCollectorName() {
		return "WTechCollector";
	}
}
