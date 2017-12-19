package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.AFamilyParser;
import com.xyz.hayhay.website.parser.CafeBizParser;
import com.xyz.hayhay.website.parser.SucKhoeDoiSongParser;
import com.xyz.hayhay.website.parser.SucKhoeGiaDinhParser;

import net.htmlparser.jericho.Source;

public class SucKhoeDoiSongNewsCollector extends ArticleCollector {
	public SucKhoeDoiSongNewsCollector(long repeatTime) {
		super(repeatTime);
	}

	String[] urls = new String[] { 
			"http://suckhoedoisong.vn/y-hoc-co-truyen-c9/",
			"http://suckhoedoisong.vn/bai-thuoc-dan-gian-c45/", "http://suckhoedoisong.vn/cay-thuoc-quanh-ta-c46/",
			"http://suckhoedoisong.vn/am-thuc-c44/", "http://suckhoedoisong.vn/dinh-duong-c38/"
			,"http://cafebiz.vn/suc-khoe.chn","http://afamily.vn/suc-khoe.chn" 
			};

	@Override
	public List<News> collectArticle(Source s, String url, String fromWebsite) {
		if ("cafebiz.vn".equals(fromWebsite)) 
			return new CafeBizParser().collectArticle(s, url, fromWebsite);
		else if ("afamily.vn".equals(fromWebsite)) 
			return new AFamilyParser().collectArticle(s, url, fromWebsite);
		else {
			return new SucKhoeDoiSongParser().collectArticle(s, url, fromWebsite);
		}
	}


	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
