package com.xyz.hayhay.website.collector.worldwebsite;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.collector.ArticleCollector;
import com.xyz.hayhay.website.parser.ChinaDailyParser;
import com.xyz.hayhay.website.parser.FoxNewsParser;
import com.xyz.hayhay.website.parser.NYTimesParser;
import com.xyz.hayhay.website.parser.SouthChinaMorningPostParser;

import net.htmlparser.jericho.Source;

public class HotNewsCollector extends ArticleCollector {
	String[] urls = new String[] { "http://www.chinadaily.com.cn/china/", "http://www.scmp.com/news/china/society" };

	public HotNewsCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if ("chinadaily.com.cn".equals(fromWebsite)) {
			return new ChinaDailyParser().collectArticle(source, url, fromWebsite);
		} else if ("scmp.com".equals(fromWebsite)) {
			return new SouthChinaMorningPostParser().collectArticle(source, url, fromWebsite);
		}
		return null;
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
