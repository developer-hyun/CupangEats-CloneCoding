package com.example.demo.src.home.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class GetHomeRes {
    private List<GetEventsRes> getEventsRes;
    private List<GetNewStoresRes> getIsSaleRes;
    private List<GetNewStoresRes> getOnlyEatsRes;
    private List<GetNewStoresRes> getHotFranchisesRes;
    private GetHomeEventRes getHomeEventRes;
    private List<GetNewStoresRes> getNewStoresRes;


}
