package com.cnpiec.ireader.service;

import java.io.IOException;
import java.util.List;

import org.apache.http.client.ClientProtocolException;

import com.cnpiec.ireader.model.Book;

public interface GetBookDataService {

    /**
     * 拉取所有的书籍和章节列表存储至库
     * 
     * @param clientId
     * @param clientSecret
     * @throws ClientProtocolException
     * @throws IOException
     */
    public void insertBook(String clientId, String clientSecret) throws ClientProtocolException, IOException;
    

    /**
     * 保存书籍详细信息至数据库
     * @param clientId
     * @param clientSecret
     * @throws IOException 
     * @throws ClientProtocolException 
     */
    public void insertBookInfo(String clientId, String clientSecret) throws ClientProtocolException, IOException;


    /**
     * 下载单本书籍至本地
     * 
     * @param clientId
     * @param clientSecret
     * @param bookId
     * @throws Exception
     */
    public void getBookDataSingle(String clientId, String clientSecret, String bookId) throws Exception;

    /**
     * 下载未处理的书籍至本地
     * 
     * @param list
     * @param clientId
     * @param clientSecret
     * @throws Exception
     */
    public void getBookDataByStatus(List<Book> list, String clientId, String clientSecret) throws Exception;

    /**
     * 查询所有未处理的书籍
     * 
     * @return
     */
    public List<Book> selectBookByStatus();

}
