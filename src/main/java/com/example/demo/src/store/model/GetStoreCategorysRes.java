package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreCategorysRes {
    private int menuCategoryIdx;
    private String name;
    private List<GetStoreMenusRes> getStoreMenusRes;
}
