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

public class ChinaDailyParser extends BaseParser {

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		List<News> news = new ArrayList<>();
		List<Element> articles = source.getElementById("D1pic1").getAllElementsByClass("fcon");
		articles.addAll(source.getAllElementsByClass("mb10 tw3_01"));
		articles.addAll(source.getAllElementsByClass("tw2_l"));
		articles.addAll(source.getAllElementsByClass("tw4"));
		articles.addAll(source.getAllElementsByClass("tw6"));
		
		A a = new A();
		a.setDomain("http://www.chinadaily.com.cn/china/");
		Title title = new Title("a", null, null, true);
		Image i = new Image();
		i.setDomain("http://www.chinadaily.com.cn/china/");
		ShotDescription p = new ShotDescription("p", "class", "summary", true);
		if (articles != null && !articles.isEmpty()) {
			for (Element article : articles) {
				News n = new News(News.COUNTRY.CHINA.name());
				n.setFromWebSite(fromWebsite);
				if(url.contains("business")){
					n.setType(NewsTypes.TYPE.Business.name());
					n.setParentCateName(NewsTypes.CATEGORY.Business.name());
				}else{
					n.setType(NewsTypes.TYPE.HotNews.name());
					n.setParentCateName(NewsTypes.CATEGORY.HotNews.name());
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
