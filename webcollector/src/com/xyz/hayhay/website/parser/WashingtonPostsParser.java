package com.xyz.hayhay.website.parser;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.entirty.NewsTypes;
import com.xyz.hayhay.entirty.webcollection.A;
import com.xyz.hayhay.entirty.webcollection.Image;
import com.xyz.hayhay.entirty.webcollection.ShotDescription;
import com.xyz.hayhay.entirty.webcollection.Title;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class WashingtonPostsParser extends BaseParser {

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		List<News> news = new ArrayList<>();
		List<Element> articles = new ArrayList<>();
		List<Element> elements = source.getAllElementsByClass("col-lg-10 col-xs-12 layout in-chain border-bottom-hairline border-bottom-");
		
		if (elements != null){
			for (Element e : elements) {
				articles.addAll(e.getAllElementsByClass("row"));
			}
		}
		
		
		String [] classes = new String[]{
		"story-list-story row first default","story-list-story row default","story-list-story row last default"};
		for(Element e : source.getAllElementsByClass("story-list default")){
			for(String cl : classes){
				articles.addAll(e.getAllElementsByClass(cl));
			}
		}
		
		
		classes = new String[]{
				"story-list-story row item"};
		
		for(Element e : source.getAllElementsByClass("col-md-12 col-lg-12")){
			for(String cl : classes){
				articles.addAll(e.getAllElementsByClass(cl));
			}
		}

		
		for(Element e : source.getAllElementsByClass("col-md-12 col-lg-11 col-lg-offset-1")){
			for(String cl : classes){
				articles.addAll(e.getAllElementsByClass(cl));
			}
		}

		
		if (articles != null && !articles.isEmpty()) {
			A a = new A();

			Title title = new Title("a", null, null, true);
			title.setValueFromAtttributeName("alt");
			Image i = new Image();
			i.setValueFromAtttributeName("data-low-res-src");
			ShotDescription p = new ShotDescription("div", "data-pb-field", "summary", true);
			
			if (!url.equals("https://www.washingtonpost.com/")) {
				title = new Title("a", "data-pb-local-content-field", "web_headline", true);
				i = new Image();
				i.setValueFromAtttributeName("data-low-res-src");
				a = new A("a", "data-pb-local-content-field", "web_headline", false);
				p = new ShotDescription("p", "data-pb-local-content-field", "summary", true);
			}
			
			for (Element article : articles) {
				News n = new News(News.COUNTRY.US.name());
				n.setFromWebSite(fromWebsite);
				if (url.equals("https://www.washingtonpost.com/")) {
					n.setType(NewsTypes.TYPE.HotNews.name());
					n.setParentCateName(NewsTypes.CATEGORY.HotNews.name());
				}else if (url.equals("https://www.washingtonpost.com/politics")) {
					n.setType(NewsTypes.TYPE.Politics.name());
					n.setParentCateName(NewsTypes.CATEGORY.Politics.name());
				}else if (url.equals("https://www.washingtonpost.com/business")) {
					n.setType(NewsTypes.TYPE.Business.name());
					n.setParentCateName(NewsTypes.CATEGORY.Business.name());
				}else if (url.equals("https://www.washingtonpost.com/business/technology")) {
					n.setType(NewsTypes.TYPE.Tech.name());
					n.setParentCateName(NewsTypes.CATEGORY.Tech.name());
				} else if (url.equals("https://www.washingtonpost.com/opinions")) {
					n.setType(NewsTypes.TYPE.Opinion.name());
					n.setParentCateName(NewsTypes.CATEGORY.Opinion.name());
				}

				
				parseElementToNews(article, n, a, title, i, p);
				if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getImageUrl() != null
						&& !n.getImageUrl().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()
						&& !news.contains(n)) {
					news.add(n);
				}
			}

		}
		return news;
	}

}
