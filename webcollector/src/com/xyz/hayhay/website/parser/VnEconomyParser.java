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

public class VnEconomyParser extends BaseParser {

	@Override
	public List<News> collectArticle(Source s, String url, String fromWebsite) {
		A a = new A("a", null, null, false);
		a.setDomain("http://m.vneconomy.vn");
		a.setValueFromAtttributeName("href");
		Title t = new Title("a", null, null, true);

		Image image = new Image("img", null, null, false);
		image.setValueFromAtttributeName("src");
		ShotDescription p = new ShotDescription("p", "class", "hdcontencmmid", true);
		;

		List<News> vneconomy = new ArrayList<>();
		String type = "";
		if (url.contains("tai-chinh.htm")) {
			type = NewsTypes.TAICHINH;
		} else if (url.contains("ngan-hang.htm")) {
			type = NewsTypes.NGANHANG;
		} else if (url.contains("thue-ngan-sach.htm")) {
			type = NewsTypes.NGANSACH;
		} else if (url.contains("lai-suat.htm")) {
			type = NewsTypes.LAISUAT;
		} else if (url.contains("thi-truong-vang.htm")) {
			type = NewsTypes.THITRUONGVANG;
		} else if (url.contains("ty-gia.htm?")) {
			type = NewsTypes.TYGIA;
		} else if (url.contains("chung-khoan.htm")) {
			type = NewsTypes.CHUNGKHOAN;
		} else if (url.contains("bat-dong-san.htm")) {
			type = NewsTypes.DIA_OC;
		}
		collectArticles(s, vneconomy, a, t, image, p, type, fromWebsite);
		// special case, change image for mobile to image for pc
		for (News n : vneconomy) {
			if (n.getImageUrl().indexOf("80_50") > 0)
				n.setImageUrl(n.getImageUrl().replace("80_50", "150_150"));
			else if (n.getImageUrl().indexOf("80_80") > 0)
				n.setImageUrl(n.getImageUrl().replace("80_80", "150_150"));
			else if (n.getImageUrl().indexOf("150_93") > 0)
				n.setImageUrl(n.getImageUrl().replace("150_93", "150_150"));
		}

		return vneconomy;
	}

	private void collectArticles(Source source, List<News> vneconomy, A a, Title t, Image image, ShotDescription p,
			String type, String fromWeb) {

		if (source.getAllElementsByClass("hdcmleft") != null && source.getAllElementsByClass("hdcmleft").size() > 0) {
			for (Element midNews : source.getAllElementsByClass("hdcmleft").get(0).getChildElements()) {
				News mn = new News();
				mn.setFromWebSite(fromWeb);
				mn.setType(type);
				mn.setNewsOrder(News.NEWS_ORDER.HI.name());
				if (NewsTypes.DIA_OC.equals(type)) {
					mn.setParentCateName(NewsTypes.XAYDUNG);
				} else {
					mn.setParentCateName(NewsTypes.TYPE_ECONOMY);
				}
				parseElementToNews(midNews, mn, a, t, image, p);
				if (mn.getTitle() != null && !mn.getTitle().isEmpty() && mn.getUrl() != null && !mn.getUrl().isEmpty()
						&& mn.getImageUrl() != null && !mn.getImageUrl().isEmpty()) {
					if (!vneconomy.contains(mn)) {
						vneconomy.add(mn);
					}
				}
			}
		}
		if (source.getAllElementsByClass("ultopmid moinhat-cont") != null
				&& source.getAllElementsByClass("ultopmid moinhat-cont").size() > 0) {
			for (Element midNews : source.getAllElementsByClass("ultopmid moinhat-cont").get(0).getChildElements()) {
				News mn = new News();
				mn.setFromWebSite(fromWeb);
				mn.setType(type);
				mn.setNewsOrder(News.NEWS_ORDER.HI.name());
				if (NewsTypes.DIA_OC.equals(type)) {
					mn.setParentCateName(NewsTypes.DIA_OC);
				} else {
					mn.setParentCateName(NewsTypes.TYPE_ECONOMY);
				}
				parseElementToNews(midNews, mn, a, t, image, null);
				if (mn.getTitle() != null && !mn.getTitle().isEmpty() && mn.getUrl() != null && !mn.getUrl().isEmpty()
						&& mn.getImageUrl() != null && !mn.getImageUrl().isEmpty()) {
					if (!vneconomy.contains(mn)) {
						vneconomy.add(mn);
					}
				}
			}
		}
		t.setElementValue("titletopmid2 ");
		p.setElementValue("contenttopmid2");
		for (Element midNews : source.getAllElementsByClass("boxnewsupdateitem")) {
			News mn = new News();
			mn.setFromWebSite(fromWeb);
			mn.setType(type);
			mn.setNewsOrder(News.NEWS_ORDER.M.name());
			if (NewsTypes.DIA_OC.equals(type)) {
				mn.setParentCateName(NewsTypes.DIA_OC);
			} else {
				mn.setParentCateName(NewsTypes.TYPE_ECONOMY);
			}
			parseElementToNews(midNews, mn, a, t, image, p);
			if (mn.getTitle() != null && !mn.getTitle().isEmpty() && mn.getUrl() != null && !mn.getUrl().isEmpty()
					&& mn.getImageUrl() != null && !mn.getImageUrl().isEmpty()) {
				if (!vneconomy.contains(mn)) {
					vneconomy.add(mn);
				}
			}
		}

		t.setElementValue("boxright5content");
		if (source.getAllElementsByClass("boxright5") != null && source.getAllElementsByClass("boxright5").size() > 0) {
			for (Element midNews : source.getAllElementsByClass("boxright5").get(0).getChildElements().get(1)
					.getChildElements()) {
				News mn = new News();
				mn.setFromWebSite(fromWeb);
				mn.setType(type);
				mn.setNewsOrder(News.NEWS_ORDER.M.name());
				if (NewsTypes.DIA_OC.equals(type)) {
					mn.setParentCateName(NewsTypes.DIA_OC);
				} else {
					mn.setParentCateName(NewsTypes.TYPE_ECONOMY);
				}
				parseElementToNews(midNews, mn, a, t, image, null);
				if (mn.getTitle() != null && !mn.getTitle().isEmpty() && mn.getUrl() != null && !mn.getUrl().isEmpty()
						&& mn.getImageUrl() != null && !mn.getImageUrl().isEmpty()) {
					if (!vneconomy.contains(mn)) {
						vneconomy.add(mn);
					}
				}
			}
		}
	}
}
