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

public class NYTimesParser extends BaseParser {

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		List<News> news = new ArrayList<>();
		List<Element> articles = source.getElementById("main").getAllElementsByClass("story");
		A a = new A();
		Title title = new Title("a", null, null, true);
		Image i = new Image();
		ShotDescription p = new ShotDescription("p", "class", "summary", true);
		if(url.endsWith("?src=busfn")){
			articles = source.getElementById("main").getAllElements("article");
			title = new Title("h2", "class", "headline", true);
		}
		if (articles != null && !articles.isEmpty()) {

			for (Element article : articles) {
				News n = new News(News.COUNTRY.US.name());
				n.setFromWebSite(fromWebsite);
				n.setLang(News.LANGUAGE.ENGLISH.name());
				if (url.contains("business")) {
					n.setType(NewsTypes.TYPE.Business.name());
					n.setParentCateName(NewsTypes.CATEGORY.Business.name());
				} else if (url.endsWith("science")) {
					n.setType(NewsTypes.TYPE.Science.name());
					n.setParentCateName(NewsTypes.CATEGORY.Science.name());
				}else if (url.endsWith("health")) {
					n.setType(NewsTypes.TYPE.Health.name());
					n.setParentCateName(NewsTypes.CATEGORY.Health.name());
				}else if (url.endsWith("politics/index.html")) {
					n.setType(NewsTypes.TYPE.Politics.name());
					n.setParentCateName(NewsTypes.CATEGORY.Politics.name());
				}else if (url.contains("technology")) {
					n.setType(NewsTypes.TYPE.Tech.name());
					n.setParentCateName(NewsTypes.CATEGORY.Tech.name());
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

}
