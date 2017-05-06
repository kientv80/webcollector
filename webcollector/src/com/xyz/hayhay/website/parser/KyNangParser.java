package com.xyz.hayhay.website.parser;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.entirty.NewsTypes;
import com.xyz.hayhay.entirty.webcollection.A;
import com.xyz.hayhay.entirty.webcollection.Image;
import com.xyz.hayhay.entirty.webcollection.ShotDescription;
import com.xyz.hayhay.entirty.webcollection.Title;

import net.htmlparser.jericho.Element;
import net.htmlparser.jericho.Source;

public class KyNangParser extends BaseParser {
	public static Map<String, String> categories = new HashMap<>();
	static {
		// Ky nang men
		categories.put("http://www.kynang.edu.vn/ky-nang-mem/ky-nang-giao-tiep","kngiaotiep");
		categories.put("http://www.kynang.edu.vn/ky-nang-mem/ky-nang-trinh-bay-thuyet-trinh","knthuyettrinh");
		categories.put( "http://www.kynang.edu.vn/ky-nang-mem/ky-nang-lam-chu-cam-xuc","knqlcamsuc");
		categories.put("http://www.kynang.edu.vn/ky-nang-mem/ky-nang-lam-viec-nhom","knlamviecnhom");
		categories.put("http://www.kynang.edu.vn/ky-nang-mem/ky-nang-phong-van-tim-viec","knphonvan");
		// categories.put("knquanlythoigian",
		// "http://www.kynang.edu.vn/ky-nang-quan-ly-thoi-gian");
		categories.put("http://www.kynang.edu.vn/ky-nang-mem/ky-nang-tu-duy-sang-tao","kntuduysangtao");
		categories.put("http://www.kynang.edu.vn/ky-nang-mem/ky-nang-tu-hoc-hieu-qua","kntuhoc");
		categories.put("http://www.kynang.edu.vn/ky-nang-mem/ky-nang-giai-quyet-van-de","kngiaiquyetvande");
		categories.put("http://www.kynang.edu.vn/ky-nang-mem/ky-nang-tao-dong-luc","kntaodongluc");
		categories.put("http://www.kynang.edu.vn/ky-nang-mem/ky-nang-thich-nghi","knthichnghi");
		// Ky nang nghe nghiep
		categories.put("http://www.kynang.edu.vn/ky-nang-nghe-nghiep/dinh-huong-nghe-nghiep","kndhnn");
		categories.put("http://www.kynang.edu.vn/ky-nang-nghe-nghiep/ky-nang-ban-hang","knbanhang");
		categories.put("http://www.kynang.edu.vn/ky-nang-nghe-nghiep/marketing-online","marketingonline");
		categories.put("http://www.kynang.edu.vn/ky-nang-nghe-nghiep/thuong-mai-dien-tu","thuongmaidt");
		categories.put("http://www.kynang.edu.vn/ky-nang-nghe-nghiep/ky-nang-quan-ly-lanh-dao","knquanlylanhdao");
		categories.put("http://www.kynang.edu.vn/ky-nang-nghe-nghiep/ky-nang-soan-thao-van-ban","knsoanthaovb");
		categories.put("http://www.kynang.edu.vn/ky-nang-nghe-nghiep/ky-nang-dam-phan-thuong-luong","kndamphanthuongluong");
		categories.put("http://www.kynang.edu.vn/tai-chinh-doanh-nghiep","kntaichinhdoannghiep");
		categories.put("http://www.kynang.edu.vn/quan-ly-tai-chinh-ca-nhan","kntaichinhcanhan");
		categories.put("http://www.kynang.edu.vn/ky-nang-phan-quyen-va-uy-thac-cong-viec","knphanquyenvauythaccv");
		categories.put("http://www.kynang.edu.vn/ky-nang-nghe-nghiep/ky-nang-quan-ly-ho-so","knqlhoso");
		categories.put("http://www.kynang.edu.vn/ky-nang-nghe-nghiep/quan-ly-nhan-su","knqlnhansu");
		categories.put("http://www.kynang.edu.vn/ky-nang-nghe-nghiep/van-hoa-doanh-nghiep","vhdoanhnghiep");
	}

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		A a = new A("a", null, null, false);
		a.setValueFromAtttributeName("href");
		Title t = new Title("a", null, null, true);
		t.setValueFromAtttributeName("title");
		Image image = new Image("img", "itemprop", "image", false);
		image.setValueFromAtttributeName("src");
		ShotDescription p = new ShotDescription("div", "class", "td-post-text-excerpt", true);

		return collectArticles(a, t, image, p, categories.get(url), source, fromWebsite);
	}

	private List<News> collectArticles(A a, Title t, Image image, ShotDescription p, String cate,
			Source s, String fromWebsite) {
		if(cate == null)
			return null;
		List<News> articles = new ArrayList<>();
		for (Element e : s.getAllElementsByClass("span6")) {
			News n = new News();
			n.setFromWebSite(fromWebsite);
			n.setType(cate);
			n.setParentCateName(NewsTypes.SOFTSKILLS);
			parseElementToNews(e, n, a, t, image, p);
			if (n.getTitle() != null && !n.getTitle().isEmpty() && n.getUrl() != null && !n.getUrl().isEmpty()
					&& n.getImageUrl() != null && !n.getImageUrl().isEmpty()) {
				if (!articles.contains(n)) {
					articles.add(n);
				}
			}
		}
		return articles;
	}
}
