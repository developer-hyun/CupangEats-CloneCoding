package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;


import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetOrdersPreparingRes {
    private int orderIdx;
    private String name;
    private String imgUrl1;
    private String createdAt;
    private String deliveryStatus;

    private List<GetOrdersPreparingMenuRes> getOrdersPreparingMenuRes;
    private int totalPrice;




}
