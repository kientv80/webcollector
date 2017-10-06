package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.VnExpressParser;
import com.xyz.hayhay.website.parser.ZingParser;

import net.htmlparser.jericho.Source;

public class XeCollector extends ArticleCollector {
	public XeCollector(long repeatTime) {
		super(repeatTime);
	}
	String urls[] = new String[] { "https://vnexpress.net/tin-tuc/oto-xe-may/tu-van",
			"https://vnexpress.net/tin-tuc/oto-xe-may/dien-dan", "https://vnexpress.net/tin-tuc/oto-xe-may/thi-truong",
			"https://news.zing.vn/oto-xe-may/xe-may.html", "https://news.zing.vn/oto-xe-may/o-to.html",
			"https://news.zing.vn/oto-xe-may/xe-do.html", "https://news.zing.vn/oto-xe-may/sieu-xe.html" };

	private List<News> collectFromVNExpress(Source s, String url,String fromWebsite) {
		return new VnExpressParser().collectArticle(s, url, fromWebsite);
	}

	private List<News> collectFromZingNews(Source s, String url, String fromWebsite) {
		return new ZingParser().collectArticle(s, url, fromWebsite);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if ("news.zing.vn".equals(fromWebsite)) {
			return collectFromZingNews(source, url,fromWebsite);
		} else {
			return collectFromVNExpress(source, url, fromWebsite);
		}
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
