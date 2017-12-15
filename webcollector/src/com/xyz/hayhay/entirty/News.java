package com.xyz.hayhay.entirty;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;

public class News {
	public static enum COUNTRY {
		VN, US, CHINA
	}

	public static enum NEWS_ORDER {
		H, HI, M
	};// hotnews,highlight news, mid news

	public News() {
		this.country = News.COUNTRY.VN.name();
	}

	public News(String country) {
		this.country = country;
	}

	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}

	/**
	 * @param title
	 *            the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}

	/**
	 * @return the shotDesc
	 */
	public String getShotDesc() {
		return shotDesc;
	}

	/**
	 * @param shotDesc
	 *            the shotDesc to set
	 */
	public void setShotDesc(String shotDesc) {
		this.shotDesc = shotDesc;
	}

	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}

	/**
	 * @param imageUrl
	 *            the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}

	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}

	/**
	 * @param url
	 *            the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * @return the type
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param type
	 *            the type to set
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return the fromWebSite
	 */
	public String getFromWebSite() {
		return fromWebSite;
	}

	/**
	 * @param fromWebSite
	 *            the fromWebSite to set
	 */
	public void setFromWebSite(String fromWebSite) {
		this.fromWebSite = fromWebSite;
	}

	/**
	 * @return the date
	 */
	public Timestamp getDate() {
		return date;
	}

	public static final SimpleDateFormat df = new SimpleDateFormat("dd/MM/YY HH:mm:ss");

	private String strDate = "";

	public String getStrDate() {
		if (getDate() != null)
			strDate = News.df.format(getDate());
		return strDate;
	}

	/**
	 * @param date
	 *            the date to set
	 */
	public void setDate(Timestamp date) {
		this.date = date;
		getStrDate();
	}

	String title;
	String shotDesc = "";
	String imageUrl;
	String url;
	String type;
	String fromWebSite;
	Timestamp date;
	int id;
	boolean isHotNews;
	String newsOrder = "M";
	boolean is360hayArticle;
	private String uniqueName = "";
	private String parentCateName;

	/**
	 * @return the is360hayArticle
	 */
	public boolean isIs360hayArticle() {
		return is360hayArticle;
	}

	/**
	 * @param is360hayArticle
	 *            the is360hayArticle to set
	 */
	public void setIs360hayArticle(boolean is360hayArticle) {
		this.is360hayArticle = is360hayArticle;
	}

	/**
	 * @return the newsOrder
	 */
	public String getNewsOrder() {
		return newsOrder;
	}

	/**
	 * @param newsOrder
	 *            the newsOrder to set
	 */
	public void setNewsOrder(String newsOrder) {
		this.newsOrder = newsOrder;
	}

	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}

	/**
	 * @param id
	 *            the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}

	/**
	 * @return the isHotNews
	 */
	public boolean isHotNews() {
		return isHotNews;
	}

	/**
	 * @param isHotNews
	 *            the isHotNews to set
	 */
	public void setHotNews(boolean isHotNews) {
		this.isHotNews = isHotNews;
	}

	public String getCateName() {
		if (MappingHelper.categoryTypeLabelMapping.get(getType()) != null)
			return MappingHelper.categoryTypeLabelMapping.get(getType());
		else
			return "";
	}

	public String getUniqueName() {
		if (uniqueName.isEmpty() && title != null && !title.isEmpty()) {
			for (String c : title.split(" ")) {
				if (c.length() > 0)
					uniqueName = uniqueName + c.charAt(0);
			}
		}
		return uniqueName;
	}

	public void setStrDate(String strDate) {
		this.strDate = strDate;
	}

	public String getParentCateName() {
		return parentCateName;
	}

	public void setParentCateName(String parentCateName) {
		this.parentCateName = parentCateName;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		// final int prime = 31;
		// int result = 1;
		// result = prime * result + ((title == null) ? 0 : title.hashCode());
		// return result;
		return 1;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#equals(java.lang.Object)
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof News)) {
			return false;
		}
		News other = (News) obj;
		double accurate = 0.8d;
		if (other.getType() == NewsTypes.TYPE_MUSIC)
			accurate = 1;
		if (this.getFromWebSite().equals(other.getFromWebSite())) {
			if (this.getUrl().equals(other.getUrl())) {
				return true;
			} else if (this.getTitle().equals(other.getTitle())) {
				return true;
			} else {
				return isSiminler(this.getTitle(), other.getTitle(), accurate);
			}
		} else {
			return isSiminler(this.getTitle(), other.getTitle(), accurate);
		}
	}

	public static boolean isSiminler(String str1, String str2, double percent) {
		if (str1 == null && str2 == null)
			return true;
		else if (str1 == null && str2 != null || str1 != null && str2 == null)
			return false;

		String[] str1List = str1.toLowerCase().split(" ");
		str2 = str2.toLowerCase();
		double similarWorldCount = 0;
		for (int i = 0; i < str1List.length; i++) {
			if (str2.contains(str1List[i])) {
				similarWorldCount++;
			}
		}
		if (similarWorldCount > 0 && ((similarWorldCount / str1List.length) > percent)) {
			return true;
		}
		return false;
	}

	public static void main(String args[]) {
		double accurate = 0.8;
		System.out.println(News.isSiminler("Ông Trump đáp trả đe dọa thử bom H ở Thái Bình Dương của Triều Tiên",
				"Ôdng Trumdp đádp trả đe dọa thử bom H ở Dai Tay Duong của Triều Tiên", accurate));
	}

	private String country;

	public String getCountry() {
		// TODO Auto-generated method stub
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	@Override
	public String toString() {
		StringBuilder content = new StringBuilder();
		content.append("Title:").append(title).append("\n");
		content.append("Desc:").append(shotDesc).append("\n");
		content.append("Url:").append(url).append("\n");
		content.append("ThumeUrl:").append(imageUrl).append("\n");
		content.append("Type:").append(type).append("\n");

		return content.toString();
	}
}
