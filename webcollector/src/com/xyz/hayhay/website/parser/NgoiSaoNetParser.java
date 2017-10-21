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

public class NgoiSaoNetParser extends BaseParser{

	@Override
	public List<News> collectArticle(Source s, String url, String fromWeb) {
		A a = new A("a", null, null, false);
		a.setValueFromAtttributeName("href");
		Title t = new Title("a", null, null, false);
		t.setValueFromAtttributeName("title");
		Image image = new Image("img", null, null, false);
		image.setValueFromAtttributeName("src");
		ShotDescription p = new ShotDescription("p", null, null, true);
		List<News> ngoisaoNet = new ArrayList<>();
		String type = "";
		if (url.indexOf("showbiz-viet") > 0) {
			type = NewsTypes.TYPE.FamousPerson.name();
		} else if (url.indexOf("chau-a") > 0) {
			type = NewsTypes.TYPE.FamousPerson.name();
		} else if (url.indexOf("hollywood") > 0) {
			type = NewsTypes.TYPE.FamousPerson.name();
		} else if (url.indexOf("phong-cach") > 0) {
			type = NewsTypes.TYPE.Style.name();
		} else {
			type = NewsTypes.TYPE.BehindTheScenes.name();
		}
		Element homeNews = s.getElementById("news_home");
		List<Element> news = s.getElementById("box_tintop_new").getAllElements("li");
		news.addAll(homeNews.getChildElements());
		
		for (Element e : news) {
			News n = new News();
			n.setFromWebSite(fromWeb);
			n.setHotNews(true);
			n.setType(type);
			n.setParentCateName(NewsTypes.CATEGORY.Entertainment.name());
			parseElementToNews(e, n, a, t, image, p);
			if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()
					&& n.getImageUrl() != null && !n.getImageUrl().isEmpty()) {
				if (!ngoisaoNet.contains(n)) {
					ngoisaoNet.add(n);
				}
			}
		}
		
		return ngoisaoNet;
	}

}
