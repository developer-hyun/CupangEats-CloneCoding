package com.example.demo.src.cart.model;


import com.example.demo.src.cart.model.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostCartReq {

    private int addressIdx;
    private int orderPrice;

    private int deliveryCost;

    private int couponIdx;
    private int paymentIdx;
    private List<PostCartMenuReq> postCartMenuReq;

    private int discount;







}
