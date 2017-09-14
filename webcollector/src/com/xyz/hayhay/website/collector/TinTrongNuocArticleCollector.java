package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.TuoiTreParser;
import com.xyz.hayhay.website.parser.VnExpressParser;

import net.htmlparser.jericho.Source;

public class TinTrongNuocArticleCollector extends ArticleCollector {

	String urls[] = new String[] { "http://tuoitre.vn/tin/chinh-tri-xa-hoi",
			"http://tuoitre.vn/tin/chinh-tri-xa-hoi/thoi-su-suy-nghi",
			"http://tuoitre.vn/tin/chinh-tri-xa-hoi/phong-su-ky-su",
			"http://tuoitre.vn/tin/chinh-tri-xa-hoi/moi-truong",
			"http://tuoitre.vn/tin/chinh-tri-xa-hoi/chuyen-thuong-ngay",
			"http://tuoitre.vn/tin/chinh-tri-xa-hoi/tieu-diem", "http://tuoitre.vn/tin/phap-luat",
			"http://tuoitre.vn/tin/nhip-song-tre", "http://vnexpress.net/tin-tuc/thoi-su"};

	public TinTrongNuocArticleCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source s, String url, String fromWebsite) {
		if ("vnexpress.net".equals(fromWebsite)) {
			return new VnExpressParser().collectArticle(s, url, fromWebsite);
		} else {
			return new TuoiTreParser().collectArticle(s, url, fromWebsite);
		}

	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}
}
