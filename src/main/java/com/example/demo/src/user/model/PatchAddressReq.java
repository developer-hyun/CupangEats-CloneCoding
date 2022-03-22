package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;


@Getter
@Setter
@AllArgsConstructor
public class PatchAddressReq {
    private final Integer existMainAddress;
    private final Integer changeMainAddress;

}
