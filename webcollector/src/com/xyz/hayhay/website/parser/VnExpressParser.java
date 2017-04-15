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

public class VnExpressParser extends BaseParser {

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		return collectNews(source, url,fromWebsite);
	}

	private List<News> collectNews(Source source, String url,String fromWebsite) {
		String type = "";
		String parentType = "";
		if (url.indexOf("thoi-su") > 0) {
			type = NewsTypes.TYPE_TINTRONGNUOC;
			parentType = NewsTypes.TYPE_TINTRONGNUOC;
		} else if (url.indexOf("bat-dong-san") > 0) {
			type = NewsTypes.BATDONGSAN;
			parentType = NewsTypes.XAYDUNG;
		}else if (url.indexOf("o-to.html") > 0){
			type = NewsTypes.OTO;
			parentType = NewsTypes.XE;
		}else if (url.indexOf("xe-do.html") > 0){
			type = NewsTypes.XEDO;
			parentType = NewsTypes.XE;
		}else if (url.indexOf("sieu-xe.html") > 0){
			type = NewsTypes.SIEUXE;
			parentType = NewsTypes.XE;
		}else if (url.indexOf("tin-tuc/cuoi") > 0){
			type = NewsTypes.FUNYSTORY;
			parentType = NewsTypes.FUNYSTORY;
		}else if (url.indexOf("tin-tuc/khoi-nghiep") > 0){
			type = NewsTypes.KHOINGHIEP;
			parentType = NewsTypes.KHOINGHIEP;
		} else if (url.indexOf("/tin-tuc/the-gioi") > 0){
			type = NewsTypes.TYPE_TINQUOCTE;
			parentType = NewsTypes.TYPE_TINQUOCTE;
		}
		if(type.isEmpty())
			return null;
		
		List<News> vnExpressNews = new ArrayList<>();
		A a = new A("a", null, null, false);
		a.setValueFromAtttributeName("href");
		Title t = new Title("a", "class", "txt_link", true);
		Image image = new Image("img", "class", null, false);
		image.setExcludedTexts("http://st.f1.kinhdoanh.vnecdn.net/i/v4/icons/icon_more.png");
		image.setValueFromAtttributeName("src");
		ShotDescription p = new ShotDescription("h4", "class", "news_lead", true);
		News n = new News();
		n.setFromWebSite(fromWebsite);
		n.setHotNews(true);
		n.setType(type);
		n.setParentCateName(parentType);
		parseElementToNews(source.getAllElementsByClass("box_hot_news").get(0), n, a, t, image, p);
		if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()
				&& n.getImageUrl() != null && !n.getImageUrl().isEmpty()) {
			if (!vnExpressNews.contains(n)) {
				vnExpressNews.add(n);
			}
		}

		for (Element midNews : source.getAllElementsByClass("content_scoller width_common").get(0).getChildElements()
				.get(0).getChildElements()) {
			News mn = new News();
			mn.setFromWebSite(fromWebsite);
			mn.setType(type);
			mn.setParentCateName(parentType);
			mn.setNewsOrder(News.NEWS_ORDER.HI.name());
			parseElementToNews(midNews, mn, a, t, image, p);
			if (mn.getTitle() != null && !mn.getTitle().isEmpty() && mn.getUrl() != null && !mn.getUrl().isEmpty()
					&& mn.getImageUrl() != null && !mn.getImageUrl().isEmpty()) {
				if (!vnExpressNews.contains(mn)) {
					vnExpressNews.add(mn);
				}
			}
		}
		t.setElementAttribute(null);
		t.setElementValue(null);
		for (Element midNews : source.getElementById("news_home").getChildElements()) {
			News mn = new News();
			mn.setFromWebSite(fromWebsite);
			mn.setType(type);
			mn.setParentCateName(parentType);
			mn.setNewsOrder(News.NEWS_ORDER.HI.name());
			parseElementToNews(midNews, mn, a, t, image, p);
			if (mn.getTitle() != null && !mn.getTitle().isEmpty() && mn.getUrl() != null && !mn.getUrl().isEmpty()
					&& mn.getImageUrl() != null && !mn.getImageUrl().isEmpty()) {
				if (!vnExpressNews.contains(mn)) {
					vnExpressNews.add(mn);
				}
			}
		}
		if (source.getAllElementsByClass("list_new_140 list_col") != null
				&& source.getAllElementsByClass("list_new_140 list_col").size() > 0) {
			for (Element midNews : source.getAllElementsByClass("list_new_140 list_col").get(0).getChildElements()) {
				News mn = new News();
				mn.setFromWebSite(fromWebsite);
				mn.setType(type);
				mn.setParentCateName(parentType);
				mn.setNewsOrder(News.NEWS_ORDER.M.name());
				parseElementToNews(midNews, mn, a, t, image, p);
				if (mn.getTitle() != null && !mn.getTitle().isEmpty() && mn.getUrl() != null && !mn.getUrl().isEmpty()
						&& mn.getImageUrl() != null && !mn.getImageUrl().isEmpty()) {
					if (!vnExpressNews.contains(mn)) {
						vnExpressNews.add(mn);
					}
				}
			}

			if (source.getAllElementsByClass("list_new_140 list_col") != null
					&& source.getAllElementsByClass("list_new_140 list_col").size() > 0) {
				for (Element midNews : source.getAllElementsByClass("list_new_140 list_col").get(1)
						.getChildElements()) {
					News mn = new News();
					mn.setFromWebSite(fromWebsite);
					mn.setType(type);
					mn.setParentCateName(parentType);
					mn.setNewsOrder(News.NEWS_ORDER.M.name());
					parseElementToNews(midNews, mn, a, t, image, p);
					if (mn.getTitle() != null && !mn.getTitle().isEmpty() && mn.getUrl() != null
							&& !mn.getUrl().isEmpty() && mn.getImageUrl() != null && !mn.getImageUrl().isEmpty()) {
						if (!vnExpressNews.contains(mn)) {
							vnExpressNews.add(mn);
						}
					}
				}
			}
			if (source.getAllElementsByClass("list_new_140 list_col end") != null
					&& source.getAllElementsByClass("list_new_140 list_col end").size() > 0) {
				for (Element midNews : source.getAllElementsByClass("list_new_140 list_col end").get(0)
						.getChildElements()) {
					News mn = new News();
					mn.setFromWebSite(fromWebsite);
					mn.setType(type);
					mn.setParentCateName(parentType);
					mn.setNewsOrder(News.NEWS_ORDER.M.name());
					parseElementToNews(midNews, mn, a, t, image, p);
					if (mn.getTitle() != null && !mn.getTitle().isEmpty() && mn.getUrl() != null
							&& !mn.getUrl().isEmpty() && mn.getImageUrl() != null && !mn.getImageUrl().isEmpty()) {
						if (!vnExpressNews.contains(mn)) {
							vnExpressNews.add(mn);
						}
					}
				}
			}
		}
		return vnExpressNews;
	}
}
