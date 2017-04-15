package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.NgoiSaoNetParser;
import com.xyz.hayhay.website.parser.Web24HParser;

import net.htmlparser.jericho.Source;

public class NgoiSaoCollector extends ArticleCollector {

	public NgoiSaoCollector(long repeatTime) {
		super(repeatTime);
	}

	String[] urls = new String[] { "http://ngoisao.net/tin-tuc/hau-truong/showbiz-viet",
			"http://ngoisao.net/tin-tuc/hau-truong/chau-a", "http://ngoisao.net/tin-tuc/hau-truong/hollywood",
			"http://ngoisao.net/tin-tuc/phong-cach", "http://ngoisao.net/tin-tuc/ben-le",
			"http://www.24h.com.vn/doi-song-showbiz-c729.html", "http://www.24h.com.vn/thoi-trang-c78.html" };

	private List<News> collectFromNgoiSaoNet(Source s, String url, String fromWeb) {
		return new NgoiSaoNetParser().collectArticle(s, url, fromWeb);
	}

	private List<News> collectFrom24h(Source s, String url, String fromWebsite) {
		return new Web24HParser().collectArticle(s, url, fromWebsite);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if ("ngoisao.net".equals(fromWebsite)) {
			return collectFromNgoiSaoNet(source, url, fromWebsite);
		} else {
			return collectFrom24h(source, url, fromWebsite);
		}
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
