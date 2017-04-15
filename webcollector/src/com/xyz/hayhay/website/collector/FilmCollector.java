package com.xyz.hayhay.website.collector;

import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.parser.PhimMoiParser;

import net.htmlparser.jericho.Source;

public class FilmCollector extends ArticleCollector {
	String[] urls = new String[] { "http://www.phimmoi.net/the-loai/phim-hanh-dong/",
			"http://www.phimmoi.net/the-loai/phim-hai-huoc/", "http://www.phimmoi.net/the-loai/phim-vien-tuong/",
			"http://www.phimmoi.net/the-loai/phim-vo-thuat/", "http://www.phimmoi.net/the-loai/phim-than-thoai/",
			"http://www.phimmoi.net/the-loai/phim-the-thao-am-nhac/",
			"http://www.phimmoi.net/the-loai/phim-chien-tranh/", "http://www.phimmoi.net/the-loai/phim-kinh-di/",
			"http://www.phimmoi.net/the-loai/phim-hoat-hinh/", "http://www.phimmoi.net/the-loai/phim-phieu-luu/",
			"http://www.phimmoi.net/the-loai/phim-tinh-cam-lang-man/" };

	public FilmCollector(long repeatTime) {
		super(repeatTime);
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		return new PhimMoiParser().collectArticle(source, url, fromWebsite);
	}

	@Override
	public String[] collectedUrls() {
		return urls;
	}
}
