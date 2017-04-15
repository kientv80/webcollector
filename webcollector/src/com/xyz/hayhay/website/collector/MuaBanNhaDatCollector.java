package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.MuaBanNhaDatParser;

import net.htmlparser.jericho.Source;

public class MuaBanNhaDatCollector extends ArticleCollector {
	String urls[] = new String[] { 
			"http://www.muabannhadat.vn/nha-dat-3490/tp-ho-chi-minh-s59",
			"http://www.muabannhadat.vn/nha-dat-3490/tp-ha-noi-s28",
			"http://www.muabannhadat.vn/nha-dat-3490/tp-da-nang-s31",
			"http://www.muabannhadat.vn/nha-dat-3490/ba-ria-vung-tau-s60" };

	public MuaBanNhaDatCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		return new MuaBanNhaDatParser().collectArticle(source, url, fromWebsite);
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}
	@Override
	protected boolean overwrite() {
		return true;
	}
}
