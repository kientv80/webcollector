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

public class SucKhoeDoiSongParser extends BaseParser{

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		A a = new A("a", null, null, false);
		Title t = new Title("a", null, null, false);
		t.setValueFromAtttributeName("title");
		Image image = new Image("img", null, null, false);
		image.setValueFromAtttributeName("src");
		ShotDescription p = new ShotDescription("p", "class", "sapo_news ", true);
		ShotDescription div = new ShotDescription("div", "class", "sapo_list_news_index fl", true);

		String type = "";
		List<News> news = new ArrayList<>();
		if (url.indexOf("y-hoc-co-truyen-c9") > 0) {
			type = NewsTypes.TYPE.MedicineFood.name();
		} else if (url.indexOf("bai-thuoc-dan-gian-c45") > 0) {
			type = NewsTypes.TYPE.MedicineFood.name();
		} else if (url.indexOf("cay-thuoc-quanh-ta-c4") > 0) {
			type = NewsTypes.TYPE.MedicineFood.name();
		} else if (url.indexOf("am-thuc-c44") > 0) {
			type = NewsTypes.TYPE.HealthyFood.name();
		} else if (url.indexOf("dinh-duong-c38") > 0) {
			type = NewsTypes.TYPE.HealthyFood.name();
		}
		collectNews(source, type, a, t, image, p, div, news, url,fromWebsite);
		return news;
	}

	private List<News> collectNews(Source s, String type, A a, Title t, Image image, ShotDescription p,
			ShotDescription div, List<News> news, String url, String website) {
		if (s.getAllElementsByClass("slide_top pkg").size() > 0) {
			Element top = s.getAllElementsByClass("slide_top pkg").get(0);
			News topNews = new News();
			topNews.setFromWebSite(website);
			topNews.setType(type);
			topNews.setParentCateName(NewsTypes.CATEGORY.Health.name());
			parseElementToNews(top, topNews, a, t, image, p);
			if (topNews.getImageUrl() != null && !topNews.getImageUrl().isEmpty() && topNews.getTitle() != null
					&& !topNews.getTitle().isEmpty()) {
				if (!news.contains(topNews))
					news.add(topNews);
			}
		}

		if (s.getAllElementsByClass("list_news_index list_news_cate").size() > 0) {
			Element listNews = s.getAllElementsByClass("list_news_index list_news_cate").get(0);
			for (Element n : listNews.getChildElements()) {
				News article = new News();
				article.setFromWebSite(website);
				article.setType(type);
				article.setParentCateName(NewsTypes.CATEGORY.Health.name());
				parseElementToNews(n, article, a, t, image, div);
				if (article.getImageUrl() != null && !article.getImageUrl().isEmpty() && article.getTitle() != null
						&& !article.getTitle().isEmpty()) {
					if (!news.contains(article))
						news.add(article);
				}
			}
		}
		return news;
	}
}
