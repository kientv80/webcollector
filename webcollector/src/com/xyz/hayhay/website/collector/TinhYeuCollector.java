package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.AFamilyParser;
import com.xyz.hayhay.website.parser.DanTriParser;

import net.htmlparser.jericho.Source;

public class TinhYeuCollector extends ArticleCollector {
	String[] urls = new String[] { "http://dantri.com.vn/tinh-yeu-gioi-tinh.htm",
			"http://dantri.com.vn/tinh-yeu-gioi-tinh/gia-dinh.htm",
			"http://dantri.com.vn/tinh-yeu-gioi-tinh/goc-tam-hon.htm",
			"http://dantri.com.vn/tinh-yeu-gioi-tinh/tinh-yeu.htm","http://afamily.vn/tinh-yeu-hon-nhan.chn","http://afamily.vn/me-va-be.chn" };

	public TinhYeuCollector(long repeatTime) {
		super(repeatTime);
	}


	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if("afamily.vn".equals(fromWebsite)){
			return new AFamilyParser().collectArticle(source, url, fromWebsite);
		}else{
			return new DanTriParser().collectArticle(source, url, fromWebsite);
		}
		
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
