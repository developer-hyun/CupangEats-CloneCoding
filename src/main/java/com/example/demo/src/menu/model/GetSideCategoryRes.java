package com.example.demo.src.menu.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetSideCategoryRes {
    private String name;
    private String isNecessary;
    private String isCheckBox;
    private int MinCheck;
    private int MaxCheck;
    private int SideCategoryIdx;
    private List<GetSubSideCategoryRes> subSideCategory;

}
