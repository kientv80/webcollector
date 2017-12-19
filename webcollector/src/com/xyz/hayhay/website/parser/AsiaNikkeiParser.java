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

public class AsiaNikkeiParser extends BaseParser {

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		List<News> news = new ArrayList<>();
		List<Element> newsFeed = source.getAllElementsByClass("article-box");
		List<Element> articles =  newsFeed.get(0).getAllElements("li");

		if (articles != null && !articles.isEmpty()) {
			A a = new A();
			a.setDomain("https://asia.nikkei.com");
			
			
			Title title = new Title("a", null, null, true);
			title.setValueFromAtttributeName("alt");
			Image i = new Image();
			i.setDomain("https://asia.nikkei.com");
			ShotDescription p = new ShotDescription("p", "class", "subhead02", true);
			for (Element article : articles) {
				News n = new News(News.COUNTRY.ASIAN.name());
				n.setFromWebSite(fromWebsite);
				if (url.endsWith("Policy-Politics")) {
					n.setType(NewsTypes.TYPE.Politics.name());
					n.setParentCateName(NewsTypes.CATEGORY.Politics.name());
				} else if (url.contains("Economy")) {
					n.setType(NewsTypes.TYPE.Economic.name());
					n.setParentCateName(NewsTypes.CATEGORY.Economic.name());
				} else if (url.contains("Companies")) {
					n.setType(NewsTypes.TYPE.Company.name());
					n.setParentCateName(NewsTypes.CATEGORY.Business.name());
				} else if (url.contains("Capital-Markets")) {
					n.setType(NewsTypes.TYPE.Economic.name());
					n.setParentCateName(NewsTypes.CATEGORY.Economic.name());
				} else if (url.contains("Tech")) {
					n.setType(NewsTypes.TYPE.Tech.name());
					n.setParentCateName(NewsTypes.CATEGORY.Tech.name());
				} else if (url.contains("Tech")) {
					n.setType(NewsTypes.TYPE.Science.name());
					n.setParentCateName(NewsTypes.CATEGORY.Science.name());
				}
				parseElementToNews(article, n, a, title, i, p);
				if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getImageUrl() != null
						&& !n.getImageUrl().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty() && !news.contains(n)) {
					news.add(n);
				}
			}

		}
		return news;
	}
	public enum ServerStatus {
        New, Running,Up,Down,Overloaded
    }
	public static void main(String[] aa) {
		System.out.println(ServerStatus.Running.ordinal());

	}
}
