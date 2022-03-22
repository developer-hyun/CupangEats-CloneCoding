package com.example.demo.src.review.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetReviewModifyRes {
    private final String storeName;
    private final float star;
    private final String reviewText;
    private final String imgUrl;
    private final String reviewDate;
    private final String reviewMenu;
    private final Integer reviewLikeCount;
    private final String reviewPatchDate;
}
