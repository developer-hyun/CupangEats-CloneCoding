package com.example.demo.src.review;

import com.example.demo.config.BaseException;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.review.model.DeleteReviewRes;
import com.example.demo.src.review.model.GetReviewStatusHelp;
import com.example.demo.src.review.model.PostReviewReq;
import com.example.demo.src.review.model.PostReviewRes;
import com.example.demo.src.store.model.GetStoreDetailRes;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import java.util.ArrayList;
import javax.sql.DataSource;

import java.awt.*;
import java.util.ArrayList;

import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.config.BaseResponseStatus.DELETE_USERS_EMPTY_USERIDX;
//import static com.example.demo.config.BaseResponseStatus.POST_USERS_EXISTS_EMAIL;

// Service Create, Update, Delete 의 로직 처리
@Service
public class ReviewService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ReviewProvider reviewProvider;
    private final ReviewDao reviewDao;
    private final JwtService jwtService;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public ReviewService(ReviewProvider reviewProvider, ReviewDao reviewDao, JwtService jwtService) {
        this.reviewProvider = reviewProvider;
        this.reviewDao = reviewDao;
        this.jwtService = jwtService;

    }

    //리뷰 생성
    public PostReviewRes postReview(int orderIdx, int menuIdx, PostReviewReq postReviewReq) throws BaseException {
        if (reviewDao.checkOrderIdx(orderIdx) == 0) {
            throw new BaseException(FAILED_TO_GETORDERIDX);
        }

        try {

            int reviewIdx = reviewDao.postReview(orderIdx, menuIdx, postReviewReq);
            PostReviewRes postReviewRes = new PostReviewRes(reviewIdx);
            return postReviewRes;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    //리뷰 수정
    public PostReviewRes patchReview(int orderIdx, int menuIdx, PostReviewReq postReviewReq) throws BaseException {
        if (reviewDao.checkOrderIdx(orderIdx) == 0) {
            throw new BaseException(FAILED_TO_GETORDERIDX);
        }
        if (reviewDao.checkReviewIdx(orderIdx) == 0) {
            throw new BaseException(FAILED_TO_GETREVIEWIDX);
        }

        try {

            int reviewIdx = reviewDao.patchReview(orderIdx, menuIdx, postReviewReq);
            PostReviewRes postReviewRes = new PostReviewRes(reviewIdx);
            return postReviewRes;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    //리뷰 삭제
    public DeleteReviewRes deleteReview(int orderIdx) throws BaseException {
        if (reviewDao.checkOrderIdx(orderIdx) == 0) {
            throw new BaseException(FAILED_TO_GETORDERIDX);
        }
        if (reviewDao.checkReviewIdx(orderIdx) == 0) {
            throw new BaseException(FAILED_TO_GETREVIEWIDX);
        }
        try {
            int reviewIdx = reviewDao.deleteReview(orderIdx);
            DeleteReviewRes deleteReviewRes = new DeleteReviewRes(orderIdx);
            return deleteReviewRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //리뷰 조회 화면에서 돼요 안돼요 누르는 부분
    public GetReviewStatusHelp getReviewStatusHelp(int userIdx, int reviewIdx, String currnetStatus, String pushStatus) throws BaseException {
//            if (reviewDao.checkReviewIdxSideReviewLike(userIdx,reviewIdx) == 0) {
//                throw new BaseException(FAILED_TO_GETREVIEWIDX);
//            }
        //    System.out.println(currnetStatus);
        //  System.out.println(reviewDao.compareStatusHelp(userIdx, reviewIdx));
//        if (!(currnetStatus.equals(reviewDao.compareStatusHelp(userIdx,reviewIdx)))){
//            throw new BaseException(NOT_EQUAL_REVIEWLIKEDSTATUS);
//        }
        try {
//            if (currnetStatus.equals(reviewDao.compareStatusHelp(userIdx, reviewIdx))) {
                if (reviewDao.checkReviewHelp(userIdx, reviewIdx) == 0) {  //테이블이 존재하지 않고
                    if (currnetStatus.equals("ZERO")) {   //존재하지않으니까 아무것도 안눌려있을거고
                        if (pushStatus.equals("YES")) {   //그중 YES를 눌렀다면
                            reviewDao.fristCreateReviewHelpPushYes(userIdx, reviewIdx); //yes실행
                            GetReviewStatusHelp getReviewStatusHelp = reviewDao.getReviewStatusHelp(userIdx, reviewIdx);
                            return getReviewStatusHelp;
                        }
                        if (pushStatus.equals("NO")) {
                            reviewDao.fristCreateReviewHelpPushNo(userIdx, reviewIdx); //no 실행
                            GetReviewStatusHelp getReviewStatusHelp = reviewDao.getReviewStatusHelp(userIdx, reviewIdx);
                            return getReviewStatusHelp;
                        }
                    }
                } else if (reviewDao.checkReviewHelp(userIdx, reviewIdx) == 1) { //테이블 존재
                    if (!(currnetStatus.equals(reviewDao.compareStatusHelp(userIdx, reviewIdx)))) {
                        throw new BaseException(NOT_EQUAL_REVIEWLIKEDSTATUS);
                    }
                    if (currnetStatus.equals("ZERO")) {  //테이블이 존재하고 아무것도 안눌려져있을때
                        if (pushStatus.equals("YES")) { //YES누르면
                            reviewDao.alreadyCreateReviewHelpZeroPushYes(userIdx, reviewIdx);
                            GetReviewStatusHelp getReviewStatusHelp = reviewDao.getReviewStatusHelp(userIdx, reviewIdx);
                            return getReviewStatusHelp;
                        }
                        if (pushStatus.equals("NO")) { //NO누르면
                            reviewDao.alreadyCreateReviewHelpZeroPushNo(userIdx, reviewIdx);
                            GetReviewStatusHelp getReviewStatusHelp = reviewDao.getReviewStatusHelp(userIdx, reviewIdx);
                            return getReviewStatusHelp;
                        }
                    }
                    if (currnetStatus.equals("YES")) {  //YES가 눌러져 있으면
                        if (pushStatus.equals("YES")) { //다ㅣ시 YES를 누르면
                            reviewDao.alreadyCreateReviewHelpYesPushZero(userIdx, reviewIdx); //zero사ㅣㅇ태
                            GetReviewStatusHelp getReviewStatusHelp = reviewDao.getReviewStatusHelp(userIdx, reviewIdx);
                            return getReviewStatusHelp;
                        }
                        if (pushStatus.equals("NO")) { //no를 누르면 no상태
                            reviewDao.alreadyCreateReviewHelpYesPushNo(userIdx, reviewIdx);
                            GetReviewStatusHelp getReviewStatusHelp = reviewDao.getReviewStatusHelp(userIdx, reviewIdx);
                            return getReviewStatusHelp;
                        }
                    }
                    if (currnetStatus.equals("NO")) { //No가 눌러져있으면
                        if (pushStatus.equals("YES")) { //yes를 누르면 바뀜
                            reviewDao.alreadyCreateReviewHelpNoPushYes(userIdx, reviewIdx);
                            GetReviewStatusHelp getReviewStatusHelp = reviewDao.getReviewStatusHelp(userIdx, reviewIdx);
                            return getReviewStatusHelp;
                        }
                        if (pushStatus.equals("NO")) { //no 하면 zero로
                            reviewDao.alreadyCreateReviewHelpNoPushZero(userIdx, reviewIdx);
                            GetReviewStatusHelp getReviewStatusHelp = reviewDao.getReviewStatusHelp(userIdx, reviewIdx);
                            return getReviewStatusHelp;
                        }
                    }
                }
//            }
            throw new BaseException(INVALID_JWT);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

        }
    }



