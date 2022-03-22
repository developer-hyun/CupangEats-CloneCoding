package com.example.demo.src.cart;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.cart.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.INVALID_JWT;


@RestController
@RequestMapping("/app/carts")
public class CartController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final CartProvider cartProvider;
    @Autowired
    private final CartService cartService;
    @Autowired
    private final JwtService jwtService;


    public CartController(CartProvider cartProvider, CartService cartService, JwtService jwtService){
        this.cartProvider = cartProvider;
        this.cartService = cartService;
        this.jwtService=jwtService;
    }


    @ResponseBody
    @GetMapping("/{userIdx}/{storeIdx}/coupon-count") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<GetCouponCountRes> getCouponCount(@RequestParam() int price,@PathVariable int userIdx,
                                                    @PathVariable int storeIdx) {

        try {
            if(userIdx!=0) {
                int userIdxByJwt = jwtService.getuserIdx();

                if (userIdx != userIdxByJwt) {

                    return new BaseResponse<>(INVALID_JWT);
                }
            }
                GetCouponCountRes getCouponCountRes = cartProvider.getCouponCount(price,userIdx,storeIdx);
                return new BaseResponse<>(getCouponCountRes);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @GetMapping("/{userIdx}/{storeIdx}/coupon-list") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetCouponsRes>> getCouponList(@RequestParam() int price,@PathVariable int userIdx,
                                                          @PathVariable int storeIdx) {

        try {
            if(userIdx!=0) {
                int userIdxByJwt = jwtService.getuserIdx();

                if (userIdx != userIdxByJwt) {

                    return new BaseResponse<>(INVALID_JWT);
                }
            }
            List<GetCouponsRes> getCouponsRes = cartProvider.getCoupons(price,userIdx,storeIdx);
            return new BaseResponse<>(getCouponsRes);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
}
