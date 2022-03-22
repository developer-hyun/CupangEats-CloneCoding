package com.example.demo.src.search;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;

import com.example.demo.src.search.model.GetStoresRes;
import com.example.demo.utils.JwtService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;


@RestController
@RequestMapping("/app/search")
public class SearchController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    private final SearchProvider searchProvider;
    @Autowired
    private final SearchService searchService;
    @Autowired
    private final JwtService jwtService;


    public SearchController(SearchProvider searchProvider, SearchService searchService, JwtService jwtService){
        this.searchProvider = searchProvider;
        this.searchService = searchService;
        this.jwtService=jwtService;
    }


    @ResponseBody
    @GetMapping("/{userIdx}/{addressIdx}") // (GET) 127.0.0.1:9000/app/users
    public BaseResponse<List<GetStoresRes>> getStores(@RequestParam(required = false,defaultValue="0") int order,
                                                           @RequestParam(required = false,defaultValue="N") String isFast,
                                                           @RequestParam(required = false,defaultValue="50000") int deliveryCost ,
                                                           @RequestParam(required = false,defaultValue="100000") int minimumAmount,
                                                           @RequestParam(required = false,defaultValue="0") int coupon,
                                                      @RequestParam() String keyword,
                                                           @PathVariable int userIdx,@PathVariable int addressIdx ) {

        if(order>4 || order<0){
            return new BaseResponse<>(INVALID_ORDER);
        }
        if(coupon<0 || coupon>1){
            return new BaseResponse<>(INVALID_COUPON);

        }
        try {

            int userIdxByJwt = jwtService.getuserIdx();

            if (userIdx != userIdxByJwt) {

                return new BaseResponse<>(INVALID_JWT);

            }
            List<GetStoresRes> getStores = searchProvider.getStores(order,isFast,deliveryCost,minimumAmount,coupon,addressIdx,keyword);
            return new BaseResponse<>(getStores);



        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }

    }



}
