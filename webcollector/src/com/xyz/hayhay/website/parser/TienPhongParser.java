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

public class TienPhongParser extends BaseParser{

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		
		List<Element> articles = new ArrayList<>();
		Image i = new Image();
		A a = new A();
		Title t = new Title("h2","class","story-heading",true);
		t.setValueFromAtttributeName("title");
		ShotDescription desc = new ShotDescription("p","class","summary",true);
		List<News> news = new ArrayList<>();
		String type = "";
		String parentType = "";
		if(url.endsWith("xa-hoi/") || url.endsWith("the-gioi/") || url.endsWith("phap-luat/") || url.endsWith("giao-duc/") ){
			type = NewsTypes.TYPE.HotNews.name();
			parentType = NewsTypes.CATEGORY.HotNews.name();
		}else if(url.endsWith("kinh-te/")){
			type = NewsTypes.TYPE.Economic.name();
			parentType = NewsTypes.CATEGORY.Economic.name();
		}else if(url.endsWith("dia-oc/")){
			type = NewsTypes.TYPE.Realty.name();
			parentType = NewsTypes.CATEGORY.Realty.name();
		}else if(url.endsWith("/the-thao/")){
			type = NewsTypes.TYPE.Sport.name();
			parentType = NewsTypes.CATEGORY.Sport.name();
		}else if(url.endsWith("xe/")){
			type = NewsTypes.TYPE.Car.name();
			parentType = NewsTypes.CATEGORY.CarAndMotor.name();
		}
		
		for(Element mc : source.getAllElementsByClass("main-content")){
			articles.addAll(mc.getAllElements("article"));
		}
		
		if(type.isEmpty())
			return null;
		
		for(Element e : articles){
			News n = new News();
			n.setType(type);
			n.setParentCateName(parentType);
			n.setFromWebSite(fromWebsite);
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
