package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetReviewsRes {
    private final Integer reviewIdx;
    private final String userName;
    private final float reviewStar;
    private final String imgUrl;
    private final String reviewText;
    private final String reviewDate;
    private final String reviewMenu;
    private final String isText;
    private final Integer reviewLikeCount;
    //private final String statusHelp;
}
