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

public class Web24HParser extends BaseParser{

	@Override
	public List<News> collectArticle(Source s, String url, String fromWebsite) {
		ShotDescription p = new ShotDescription("span", "class", "news-sapo", true);
		Title title = new Title("a", null, null, false);
		title.setValueFromAtttributeName("title");
		Image image = new Image("img", "width", "110", false);
		image.setValueFromAtttributeName("src");
		A a = new A();
		a.setDomain("http://www.24h.com.vn/");
		String type = "";
		type = NewsTypes.TYPE.FamousPerson.name();
		String parentType = NewsTypes.CATEGORY.Entertainment.name();
		
		if (url.indexOf("doi-song-showbiz-c729.html") > 0) {
			type = NewsTypes.TYPE.FamousPerson.name();
		} else if (url.contains("thoi-trang-c78.html"))
			type = NewsTypes.TYPE.Style.name();
		else if (url.contains("bong-da-c48.html") || url.contains("su-kien-binh-luan-c447.html")){
			type = NewsTypes.TYPE.Football.name();
			parentType = NewsTypes.CATEGORY.Sport.name();
		} else if (url.contains("anh-bong-da-nguoi-dep-c507.html")){
			type = NewsTypes.TYPE.BehindTheScenes.name();
			parentType = NewsTypes.CATEGORY.Entertainment.name();
		}
		
		List<News> ngoisao = new ArrayList<>();
		List<Element> newsItems = s.getAllElementsByClass("boxDoi-sub-Item-trangtrong");
		newsItems.addAll(s.getAllElementsByClass("boxDoi-sub-Item-trangtrong marL10"));
		if (newsItems != null && newsItems.size() > 0) {
			for (Element item : newsItems) {
				News n = new News();
				n.setType(type);
				n.setParentCateName(parentType);
				n.setFromWebSite(fromWebsite);
				parseElementToNews(item, n, a, title, image, p);
				if (n.getTitle() != null && n.getUrl() != null && n.getImageUrl() != null) {
					if (!ngoisao.contains(n)) {
						ngoisao.add(n);
					}
				}
			}
		}
		return ngoisao;
	}

}
