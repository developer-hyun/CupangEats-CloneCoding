package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetLikesRes {
    private final String imageUrl;
    private final String name;
    private final String isFast;
    private  final float avgStar;
    private final Integer countReview;
    private final String deliveryDistance;
    private final String deliveryTime;
    private final String deliveryCost;
}
