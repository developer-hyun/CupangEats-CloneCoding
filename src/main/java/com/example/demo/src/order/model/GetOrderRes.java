package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetOrderRes {
    private String name;
    private String createdAt;
    private List<GetOrderMenuRes> getOrderMenuRes;
    private int orderPrice;
    private int deliveryCost;
    private int discount;
    private int totalPrice;
    private String bank;
    private String paymentNumber;
    private String payment;
    private String status;




}
