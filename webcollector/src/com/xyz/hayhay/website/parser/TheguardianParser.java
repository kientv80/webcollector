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

public class TheguardianParser extends BaseParser {

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		List<News> news = new ArrayList<>();
		List<Element> newsFeed = source.getAllElementsByClass("fc-item__container");
		
		
//		if (newsFeed != null && !newsFeed.isEmpty()) {
//			A a = new A();
//			Title title = new Title("span", "class", "js-headline-text", true);
//			Image i = new Image("source","media","(min-width: 740px)",false);
//			i.setValueFromAtttributeName("srcset");
//			
//			ShotDescription p = new ShotDescription("h3", "class", "fc-sublink__title", true);
//			
//			for (Element article : newsFeed) {
//				News n = new News(News.COUNTRY.BRITISH.name());
//				n.setFromWebSite(fromWebsite);
//				n.setLang(News.LANGUAGE.ENGLISH.name());
//				if (url.endsWith("/international") || url.endsWith("/uk-news") || url.endsWith("/cities")) {
//					n.setType(NewsTypes.TYPE.HotNews.name());
//					n.setParentCateName(NewsTypes.CATEGORY.HotNews.name());
//					
//				}else if (url.endsWith("/science")) {
//					n.setType(NewsTypes.TYPE.Science.name());
//					n.setParentCateName(NewsTypes.CATEGORY.Science.name());
//					
//				}else if (url.endsWith("/technology")) {
//					n.setType(NewsTypes.TYPE.Tech.name());
//					n.setParentCateName(NewsTypes.CATEGORY.Tech.name());
//					
//				}else if (url.endsWith("/business")) {
//					n.setType(NewsTypes.TYPE.Business.name());
//					n.setParentCateName(NewsTypes.CATEGORY.Business.name());
//				}else if (url.endsWith("/economics")) {
//					n.setType(NewsTypes.TYPE.Economic.name());
//					n.setParentCateName(NewsTypes.CATEGORY.Economic.name());
//				}else if (url.endsWith("/commentisfree")) {
//					n.setType(NewsTypes.TYPE.Opinion.name());
//					n.setParentCateName(NewsTypes.CATEGORY.Opinion.name());
//				}
//				
//				
//				parseElementToNews(article, n, a, title, i, p);
//				if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getImageUrl() != null
//						&& !n.getImageUrl().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty() && !news.contains(n)) {
//					String turl = n.getImageUrl().split(" ")[0];
//					n.setImageUrl(turl);
//					news.add(n);
//				}
//			}
//
//		}
		return news;
	}
	
}
