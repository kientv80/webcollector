package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.CafeBizParser;

import net.htmlparser.jericho.Source;

public class KinhDoanhCollector extends ArticleCollector{
	
	public KinhDoanhCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if("cafebiz.vn".equals(fromWebsite)){
			return new CafeBizParser().collectArticle(source, url, fromWebsite);
		}
		return null;
	}

	@Override
	public String[] collectedUrls() {
		return "http://cafebiz.vn/nhan-vat.chn,http://cafebiz.vn/quan-tri.chn,http://cafebiz.vn/nghe-nghiep.chn,http://cafebiz.vn/thuong-hieu.chn,http://cafebiz.vn/welearn.chn,http://cafebiz.vn/doanh-nghiep-cong-nghe.chn,https://kinhdoanh.vnexpress.net/".split(",");
	}

}
