package com.xyz.hayhay.website.parser;

import java.util.ArrayList;
import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.entirty.NewsTypes;
import com.xyz.hayhay.entirty.webcollection.A;
import com.xyz.hayhay.entirty.webcollection.Image;
import com.xyz.hayhay.entirty.webcollection.ShotDescription;
import com.xyz.hayhay.entirty.webcollection.Title;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class FoxNewsParser extends BaseParser {

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		List<News> news = new ArrayList<>();
		List<Element> newsFeed = source.getAllElementsByClass("news-feed");
		List<Element> articles = null;
		if ((url.endsWith("tech.html") || url.endsWith("science.html")) && source.getElementById("content") != null) {
			articles = source.getElementById("content").getAllElements("article");
		} else if (newsFeed != null && !newsFeed.isEmpty()) {
			articles = newsFeed.get(0).getAllElements("article");
		}

		if (articles != null && !articles.isEmpty()) {
			A a = new A();
			if ("foxbusiness.com".equals(fromWebsite))
				a.setDomain("http://www.foxbusiness.com");
			else
				a.setDomain("http://www.foxnews.com");
			
			Title title = new Title("img", null, null, false);
			title.setValueFromAtttributeName("alt");
			Image i = new Image();
			i.setDomain("http:");
			ShotDescription p = new ShotDescription("p", null, null, true);
			for (Element article : articles) {
				News n = new News();
				n.setFromWebSite(fromWebsite);
				if (fromWebsite.contains("foxbusiness")) {
					n.setType(NewsTypes.WN_BIZ);
					n.setParentCateName(NewsTypes.WN_BIZ);
				} else if (url.endsWith("tech.html")) {
					title = new Title("a", null, null, true);
					title.setMinLength(20);
					n.setType(NewsTypes.WN_TECH);
					n.setParentCateName(NewsTypes.WN_TECH);
				} else if (url.endsWith("science.html")) {
					title = new Title("a", null, null, true);
					title.setMinLength(50);
					n.setType(NewsTypes.WN_SIENCE);
					n.setParentCateName(NewsTypes.WN_SIENCE);
				}
				parseElementToNews(article, n, a, title, i, p);
				if (!news.contains(n) && n.getTitle() != null && !n.getTitle().isEmpty() && n.getImageUrl() != null
						&& !n.getImageUrl().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()) {
					news.add(n);
				}
			}

		}
		return news;
	}

}
