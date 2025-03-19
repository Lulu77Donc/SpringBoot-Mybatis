package com.ljx.service.Impl;

import com.ljx.service.BookService;
import org.springframework.stereotype.Service;

@Service

public class BookServiceImpl implements BookService {
    @Override
    public void save() {
        System.out.println("book service is running...");
    }
}
