package com.example.demo.src.home;


import com.example.demo.src.home.model.*;
import com.example.demo.src.order.model.GetOrderMenuRes;
import com.example.demo.src.order.model.GetOrderRes;
import com.example.demo.src.order.model.GetOrderSideMenuRes;
import com.example.demo.src.store.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class HomeDao {

    private JdbcTemplate jdbcTemplate;
    private List<GetStoreMenusRes> getStoreMenusRes;
    private List<GetEventsRes> getEventsRes;
    private List<GetNewStoresRes> getNewStoresRes;
    private List<GetNewStoresRes> getOnlyEatsRes;
    private List<GetNewStoresRes> getHotFranchisesRes;
    private List<GetNewStoresRes> getIsSaleRes;
    private GetHomeEventRes getHomeEventRes=new GetHomeEventRes(8,"https://dev.developer-hyun.com/Image/event_friend.jpg");
    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GetHomeRes getHome(int addressIdx){



        Object[] getParams = new Object[]{addressIdx,addressIdx};
       return new GetHomeRes(




                        getEventsRes=this.jdbcTemplate.query("select eventIdx,bannerImgUrl From Event\n" +
                                        "where  Event.status='ACTIVE'\n" +
                                        "\n" +
                                        "order by Event.createdAt",
                                (rk,rownum) -> new GetEventsRes(
                                        rk.getInt("eventIdx"),
                                        rk.getString("bannerImgUrl")

                                )),
               getIsSaleRes=this.jdbcTemplate.query("select distinct Store.storeIdx,Store.imgUrl1 ,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                               "               Store.isEatsOriginal, Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                               "\n" +
                               "    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                               "                                                                                                                                                                                                                                                                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                               "                                                                                                                                                                                                                                                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                               "        ,(select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                               "                                        left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                               "                                        left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx ) as OrderIdxx,\n" +
                               "                (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                               "                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                               "                         left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName from Store\n" +

                               "                           left join Coupon C on Store.couponIdx = C.couponIdx" +
                               "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                               "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                               "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                               "where Store.isSale='Y' " +

                               "      and  (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +

                               "            -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                               "order by  Store.createdAt desc",
                       (rk,rownum) -> new GetNewStoresRes(
                               rk.getInt("storeIdx"),
                               rk.getString("imgUrl1"),

                               rk.getString("isFast"),
                               rk.getInt("deliveryMinTime"),
                               rk.getInt("deliveryMaxTime"),
                               rk.getString("isEatsOriginal"),
                               rk.getString("name"),
                               rk.getString("star"),
                               rk.getInt("reviewNumber"),
                               rk.getString("distance"),
                               rk.getInt("deliveryMinCost"),
                               rk.getInt("couponIdx"),
                               rk.getString("couponName")

                       ),getParams),
                        getOnlyEatsRes=this.jdbcTemplate.query("select distinct Store.storeIdx,Store.imgUrl1 ,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                                        "               Store.isEatsOriginal, Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                                        "\n" +
                                        "    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                                        "                                                                                                                                                                                                                                                                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                                        "                                                                                                                                                                                                                                                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                                        "        ,(select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                                        "                                        left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                                        "                                        left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx ) as OrderIdxx,\n" +
                                        "                (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                                        "                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                                        "                         left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName from Store\n" +

                                        "                           left join Coupon C on Store.couponIdx = C.couponIdx" +
                                        "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                                        "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                                        "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                                        "where Store.isEatsOriginal='Y' " +

                                        "      and  (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +

                                        "            -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                                        "order by  Store.createdAt desc",
                                (rk,rownum) -> new GetNewStoresRes(
                                        rk.getInt("storeIdx"),
                                        rk.getString("imgUrl1"),

                                        rk.getString("isFast"),
                                        rk.getInt("deliveryMinTime"),
                                        rk.getInt("deliveryMaxTime"),
                                        rk.getString("isEatsOriginal"),
                                        rk.getString("name"),
                                        rk.getString("star"),
                                        rk.getInt("reviewNumber"),
                                        rk.getString("distance"),
                                        rk.getInt("deliveryMinCost"),
                                        rk.getInt("couponIdx"),
                                        rk.getString("couponName")

                                ),getParams),
                        getHotFranchisesRes=this.jdbcTemplate.query(" select Store.storeIdx,Store.imgUrl1,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                                "      Store.isEatsOriginal, Store.name, round(sum( R.star)/count( R.reviewIdx),1  )as star,count(distinct R.reviewIdx) as reviewNumber, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                                "\n" +
                                "    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km' )from Address where Address.addressIdx=?) as distance, deliveryMinCost,C.couponIdx,C.name as couponName from Store\n" +
                                "     left join MenuCategory MC on Store.storeIdx = MC.storeIdx left join Menu M on MC.menuCategoryIdx = M.menuCategoryIdx\n" +
                                "     left join MenuSideCateg MSC on M.menuIdx = MSC.menuIdx\n" +
                                "     left join SideCategory SC on MSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                                "     left join SubSideCategory SSC on SC.sideCategoryIdx = SSC.sideCategoryIdx\n" +
                                "     left join OrderSM OS on SSC.subSideCategoryIdx = OS.subSideCategoryIdx\n" +
                                "     left join OrderMenu OM on OS.orderMenuIdx = OM.orderMenuIdx\n" +
                                "     left join Orders O on OM.orderIdx = O.orderIdx\n" +
                                "     left join Review R on O.orderIdx = R.orderIdx\n" +
                                "    left join StoreCategory S on Store.storeIdx = S.storeIdx\n left join Coupon C on Store.couponIdx = C.couponIdx" +
                                "\n" +
                                "    where S.subCategoryIdx=100 and Store.Status='ACTIVE' and\n" +
                                "\n" +
                                "        (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                                "\n" +
                                "     -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                                " group by Store.storeIdx\n" +
                                "    order by sum(R.star)/count( R.reviewIdx) desc limit 10" ,
                                (rk,rownum) -> new GetNewStoresRes(
                                        rk.getInt("storeIdx"),
                                        rk.getString("imgUrl1"),

                                        rk.getString("isFast"),
                                        rk.getInt("deliveryMinTime"),
                                        rk.getInt("deliveryMaxTime"),
                                        rk.getString("isEatsOriginal"),
                                        rk.getString("name"),
                                        rk.getString("star"),
                                        rk.getInt("reviewNumber"),
                                        rk.getString("distance"),
                                        rk.getInt("deliveryMinCost"),
                                        rk.getInt("couponIdx"),
                                        rk.getString("couponName")

                                ),getParams ),
                       getHomeEventRes,
                        getNewStoresRes=this.jdbcTemplate.query("select distinct Store.storeIdx,Store.imgUrl1 ,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                                        "               Store.isEatsOriginal, Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                                        "\n" +
                                        "    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                                        "                                                                                                                                                                                                                                                                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                                        "                                                                                                                                                                                                                                                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                                        "        ,(select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                                        "                                        left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                                        "                                        left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx ) as OrderIdxx,\n" +
                                        "                (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                                        "                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                                        "                         left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName from Store\n" +

                                        "                           left join Coupon C on Store.couponIdx = C.couponIdx" +
                                        "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                                        "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                                        "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                                        "where Store.isNew='Y' " +

                                        "      and  (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +

                                        "            -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                                        "order by  Store.createdAt desc",
                                (rk,rownum) -> new GetNewStoresRes(
                                        rk.getInt("storeIdx"),
                                        rk.getString("imgUrl1"),

                                        rk.getString("isFast"),
                                        rk.getInt("deliveryMinTime"),
                                        rk.getInt("deliveryMaxTime"),
                                        rk.getString("isEatsOriginal"),
                                        rk.getString("name"),
                                        rk.getString("star"),
                                        rk.getInt("reviewNumber"),
                                        rk.getString("distance"),
                                        rk.getInt("deliveryMinCost"),
                                        rk.getInt("couponIdx"),
                                        rk.getString("couponName")

                                ),getParams)



                );


    }

//인기 프랜차이즈

    public List<GetNewStoresCategoryRes> getNewStoresCategory(int addressIdx, int categoryIdx){
        String getNewStoresQuery ="select distinct Store.storeIdx,Store.imgUrl1 ,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
        "             Store.isEatsOriginal,   Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                "\n" +
                "    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                "                                                                                                                                                                                                                                                                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                "                                                                                                                                                                                                                                                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                "        ,(select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                "                                        left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                "                                        left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx ) as OrderIdxx,\n" +
                "                (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                "                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                "                         left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName" +
                ",Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +

                "                           left join Coupon C on Store.couponIdx = C.couponIdx" +
                "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                "where Store.isNew='Y' and  C2.categoryIdx=?" +

                "      and  (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +

                "            -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                 "order by  Store.createdAt desc ";

        Object[] getNewStoresParams = new Object[]{addressIdx,categoryIdx,addressIdx};
        return this.jdbcTemplate.query(getNewStoresQuery,
                (rs, rowNum) -> new GetNewStoresCategoryRes(
                        rs.getInt("storeIdx"),
                        rs.getString("imgUrl1"),

                        rs.getString("isFast"),
                        rs.getInt("deliveryMinTime"),
                        rs.getInt("deliveryMaxTime"),
                        rs.getString("isEatsOriginal"),
                        rs.getString("name"),
                        rs.getString("star"),
                        rs.getInt("reviewNumber"),
                        rs.getString("distance"),
                        rs.getInt("deliveryMinCost"),
                        rs.getInt("couponIdx"),
                        rs.getString("couponName"),
                        rs.getString("isOpened"),
                        rs.getString("closeMent")

                ),
                getNewStoresParams);
    }

    public int checkCategoryIdx(int  categoryIdx){
        String checkCategoryQuery = "select exists(select categoryIdx from Category where categoryIdx = ?)";
        int checkCategoryParams = categoryIdx;
        return this.jdbcTemplate.queryForObject(checkCategoryQuery,
                int.class,
                checkCategoryParams);
    }
    public int checkAddressIdx(int  addressIdx){
        String checkAddressQuery = "select exists(select addressIdx from Address where addressIdx = ?)";
        int checkAddressParams = addressIdx;
        return this.jdbcTemplate.queryForObject(checkAddressQuery,
                int.class,
                checkAddressParams);
    }

    public List<GetStoresCategoryRes> getStoresCategory(int order, String isFast, int deliveryCost, int minimumAmount, int coupon, int addressIdx, int categoryIdx){
        String getStoresQuery;
        if(order==2){
            getStoresQuery ="select distinct Store.storeIdx,Store.imgUrl1,Store.menuImg1 ,Store.menuImg2 ,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "          Store.isEatsOriginal,  Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                   -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                       left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                       left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "                ,\n" +
                    "                    (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                  left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    " ,Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +
            "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where C2.categoryIdx=? and" +
                    "                case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "                    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +
                    "               (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "                 #   group by(select count(distinct O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                   #                           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                     #                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                    order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    when 1 then (select count(distinct  O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                 left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                           when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "             when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    end ;";}
        else if(order==1){
            getStoresQuery="select distinct Store.storeIdx,Store.imgUrl1,Store.menuImg1 ,Store.menuImg2 ,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "         Store.isEatsOriginal,            Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                   -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                       left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                       left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "                ,\n" +
                    "                    (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                  left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    " ,Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent  from Store\n" +

                    "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where C2.categoryIdx=? and" +

                    "             case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "                    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +

                    "               (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +

                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "                    group by(select count(distinct O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                              left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                          left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                    order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    when 1 then (select count(distinct  O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                 left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                           when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "             when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    end desc ";
        }

        else  if(order<4){
            getStoresQuery ="select distinct Store.storeIdx,Store.imgUrl1,Store.menuImg1 ,Store.menuImg2 ,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "           Store.isEatsOriginal,          Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                   -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) ,'km')from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                       left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                       left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "                ,\n" +
                    "                    (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                  left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    " ,Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +
                    "\n" +
                    "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where C2.categoryIdx=? and" +

                    "            case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "                    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +
                    "\n" +
                    "               (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "                 #   group by(select count(distinct O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                   #                           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                     #                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                    order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    when 1 then (select count(distinct  O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                 left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                           when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "             when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    end desc ";}
        else
        {
            getStoresQuery ="select distinct Store.storeIdx,Store.imgUrl1 ,Store.menuImg1,Store.menuImg2,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "                Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                                                                                                                                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                                                                                                                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "        ,(select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                        left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                        left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx ) as OrderIdxx,\n" +
                    "                (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                         left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    " ,Store.isEatsOriginal,Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +

                    "                           left join Coupon C on Store.couponIdx = C.couponIdx" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where C2.categoryIdx=? and\n" +

                    "case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +
                    "\n" +
                    "        (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "            -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "#group by(select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    " #                                      left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "  #                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                               left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                when 1 then (select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                           left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "                when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "          else  Store.createdAt end desc;";
        }
        Object[] getStoresParams = new Object[]{addressIdx,categoryIdx,isFast,deliveryCost,minimumAmount,coupon,addressIdx,order,addressIdx};
        return this.jdbcTemplate.query(getStoresQuery,
                (rs, rowNum) -> new GetStoresCategoryRes(
                        rs.getInt("storeIdx"),
                        rs.getString("imgUrl1"),
                        rs.getString("menuImg1"),
                        rs.getString("menuImg2"),
                        rs.getString("name"),
                        rs.getString("isNew"),
                        rs.getString("isFast"),
                        rs.getInt("deliveryMinTime"),
                        rs.getInt("deliveryMaxTime"),
                        rs.getString("isEatsOriginal"),
                        rs.getString("star"),
                        rs.getInt("reviewNumber"),
                        rs.getString("distance"),
                        rs.getInt("deliveryMinCost"),
                        rs.getInt("couponIdx"),
                        rs.getString("couponName"),
                        rs.getString("isOpened"),
                        rs.getString("closeMent")

                ),
                getStoresParams);
    }

    public int checkAddressStatus(int  addressIdx){
        String checkAddressQuery = "select case when (status='ACTIVE')   then 1 else 0 end from Address where addressIdx=?";
        int checkAddressParams = addressIdx;
        return this.jdbcTemplate.queryForObject(checkAddressQuery,
                int.class,
                checkAddressParams);
    }
    public int checkCategoryStatus(int  categoryIdx){
        String checkCategoryQuery = "select case when (status='ACTIVE')   then 1 else 0 end from Category where categoryIdx=?";
        int checkCategoryParams = categoryIdx;
        return this.jdbcTemplate.queryForObject(checkCategoryQuery,
                int.class,
                checkCategoryParams);
    }
    public List<GetStoresRes> getAroundStores(int order,String isFast,int deliveryCost,int minimumAmount,int coupon,int addressIdx){
        String getStoresQuery;
        if(order==2){
            getStoresQuery ="select distinct Store.storeIdx,Store.imgUrl1,Store.menuImg1 ,Store.menuImg2 ,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "         Store.isEatsOriginal,            Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                   -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) ,'km')from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                       left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                       left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "                ,\n" +
                    "                    (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                  left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName from Store\n" +
                    "\n" +
                    "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +

                    "                where Store.Status='ACTIVE'  and  case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "                    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +
                    "\n" +
                    "               (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "                 #   group by(select count(distinct O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                   #                           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                     #                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                    order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    when 1 then (select count(distinct  O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                 left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                           when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "             when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    end ;";}
     else if(order==1){
         getStoresQuery="select distinct Store.storeIdx,Store.imgUrl1,Store.menuImg1 ,Store.menuImg2 ,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                 "           Store.isEatsOriginal,          Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                 "\n" +
                 "                   -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                 "                                                       left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                 "                       left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                 "                ,\n" +
                 "                    (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                 "                  left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                 "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName from Store\n" +
                 "\n" +
                 "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +

                 "                where Store.Status='ACTIVE'  and  case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                 "                    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +

                 "               (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +

                 "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                 "                    group by(select count(distinct O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                 "                                              left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                 "                                          left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                 "                    order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                 "                                                                                                                                                     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                 "                                                                                                                                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                 "                    when 1 then (select count(distinct  O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                 "                                 left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                 "                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                 "                           when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                 "\n" +
                 "                -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                 "             when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                 "     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                 "    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                 "                    end desc ;";
        }

       else  if(order<4){
            getStoresQuery ="select distinct Store.storeIdx,Store.imgUrl1,Store.menuImg1 ,Store.menuImg2 ,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "          Store.isEatsOriginal,           Store.name, (select concat( round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                   -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) ,'km')from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                       left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                       left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "                ,\n" +
                    "                    (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                  left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName from Store\n" +
                    "\n" +
                    "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +

                    "                where   Store.Status='ACTIVE'  and case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "                    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +
                    "\n" +
                    "               (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "                 #   group by(select count(distinct O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                   #                           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                     #                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                    order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    when 1 then (select count(distinct  O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                 left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                           when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "             when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    end desc ";}
        else
        {
            getStoresQuery ="select distinct Store.storeIdx,Store.imgUrl1 ,Store.menuImg1,Store.menuImg2,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "            Store.isEatsOriginal,    Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                                                                                                                                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                                                                                                                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "        ,(select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                        left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                        left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx ) as OrderIdxx,\n" +
                    "                (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                         left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName from Store\n" +

                    "                           left join Coupon C on Store.couponIdx = C.couponIdx\n" +

                    "where Store.Status='ACTIVE'  and  case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +
                    "\n" +
                    "        (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "            -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "#group by(select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    " #                                      left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "  #                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                               left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                when 1 then (select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                           left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "                when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "          else  Store.createdAt end desc;";
        }

        Object[] getStoresParams = new Object[]{addressIdx,isFast,deliveryCost,minimumAmount,coupon,addressIdx,order,addressIdx};
        return this.jdbcTemplate.query(getStoresQuery,
                (rs, rowNum) -> new GetStoresRes(
                        rs.getInt("storeIdx"),
                        rs.getString("imgUrl1"),
                        rs.getString("menuImg1"),
                        rs.getString("menuImg2"),
                        rs.getString("name"),
                        rs.getString("isNew"),
                        rs.getString("isFast"),
                        rs.getInt("deliveryMinTime"),
                        rs.getInt("deliveryMaxTime"),
rs.getString("isEatsOriginal"),
                        rs.getString("star"),
                        rs.getInt("reviewNumber"),
                        rs.getString("distance"),
                        rs.getInt("deliveryMinCost"),
                        rs.getInt("couponIdx"),
                        rs.getString("couponName")

                ),
                getStoresParams);
    }



    public List<GetStoresCategoryRes> getNewStoresCategory(int order, String isFast, int deliveryCost, int minimumAmount, int coupon, int addressIdx){
        String getStoresQuery;
        if(order==2){
            getStoresQuery ="select distinct Store.storeIdx,Store.imgUrl1,Store.menuImg1 ,Store.menuImg2 ,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "          Store.isEatsOriginal,           Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                   -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                       left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                       left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "                ,\n" +
                    "                    (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                  left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    " ,Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +
                    "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where  Store.isNew='Y'  and Store.Status='ACTIVE' and" +
                    "                case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "                    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +
                    "               (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "                 #   group by(select count(distinct O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                   #                           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                     #                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                    order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    when 1 then (select count(distinct  O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                 left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                           when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "             when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    end ;";}
        else if(order==1){
            getStoresQuery="select distinct Store.storeIdx,Store.imgUrl1,Store.menuImg1 ,Store.menuImg2 ,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "         Store.isEatsOriginal,             Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                   -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                       left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                       left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "                ,\n" +
                    "                    (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                  left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName," +
                    "Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +

                    "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where Store.isNew='Y' and Store.Status='ACTIVE'   and" +

                    "             case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "                    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +

                    "               (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +

                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "                    group by(select count(distinct O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                              left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                          left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                    order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    when 1 then (select count(distinct  O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                 left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                           when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "             when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    end desc ";
        }

        else  if(order<4){
            getStoresQuery ="select distinct Store.storeIdx,Store.imgUrl1,Store.menuImg1 ,Store.menuImg2 ,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "               Store.isEatsOriginal,      Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                   -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) ,'km')from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                       left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                       left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "                ,\n" +
                    "                    (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                  left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    " ,Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +
                    "\n" +
                    "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where Store.isNew='Y'  and Store.Status='ACTIVE' and" +

                    "            case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "                    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +
                    "\n" +
                    "               (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "                 #   group by(select count(distinct O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                   #                           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                     #                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                    order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    when 1 then (select count(distinct  O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                 left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                           when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "             when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    end desc ";}
        else
        {
            getStoresQuery ="select distinct Store.storeIdx,Store.imgUrl1 ,Store.menuImg1,Store.menuImg2,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "                Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                                                                                                                                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                                                                                                                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "        ,(select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                        left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                        left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx ) as OrderIdxx,\n" +
                    "                (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                         left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    " ,Store.isEatsOriginal,Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +

                    "                           left join Coupon C on Store.couponIdx = C.couponIdx" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where Store.isNew='Y' and Store.Status='ACTIVE' and\n" +

                    "case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +
                    "\n" +
                    "        (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "            -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "#group by(select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    " #                                      left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "  #                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                               left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                when 1 then (select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                           left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "                when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "          else  Store.createdAt end desc;";
        }
        Object[] getStoresParams = new Object[]{addressIdx,isFast,deliveryCost,minimumAmount,coupon,addressIdx,order,addressIdx};
        return this.jdbcTemplate.query(getStoresQuery,
                (rs, rowNum) -> new GetStoresCategoryRes(
                        rs.getInt("storeIdx"),
                        rs.getString("imgUrl1"),
                        rs.getString("menuImg1"),
                        rs.getString("menuImg2"),
                        rs.getString("name"),
                        rs.getString("isNew"),
                        rs.getString("isFast"),
                        rs.getInt("deliveryMinTime"),
                        rs.getInt("deliveryMaxTime"),
        rs.getString("isEatsOriginal"),
                        rs.getString("star"),
                        rs.getInt("reviewNumber"),
                        rs.getString("distance"),
                        rs.getInt("deliveryMinCost"),
                        rs.getInt("couponIdx"),
                        rs.getString("couponName"),
                        rs.getString("isOpened"),
                        rs.getString("closeMent")

                ),
                getStoresParams);
    }

    public List<GetStoresCategoryRes> getHotFranchisesCategory(int order, String isFast, int deliveryCost, int minimumAmount, int coupon, int addressIdx){
        String getStoresQuery;
        if(order==2){
            getStoresQuery ="select distinct Store.storeIdx,Store.imgUrl1,Store.menuImg1 ,Store.menuImg2 ,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "                     Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                   -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                       left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                       left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "                ,\n" +
                    "                    (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                  left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    " ,Store.isEatsOriginal,Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +
                    "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where S.subCategoryIdx=100 and Store.Status='ACTIVE' and" +
                    "                case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "                    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +
                    "               (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "                 #   group by(select count(distinct O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                   #                           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                     #                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                    order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    when 1 then (select count(distinct  O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                 left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                           when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "             when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    end ;";}
        else if(order==1){
            getStoresQuery="select distinct Store.storeIdx,Store.imgUrl1,Store.menuImg1 ,Store.menuImg2 ,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "                     Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                   -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                       left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                       left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "                ,\n" +
                    "                    (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                  left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    " ,Store.isEatsOriginal,Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from  Store\n" +

                    "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where S.subCategoryIdx=100 and Store.Status='ACTIVE' and" +

                    "             case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "                    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +

                    "               (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +

                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "                    group by(select count(distinct O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                              left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                          left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                    order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    when 1 then (select count(distinct  O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                 left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                           when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "             when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    end desc ";
        }

        else  if(order<4){
            getStoresQuery ="select distinct Store.storeIdx,Store.imgUrl1,Store.menuImg1 ,Store.menuImg2 ,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "                     Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                   -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) ,'km')from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                       left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                       left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "                ,\n" +
                    "                    (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                  left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    " ,Store.isEatsOriginal,Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +
                    "\n" +
                    "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where  S.subCategoryIdx=100 and Store.Status='ACTIVE' and" +

                    "            case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "                    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +
                    "\n" +
                    "               (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "                 #   group by(select count(distinct O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                   #                           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                     #                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                    order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    when 1 then (select count(distinct  O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                 left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                           when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "             when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    end desc ";}
        else
        {
            getStoresQuery ="select distinct Store.storeIdx,Store.imgUrl1 ,Store.menuImg1,Store.menuImg2,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "                Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                                                                                                                                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                                                                                                                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "        ,(select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                        left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                        left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx ) as OrderIdxx,\n" +
                    "                (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                         left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    " ,Store.isEatsOriginal,Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +

                    "                           left join Coupon C on Store.couponIdx = C.couponIdx" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where   S.subCategoryIdx=100 and Store.Status='ACTIVE'and\n" +

                    "case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +
                    "\n" +
                    "        (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "            -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "#group by(select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    " #                                      left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "  #                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                               left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                when 1 then (select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                           left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "                when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "          else  Store.createdAt end desc;";
        }
        Object[] getStoresParams = new Object[]{addressIdx,isFast,deliveryCost,minimumAmount,coupon,addressIdx,order,addressIdx};
        return this.jdbcTemplate.query(getStoresQuery,
                (rs, rowNum) -> new GetStoresCategoryRes(
                        rs.getInt("storeIdx"),
                        rs.getString("imgUrl1"),
                        rs.getString("menuImg1"),
                        rs.getString("menuImg2"),
                        rs.getString("name"),
                        rs.getString("isNew"),
                        rs.getString("isFast"),
                        rs.getInt("deliveryMinTime"),
                        rs.getInt("deliveryMaxTime"),
                        rs.getString("isEatsOriginal"),

                        rs.getString("star"),
                        rs.getInt("reviewNumber"),
                        rs.getString("distance"),
                        rs.getInt("deliveryMinCost"),
                        rs.getInt("couponIdx"),
                        rs.getString("couponName"),
                        rs.getString("isOpened"),
                        rs.getString("closeMent")

                ),
                getStoresParams);
    }


    public List<GetStoresCategoryRes> getOnlyEatsList(int order, String isFast, int deliveryCost, int minimumAmount, int coupon, int addressIdx){
        String getStoresQuery;
        if(order==2){
            getStoresQuery ="select distinct Store.storeIdx,Store.imgUrl1,Store.menuImg1 ,Store.menuImg2 ,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "                     Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                   -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                       left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                       left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "                ,\n" +
                    "                    (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                  left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    " ,Store.isEatsOriginal,Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +
                    "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where Store.isEatsOriginal='Y' and Store.Status='ACTIVE' and" +
                    "                case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "                    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +
                    "               (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "                 #   group by(select count(distinct O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                   #                           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                     #                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                    order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    when 1 then (select count(distinct  O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                 left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                           when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "             when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    end ;";}
        else if(order==1){
            getStoresQuery="select distinct Store.storeIdx,Store.imgUrl1,Store.menuImg1 ,Store.menuImg2 ,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "                     Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                   -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                       left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                       left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "                ,\n" +
                    "                    (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                  left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    " ,Store.isEatsOriginal,Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from  Store\n" +

                    "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where Store.isEatsOriginal='Y' and Store.Status='ACTIVE' and" +

                    "             case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "                    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +

                    "               (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +

                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "                    group by(select count(distinct O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                              left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                          left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                    order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    when 1 then (select count(distinct  O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                 left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                           when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "             when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    end desc ";
        }

        else  if(order<4){
            getStoresQuery ="select distinct Store.storeIdx,Store.imgUrl1,Store.menuImg1 ,Store.menuImg2 ,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "                     Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                   -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) ,'km')from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                       left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                       left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "                ,\n" +
                    "                    (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                  left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    " ,Store.isEatsOriginal,Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +
                    "\n" +
                    "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where  Store.isEatsOriginal='Y' and Store.Status='ACTIVE' and" +

                    "            case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "                    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +
                    "\n" +
                    "               (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "                 #   group by(select count(distinct O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                   #                           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                     #                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                    order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    when 1 then (select count(distinct  O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                 left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                           when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "             when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    end desc ";}
        else
        {
            getStoresQuery ="select distinct Store.storeIdx,Store.imgUrl1 ,Store.menuImg1,Store.menuImg2,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "                Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                                                                                                                                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                                                                                                                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "        ,(select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                        left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                        left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx ) as OrderIdxx,\n" +
                    "                (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                         left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    " ,Store.isEatsOriginal,Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +

                    "                           left join Coupon C on Store.couponIdx = C.couponIdx" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where   Store.isEatsOriginal='Y' and Store.Status='ACTIVE'and\n" +

                    "case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +
                    "\n" +
                    "        (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "            -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "#group by(select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    " #                                      left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "  #                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                               left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                when 1 then (select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                           left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "                when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "          else  Store.createdAt end desc;";
        }
        Object[] getStoresParams = new Object[]{addressIdx,isFast,deliveryCost,minimumAmount,coupon,addressIdx,order,addressIdx};
        return this.jdbcTemplate.query(getStoresQuery,
                (rs, rowNum) -> new GetStoresCategoryRes(
                        rs.getInt("storeIdx"),
                        rs.getString("imgUrl1"),
                        rs.getString("menuImg1"),
                        rs.getString("menuImg2"),
                        rs.getString("name"),
                        rs.getString("isNew"),
                        rs.getString("isFast"),
                        rs.getInt("deliveryMinTime"),
                        rs.getInt("deliveryMaxTime"),
                        rs.getString("isEatsOriginal"),

                        rs.getString("star"),
                        rs.getInt("reviewNumber"),
                        rs.getString("distance"),
                        rs.getInt("deliveryMinCost"),
                        rs.getInt("couponIdx"),
                        rs.getString("couponName"),
                        rs.getString("isOpened"),
                        rs.getString("closeMent")

                ),
                getStoresParams);
    }


    public List<GetStoresCategoryRes> getIsSaleList(int order, String isFast, int deliveryCost, int minimumAmount, int coupon, int addressIdx){
        String getStoresQuery;
        if(order==2){
            getStoresQuery ="select distinct Store.storeIdx,Store.imgUrl1,Store.menuImg1 ,Store.menuImg2 ,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "                     Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                   -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                       left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                       left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "                ,\n" +
                    "                    (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                  left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    " ,Store.isEatsOriginal,Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +
                    "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where Store.isSale='Y' and Store.Status='ACTIVE' and" +
                    "                case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "                    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +
                    "               (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "                 #   group by(select count(distinct O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                   #                           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                     #                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                    order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    when 1 then (select count(distinct  O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                 left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                           when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "             when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    end ;";}
        else if(order==1){
            getStoresQuery="select distinct Store.storeIdx,Store.imgUrl1,Store.menuImg1 ,Store.menuImg2 ,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "                     Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                   -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                       left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                       left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "                ,\n" +
                    "                    (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                  left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    " ,Store.isEatsOriginal,Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from  Store\n" +

                    "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where  Store.isSale='Y' and Store.Status='ACTIVE' and" +

                    "             case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "                    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +

                    "               (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +

                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "                    group by(select count(distinct O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                              left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                          left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                    order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    when 1 then (select count(distinct  O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                 left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                           when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "             when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    end desc ";
        }

        else  if(order<4){
            getStoresQuery ="select distinct Store.storeIdx,Store.imgUrl1,Store.menuImg1 ,Store.menuImg2 ,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "                     Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                   -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) ,'km')from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                       left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                       left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "                ,\n" +
                    "                    (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                  left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    " ,Store.isEatsOriginal,Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +
                    "\n" +
                    "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where  Store.isSale='Y' and Store.Status='ACTIVE' and" +

                    "            case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "                    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +
                    "\n" +
                    "               (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "                 #   group by(select count(distinct O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                   #                           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                     #                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                    order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    when 1 then (select count(distinct  O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                 left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                           when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "             when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "     left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                    end desc ";}
        else
        {
            getStoresQuery ="select distinct Store.storeIdx,Store.imgUrl1 ,Store.menuImg1,Store.menuImg2,Store.isNew,Store.isFast ,Store.deliveryMinTime,Store.deliveryMaxTime,\n" +
                    "                Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1),'km') from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                                                                                                                                                                                                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                                                                                                                                                                                                    left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "        ,(select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                        left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                        left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx ) as OrderIdxx,\n" +
                    "                (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                    left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                         left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    " ,Store.isEatsOriginal,Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +

                    "                           left join Coupon C on Store.couponIdx = C.couponIdx" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where    Store.isSale='Y' and Store.Status='ACTIVE'and\n" +

                    "case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "    case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +
                    "\n" +
                    "        (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "            -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "#group by(select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    " #                                      left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "  #                                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                               left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                when 1 then (select count(*)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                           left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "                when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "\n" +
                    "                    -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "                when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                                                                                left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                                                                                left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "          else  Store.createdAt end desc;";
        }
        Object[] getStoresParams = new Object[]{addressIdx,isFast,deliveryCost,minimumAmount,coupon,addressIdx,order,addressIdx};
        return this.jdbcTemplate.query(getStoresQuery,
                (rs, rowNum) -> new GetStoresCategoryRes(
                        rs.getInt("storeIdx"),
                        rs.getString("imgUrl1"),
                        rs.getString("menuImg1"),
                        rs.getString("menuImg2"),
                        rs.getString("name"),
                        rs.getString("isNew"),
                        rs.getString("isFast"),
                        rs.getInt("deliveryMinTime"),
                        rs.getInt("deliveryMaxTime"),
                        rs.getString("isEatsOriginal"),

                        rs.getString("star"),
                        rs.getInt("reviewNumber"),
                        rs.getString("distance"),
                        rs.getInt("deliveryMinCost"),
                        rs.getInt("couponIdx"),
                        rs.getString("couponName"),
                        rs.getString("isOpened"),
                        rs.getString("closeMent")

                ),
                getStoresParams);
    }
}



