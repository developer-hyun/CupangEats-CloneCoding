package com.example.demo.src.store;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.DeleteResponse;
import com.example.demo.src.store.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_JWT;


@RestController
@RequestMapping("/app/stores")
public class StoreController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final StoreProvider storeProvider;
    @Autowired
    private final StoreService storeService;
    @Autowired
    private final JwtService jwtService;


    public StoreController(StoreProvider storeProvider, StoreService storeService,JwtService jwtService){
        this.storeProvider = storeProvider;
        this.storeService = storeService;
        this.jwtService=jwtService;
    }


    @ResponseBody
    @GetMapping("/{storeIdx}") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<GetStoreRes> getStore(@RequestParam(required = false,defaultValue="0") int userIdx,
                                              @PathVariable int storeIdx) {

        try {
            if(userIdx!=0) {
                int userIdxByJwt = jwtService.getuserIdx();

                if (userIdx != userIdxByJwt) {

                    return new BaseResponse<>(INVALID_JWT);
                }
            }
                GetStoreRes getStoreRes = storeProvider.getStore(userIdx,storeIdx);
                return new BaseResponse<>(getStoreRes);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @GetMapping("/{storeIdx}/details") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<GetStoreDetailRes> getStoreDetail(
                                              @PathVariable int storeIdx) {
        try {

            GetStoreDetailRes getStoreDetailRes = storeProvider.getStoreDetail(storeIdx);
            return new BaseResponse<>(getStoreDetailRes);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @GetMapping("/{storeIdx}/reviews") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetPhotoReviewsRes>> getPhotoReviewRes(
            @PathVariable int storeIdx) {
        try {

            List<GetPhotoReviewsRes> getPhotoReviewRes = storeProvider.getPhotoReviews(storeIdx);
            return new BaseResponse<>(getPhotoReviewRes);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    @ResponseBody
    @GetMapping("/{storeIdx}/menus") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetStoreCategorysRes>> getStoreCategorys(
            @PathVariable int storeIdx) {
        try {

            List<GetStoreCategorysRes> getStoreCategorysRes = storeProvider.getStoreCategorys(storeIdx);
            return new BaseResponse<>(getStoreCategorysRes);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    @ResponseBody
    @GetMapping("/{storeIdx}/delivery-cost")
    public BaseResponse<List<GetStoreDeliveryCostRes>> getStoreDeliveryCost(
            @PathVariable int storeIdx) {
        try {

            List<GetStoreDeliveryCostRes> getStoreDeliveryCostRes = storeProvider.getStoreDeliveryCost(storeIdx);
            return new BaseResponse<>(getStoreDeliveryCostRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
}
