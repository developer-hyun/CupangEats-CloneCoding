package com.example.demo.src.menu;


import com.example.demo.config.BaseException;
import com.example.demo.src.menu.model.GetMenuRes;
import com.example.demo.src.menu.model.GetSideCategoryRes;
import com.example.demo.src.store.model.GetStoreDetailRes;
import com.example.demo.src.store.model.GetStoreRes;
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
public class MenuProvider {

    private final MenuDao menuDao;



    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public MenuProvider(MenuDao menuDao) {
        this.menuDao = menuDao;

    }

//메뉴의 사이드 메뉴
    public List<GetSideCategoryRes> getSideCategory(int menuIdx) throws BaseException {
        if (menuDao.checkMenuIdx(menuIdx) != 1) {

            throw new BaseException(INVALID_MENUIDX);
        }
        if (menuDao.checkMenuStatus(menuIdx) != 1) {
            throw new BaseException(FAILED_TO_GETMENU);
        }

       try {

          List<GetSideCategoryRes> getSideCategory = menuDao.getSideCategory( menuIdx);

               return getSideCategory;





     } catch (Exception exception) {
        throw new BaseException(DATABASE_ERROR);
      }
   }
   //메뉴의 정보
    public GetMenuRes getMenu(int menuIdx) throws BaseException {
        if (menuDao.checkMenuIdx(menuIdx) != 1) {
            throw new BaseException(INVALID_MENUIDX);
        }
        if (menuDao.checkMenuStatus(menuIdx) != 1) {
            throw new BaseException(FAILED_TO_GETMENU);
        }

        try {

            GetMenuRes getMenu = menuDao.getMenu( menuIdx);

            return getMenu;





        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }


}
