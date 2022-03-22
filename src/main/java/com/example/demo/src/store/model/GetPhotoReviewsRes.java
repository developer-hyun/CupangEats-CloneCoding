package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;



@Getter
@Setter
@AllArgsConstructor
public class GetPhotoReviewsRes {

    private int reviewIdx;
    private String imgUrl;
    private String reviewText;
    private float star;


}
