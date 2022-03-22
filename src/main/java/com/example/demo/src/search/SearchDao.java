package com.example.demo.src.search;


import com.example.demo.src.home.model.GetHotFranchisesRes;

import com.example.demo.src.search.model.GetStoresRes;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class SearchDao {

    private JdbcTemplate jdbcTemplate;


    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



//인기 프랜차이즈

    public int checkKeyword(String  keyword){
        String checkKeywordQuery =
                "select exists(select 1 from SubCategory\n" +
                "left join Category on SubCategory.categoryIdx = Category.categoryIdx\n" +
                "left join StoreCategory SC on SubCategory.subCategoryIdx = SC.subCategoryIdx\n" +
                "left join Store on SC.storeIdx = Store.storeIdx\n" +
                "where Store.name = ? or SubCategory.name=?)";
        Object[] checkKeywordParams = new Object[]{keyword,keyword};
        return this.jdbcTemplate.queryForObject(checkKeywordQuery,
                int.class,
                checkKeywordParams);
    }
    public int checkAddressIdx(int  addressIdx){
        String checkAddressQuery = "select exists(select addressIdx from Address where addressIdx = ?)";
        int checkAddressParams = addressIdx;
        return this.jdbcTemplate.queryForObject(checkAddressQuery,
                int.class,
                checkAddressParams);
    }

    public List<GetStoresRes> getStores(int order,String isFast,int deliveryCost,int minimumAmount,int coupon,int addressIdx,String keyword){
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
                    "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName" +
                    ",Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +
            "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where S.name=? or Store.name=? and " +
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
                    "                    end ";}
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
                    ",Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +

                    "          left join Coupon C on Store.couponIdx = C.couponIdx\n" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where S.name=? or Store.name=? and" +

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
                    "                 Store.name, (select concat(round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "                -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) ,'km')from Address where Address.addressIdx=? ) as distance, (select  case when sum(Review.star) =0 then '0' else round(sum( Review.star)/count( *),1  ) end  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                 left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                 left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as star\n" +
                    "                  , (select count(distinct  reviewIdx)from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                  left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                  left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)as reviewNumber,deliveryMinCost ,C.couponIdx,C.name as CouponName " +
                    ",Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +
                    "                  left join Coupon C on Store.couponIdx = C.couponIdx\n" +
                    "                  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "                  left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "                 left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "                  where S.name=? or Store.name=? and\n" +
                    "          case ? when 'Y' then  Store.isFast='Y' else  Store.isFast='Y' or Store.isFast='N'  end  and Store.deliveryMinCost<=? and Store.minimumAmount<=? and\n" +
                    "                     case ? when  1 then Store.couponIdx !=0 else Store.couponIdx !=0 or Store.couponIdx=0  end and Store.Status='ACTIVE' and\n" +
                    "              (select round((6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "                     -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))),1) from Address where Address.addressIdx=? ) <5\n" +
                    "                   #   group by(select count(distinct O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "                #                           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "                        #                     left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "               order by case ? when 0 then  (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "           left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "               left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "             when 1 then (select count(distinct  O2.orderIdx)from Orders O2 left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "          left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "           left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx )\n" +
                    "           when 2 then (select (6371*acos(cos(radians(Store.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude)\n" +
                    "     -radians(Store.longitude))+sin(radians(Store.latitude))*sin(radians(Address.latitude)))) from Address where Address.addressIdx=?)\n" +
                    "             when 3 then   (select  sum( Review.star)/count( *)  from Review left join Orders O2 on Review.orderIdx = O2.orderIdx left join OrderMenu OM2 on O2.orderIdx = OM2.orderIdx left join OrderSM OS2 on OM2.orderMenuIdx = OS2.orderMenuIdx\n" +
                    "       left join Menu M2 on OM2.menuIdx = M2.menuIdx left join MenuSideCateg C on M2.menuIdx = C.menuIdx left join MenuCategory MC2 on M2.menuCategoryIdx = MC2.menuCategoryIdx left join SideCategory SC2 on C.sideCategoryIdx = SC2.sideCategoryIdx left join SubSideCategory SSC2 on SC2.sideCategoryIdx = SSC2.sideCategoryIdx\n" +
                    "       left join Store S2 on MC2.storeIdx = S2.storeIdx  where S2.storeIdx=Store.storeIdx)\n" +
                    "                       end desc";}
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
                    ",Store.isOpened, case when isOpened='N' then Store.closeMent else '-' end as closeMent from Store\n" +

                    "                           left join Coupon C on Store.couponIdx = C.couponIdx" +
                    "  left join StoreCategory SC on Store.storeIdx = SC.storeIdx\n" +
                    "     left join SubCategory S on SC.subCategoryIdx = S.subCategoryIdx\n" +
                    "left join Category C2 on S.categoryIdx = C2.categoryIdx\n" +
                    "where S.name=? or Store.name=? and\n" +

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
        Object[] getStoresParams = new Object[]{addressIdx,keyword,keyword,isFast,deliveryCost,minimumAmount,coupon,addressIdx,order,addressIdx};
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



