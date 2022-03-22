package com.example.demo.src.order.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostOrderMenuReq {


    private int menuIdx;
    private int count;

    private List<PostOrderSideMenuReq> postOrderSideMenuReq;




}
