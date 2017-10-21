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

public class ICTNewsParser  extends BaseParser{

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		List<News> congngheNews = new ArrayList<>();
		String type = "";
		if(url.indexOf("cntt")>0){
			type = NewsTypes.TYPE.IT.name();
		}else if(url.indexOf("vien-thong")>0){
			type=NewsTypes.TYPE.Telecom.name();
		}else if(url.indexOf("internet")>0){
			type = NewsTypes.TYPE.Internet.name();
		}else if(url.indexOf("the-gioi-so")>0){
			type = NewsTypes.TYPE.Telecom.name();
		}else if(url.indexOf("khoi-nghiep")>0){
			type = NewsTypes.TYPE.StatUp.name();
		}else{//cong-nghe-360
			type = NewsTypes.TYPE.Tech.name();
		}
		if(NewsTypes.TYPE.StatUp.name().equals(type)){
			collectICTNewsArticles(source, congngheNews,type,NewsTypes.CATEGORY.Business.name(),fromWebsite);
		}else{
			collectICTNewsArticles(source, congngheNews,type,NewsTypes.CATEGORY.Tech.name(),fromWebsite);
		}
		return congngheNews;
	}
	private void collectICTNewsArticles(Source source, List<News> congngheNews,String type, String parentCateName, String fromWebsite) {
		A a = new A();
		Title t = new Title("a","class","g-title",false); 
		t.setValueFromAtttributeName("title");
		
		Image image = new Image();
		image.setValueFromAtttributeName("src");
		ShotDescription p = new ShotDescription("div","class","g-row",true);
		p.setExcludedTexts("CNTT,Bảo mật,Nước mạnh CNTT,Phần mềm,Viễn thông,Số hóa truyền hình,Xã hội @,Internet,Thủ thuật,Di động,Khởi nghiệp,Ô tô – Xe máy,Khoa học,Điện máy,Khoa học,Máy tính");
		Element articles = source.getAllElementsByClass("news-list").get(0).getChildElements().get(0);
		for(Element e : articles.getChildElements()){
			News n = new News();
			n.setFromWebSite(fromWebsite);
			n.setType(type);
			n.setParentCateName(parentCateName);
			parseElementToNews(e, n, a, t, image, p);
			if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty() && n.getImageUrl() != null &&  !n.getImageUrl().isEmpty()) {
				if (!congngheNews.contains(n)) {
					congngheNews.add(n);
				}
			}
		}
	}
}
