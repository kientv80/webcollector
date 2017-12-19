package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.SucKhoeGiaDinhParser;

import net.htmlparser.jericho.Source;

public class SucKhoeNewsCollector extends ArticleCollector {

	public SucKhoeNewsCollector(long repeatTime) {
		super(repeatTime);
	}

	String[] urls = new String[] { "http://suckhoegiadinh.com.vn/dinh-duong" };

	

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		return new SucKhoeGiaDinhParser().collectArticle(source, url, fromWebsite);
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
