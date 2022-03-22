package com.example.demo.src.review;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;

import com.example.demo.src.review.model.GetReviewModifyRes;
import com.example.demo.src.review.model.GetReviewModifyViewRes;
import com.example.demo.src.review.model.GetReviewRes;
import com.example.demo.src.review.model.GetReviewViewRes;
import com.example.demo.src.user.UserDao;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.sun.el.parser.AstFalse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class ReviewProvider {

    private final ReviewDao reviewDao;
    private final JwtService jwtService;
    private JdbcTemplate jdbcTemplate;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public ReviewProvider(ReviewDao reviewDao, JwtService jwtService) {
        this.reviewDao = reviewDao;

        this.jwtService = jwtService;
    }

    public GetReviewRes getReview(int userIdx, int storeIdx, String reviewSort, String reviewPhoto) throws BaseException {
        if (reviewDao.checkStoreIdx(storeIdx) == 0) {
            throw new BaseException(INVALID_STOREIDX);
        }
        try {
            if (reviewPhoto.equals("photoNo")) {

                if (reviewSort.equals("latelyReview")) {
                    GetReviewRes getReviewRes = reviewDao.getlatelyReview(userIdx, storeIdx);  //포토없고 최신순 정렬
                    return getReviewRes;
                } else if (reviewSort.equals("reviewHelp")) {
                    GetReviewRes getReviewRes = reviewDao.getReviewHelp(userIdx, storeIdx); //포토없고 도움순 정렬
                    return getReviewRes;
                } else if (reviewSort.equals("starHigh")) {
                    GetReviewRes getReviewRes = reviewDao.getHighStar(userIdx, storeIdx); //포토없고 평점 높은 순 정렬
                    return getReviewRes;
                }
//                 else if (reviewSort.equals("starLow")) {
//                    GetReviewRes getReviewRes = reviewDao.getLowStar(userIdx, storeIdx); //포토없고 평점 낮은 순
//                    return getReviewRes;
                else {
                    GetReviewRes getReviewRes = reviewDao.getLowStar(userIdx, storeIdx);
                    return getReviewRes;
                }

            } else {
                if (reviewSort.equals("latelyReview")) {
                    GetReviewRes getReviewRes = reviewDao.getlatelyReviewPhoto(userIdx, storeIdx);
                    return getReviewRes;
                } else if (reviewSort.equals("reviewHelp")) {
                    GetReviewRes getReviewRes = reviewDao.getReviewHelpPhoto(userIdx, storeIdx);
                    return getReviewRes;
                } else if (reviewSort.equals("starHigh")) {
                    GetReviewRes getReviewRes = reviewDao.getHighStarPhoto(userIdx, storeIdx);
                    return getReviewRes;
                }
//                 else if (reviewSort.equals("starLow")) {
//                    GetReviewRes getReviewRes = reviewDao.getHighStarPhoto(userIdx, storeIdx);
//                    return getReviewRes;
                else {
                    GetReviewRes getReviewRes = reviewDao.getLowStarPhoto(userIdx, storeIdx);
                    return getReviewRes;
                }

               // throw new BaseException(INVALID_REVIEWPHOTO);
//            } else {
//                throw new BaseException(INVALID_REVIEWPHOTO);
//
//            }

            }
       } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //리뷰 생성 화면
    public GetReviewViewRes getReviewView(int orderIdx) throws BaseException {
        if (reviewDao.checkOrderIdx(orderIdx) == 0) {
            throw new BaseException(FAILED_TO_GETORDERIDX);
        }
        try {
            GetReviewViewRes getReviewViewRes = reviewDao.getReviewView(orderIdx);
            return getReviewViewRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //리뷰 수정 화면
    public GetReviewModifyRes getReviewModify(int orderIdx) throws BaseException {
        if (reviewDao.checkOrderIdx(orderIdx) == 0) {
            throw new BaseException(FAILED_TO_GETORDERIDX);
        }
        if (reviewDao.checkReviewDeadLine(orderIdx) == -1) {
            throw new BaseException(FALIED_TO_GETREVIEW_DEADLINE);
        }
        try {
            GetReviewModifyRes getReviewModifyRes = reviewDao.getReviewModify(orderIdx);
            return getReviewModifyRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //리뷰 수정 클릭시 화면
    public GetReviewModifyViewRes getReviewModifyView(int orderIdx) throws BaseException {
        if (reviewDao.checkOrderIdx(orderIdx) == 0) {
            throw new BaseException(FAILED_TO_GETORDERIDX);
        }
        if (reviewDao.checkReviewIdx(orderIdx) == 0) {
            throw new BaseException(FAILED_TO_GETREVIEWIDX);
        }
        try {
            GetReviewModifyViewRes getReviewModifyViewRes = reviewDao.getReviewModifyView(orderIdx);
            return getReviewModifyViewRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
}