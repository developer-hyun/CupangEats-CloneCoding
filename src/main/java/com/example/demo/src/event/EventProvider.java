package com.example.demo.src.event;


import com.example.demo.config.BaseException;
import com.example.demo.src.event.model.GetEventsRes;
import com.example.demo.src.home.model.GetHotFranchisesRes;
import com.example.demo.src.home.model.GetNewStoresRes;
import com.example.demo.src.home.model.GetStoresRes;
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
public class EventProvider {

    private final EventDao eventDao;


    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public EventProvider(EventDao eventDao) {
        this.eventDao = eventDao;

    }


    public List<GetEventsRes> getEvents() throws BaseException {

           try {

            List<GetEventsRes> getEvents = eventDao.getEvents();

           return getEvents;


        } catch (Exception exception) {
           throw new BaseException(DATABASE_ERROR);
       }


    }




}
