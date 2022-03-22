package com.example.demo.src.search.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetStoresRes {
   private int storeIdx;
   private String imgUrl1;
   private String menuImg1;
   private String menuImg2;
   private String name;
   private String isNew;
   private String isFast;
   private int deliveryMinTime;
   private int deliveryMaxTime;

   private String star;
   private int reviewNumber;
   private String distance;
   private int deliveryMinCost;
   private int couponIdx;
   private String couponName;
   private String isOpened;
   private String closeMent;
}
