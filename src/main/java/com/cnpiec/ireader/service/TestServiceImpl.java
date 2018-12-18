package com.cnpiec.ireader.service;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cnpiec.ireader.dao.GetBookDataDao;
import com.cnpiec.ireader.model.Book;

@Service
public class TestServiceImpl implements TestService {

    @Autowired
    private GetBookDataDao getBookDataDao;

    @Override
    @Transactional
    public void addPojo() {
        Book book = new Book();
        book.setBookId("1");
        book.setName("测试1");
        book.setPartnerName("测试P1");
        ArrayList<Book> list = new ArrayList<>();
        list.add(book);
        getBookDataDao.batchInsertBook(list);

        Book book2 = new Book();
        book2.setBookId("2");
        book2.setName("测试2");
        book2.setPartnerName("测试P2");
        ArrayList<Book> list2 = new ArrayList<>();
        list2.add(book2);
        getBookDataDao.batchInsertBook(list2);
    };
}
