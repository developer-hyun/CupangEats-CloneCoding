package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetReviewRes {
    private final String storeName;
    private final List<GetReviewsRes> getReviewsRes;
    private final List<GetReviewStatusRes> getReviewStatusRes;
}
