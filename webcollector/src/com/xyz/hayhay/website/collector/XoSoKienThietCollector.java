package com.xyz.hayhay.website.collector;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;

import com.xyz.hayhay.db.JDBCConnection;
import com.xyz.hayhay.entirty.News;
import com.xyz.hayhay.website.nonearticle.parser.XoSoKienThietParser;

import net.htmlparser.jericho.Source;

public class XoSoKienThietCollector extends ArticleCollector {
	String strurls = "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/mien-bac-xsmb/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/an-giang-xsag/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/binh-duong-xsbd/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/binh-dinh-xsbdi/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/bac-lieu-xsbl/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/binh-phuoc-xsbp/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/ben-tre-xsbt/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/binh-thuan-xsbth/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/ca-mau-xscm/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/can-tho-xsct/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/dac-lac-xsdlk/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/dong-nai-xsdn/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/da-nang-xsdng/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/dac-nong-xsdno/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/dong-thap-xsdt/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/gia-lai-xsgl/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/tp-hcm-xshcm/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/hau-giang-xshg/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/kien-giang-xskg/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/khanh-hoa-xskh/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/kon-tum-xskt/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/long-an-xsla/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/lam-dong-xsld/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/ninh-thuan-xsnt/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/phu-yen-xspy/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/quang-binh-xsqb/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/quang-ngai-xsqng/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/quang-nam-xsqnm/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/quang-tri-xsqt/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/soc-trang-xsst/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/tien-giang-xstg/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/tay-ninh-xstn/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/thua-thien-hue-xstth/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/tra-vinh-xstv/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/vinh-long-xsvl/,"
			+ "http://xskt.com.vn/ket-qua-xo-so-theo-ngay/vung-tau-xsvt/";
	public static Map<String, String> nameMapping = new LinkedHashMap<>();
	static {
		nameMapping.put("mien-bac-xsmb", "Miền Bắc");
		nameMapping.put("an-giang-xsag", "An Giang");
		nameMapping.put("binh-duong-xsbd", "Bình Dương");
		nameMapping.put("binh-dinh-xsbdi", "Bình Định");
		nameMapping.put("bac-lieu-xsbl", "Bạc Liêu");
		nameMapping.put("binh-phuoc-xsbp", "Bình Phước");
		nameMapping.put("ben-tre-xsbt", "Bến Tre");
		nameMapping.put("binh-thuan-xsbth", "Bình Thuận");
		nameMapping.put("ca-mau-xscm", "Cà Mau");
		nameMapping.put("can-tho-xsct", "Cần Thơ");
		nameMapping.put("dac-lac-xsdlk", "Đắc Lắc");
		nameMapping.put("dong-nai-xsdn", "Đống Nai");
		nameMapping.put("da-nang-xsdng", "Đà Nẳng");
		nameMapping.put("dac-nong-xsdno", "Đắc Nông");
		nameMapping.put("dong-thap-xsdt", "Đống Tháp");
		nameMapping.put("gia-lai-xsgl", "Gia Lai");
		nameMapping.put("tp-hcm-xshcm", "TP Hồ Chí Minh");
		nameMapping.put("hau-giang-xshg", "Hậu Giang");
		nameMapping.put("kien-giang-xskg", "Kiên Giang");
		nameMapping.put("khanh-hoa-xskh", "Khánh Hòa");
		nameMapping.put("kon-tum-xskt", "Kon Tum");
		nameMapping.put("long-an-xsla", "Long An");
		nameMapping.put("lam-dong-xsld", "Lâm Đồng");
		nameMapping.put("ninh-thuan-xsnt", "Ninh Thận");
		nameMapping.put("phu-yen-xspy", "Phú Yên");
		nameMapping.put("quang-binh-xsqb", "Quảng Bình");
		nameMapping.put("quang-ngai-xsqng", "Quảng Ngãi");
		nameMapping.put("quang-nam-xsqnm", "Quảng Nam");
		nameMapping.put("quang-tri-xsqt", "Quảng Trị");
		nameMapping.put("soc-trang-xsst", "Sóc Trăng");
		nameMapping.put("tien-giang-xstg", "Tiền Giang");
		nameMapping.put("tay-ninh-xstn", "Tây Ninh");
		nameMapping.put("thua-thien-hue-xstth", "Thừa Thiên Huế");
		nameMapping.put("tra-vinh-xstv", "Trà Vinh");
		nameMapping.put("vinh-long-xsvl", "Vĩnh Long");
		nameMapping.put("vung-tau-xsvt", "Vũng Tầu");

	}
	String urls[] = strurls.split(",");

	public XoSoKienThietCollector(long repeatTime) {
		super(repeatTime);
	}

	SimpleDateFormat df = new SimpleDateFormat("d-M-YYYY");

	@Override
	public List<News> collectArticle(Source source, String url, String fromWebsite) {
		int hour = Calendar.getInstance().get(Calendar.HOUR_OF_DAY);
		if (hour >= 16 && hour <= 19) {
			String letteryAgency = url.substring("http://xskt.com.vn/ket-qua-xo-so-theo-ngay/".length(),
					url.lastIndexOf("/"));
			try {
				if (!isCollected(letteryAgency)) {
					JSONArray result = new XoSoKienThietParser().collectArticle(source, url, fromWebsite);
					if (!"[]".equals(result.toString()))
						store(letteryAgency, result);
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	@Override
	public String[] collectedUrls() {
		StringBuilder collectUrls = new StringBuilder();
		String date = df.format(Calendar.getInstance().getTime());
		for (String url : urls) {
			collectUrls.append(url).append(date).append(".html").append(",");
		}
		return collectUrls.toString().split(",");
	}

	private boolean isCollected(String letteryAgency) throws Exception {
		String sql = "SELECT id from lottery where date = current_date and code=?";
		Connection con = JDBCConnection.getInstance().getConnection();
		boolean result = false;
		try {

			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, letteryAgency);
			ResultSet rs = stm.executeQuery();
			while (rs.next()) {
				result = true;
			}
			rs.close();
			stm.close();
		} finally {
			con.close();
		}
		return result;
	}

	private void store(String letteryAgency, JSONArray data) throws Exception {
		String sql = "INSERT INTO lottery(code,name,date,data)values(?,?,?,?)";
		Connection con = JDBCConnection.getInstance().getConnection();
		try {
			PreparedStatement stm = con.prepareStatement(sql);
			stm.setString(1, letteryAgency);
			stm.setString(2, nameMapping.get(letteryAgency));
			stm.setDate(3, new Date(Calendar.getInstance().getTimeInMillis()));
			stm.setString(4, data.toJSONString());
			stm.execute();
			stm.close();
		} finally {
			con.close();
		}

	}

	public static void main(String[] args) {

	}
}
