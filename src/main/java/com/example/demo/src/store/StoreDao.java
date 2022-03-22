package com.example.demo.src.store;


import com.example.demo.src.menu.model.GetSubSideCategoryRes;
import com.example.demo.src.store.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class StoreDao {

    private JdbcTemplate jdbcTemplate;
    private List<GetStoreMenusRes> getStoreMenusRes;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


//스토어의 정보 -로그인 시
    public GetStoreRes getStore(int userIdx,int storeIdx){
        String getStoreQuery ="select Store.imgUrl1,Store.imgUrl2,Store.imgUrl3,case when EXISTS (select * from Liked where userIdx=? AND  Store.storeIdx=?)=1 then 'Liked' else '-' end as liked ,Store.name,case when count(distinct R.reviewIdx)<10 then '-' else sum(distinct R.star)/count(distinct R.reviewIdx) end as star,count(distinct R.reviewIdx) as reviewNumber,Coupon.name as couponName, Coupon.couponIdx, Store.isFast,deliveryMinTime,deliveryMaxTime,deliveryMinCost,deliveryMaxCost,Store.minimumAmount from Store left join Liked L on Store.storeIdx = L.storeIdx\n" +
                "                                                                                                                                                                                                                                                                                                                                                                                                                                        left join Coupon on Store.couponIdx = Coupon.couponIdx\n" +
                "    left join MenuCategory MC on Store.storeIdx = MC.storeIdx left join Menu M on MC.menuCategoryIdx = M.menuCategoryIdx left join MenuSideCateg MSC on M.menuIdx = MSC.menuIdx left join SideCategory SC on MSC.sideCategoryIdx = SC.sideCategoryIdx left join SubSideCategory SSC on SC.sideCategoryIdx = SSC.sideCategoryIdx left join OrderSM OS on SSC.subSideCategoryIdx = OS.subSideCategoryIdx left join OrderMenu OM on OS.orderMenuIdx = OM.orderMenuIdx left join Orders O on OM.orderIdx = O.orderIdx left join Review R on O.orderIdx = R.orderIdx where Store.storeIdx=?";
        Object[] getStoreParams = new Object[]{userIdx,storeIdx,storeIdx};
        return this.jdbcTemplate.queryForObject(getStoreQuery,
                (rs, rowNum) -> new GetStoreRes(
                        rs.getString("imgUrl1"),
                        rs.getString("imgUrl2"),
                        rs.getString("imgUrl3"),
                rs.getString("liked"),
                rs.getString("name"),
                rs.getString("star"),
                rs.getInt("reviewNumber"),
                        rs.getString("couponName"),
                        rs.getInt("couponIdx"),
                        rs.getString("isFast"),
                rs.getInt("deliveryMinTime"),
                        rs.getInt("deliveryMaxTime"),
                        rs.getInt("deliveryMinCost"),
                        rs.getInt("deliveryMaxCost"),
                        rs.getInt("minimumAmount")),
                getStoreParams);
    }
    //스토어의 정보-비로그인
    public GetStoreRes getStore(int storeIdx){
        String getStoreQuery ="select Store.imgUrl1,Store.imgUrl2,Store.imgUrl3,'-'  as liked ,Store.name,case when count(distinct R.reviewIdx)<10 then '-' else sum(distinct R.star)/count(distinct R.reviewIdx) end as star,count(distinct R.reviewIdx) as reviewNumber,Coupon.name as couponName, Coupon.couponIdx, Store.isFast ,deliveryMinTime,deliveryMaxTime,deliveryMinCost,deliveryMaxCost,Store.minimumAmount from Store left join Liked L on Store.storeIdx = L.storeIdx left join MenuCategory MC on Store.storeIdx = MC.storeIdx left join Menu M on MC.menuCategoryIdx = M.menuCategoryIdx\n" +
                "    left join Coupon on Store.couponIdx = Coupon.couponIdx\n" +
                "    left join MenuSideCateg MSC on M.menuIdx = MSC.menuIdx left join SideCategory SC on MSC.sideCategoryIdx = SC.sideCategoryIdx left join SubSideCategory SSC on SC.sideCategoryIdx = SSC.sideCategoryIdx left join OrderSM OS on SSC.subSideCategoryIdx = OS.subSideCategoryIdx left join OrderMenu OM on OS.orderMenuIdx = OM.orderMenuIdx left join Orders O on OM.orderIdx = O.orderIdx left join Review R on O.orderIdx = R.orderIdx where Store.storeIdx=?\n" ;
        Object[] getStoreParams = new Object[]{storeIdx};
        return this.jdbcTemplate.queryForObject(getStoreQuery,
                (rs, rowNum) -> new GetStoreRes(
                        rs.getString("imgUrl1"),
                        rs.getString("imgUrl2"),
                        rs.getString("imgUrl3"),
                        rs.getString("liked"),
                        rs.getString("name"),
                        rs.getString("star"),
                        rs.getInt("reviewNumber"),
                        rs.getString("couponName"),
                        rs.getInt("couponIdx"),
                        rs.getString("isFast"),
                        rs.getInt("deliveryMinTime"),
                        rs.getInt("deliveryMaxTime"),
                        rs.getInt("deliveryMinCost"),
                        rs.getInt("deliveryMaxCost"),
                        rs.getInt("minimumAmount")),
                getStoreParams);
    }
    public GetStoreDetailRes getStoreDetail(int storeIdx){
        String getStoreDetailQuery = "select name,phone,address,latitude,longitude,representative,companyNumber,businessName, openingHours,introduce,notice,originInformation from Store where storeIdx=?";
        Object[] getStoreDetailParams = new Object[]{storeIdx};
        return this.jdbcTemplate.queryForObject(getStoreDetailQuery,
                (rs, rowNum) -> new GetStoreDetailRes(
                        rs.getString("name"),
                        rs.getString("phone"),
                        rs.getString("address"),
                        rs.getDouble("latitude"),
                        rs.getDouble("longitude"),
                        rs.getString("representative"),
                        rs.getString("companyNumber"),
                        rs.getString("businessName"),
                        rs.getString("openingHours"),
                        rs.getString("introduce"),
                        rs.getString("notice"),
                        rs.getString("originInformation")
                ),
                getStoreDetailParams);
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


    public List<GetPhotoReviewsRes> getPhotoReviews(int storeIdx){
        String getPhotoReviewsQuery = "select distinct Review.reviewIdx,Review.imgUrl, reviewText,star from Review\n" +
                "left join Orders O on Review.orderIdx = O.orderIdx\n" +
                "left join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                "left join Menu M on OM.menuIdx = M.menuIdx\n" +
                "left join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                "left join Store S on MC.storeIdx = S.storeIdx\n" +
                "where S.storeIdx=? and Review.isPhoto='Y'\n" +
                "order by Review.createdAt limit 3" ;
        Object[] getPhotoReviewsParams = new Object[]{storeIdx};
        return this.jdbcTemplate.query(getPhotoReviewsQuery,
                (rs, rowNum) -> new GetPhotoReviewsRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("imgUrl"),
                        rs.getString("reviewText"),
                        rs.getFloat("star")

                ),
                getPhotoReviewsParams);
    }

    public List<GetStoreCategorysRes> getStoreCategorys(int storeIdx){
        String getStoreCategorysQuery = "select distinct MenuCategory.menuCategoryIdx, MenuCategory.name , MenuCategory.menuCategoryIdx from  MenuCategory\n" +
                "where storeIdx=?" ;
        Object[] getStoreCategorysParams = new Object[]{storeIdx};
        return this.jdbcTemplate.query(getStoreCategorysQuery,
                (rs, rowNum) -> new GetStoreCategorysRes(
                        rs.getInt("menuCategoryIdx"),
                        rs.getString("name"),
                        getStoreMenusRes=this.jdbcTemplate.query("select  Menu.menuIdx,case when isManyOrders='Y' then '주문많음' else '-' end as isManyOrders, case when isGoodReview='Y' then '리뷰최고' else '-' end as isGoodReview,Menu.name,Menu.price,Menu.imgUrl1, Menu.information from Menu\n" +
                                "where menuCategoryIdx=?",
                                (rk,rownum) -> new GetStoreMenusRes(
                                        rk.getInt("menuIdx"),
                                        rk.getString("isManyOrders"),
                                        rk.getString("isGoodReview"),
                                        rk.getString("name"),
                                        rk.getInt("price"),
                                        rk.getString("imgUrl1"),
                                        rk.getString("information")

                                ),rs.getInt("menuCategoryIdx"))



                ),
                getStoreCategorysParams);
    }




    public List<GetStoreDeliveryCostRes> getStoreDeliveryCost(int storeIdx){
        String getStoreDeliveryCostQuery = "select minCost,maxCost, deliveryCost from DeliveryCost where storeIdx=? order by deliveryCost desc";
        Object[] getStoreDeliveryParams = new Object[]{storeIdx};
        return this.jdbcTemplate.query(getStoreDeliveryCostQuery,
                (rs, rowNum) -> new GetStoreDeliveryCostRes(
                        rs.getInt("minCost"),
                        rs.getInt("maxCost"),
                        rs.getInt("deliveryCost")




                ),
                getStoreDeliveryParams);
    }
}




