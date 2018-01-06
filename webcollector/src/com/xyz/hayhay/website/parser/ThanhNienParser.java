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

public class ThanhNienParser extends BaseParser{

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		List<Element> articles = null;
		Image i = new Image();
		A a = new A();
		a.setDomain("https://thanhnien.vn");
		
		Title t = new Title("a",null,null,false);
		t.setValueFromAtttributeName("title");
		ShotDescription desc = new ShotDescription("div","class","summary",true);
		List<News> news = new ArrayList<>();
		String type = NewsTypes.TYPE.HotNews.name();
		String parentType = NewsTypes.CATEGORY.HotNews.name();
		articles = source.getAllElementsByClass("subcate-highlight clearfix").get(0).getAllElements("article");
		articles.addAll(source.getAllElementsByClass("cate-list").get(0).getAllElements("article"));
		
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
