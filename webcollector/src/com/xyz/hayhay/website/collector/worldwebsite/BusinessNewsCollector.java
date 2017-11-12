package com.xyz.hayhay.website.collector.worldwebsite;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.collector.ArticleCollector;
import com.xyz.hayhay.website.parser.ChinaDailyParser;
import com.xyz.hayhay.website.parser.FoxNewsParser;
import com.xyz.hayhay.website.parser.NYTimesParser;
import com.xyz.hayhay.website.parser.SouthChinaMorningPostParser;

import net.htmlparser.jericho.Source;

public class BusinessNewsCollector extends ArticleCollector {
	String[] urls = new String[] { "http://www.foxbusiness.com/markets.html", "http://www.nytimes.com/pages/business/index.html",
			"http://money.cnn.com/news/","http://www.chinadaily.com.cn/business/","http://www.scmp.com/news/china/economy","http://www.scmp.com/business" };

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
		}

		return null;
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
