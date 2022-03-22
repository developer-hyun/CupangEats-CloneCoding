package com.example.demo.src.cart.model;

import com.example.demo.src.cart.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostCartMenuReq {


    private int menuIdx;
    private int count;

    private List<PostCartSideMenuReq> postCartSideMenuReq;




}
