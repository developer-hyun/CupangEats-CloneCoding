package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetOrdersMenuRes {

    private int menuIdx;
    private String name;

    private int count;
    private String liked;
    private List<GetOrdersSideMenuRes> getOrdersSideMenuRes;

}
