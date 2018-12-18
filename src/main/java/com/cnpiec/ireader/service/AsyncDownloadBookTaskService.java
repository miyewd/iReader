package com.cnpiec.ireader.service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.util.List;
import java.util.concurrent.Future;

import org.apache.http.HttpEntity;
import org.apache.http.StatusLine;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import com.alibaba.fastjson.JSONObject;
import com.cnpiec.ireader.dao.GetBookDataDao;
import com.cnpiec.ireader.model.Book;
import com.cnpiec.ireader.model.Chapter;
import com.cnpiec.ireader.utils.FileNameUtils;

@Service
// 线程执行任务类
public class AsyncDownloadBookTaskService {

    private Logger logger = LoggerFactory.getLogger(AsyncDownloadBookTaskService.class);

    @Autowired
    private GetBookDataDao getBookDataDao;

    @Async
    // 表明是异步方法
    // 无返回值
    public void executeAsyncTask(List<Book> list, String clientId, String clientSecret) {
        System.out.println(Thread.currentThread().getName() + "开启新线程执行");
        for (Book book : list) {
            String name = book.getName();
            File file = new File("iReaderResource/" + name);
            if (file.mkdirs()) {
                logger.info("文件夹创建成功！创建后的文件目录为：" + file.getPath());
            }
            try {
                getChapterList(book, clientId, clientSecret);
            } catch (Exception e) {
                logger.error("多线程下载书籍失败:" + book.getBookId(), e);
                e.printStackTrace();
            }
        }
    }

    /**
     * 异常调用返回Future
     *
     * @param i
     * @return
     * @throws InterruptedException
     */
    @Async
    public Future<String> asyncInvokeReturnFuture()
            throws InterruptedException {
        System.out.println("异步执行:"+Thread.currentThread().getName());
        Future<String> future = new AsyncResult<String>("success:");// Future接收返回值，这里是String类型，可以指明其他类型
        return future;
    }

    /**
     * 根据书籍ID获取章节创建文件并写入章节信息
     * 
     * @param book
     * @param clientId
     * @param clientSecret
     * @throws Exception
     */
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
                File file = new File("iReaderResource/" + book.getName() + "/"
                        + FileNameUtils.replaceSpecialCharacters(chapter.getTitle()) + ".txt");
                if (file.createNewFile()) {
                    logger.info("创建章节文件成功：" + file.getPath());
                }
                String filePath = file.getPath();
                getChapterInfo(chapter, book, clientId, clientSecret, filePath);

            }
            getBookDataDao.updateBookStatus(book.getBookId());
        }

    }

    /**
     * 获取章节信息写入文本文件
     * 
     * @param chapter
     * @param book
     * @param clientId
     * @param clientSecret
     * @param filePath
     * @throws Exception
     */
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
}