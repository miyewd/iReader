package com.cnpiec.ireader.model;

import java.util.Date;

public class Chapter {
	private String chapterOrder;//章节序号
	private String title;//章节名称
	private String free;//是否收费(0收费     1免费章节)
	private String chapterId;//章节ID
	private String content;//章节内容
	private String bookId;//书籍ID
	private Date createTime;
	private Date updateTime;
	
	
	public String getBookId() {
		return bookId;
	}
	public void setBookId(String bookId) {
		this.bookId = bookId;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getChapterOrder() {
		return chapterOrder;
	}
	public void setChapterOrder(String chapterOrder) {
		this.chapterOrder = chapterOrder;
	}
	public Date getUpdateTime() {
		return updateTime;
	}
	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getFree() {
		return free;
	}
	public void setFree(String free) {
		this.free = free;
	}
	public String getChapterId() {
		return chapterId;
	}
	public void setChapterId(String chapterId) {
		this.chapterId = chapterId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	
}
