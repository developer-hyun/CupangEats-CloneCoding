package com.example.demo.src.store.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetStoreDetailRes {
    private String name;
    private String phone;
    private String address;
    private double latitude;
    private double longitude;
    private String representative;
    private String companyNumber;
    private String businessName;
    private String openingHours;
    private String introduce;
    private String notice;
    private String originInformation;

}
