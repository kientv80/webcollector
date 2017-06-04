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
				News n = new News();
				n.setFromWebSite(fromWebsite);
				if (url.contains("business")) {
					n.setType(NewsTypes.WN_BIZ);
					n.setParentCateName(NewsTypes.WN_BIZ);
				} else if (url.endsWith("science")) {
					n.setType(NewsTypes.WN_SIENCE);
					n.setParentCateName(NewsTypes.WN_SIENCE);
				}else if (url.endsWith("health")) {
					n.setType(NewsTypes.WN_HEALTH);
					n.setParentCateName(NewsTypes.WN_HEALTH);
				}else if (url.endsWith("politics/index.html")) {
					n.setType(NewsTypes.WN_POLITICS);
					n.setParentCateName(NewsTypes.WN_POLITICS);
				}else if (url.contains("technology")) {
					n.setType(NewsTypes.WN_TECH);
					n.setParentCateName(NewsTypes.WN_TECH);
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
