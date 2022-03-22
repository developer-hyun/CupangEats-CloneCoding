package com.example.demo.src.search;


import com.example.demo.config.BaseException;

import com.example.demo.src.search.model.GetStoresRes;
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
public class SearchProvider {

    private final SearchDao searchDao;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public SearchProvider(SearchDao searchDao) {
        this.searchDao = searchDao;

    }


    public List<GetStoresRes> getStores(int order,String isFast,int deliveryCost,int minimumAmount,int coupon,int addressIdx,String keyword) throws BaseException {
        if (searchDao.checkAddressIdx(addressIdx) != 1) {
            throw new BaseException(INVALID_ADDRESSIDX);
        }
        if (searchDao.checkKeyword(keyword) != 1) {
            throw new BaseException(INVALID_KEYWORD);
        }
        try {

            List<GetStoresRes> getStores = searchDao.getStores(order,isFast,deliveryCost,minimumAmount,coupon,addressIdx,keyword);

            return getStores;

        } catch (Exception exception) {

        }       throw new BaseException(DATABASE_ERROR);
    }





}
