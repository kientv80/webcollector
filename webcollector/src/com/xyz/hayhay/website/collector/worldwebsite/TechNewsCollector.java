package com.xyz.hayhay.website.collector.worldwebsite;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.collector.ArticleCollector;
import com.xyz.hayhay.website.parser.FoxNewsParser;
import com.xyz.hayhay.website.parser.NYTimesParser;

import net.htmlparser.jericho.Source;

public class TechNewsCollector extends ArticleCollector {
	String[] urls = new String[] { "http://www.foxnews.com/tech.html", "https://www.nytimes.com/section/technology?src=busfn",
			"http://money.cnn.com/technology/?iid=Tech_Nav" };

	public TechNewsCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if ("foxbusiness.com".equals(fromWebsite) || "foxnews.com".equals(fromWebsite)) {
			return new FoxNewsParser().collectArticle(source, url, fromWebsite);
		}else if ("nytimes.com".equals(fromWebsite)){
			new NYTimesParser().collectArticle(source, url, fromWebsite);
		}
		return null;
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
