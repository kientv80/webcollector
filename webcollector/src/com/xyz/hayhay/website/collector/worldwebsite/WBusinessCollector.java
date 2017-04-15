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

public class WBusinessCollector extends BasedCollector {
	private static final String CNNNEWS = "cnn.com";

	@Override
	public void processWebsiteContent() {
		try {
			Website w = collectBusinessInCnn();
			storeNews(w);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private Website collectBusinessInCnn() throws IOException, MalformedURLException {
		Website nytimes = new Website();
		nytimes.setName(CNNNEWS);
		List<News> news = new ArrayList<>();
		String[] urls = new String[] { "http://money.cnn.com/data/markets/investing-guide/?iid=H_MKT_QL", "http://money.cnn.com/news/specials/fed/?iid=H_MKT_QL"};
		A a = new A("a", "class", "summaryImg", false);
		a.setValueFromAtttributeName("href");
		a.setDomain("http://money.cnn.com/");
		Title t = new Title("a", null, null, true);
		Image i = new Image();
		ShotDescription d = new ShotDescription();
		for (String url : urls) {
			Source s = new Source(new URL(url));
			for (Element e : s.getElementById("mod-river").getChildElements().get(1).getChildElements()) {
				News n = new News();
				n.setFromWebSite(CNNNEWS);
				n.setHotNews(true);
				n.setType(NewsTypes.WN_BIZ);
				parseElementToNews(e, n, a, t, i, d);
				if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty() && n.getImageUrl() != null && !n.getImageUrl().isEmpty()) {
					if (!news.contains(n)) {
						news.add(n);
					}
				}
			}
		}
		nytimes.setNews(news);
		return nytimes;
	}
	
	public static void main(String[] args) {
		WBusinessCollector t  = new WBusinessCollector();
		t.run();
	}

	@Override
	public String getCollectorName() {
		return "WBusinessCollector";
	}
}
