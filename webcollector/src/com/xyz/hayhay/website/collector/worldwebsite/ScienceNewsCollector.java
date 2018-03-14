package com.xyz.hayhay.website.collector.worldwebsite;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.collector.ArticleCollector;
import com.xyz.hayhay.website.parser.AsiaNikkeiParser;
import com.xyz.hayhay.website.parser.FoxNewsParser;
import com.xyz.hayhay.website.parser.NYTimesParser;
import com.xyz.hayhay.website.parser.ScienceNewsParser;
import com.xyz.hayhay.website.parser.TheguardianParser;
import com.xyz.hayhay.website.parser.TienPhongParser;
import com.xyz.hayhay.website.parser.VnExpressParser;

import net.htmlparser.jericho.Source;

public class ScienceNewsCollector extends ArticleCollector {
	String[] urls = new String[] {"https://www.theguardian.com/science",
			"https://asia.nikkei.com/Tech-Science/Science/", "http://www.foxnews.com/science.html", "https://www.nytimes.com/section/science"
			,"https://www.sciencenews.org/","https://vnexpress.net/tin-tuc/khoa-hoc","https://www.tienphong.vn/cong-nghe/"};

	public ScienceNewsCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if ("foxnews.com".equals(fromWebsite)) {
			return new FoxNewsParser().collectArticle(source, url, fromWebsite);
		}else if ("sciencenews.org".equals(fromWebsite)) {
			return new ScienceNewsParser().collectArticle(source, url, fromWebsite);
		}else if ("nytimes.com".equals(fromWebsite)) {
			return new NYTimesParser().collectArticle(source, url, fromWebsite);
		}else if ("asia.nikkei.com".equals(fromWebsite)) {
			return new AsiaNikkeiParser().collectArticle(source, url, fromWebsite);
		}else if ("vnexpress.net".equals(fromWebsite)) {
			return new VnExpressParser().collectArticle(source, url, fromWebsite);
		}else if ("tienphong.vn".equals(fromWebsite)) {
			return new TienPhongParser().collectArticle(source, url, fromWebsite);
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
