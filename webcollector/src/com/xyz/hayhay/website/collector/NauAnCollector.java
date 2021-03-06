package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.AFamilyParser;
import com.xyz.hayhay.website.parser.MonNgonMoiNgayParser;

import net.htmlparser.jericho.Source;

public class NauAnCollector extends ArticleCollector {
	public NauAnCollector(long repeatTime) {
		super(repeatTime);
	}

	public static Boolean firstRun = false;
	String[] urls = new String[] { "http://monngonmoingay.com/mon-ngon-tu-thit-heo/",
			"http://monngonmoingay.com/mon-ngon-tu-thit-ga/", "http://monngonmoingay.com/mon-ngon-tu-thit-bo/",
			"http://monngonmoingay.com/mon-ngon-tu-ca/", "http://monngonmoingay.com/mon-ngon-tu-tom/",
			"http://monngonmoingay.com/mon-ngon-tu-muc/", "http://monngonmoingay.com/hai-san-khac/",
			"http://monngonmoingay.com/mon-ngon-tu-nam/", "http://monngonmoingay.com/mon-ngon-tu-dau-hu/",
			
			"http://afamily.vn/an-ngon/mon-an-tu-thit-heo.htm","http://afamily.vn/an-ngon/mon-an-tu-thit-ga.htm",
			"http://afamily.vn/an-ngon/mon-an-tu-rau-cu.htm","http://afamily.vn/an-ngon/mon-an-tu-tom.htm",
			"http://afamily.vn/an-ngon/mon-an-tu-trung.htm","http://afamily.vn/an-ngon/mon-an-y.htm",
			"http://afamily.vn/an-ngon/mon-an-han-quoc.htm","http://afamily.vn/an-ngon/mon-an-nhat-ban.htm",
			"http://afamily.vn/an-ngon/mon-an-thai-lan.htm","http://afamily.vn/an-ngon/mon-an-phap.htm"
			};

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if("afamily.vn".equals(fromWebsite)){
			return new AFamilyParser().collectArticle(source, url, fromWebsite);
		}else{
			return new MonNgonMoiNgayParser().collectArticle(source, url, fromWebsite);
		}
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}
}
