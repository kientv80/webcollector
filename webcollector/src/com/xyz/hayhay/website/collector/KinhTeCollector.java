package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.AsiaNikkeiParser;
import com.xyz.hayhay.website.parser.PetrotimesParser;
import com.xyz.hayhay.website.parser.TienPhongParser;
import com.xyz.hayhay.website.parser.TuoiTreParser;

import net.htmlparser.jericho.Source;

public class KinhTeCollector extends ArticleCollector {
	String[] urls = new String[] {"https://asia.nikkei.com/Politics-Economy/Economy/",
			"https://asia.nikkei.com/Business/Consumers/", 
			"http://petrotimes.vn/kinh-te", 
			"https://tuoitre.vn/kinh-doanh.htm" ,
			"https://www.tienphong.vn/kinh-te/"};

	public KinhTeCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if("petrotimes.vn".equals(fromWebsite)){
			return new PetrotimesParser().collectArticle(source, url, fromWebsite);
		}else if("tuoitre.vn".equals(fromWebsite)){
			return new TuoiTreParser().collectArticle(source, url, fromWebsite);
		}else if("asia.nikkei.com".equals(fromWebsite)){
			return new AsiaNikkeiParser().collectArticle(source, url, fromWebsite);
		}else if("tienphong.vn".equals(fromWebsite)){
			return new TienPhongParser().collectArticle(source, url, fromWebsite);
		}
		return null;
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
