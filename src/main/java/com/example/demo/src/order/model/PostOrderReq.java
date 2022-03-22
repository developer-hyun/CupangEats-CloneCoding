package com.example.demo.src.order.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostOrderReq {

    private int addressIdx;
    private int orderPrice;

    private int deliveryCost;

    private int couponIdx;
    private int paymentIdx;
    private List<PostOrderMenuReq> postOrderMenuReq;
    private String isDisposable;
    private String toStore;
    private String toDeliveryRider;

    private int discount;







}
