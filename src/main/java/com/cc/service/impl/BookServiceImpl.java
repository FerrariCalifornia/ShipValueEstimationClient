package com.cc.service.impl;

import com.cc.dao.BookMapper;
import com.cc.pojo.Book;
import com.cc.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by cc on 2017/2/27.
 */
@Service("bookService")
public class BookServiceImpl implements BookService {

    @Autowired
    private BookMapper bookMapper;

    @Override
    public Book findAll() {
        return bookMapper.selectByPrimaryKey(1);
    }
}
