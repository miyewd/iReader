package com.cnpiec.ireader.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.JSONObject;
import com.cnpiec.ireader.dao.GetBookDataDao;
import com.cnpiec.ireader.model.Book;
import com.cnpiec.ireader.model.BookInfo;
import com.cnpiec.ireader.model.Chapter;
import com.cnpiec.ireader.utils.FileNameUtils;
import com.cnpiec.ireader.utils.ListUtils;

@Service
public class GetBookDataServiceImpl implements GetBookDataService {
    private Logger logger = LoggerFactory.getLogger(GetBookDataServiceImpl.class);

    @Autowired
    private GetBookDataDao getBookDataDao;

    @Override
    public void getBookDataSingle(String clientId, String clientSecret, String bookId) throws Exception {
        Book book = getBookDataDao.selectBookByBookId(bookId);
        String name = book.getName();
        File file = new File("iReaderResource/" + name);
        if (file.mkdirs()) {
            logger.info("文件夹创建成功！创建后的文件目录为：" + file.getPath());
        }
        getChapterList(book, clientId, clientSecret);
    }



    private void getChapterList(Book book, String clientId, String clientSecret) throws Exception {
        String stempSign = clientId + clientSecret + book.getBookId();
        String sign = DigestUtils.md5DigestAsHex(stempSign.getBytes());
        CloseableHttpClient httpclient = HttpClients.createDefault();
        StringBuffer sb = new StringBuffer();
        sb.append("http://api.res.ireader.com/api/v2/book/chapterList?").append("bookId=").append(book.getBookId())
                .append("&clientId=").append(clientId).append("&sign=").append(sign).append("&resType=json");
        HttpGet httpget = new HttpGet(sb.toString());
        CloseableHttpResponse response = httpclient.execute(httpget);

        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
            List<Chapter> chapterList = JSONObject.parseArray(result, Chapter.class);
            for (Chapter chapter : chapterList) {
                File file = new File("iReaderResource/" + book.getName() + "/" + FileNameUtils.replaceSpecialCharacters(chapter.getTitle()) + ".txt");
                if (file.createNewFile()) {
                    logger.info("创建章节文件成功：" + file.getPath());
                }
                String filePath = file.getPath();
                getChapterInfo(chapter, book, clientId, clientSecret, filePath);

            }
            getBookDataDao.updateBookStatus(book.getBookId());
        }

    }

    private void getChapterInfo(Chapter chapter, Book book, String clientId, String clientSecret, String filePath)
            throws Exception {
        String stempSign = clientId + clientSecret + book.getBookId() + chapter.getChapterId();
        String sign = DigestUtils.md5DigestAsHex(stempSign.getBytes());
        CloseableHttpClient httpclient = HttpClients.createDefault();
        StringBuffer sb = new StringBuffer();
        sb.append("http://api.res.ireader.com/api/v2/book/chapterInfo?").append("bookId=").append(book.getBookId())
                .append("&chapterId=").append(chapter.getChapterId()).append("&clientId=").append(clientId)
                .append("&sign=").append(sign).append("&resType=json");
        HttpGet httpget = new HttpGet(sb.toString());
        CloseableHttpResponse response = httpclient.execute(httpget);

        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() == 200) {
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
            Chapter chapter2 = JSONObject.parseObject(result, Chapter.class);
            String content = chapter2.getContent();
            // 写文件内容
            BufferedWriter writer = new BufferedWriter(new FileWriter(new File(filePath), true));
            writer.write(content);
            writer.close();
        }
    }

    @Override
    public void insertBook(String clientId, String clientSecret) throws ClientProtocolException, IOException {

        String stempSign = clientId + clientSecret;
        String sign = DigestUtils.md5DigestAsHex(stempSign.getBytes());
        CloseableHttpClient httpclient = HttpClients.createDefault();
        HttpGet httpget = new HttpGet("http://api.res.ireader.com/api/v2/book/bookList?clientId=" + clientId + "&sign="
                + sign + "&resType=json");
        CloseableHttpResponse response = httpclient.execute(httpget);

        StatusLine statusLine = response.getStatusLine();
        if (statusLine.getStatusCode() == 200) {
            // 保存书籍列表
            HttpEntity entity = response.getEntity();
            String result = EntityUtils.toString(entity, "utf-8");
            List<Book> bookList = JSONObject.parseArray(result, Book.class);
            logger.info(bookList.toString());
            List<List<Book>> splitList = ListUtils.splitList(bookList, 1000);
            for (List<Book> list : splitList) {
                getBookDataDao.batchInsertBook(list);
            }

            // 保存章节列表
            List<Chapter> chapterList = new ArrayList<>();
            for (Book book : bookList) {
                String stempChapterList = clientId + clientSecret + book.getBookId();
                String signChapterList = DigestUtils.md5DigestAsHex(stempChapterList.getBytes());
                CloseableHttpClient httpclient1 = HttpClients.createDefault();
                StringBuffer sb = new StringBuffer();
                sb.append("http://api.res.ireader.com/api/v2/book/chapterList?").append("bookId=")
                        .append(book.getBookId()).append("&clientId=").append(clientId).append("&sign=")
                        .append(signChapterList).append("&resType=json");
                HttpGet httpget1 = new HttpGet(sb.toString());
                CloseableHttpResponse response1 = httpclient1.execute(httpget1);

                StatusLine statusLine1 = response1.getStatusLine();
                if (statusLine1.getStatusCode() == 200) {
                    HttpEntity entity1 = response1.getEntity();
                    String result1 = EntityUtils.toString(entity1, "utf-8");
                    List<Chapter> chapterList1 = JSONObject.parseArray(result1, Chapter.class);
                    chapterList.addAll(chapterList1);
                    for (Chapter chapter : chapterList1) {
                        chapter.setBookId(book.getBookId());
                    }
                }
            }

            List<List<Chapter>> splitList1 = ListUtils.splitList(chapterList, 1000);
            for (List<Chapter> list : splitList1) {
                getBookDataDao.batchInsertChapter(list);
            }
        }
    }

    @Override
    public List<Book> selectBookByStatus() {
        return getBookDataDao.selectBookByStatus();
    }

    @Override
    public void getBookDataByStatus(List<Book> list, String clientId, String clientSecret) throws Exception {
        for (Book book : list) {
            String name = book.getName();
            File file = new File("iReaderResource/" + name);
            if (file.mkdirs()) {
                logger.info("文件夹创建成功！创建后的文件目录为：" + file.getPath());
            }
            getChapterList(book, clientId, clientSecret);
        }

    }



    @Override
    public void insertBookInfo(String clientId, String clientSecret) throws ClientProtocolException, IOException {
       List<Book> list = getBookDataDao.selectBooks();
        for (Book book : list) {
            String bookId = book.getBookId();
            String stempSign = clientId + clientSecret + bookId;
            String sign = DigestUtils.md5DigestAsHex(stempSign.getBytes());
            CloseableHttpClient httpclient = HttpClients.createDefault();
            StringBuffer sb = new StringBuffer();
            sb.append("http://api.res.ireader.com/api/v2/book/bookInfo?").append("bookId=").append(book.getBookId())
                    .append("&clientId=").append(clientId)
                    .append("&sign=").append(sign).append("&resType=json");
            HttpGet httpget = new HttpGet(sb.toString());
            CloseableHttpResponse response = httpclient.execute(httpget);
            
            StatusLine statusLine = response.getStatusLine();
            if (statusLine.getStatusCode() == 200) {
                HttpEntity entity = response.getEntity();
                String result = EntityUtils.toString(entity, "utf-8");
                BookInfo bookInfo = JSONObject.parseObject(result, BookInfo.class);
                getBookDataDao.inserBookInfo(bookInfo);
            }
        }
    }

}
