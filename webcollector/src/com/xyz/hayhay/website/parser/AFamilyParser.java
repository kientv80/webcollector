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

public class AFamilyParser extends BaseParser{

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		List<Element> articles = null;
		Image i = new Image("a","class","afwblul-thumb", false);
		i.setValueFromAtttributeName("style");
		A a = new A();
		a.setDomain("http://m.afamily.vn");
		Title t = new Title("a",null,null,false);
		t.setValueFromAtttributeName("title");
		ShotDescription desc = new ShotDescription("p",null,null,true);
		List<News> news = new ArrayList<>();
		String type = "";
		String parentType = "";
		if(url.endsWith("tinh-yeu-hon-nhan.chn")){
			type = NewsTypes.TYPE.Love.name();
			parentType = NewsTypes.CATEGORY.Love.name();
			articles = source.getAllElementsByClass("afctr-ul").get(0).getChildElements();
		}else if(url.endsWith("me-va-be.chn")){
			type = NewsTypes.TYPE.Family.name();
			parentType = NewsTypes.CATEGORY.Love.name();
			articles = source.getAllElementsByClass("afctr-ul").get(0).getChildElements();
		}else if(url.endsWith("suc-khoe.chn")){
			type = NewsTypes.TYPE.Health.name();
			parentType = NewsTypes.CATEGORY.Health.name();
			articles = source.getAllElementsByClass("afctr-ul").get(0).getChildElements();
		} else{//expect this is cooking
			if(url.endsWith("mon-an-tu-thit-heo.html")){
				type = NewsTypes.TYPE.CookingPig.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();
			}else if(url.endsWith("mon-an-tu-thit-ga.html")){
				type = NewsTypes.TYPE.CookingHen.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();;
			}else if(url.endsWith("mon-an-tu-tom.html")){
				type = NewsTypes.TYPE.CookingShrimp.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();;
			}else if(url.endsWith("mon-an-tu-rau-cu.html")){
				type = NewsTypes.TYPE.CookingVegetable.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();;
			}else if(url.endsWith("mon-an-tu-trung.html")){
				type = NewsTypes.TYPE.CookingEgg.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();;
			}else if(url.endsWith("mon-an-y.html")){
				type = NewsTypes.TYPE.CookingItaly.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();;
			}else if(url.endsWith("mon-an-han-quoc.html")){
				type = NewsTypes.TYPE.CookingKorea.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();;
			}else if(url.endsWith("mon-an-nhat-ban.html")){
				type = NewsTypes.TYPE.CookingJapan.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();;
			}else if(url.endsWith("mon-an-thai-lan.html")){
				type = NewsTypes.TYPE.CookingThai.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();;
			}else if(url.endsWith("mon-an-phap.html")){
				type = NewsTypes.TYPE.CookingFrance.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();;
			}
			
			if(articles == null )
				articles = new ArrayList<>();
			
			for(Element e : source.getAllElementsByClass("afwbl-ul")) {
				articles.addAll(e.getAllElements("li"));
			}

		}
		if(type.isEmpty())
			return null;
		
		for(Element e : articles){
			News n = new News();
			n.setType(type);
			n.setParentCateName(parentType);
			n.setFromWebSite(fromWebsite);
			parseElementToNews(e, n, a, t, i, desc);
			if(n.getImageUrl() !=  null && !n.getImageUrl().isEmpty()) {
				//System.out.println(n.getImageUrl());
				n.setImageUrl(n.getImageUrl().substring(n.getImageUrl().indexOf("https:"), n.getImageUrl().indexOf("')")));
			}
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
