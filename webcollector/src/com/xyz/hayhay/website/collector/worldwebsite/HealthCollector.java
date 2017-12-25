package com.xyz.hayhay.website.collector.worldwebsite;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.collector.ArticleCollector;
import com.xyz.hayhay.website.parser.EpochtimesParser;
import com.xyz.hayhay.website.parser.FoxNewsParser;
import com.xyz.hayhay.website.parser.NYTimesParser;
import net.htmlparser.jericho.Source;

public class HealthCollector extends ArticleCollector {
	String[] urls = new String[] {"http://www.epochtimes.com/gb/ncid246.htm"
			, "http://www.foxnews.com/health.html"
			, "https://www.nytimes.com/section/health"
			};

	public HealthCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if ("foxnews.com".equals(fromWebsite)) {
			return new FoxNewsParser().collectArticle(source, url, fromWebsite);
		}else if ("nytimes.com".equals(fromWebsite)) {
			return new NYTimesParser().collectArticle(source, url, fromWebsite);
		}else if ("epochtimes.com".equals(fromWebsite)) {
			return new EpochtimesParser().collectArticle(source, url, fromWebsite);
		}
		return null;
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
