package com.example.demo.src.cart;


import com.example.demo.src.cart.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class CartDao {

    private JdbcTemplate jdbcTemplate;


    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



    public GetCouponCountRes getCouponCount(int price,int userIdx, int storeIdx){
        String getStoreQuery ="select  count(*) as count, case when (count(*)=0 or ?<C.minimumAmount )then '쿠폰을 선택해주세요' else concat('-',FORMAT(C.dicountAmount , 0),'원')  end as price ,C.dicountAmount as couponPrice from UserCoupon\n" +
                "left join Store S on UserCoupon.couponIdx = S.couponIdx\n" +
                "left join Coupon C on S.couponIdx = C.couponIdx\n" +
                "where UserCoupon.userIdx=? and S.storeIdx=?";
        Object[] getStoreParams = new Object[]{ price,userIdx,storeIdx};
        return this.jdbcTemplate.queryForObject(getStoreQuery,
                (rs, rowNum) -> new GetCouponCountRes(
                        rs.getString("count"),
                        rs.getString("price"),
                rs.getInt("couponPrice")),

                getStoreParams);
    }



    public int checkStoreIdx(int  storeIdx){
        String checkCategoryQuery = "select exists(select storeIdx from Store where storeIdx = ?)";
        int checkCategoryParams = storeIdx;
        return this.jdbcTemplate.queryForObject(checkCategoryQuery,
                int.class,
                checkCategoryParams);
    }
    public int checkStoreStatus(int  storeIdx){
        String checkCategoryQuery = "select case when status='ACTIVE' then 1 else 0 end from Store where storeIdx=?";
        int checkCategoryParams = storeIdx;
        return this.jdbcTemplate.queryForObject(checkCategoryQuery,
                int.class,
                checkCategoryParams);
    }

    public List<GetCouponsRes> getCoupons(int price,int userIdx, int storeIdx){
        String getStoreQuery ="select  distinct C.couponIdx,C.information,C.name,concat(FORMAT(C.minimumAmount,0),'원 이상 주문 시') as minimumAmount,case when C.status='INACTIVE' then '기간만료' else concat( DATE_FORMAT(C.endTime, '%m/%d' ),' 까지') end as endTime\n" +
                ",case when  (select  Store.couponIdx from Store where Store.storeIdx=?) =C.couponIdx and C.minimumAmount<? then 'Y' else 'N' end as isAvailable\n" +
                "from UserCoupon\n" +
                "  left join Store S on UserCoupon.couponIdx = S.couponIdx\n" +
                " left join Coupon C on S.couponIdx = C.couponIdx\n" +
                "where UserCoupon.userIdx=?\n" +
                "order by C.endTime desc;";
        Object[] getStoreParams = new Object[]{ storeIdx,price,userIdx};
        return this.jdbcTemplate.query(getStoreQuery,
                (rs, rowNum) -> new GetCouponsRes(
                        rs.getInt("couponIdx"),
                        rs.getString("information"),
                        rs.getString("name"),
                        rs.getString ("minimumAmount"),
                        rs.getString("endTime"),
                        rs.getString("isAvailable")
                ),

                getStoreParams);
    }

}




