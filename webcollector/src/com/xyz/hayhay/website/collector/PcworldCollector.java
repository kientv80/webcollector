package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.PCWorldParser;

import net.htmlparser.jericho.Source;

public class PcworldCollector extends ArticleCollector{
	String[] urls = new String[]{"http://www.pcworld.com.vn/articles/cong-nghe"};
	public PcworldCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		return new PCWorldParser().collectArticle(source, url, fromWebsite);
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
