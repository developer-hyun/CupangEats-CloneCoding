package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class kakaologin {
    private String grant_type;
    private String client_id;
    private String redirect_uri;
    private String code;
}
