package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.PetrotimesParser;
import com.xyz.hayhay.website.parser.TuoiTreParser;

import net.htmlparser.jericho.Source;

public class KinhTeCollector extends ArticleCollector {
	String[] urls = new String[] { "http://petrotimes.vn/kinh-te", "http://tuoitre.vn/tin/kinh-te" };

	public KinhTeCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if("petrotimes.vn".equals(fromWebsite)){
			return new PetrotimesParser().collectArticle(source, url, fromWebsite);
		}else if("tuoitre.vn".equals(fromWebsite)){
			return new TuoiTreParser().collectArticle(source, url, fromWebsite);
		}
		return null;
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
