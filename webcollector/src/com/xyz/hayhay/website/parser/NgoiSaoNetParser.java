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
		A a = new A("a", "class", "title_tin", false);
		a.setValueFromAtttributeName("href");
		Title t = new Title("a", "class", "img_tin", false);
		t.setValueFromAtttributeName("title");
		Image image = new Image("img", null, null, false);
		image.setValueFromAtttributeName("src");
		ShotDescription p = new ShotDescription("div", "class", "lead_tin", true);
		List<News> ngoisaoNet = new ArrayList<>();
		String type = "";
		if (url.indexOf("showbiz-viet") > 0) {
			type = NewsTypes.SHOWBIZVIET;
		} else if (url.indexOf("chau-a") > 0) {
			type = NewsTypes.CHAUA;
		} else if (url.indexOf("hollywood") > 0) {
			type = NewsTypes.HOLLYWOOD;
		} else if (url.indexOf("phong-cach") > 0) {
			type = NewsTypes.PHONGCACH;
		} else {
			type = NewsTypes.BENLE;
		}
		Element homeNews = s.getElementById("news_home");
		for (Element e : homeNews.getChildElements()) {
			News n = new News();
			n.setFromWebSite(fromWeb);
			n.setHotNews(true);
			n.setType(type);
			n.setParentCateName(NewsTypes.NGOISAO);
			parseElementToNews(e, n, a, t, image, p);
			if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()
					&& n.getImageUrl() != null && !n.getImageUrl().isEmpty()) {
				if (!ngoisaoNet.contains(n)) {
					ngoisaoNet.add(n);
				}
			}
		}
		if (s.getAllElementsByClass("list_tin width_common") != null
				&& s.getAllElementsByClass("list_tin width_common").size() > 0) {
			Element tieudiem = s.getAllElementsByClass("list_tin width_common").get(0);
			for (Element e : tieudiem.getChildElements()) {
				News n = new News();
				n.setFromWebSite(fromWeb);
				n.setHotNews(true);
				n.setType(type);
				n.setParentCateName(NewsTypes.NGOISAO);
				parseElementToNews(e, n, a, t, image, p);
				if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()
						&& n.getImageUrl() != null && !n.getImageUrl().isEmpty()) {
					if (!ngoisaoNet.contains(n)) {
						ngoisaoNet.add(n);
					}
				}
			}
		}
		return ngoisaoNet;
	}

}
