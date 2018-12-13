package com.xyz.hayhay.website.collector.worldwebsite;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.collector.ArticleCollector;
import com.xyz.hayhay.website.parser.FoxNewsParser;
import com.xyz.hayhay.website.parser.NYTimesParser;
import com.xyz.hayhay.website.parser.SouthChinaMorningPostParser;
import com.xyz.hayhay.website.parser.TheguardianParser;
import com.xyz.hayhay.website.parser.WashingtonPostsParser;

import net.htmlparser.jericho.Source;

public class OpinionsCollector extends ArticleCollector {
	String[] urls = new String[] { "https://www.washingtonpost.com/opinions","https://www.foxnews.com/opinion"};

	public OpinionsCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if ("washingtonpost.com".equals(fromWebsite)) {
			return new WashingtonPostsParser().collectArticle(source, url, fromWebsite);
		}else if ("theguardian.com".equals(fromWebsite)) {
			return new TheguardianParser().collectArticle(source, url, fromWebsite);
		}else if ("foxnews.com".equals(fromWebsite)) {
			return new FoxNewsParser().collectArticle(source, url, fromWebsite);
		}
		return null;
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
