package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class PatchAddressRes {
    private final Integer addressIdx;
    private final Integer isWhere;
    private final String isMain;
    private final String buildingAddress;
    private final String mainAddress;
    private final String subAddress;
}
