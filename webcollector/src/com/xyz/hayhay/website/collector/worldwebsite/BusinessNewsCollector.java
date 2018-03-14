package com.xyz.hayhay.website.collector.worldwebsite;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.collector.ArticleCollector;
import com.xyz.hayhay.website.parser.ChinaDailyParser;
import com.xyz.hayhay.website.parser.FoxNewsParser;
import com.xyz.hayhay.website.parser.NYTimesParser;
import com.xyz.hayhay.website.parser.SouthChinaMorningPostParser;
import com.xyz.hayhay.website.parser.TheguardianParser;
import com.xyz.hayhay.website.parser.WashingtonPostsParser;

import net.htmlparser.jericho.Source;

public class BusinessNewsCollector extends ArticleCollector {
	String[] urls = new String[] {"https://www.theguardian.com/uk/business","https://www.theguardian.com/business/economics",
			"http://www.foxbusiness.com/markets.html", "http://www.nytimes.com/pages/business/index.html",
			"http://money.cnn.com/news/","http://www.chinadaily.com.cn/business/","http://www.scmp.com/news/china/economy",
			"http://www.scmp.com/business","https://www.washingtonpost.com/business" };

	public BusinessNewsCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if ("foxbusiness.com".equals(fromWebsite)) {
			return new FoxNewsParser().collectArticle(source, url, fromWebsite);
		}else if ("nytimes.com".equals(fromWebsite)) {
			return new NYTimesParser().collectArticle(source, url, fromWebsite);
		}else if ("chinadaily.com.cn".equals(fromWebsite)) {
			return new ChinaDailyParser().collectArticle(source, url, fromWebsite);
		}else if ("scmp.com".equals(fromWebsite)) {
			return new SouthChinaMorningPostParser().collectArticle(source, url, fromWebsite);
		}else if ("washingtonpost.com".equals(fromWebsite)) {
			return new WashingtonPostsParser().collectArticle(source, url, fromWebsite);
		}else if ("theguardian.com".equals(fromWebsite)) {
			return new TheguardianParser().collectArticle(source, url, fromWebsite);
		}

		return null;
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
