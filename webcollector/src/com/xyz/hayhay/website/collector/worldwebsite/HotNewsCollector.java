package com.xyz.hayhay.website.collector.worldwebsite;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.collector.ArticleCollector;
import com.xyz.hayhay.website.parser.AsiaNikkeiParser;
import com.xyz.hayhay.website.parser.ChinaDailyParser;
import com.xyz.hayhay.website.parser.EpochtimesParser;
import com.xyz.hayhay.website.parser.SouthChinaMorningPostParser;
import com.xyz.hayhay.website.parser.TheguardianParser;
import com.xyz.hayhay.website.parser.WashingtonPostsParser;

import net.htmlparser.jericho.Source;

public class HotNewsCollector extends ArticleCollector {
	String[] urls = new String[] {
			"https://www.theguardian.com/international",
			"https://www.theguardian.com/uk-news",
			"https://www.theguardian.com/cities",
			"http://www.epochtimes.com/gb/n24hr.htm",
			"http://www.epochtimes.com/gb/ncyule.htm", 
			"http://www.chinadaily.com.cn/china/", 
			"http://www.scmp.com/news/china/society",
			"https://www.washingtonpost.com/",
			"https://asia.nikkei.com/"
			};

	public HotNewsCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if ("chinadaily.com.cn".equals(fromWebsite)) {
			return new ChinaDailyParser().collectArticle(source, url, fromWebsite);
		} else if ("scmp.com".equals(fromWebsite)) {
			return new SouthChinaMorningPostParser().collectArticle(source, url, fromWebsite);
		} else if ("washingtonpost.com".equals(fromWebsite)) {
			return new WashingtonPostsParser().collectArticle(source, url, fromWebsite);
		}else if ("epochtimes.com".equals(fromWebsite)) {
			return new EpochtimesParser().collectArticle(source, url, fromWebsite);
		}else if ("asia.nikkei.com".equals(fromWebsite)) {
			return new AsiaNikkeiParser().collectArticle(source, url, fromWebsite);
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
