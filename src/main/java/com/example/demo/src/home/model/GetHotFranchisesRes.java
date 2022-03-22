package com.example.demo.src.home.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetHotFranchisesRes {
   private int storeIdx;
   private String imgUrl1;
   private String isFast;
   private int deliveryMinTime;
   private int deliveryMaxTime;
   private String isEatsOriginal;
   private String name;
   private String star;
   private int reviewNumber;
   private String distance;
   private int deliveryMinCost;
   private int couponIdx;
   private String couponName;
}
