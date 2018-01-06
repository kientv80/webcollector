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

public class TuoiTreParser extends BaseParser {

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		A a = new A("a", null, null, false);
		a.setDomain("http://tuoitre.vn");
		a.setValueFromAtttributeName("href");
		Title t = new Title("img", null, null, false);
		t.setValueFromAtttributeName("alt");
		Image image = new Image("img", null, null, false);
		image.setValueFromAtttributeName("src");
		ShotDescription p = new ShotDescription("p", null, null, true);

		String type = NewsTypes.TYPE.HotNews.name();
		String parentCate = NewsTypes.CATEGORY.HotNews.name();
		if (url.endsWith("kinh-doanh.htm")){
			type = NewsTypes.TYPE.Economic.name();
			parentCate = NewsTypes.CATEGORY.Economic.name();
		}else if (fromWebsite.endsWith("congnghe.tuoitre.vn")){
			type = NewsTypes.TYPE.Tech.name();
			parentCate = NewsTypes.CATEGORY.Tech.name();
		}
		if(type == null || type.isEmpty())
			return null;
		
		return collectArticles(source, a, t, image, p, type,parentCate, fromWebsite);
	}

	private List<News> collectArticles(Source source, A a, Title t, Image image, ShotDescription p, String type,String parentCate,
			String fromWebsite) {
		List<News> tuoitreNews = new ArrayList<>();
		
		List<Element> hostNews = source.getAllElementsByClass("list-top").get(0).getAllElements("li");
		for(Element hn : hostNews){
			News n = new News();
			n.setFromWebSite(fromWebsite);
			n.setHotNews(true);
			n.setType(type);
			n.setParentCateName(parentCate);
			parseElementToNews(hn, n, a, t, image, p);
			if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()
					&& n.getImageUrl() != null && !n.getImageUrl().isEmpty()) {
				if (!tuoitreNews.contains(n)) {
					tuoitreNews.add(n);
				}
			}
		}
		
		t = new Title("a", "class", "ff-bold", true);
		if (source.getAllElementsByClass("list-news-content") != null && source.getAllElementsByClass("list-news-content").size() > 0) {
			for (Element midNews : source.getAllElementsByClass("list-news-content").get(0).getChildElements()) {
				News mn = new News();
				mn.setFromWebSite(fromWebsite);
				mn.setType(type);
				mn.setNewsOrder(News.NEWS_ORDER.HI.name());
				mn.setParentCateName(parentCate);
				parseElementToNews(midNews, mn, a, t, image, p);
				if (mn.getTitle() != null && !mn.getTitle().isEmpty() && mn.getUrl() != null && !mn.getUrl().isEmpty()
						&& mn.getImageUrl() != null && !mn.getImageUrl().isEmpty()) {
					if (!tuoitreNews.contains(mn)) {
						tuoitreNews.add(mn);
					}
				}
			}
		}

		
		return tuoitreNews;
	}
}
