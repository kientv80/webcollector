package com.xyz.hayhay.website.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
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

public class SouthChinaMorningPostParser extends BaseParser {

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {

		List<News> news = new ArrayList<>();
		List<Element> articles = source.getAllElements("article");

		A a = new A();
		a.setDomain("http://www.scmp.com/");
		a.setExcludedTexts("article-type,author,columns");
		Title title = new Title("a", null, null, false);
		title.setValueFromAtttributeName("title");
		Image i = new Image();
		i.setValueFromAtttributeName("data-original");
		ShotDescription p = new ShotDescription("p", null, null, true);
		if (articles != null && !articles.isEmpty()) {
			for (Element article : articles) {
				News n = new News(News.COUNTRY.CHINA.name());
				n.setFromWebSite(fromWebsite);
				if (url.contains("china/society")) {
					n.setType(NewsTypes.TYPE.HotNews.name());
					n.setParentCateName(NewsTypes.CATEGORY.HotNews.name());
				} else if (url.contains("china/policies-politics")) {
					n.setType(NewsTypes.TYPE.Politics.name());
					n.setParentCateName(NewsTypes.CATEGORY.Politics.name());
				} else if (url.contains("tech")) {
					n.setType(NewsTypes.TYPE.Tech.name());
					n.setParentCateName(NewsTypes.CATEGORY.Tech.name());
				} else if (url.contains("economy")) {
					n.setType(NewsTypes.TYPE.Economic.name());
					n.setParentCateName(NewsTypes.CATEGORY.Economic.name());
				} else if (url.contains("business")) {
					n.setType(NewsTypes.TYPE.Business.name());
					n.setParentCateName(NewsTypes.CATEGORY.Business.name());
				}
				parseElementToNews(article, n, a, title, i, p);
				if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getImageUrl() != null
						&& !n.getImageUrl().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()
						&& !news.contains(n)) {
					news.add(n);
				}
			}

		}

		return news;
	}

}
