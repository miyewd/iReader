package com.cnpiec.ireader.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cnpiec.ireader.model.Book;
import com.cnpiec.ireader.model.Result;
import com.cnpiec.ireader.service.AsyncDownloadBookTaskService;
import com.cnpiec.ireader.service.GetBookDataService;
import com.cnpiec.ireader.utils.ListUtils;

@Controller
public class GetBookDataController {
    private Logger logger = LoggerFactory.getLogger(GetBookDataController.class);
    @Autowired
    private GetBookDataService getDataServiceImpl;
    
    @Autowired
    private AsyncDownloadBookTaskService asyncTaskService;

    /**
     * 保存单本书籍至本地
     * @param clientId
     * @param clientSecret
     * @param bookId
     * @param key
     * @return
     */
    @RequestMapping("/getBookSingle")
    @ResponseBody
    public Result getBookSingle(String clientId, String clientSecret, String bookId, String key) {
        try {
            if ("67209774".equals(key)) {
                getDataServiceImpl.getBookDataSingle(clientId, clientSecret, bookId);
            } else {
                Result result = new Result("404", "保存单本书籍至本地接口不存在,请联系管理员!");
                return result;
            }
        } catch (Exception e) {
            logger.error("异常:", e);
            Result result = new Result("500", "保存单本书籍至本地接口异常,请稍后重试或联系管理员!");
            return result;
        }
        Result result = new Result("200", "保存单本书籍至本地接口执行成功!");
        return result;
    }

   /**
    * 执行下载  数据库中未下载的书籍 至本地
    * @param clientId
    * @param clientSecret
    * @param key
    * @return
    */
    @RequestMapping("/getBookByStatus")
    @ResponseBody
    public Result getBookByStatus(String clientId, String clientSecret, String key) {
        try {
            if ("67209774".equals(key)) {
                List<Book> list = getDataServiceImpl.selectBookByStatus();
                getDataServiceImpl.getBookDataByStatus(list, clientId, clientSecret);
            } else {
                Result result = new Result("404", "保存未处理的书籍至本地接口不存在,请联系管理员!");
                return result;
            }
        } catch (Exception e) {
            logger.error("异常:", e);
            Result result = new Result("500", "保存未处理的书籍至本地接口异常,请稍后重试或联系管理员!");
            return result;
        }
        Result result = new Result("200", "保存未处理的书籍至本地接口执行成功!");
        return result;
    }


    /**
     * 保存书籍列表至数据库            
     * @param clientId
     * @param clientSecret
     * @param key
     * @return
     */
    @RequestMapping("/insertBook")
    @ResponseBody
    public Result insertBook(String clientId, String clientSecret, String key) {
        try {
            if ("67209774".equals(key)) {
                getDataServiceImpl.insertBook(clientId, clientSecret);
            } else {
                Result result = new Result("404", "接口不存在,请联系管理员!");
                return result;
            }
        } catch (Exception e) {
            logger.error("异常:", e);
            Result result = new Result("500", "拉取所有的书籍和章节列表存储至库异常,请稍后重试或联系管理员!");
            return result;
        }
        Result result = new Result("200", "拉取所有的书籍和章节列表存储至库执行成功!");
        return result;
    }
    
    
    /**
     * 保存书籍详细信息至数据库            
     * @param clientId
     * @param clientSecret
     * @param key
     * @return
     */
    @RequestMapping("/insertBookInfo")
    @ResponseBody
    public Result insertBookInfo(String clientId, String clientSecret, String key) {
        try {
            if ("67209774".equals(key)) {
                getDataServiceImpl.insertBookInfo(clientId, clientSecret);
            } else {
                Result result = new Result("404", "保存书籍详细信息至数据库     ,请联系管理员!");
                return result;
            }
        } catch (Exception e) {
            logger.error("异常:", e);
            Result result = new Result("500", "保存书籍详细信息至数据库     异常,请稍后重试或联系管理员!");
            return result;
        }
        Result result = new Result("200", "保存书籍详细信息至数据库     执行成功!");
        return result;
    }

    /**
     * 多线程执行下载  数据库中未下载的书籍 至本地
     * @param clientId
     * @param clientSecret
     * @param key
     * @return
     */
    @RequestMapping("/batchDownladBook")
    @ResponseBody
    public Result batchDownladBook(String clientId, String clientSecret, String key) {
        try {
            if ("67209774".equals(key)) {
                List<Book> list = getDataServiceImpl.selectBookByStatus();
                List<List<Book>> list2 = ListUtils.averageAssign(list, 10);
                for (int i = 0; i < list2.size(); i++) {
                    asyncTaskService.executeAsyncTask(list2.get(i),clientId,clientSecret);
                }
            } else {
                Result result = new Result("404", "接口不存在,请联系管理员!");
                return result;
            }
        } catch (Exception e) {
            logger.error("异常:", e);
            Result result = new Result("500", "异常,请稍后重试或联系管理员!");
            return result;
        }
        Result result = new Result("200", "库执行成功!");
        return result;
    }
    
    /**
     * 多线程测试
     * @return
     * @throws InterruptedException
     */
    @RequestMapping("/test")
    @ResponseBody
    public Result test() throws InterruptedException {
        for (int i = 0; i < 100; i++) {
            asyncTaskService.asyncInvokeReturnFuture();
        }
        Result result = new Result("200", "库执行成功!");
        return result;
    }
    
    
}
