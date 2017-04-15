package com.xyz.hayhay.website.collector;

import java.util.ArrayList;
import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.entirty.NewsTypes;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class SucKhoeNewsCollector extends ArticleCollector {

	public SucKhoeNewsCollector(long repeatTime) {
		super(repeatTime);
	}

	private static final String SUCKHOE = "suckhoegiadinh.com.vn";
	String[] urls = new String[] { "http://suckhoegiadinh.com.vn/dinh-duong" };

	public List<News> processWebsiteContent(Source source) {
		List<News> suckhoe = new ArrayList<>();
		Element hotNews = source.getAllElementsByClass("ul_list_mid").get(0);
		String title = "";
		String shotDesc = "";
		String url = "";
		String imageUrl = "";
		hotNews = source.getAllElementsByClass("ul_list_mid").get(0);
		suckhoe = new ArrayList<>();
		for (Element hotNew : hotNews.getChildElements()) {
			Element hn = hotNew.getChildElements().get(2);
			url = "http://suckhoegiadinh.com.vn/" + hn.getChildElements().get(0).getAttributeValue("href");
			title = hn.getChildElements().get(0).getAttributeValue("title");
			imageUrl = hn.getChildElements().get(0).getChildElements().get(0).getAttributeValue("src");
			shotDesc = hotNew.getChildElements().get(2).getChildElements().get(1).getTextExtractor().toString();

			if (!title.isEmpty() && !shotDesc.isEmpty() && !url.isEmpty() && !imageUrl.isEmpty()) {
				News n = new News();
				n.setTitle(title);
				n.setShotDesc(shotDesc);
				n.setImageUrl(imageUrl);
				n.setFromWebSite(SUCKHOE);
				n.setUrl(url);
				n.setType(NewsTypes.DINHDUONG);
				n.setParentCateName(NewsTypes.TYPE_SUCKHOE);
				if (!suckhoe.contains(n))
					suckhoe.add(n);
			}
		}
		return suckhoe;
	}


	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		return processWebsiteContent(source);
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
