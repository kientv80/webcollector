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

public class TechNewsCollector extends ArticleCollector {
	String[] urls = new String[] { "https://asia.nikkei.com/Tech-Science/Tech/",
									"http://www.foxnews.com/tech.html", 
									"https://www.nytimes.com/section/technology?src=busfn",
									"http://www.scmp.com/tech",
									"https://www.washingtonpost.com/business/technology"};

	public TechNewsCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if ("foxbusiness.com".equals(fromWebsite) || "foxnews.com".equals(fromWebsite)) {
			return new FoxNewsParser().collectArticle(source, url, fromWebsite);
		}else if ("nytimes.com".equals(fromWebsite)){
			return new NYTimesParser().collectArticle(source, url, fromWebsite);
		}else if ("scmp.com".equals(fromWebsite)){
			return new SouthChinaMorningPostParser().collectArticle(source, url, fromWebsite);
		}else if ("washingtonpost.com".equals(fromWebsite)){
			return new WashingtonPostsParser().collectArticle(source, url, fromWebsite);
		}else if ("asia.nikkei.com".equals(fromWebsite)){
			return new AsiaNikkeiParser().collectArticle(source, url, fromWebsite);
		}
		return null;
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
