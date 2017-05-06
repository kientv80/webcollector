package com.xyz.hayhay.entirty;

import java.util.ArrayList;
import java.util.List;

public class MenuItem {
	private List<MenuItem> submenus = new ArrayList<MenuItem>();
	private String cateId;
	
	public MenuItem(String cateId,String href, String title, String lable, String image) {
		this.href = href;
		this.title = title;
		this.lable = lable;
		this.image = image;
		this.setCateId(cateId);
	}
	
	
	/**
	 * @return the href
	 */
	public String getHref() {
		return href;
	}
	/**
	 * @param href the href to set
	 */
	public void setHref(String href) {
		this.href = href;
	}
	/**
	 * @return the title
	 */
	public String getTitle() {
		return title;
	}
	/**
	 * @param title the title to set
	 */
	public void setTitle(String title) {
		this.title = title;
	}
	/**
	 * @return the image
	 */
	public String getImage() {
		return image;
	}
	/**
	 * @param image the image to set
	 */
	public void setImage(String image) {
		this.image = image;
	}
	/**
	 * @return the lable
	 */
	public String getLable() {
		return lable;
	}
	/**
	 * @param lable the lable to set
	 */
	public void setLable(String lable) {
		this.lable = lable;
	}
	public List<MenuItem> getSubmenus() {
		return submenus;
	}


	public void setSubmenus(List<MenuItem> submenus) {
		this.submenus = submenus;
	}
	public String getCateId() {
		return cateId;
	}


	public void setCateId(String cateId) {
		this.cateId = cateId;
	}
	String href;
	String title;
	String image;
	String lable;
}
