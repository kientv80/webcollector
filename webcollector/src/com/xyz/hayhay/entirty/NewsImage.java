package com.xyz.hayhay.entirty;

public class NewsImage {
	/**
	 * @return the id
	 */
	public int getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(int id) {
		this.id = id;
	}
	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	int id;
	String imageUrl;
	public NewsImage(int id, String imageUrl) {
		super();
		this.id = id;
		this.imageUrl = imageUrl;
	}
	
}
