package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.NhacCuaTuiParser;
import com.xyz.hayhay.website.parser.NhacVnParser;

import net.htmlparser.jericho.Source;

public class MusicCollector extends ArticleCollector {
	String[] urls = new String[] { "http://nhac.vn/album/nhac-nhac-khong-loi-gr1NM",
			"http://nhac.vn/album/nhac-rap-hiphop-viet-gr9w", "http://nhac.vn/album/nhac-au-my-grjA",
			"http://nhac.vn/album/nhac-nhac-tre-grbR", "http://nhac.vn/album/nhac-nhac-trinh-grP5",
			"http://nhac.vn/album/nhac-tru-tinh-grnj", "http://nhac.vn/album/nhac-rock-viet-grLE",
			"http://nhac.vn/album/nhac-thieu-nhi-grwq", "http://www.nhaccuatui.com/playlist/khong-loi-moi.html",
			"http://www.nhaccuatui.com/playlist/rap-viet-moi.html", "http://www.nhaccuatui.com/playlist/au-my-moi.html",
			"http://www.nhaccuatui.com/playlist/nhac-tre-moi.html",
			"http://www.nhaccuatui.com/playlist/tru-tinh-moi.html",
			"http://www.nhaccuatui.com/playlist/nhac-trinh-moi.html",
			"http://www.nhaccuatui.com/playlist/rock-viet-moi.html",
			"http://www.nhaccuatui.com/playlist/thieu-nhi-moi.html", "http://www.nhaccuatui.com/video.html" };

	public MusicCollector(long repeatTime) {
		super(repeatTime);
	}

	private List<News> collectFromNacVN(Source s, String url, String fromWeb) {
		return new NhacVnParser().collectArticle(s, url, fromWeb);
	}

	private List<News> collectFromNCT(Source s, String url, String fromWeb) {
		return new NhacCuaTuiParser().collectArticle(s, url, fromWeb);
	}

	
	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		if("nhac.vn".equals(fromWebsite)){
			return collectFromNacVN(source, url, fromWebsite);
		}else{
			return collectFromNCT(source, url, fromWebsite);
		}
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}

}
