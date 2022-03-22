package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetOrderMenuRes {
    private String name;
    private int price;
    private int count;
    private List<GetOrderSideMenuRes> getOrderSideMenuRes;
}
