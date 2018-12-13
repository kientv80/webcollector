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

public class ScienceNewsParser extends BaseParser{

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		List<News> news = new ArrayList<>();
		List<Element> articles = source.getAllElements("article");
		if(articles != null && articles.size() > 0){
			
			A a = new A();
			a.setDomain("https://www.sciencenews.org");
			Title title = new Title("a", null, null, false);
			title.setValueFromAtttributeName("title");
			Image i = new Image("img","typeof","foaf:Image",false);
			i.setValueFromAtttributeName("src");
			i.setExcludedTexts("data:image");
			ShotDescription p = new ShotDescription("div", "class", "content clearfix", true);
			for(Element article : articles){
				News n = new News(News.COUNTRY.US.name());
				n.setFromWebSite(fromWebsite);
				n.setLang(News.LANGUAGE.ENGLISH.name());
				n.setType(NewsTypes.TYPE.Science.name());
				n.setParentCateName(NewsTypes.CATEGORY.Science.name());
				parseElementToNews(article, n, a, title, i, p);
				if (!news.contains(n) && n.getTitle() != null && !n.getTitle().isEmpty() && n.getImageUrl() != null
						&& !n.getImageUrl().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()) {
					news.add(n);
				}
			}
		}
		
		return news;
	}

}
