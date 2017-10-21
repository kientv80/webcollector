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

public class BizLiveParser extends BaseParser {

	@Override
	public List<News> collectArticle(Source s, String url, String fromWebsite) {
		A a = new A();
		a.setDomain("http://bizlive.vn/");
		Title t = new Title("a", null, null, false);
		t.setValueFromAtttributeName("title");

		Image image = new Image("img", null, null, false);
		image.setValueFromAtttributeName("src");
		ShotDescription p = new ShotDescription("span", "class", "imgtitle", true);
		List<News> news = new ArrayList<>();
		String type = NewsTypes.TYPE.Finance.name();
		if (url.contains("vang-tien")) {
			type = NewsTypes.TYPE.Gold.name();
		} else if (url.contains("chung-khoan")) {
			type = NewsTypes.TYPE.Stock.name();
		}
		if (s.getAllElementsByClass("col-440") != null && s.getAllElementsByClass("col-440").size() > 0) {
			Element topNews = s.getAllElementsByClass("col-440").get(0);
			Element hotNews = topNews.getChildElements().get(1);
			News mn = new News();
			mn.setFromWebSite(fromWebsite);
			mn.setType(type);
			mn.setNewsOrder(News.NEWS_ORDER.HI.name());
			mn.setHotNews(true);
			mn.setParentCateName(NewsTypes.CATEGORY.Economic.name());
			parseElementToNews(hotNews, mn, a, t, image, p);
			if (mn.getTitle() != null && !mn.getTitle().isEmpty() && mn.getUrl() != null && !mn.getUrl().isEmpty()
					&& mn.getImageUrl() != null && !mn.getImageUrl().isEmpty()) {
				if (!news.contains(mn)) {
					news.add(mn);
				}
			}
			for (Element mdnews : s.getAllElementsByClass("list-item clearfix").get(0).getChildElements().get(0)
					.getChildElements()) {
				News mdnew = new News();
				mdnew.setFromWebSite(fromWebsite);
				mdnew.setType(type);
				mdnew.setNewsOrder(News.NEWS_ORDER.HI.name());
				mdnew.setParentCateName(NewsTypes.CATEGORY.Economic.name());
				parseElementToNews(mdnews, mdnew, a, t, image, null);
				if (mdnew.getTitle() != null && !mdnew.getTitle().isEmpty() && mdnew.getUrl() != null
						&& !mdnew.getUrl().isEmpty() && mdnew.getImageUrl() != null && !mdnew.getImageUrl().isEmpty()) {
					if (!news.contains(mdnew)) {
						news.add(mdnew);
					}
				}
			}

			for (Element mdnews : s.getAllElementsByClass("list-item-news-1 clearfix").get(0).getChildElements().get(0)
					.getChildElements()) {
				News mdnew = new News();
				mdnew.setFromWebSite(fromWebsite);
				mdnew.setType(type);
				mdnew.setNewsOrder(News.NEWS_ORDER.HI.name());
				mdnew.setParentCateName(NewsTypes.CATEGORY.Economic.name());
				parseElementToNews(mdnews, mdnew, a, t, image, null);
				if (mdnew.getTitle() != null && !mdnew.getTitle().isEmpty() && mdnew.getUrl() != null
						&& !mdnew.getUrl().isEmpty() && mdnew.getImageUrl() != null && !mdnew.getImageUrl().isEmpty()) {
					if (!news.contains(mdnew)) {
						news.add(mdnew);
					}
				}
			}
		}
		return news;
	}

}
