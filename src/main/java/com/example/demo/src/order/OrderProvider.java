package com.example.demo.src.order;


import com.example.demo.config.BaseException;

import com.example.demo.src.order.model.*;

import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리, select
@Service
@Transactional
public class OrderProvider {

    private final OrderDao orderDao;
    private final JwtService jwtService;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public OrderProvider(OrderDao orderDao,JwtService jwtService) {
        this.orderDao = orderDao;
        this.jwtService=jwtService;
    }


    public GetOrderRes getOrder(int userIdx, int orderIdx) throws BaseException {
        if (orderDao.checkOrderIdx(orderIdx) != 1) {
            throw new BaseException(INVALID_ORDERIDX);
        }


        try {
            if(userIdx==0){
                GetOrderRes getOrderRes = orderDao.getOrder(userIdx,orderIdx);

                return getOrderRes;

            }
            else{GetOrderRes getOrderRes = orderDao.getOrder(userIdx, orderIdx);

                return getOrderRes;


            }


       } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetOrdersRes> getOrders(int userIdx) throws BaseException {

       try {

            List<GetOrdersRes> getOrdersRes = orderDao.getOrders(userIdx);

                return getOrdersRes;
                 } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


    public GetReviewCountRes getReviewCount(int userIdx) throws BaseException {

           try {

        GetReviewCountRes getReviewCount = orderDao.getReviewCount(userIdx);

        return getReviewCount;

          } catch (Exception exception) {
              throw new BaseException(DATABASE_ERROR);
          }
    }

    public List<GetOrdersPreparingRes> getOrdersPreparing(int userIdx) throws BaseException {

           try {

        List<GetOrdersPreparingRes> getOrdersPreparing = orderDao.getOrdersPreparing(userIdx);

        return getOrdersPreparing;

          } catch (Exception exception) {
              throw new BaseException(DATABASE_ERROR);
          }
    }


    public GetOrderDeliveryRes getOrderDelivery(int orderIdx) throws BaseException {
        if (orderDao.checkOrderIdx(orderIdx) != 1) {
            throw new BaseException(INVALID_ORDERIDX);
        }
        if (orderDao.checkOrderStatus(orderIdx) != 1) {
            throw new BaseException(FAILED_TO_PATCHORDER);
        }
        if (orderDao.checkDeliveryStatus(orderIdx) != 1) {
            throw new BaseException(FAILED_TO_GETDELIVERY);
        }
           try {

        GetOrderDeliveryRes getOrderDelivery = orderDao.getOrderDelivery(orderIdx);

        return getOrderDelivery;

         } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
          }
    }
}