package com.example.demo.src.home;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;

import com.example.demo.src.home.HomeProvider;
import com.example.demo.src.home.HomeService;
import com.example.demo.src.home.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;


@RestController
@RequestMapping("/app/homes")
public class HomeController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final HomeProvider homeProvider;
    @Autowired
    private final HomeService homeService;
    @Autowired
    private final JwtService jwtService;


    public HomeController(HomeProvider homeProvider, HomeService homeService, JwtService jwtService){
        this.homeProvider = homeProvider;
        this.homeService = homeService;
        this.jwtService=jwtService;
    }



    @ResponseBody
    @GetMapping("/{userIdx}/{addressIdx}") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<GetHomeRes> getHome(
            @PathVariable int userIdx,@PathVariable int addressIdx) {

        try {

//            int userIdxByJwt = jwtService.getuserIdx();
//
//            if (userIdx != userIdxByJwt) {
//
//                return new BaseResponse<>(INVALID_JWT);
//
//            }
            GetHomeRes getHome = homeProvider.getHome(addressIdx);
            return new BaseResponse<>(getHome);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    @ResponseBody
    @GetMapping("/{userIdx}/{addressIdx}/{categoryIdx}/new-store") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetNewStoresCategoryRes>> getNewStoresCategory(
            @PathVariable int userIdx,@PathVariable int addressIdx, @PathVariable int categoryIdx) {

        try {

            int userIdxByJwt = jwtService.getuserIdx();

            if (userIdx != userIdxByJwt) {

                return new BaseResponse<>(INVALID_JWT);

            }
            List<GetNewStoresCategoryRes> getNewStores = homeProvider.getNewStoresCategory(addressIdx,categoryIdx);
            return new BaseResponse<>(getNewStores);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    @ResponseBody
    @GetMapping("/{userIdx}/{addressIdx}/{categoryIdx}") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetStoresCategoryRes>> getStoresCategory(@RequestParam(required = false,defaultValue="0") int order,
                                                           @RequestParam(required = false,defaultValue="N") String isFast,
                                                           @RequestParam(required = false,defaultValue="50000") int deliveryCost ,
                                                           @RequestParam(required = false,defaultValue="100000") int minimumAmount,
                                                           @RequestParam(required = false,defaultValue="0") int coupon,
                                                           @PathVariable int userIdx,@PathVariable int addressIdx ,@PathVariable int categoryIdx) {

        if(order>4 || order<0){
            return new BaseResponse<>(INVALID_ORDER);
        }
        if(coupon<0 || coupon>1){
            return new BaseResponse<>(INVALID_COUPON);

        }
        try {

            int userIdxByJwt = jwtService.getuserIdx();

            if (userIdx != userIdxByJwt) {

                return new BaseResponse<>(INVALID_JWT);

            }
            List<GetStoresCategoryRes> getStoresCategory = homeProvider.getStoresCategory(order,isFast,deliveryCost,minimumAmount,coupon,addressIdx,categoryIdx);
            return new BaseResponse<>(getStoresCategory);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @GetMapping("/{userIdx}/{addressIdx}/new-store-list")
    public BaseResponse<List<GetStoresCategoryRes>> getNewStoresList(@RequestParam(required = false,defaultValue="0") int order,
                                                                      @RequestParam(required = false,defaultValue="N") String isFast,
                                                                      @RequestParam(required = false,defaultValue="50000") int deliveryCost ,
                                                                      @RequestParam(required = false,defaultValue="100000") int minimumAmount,
                                                                      @RequestParam(required = false,defaultValue="0") int coupon,
                                                                      @PathVariable int userIdx,@PathVariable int addressIdx ) {

        if(order>4 || order<0){
            return new BaseResponse<>(INVALID_ORDER);
        }
        if(coupon<0 || coupon>1){
            return new BaseResponse<>(INVALID_COUPON);

        }
        try {

            int userIdxByJwt = jwtService.getuserIdx();

            if (userIdx != userIdxByJwt) {

                return new BaseResponse<>(INVALID_JWT);

            }
            List<GetStoresCategoryRes> getStoresCategory = homeProvider.getNewStoresCategory(order,isFast,deliveryCost,minimumAmount,coupon,addressIdx);
            return new BaseResponse<>(getStoresCategory);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }



    @ResponseBody
    @GetMapping("/{userIdx}/{addressIdx}/around-store") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetStoresRes>> getArondStores(@RequestParam(required = false,defaultValue="0") int order,
                                                      @RequestParam(required = false,defaultValue="N") String isFast,
                                                      @RequestParam(required = false,defaultValue="50000") int deliveryCost ,
                                                      @RequestParam(required = false,defaultValue="100000") int minimumAmount,
                                                      @RequestParam(required = false,defaultValue="0") int coupon,
                                                      @PathVariable int userIdx,@PathVariable int addressIdx ) {

        if(order>4 || order<0){
            return new BaseResponse<>(INVALID_ORDER);
        }
        if(coupon<0 || coupon>1){
            return new BaseResponse<>(INVALID_COUPON);

        }
        try {

//            int userIdxByJwt = jwtService.getuserIdx();
//
//            if (userIdx != userIdxByJwt) {
//
//                return new BaseResponse<>(INVALID_JWT);
//
//            }
            List<GetStoresRes> getStores = homeProvider.getAroundStores(order,isFast,deliveryCost,minimumAmount,coupon,addressIdx);
            return new BaseResponse<>(getStores);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @GetMapping("/{userIdx}/{addressIdx}/hot-franchise-list")
    public BaseResponse<List<GetStoresCategoryRes>> getHotList(@RequestParam(required = false,defaultValue="0") int order,
                                                                          @RequestParam(required = false,defaultValue="N") String isFast,
                                                                          @RequestParam(required = false,defaultValue="50000") int deliveryCost ,
                                                                          @RequestParam(required = false,defaultValue="100000") int minimumAmount,
                                                                          @RequestParam(required = false,defaultValue="0") int coupon,
                                                                          @PathVariable int userIdx,@PathVariable int addressIdx ) {

        if(order>4 || order<0){
            return new BaseResponse<>(INVALID_ORDER);
        }
        if(coupon<0 || coupon>1){
            return new BaseResponse<>(INVALID_COUPON);

        }
        try {

            int userIdxByJwt = jwtService.getuserIdx();

            if (userIdx != userIdxByJwt) {

                return new BaseResponse<>(INVALID_JWT);

            }
            List<GetStoresCategoryRes> getStoresCategory = homeProvider.getHotFranchisesCategory(order,isFast,deliveryCost,minimumAmount,coupon,addressIdx);
            return new BaseResponse<>(getStoresCategory);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
    @ResponseBody
    @GetMapping("/{userIdx}/{addressIdx}/only-eats-list")
    public BaseResponse<List<GetStoresCategoryRes>> getOnlyEatsList(@RequestParam(required = false,defaultValue="0") int order,
                                                               @RequestParam(required = false,defaultValue="N") String isFast,
                                                               @RequestParam(required = false,defaultValue="50000") int deliveryCost ,
                                                               @RequestParam(required = false,defaultValue="100000") int minimumAmount,
                                                               @RequestParam(required = false,defaultValue="0") int coupon,
                                                               @PathVariable int userIdx,@PathVariable int addressIdx ) {

        if(order>4 || order<0){
            return new BaseResponse<>(INVALID_ORDER);
        }
        if(coupon<0 || coupon>1){
            return new BaseResponse<>(INVALID_COUPON);

        }
        try {

            int userIdxByJwt = jwtService.getuserIdx();

            if (userIdx != userIdxByJwt) {

                return new BaseResponse<>(INVALID_JWT);

            }
            List<GetStoresCategoryRes> getStoresCategory = homeProvider.getOnlyEatsList(order,isFast,deliveryCost,minimumAmount,coupon,addressIdx);
            return new BaseResponse<>(getStoresCategory);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
    @ResponseBody
    @GetMapping("/{userIdx}/{addressIdx}/is-sale-list")
    public BaseResponse<List<GetStoresCategoryRes>> getIsSale(@RequestParam(required = false,defaultValue="0") int order,
                                                                    @RequestParam(required = false,defaultValue="N") String isFast,
                                                                    @RequestParam(required = false,defaultValue="50000") int deliveryCost ,
                                                                    @RequestParam(required = false,defaultValue="100000") int minimumAmount,
                                                                    @RequestParam(required = false,defaultValue="0") int coupon,
                                                                    @PathVariable int userIdx,@PathVariable int addressIdx ) {

        if(order>4 || order<0){
            return new BaseResponse<>(INVALID_ORDER);
        }
        if(coupon<0 || coupon>1){
            return new BaseResponse<>(INVALID_COUPON);

        }
        try {

            int userIdxByJwt = jwtService.getuserIdx();

            if (userIdx != userIdxByJwt) {

                return new BaseResponse<>(INVALID_JWT);

            }
            List<GetStoresCategoryRes> getStoresCategory = homeProvider.getIsSaleList(order,isFast,deliveryCost,minimumAmount,coupon,addressIdx);
            return new BaseResponse<>(getStoresCategory);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

}
