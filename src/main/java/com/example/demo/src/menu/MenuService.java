package com.example.demo.src.menu;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


// Service Create, Update, Delete 의 로직 처리
@Service
@Transactional
public class MenuService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final MenuDao menuDao;
    private final MenuProvider menuProvider;



    @Autowired
    public MenuService(MenuDao menuDao, MenuProvider menuProvider) {
        this.menuDao = menuDao;
        this.menuProvider = menuProvider;

    }




}

