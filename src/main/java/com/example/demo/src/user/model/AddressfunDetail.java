package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class AddressfunDetail {
    public Road_address road_address;
    public Address address;
    @Data
    public static class Road_address {
        private  String address_name;
        private  String region_1depth_name;
        private  String region_2depth_name;
        private  String region_3depth_name;
        private  String road_name;
        private  String underground_yn;
        private  String main_building_no;
        private  String sub_building_no;
        private  String building_name;
        private  String zone_no;
    }
    @Data
    public static class Address {
        private String address_name;
        private String region_1depth_name;
        private String region_2depth_name;
        private String region_3depth_name;
        private String mountain_yn;
        private String main_address_no;
        private String sub_address_no;
        private String zip_code;
    }
}
