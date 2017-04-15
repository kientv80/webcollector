package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.ICTNewsParser;

import net.htmlparser.jericho.Source;

public class ICTNewsCollector extends ArticleCollector {

	public ICTNewsCollector(long repeatTime) {
		super(repeatTime);
	}

	String[] urls = new String[]{"http://m.ictnews.vn/cntt"
			,
			"http://m.ictnews.vn/vien-thong",
			"http://m.ictnews.vn/internet",
			"http://m.ictnews.vn/the-gioi-so",
			"http://m.ictnews.vn/cong-nghe-360"
			};
	
	


	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		return new ICTNewsParser().collectArticle(source, url, fromWebsite);
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
