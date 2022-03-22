package com.example.demo.src.review;

import com.example.demo.src.review.model.*;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.UserService;
import com.example.demo.utils.ValidationRegex;
import com.fasterxml.jackson.databind.ser.Serializers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.web.bind.annotation.*;

import java.util.List;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.isRegexEmail;

@RestController
@RequestMapping("/app/reviews")  // 이 url을 입력하면 이렇게 되도록 맵핑함
public class ReviewController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //엮겠다.  // 대문자는 해당 클래스를 불러오고 소문자는 클래스에서 객체를 만들어서 사용함
    private final ReviewProvider reviewProvider;
    // @Autowired
    private final ReviewService reviewService;
    @Autowired
    private final JwtService jwtService;

    // autowired를 쓰려면 밑에 이렇게 한다음 클래스 전체에서 쓸수 있게 this로 해줘야한다??  // 생성자
    public ReviewController(ReviewProvider reviewProvider, ReviewService reviewService, JwtService jwtService) {
        this.reviewProvider = reviewProvider;   // userProvider를 클래스 전체에서 쓰겠당
        this.reviewService = reviewService;
        this.jwtService = jwtService;
    }

    //리뷰 조회 API
    @ResponseBody
    @GetMapping("/{userIdx}/{storeIdx}")
    public BaseResponse<GetReviewRes> getReview(@PathVariable("userIdx") int userIdx,
                                                @PathVariable("storeIdx") int storeIdx,
                                                @RequestParam(required = false, defaultValue = "latelyReview") String reviewSort,
                                                @RequestParam(required = false, defaultValue = "photoNo") String reviewPhoto) throws BaseException {

//        try {
//            int userIdxJwt = jwtService.getuserIdx();
//        } catch (BaseException exception) {
//            return new BaseResponse<>(EMPTY_JWT);
//        }
//        try {
//            int userIdxJwt = jwtService.getuserIdx();
//
//            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
//                return new BaseResponse<>(INVALID_JWT);
//            }

            if ((reviewSort.equals("latelyReview")) || reviewSort.equals("reviewHelp") || reviewSort.equals("starHigh") || reviewSort.equals("starLow")) {
                if ((reviewPhoto.equals("photoNo")) || reviewPhoto.equals("photoYes")) {
                    GetReviewRes getReviewRes = reviewProvider.getReview(userIdx, storeIdx, reviewSort, reviewPhoto);
                    return new BaseResponse<>(getReviewRes);
                } else {
                    throw new BaseException(INVALID_REVIEWPHOTO);

                }
            } else {
                throw new BaseException(INVALID_REVIEWSORT);
            }

//
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//
//
//        }

    }   //리뷰 생성 화면

    @ResponseBody
    @GetMapping("/view/create/{userIdx}/{orderIdx}")
    public BaseResponse<GetReviewViewRes> getReviewView(@PathVariable("userIdx") int userIdx,
                                                        @PathVariable("orderIdx") int orderIdx) throws BaseException {

        try {
            int userIdxJwt = jwtService.getuserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        try {
            int userIdxJwt = jwtService.getuserIdx();

            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
                return new BaseResponse<>(INVALID_JWT);
            }

            GetReviewViewRes getReviewViewRes = reviewProvider.getReviewView(orderIdx);
            return new BaseResponse<>(getReviewViewRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));


        }
    }

    //리뷰 생성
    @ResponseBody
    @PostMapping("/{userIdx}/{orderIdx}/{menuIdx}")
    public BaseResponse<PostReviewRes> postReview(@PathVariable("userIdx") int userIdx,
                                                  @PathVariable("orderIdx") int orderIdx,
                                                  @PathVariable("menuIdx") int menuIdx,
                                                  @RequestBody PostReviewReq postReviewReq) throws BaseException {
        try {
            int userIdxJwt = jwtService.getuserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        try {
            int userIdxJwt = jwtService.getuserIdx();

            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
                return new BaseResponse<>(INVALID_JWT);
            }
            if (postReviewReq.getOrderIdx() == null) {
                return new BaseResponse<>(INVALID_GETORDERIDX);
            }
            if (postReviewReq.getIsPhoto() == null) {
                return new BaseResponse<>(INVALID_GETISPHOTO);
            }
            if (postReviewReq.getIsText() == null) {
                return new BaseResponse<>(INVALID_GETISTEXT);
            }

            PostReviewRes postReviewRes = reviewService.postReview(orderIdx, menuIdx, postReviewReq);
            return new BaseResponse<>(postReviewRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));


        }

    }

    //리뷰 수정 화면
    @ResponseBody
    @GetMapping("/view/my/{userIdx}/{orderIdx}")
    public BaseResponse<GetReviewModifyRes> getReviewModify(@PathVariable("userIdx") int userIdx,
                                                            @PathVariable("orderIdx") int orderIdx) throws BaseException {
        try {
            int userIdxJwt = jwtService.getuserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        try {
            int userIdxJwt = jwtService.getuserIdx();

            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
                return new BaseResponse<>(INVALID_JWT);
            }
            GetReviewModifyRes getReviewModifyRes = reviewProvider.getReviewModify(orderIdx);
            return new BaseResponse<>(getReviewModifyRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));


        }

    }

    //리뷰 클릭시 수정 화면
    @ResponseBody
    @GetMapping("/view/modify/{userIdx}/{orderIdx}")
    public BaseResponse<GetReviewModifyViewRes> getReviewModifyView(@PathVariable("userIdx") int userIdx,
                                                                    @PathVariable("orderIdx") int orderIdx) throws BaseException {
        try {
            int userIdxJwt = jwtService.getuserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        try {
            int userIdxJwt = jwtService.getuserIdx();

            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
                return new BaseResponse<>(INVALID_JWT);
            }
            GetReviewModifyViewRes getReviewModifyViewRes = reviewProvider.getReviewModifyView(orderIdx);
            return new BaseResponse<>(getReviewModifyViewRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));


        }


    }

    //리뷰 t수정
    @ResponseBody
    @PatchMapping("/{userIdx}/{orderIdx}/{menuIdx}")
    public BaseResponse<PostReviewRes> patchReview(@PathVariable("userIdx") int userIdx,
                                                   @PathVariable("orderIdx") int orderIdx,
                                                   @PathVariable("menuIdx") int menuIdx,
                                                   @RequestBody PostReviewReq postReviewReq) throws BaseException {
        try {
            int userIdxJwt = jwtService.getuserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        try {
            int userIdxJwt = jwtService.getuserIdx();

            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
                return new BaseResponse<>(INVALID_JWT);
            }
            if (postReviewReq.getOrderIdx() == null) {
                return new BaseResponse<>(INVALID_GETORDERIDX);
            }
            if (postReviewReq.getIsPhoto() == null) {
                return new BaseResponse<>(INVALID_GETISPHOTO);
            }
            if (postReviewReq.getIsText() == null) {
                return new BaseResponse<>(INVALID_GETISTEXT);
            }

            PostReviewRes postReviewRes = reviewService.patchReview(orderIdx, menuIdx, postReviewReq);
            return new BaseResponse<>(postReviewRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));


        }

    }

    //리뷰 삭제
    @ResponseBody
    @DeleteMapping("/{userIdx}/{orderIdx}")
    public BaseResponse<DeleteReviewRes> deleteReview(@PathVariable("userIdx") int userIdx,
                                                      @PathVariable("orderIdx") int orderIdx) throws BaseException {
        try {
            int userIdxJwt = jwtService.getuserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        try {
            int userIdxJwt = jwtService.getuserIdx();

            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
                return new BaseResponse<>(INVALID_JWT);
            }
            DeleteReviewRes deleteReviewRes = reviewService.deleteReview(orderIdx);
            return new BaseResponse<>(deleteReviewRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));


        }
    }

    //리뷰화면에서 돼요 안돼요 누르는 부분
    @ResponseBody
    @PatchMapping("/help/{userIdx}/{reviewIdx}")
    public BaseResponse<GetReviewStatusHelp> getReviewStatusHelp(@PathVariable("userIdx") int userIdx,
                                                                 @PathVariable("reviewIdx") int reviewIdx,
                                                                 @RequestParam(required = true) String currentStatus,
                                                                 @RequestParam(required = true) String pushStatus) throws BaseException{

        try {
            int userIdxJwt = jwtService.getuserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        if (!((currentStatus.equals("ZERO")) || (currentStatus.equals("YES")) || currentStatus.equals("NO"))) {
            return new BaseResponse<>(INVALID_CURRNETSTATUS);
        }
        if (!((pushStatus.equals("YES")) || (pushStatus.equals("NO")))){
            return new BaseResponse<>(INVALID_PUSHSTATUS);
        }
        //currenstatus 재확인 필요
        try {
            int userIdxJwt = jwtService.getuserIdx();

            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
                return new BaseResponse<>(INVALID_JWT);
            }
            GetReviewStatusHelp getReviewStatusHelp = reviewService.getReviewStatusHelp(userIdx, reviewIdx, currentStatus, pushStatus);
            return new BaseResponse<>(getReviewStatusHelp);
        }catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));


        }
    }
}