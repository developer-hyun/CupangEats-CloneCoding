package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GetLogInInfo {
    private final Integer userIdx;
    private final String id;
    private final String password;

}
