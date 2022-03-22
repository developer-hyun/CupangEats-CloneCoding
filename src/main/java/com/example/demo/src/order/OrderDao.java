package com.example.demo.src.order;



import com.example.demo.src.order.model.*;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class OrderDao {

    private JdbcTemplate jdbcTemplate;
    private List<GetOrderSideMenuRes> getOrderSideMenuRes;
    private List<GetOrderMenuRes> getOrderMenuRes;
    private List<GetOrdersSideMenuRes> getOrdersSideMenuRes;
    private List<GetOrdersMenuRes> getOrdersMenuRes;
    private List<GetOrdersPreparingSideMenuRes> getOrdersPreparingSideMenuRes;
    private List<GetOrdersPreparingMenuRes> getOrdersPreparingMenuRes;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    public GetOrderRes getOrder(int userIdx,int orderIdx){

        String getOrderQuery = "select Store.name,left( O.createdAt,16) as createdAt,O.orderPrice,O.deliveryCost,C.dicountAmount as discount,O.orderPrice+O.deliveryCost-C.dicountAmount as totalPrice ,P.bank,concat('****',right(P.number,4)) as paymentNumber,case when O.payment=0 then '결제완료' when O.payment=1 then '환불요청' else '환불완료' end as payment ,O.orderIdx,O.status from Store\n" +
                "\n" +
                "  left join MenuCategory MC on Store.storeIdx = MC.storeIdx\n" +
                "  left join Menu M on MC.menuCategoryIdx = M.menuCategoryIdx\n" +
                "left join MenuSideCateg MSC on M.menuIdx = MSC.menuIdx\n" +
                "   left join SideCategory SC on MSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                "left join SubSideCategory SSC on SC.sideCategoryIdx = SSC.sideCategoryIdx\n" +
                "      left join OrderSM OS on SSC.subSideCategoryIdx = OS.subSideCategoryIdx\n" +
                "    left join OrderMenu OM on OS.orderMenuIdx = OM.orderMenuIdx\n" +
                " left join Orders O on OM.orderIdx = O.orderIdx\n" +
                "     left join Payment P on O.paymentIdx = P.paymentIdx\n" +
                "left join Coupon C on O.couponIdx = C.couponIdx\n" +
                "   where O.orderIdx=?\n" +
                "    group by O.orderIdx";
                Object[] getOrderParams = new Object[]{orderIdx};

        return this.jdbcTemplate.queryForObject(getOrderQuery,
                (rs, rowNum) -> new GetOrderRes(

                        rs.getString("name"),
                        rs.getString("createdAt"),


                        getOrderMenuRes=this.jdbcTemplate.query("select M.name, M.price ,OrderMenu.count,OrderMenu.orderMenuIdx from OrderMenu\n" +
                                        "left join Menu M on OrderMenu.menuIdx = M.menuIdx\n" +
                                        "where OrderMenu.orderIdx=?\n" +
                                        "\n" +
                                        "group by M.menuIdx",
                                (rk,rownum) -> new GetOrderMenuRes(
                                        rk.getString("name"),
                                        rk.getInt("price"),
                                        rk.getInt("count"),
                                        getOrderSideMenuRes=this.jdbcTemplate.query("select SSC.name, SSC.price from OrderSM\n" +
                                                "left join OrderMenu on OrderSM.orderMenuIdx = OrderMenu.orderMenuIdx\n" +
                                                "left join SubSideCategory SSC on OrderSM.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                                                "left join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                                                "left join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                                                "left join Menu M on MSC.menuIdx = M.menuIdx\n" +
                                                "where OrderMenu.orderMenuIdx=?\n" +
                                                "group by SSC.subSideCategoryIdx",
                                                (rp,rownum2) -> new GetOrderSideMenuRes(
                                                        rp.getString("name"),
                                                        rp.getInt("price")

                                                ),rk.getInt("orderMenuIdx"))

                                ),orderIdx),
                        rs.getInt("orderPrice"),
                        rs.getInt("deliveryCost"),
                        rs.getInt("discount"),
                        rs.getInt("totalPrice"),
                        rs.getString("bank"),
                        rs.getString("paymentNumber"),
                        rs.getString("payment"),
                        rs.getString("status")



                ),getOrderParams
        );

    }

    public int checkOrderIdx(int  orderIdx){
        String checkOrderQuery = "select exists(select orderIdx from Orders where orderIdx = ?)";
        int checkOrderParams = orderIdx;
        return this.jdbcTemplate.queryForObject(checkOrderQuery,
                int.class,
                checkOrderParams);
    }
    public int checkOrderStatus(int  orderIdx){
        String checkOrderQuery = "select case when status='ACTIVE' then 1 else 0 end from Orders where orderIdx=?";
        int checkOrderParams = orderIdx;
        return this.jdbcTemplate.queryForObject(checkOrderQuery,
                int.class,
                checkOrderParams);
    }

    public int createOrder(int  userIdx,PostOrderReq postOrderReq){


        String createOrderQuery = "insert into Orders  ( userIdx,addressIdx,orderPrice ,deliveryCost,couponIdx,paymentIdx,isDisposable,  toStore,toDeliveryRider) VALUES (?,?,?,?,?,?,?,?,?)";
        Object[] createOrderParams = new Object[]{userIdx, postOrderReq.getAddressIdx(),  postOrderReq.getOrderPrice(),postOrderReq.getDeliveryCost(),postOrderReq.getCouponIdx(),postOrderReq.getPaymentIdx(),postOrderReq.getIsDisposable(),postOrderReq.getToStore(),postOrderReq.getToDeliveryRider()};
        this.jdbcTemplate.update(createOrderQuery, createOrderParams);

        String lastInsertOrderIdxQuery = "select last_insert_id()";
        int orderIdx= this.jdbcTemplate.queryForObject(lastInsertOrderIdxQuery,int.class);


        int k= this.jdbcTemplate.queryForObject(lastInsertOrderIdxQuery,int.class);


        for(int i=0;i<postOrderReq.getPostOrderMenuReq().size();i++) {

            String createOrderMenuQuery = "insert into OrderMenu(menuIdx,count ,orderIdx) VALUES (?,?,?)";


            Object[] createOrderMenuParams = new Object[]{postOrderReq.getPostOrderMenuReq().get(i).getMenuIdx(),postOrderReq.getPostOrderMenuReq().get(i).getCount(),k};

            this.jdbcTemplate.update(createOrderMenuQuery, createOrderMenuParams);
            String lastInsertOrderMenuIdxQuery = "select last_insert_id()";
            int kk= this.jdbcTemplate.queryForObject(lastInsertOrderMenuIdxQuery,int.class);
            for(int j=0;j<postOrderReq.getPostOrderMenuReq().get(i).getPostOrderSideMenuReq().size();j++){
                String createOrderSideMenuQuery = "insert into OrderSM(subSideCategoryIdx ,orderMenuIdx) VALUES (?,?)";
                Object[] createOrderSideMenuParams = new Object[]{postOrderReq.getPostOrderMenuReq().get(i).getPostOrderSideMenuReq().get(j).getSubSideCategoryIdx(),kk};
                this.jdbcTemplate.update(createOrderSideMenuQuery, createOrderSideMenuParams);
            }
        }


        return orderIdx;
    }

    public int patchOrder(PatchOrderReq patchOrderReq){
        String patchOrderQuery = " update Orders set status = 'INACTIVE' ,  cancelReason ='U' where orderIdx=?";
        Object[] patchOrderParam = new Object[]{patchOrderReq.getOrderIdx()};

        return this.jdbcTemplate.update(patchOrderQuery,patchOrderParam);


    }
    public int checkAddressStatus(int  addressIdx){
        String checkAddressQuery = "select case when (status='ACTIVE')   then 1 else 0 end from Address where addressIdx=?";
        int checkAddressParams = addressIdx;
        return this.jdbcTemplate.queryForObject(checkAddressQuery,
                int.class,
                checkAddressParams);
    }
    public int checkSubSideCategoryStatus(int  subCategoryIdx){
        String checkSubCategoryQuery = "select case when (status='ACTIVE')   then 1 else 0 end from SubSideCategory where subSideCategoryIdx=?";
        int checkSubCategoryParams = subCategoryIdx;
        return this.jdbcTemplate.queryForObject(checkSubCategoryQuery,
                int.class,
                checkSubCategoryParams);
    }
    public int checkPaymentStatus(int  paymentIdx){
        String checkPaymentQuery = "select case when (status='ACTIVE')   then 1 else 0 end from Payment where paymentIdx=?";
        int checkPaymentParams = paymentIdx;
        return this.jdbcTemplate.queryForObject(checkPaymentQuery,
                int.class,
                checkPaymentParams);
    }
    public int checkMenuStatus(int  menuIdx){
        String checkMenuQuery = "select case when (status='ACTIVE')   then 1 else 0 end from Menu where menuIdx=?";
        int checkMenuParams = menuIdx;
        return this.jdbcTemplate.queryForObject(checkMenuQuery,
                int.class,
                checkMenuParams);
    }
    public int checkMenuIdx(int  menuIdx){
        String checkMenuQuery = "select exists(select menuIdx from Menu where menuIdx = ?)";
        int checkMenuParams = menuIdx;
        return this.jdbcTemplate.queryForObject(checkMenuQuery,
                int.class,
                checkMenuParams);
    }
    public int checkAddressIdx(int  addressIdx){
        String checkAddressQuery = "select exists(select addressIdx from Address where addressIdx = ?)";
        int checkAddressParams = addressIdx;
        return this.jdbcTemplate.queryForObject(checkAddressQuery,
                int.class,
                checkAddressParams);
    }
    public int checkPaymentIdx(int  paymentIdx){
        String checkPaymentIdxQuery = "select exists(select paymentIdx from Payment where paymentIdx = ?)";
        int checkPaymenyIdxParams = paymentIdx;
        return this.jdbcTemplate.queryForObject(checkPaymentIdxQuery,
                int.class,
                checkPaymenyIdxParams);
    }
    public int checkSubSideCategoryIdx(int  subSideCategoryIdx){
        String checkSubSideCategoryQuery = "select exists(select subSideCategoryIdx from SubSideCategory where subSideCategoryIdx = ?)";
        int checkSubSideCategoryParams = subSideCategoryIdx;
        return this.jdbcTemplate.queryForObject(checkSubSideCategoryQuery,
                int.class,
                checkSubSideCategoryParams);
    }
    public int checkCouponAvailable(int  couponIdx,int userIdx, int menuIdx){
        String checkCouponQuery = "select distinct case when (case when (select couponIdx from Store\n" +
                "    left join MenuCategory MC on Store.storeIdx = MC.storeIdx\n" +
                "    left join Menu M on MC.menuCategoryIdx = M.menuCategoryIdx\n" +
                "    where M.menuIdx=?) = ? then 1 else 0 end)=1 and exists((select couponIdx from Store\n" +
                "                                                                                                               left join MenuCategory MC on Store.storeIdx = MC.storeIdx\n" +
                "                                                                                                               left join Menu M on MC.menuCategoryIdx = M.menuCategoryIdx\n" +
                "                                                           where M.menuIdx=?)) then 1 else 0 end from UserCoupon\n" +

                "where UserCoupon.userIdx=? and UserCoupon.couponIdx=?";
        Object[] checkCouponParams = new Object[]{menuIdx,couponIdx,menuIdx,userIdx,couponIdx};
        return this.jdbcTemplate.queryForObject(checkCouponQuery,
                int.class,
                checkCouponParams);
    }

    public int checkDeliveryStatus(int  orderIdx){
        String checkDeliveryQuery = "select case when (deliveryStatus=0 or deliveryStatus=1 or deliveryStatus=3)   then 1 else 0 end from Orders where orderIdx=?";
        int checkDeliveryParams = orderIdx;
        return this.jdbcTemplate.queryForObject(checkDeliveryQuery,
                int.class,
                checkDeliveryParams);
    }

    public List< GetOrdersRes> getOrders(int userIdx){

        String getOrderQuery = "select distinct  O.orderIdx,Store.name,Store.imgUrl1, left( O.createdAt,16) as createdAt,case when O.status ='INACTIVE' then '주문 취소됨' else  '배달 완료' end as deliveryStatus,case when O.status='INACTIVE' and cancelReason='U' then '고객 취소' when O.status='INACTIVE' and cancelReason='S' then '주문 불가' else '-' end as cancelReason\n" +
                "       , O.isReviewed,(select   case when sum(Review.star) =0 then '-' else round(sum( Review.star)/count( *),1  ) end  from Review\n" +
                "      where Review.orderIdx=O.orderIdx) as star\n" +
                "      , case when O.status='ACTIVE' then O.orderPrice+O.deliveryCost-C.dicountAmount else 0 end as totalPrice ,case when exists(select   Review.reviewIdx  from Review\n" +
                "     where Review.orderIdx=O.orderIdx) then (select   Review.reviewIdx  from Review\n" +
                "     where Review.orderIdx=O.orderIdx) else 0 end as reviewIdx ,  case when exists(select   Review.reviewIdx  from Review\n" +
                "                   where Review.orderIdx=O.orderIdx)=0 and( O.status='ACTIVE') and (select TRUNCATE((O.createdAt+51840000*2-current_timestamp)/5184000,0)\n" +
                ") >0 then concat('리뷰 작성 기간이 ',(select TRUNCATE((O.createdAt+51840000*1.5-current_timestamp)/5184000,0)\n" +
                "                                                          ),'일 남았습니다.')  else '-' end as postReviewDate ,Store.isOpened as storeStatus, Store.closeMent" +
                "               from Store\n" +
                "                 left join MenuCategory MC on Store.storeIdx = MC.storeIdx\n" +
                "                 left join Menu M on MC.menuCategoryIdx = M.menuCategoryIdx\n" +
                "            left join MenuSideCateg MSC on M.menuIdx = MSC.menuIdx\n" +
                "                  left join SideCategory SC on MSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                "            left join SubSideCategory SSC on SC.sideCategoryIdx = SSC.sideCategoryIdx\n" +
                "             left join OrderSM OS on SSC.subSideCategoryIdx = OS.subSideCategoryIdx\n" +
                "             left join OrderMenu OM on OS.orderMenuIdx = OM.orderMenuIdx\n" +
                "             left join Orders O on OM.orderIdx = O.orderIdx\n" +
                "             left join Payment P on O.paymentIdx = P.paymentIdx\n" +
                "            left join Coupon C on O.couponIdx = C.couponIdx\n" +
                "              where O.userIdx=? and (O.deliveryStatus=2 or O.status='INACTIVE')" +
                "             order by O.createdAt desc";

        Object[] getOrderParams = new Object[]{userIdx};

        return this.jdbcTemplate.query(getOrderQuery,
                (rs, rowNum) -> new GetOrdersRes(
                        rs.getInt("orderIdx"),

                        rs.getString("name"),
                        rs.getString("imgUrl1"),
                        rs.getString("createdAt"),

                        rs.getString("deliveryStatus"),
                        rs.getString("cancelReason"),
                        rs.getString("isReviewed"),
                        rs.getString("star"),
                        getOrdersMenuRes=this.jdbcTemplate.query("select M.menuIdx,OrderMenu.orderMenuIdx,M.name ,OrderMenu.count ,case  when OrderMenu.liked=6 then '-' when OrderMenu.liked=0 then 'like' else 'hate'end  as liked  from OrderMenu\n" +
                                        "left join Menu M on OrderMenu.menuIdx = M.menuIdx\n" +
                                        "where OrderMenu.orderIdx=?\n" +

                                        "group by M.menuIdx",
                                (rk,rownum) -> new GetOrdersMenuRes(
                                        rk.getInt("menuIdx"),

                                        rk.getString("name"),

                                        rk.getInt("count"),
                                        rk.getString("liked"),
                                        getOrdersSideMenuRes=this.jdbcTemplate.query("select  SSC.subSideCategoryIdx, SSC.name from OrderSM\n" +
                                                        "left join OrderMenu on OrderSM.orderMenuIdx = OrderMenu.orderMenuIdx\n" +
                                                        "left join SubSideCategory SSC on OrderSM.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                                                        "left join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                                                        "left join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                                                        "left join Menu M on MSC.menuIdx = M.menuIdx\n" +
                                                        "where OrderMenu.orderMenuIdx=?\n" +
                                                        "group by SSC.subSideCategoryIdx",
                                                (rp,rownum2) -> new GetOrdersSideMenuRes(
                                                        rp.getInt("subSideCategoryIdx"),
                                                        rp.getString("name")


                                                ),rk.getInt("orderMenuIdx"))

                                ),rs.getInt("orderIdx")),

                        rs.getInt("totalPrice"),
                        rs.getInt("reviewIdx"),
                        rs.getString("postReviewDate"),
                        rs.getString("storeStatus"),
                        rs.getString("closeMent")




                ),getOrderParams
        );

    }


    //
    public GetReviewCountRes getReviewCount(int userIdx){

        String getCountQuery = "select  concat(count(*),'건') as count from Orders\n" +
                "where exists(select   Review.reviewIdx  from Review\n" +
                "             where Review.orderIdx=Orders.orderIdx)=0 and( Orders.status='ACTIVE') and (select TRUNCATE((Orders.createdAt+51840000*2-current_timestamp)/5184000,0)\n" +
                "                                                                             ) >0\n" +
                "and Orders.userIdx=? and (Orders.deliveryStatus=2 or Orders.status='INACTIVE')";
        Object[] getCountParams = new Object[]{userIdx};

        return this.jdbcTemplate.queryForObject(getCountQuery,
                (rs, rowNum) -> new GetReviewCountRes(
                        rs.getString("count")


                ),getCountParams
        );

    }

    public List< GetOrdersPreparingRes> getOrdersPreparing(int userIdx){

        String getOrderQuery = "select distinct  O.orderIdx,Store.name,Store.imgUrl1, left( O.createdAt,16) as createdAt,case when O.deliveryStatus =0 then '메뉴 준비중' else  '배달중' end as deliveryStatus," +

                "      O.orderPrice+O.deliveryCost-C.dicountAmount as totalPrice  from Store\n" +
                "                 left join MenuCategory MC on Store.storeIdx = MC.storeIdx\n" +
                "                 left join Menu M on MC.menuCategoryIdx = M.menuCategoryIdx\n" +
                "            left join MenuSideCateg MSC on M.menuIdx = MSC.menuIdx\n" +
                "                  left join SideCategory SC on MSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                "            left join SubSideCategory SSC on SC.sideCategoryIdx = SSC.sideCategoryIdx\n" +
                "             left join OrderSM OS on SSC.subSideCategoryIdx = OS.subSideCategoryIdx\n" +
                "             left join OrderMenu OM on OS.orderMenuIdx = OM.orderMenuIdx\n" +
                "             left join Orders O on OM.orderIdx = O.orderIdx\n" +
                "             left join Payment P on O.paymentIdx = P.paymentIdx\n" +
                "            left join Coupon C on O.couponIdx = C.couponIdx\n" +
                "              where O.userIdx=? and (O.deliveryStatus<2 and O.status='ACTIVE')" +
                "             order by O.createdAt desc";
        Object[] getOrderParams = new Object[]{userIdx};

        return this.jdbcTemplate.query(getOrderQuery,
                (rs, rowNum) -> new GetOrdersPreparingRes(
                        rs.getInt("orderIdx"),

                        rs.getString("name"),
                        rs.getString("imgUrl1"),
                        rs.getString("createdAt"),

                        rs.getString("deliveryStatus"),

                        getOrdersPreparingMenuRes=this.jdbcTemplate.query("select OrderMenu.orderMenuIdx,M.name ,OrderMenu.count  from OrderMenu\n" +
                                        "left join Menu M on OrderMenu.menuIdx = M.menuIdx\n" +
                                        "where OrderMenu.orderIdx=?\n" +

                                        "group by M.menuIdx",
                                (rk,rownum) -> new GetOrdersPreparingMenuRes(


                                        rk.getString("name"),

                                        rk.getInt("count"),

                                        getOrdersPreparingSideMenuRes=this.jdbcTemplate.query("select  SSC.name from OrderSM\n" +
                                                        "left join OrderMenu on OrderSM.orderMenuIdx = OrderMenu.orderMenuIdx\n" +
                                                        "left join SubSideCategory SSC on OrderSM.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                                                        "left join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                                                        "left join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                                                        "left join Menu M on MSC.menuIdx = M.menuIdx\n" +
                                                        "where OrderMenu.orderMenuIdx=?\n" +
                                                        "group by SSC.subSideCategoryIdx",
                                                (rp,rownum2) -> new GetOrdersPreparingSideMenuRes(

                                                        rp.getString("name")


                                                ),rk.getInt("orderMenuIdx"))

                                ),rs.getInt("orderIdx")),

                        rs.getInt("totalPrice")





                ),getOrderParams
        );

    }

    public GetOrderDeliveryRes getOrderDelivery(int orderIdx){

        String getOrderQuery ="select  TIMESTAMPDIFF(MINUTE,CURRENT_TIMESTAMP,DATE_ADD(O.orderAccept, INTERVAL Store.deliveryMaxTime MINUTE)) as time,concat(substring(DATE_FORMAT(DATE_ADD(current_timestamp, INTERVAL Store.deliveryMaxTime MINUTE), '%k:%m %T'),6,6),' PM 도착예정' ) as endTime,case when O.deliveryStatus=0 then '메뉴 준비중' when O.deliveryStatus=1 then '배달중' when O.deliveryStatus=3 then '매장에서 주문을 확인하고 있습니다' else '배달완료' end as deliveryStatus,\n" +
                "        concat(substring(DATE_FORMAT(O.orderAccept, '%H:%m %T'),6,6),' PM ') as orderAccept,concat(substring(DATE_FORMAT(O.orderDelivery, '%H:%m %r'),6,6),' PM ') as orderDelivery,concat(substring(DATE_FORMAT(O.orderEnd, '%H:%m %T'),6,6),' PM ') as orderEnd,concat(A.mainAddress,A.subAddress,A.guidAddress) as address,O.orderIdx,Store.name as storeName,O.orderPrice+O.deliveryCost-C.dicountAmount as totalPrice ,P.bank,concat('****',right(P.number,4)) as paymentNumber,case when O.payment=0 then '결제완료' when O.payment=1 then '환불요청' else '환불완료' end as payment from Store\n" +

                " left join MenuCategory MC on Store.storeIdx = MC.storeIdx\n" +
                "left join Menu M on MC.menuCategoryIdx = M.menuCategoryIdx\n" +
                "left join MenuSideCateg MSC on M.menuIdx = MSC.menuIdx\n" +
                "left join SideCategory SC on MSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                "left join SubSideCategory SSC on SC.sideCategoryIdx = SSC.sideCategoryIdx\n" +
                "left join OrderSM OS on SSC.subSideCategoryIdx = OS.subSideCategoryIdx\n" +
                "left join OrderMenu OM on OS.orderMenuIdx = OM.orderMenuIdx\n" +
                "left join Orders O on OM.orderIdx = O.orderIdx\n" +
                "left join Payment P on O.paymentIdx = P.paymentIdx\n" +
                "left join Coupon C on O.couponIdx = C.couponIdx\n" +
                "left join Address A on O.addressIdx = A.addressIdx\n" +
                " where O.orderIdx=?\n" +
                " group by O.orderIdx";
        Object[] getOrderParams = new Object[]{orderIdx};

        return this.jdbcTemplate.queryForObject(getOrderQuery,
                (rs, rowNum) -> new GetOrderDeliveryRes(

                        rs.getInt("time"),
                        rs.getString("endTime"),
                        rs.getString("deliveryStatus"),
                        rs.getString("orderAccept"),
                        rs.getString("orderDelivery"),
                        rs.getString("orderEnd"),
                        rs.getString("address"),
                    orderIdx,
                        rs.getString("storeName"),


                        getOrdersPreparingMenuRes=this.jdbcTemplate.query("select M.name,OrderMenu.count,OrderMenu.orderMenuIdx from OrderMenu\n" +
                                        "  left join Menu M on OrderMenu.menuIdx = M.menuIdx\n" +
                                        "   where OrderMenu.orderIdx=?\n" +
                                        "\n" +
                                        "                          group by M.menuIdx;\n",
                                (rk,rownum) -> new GetOrdersPreparingMenuRes(
                                        rk.getString("name"),

                                        rk.getInt("count"),
                                        getOrdersPreparingSideMenuRes=this.jdbcTemplate.query("select SSC.name from OrderSM\n" +
                                                        "left join OrderMenu on OrderSM.orderMenuIdx = OrderMenu.orderMenuIdx\n" +
                                                        "left join SubSideCategory SSC on OrderSM.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                                                        "left join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                                                        "left join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                                                        "left join Menu M on MSC.menuIdx = M.menuIdx\n" +
                                                        "where OrderMenu.orderMenuIdx=?\n" +
                                                        "group by SSC.subSideCategoryIdx;",
                                                (rp,rownum2) -> new GetOrdersPreparingSideMenuRes(
                                                        rp.getString("name")


                                                ),rk.getInt("orderMenuIdx"))

                                ),rs.getInt("orderIdx")),

                        rs.getInt("totalPrice"),
                        rs.getString("bank"),
                        rs.getString("paymentNumber"),
                        rs.getString("payment")




                ),getOrderParams
        );

    }
}