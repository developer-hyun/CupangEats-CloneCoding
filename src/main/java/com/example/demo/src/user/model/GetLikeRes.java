package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetLikeRes {
    private final Integer totalNum;  //즐겨찾기 총개수
//    private final String imageUrl;  // 즐겨찾기 상품 이미지
//    private final String storeName; //가게이름
//    private final float storeStar; //가게평점
//    private final Integer reviewCount; //리뷰 수
    private final List<GetLikesRes> likestore;
}
