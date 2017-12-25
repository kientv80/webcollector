package com.xyz.hayhay.website.collector.worldwebsite;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.collector.ArticleCollector;
import com.xyz.hayhay.website.parser.AsiaNikkeiParser;
import com.xyz.hayhay.website.parser.FoxNewsParser;
import com.xyz.hayhay.website.parser.NYTimesParser;
import com.xyz.hayhay.website.parser.SouthChinaMorningPostParser;
import com.xyz.hayhay.website.parser.WashingtonPostsParser;

import net.htmlparser.jericho.Source;

public class PoliticsCollector extends ArticleCollector {
	String[] urls = new String[] { "https://asia.nikkei.com/Politics-Economy/Policy-Politics","http://www.foxnews.com/politics.html", "https://www.nytimes.com/pages/politics/index.html"
			,"http://www.scmp.com/news/china/policies-politics","https://www.washingtonpost.com/politics"};

	public PoliticsCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if ("foxnews.com".equals(fromWebsite)) {
			return new FoxNewsParser().collectArticle(source, url, fromWebsite);
		}else if ("nytimes.com".equals(fromWebsite)) {
			return new NYTimesParser().collectArticle(source, url, fromWebsite);
		}else if ("scmp.com".equals(fromWebsite)) {
			return new SouthChinaMorningPostParser().collectArticle(source, url, fromWebsite);
		}else if ("washingtonpost.com".equals(fromWebsite)) {
			return new WashingtonPostsParser().collectArticle(source, url, fromWebsite);
		} else if ("asia.nikkei.com".equals(fromWebsite)) {
			return new AsiaNikkeiParser().collectArticle(source, url, fromWebsite);
		}
		return null;
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
