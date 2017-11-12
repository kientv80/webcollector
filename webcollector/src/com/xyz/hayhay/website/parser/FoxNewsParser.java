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
		if ((url.trim().endsWith("tech.html") || url.endsWith("science.html") || url.endsWith("health.html") || url.endsWith("politics.html"))) {
			articles = source.getAllElementsByClass("content article-list").get(0).getAllElements("article");
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
				News n = new News(News.COUNTRY.US.name());
				n.setFromWebSite(fromWebsite);
				if (fromWebsite.contains("foxbusiness")) {
					n.setType(NewsTypes.TYPE.Business.name());
					n.setParentCateName(NewsTypes.CATEGORY.Business.name());
				} else if (url.endsWith("tech.html")) {
					title = new Title("a", null, null, true);
					title.setMinLength(20);
					n.setType(NewsTypes.TYPE.Tech.name());
					n.setParentCateName(NewsTypes.CATEGORY.Tech.name());
				} else if (url.endsWith("science.html")) {
					title = new Title("a", null, null, true);
					title.setMinLength(50);
					n.setType(NewsTypes.TYPE.Science.name());
					n.setParentCateName(NewsTypes.CATEGORY.Tech.name());
				} else if (url.endsWith("health.html")) {
					title = new Title("a", null, null, true);
					title.setMinLength(50);
					n.setType(NewsTypes.TYPE.Health.name());
					n.setParentCateName(NewsTypes.CATEGORY.Health.name());
				} else if (url.endsWith("politics.html")) {
					title = new Title("a", null, null, true);
					title.setMinLength(50);
					n.setType(NewsTypes.TYPE.Politics.name());
					n.setParentCateName(NewsTypes.CATEGORY.Politics.name());
				}
				parseElementToNews(article, n, a, title, i, p);
				if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getImageUrl() != null
						&& !n.getImageUrl().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty() && !news.contains(n)) {
					news.add(n);
				}
			}

		}
		return news;
	}
	public enum ServerStatus {
        New, Running,Up,Down,Overloaded
    }
	public static void main(String[] aa) {
		System.out.println(ServerStatus.Running.ordinal());

	}
}
