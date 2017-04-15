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

public class MuaBanNhaDatParser  extends BaseParser{

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		A a = new A();
		a.setDomain("http://www.muabannhadat.vn/");
		Title t = new Title("a", "class", "title-filter-link", true);
		Image image = new Image("div", "class", "image-list", false);
		image.setValueFromAtttributeName("style");
		ShotDescription p = new ShotDescription("div", "class", "col-xs-12 hidden-xs hidden-sm cusshort", true);
		String type;
		if (url.indexOf("tp-ho-chi-minh") > 0) {
			type = "BDS_HCM";
		} else if (url.indexOf("tp-ha-noi") > 0) {
			type = "BDS_HN";
		} else if (url.indexOf("tp-da-nang") > 0) {
			type = "BDS_DN";
		} else {
			type = "BDS_VT";
		}
		List<Element> items = source.getAllElementsByClass("resultItem");
		List<News> articles = new ArrayList<News>();
		for (Element e : items) {
			News n = new News();
			n.setFromWebSite(fromWebsite);
			n.setType(type);
			n.setParentCateName(NewsTypes.MUABANNHADAT);
			parseElementToNews(e, n, a, t, image, p);
			if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()
					&& n.getImageUrl() != null && !n.getImageUrl().isEmpty()) {
				String urlImg = n.getImageUrl().substring("background:url('".length()+1, n.getImageUrl().indexOf("')"));
				if (urlImg.startsWith("http")) {
					n.setImageUrl(urlImg);
				} else {
					n.setImageUrl("http://www.muabannhadat.vn" + urlImg);
				}
				if (!articles.contains(n)) {
					articles.add(n);
				}
			}
		}
		return articles;
	}

}
