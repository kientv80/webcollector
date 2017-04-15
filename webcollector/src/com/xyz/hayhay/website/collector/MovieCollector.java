package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.GalaxyCineParser;

import net.htmlparser.jericho.Source;

public class MovieCollector extends ArticleCollector {
	String urls[] = new String[] { "https://www.galaxycine.vn/phim-dang-chieu", "https://www.galaxycine.vn/phim-sap-chieu", "https://www.galaxycine.vn/khuyen-mai" };
	
	public MovieCollector(long repeatTime) {
		super(repeatTime);
	}
	@Override
	protected boolean overwrite() {
		return true;
	}
	



	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		return new GalaxyCineParser().collectArticle(source, url, fromWebsite);
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}
}
