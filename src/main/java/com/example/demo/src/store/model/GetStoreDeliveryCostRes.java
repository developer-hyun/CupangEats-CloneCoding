package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreDeliveryCostRes {
    private int minCost;
    private int maxCost;
    private int deliveryCost;
}
