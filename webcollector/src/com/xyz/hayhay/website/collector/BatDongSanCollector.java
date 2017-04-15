package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.BaoXanDungParser;
import com.xyz.hayhay.website.parser.VnExpressParser;

import net.htmlparser.jericho.Source;

public class BatDongSanCollector extends ArticleCollector {
	String urls[] = new String[] { "http://www.baoxaydung.com.vn/news/vn/quy-hoach-kien-truc/nha-dep",
			"http://www.baoxaydung.com.vn/news/vn/quy-hoach-kien-truc/quy-hoach",
			"http://www.baoxaydung.com.vn/news/vn/vat-lieu",
			"http://www.baoxaydung.com.vn/news/vn/quy-hoach-kien-truc/kien-truc",
			"http://www.baoxaydung.com.vn/news/vn/bat-dong-san",
			"http://kinhdoanh.vnexpress.net/tin-tuc/bat-dong-san" };

	public BatDongSanCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if ("kinhdoanh.vnexpress.net".equals(fromWebsite)) {
			return new VnExpressParser().collectArticle(source, url, fromWebsite);
		} else {
			return new BaoXanDungParser().collectArticle(source, url, fromWebsite);
		}
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}
}
