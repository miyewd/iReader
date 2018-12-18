package com.cnpiec.ireader.model;

public class Book {
    private String partnerName;
    private String name;// 书籍名称
    private String bookId;// 书籍ID
    private String status;// 0未下载 1开始下载 2下载完成 3下载错误

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

}
