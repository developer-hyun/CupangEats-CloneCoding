package com.example.demo.src.cart.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetCouponCountRes {
    private String count;
    private String price;
private int couponPrice;

}
