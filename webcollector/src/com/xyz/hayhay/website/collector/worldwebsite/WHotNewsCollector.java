package com.xyz.hayhay.website.collector.worldwebsite;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.entirty.NewsTypes;
import com.xyz.hayhay.entirty.Website;
import com.xyz.hayhay.entirty.webcollection.A;
import com.xyz.hayhay.entirty.webcollection.Image;
import com.xyz.hayhay.entirty.webcollection.ShotDescription;
import com.xyz.hayhay.entirty.webcollection.Title;
import com.xyz.hayhay.website.collector.BasedCollector;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class WHotNewsCollector extends BasedCollector {

	@Override
	public void processWebsiteContent() {
		try {
			collectFoxHotNews();
			collectNYTimesHotNews();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void collectFoxHotNews() throws IOException, MalformedURLException {
		Website nytimes = new Website();
		nytimes.setName("foxnews.com");
		List<News> news = new ArrayList<>();
		String[] urls = new String[] { "http://www.foxnews.com/"};
		A a = new A();
		Title t = new Title("h3", null, null, true);
		Image i = new Image();
		i.setValueFromAtttributeName("data-src");
		ShotDescription d = new ShotDescription();
		for (String url : urls) {
			Source s = new Source(new URL(url));
			for (Element e : s.getAllElementsByClass("bkt article-ct")) {
				News n = new News();
				n.setFromWebSite("foxnews.com");
				n.setHotNews(true);
				n.setType(NewsTypes.WN_HOME);
				parseElementToNews(e, n, a, t, i, d);
				if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty() && n.getImageUrl() != null && !n.getImageUrl().isEmpty()) {
					if (!news.contains(n)) {
						news.add(n);
					}
				}
			}
			
			for (Element e : s.getAllElementsByClass("bkt video-ct")) {
				News n = new News();
				n.setFromWebSite("foxnews.com");
				n.setHotNews(true);
				n.setType(NewsTypes.WN_HOME);
				parseElementToNews(e, n, a, t, i, d);
				if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty() && n.getImageUrl() != null && !n.getImageUrl().isEmpty()) {
					if (!news.contains(n)) {
						n.setTitle("(Video)" + n.getTitle());
						news.add(n);
					}
				}
			}
		}
		nytimes.setNews(news);
		storeNews(nytimes);
	}
	private void collectNYTimesHotNews() throws IOException, MalformedURLException {
		Website nytimes = new Website();
		nytimes.setName("nytimes.com");
		List<News> news = new ArrayList<>();
		String[] urls = new String[] { "http://www.nytimes.com/pages/world/index.html"};
		A a = new A();
		Title t = new Title("a", null, null, true);
		Image i = new Image();
		ShotDescription d = new ShotDescription("p","class","summary",true);
		for (String url : urls) {
			Source s = new Source(new URL(url));
			for (Element e : s.getAllElementsByClass("story")) {
				News n = new News();
				n.setFromWebSite("nytimes.com");
				n.setType(NewsTypes.WN_HOME);
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
	public static void main(String[] args) {
		WHotNewsCollector t  = new WHotNewsCollector();
		t.run();
	}

	@Override
	public String getCollectorName() {
		return "WHotNewsCollector";
	}
}
