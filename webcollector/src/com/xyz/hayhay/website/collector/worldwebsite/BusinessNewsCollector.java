package com.xyz.hayhay.website.collector.worldwebsite;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.collector.ArticleCollector;
import com.xyz.hayhay.website.parser.FoxNewsParser;
import com.xyz.hayhay.website.parser.NYTimesParser;

import net.htmlparser.jericho.Source;

public class BusinessNewsCollector extends ArticleCollector {
	String[] urls = new String[] { "http://www.foxbusiness.com/", "http://www.nytimes.com/pages/business/index.html",
			"http://money.cnn.com/news/" };

	public BusinessNewsCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if ("foxbusiness.com".equals(fromWebsite)) {
			return new FoxNewsParser().collectArticle(source, url, fromWebsite);
		}else if ("nytimes.com".equals(fromWebsite)) {
			return new NYTimesParser().collectArticle(source, url, fromWebsite);
		}

		return null;
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
