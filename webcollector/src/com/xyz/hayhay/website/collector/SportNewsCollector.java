package com.xyz.hayhay.website.collector;

import java.util.ArrayList;
import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.entirty.NewsTypes;
import com.xyz.hayhay.entirty.webcollection.A;
import com.xyz.hayhay.entirty.webcollection.Image;
import com.xyz.hayhay.entirty.webcollection.ShotDescription;
import com.xyz.hayhay.entirty.webcollection.Title;
import com.xyz.hayhay.website.parser.TheThaoTVParser;
import com.xyz.hayhay.website.parser.Thethao247Parser;
import com.xyz.hayhay.website.parser.TienPhongParser;
import com.xyz.hayhay.website.parser.VnExpressParser;
import com.xyz.hayhay.website.parser.Web24HParser;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class SportNewsCollector extends ArticleCollector {

	public SportNewsCollector(long repeatTime) {
		super(repeatTime);
	}

	String[] urls = new String[] { 
			"https://thethao.vnexpress.net/",
			"https://www.tienphong.vn/the-thao/" };

	private List<News> collectFrom24h(Source s, String url, String fromWebsite) {
		if(url.contains("thethaotv.vn")){
			return new TheThaoTVParser().collectArticle(s, url, fromWebsite);
		}else if(url.contains("tienphong.vn")){
			return new TienPhongParser().collectArticle(s, url, fromWebsite);
		}else if(url.contains("thethao.vnexpress.net")){
			return new VnExpressParser().collectArticle(s, url, fromWebsite);
		} else{
			return new Web24HParser().collectArticle(s, url, fromWebsite);
		}
		
	}

	private List<News> collectFromBongda24h(Source s, String url, String fromWeb) {
		ShotDescription p = new ShotDescription("span", "class", "tin_moi_3_left_img", true);
		Title title = new Title("a", "class", "tin_moi_2_left_tieude mnh4", true);
		Image image = new Image("img", "width", "150", false);
		image.setValueFromAtttributeName("src");
		A a = new A();
		String type = NewsTypes.BONGDA;
		List<News> sportNews = new ArrayList<>();
		List<Element> newsItems = s.getAllElementsByClass("tin_moi_2_left");
		if (newsItems != null && newsItems.size() > 0) {
			for (Element item : newsItems) {
				News n = new News();
				n.setType(type);
				n.setParentCateName(NewsTypes.TYPE_SPORTNEWS);
				n.setFromWebSite(fromWeb);
				parseElementToNews(item, n, a, title, image, p);
				if (n.getTitle() != null && n.getUrl() != null && n.getImageUrl() != null) {
					if (!sportNews.contains(n)) {
						sportNews.add(n);
					}
				}
			}
		}
		return sportNews;
	}

	private List<News> collectFromThethao247(Source s, String url, String fromWebsite) {
		return new Thethao247Parser().collectArticle(s, url, fromWebsite);
	}

	private List<News> collectFromThethaoTV(Source s, String url, String fromWebsite) {
		return new TheThaoTVParser().collectArticle(s, url, fromWebsite);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if ("24h.com.vn".equals(fromWebsite)) {
			return collectFrom24h(source, url, fromWebsite);
		} else if ("bongda24h.vn".equals(fromWebsite)) {
			return collectFromBongda24h(source, url, fromWebsite);
		} else if ("thethao247.vn".equals(fromWebsite)) {
			return collectFromThethao247(source, url, fromWebsite);
		} else if ("thethaotv.vn".equals(fromWebsite)) {
			return collectFromThethaoTV(source, url, fromWebsite);
		}
		return null;
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
