package com.example.demo.src.store;


import com.example.demo.config.BaseException;
import com.example.demo.src.store.model.*;
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
public class StoreProvider {

    private final StoreDao storeDao;



    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public StoreProvider(StoreDao storeDao) {
        this.storeDao = storeDao;

    }


    public GetStoreRes getStore(int userIdx,int storeIdx) throws BaseException {
        if (storeDao.checkStoreIdx(storeIdx) != 1) {
            throw new BaseException(INVALID_STOREIDX);
        }

        if (storeDao.checkStoreStatus(storeIdx) != 1) {
            throw new BaseException(FAILED_TO_GETSTORE);
        }
       try {
          if(userIdx==0){
               GetStoreRes getStoreRes = storeDao.getStore(storeIdx);

               return getStoreRes;

           }
           else{GetStoreRes getStoreRes = storeDao.getStore(userIdx, storeIdx);

               return getStoreRes;


           }


     } catch (Exception exception) {
        throw new BaseException(DATABASE_ERROR);
      }
   }
    public GetStoreDetailRes getStoreDetail(int storeIdx) throws BaseException {

        if (storeDao.checkStoreIdx(storeIdx) != 1) {
            throw new BaseException(INVALID_STOREIDX);
        }
        if (storeDao.checkStoreStatus(storeIdx) != 1) {
            throw new BaseException(FAILED_TO_GETSTORE);
        }
       try {
            GetStoreDetailRes getStoreDetailRes = storeDao.getStoreDetail(storeIdx);

            return getStoreDetailRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    public List<GetPhotoReviewsRes> getPhotoReviews(int storeIdx) throws BaseException {

        if (storeDao.checkStoreIdx(storeIdx) != 1) {
            throw new BaseException(INVALID_STOREIDX);
        }
        if (storeDao.checkStoreStatus(storeIdx) != 1) {
            throw new BaseException(FAILED_TO_GETSTORE);
        }
        try {
            List<GetPhotoReviewsRes> getPhotoReviewsRes = storeDao.getPhotoReviews(storeIdx);

            return getPhotoReviewsRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    public List<GetStoreCategorysRes> getStoreCategorys(int storeIdx) throws BaseException {

        if (storeDao.checkStoreIdx(storeIdx) != 1) {
            throw new BaseException(INVALID_STOREIDX);
        }
        if (storeDao.checkStoreStatus(storeIdx) != 1) {
            throw new BaseException(FAILED_TO_GETSTORE);
        }
        try {
            List<GetStoreCategorysRes> getStoreCategorysRes = storeDao.getStoreCategorys(storeIdx);

            return getStoreCategorysRes;
        } catch (Exception exception) {
          throw new BaseException(DATABASE_ERROR);
        }
    }


    public List<GetStoreDeliveryCostRes> getStoreDeliveryCost(int storeIdx) throws BaseException {

        if (storeDao.checkStoreIdx(storeIdx) != 1) {
            throw new BaseException(INVALID_STOREIDX);
        }
        if (storeDao.checkStoreStatus(storeIdx) != 1) {
            throw new BaseException(FAILED_TO_GETSTORE);
        }
        try {
            List<GetStoreDeliveryCostRes> getStoreDeliveryCost = storeDao.getStoreDeliveryCost(storeIdx);

            return getStoreDeliveryCost;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}
