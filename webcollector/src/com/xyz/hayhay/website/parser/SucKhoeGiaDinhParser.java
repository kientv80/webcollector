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

public class SucKhoeGiaDinhParser extends BaseParser {

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		List<News> suckhoe = new ArrayList<>();
		Element hotNews = source.getElementById("content");
		A a = new A("a", null, null, false);
		a.setDomain("http://www.suckhoegiadinh.com.vn");
		Title t = new Title("a", null, null, false);
		t.setValueFromAtttributeName("title");
		Image image = new Image("img", null, null, false);
		image.setValueFromAtttributeName("src");
		ShotDescription p = new ShotDescription("p", "class", "lead", true);

		for (Element hotNew : hotNews.getAllElements("li")) {
			News n = new News();
			n.setFromWebSite(fromWebsite);
			n.setParentCateName(NewsTypes.CATEGORY.Health.name());
			n.setType(NewsTypes.TYPE.Health.name());

			parseElementToNews(hotNew, n, a, t, image, p);
			if (n.getImageUrl() != null && !n.getImageUrl().isEmpty() && n.getTitle() != null
					&& !n.getTitle().isEmpty()) {
				if (!suckhoe.contains(n))
					suckhoe.add(n);
			}
		}
		return suckhoe;
	}

}
