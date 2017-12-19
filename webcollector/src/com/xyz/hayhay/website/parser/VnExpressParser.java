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
		return collectNews(source, url, fromWebsite);
	}

	private List<News> collectNews(Source source, String url, String fromWebsite) {
		String type = "";
		String parentType = "";
		if (url.indexOf("thoi-su") > 0) {
			type = NewsTypes.TYPE.HotNews.name();
			parentType = NewsTypes.CATEGORY.HotNews.name();
		} else if (url.indexOf("bat-dong-san") > 0) {
			type = NewsTypes.TYPE.Realty.name();
			parentType = NewsTypes.CATEGORY.Realty.name();
		} else if (url.indexOf("o-to.html") > 0) {
			type = NewsTypes.TYPE.Car.name();
			parentType = NewsTypes.CATEGORY.CarAndMotor.name();
		} else if (url.indexOf("xe-do.html") > 0) {
			type = NewsTypes.TYPE.AdjustCar.name();
			parentType = NewsTypes.CATEGORY.CarAndMotor.name();
		} else if (url.indexOf("sieu-xe.html") > 0) {
			type = NewsTypes.TYPE.SupperCar.name();
			parentType = NewsTypes.CATEGORY.CarAndMotor.name();
		} else if (url.indexOf("tin-tuc/cuoi") > 0) {
			type = NewsTypes.TYPE.Funny.name();
			parentType = NewsTypes.CATEGORY.Funny.name();
		} else if (url.indexOf("tin-tuc/khoi-nghiep") > 0) {
			type = NewsTypes.TYPE.StatUp.name();
			parentType = NewsTypes.CATEGORY.Business.name();
		} else if (url.indexOf("/tin-tuc/the-gioi") > 0) {
			type = NewsTypes.TYPE.HotNews.name();
			parentType = NewsTypes.CATEGORY.HotNews.name();
		} else if (url.contains("oto-xe-may")) {
			type = NewsTypes.TYPE.Car.name();
			parentType = NewsTypes.CATEGORY.CarAndMotor.name();
		}
		if (type.isEmpty())
			return null;

		List<News> vnExpressNews = new ArrayList<>();
		A a = new A("a", null, null, false);
		a.setValueFromAtttributeName("href");
		Title t = new Title("a", "class", "thumb thumb_5x3", false);
		t.setValueFromAtttributeName("title");
		Image image = new Image("img", "class", null, false);
		image.setExcludedTexts("http://st.f1.kinhdoanh.vnecdn.net/i/v4/icons/icon_more.png,data:image/gif");
		image.setValueFromAtttributeName("src,data-original");
		ShotDescription p = new ShotDescription("h4", "class", "description", true);
		News n = new News();
		n.setFromWebSite(fromWebsite);
		n.setHotNews(true);
		n.setType(type);
		n.setParentCateName(parentType);
		try {
			parseElementToNews(
					source.getAllElementsByClass("featured container clearfix").get(0).getAllElements("article").get(0),
					n, a, t, image, new ShotDescription("h2", "class", "description", true));
			if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()
					&& n.getImageUrl() != null && !n.getImageUrl().isEmpty()) {
				if (!vnExpressNews.contains(n)) {
					vnExpressNews.add(n);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		for (Element midNews : source.getAllElements("article")) {
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

		return vnExpressNews;
	}
}
