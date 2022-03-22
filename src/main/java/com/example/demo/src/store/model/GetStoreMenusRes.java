package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreMenusRes {
    private int menuIdx;
    private String isManyOrders;
    private String isGoodReview;
    private String name;
    private int price;
    private String imgUrl1;
    private String information;

}
