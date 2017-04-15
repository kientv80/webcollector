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

public class NhacCuaTuiParser  extends BaseParser{

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		Title title = new Title("a", "class", "name_song", true);
		Image image = new Image();
		image.setValueFromAtttributeName("data-src");
		String type = "";
		if (url.indexOf("au-my-moi.html") > 0) {
			type = NewsTypes.NHACAUMY;
		} else if (url.indexOf("nhac-tre-moi") > 0) {
			type = NewsTypes.NHACTREMOI;
		} else if (url.indexOf("tru-tinh-moi") > 0) {
			type = NewsTypes.NHACTRUTINH;
		} else if (url.indexOf("nhac-trinh-moi") > 0) {
			type = NewsTypes.NHACTRINH;
		} else if (url.indexOf("rock-viet-moi") > 0) {
			type = NewsTypes.ROCKVIET;
		} else if (url.indexOf("rap-viet-moi.html") > 0) {
			type = NewsTypes.NHACRAP;
		} else if (url.indexOf("thieu-nhi-moi") > 0) {
			type = NewsTypes.THIEUNHI;
		} else if (url.indexOf("video") > 0) {
			type = NewsTypes.VIDEO;
		} else if (url.indexOf("khong-loi-moi.html") > 0) {
			type = NewsTypes.NHACKHONGLOI;
		}
		return  collectMusic(title, image, source, type, fromWebsite);
	}
	private List<News> collectMusic(Title title, Image image, Source s, String type, String fromWeb) {
		List<News> musics = new ArrayList<>();
		List<Element> album = s.getAllElementsByClass("fram_select");
		A a = new A("a", "class", "name_song", false);
		if (album != null && album.size() > 0) {
			for (Element al : album) {
				List<Element> items = al.getAllElements("li");
				for (Element hotMusic : items) {
					News n = new News();
					n.setFromWebSite(fromWeb);
					n.setType(type);
					n.setParentCateName(NewsTypes.TYPE_MUSIC);
					parseElementToNews(hotMusic, n, a, title, image, null);
					if (!musics.contains(n) && n.getTitle() != null && !n.getTitle().isEmpty()
							&& n.getImageUrl() != null && !n.getImageUrl().isEmpty() && n.getUrl() != null
							&& !n.getUrl().isEmpty()) {
						musics.add(n);
					}
				}
			}
		}
		return musics;
	}

}
