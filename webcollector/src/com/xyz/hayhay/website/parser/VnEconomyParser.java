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
		ShotDescription p = new ShotDescription("p", "data-type", "sapo", true);
		;

		List<News> vneconomy = new ArrayList<>();
		String type = "";
		String ptype = "";
		if (url.contains("tai-chinh.htm")) {
			type = NewsTypes.TYPE.Finance.name();
			ptype = NewsTypes.CATEGORY.Economic.name();
		} else if (url.contains("ngan-hang.htm")) {
			type = NewsTypes.TYPE.Banking.name();
		} else if (url.contains("thue-ngan-sach.htm")) {
			type = NewsTypes.TYPE.Budget.name();
			ptype = NewsTypes.CATEGORY.Economic.name();
		} else if (url.contains("lai-suat.htm")) {
			type = NewsTypes.TYPE.Banking.name();
			ptype = NewsTypes.CATEGORY.Economic.name();
		} else if (url.contains("thi-truong-vang.htm")) {
			type = NewsTypes.TYPE.Gold.name();
			ptype = NewsTypes.CATEGORY.Economic.name();
		} else if (url.contains("ty-gia.htm?")) {
			type = NewsTypes.TYPE.Banking.name();
			ptype = NewsTypes.CATEGORY.Economic.name();
		} else if (url.contains("chung-khoan.htm")) {
			type = NewsTypes.TYPE.Stock.name();
			ptype = NewsTypes.CATEGORY.Economic.name();
		} else if (url.contains("bat-dong-san.htm")) {
			type = NewsTypes.TYPE.Realty.name();
			ptype = NewsTypes.CATEGORY.Realty.name();
		}
		collectArticles(s, vneconomy, a, t, image, p, type, ptype, fromWebsite);
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
			String type, String ptype, String fromWeb) {

		if (source.getElementById("LoadTimeLine") != null) {
			for (Element midNews : source.getElementById("LoadTimeLine").getAllElements("li")) {
				News mn = new News();
				mn.setFromWebSite(fromWeb);
				mn.setType(type);
				mn.setNewsOrder(News.NEWS_ORDER.HI.name());
				if (NewsTypes.TYPE.Realty.name().equals(type)) {
					mn.setParentCateName(NewsTypes.CATEGORY.Realty.name());
				} else {
					mn.setParentCateName(NewsTypes.CATEGORY.Economic.name());
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
		
	}
}
