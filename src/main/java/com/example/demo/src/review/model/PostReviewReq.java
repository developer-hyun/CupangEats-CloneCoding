package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostReviewReq {
    private final Integer orderIdx;
    private final float star;
    private final String imgUrl;
    private final String reviewText;
    private final String isPhoto;
    private final String isText;
    private final Integer deliveryLike;
    private final String deliveryOpinion;
    private final Integer liked;
    private final String menuOpinion;
}
