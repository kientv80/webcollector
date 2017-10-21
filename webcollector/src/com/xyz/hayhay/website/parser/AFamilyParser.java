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
		Image i = new Image();
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
			articles = source.getAllElementsByClass("list-news1").get(0).getChildElements();
		}else if(url.endsWith("me-va-be.chn")){
			type = NewsTypes.TYPE.Family.name();
			parentType = NewsTypes.CATEGORY.Love.name();
			articles = source.getAllElementsByClass("list-news1").get(0).getChildElements();
		}else if(url.endsWith("suc-khoe.chn")){
			type = NewsTypes.TYPE.Health.name();
			parentType = NewsTypes.CATEGORY.Health.name();
			articles = source.getAllElementsByClass("list-news1").get(0).getChildElements();
		} else{//expect this is cooking
			if(url.endsWith("mon-an-tu-thit-heo.htm")){
				type = NewsTypes.TYPE.CookingPig.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();
			}else if(url.endsWith("mon-an-tu-thit-ga.htm")){
				type = NewsTypes.TYPE.CookingHen.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();;
			}else if(url.endsWith("mon-an-tu-tom.htm")){
				type = NewsTypes.TYPE.CookingShrimp.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();;
			}else if(url.endsWith("mon-an-tu-rau-cu.htm")){
				type = NewsTypes.TYPE.CookingVegetable.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();;
			}else if(url.endsWith("mon-an-tu-trung.htm")){
				type = NewsTypes.TYPE.CookingEgg.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();;
			}else if(url.endsWith("mon-an-y.htm")){
				type = NewsTypes.TYPE.CookingItaly.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();;
			}else if(url.endsWith("mon-an-han-quoc.htm")){
				type = NewsTypes.TYPE.CookingKorea.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();;
			}else if(url.endsWith("mon-an-nhat-ban.htm")){
				type = NewsTypes.TYPE.CookingJapan.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();;
			}else if(url.endsWith("mon-an-thai-lan.htm")){
				type = NewsTypes.TYPE.CookingThai.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();;
			}else if(url.endsWith("mon-an-phap.htm")){
				type = NewsTypes.TYPE.CookingFrance.name();
				parentType = NewsTypes.CATEGORY.Cooking.name();;
			}
			
			articles = source.getAllElementsByClass("solr-content").get(0).getChildElements().get(0).getChildElements();
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
