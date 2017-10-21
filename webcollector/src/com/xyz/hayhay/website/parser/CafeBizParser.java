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
		String parentCate = NewsTypes.CATEGORY.Business.name();
		if(url.contains("nhan-vat.chn")){
			type = NewsTypes.TYPE.Figure.name();
		}else if(url.contains("quan-tri.chn")){
			type = NewsTypes.TYPE.Management.name();
		}else if(url.contains("nghe-nghiep.chn")){
			type = NewsTypes.TYPE.Job.name();
		}else if(url.contains("thuong-hieu.chn")){
			type = NewsTypes.TYPE.Trademark.name();
		}else if(url.contains("welearn.chn")){
			type = NewsTypes.TYPE.Experience.name();
		}else if(url.contains("tai-chinh.chn")){
			type = NewsTypes.TYPE.Finance.name();
			parentCate = NewsTypes.CATEGORY.Economic.name();
		}else if(url.contains("chinh-sach.chn")){
			type = NewsTypes.TYPE.Economic.name();
			parentCate = NewsTypes.CATEGORY.Economic.name();
		}else if(url.contains("startup.chn")){
			type = NewsTypes.TYPE.StatUp.name();
		}else if(url.contains("doanh-nghiep-cong-nghe.chn")){
			type = NewsTypes.TYPE.Company.name();
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
