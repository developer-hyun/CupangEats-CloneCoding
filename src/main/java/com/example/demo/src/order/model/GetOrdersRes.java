package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetOrdersRes {
    private int orderIdx;
    private String name;
    private String imgUrl1;
    private String createdAt;
    private String deliveryStatus;
    private String cancelReason;
    private String isReviewed;
    private String star;

    private List<GetOrdersMenuRes> getOrdersMenuRes;
    private int totalPrice;

private int reviewIdx;
    private String postReviewDate;
    private String storeStatus;
    private String closeMent;




}
