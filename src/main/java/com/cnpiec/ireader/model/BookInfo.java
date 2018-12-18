package com.cnpiec.ireader.model;

import java.math.BigDecimal;
import java.util.Date;

public class BookInfo {
    private String bookId;
    private String name;
    private String author;
    private String brief;
    private String category;
    private String categoryId;
    private String keywords;
    private String cover;
    private String completeStatus;
    private String displayName;
    private String partnerName;
    private BigDecimal price;
    private String startChargeChapter;
    private String wordCount;
    private Date createTime;
    private Date updateTime;
    private String time;
    private String billPattern;
    public String getBookId() {
        return bookId;
    }
    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAuthor() {
        return author;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public String getBrief() {
        return brief;
    }
    public void setBrief(String brief) {
        this.brief = brief;
    }
    public String getCategory() {
        return category;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public String getCategoryId() {
        return categoryId;
    }
    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }
    public String getKeywords() {
        return keywords;
    }
    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
    public String getCover() {
        return cover;
    }
    public void setCover(String cover) {
        this.cover = cover;
    }
    public String getCompleteStatus() {
        return completeStatus;
    }
    public void setCompleteStatus(String completeStatus) {
        this.completeStatus = completeStatus;
    }
    public String getDisplayName() {
        return displayName;
    }
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }
    public String getPartnerName() {
        return partnerName;
    }
    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public String getStartChargeChapter() {
        return startChargeChapter;
    }
    public void setStartChargeChapter(String startChargeChapter) {
        this.startChargeChapter = startChargeChapter;
    }
    public String getWordCount() {
        return wordCount;
    }
    public void setWordCount(String wordCount) {
        this.wordCount = wordCount;
    }
    public Date getCreateTime() {
        return createTime;
    }
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
    public Date getUpdateTime() {
        return updateTime;
    }
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }
    public String getBillPattern() {
        return billPattern;
    }
    public void setBillPattern(String billPattern) {
        this.billPattern = billPattern;
    }
    
}
