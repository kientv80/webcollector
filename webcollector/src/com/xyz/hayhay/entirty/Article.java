package com.xyz.hayhay.entirty;

import java.io.Serializable;
import java.sql.Date;

import org.apache.commons.lang.StringEscapeUtils;

public class Article implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = -7192377231110519033L;
	/**
	 * 
	 */
	Long id = 0l;
	String subject;
	String content;
	String image;
	Date createDate;
	String createdBy;
	String commentIds;
	String likeIds;
	String shareIds;
	String href;
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
	 * @return the serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(Long id) {
		this.id = id;
	}

	String shortContent500;
	String shortContent1200;
	String shortContent200;
	
	int type;
	public String getShortContent(){
		String sc = content.replaceAll("\\<.*?\\>", "");
		sc = StringEscapeUtils.escapeHtml(sc);
		sc = sc.replaceAll("\"", "");
		if(sc.length() > 500){
			sc = sc.substring(0,500) + " ...";
		}
		return sc;
	}
	public String getShortContent2(){
		String sc = content.replaceAll("\\<.*?\\>", "");
		if(sc.length() > 500){
			sc = sc.substring(0,500) + " ...";
		}
		return sc;
	}
	/**
	 * @param likeCount the likeCount to set
	 */
	public void setLikeCount(int likeCount) {
		this.likeCount = likeCount;
	}
	/**
	 * @param commentCount the commentCount to set
	 */
	public void setCommentCount(int commentCount) {
		this.commentCount = commentCount;
	}
	/**
	 * @param shareCount the shareCount to set
	 */
	public void setShareCount(int shareCount) {
		this.shareCount = shareCount;
	}

	int likeCount;
	int commentCount;
	int shareCount;
	/**
	 * @return the type
	 */
	public int getType() {
		return type;
	}
	/**
	 * @param type the type to set
	 */
	public void setType(int type) {
		this.type = type;
	}
	public enum TYPE{HAY,CUOI,DOCDAO,OTHER};
	
	public Article() {
	}
	public Article(long id,String subject,String content,String image,Date createDate,String createdBy){
		this.id = id;
		this.subject = subject;
		this.content = content;
		this.image = image;
		this.createDate = createDate;
		this.createdBy = createdBy;
	}
	/**
	 * @return the id
	 */
	public Long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
	}
	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}
	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}
	/**
	 * @return the content
	 */
	public String getContent() {
		return content;
	}
	/**
	 * @param content the content to set
	 */
	public void setContent(String content) {
		this.content = content;
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
	 * @return the createDate
	 */
	public Date getCreateDate() {
		return createDate;
	}
	/**
	 * @param createDate the createDate to set
	 */
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	/**
	 * @return the createdBy
	 */
	public String getCreatedBy() {
		return createdBy;
	}
	/**
	 * @param createdBy the createdBy to set
	 */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}
	/**
	 * @return the commentIds
	 */
	public String getCommentIds() {
		return commentIds;
	}
	/**
	 * @param commentIds the commentIds to set
	 */
	public void setCommentIds(String commentIds) {
		this.commentIds = commentIds;
	}
	/**
	 * @return the likeIds
	 */
	public String getLikeIds() {
		return likeIds;
	}
	/**
	 * @param likeIds the likeIds to set
	 */
	public void setLikeIds(String likeIds) {
		this.likeIds = likeIds;
	}
	/**
	 * @return the shareIds
	 */
	public String getShareIds() {
		return shareIds;
	}
	/**
	 * @param shareIds the shareIds to set
	 */
	public void setShareIds(String shareIds) {
		this.shareIds = shareIds;
	}
	public int getLikeCount(){
		return this.likeCount;
	}
	public int getCommentCount(){
		if(this.commentIds == null || this.commentIds.isEmpty())
			return 0;
		return this.commentIds.split(",").length;
	}
	public int getShareCount(){
		if(this.shareIds == null || this.shareIds.isEmpty())
			return 0;
		return this.shareIds.split(",").length;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (obj instanceof Article) {
			Article tmp = (Article) obj;
			if(tmp.getId().equals(this.getId())){
				return true;
			}
			return false;
		}
		return super.equals(obj);
	}
    public static void main(String a[]){
        String text = "<p>Chỉ tr&iacute;ch l&agrave; v&ocirc; &iacute;ch (n&oacute; l&agrave;m cho kẻ bị chỉ tr&iacute;ch phải chống cự lại v&agrave; tự b&agrave;o chữa) m&agrave; c&ograve;n nguy hiểm, o&aacute;n th&ugrave; ...";
        String sc = text.replaceAll("\\<.*?\\>", "");
		sc = StringEscapeUtils.escapeHtml(sc);
		sc = sc.replaceAll("\"", "");
		if(sc.length() > 400){
			sc = sc.substring(0,400) + " ...";
		}
		System.out.println(sc);
    }

}
