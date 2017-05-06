package com.xyz.hayhay.entirty;

public class TrackingInfo {
	String id;
	String name;
	boolean trackLike = false;
	private boolean trackVisitor = false;
	
	/**
	 * @return the trackLike
	 */
	public boolean isTrackLike() {
		return trackLike;
	}

	/**
	 * @param trackLike the trackLike to set
	 */
	public void setTrackLike(boolean trackLike) {
		this.trackLike = trackLike;
	}

	public TrackingInfo(String id, String name) {
		this.id = id;
		this.name = name;
		// TODO Auto-generated constructor stub
	}
	
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	public boolean isTrackVisitor() {
		return trackVisitor;
	}

	public void setTrackVisitor(boolean trackVisitor) {
		this.trackVisitor = trackVisitor;
	}
	
}
