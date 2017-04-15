package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.VnExpressParser;

import net.htmlparser.jericho.Source;

public class TinTheGioiCollector extends ArticleCollector {
	String urls[] = new String[] { "http://vnexpress.net/tin-tuc/the-gioi"};
	public TinTheGioiCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		return new VnExpressParser().collectArticle(source, url, fromWebsite);
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
