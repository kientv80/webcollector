package com.xyz.hayhay.entirty;

public class Feature extends CategoryInfo{
	private String thumeImage;
	public Feature(String name, String url, String image, String title, String desc, String keywords) {
		super(name, url, image, title, desc, keywords);
	}
	public Feature(String name, String url, String image,String thumeImage, String title, String desc, String keywords) {
		super(name, url, image, title, desc, "");
		this.thumeImage = thumeImage;
	}
	public String getThumeImage() {
		return thumeImage;
	}
	public void setThumeImage(String thumeImage) {
		this.thumeImage = thumeImage;
	}
}
