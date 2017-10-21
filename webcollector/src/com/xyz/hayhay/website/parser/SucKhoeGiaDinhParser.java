package com.xyz.hayhay.website.parser;

import java.util.ArrayList;
import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.entirty.NewsTypes;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class SucKhoeGiaDinhParser  extends BaseParser{

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		List<News> suckhoe = new ArrayList<>();
		Element hotNews = source.getAllElementsByClass("ul_list_mid").get(0);
		String title = "";
		String shotDesc = "";
		String imageUrl = "";
		int count = 0;
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
				n.setFromWebSite(fromWebsite);
				n.setUrl(url);
				n.setParentCateName(NewsTypes.CATEGORY.Health.name());
				if (count == 0)
					n.setHotNews(true);
				n.setType(NewsTypes.TYPE.Health.name());
				if (!suckhoe.contains(n))
					suckhoe.add(n);

			}

		}
		return suckhoe;
	}

}
