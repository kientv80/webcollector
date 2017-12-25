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

public class EpochtimesParser extends BaseParser {

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		List<Element> articles = new ArrayList<>();
		Image i = new Image();
		i.setValueFromAtttributeName("data-src");
		A a = new A();
		
		Title t = new Title("a", null, null, true);
		ShotDescription desc = new ShotDescription("div", "class", "content", true);
		List<News> news = new ArrayList<>();
		String type = "";
		String parentType = "";

		if (url.endsWith("n24hr.htm")) {
			type = NewsTypes.TYPE.HotNews.name();
			parentType = NewsTypes.CATEGORY.HotNews.name();
			articles = source.getElementById("artlist").getAllElements();
		}else if (url.endsWith("ncyule.htm")) {
			type = NewsTypes.TYPE.FamousPerson.name();
			parentType = NewsTypes.CATEGORY.Entertainment.name();
			articles = source.getAllElementsByClass("artbox");
		}else if (url.endsWith("ncid246.htm")) {
			type = NewsTypes.TYPE.Health.name();
			parentType = NewsTypes.CATEGORY.Health.name();
			for(Element e : source.getAllElementsByClass("inspired-category-posts-list")){
				i.setValueFromAtttributeName("src");
				desc = new ShotDescription("div", "class", "desc", true);
				
				articles.addAll(e.getAllElements("li"));
			}
		
		}


		for (Element e : articles) {
			News n = new News();
			n.setType(type);
			n.setParentCateName(parentType);
			n.setFromWebSite(fromWebsite);
			n.setLang(News.LANGUAGE.CHINESE.name());
			n.setCountry(News.COUNTRY.CHINA.name());
			parseElementToNews(e, n, a, t, i, desc);
			if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()
					&& n.getImageUrl() != null && !n.getImageUrl().isEmpty()) {
				if (!news.contains(n)) {
					news.add(n);
				}
			}
		}
		return news;
	}

}
