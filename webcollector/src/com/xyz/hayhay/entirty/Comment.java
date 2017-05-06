package com.xyz.hayhay.entirty;

import java.sql.Date;

public class Comment {
	long id;
	long articleId;
	String content;
	Date createDate;
	String createdBy;
	String commentIds;
	String likeIds;
	String replyCommentIds;
	
	/**
	 * @return the replyCommentIds
	 */
	public String getReplyCommentIds() {
		return replyCommentIds;
	}
	/**
	 * @param replyCommentIds the replyCommentIds to set
	 */
	public void setReplyCommentIds(String replyCommentIds) {
		this.replyCommentIds = replyCommentIds;
	}
	public Comment() {
	}
	public Comment(long id,String content,Date createDate,String createdBy){
		this.id = id;
		this.content = content;
		this.createDate = createDate;
		this.createdBy = createdBy;
	}
	/**
	 * @return the itemId
	 */
	public long getItemId() {
		return articleId;
	}
	/**
	 * @param itemId the itemId to set
	 */
	public void setItemId(long itemId) {
		this.articleId = itemId;
	}
	/**
	 * @return the id
	 */
	public long getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(long id) {
		this.id = id;
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
}
