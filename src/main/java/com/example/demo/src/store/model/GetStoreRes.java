package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreRes {
    private String imgUrl1;
    private String imgUrl2;
    private String imgUrl3;
    private String liked;
    private String name;
    private String star;
    private int reviewNum;
    private String couponName;
    private int couponIdx;
    private String isFast;
    private int deliveryMinTime;
    private int deliveryMaxTime;
    private int deliveryMinCost;
    private int deliveryMaxCost;
    private int minimumAmount;


}
