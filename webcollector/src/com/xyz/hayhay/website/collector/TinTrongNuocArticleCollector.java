package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.ThanhNienParser;
import com.xyz.hayhay.website.parser.TienPhongParser;
import com.xyz.hayhay.website.parser.TuoiTreParser;
import com.xyz.hayhay.website.parser.VnExpressParser;

import net.htmlparser.jericho.Source;

public class TinTrongNuocArticleCollector extends ArticleCollector {

	String urls[] = new String[] {
			"https://tuoitre.vn/thoi-su.htm",
			"https://tuoitre.vn/thoi-su/xa-hoi.htm",
			"https://tuoitre.vn/thoi-su/phong-su.htm",
			"https://tuoitre.vn/phap-luat.htm",
			"https://tuoitre.vn/nhip-song-tre.htm",
			"https://tuoitre.vn/van-hoa.htm",
			"https://vnexpress.net/tin-tuc/thoi-su",
			"https://vnexpress.net/tin-tuc/phap-luat",
			"https://vnexpress.net/tin-tuc/giao-duc",
			"https://thanhnien.vn/viet-nam/",
			"https://thanhnien.vn/the-gioi/",
			"https://www.tienphong.vn/xa-hoi/",
			"https://www.tienphong.vn/the-gioi/","https://www.tienphong.vn/phap-luat/","https://www.tienphong.vn/giao-duc/"};

	public TinTrongNuocArticleCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source s, String url, String fromWebsite) {
		if ("vnexpress.net".equals(fromWebsite)) {
			return new VnExpressParser().collectArticle(s, url, fromWebsite);
		} else if ("thanhnien.vn".equals(fromWebsite)) {
			return new ThanhNienParser().collectArticle(s, url, fromWebsite);
		} else if ("tienphong.vn".equals(fromWebsite)) {
			return new TienPhongParser().collectArticle(s, url, fromWebsite);
		} else if ("tuoitre.vn".equals(fromWebsite)) {
			return new TuoiTreParser().collectArticle(s, url, fromWebsite);
		}
		return null;
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}
}
