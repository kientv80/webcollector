package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.BizLiveParser;
import com.xyz.hayhay.website.parser.CafeBizParser;
import com.xyz.hayhay.website.parser.VnEconomyParser;

import net.htmlparser.jericho.Source;

public class TaiChinhNewsCollector extends ArticleCollector {
	public TaiChinhNewsCollector(long repeatTime) {
		super(repeatTime);
	}

	String[] urls = new String[] { 
			"http://vneconomy.vn/tai-chinh.htm",
			"http://vneconomy.vn/tai-chinh/ngan-hang.htm?mobile=true",
			"http://vneconomy.vn/tai-chinh/thue-ngan-sach.htm?mobile=true",
			"http://vneconomy.vn/tai-chinh/lai-suat.htm?mobile=true",
			"http://vneconomy.vn/tai-chinh/ty-gia.htm?mobile=true", 
			"http://vneconomy.vn/chung-khoan.htm?mobile=true",
			"http://vneconomy.vn/bat-dong-san.htm?mobile=true",
			"http://bizlive.vn/vang-tien/",
			"http://bizlive.vn/chung-khoan/","http://cafebiz.vn/tai-chinh.chn","http://cafebiz.vn/chinh-sach.chn" };
	private static final String ECONOMY_VN = "vneconomy.vn";

	private List<News> collectFromVnEconomic(Source s, String url,String website) {
		return new VnEconomyParser().collectArticle(s, url, website);
	}

	

	private List<News> collectFromBizLive(Source s, String url,String web) {
		return new BizLiveParser().collectArticle(s, url, web);
	}


	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if(ECONOMY_VN.equals(fromWebsite)){
			return collectFromVnEconomic(source, url,fromWebsite);
		}else if("cafebiz.vn".equals(fromWebsite)){
			return new CafeBizParser().collectArticle(source, url, fromWebsite);
		}else{
			return collectFromBizLive(source, url,fromWebsite);
		}
		
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
