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

public class PetrotimesParser extends BaseParser {
	@Override
	public List<News> collectArticle(Source s, String url, String fromWeb) {
		return null;
	}
	
//	@Override
	public List<News> collectArticleBK(Source s, String url, String fromWeb) {
		A a = new A();
		Title t = new Title("a", null, null, true);
		t.setValueFromAtttributeName("title");
		Image image = new Image();
		image.setValueFromAtttributeName("src");
		ShotDescription p = new ShotDescription("p", "itemprop", "description", true);
		List<News> news = new ArrayList<>();
		News hotN = new News();
		hotN.setFromWebSite(fromWeb);
		hotN.setType(NewsTypes.TYPE.Economic.name());
		hotN.setParentCateName(NewsTypes.CATEGORY.Economic.name());
		if (s.getAllElementsByClass("fullboxNewsHotItem clearfix") != null
				&& s.getAllElementsByClass("fullboxNewsHotItem clearfix").size() > 0) {
			Element hot = s.getAllElementsByClass("fullboxNewsHotItem clearfix").get(0);
			
			parseElementToNews(hot, hotN, a, t, image, p);
			if (hotN.getTitle() != null && !hotN.getTitle().isEmpty() && hotN.getUrl() != null
					&& !hotN.getUrl().isEmpty() && hotN.getImageUrl() != null && !hotN.getImageUrl().isEmpty()) {
				if (!news.contains(hotN)) {
					news.add(hotN);
				}
			}
		}
		for (Element e : s.getAllElementsByClass("listINewsHotListing clearfix").get(0).getChildElements().get(0)
				.getChildElements()) {
			News n = new News();
			n.setFromWebSite(fromWeb);
			n.setType(NewsTypes.TYPE.Economic.name());
			hotN.setParentCateName(NewsTypes.CATEGORY.Economic.name());
			parseElementToNews(e, n, a, t, image, p);
			if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()
					&& n.getImageUrl() != null && !n.getImageUrl().isEmpty()) {
				if (!news.contains(n)) {
					news.add(n);
				}
			}
		}

		for (Element e : s.getAllElementsByClass("boxlistingNewsgr").get(0).getAllElements("li")) {
			News n = new News();
			n.setFromWebSite(fromWeb);
			n.setType(NewsTypes.TYPE.Economic.name());
			hotN.setParentCateName(NewsTypes.CATEGORY.Economic.name());
			parseElementToNews(e, n, a, t, image, p);
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
