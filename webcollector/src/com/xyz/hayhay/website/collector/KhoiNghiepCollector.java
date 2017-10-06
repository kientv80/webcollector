package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.CafeBizParser;
import com.xyz.hayhay.website.parser.ICTNewsParser;
import com.xyz.hayhay.website.parser.VnExpressParser;

import net.htmlparser.jericho.Source;

public class KhoiNghiepCollector extends ArticleCollector {
	String[] urls = new String[] { "http://kinhdoanh.vnexpress.net/tin-tuc/khoi-nghiep",
			"http://cafebiz.vn/startup.chn",
			"http://ictnews.vn/khoi-nghiep"};
	public KhoiNghiepCollector(long repeatTime) {
		super(repeatTime);
	}


	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if("cafebiz.vn".equals(fromWebsite)){
			return new CafeBizParser().collectArticle(source, url, fromWebsite);
		}else if("ictnews.vn".equals(fromWebsite)){
			return new ICTNewsParser().collectArticle(source, url, fromWebsite);
		} else{
			return new VnExpressParser().collectArticle(source, url, fromWebsite);
		}
		
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
