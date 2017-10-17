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

public class ZingParser  extends BaseParser{

	@Override
	public List<News> collectArticle(Source s, String url, String fromWebsite) {
		A a = new A();
		a.setDomain("http://news.zing.vn");
		Title t = new Title("img", null, null, false);
		t.setValueFromAtttributeName("title");
		Image image = new Image();
		image.setDomain("https:");
		ShotDescription desc = new ShotDescription("p", "class", "summary", true);
		String type = NewsTypes.XEMAY;
		if (url.indexOf("o-to.html") > 0)
			type = NewsTypes.OTO;
		else if (url.indexOf("xe-do.html") > 0)
			type = NewsTypes.XEDO;
		else if (url.indexOf("sieu-xe.html") > 0)
			type = NewsTypes.SIEUXE;

		Element e = s.getElementById("category").getChildElements().get(0).getChildElements().get(1);
		List<Element> articles = e.getAllElements("article");
		List<News> xe = new ArrayList<>();
		for (Element article : articles) {
			News n = new News();
			n.setFromWebSite("news.zing.vn");
			n.setType(type);
			n.setParentCateName(NewsTypes.XE);
			parseElementToNews(article, n, a, t, image, desc);
			if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()
					&& n.getImageUrl() != null && !n.getImageUrl().isEmpty()) {
				if (!xe.contains(n)) {
					xe.add(n);
				}
			}
		}
		return xe;
	}

}
