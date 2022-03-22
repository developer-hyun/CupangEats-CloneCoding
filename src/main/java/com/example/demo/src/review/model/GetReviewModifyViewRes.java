package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
//리뷰 수정 클릭했을 때 화면
public class GetReviewModifyViewRes {
    private final Integer reviewIdx;
    private final String storeName;
    private final float star;
    private final String imgUrl;
    private final String reviewText;
    private final String isPhoto;
    private final String isText;
    private final Integer deliveryLike;
    private final String deliveryOpinion;
    private final String menuName;
    private final Integer menuIdx;
    private final Integer liked;
    private final String menuOpinion;
}
