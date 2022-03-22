package com.example.demo.src.home;


import com.example.demo.config.BaseException;
import com.example.demo.src.home.model.*;
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
public class HomeProvider {

    private final HomeDao homeDao;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public HomeProvider(HomeDao homeDao) {
        this.homeDao = homeDao;

    }



    public GetHomeRes getHome(int addressIdx) throws BaseException {
        if (homeDao.checkAddressIdx(addressIdx) != 1) {
            throw new BaseException(INVALID_ADDRESSIDX);
        }
        if (homeDao.checkAddressStatus(addressIdx) != 1) {
            throw new BaseException(FAILED_TO_ADDRESS);
        }
        try {

            GetHomeRes getHome = homeDao.getHome(addressIdx);

            return getHome;


        } catch (Exception exception) {
        throw new BaseException(DATABASE_ERROR);
        }


    }


    public List<GetNewStoresCategoryRes> getNewStoresCategory(int addressIdx, int categoryIdx) throws BaseException {
        if (homeDao.checkAddressIdx(addressIdx) != 1) {
            throw new BaseException(INVALID_ADDRESSIDX);
        }
        if (homeDao.checkAddressStatus(addressIdx) != 1) {
            throw new BaseException(FAILED_TO_ADDRESS);
        }
        if (homeDao.checkCategoryIdx(categoryIdx) != 1) {
            throw new BaseException(INVALID_CATEGORYIDX);
        }
        if (homeDao.checkCategoryStatus(categoryIdx) != 1) {
            throw new BaseException(FAILED_TO_CATEGORY);
        }
        try {

            List<GetNewStoresCategoryRes> getNewStores = homeDao.getNewStoresCategory(addressIdx,categoryIdx);

            return getNewStores;

        } catch (Exception exception) {

        }       throw new BaseException(DATABASE_ERROR);
    }

    public List<GetStoresCategoryRes> getStoresCategory(int order, String isFast, int deliveryCost, int minimumAmount, int coupon, int addressIdx, int categoryIdx) throws BaseException {
        if (homeDao.checkAddressIdx(addressIdx) != 1) {
            throw new BaseException(INVALID_ADDRESSIDX);
        }
        if (homeDao.checkCategoryIdx(categoryIdx) != 1) {
            throw new BaseException(INVALID_CATEGORYIDX);
        }
        if (homeDao.checkCategoryStatus(categoryIdx) != 1) {
            throw new BaseException(FAILED_TO_CATEGORY);
        }
        if (homeDao.checkAddressStatus(addressIdx) != 1) {
            throw new BaseException(FAILED_TO_ADDRESS);
        }
        try {

            List<GetStoresCategoryRes> getStores = homeDao.getStoresCategory(order,isFast,deliveryCost,minimumAmount,coupon,addressIdx,categoryIdx);

            return getStores;

        } catch (Exception exception) {

        }       throw new BaseException(DATABASE_ERROR);
    }


    public List<GetStoresCategoryRes> getNewStoresCategory(int order, String isFast, int deliveryCost, int minimumAmount, int coupon, int addressIdx) throws BaseException {
        if (homeDao.checkAddressIdx(addressIdx) != 1) {
            throw new BaseException(INVALID_ADDRESSIDX);
        }
        if (homeDao.checkAddressStatus(addressIdx) != 1) {
            throw new BaseException(FAILED_TO_ADDRESS);
        }

        if (homeDao.checkAddressStatus(addressIdx) != 1) {
            throw new BaseException(FAILED_TO_ADDRESS);
        }
         try {

        List<GetStoresCategoryRes> getStores = homeDao.getNewStoresCategory(order,isFast,deliveryCost,minimumAmount,coupon,addressIdx);

        return getStores;

          } catch (Exception exception) {

         }       throw new BaseException(DATABASE_ERROR);
    }




    public List<GetStoresRes> getAroundStores(int order,String isFast,int deliveryCost,int minimumAmount,int coupon,int addressIdx) throws BaseException {
        if (homeDao.checkAddressIdx(addressIdx) != 1) {
            throw new BaseException(INVALID_ADDRESSIDX);
        }

        if (homeDao.checkAddressStatus(addressIdx) != 1) {
            throw new BaseException(FAILED_TO_ADDRESS);
        }
        try {

            List<GetStoresRes> getStores = homeDao.getAroundStores(order,isFast,deliveryCost,minimumAmount,coupon,addressIdx);

            return getStores;

        } catch (Exception exception) {

        }       throw new BaseException(DATABASE_ERROR);
    }

    public List<GetStoresCategoryRes> getHotFranchisesCategory(int order, String isFast, int deliveryCost, int minimumAmount, int coupon, int addressIdx) throws BaseException {
        if (homeDao.checkAddressIdx(addressIdx) != 1) {
            throw new BaseException(INVALID_ADDRESSIDX);
        }
        if (homeDao.checkAddressStatus(addressIdx) != 1) {
            throw new BaseException(FAILED_TO_ADDRESS);
        }

          try {

        List<GetStoresCategoryRes> getStores = homeDao.getHotFranchisesCategory(order,isFast,deliveryCost,minimumAmount,coupon,addressIdx);

        return getStores;

         } catch (Exception exception) {

         }       throw new BaseException(DATABASE_ERROR);
    }


    public List<GetStoresCategoryRes> getOnlyEatsList(int order, String isFast, int deliveryCost, int minimumAmount, int coupon, int addressIdx) throws BaseException {
        if (homeDao.checkAddressIdx(addressIdx) != 1) {
            throw new BaseException(INVALID_ADDRESSIDX);
        }

        if (homeDao.checkAddressStatus(addressIdx) != 1) {
            throw new BaseException(FAILED_TO_ADDRESS);
        }
          try {

        List<GetStoresCategoryRes> getStores = homeDao.getOnlyEatsList(order,isFast,deliveryCost,minimumAmount,coupon,addressIdx);

        return getStores;

          } catch (Exception exception) {

         }       throw new BaseException(DATABASE_ERROR);
    }

    public List<GetStoresCategoryRes> getIsSaleList(int order, String isFast, int deliveryCost, int minimumAmount, int coupon, int addressIdx) throws BaseException {
        if (homeDao.checkAddressIdx(addressIdx) != 1) {
            throw new BaseException(INVALID_ADDRESSIDX);
        }

        if (homeDao.checkAddressStatus(addressIdx) != 1) {
            throw new BaseException(FAILED_TO_ADDRESS);
        }
        try {

            List<GetStoresCategoryRes> getStores = homeDao.getIsSaleList(order,isFast,deliveryCost,minimumAmount,coupon,addressIdx);

            return getStores;

        } catch (Exception exception) {

        }       throw new BaseException(DATABASE_ERROR);
    }
}
