package com.xyz.hayhay.website.parser;

import java.util.ArrayList;
import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.entirty.NewsTypes;
import com.xyz.hayhay.entirty.webcollection.A;
import com.xyz.hayhay.entirty.webcollection.Image;
import com.xyz.hayhay.entirty.webcollection.Title;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class PhimMoiParser  extends BaseParser{

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		Title title = new Title("span", "class", "movie-title-1", true);
		Image image = new Image("div", "class", "movie-thumbnail", false);
		image.setValueFromAtttributeName("style");
		A a = new A();
		a.setDomain("http://www.phimmoi.net/");
		String type = "";
		
		if (url.indexOf("phim-hanh-dong") > 0) {
			type = NewsTypes.FILM_HANHDONG;
		} else if (url.indexOf("phim-hai-huoc") > 0) {
			type = NewsTypes.FILM_HAIHUOC;
		} else if (url.indexOf("phim-vien-tuong") > 0) {
			type = NewsTypes.FILM_VIENTUONG;
		} else if (url.indexOf("phim-vo-thuat") > 0) {
			type = NewsTypes.FILM_VOTHUAT;
		} else if (url.indexOf("phim-than-thoai") > 0) {
			type = NewsTypes.FILM_THANTHOAI;
		} else if (url.indexOf("phim-the-thao-am-nhac") > 0) {
			type = NewsTypes.FILM_NHAC;
		} else if (url.indexOf("phim-chien-tranh") > 0) {
			type = NewsTypes.FILM_CHIENTRANH;
		} else if (url.indexOf("phim-kinh-di") > 0) {
			type = NewsTypes.FILM_KINHDI;
		} else if (url.indexOf("phim-hoat-hinh") > 0) {
			type = NewsTypes.FILM_HOATHINH;
		} else if (url.indexOf("phim-phieu-luu") > 0) {
			type = NewsTypes.FILM_PHIEULUU;
		} else if (url.indexOf("phim-tinh-cam-lang-man") > 0) {
			type = NewsTypes.FILM_TINHCAM;
		}
		List<News> listFilms = new ArrayList<>();
		if (!type.isEmpty()) {
			List<Element> films = source.getAllElementsByClass("list-movie").get(0).getAllElements("li");
			for (Element film : films) {
				News n = new News();
				n.setFromWebSite("phimmoi.net");
				n.setType(type);
				n.setParentCateName(NewsTypes.TYPE_FILM);
				parseElementToNews(film, n, a, title, image, null);
				int fronIndex = n.getImageUrl().indexOf("url=");
				int toIndex = n.getImageUrl().indexOf("poster.thumb.jpg");
				if (n.getImageUrl() != null && fronIndex > 0 && toIndex > 0) {
					n.setImageUrl(n.getImageUrl().substring(fronIndex + "url=".length(),
							toIndex + "poster.thumb.jpg".length()));
					if (!listFilms.contains(n) && n.getTitle() != null && !n.getTitle().isEmpty()
							&& n.getImageUrl() != null && !n.getImageUrl().isEmpty() && n.getUrl() != null
							&& !n.getUrl().isEmpty()) {
						listFilms.add(n);
					}
				}
			}
		}
		return listFilms;
	}

}
