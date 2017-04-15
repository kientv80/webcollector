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

public class WScienceCollector extends BasedCollector {

	@Override
	public void processWebsiteContent() {
		try {
			collectTechInScienceOrg();
			collectTechInFox();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void collectTechInScienceOrg() throws IOException, MalformedURLException {
		Website nytimes = new Website();
		nytimes.setName("sciencenews.org");
		List<News> news = new ArrayList<>();
		String[] urls = new String[] { "https://www.sciencenews.org/"};
		A a = new A("a", null, null, false);
		a.setValueFromAtttributeName("href");
		a.setDomain("https://www.sciencenews.org/");
		Title t = new Title("a", null, null, true);
		Image i = new Image();
		ShotDescription d = new ShotDescription("div","property","rnews:articlebody schema:articleBody",true);
		for (String url : urls) {
			Source s = new Source(new URL(url));
			for (Element e : s.getAllElements("article")) {
				News n = new News();
				n.setFromWebSite("sciencenews.org");
				n.setHotNews(true);
				n.setType(NewsTypes.WN_SIENCE);
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
	private void collectTechInFox() throws IOException, MalformedURLException {
		Website nytimes = new Website();
		nytimes.setName("foxnews.com");
		List<News> news = new ArrayList<>();
		String[] urls = new String[] { "http://www.foxnews.com/category/science/air-and-space.html?intcmp=subnav","http://www.foxnews.com/category/science/natural-science.html?intcmp=subnav","http://www.foxnews.com/category/science/planet-earth.html?intcmp=subnav"};
		A a = new A("a", null, null, false);
		a.setValueFromAtttributeName("href");
		a.setDomain("http://www.foxnews.com/");
		Title t = new Title("a", null, null, true);
		Image i = new Image();
		ShotDescription d = new ShotDescription("p",null,null,true);
		for (String url : urls) {
			Source s = new Source(new URL(url));
			for (Element e : s.getAllElements("article")) {
				News n = new News();
				n.setFromWebSite("foxnews.com");
				n.setHotNews(true);
				n.setType(NewsTypes.WN_SIENCE);
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
		WScienceCollector t  = new WScienceCollector();
		t.run();
	}

	@Override
	public String getCollectorName() {
		return "WScienceCollector";
	}
}
