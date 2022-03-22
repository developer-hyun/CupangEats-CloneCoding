package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PostCashReceiptRes {
    private final Integer cashReceiptIdx;
    private final String number;
    private final Integer isMethod;
}
