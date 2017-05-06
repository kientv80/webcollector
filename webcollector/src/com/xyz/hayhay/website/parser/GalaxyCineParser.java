package com.xyz.hayhay.website.parser;

import java.util.ArrayList;
import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.entirty.NewsTypes;
import com.xyz.hayhay.entirty.webcollection.A;
import com.xyz.hayhay.entirty.webcollection.Image;
import com.xyz.hayhay.entirty.webcollection.Title;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class GalaxyCineParser  extends BaseParser{

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		String type = "";
		if (url.indexOf("dang-chieu") > 0) {
			type = "movie_playing";
		} else if (url.indexOf("sap-chieu") > 0) {
			type = "movie_comming";
		} else if (url.indexOf("khuyen-mai") > 0) {
			type = "movie_discount";
		}
		return collectArticles(type, source, fromWebsite);
	}
	private List<News> collectArticles(String type, Source s, String fromWebsite){
		A a = new A();
		Title t = new Title("a", null, null, false);
		t.setValueFromAtttributeName("title");
		Image i = new Image();
		List<Element> articles = s.getAllElements("article");
		List<News> collectedMovies = new ArrayList<>();
		for (Element article : articles) {
			if ("general-block".equals(article.getAttributeValue("class"))) {
				List<Element> movies = article.getAllElements("li");
				for (Element mov : movies) {
					News n = new News();
					n.setFromWebSite(fromWebsite);
					n.setType(type);
					n.setParentCateName(NewsTypes.MOVIE_NEWS);
					parseElementToNews(mov, n, a, t, i, null);
					if (!collectedMovies.contains(n) && n.getTitle() != null && !n.getTitle().isEmpty() && n.getImageUrl() != null && !n.getImageUrl().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()) {
						collectedMovies.add(n);
					}
				}
			}
		}
		return collectedMovies;
	}
}
