package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
        import lombok.Getter;
        import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class PostUserReq {
    private final String id;
    private final String password;
    private final String name;
    private final String phoneNumber;
    private final Integer signUpAgreeCheckBox;
    private final Integer socialLogIn;
}

