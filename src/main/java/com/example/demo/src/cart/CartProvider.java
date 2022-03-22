package com.example.demo.src.cart;


import com.example.demo.config.BaseException;
import com.example.demo.src.cart.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
@Transactional
public class CartProvider {

    private final CartDao cartDao;



    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public CartProvider(CartDao cartDao) {
        this.cartDao = cartDao;

    }


    public GetCouponCountRes getCouponCount(int price,int userIdx, int storeIdx) throws BaseException {
        if (cartDao.checkStoreIdx(storeIdx) != 1) {
            throw new BaseException(INVALID_STOREIDX);
        }

        if (cartDao.checkStoreStatus(storeIdx) != 1) {
            throw new BaseException(FAILED_TO_GETSTORE);
        }
       try {


              GetCouponCountRes getCouponCountRes = cartDao.getCouponCount(price,userIdx, storeIdx);

               return getCouponCountRes;


     } catch (Exception exception) {
        throw new BaseException(DATABASE_ERROR);
      }
   }
    public List<GetCouponsRes> getCoupons(int price,int userIdx, int storeIdx) throws BaseException {
        if (cartDao.checkStoreIdx(storeIdx) != 1) {
            throw new BaseException(INVALID_STOREIDX);
        }

        if (cartDao.checkStoreStatus(storeIdx) != 1) {
            throw new BaseException(FAILED_TO_GETSTORE);
        }
           try {


        List<GetCouponsRes> getCoupons = cartDao.getCoupons(price,userIdx, storeIdx);

        return getCoupons;


          } catch (Exception exception) {
              throw new BaseException(DATABASE_ERROR);
           }
    }
}
