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

public class NhacVnParser  extends BaseParser{

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		Title title = new Title("a", null, null, false);
		title.setValueFromAtttributeName("title");
		Image image = new Image();
		A a = new A();
		String type = "";
		if (url.indexOf("nhac-au-my-grjA") > 0) {
			type = NewsTypes.NHACAUMY;
		} else if (url.indexOf("nhac-nhac-tre-grbR") > 0) {
			type = NewsTypes.NHACTREMOI;
		} else if (url.indexOf("nhac-tru-tinh-grnj") > 0) {
			type = NewsTypes.NHACTRUTINH;
		} else if (url.indexOf("nhac-nhac-trinh-grP5") > 0) {
			type = NewsTypes.NHACTRINH;
		} else if (url.indexOf("nhac-rock-viet-grLE") > 0) {
			type = NewsTypes.ROCKVIET;
		} else if (url.indexOf("nhac-rap-hiphop-viet-gr9w") > 0) {
			type = NewsTypes.NHACRAP;
		} else if (url.indexOf("nhac-thieu-nhi-grwq") > 0) {
			type = NewsTypes.THIEUNHI;
		} else if (url.indexOf("nhac-nhac-khong-loi-gr1NM") > 0) {
			type = NewsTypes.NHACKHONGLOI;
		}
		List<News> listSongs = new ArrayList<>();
		if (!type.isEmpty()) {
			List<Element> songs = source.getAllElementsByClass("box_content").get(0).getAllElements("li");
			for (Element song : songs) {
				News n = new News();
				n.setFromWebSite(fromWebsite);
				n.setType(type);
				n.setParentCateName(NewsTypes.TYPE_MUSIC);
				parseElementToNews(song, n, a, title, image, null);
				if (!listSongs.contains(n) && n.getTitle() != null && !n.getTitle().isEmpty() && n.getImageUrl() != null
						&& !n.getImageUrl().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()) {
					listSongs.add(n);
				}
			}
		}
		return listSongs;
	}

}
