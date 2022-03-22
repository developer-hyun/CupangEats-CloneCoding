package com.example.demo.src.order.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetOrdersPreparingMenuRes {

    private String name;

    private int count;

    private List<GetOrdersPreparingSideMenuRes> getOrdersPreparingSideMenuRes;

}
