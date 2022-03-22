package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetOrderDeliveryRes {

    private int time;
    private String endTime;
    private String deliveryStatus;
    private String orderAccept;
    private String orderDelivery;
    private String orderEnd;
    private String address;
private int orderIdx;
    private String storeName;
    private List<GetOrdersPreparingMenuRes> getOrdersPreparingMenuRes;

    private int totalPrice;
    private String bank;
    private String paymentNumber;
    private String payment;



}
