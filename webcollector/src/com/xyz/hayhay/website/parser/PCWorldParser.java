package com.xyz.hayhay.website.parser;

import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.entirty.NewsTypes;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class PCWorldParser extends BaseParser{

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		String title;
		String shotDesc;
		String imageUrl;
		//Collect from PCworld
		List<News> congngheNews = new ArrayList<>();
		try {
			URL url2 = new URL("http://www.pcworld.com.vn/articles/cong-nghe");
			HttpURLConnection connection = (HttpURLConnection) url2.openConnection();
			connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/42.0.2311.135 Safari/537.36");
			Source pcw = new Source(connection.getInputStream());
			List<Element> articles = pcw.getAllElementsByClass("list-area");
			for(Element art : articles){
				url = "";
				title = "";
				imageUrl = "";
				shotDesc = "";
				Element mni = art.getChildElements().get(0).getChildElements().get(0);
				url = "http://www.pcworld.com.vn/"+mni.getAttributeValue("href");
				imageUrl = "http://www.pcworld.com.vn/"+mni.getChildElements().get(0).getAttributeValue("src");
				mni = art.getChildElements().get(1).getChildElements().get(0);
				title = mni.getTextExtractor().toString();
				mni = art.getChildElements().get(1).getChildElements().get(2);
				shotDesc = mni.getTextExtractor().toString();
				if (!title.isEmpty() && !shotDesc.isEmpty() && !url.isEmpty() && !imageUrl.isEmpty()) {
					News n = new News();
					n.setTitle(title);
					n.setShotDesc(shotDesc);
					n.setImageUrl(imageUrl);
					n.setFromWebSite(fromWebsite);
					n.setUrl(url);
					n.setHotNews(false);
					n.setType(NewsTypes.TYPE_CONGNGHE);
					n.setParentCateName(NewsTypes.TYPE_CONGNGHE);
					n.setNewsOrder(News.NEWS_ORDER.M.name());
					congngheNews.add(n);
				}
			}
			
		} catch (Exception e2) {
			e2.printStackTrace();
		}
		return congngheNews;
	}

}
