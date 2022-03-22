package com.example.demo.src.user.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostAddressReq {
    private final String buildingAddress;
    private final String mainAddress;
    private final float latitude;
    private final float longitude;
    private final String subAdress;
    private final String guidAdress;
    private final Integer isWhere;
}
