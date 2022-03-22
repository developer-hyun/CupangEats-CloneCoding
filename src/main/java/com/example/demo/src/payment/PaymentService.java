package com.example.demo.src.payment;


import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


// Service Create, Update, Delete 의 로직 처리
@Service
@Transactional
public class PaymentService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PaymentDao paymentDao;
    private final PaymentProvider paymentProvider;
    private final JwtService jwtService;



    @Autowired
    public PaymentService(PaymentDao paymentDao, PaymentProvider paymentProvider, JwtService jwtService) {
        this.paymentDao = paymentDao;
        this.paymentProvider = paymentProvider;
        this.jwtService = jwtService;

    }




}

