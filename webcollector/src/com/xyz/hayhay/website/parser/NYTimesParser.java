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
		if(url.endsWith("")){
			articles = source.getElementById("main").getAllElements("article");
		}
		if (articles != null && !articles.isEmpty()) {
			A a = new A();
			Title title = new Title("a", null, null, true);
			Image i = new Image();
			ShotDescription p = new ShotDescription("p", "class", "summary", true);
			for (Element article : articles) {
				News n = new News();
				n.setFromWebSite(fromWebsite);
				if (url.contains("business")) {
					n.setType(NewsTypes.WN_BIZ);
					n.setParentCateName(NewsTypes.WN_BIZ);
				} else if (url.endsWith("science")) {
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
