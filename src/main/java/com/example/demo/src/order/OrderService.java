package com.example.demo.src.order;


import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.secret.Secret;

import com.example.demo.src.order.*;

import com.example.demo.src.order.model.PatchOrderReq;
import com.example.demo.src.order.model.PatchOrderRes;

import com.example.demo.src.order.model.PostOrderReq;
import com.example.demo.src.order.model.PostOrderRes;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.example.demo.config.BaseResponseStatus.*;


// Service Create, Update, Delete 의 로직 처리
@Service
@Transactional
public class OrderService {


    private final OrderDao orderDao;
    private final OrderProvider orderProvider;
    private final JwtService jwtService;


    @Autowired
    public OrderService(OrderDao orderDao, OrderProvider orderProvider, JwtService jwtService) {
        this.orderDao = orderDao;
        this.orderProvider = orderProvider;
        this.jwtService = jwtService;

    }

    public PostOrderRes createOrder(int userIdx, PostOrderReq postOrderReq) throws BaseException {

        if (orderDao.checkAddressIdx(postOrderReq.getAddressIdx()) != 1) {

            throw new BaseException(INVALID_ADDRESSIDX);
        }
        if (orderDao.checkPaymentIdx(postOrderReq.getPaymentIdx()) != 1) {

            throw new BaseException(INVALID_PAYMENTIDX);
        }
        if (orderDao.checkAddressStatus(postOrderReq.getAddressIdx()) != 1) {

            throw new BaseException(FAILED_TO_ADDRESS);
        }
        if (orderDao.checkPaymentStatus(postOrderReq.getPaymentIdx()) != 1) {

            throw new BaseException(FAILED_TO_PAYMENT);
        }
        if(postOrderReq.getCouponIdx()!=0){
        if (orderDao.checkCouponAvailable(postOrderReq.getCouponIdx(),userIdx,postOrderReq.getPostOrderMenuReq().get(0).getMenuIdx()) != 1) {

            throw new BaseException(FAILED_TO_USECOUPON);
        }}
        for(int i=0;i<postOrderReq.getPostOrderMenuReq().size();i++) {
            if (orderDao.checkMenuIdx(postOrderReq.getPostOrderMenuReq().get(i).getMenuIdx()) != 1) {



                throw new BaseException(INVALID_MENUIDX);
            }
            if (orderDao.checkMenuStatus(postOrderReq.getPostOrderMenuReq().get(i).getMenuIdx()) != 1) {



                throw new BaseException(FAILED_TO_GETMENU);
            }
            for (int j = 0; j < postOrderReq.getPostOrderMenuReq().get(i).getPostOrderSideMenuReq().size(); j++) {
                if (orderDao.checkSubSideCategoryIdx(postOrderReq.getPostOrderMenuReq().get(i).getPostOrderSideMenuReq().get(j).getSubSideCategoryIdx()) != 1) {

                    throw new BaseException(INVALID_SUBSIDECATEGORYIDX);
                }
                if (orderDao.checkSubSideCategoryStatus(postOrderReq.getPostOrderMenuReq().get(i).getPostOrderSideMenuReq().get(j).getSubSideCategoryIdx()) != 1) {

                    throw new BaseException(FAILED_TO_GETMENU);
                }


            }
        }


       try {

                orderDao.createOrder(userIdx, postOrderReq);

                int orderIdx = orderDao.createOrder(userIdx, postOrderReq);


                return new PostOrderRes(orderIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
      }

    }

    public PatchOrderRes patchOrder(PatchOrderReq patchOrderReq) throws BaseException {

        if (orderDao.checkOrderStatus(patchOrderReq.getOrderIdx()) != 1) {
            throw new BaseException(FAILED_TO_PATCHORDER);
        }
        if (orderDao.checkOrderIdx(patchOrderReq.getOrderIdx()) != 1) {
            throw new BaseException(INVALID_ORDERIDX);
        }
        try {
            int orderIdx = orderDao.patchOrder(patchOrderReq);

            return new PatchOrderRes(orderIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }




}