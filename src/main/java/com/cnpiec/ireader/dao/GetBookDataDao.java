package com.cnpiec.ireader.dao;

import java.util.List;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.cnpiec.ireader.model.Book;
import com.cnpiec.ireader.model.BookInfo;
import com.cnpiec.ireader.model.Chapter;

public interface GetBookDataDao {
    public void batchInsertBook(List<Book> list);

    public void batchInsertChapter(List<Chapter> list);

    @Select("select bookId,name from book where  bookId = #{bookId}")
    public Book selectBookByBookId(String bookId);

    @Select("select bookId,name,status from book where  status = 0 ")
    public List<Book> selectBookByStatus();

    @Update("update book set status = 2 where bookId = #{bookId} ")
    public void updateBookStatus(String booId);
    
    @Select("select bookId,name from book")
    public List<Book> selectBooks();

    @Insert("insert into book_info values (#{bookId},#{name},#{author},#{brief},"
            + "#{category},#{categoryId},#{keywords},#{cover},#{completeStatus},"
            + "#{displayName},#{partnerName},#{price},#{startChargeChapter},"
            + "#{wordCount},#{createTime},#{updateTime},SYSDATE(),#{billPattern} ) ")
    public void inserBookInfo(BookInfo bookInfo);
}
