package com.xyz.hayhay.website.parser;

import java.util.ArrayList;
import java.util.List;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.entirty.NewsTypes;
import com.xyz.hayhay.entirty.webcollection.A;
import com.xyz.hayhay.entirty.webcollection.Image;
import com.xyz.hayhay.entirty.webcollection.ShotDescription;
import com.xyz.hayhay.entirty.webcollection.Title;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class MonNgonMoiNgayParser extends BaseParser{

	
	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		String type = "";
		if (url.indexOf("thit-heo") > 0) {
			type = NewsTypes.NAUAN_HEO;
		} else if (url.indexOf("thit-ga") > 0) {
			type = NewsTypes.NAUAN_GA;
		} else if (url.indexOf("thit-bo") > 0) {
			type = NewsTypes.NAUAN_BO;
		} else if (url.indexOf("mon-ngon-tu-ca") > 0) {
			type = NewsTypes.NAUAN_CA;
		} else if (url.indexOf("mon-ngon-tu-muc") > 0) {
			type = NewsTypes.NAUAN_MUC;
		} else if (url.indexOf("mon-ngon-tu-tom") > 0) {
			type = NewsTypes.NAUAN_TOM;
		} else if (url.indexOf("hai-san-khac") > 0) {
			type = NewsTypes.NAUAN_HAISANKHAC;
		} else if (url.indexOf("mon-ngon-tu-nam") > 0) {
			type = NewsTypes.NAUAN_NAM;
		} else if (url.indexOf("mon-ngon-tu-dau-hu") > 0) {
			type = NewsTypes.NAUAN_DAUHU;
		}
		return collectNews(type, source, fromWebsite);
	}
	private List<News> collectNews(String type, Source s, String fromWebsite) {
		List<News> nauannews = new ArrayList<News>();
		A a = new A();
		Image i = new Image();
		Title t = new Title("h3", null, null, true);
		ShotDescription d = new ShotDescription("p", "class", "info", true);
		List<Element> news = s.getAllElementsByClass("thanhvien_gr03_child");
		for (Element e : news) {
			News n = new News();
			n.setFromWebSite(fromWebsite);
			n.setType(type);
			n.setParentCateName(NewsTypes.NAUAN);
			parseElementToNews(e, n, a, t, i, d);
			if (!nauannews.contains(n) && n.getTitle() != null && !n.getTitle().isEmpty() && n.getImageUrl() != null
					&& !n.getImageUrl().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()) {
				nauannews.add(n);
			}
		}
		return nauannews;
	}
}
