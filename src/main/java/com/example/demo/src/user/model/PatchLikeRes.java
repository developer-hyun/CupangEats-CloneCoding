package com.example.demo.src.user.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class PatchLikeRes {
    private final Integer userIdx;
    private final List<Integer> storeIdx;

   // rivate final List<Integer> storeIdx;
}
