package com.example.demo.src.payment;


import com.example.demo.config.BaseException;
import com.example.demo.src.payment.model.GetPaymentRes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.DATABASE_ERROR;

//Provider : Read의 비즈니스 로직 처리
@Service
@Transactional
public class PaymentProvider {

    private final PaymentDao paymentDao;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public PaymentProvider(PaymentDao paymentDao) {
        this.paymentDao = paymentDao;

    }


    public List<GetPaymentRes> getEvents() throws BaseException {

           try {

            List<GetPaymentRes> getEvents = paymentDao.getEvents();

           return getEvents;


        } catch (Exception exception) {
           throw new BaseException(DATABASE_ERROR);
       }


    }




}
