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

public class CafeBizParser extends BaseParser{

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		String type = "";
		String parentCate = "";
		if(url.contains("nhan-vat.chn")){
			type = NewsTypes.NHAN_VAT;
			parentCate = NewsTypes.KINH_DOANH;
		}else if(url.contains("quan-tri.chn")){
			type = NewsTypes.QUAN_TRI;
			parentCate = NewsTypes.KINH_DOANH;
		}else if(url.contains("nghe-nghiep.chn")){
			type = NewsTypes.NGHE_NGHIEP;
			parentCate = NewsTypes.KINH_DOANH;
		}else if(url.contains("thuong-hieu.chn")){
			type = NewsTypes.THUONG_HIEU;
			parentCate = NewsTypes.KINH_DOANH;
		}else if(url.contains("welearn.chn")){
			type = NewsTypes.KINH_NHIEM_KINH_DOANH;
			parentCate = NewsTypes.KINH_DOANH;
		}else if(url.contains("tai-chinh.chn")){
			type = NewsTypes.TAICHINH;
			parentCate = NewsTypes.TYPE_ECONOMY;
		}else if(url.contains("chinh-sach.chn")){
			type = NewsTypes.CHINHSACH;
			parentCate = NewsTypes.TYPE_ECONOMY;
		}else if(url.contains("startup.chn")){
			type = NewsTypes.KHOINGHIEP;
			parentCate = NewsTypes.KHOINGHIEP;
		}else if(url.contains("doanh-nghiep-cong-nghe.chn")){
			type = NewsTypes.DOANH_NGHIEP;
			parentCate = NewsTypes.TYPE_CONGNGHE;
		}else if(url.contains("suc-khoe.chn")){
			type = NewsTypes.TYPE_SUCKHOE;
			parentCate = NewsTypes.TYPE_SUCKHOE;
		}
		
		if(type.isEmpty() || parentCate.isEmpty())
			return null;
		A a = new A();
		Image i = new Image();
		a.setDomain("http://cafebiz.vn");
		Title t = new Title("a",null,null,false);
		t.setValueFromAtttributeName("title");
		List<News> news = new ArrayList<>();
		List<Element> articles = source.getAllElementsByClass("noibat_cate").get(0).getAllElements("li");
		List<Element> articlesList = source.getAllElementsByClass("list_cate").get(0).getAllElements("li");
		for(Element e : articles){
			News n = new News();
			n.setType(type);
			n.setParentCateName(parentCate);
			n.setFromWebSite(fromWebsite);
			parseElementToNews(e, n, a, t, i, null);
			n.setUrl(n.getUrl().replace("http://cafebiz.vn", "http://m.cafebiz.vn"));
			if (!news.contains(n) && n.getTitle() != null && !n.getTitle().isEmpty() && n.getImageUrl() != null
					&& !n.getImageUrl().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()) {
				news.add(n);
			}
		}
		if(articlesList != null)
			for(Element e : articlesList){
				News n = new News();
				n.setType(type);
				n.setParentCateName(parentCate);
				n.setFromWebSite(fromWebsite);
				parseElementToNews(e, n, a, t, i, null);
				n.setUrl(n.getUrl().replace("http://cafebiz.vn", "http://m.cafebiz.vn"));
				if (!news.contains(n) && n.getTitle() != null && !n.getTitle().isEmpty() && n.getImageUrl() != null
						&& !n.getImageUrl().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()) {
					news.add(n);
				}
			}
		return news;
	}

}
