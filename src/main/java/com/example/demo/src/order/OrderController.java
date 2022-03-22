package com.example.demo.src.order;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;

import com.example.demo.src.order.model.*;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

@RestController
@RequestMapping("/app/orders")
public class OrderController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final OrderProvider orderProvider;
    @Autowired
    private final OrderService orderService;
    @Autowired
    private final JwtService jwtService;


    public OrderController(OrderProvider orderProvider, OrderService orderService, JwtService jwtService) {
        this.orderProvider = orderProvider;
        this.orderService = orderService;
        this.jwtService = jwtService;
    }


    //주문하기 //jwt 인증하기
    @ResponseBody
    @PostMapping("/{userIdx}")
    public BaseResponse<PostOrderRes> createOrder(@PathVariable("userIdx") int userIdx, @RequestBody PostOrderReq postOrderReq) {


        if (postOrderReq.getOrderPrice() == 0) {

            return new BaseResponse<>(POST_ORDERS_EMPTY_ORDERPRICE);
        }
        if (postOrderReq.getAddressIdx() == 0) {

            return new BaseResponse<>(POST_ORDERS_EMPTY_ADDRESSIDX);
        }

        if (postOrderReq.getPaymentIdx() == 0) {

            return new BaseResponse<>(POST_ORDERS_EMPTY_PAYMENTIDX);
        }

        if (postOrderReq.getIsDisposable() == null) {

            return new BaseResponse<>(POST_ORDERS_EMPTY_ISDISPOSABLE);
        }
        if (postOrderReq.getToDeliveryRider() == null) {

            return new BaseResponse<>(POST_ORDERS_EMPTY_TODELIVERYRIDER);
        }


        //이번엔 OrderMenu에 들어갈 것
        for (int i = 0; i < postOrderReq.getPostOrderMenuReq().size(); i++) {
            if (postOrderReq.getPostOrderMenuReq().get(i).getMenuIdx() == 0) {

                return new BaseResponse<>(POST_ORDERS_EMPTY_MENUIDX);
            }
            if (postOrderReq.getPostOrderMenuReq().get(i).getCount() == 0) {

                return new BaseResponse<>(POST_ORDERS_EMPTY_COUNT);
            }
            for (int j = 0; j < postOrderReq.getPostOrderMenuReq().get(i).getPostOrderSideMenuReq().size(); j++) {
                if (postOrderReq.getPostOrderMenuReq().get(i).getPostOrderSideMenuReq().get(j).getSubSideCategoryIdx() == 0) {

                    return new BaseResponse<>(POST_ORDERS_EMPTY_SUBSIDECATEGORYIDX);
                }

            }
        }


        try {


            int userIdxByJwt = jwtService.getuserIdx();

            if (userIdx != userIdxByJwt)
                return new BaseResponse<>(INVALID_JWT);
            PostOrderRes postOrder = orderService.createOrder(userIdx, postOrderReq);
            return new BaseResponse<>(postOrder);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }




    @ResponseBody
    @PatchMapping("/{userIdx}")

    public BaseResponse<PatchOrderRes> patchOrder(@PathVariable("userIdx") int userIdx, @RequestBody PatchOrderReq patchOrderReq) {

        if(patchOrderReq.getOrderIdx()==0)
            return new BaseResponse<>(PATCH_ORDERS_EMPTY_ORDERIDX);

        try {

            int userIdxByJwt = jwtService.getuserIdx();

            if (userIdx != userIdxByJwt)
                return new BaseResponse<>(INVALID_JWT);
            PatchOrderRes patchOrder = orderService.patchOrder(patchOrderReq);

            return new BaseResponse<>(patchOrder);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }


    @ResponseBody
    @GetMapping("/receipt/{userIdx}/{orderIdx}")
    public BaseResponse<GetOrderRes> getOrder(
            @PathVariable int userIdx ,@PathVariable int orderIdx) {

        try {
            int userIdxByJwt= jwtService.getuserIdx();

            if(userIdx!=userIdxByJwt) {

                return new BaseResponse<>(INVALID_JWT);
            }
           GetOrderRes getOrder = orderProvider.getOrder(userIdx,orderIdx);
            return new BaseResponse<>(getOrder);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    @ResponseBody
    @GetMapping("/{userIdx}")
    public BaseResponse<List<GetOrdersRes>> getOrders(
            @PathVariable int userIdx ) {

        try {
            int userIdxByJwt= jwtService.getuserIdx();

            if(userIdx!=userIdxByJwt) {

                return new BaseResponse<>(INVALID_JWT);
            }
            List<GetOrdersRes> getOrders = orderProvider.getOrders(userIdx);
            return new BaseResponse<>(getOrders);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

//리뷰 작성 가능 수
    @ResponseBody
    @GetMapping("/{userIdx}/review-count")
    public BaseResponse<GetReviewCountRes> getReviewCount(
            @PathVariable int userIdx ) {

        try {
            int userIdxByJwt= jwtService.getuserIdx();

            if(userIdx!=userIdxByJwt) {

                return new BaseResponse<>(INVALID_JWT);
            }
            GetReviewCountRes getReviewCount = orderProvider.getReviewCount(userIdx);
            return new BaseResponse<>(getReviewCount);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }


    @ResponseBody
    @GetMapping("/{userIdx}/preparing")
    public BaseResponse<List<GetOrdersPreparingRes>> getOrdersPreparing(
            @PathVariable int userIdx ) {

        try {
            int userIdxByJwt= jwtService.getuserIdx();

            if(userIdx!=userIdxByJwt) {

                return new BaseResponse<>(INVALID_JWT);
            }
            List<GetOrdersPreparingRes> getOrdersPreparing = orderProvider.getOrdersPreparing(userIdx);
            return new BaseResponse<>(getOrdersPreparing);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }

    @ResponseBody
    @GetMapping("/{userIdx}/{orderIdx}/delivery-status")
    public BaseResponse<GetOrderDeliveryRes> getOrderDeliveryRes(
            @PathVariable int userIdx ,  @PathVariable int orderIdx ) {

        try {
            int userIdxByJwt= jwtService.getuserIdx();

            if(userIdx!=userIdxByJwt) {

                return new BaseResponse<>(INVALID_JWT);
            }
            GetOrderDeliveryRes getOrderDelivery = orderProvider.getOrderDelivery(orderIdx);
            return new BaseResponse<>(getOrderDelivery);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }
}

