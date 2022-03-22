package com.example.demo.src.miggle.model;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostMiggleReq {
    private final String id;
    private final String password;
}
