package com.example.demo.src.cart.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCouponsRes {
    private int couponIdx;
    private String information;
    private String name;
    private String minimumAmount;
    private String endTime;
    private String isAvailable;



}
