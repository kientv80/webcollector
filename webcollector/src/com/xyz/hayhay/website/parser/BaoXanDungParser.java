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

public class BaoXanDungParser extends BaseParser{

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		String type = NewsTypes.TYPE.BeautifulHouse.name();
		if (url.endsWith("/quy-hoach"))
			type = NewsTypes.TYPE.Zoing.name();
		else if (url.endsWith("/vat-lieu"))
			type = NewsTypes.TYPE.Material.name();
		else if (url.endsWith("/kien-truc"))
			type = NewsTypes.TYPE.Architecture.name();
		else if (url.endsWith("/bat-dong-san"))
			type = NewsTypes.TYPE.Realty.name();
		List<Element> articles = source.getElementById("main_content").getAllElements("li");
		A a = new A();
		Title t = new Title("a", null, null, false);
		t.setValueFromAtttributeName("title");
		Image i = new Image();
		ShotDescription desc = new ShotDescription("h3", null, null, true);
		List<News> listNews = new ArrayList<>();
		for (Element e : articles) {
			News n = new News();
			n.setFromWebSite(fromWebsite);
			n.setType(type);
			n.setParentCateName(NewsTypes.CATEGORY.Realty.name());
			parseElementToNews(e, n, a, t, i, desc);
			if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()
					&& n.getImageUrl() != null && !n.getImageUrl().isEmpty()) {
				if (!listNews.contains(n))
					listNews.add(n);
			}
		}
		return listNews;
	}
}
