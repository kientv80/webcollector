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

public class TheThaoTVParser extends BaseParser {

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		// Collect in 24h.com first
		ShotDescription p = new ShotDescription("a", "class", "title_item_cate mar_top10 mar_bottom10", true);
		Title title = new Title("a", null, null, false);
		title.setValueFromAtttributeName("title");

		String type = "";
		type = "";
		if (url.indexOf("bong-da") > 0) {
			type = NewsTypes.BONGDA;
		} else if (url.indexOf("tennis") > 0) {
			type = NewsTypes.TENNIS;
		} else if (url.indexOf("golf") > 0) {
			type = NewsTypes.GOLF;
		} else if (url.indexOf("hau-truong") > 0) {
			type = NewsTypes.HAUTRUONG;
		}
		return collectNews(type, p, title, fromWebsite, source);
	}

	private List<News> collectNews(String type, ShotDescription p, Title title, String fromWeb, Source s2) {
		List<News> sportNews = new ArrayList<>();
		// minify_fix_1 -> list ->item
		List<Element> items = s2.getElementById("list_item_cate").getAllElements("li");
		for (Element item : items) {
			News n = new News();
			n.setShotDesc("");
			n.setFromWebSite(fromWeb);
			n.setType(type);
			n.setParentCateName(NewsTypes.TYPE_SPORTNEWS);
			parseElementToNews(item, n, new A(), title, new Image(), p);
			if (n.getTitle() != null && n.getUrl() != null && n.getImageUrl() != null) {
				if (!sportNews.contains(n)) {
					sportNews.add(n);
				}
			}
		}
		
		return sportNews;
	}
}
