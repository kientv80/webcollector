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

public class Thethao247Parser extends BaseParser {

	@Override
	public List<News> collectArticle(Source s, String url, String fromWebsite) {
		ShotDescription p = new ShotDescription("p", null, null, true);
		Title title = new Title("a", "class", "thumb_cat_row", false);
		title.setValueFromAtttributeName("title");
		Image image = new Image("img", "width", "200", false);
		image.setValueFromAtttributeName("src");
		A a = new A();
		String type = NewsTypes.TYPE.Tennis.name();

		List<News> sportNews = new ArrayList<>();
		List<Element> newsItems = s.getAllElementsByClass("cat-row");
		if (newsItems != null && newsItems.size() > 0) {
			for (Element item : newsItems) {
				News n = new News();
				n.setType(type);
				n.setParentCateName(NewsTypes.CATEGORY.Sport.name());
				n.setFromWebSite(fromWebsite);
				parseElementToNews(item, n, a, title, image, p);
				if (n.getTitle() != null && n.getUrl() != null && n.getImageUrl() != null) {
					if (!sportNews.contains(n)) {
						sportNews.add(n);
					}
				}
			}
		}
		return sportNews;
	}

}
