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

public class DanTriParser  extends BaseParser{

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		A a = new A("a", null, null, false);
		a.setDomain("http://dantri.com.vn/");
		a.setValueFromAtttributeName("href");
		Title t = new Title("a", null, null, true);
		t.setValueFromAtttributeName("title");
		Image image = new Image("img", null, null, false);
		image.setValueFromAtttributeName("src");
		ShotDescription p = new ShotDescription("div", "class", "fon5 wid324 fl", true);
		String type = "";
		if (url.indexOf("tinh-yeu-gioi-tinh.htm") > 0) {
			type = NewsTypes.GIOITINH;
		} else if (url.indexOf("gia-dinh.htm") > 0) {
			type = NewsTypes.GIADINH;
		} else if (url.indexOf("goc-tam-hon.htm") > 0) {
			type = NewsTypes.GOCTAMHON;
		} else {
			type = NewsTypes.TINHYEU;
		}
		List<News> tinhYeuNews = new ArrayList<>();
		List<Element> elements = source.getAllElementsByClass("fl wid470").get(0).getChildElements();
		for (Element e0 : elements) {
			for (Element e : e0.getChildElements()) {
				News n = new News();
				n.setFromWebSite(fromWebsite);
				n.setParentCateName(NewsTypes.TINHYEU);
				n.setType(type);
				parseElementToNews(e, n, a, t, image, p);
				if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()
						&& n.getImageUrl() != null && !n.getImageUrl().isEmpty()) {
					if (!tinhYeuNews.contains(n)) {
						tinhYeuNews.add(n);
					}
				}

			}
		}
		return tinhYeuNews;
	}

}
