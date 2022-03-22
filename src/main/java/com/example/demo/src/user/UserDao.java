package com.example.demo.src.user;


import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class UserDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    /**
     * 회원가입 API
     *
     * @param postUserReq
     * @return
     */
    public int createUser(PostUserReq postUserReq) {
        this.jdbcTemplate.update("insert into User (id,password,name,phoneNumber,signUpAgreeCheckBox,socialLogIn) VALUES (?,?,?,?,?,?)",
                new Object[]{postUserReq.getId(), postUserReq.getPassword(), postUserReq.getName(), postUserReq.getPhoneNumber(), postUserReq.getSignUpAgreeCheckBox(), postUserReq.getSocialLogIn()}
        );
        return this.jdbcTemplate.queryForObject("select last_insert_id()", int.class);
    }

    /**
     * 중복 id체크
     *
     * @param id
     * @return
     */
    public int checkId(String id) {
        return this.jdbcTemplate.queryForObject("select exists(select id from User where id = ?)",
                int.class,
                id);
    }

    /**
     * 중복 폰번호 체크
     */
    public int checkPhoneNumber(String phoneNumber) {
        return this.jdbcTemplate.queryForObject("select exists(select phoneNumber from User where phoneNumber = ?)",
                int.class,
                phoneNumber);
    }

    /**
     * 로그인 API
     * //     *
     * //
     */
    public GetLogInInfo logIn(PostLogInReq postLogInReq) {
        String query = "select userIdx,id,password from User where id=?";
        String param = postLogInReq.getId();
        System.out.println(postLogInReq.getPassword());
        System.out.println(postLogInReq.getId());
        return this.jdbcTemplate.queryForObject(query,
                (rs, rownum) -> new GetLogInInfo(
                        rs.getInt("userIdx"),
                        rs.getString("id"),
                        rs.getString("password")),
                param
        );


    }

    /**
     * autologIn - myeats API
     */
    public GetMyEatsRes myeats(int userIdx) {
//        return this.jdbcTemplate.queryForObject("select name,phoneNumber from User where = userIdx=?",
//                (rs,rownum) -> new GetMyEatsRes(
//                        rs.getString("name"),
//                        rs.getString("phonewNumber")),
//                                userIdx);
        String name = this.jdbcTemplate.queryForObject("select name from User where userIdx=?",
                String.class, userIdx);
        String phoneNumber = this.jdbcTemplate.queryForObject("select phoneNumber from User where userIdx=?",
                String.class, userIdx);
        //  System.out.println(name);
        //  System.out.println(phoneNumber);
        String front = phoneNumber.substring(0, 3);
        String end = phoneNumber.substring(7);
        String mid = "****";
        String cep = "-";
        //  System.out.println(front);
        //  System.out.println(end);
        String number = front + cep + mid + cep + end;
        //ystem.out.println(number);
        GetMyEatsRes getMyEatsRes = new GetMyEatsRes(name, number);
        return getMyEatsRes;
    }

    /**
     * 즐겨찾기 API
     */  //최근 주문순 정렬 부분
    public GetLikeRes likeReslatelyOrder(int userIdx) {
        int totalCount = this.jdbcTemplate.queryForObject("select count(likedIdx)  from Liked where userIdx=? AND status='ACTIVE'",
                int.class, userIdx);

        List<GetLikesRes> getLikesRes = this.jdbcTemplate.query("select S.storeIdx,S.imgUrl1 as imageUrl,S.name as name ,S.isFast as isFast,TRUNCATE(avg(R.star),2) as avgStar, count(distinct R.reviewIdx) as countReview, CONCAT((select round((6371*acos(cos(radians(S.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude) -radians(S.longitude))+sin(radians(S.latitude))*sin(radians(Address.latitude)))),1) from Address inner join User U on Address.userIdx = U.userIdx where Address.userIdx=? AND Address.isMain='Y' ),'km') as deliveryDistance, CONCAT(S.deliveryMinTime ,'-',S.deliveryMaxTime,'분') as deliveryTime, CONCAT(FORMAT(MIN(DC.deliveryCost),0),'원') as deliveryCost from Liked as L inner join Store S on L.storeIdx = S.storeIdx inner join MenuCategory MC on S.storeIdx = MC.storeIdx inner join Menu M on MC.menuCategoryIdx = M.menuCategoryIdx inner join MenuSideCateg MSC on M.menuIdx = MSC.menuIdx inner join SideCategory SC on MSC.sideCategoryIdx = SC.sideCategoryIdx inner join SubSideCategory SSC on SC.sideCategoryIdx = SSC.sideCategoryIdx inner join OrderSM OS on SSC.subSideCategoryIdx = OS.subSideCategoryIdx inner join OrderMenu OM on OS.orderMenuIdx = OM.orderMenuIdx inner join Orders O on OM.orderIdx = O.orderIdx inner join Review R on O.orderIdx = R.orderIdx inner join DeliveryCost DC on L.storeIdx = DC.storeIdx where L.userIdx=? and L.status = 'ACTIVE' group by L.storeIdx order by O.updatedAt DESC",
                (rs, rownum) -> new GetLikesRes(
                        rs.getString("imageUrl"),
                        rs.getString("name"),
                        rs.getString("isFast"),
                        rs.getFloat("avgStar"),
                        rs.getInt("countReview"),
                        rs.getString("deliveryDistance"),
                        rs.getString("deliveryTime"),
                        rs.getString("deliveryCost")
                ), userIdx, userIdx
        );
        GetLikeRes getLikeRes = new GetLikeRes(totalCount, getLikesRes);
        return getLikeRes;
    }

    /**
     * // 최근 추가 순 정렬 부분
     */
    public GetLikeRes likeReslatelyPlus(int userIdx) {
        int totalCount = this.jdbcTemplate.queryForObject("select count(likedIdx)  from Liked where userIdx=? AND status='ACTIVE'",
                int.class, userIdx);

        List<GetLikesRes> getLikesRes = this.jdbcTemplate.query("select S.storeIdx,S.imgUrl1 as imageUrl,S.name as name ,S.isFast as isFast,TRUNCATE(avg(R.star),2) as avgStar, count(distinct R.reviewIdx) as countReview, CONCAT((select round((6371*acos(cos(radians(S.latitude))*cos(radians(Address.latitude))*cos(radians(Address.longitude) -radians(S.longitude))+sin(radians(S.latitude))*sin(radians(Address.latitude)))),1) from Address inner join User U on Address.userIdx = U.userIdx where Address.userIdx=? AND Address.isMain='Y' ),'km') as deliveryDistance, CONCAT(S.deliveryMinTime ,'-',S.deliveryMaxTime,'분') as deliveryTime, CONCAT(FORMAT(MIN(DC.deliveryCost),0),'원') as deliveryCost from Liked as L inner join Store S on L.storeIdx = S.storeIdx inner join MenuCategory MC on S.storeIdx = MC.storeIdx inner join Menu M on MC.menuCategoryIdx = M.menuCategoryIdx inner join MenuSideCateg MSC on M.menuIdx = MSC.menuIdx inner join SideCategory SC on MSC.sideCategoryIdx = SC.sideCategoryIdx inner join SubSideCategory SSC on SC.sideCategoryIdx = SSC.sideCategoryIdx inner join OrderSM OS on SSC.subSideCategoryIdx = OS.subSideCategoryIdx inner join OrderMenu OM on OS.orderMenuIdx = OM.orderMenuIdx inner join Orders O on OM.orderIdx = O.orderIdx inner join Review R on O.orderIdx = R.orderIdx inner join DeliveryCost DC on L.storeIdx = DC.storeIdx where L.userIdx=? and L.status = 'ACTIVE' group by L.storeIdx order by L.updatedAt DESC",
                (rs, rownum) -> new GetLikesRes(
                        rs.getString("imageUrl"),
                        rs.getString("name"),
                        rs.getString("isFast"),
                        rs.getFloat("avgStar"),
                        rs.getInt("countReview"),
                        rs.getString("deliveryDistance"),
                        rs.getString("deliveryTime"),
                        rs.getString("deliveryCost")
                ), userIdx, userIdx
        );
        GetLikeRes getLikeRes = new GetLikeRes(totalCount, getLikesRes);
        return getLikeRes;
    }

    /**
     * 유제 삭제 API
     */
    public void deleteUser(int userIdx) {
        this.jdbcTemplate.update("update User SET status = 'INACTIVE' where userIdx=?",
                userIdx);
    }

    public int checkuserIdx(int userIdx) {
        return this.jdbcTemplate.queryForObject("select exists(select userIdx from User where userIdx=?)",
                int.class,
                userIdx);
    }

    //    /**
//     * 즐겨찾기 생성
//     * @param userIdx
//     * @param storeIdx
//     * @return
//     */
//    public int likeAdd(int userIdx,int storeIdx) {
//        this.jdbcTemplate.update("INSERT INTO Liked (userIdx,storeIdx) select ?,? from dual where not exists(select userIdx,storeIdx from Liked where userIdx= ? and storeIdx = ?)",
//                userIdx,storeIdx,userIdx,storeIdx);
//        return this.jdbcTemplate.queryForObject("select last_insert_id()",int.class);
//    }
    //즐겨찾기 해당 컬럼이 있나 없나 확인
    public int likeExist(int userIdx, int storeIdx) {
        return this.jdbcTemplate.queryForObject("select exists(select likedIdx from Liked where userIdx=? and storeIdx=?)",
                int.class, userIdx, storeIdx);
    }  //1이 있는거 있는거

    //즐겨찾기 생성 부분
    public int likeAdd(int userIdx, int storeIdx) {
        this.jdbcTemplate.update("insert into Liked (useridx,storeIdx) VALUES (?,?)",
                userIdx, storeIdx);

        return this.jdbcTemplate.queryForObject("select last_insert_id()", int.class);
    }

    //즐겨찾기 현재상태확인
    public String likeCheckStatus(int userIdx, int storeIdx) {
        String status = this.jdbcTemplate.queryForObject("select status from Liked where userIdx= ? and storeIdx=?", String.class, userIdx, storeIdx);
        return status;
    }

    public int likeChangeStatusInactive(int userIdx, int storeIdx) {
        this.jdbcTemplate.update("update Liked SET status = 'INACTIVE' where userIdx=? and storeIdx=?",
                userIdx, storeIdx);
        return this.jdbcTemplate.queryForObject("select last_insert_id()", int.class);
    }

    public int likeChangeStatusActive(int userIdx, int storeIdx) {
        this.jdbcTemplate.update("update Liked SET status = 'ACTIVE' where userIdx=? and storeIdx=?",
                userIdx, storeIdx);
        return this.jdbcTemplate.queryForObject("select last_insert_id()", int.class);
    }

    // 스토어 존재 여부
    public int checkStoreIdx(int storeIdx) {
        String checkCategoryQuery = "select exists(select storeIdx from Store where storeIdx = ?)";
        int checkCategoryParams = storeIdx;
        return this.jdbcTemplate.queryForObject(checkCategoryQuery,
                int.class,
                checkCategoryParams);
    }

    //즐겨 찾기 수정
    public void patchLike(int userIdx, int storeIdx) {
        this.jdbcTemplate.update("update Liked SET status='INACTIVE' where userIdx=? and storeIdx=?",
                userIdx, storeIdx);
//        PatchLikeRes patchLikeRes = new PatchLikeRes(userIdx,storeIdx);
//        return patchLikeRes;
    }

    //배달지 주소 생성
//    public PostAdressRes postAdress(int userIdx,PostAdressReq postAdressReq){
//        this.jdbcTemplate.update("insert into Address (userIdx,latitude,longitude,mainAdress ,subAdress,guidAdress,isWhere) VALUES (?,?,?,?,?,?,?)",
//                userIdx,postAdressReq.getLatitude(),postAdressReq.getLongitude(),postAdressReq.getMainAdress(),postAdressReq.getSubAdress(),postAdressReq.getGuidAdress(),postAdressReq.getIsWhere());
//        Integer addressIdx = this.jdbcTemplate.queryForObject("select last_insert_id()",int.class);
//        String isMain = this.jdbcTemplate.queryForObject("select isMain from Address where addressIdx=?",
//                String.class,addressIdx);
//        PostAdressRes postAdressRes = new PostAdressRes(addressIdx,isMain);
//        return postAdressRes;
//    }

    //배달지 주소 내역 API
//    public GetAddressRes getAddress(int userIdx) {
//
//    }


    public int createKakaoUser(String email, String name) {

        String createUserQuery = "insert into User (socialLogin,id,name) VALUES (?,?,?)";

        Object[] createUserParams = new Object[]{1, email, name};

        this.jdbcTemplate.update(createUserQuery, createUserParams);

        String lastInsertIdQuery = "select last_insert_id()";

        return this.jdbcTemplate.queryForObject(lastInsertIdQuery, int.class);
    }


    public GetLogInInfo kakaologIn(String id) {
        String query = "select userIdx,id,password from User where id=?";

        return this.jdbcTemplate.queryForObject(query,
                (rs, rownum) -> new GetLogInInfo(
                        rs.getInt("userIdx"),
                        rs.getString("id"),
                        rs.getString("password")),
                id
        );


    }

    public void patchCheck(String phone) {
        String patchOrderQuery = " update Authentication set status = 'INACTIVE'  where phone=?";
        Object[] patchOrderParam = new Object[]{phone};
        this.jdbcTemplate.update(patchOrderQuery, patchOrderParam);


    }

    public void createPhone(String phone, String a) {

        this.jdbcTemplate.update("insert into Authentication (phone,number) VALUES (?,?)",
                new Object[]{phone, a}
        );

    }

    public int Check(String code, String phone) {

        String checkOrderQuery = "select case when  Authentication.number=? and phone=? and status='ACTIVE' then 1 else 0 end from Authentication where phone=? order by authenticationIdx desc limit 1";

        return this.jdbcTemplate.queryForObject(checkOrderQuery,
                int.class,
                new Object[]{code, phone, phone});

    }


    // iswhere 이 1일때 집이 존재하는가
    public int checkHouseExists(int userIdx) {
        return this.jdbcTemplate.queryForObject("select exists(select addressIdx from Address where userIdx=? AND status='ACTIVE' AND isWhere =1)",
                int.class, userIdx);
    }

    //iswhere 이 2일 때 회사가 존재하는가
    public int checkCompanyExists(int userIdx) {
        return this.jdbcTemplate.queryForObject("select exists(select addressIdx from Address where userIdx=? AND status='ACTIVE' AND isWhere =2)",
                int.class, userIdx);
    }

    //기존 주소 가 뭔지 addressIdx반환
    public int mainAddress(int userIdx) {
        return this.jdbcTemplate.queryForObject("select addressIdx from Address where userIdx=? AND status='ACTIVE' AND isMain='Y'",
                int.class, userIdx);
    }

    //수정 부분
    public PostAddressRes postAddress(int userIdx, PostAddressReq postAddressReq) {
        this.jdbcTemplate.update("insert into Address (userIdx,latitude,longitude,buildingAddress,mainAddress ,subAddress,guidAddress,isWhere) VALUES (?,?,?,?,?,?,?,?)",
                userIdx, postAddressReq.getLatitude(), postAddressReq.getLongitude(), postAddressReq.getBuildingAddress(), postAddressReq.getMainAddress(), postAddressReq.getSubAdress(), postAddressReq.getGuidAdress(), postAddressReq.getIsWhere());
        int addressIdx = this.jdbcTemplate.queryForObject("select last_insert_id()", int.class);
        String isMain = this.jdbcTemplate.queryForObject("select isMain from Address where status='ACTIVE' AND addressIdx=?",
                String.class, addressIdx);
        PostAddressRes postAddressRes = new PostAddressRes(addressIdx, isMain);
        return postAddressRes;
    }

    //기존 주소 N으로 바꾸기
    public void changeMainAddressN(int userIdx, int addressIdx) {
        this.jdbcTemplate.update("update Address SET isMAIN='N' where userIdx=? AND addressIdx=?",
                userIdx, addressIdx);
    }

    //이미 존재하는 집
    public void alreadlyexistHouseChangeInactive(int userIdx) {
        this.jdbcTemplate.update("update Address SET status='INACTIVE' where userIdx =? AND isWhere=1 AND status='ACTIVE'", userIdx);
    }

    //이미 존재하는 회사
    public void alreadlyexistCompanyChangeInactive(int userIdx) {
        this.jdbcTemplate.update("update Address SET status='INACTIVE' where userIdx =? AND isWhere=2 AND status='ACTIVE'", userIdx);

    }

    //배달 주소 내역 조회
    public List<GetAddressHistoryRes> getAddressHistory(int userIdx) {
        return this.jdbcTemplate.query("select addressIdx,isWhere,isMain, buildingAddress,mainAddress,subAddress\n" +
                        "      from Address where userIdx = ? AND status='ACTIVE'",
                (rs, rownum) -> new GetAddressHistoryRes(
                        rs.getInt("addressIdx"),
                        rs.getInt("isWhere"),
                        rs.getString("isMain"),
                        rs.getString("buildingAddress"),
                        rs.getString("mainAddress"),
                        rs.getString("subAddress"))
                , userIdx);
    }

    //배달 주소 설정
    public PatchAddressRes patchAddress(int userIdx, PatchAddressReq patchAddressReq) {
        this.jdbcTemplate.update("update Address SET isMain='N' where addressIdx=?", patchAddressReq.getExistMainAddress());
        this.jdbcTemplate.update("update Address SET isMain='Y' where addressIdx=?", patchAddressReq.getChangeMainAddress());
        return this.jdbcTemplate.queryForObject("select addressIdx,isWhere,isMain, buildingAddress,mainAddress,subAddress\n" +
                        "      from Address where addressIdx=? AND status='ACTIVE'",
                (rs, rowNum) -> new PatchAddressRes(
                        rs.getInt("addressIdx"),
                        rs.getInt("isWhere"),
                        rs.getString("isMain"),
                        rs.getString("buildingAddress"),
                        rs.getString("mainAddress"),
                        rs.getString("subAddress")
                ), patchAddressReq.getChangeMainAddress());
    }

    //메인 주소인지 아닌지 체크
    public int checkMainAddress(int userIdx, PatchAddressReq patchAddressReq) {
        return this.jdbcTemplate.queryForObject("select exists(select addressIdx from Address where userIdx=? AND addressIdx=? AND status='ACTIVE' AND isMain='Y')",
                int.class, userIdx, patchAddressReq.getExistMainAddress());
    }

    //기존 메인주소라고 받은 것의 존재 확인
    public int checkexistsAddressIdx(int userIdx, PatchAddressReq patchAddressReq) {
        return this.jdbcTemplate.queryForObject("select exists(select addressIdx from Address where addressIdx=?)", int.class, patchAddressReq.getExistMainAddress());
    }

    //q바꾸고 싶다고 메인주소 받은것의 존재 확인
    public int checkchangeAddressIdx(int userIdx, PatchAddressReq patchAddressReq) {
        return this.jdbcTemplate.queryForObject("select exists(select addressIdx from Address where addressIdx=?)", int.class, patchAddressReq.getChangeMainAddress());
    }

    //결제관리조회
    public GetCrditRes getCrdit(int userIdx) {
        List<GetCardRes> getCardRes = this.jdbcTemplate.query("select paymentIdx,bank , concat('****',substring(number,5,3),'*') as number\n" +
                        "from Payment where paymentCategIdx=1 AND userIdx=? AND status='ACTIVE'",
                (rs, rownum) -> new GetCardRes(
                        rs.getInt("paymentIdx"),
                        rs.getString("bank"),
                        rs.getString("number")),
                userIdx
        );
        List<GetBankTransferRes> getBankTransfer = this.jdbcTemplate.query("select paymentIdx,bank ,  concat('****',right(number,4)) as number\n" +
                        "from Payment where paymentCategIdx=2 AND userIdx=? AND status='ACTIVE'",
                (rs, rownum) -> new GetBankTransferRes(
                        rs.getInt("paymentIdx"),
                        rs.getString("bank"),
                        rs.getString("number")), userIdx
        );
        String cashReceiptNumber = this.jdbcTemplate.queryForObject("select number as cashReceiptNumber from CashReceipt where userIdx=? AND status='ACTIVE'",
                String.class, userIdx);
        GetCrditRes getCrditRes = new GetCrditRes(getCardRes, getBankTransfer, cashReceiptNumber);
        return getCrditRes;
    }
    //결제 삭제 API
    public DeleteCreditRes deleteCredit(int userIdx,int paymentIdx){
        this.jdbcTemplate.update("update Payment SET status='INACTIVE' where paymentIdx=? AND userIdx=?",paymentIdx,userIdx);
        DeleteCreditRes deleteCreditRes = new DeleteCreditRes(paymentIdx);
        return deleteCreditRes;
    }
    //paymentIdx존재여부 리턴
    public int checkPaymentIdx(int userIdx,int paymentIdx){
        return this.jdbcTemplate.queryForObject("select exists(select paymentIdx from Payment where paymentIdx=?)",int.class,paymentIdx);
    }

    //현금 영수증 등록 API
    public PostCashReceiptRes postCashReceipt(int userIdx,PostCashReceiptReq postCashReceiptReq){
        this.jdbcTemplate.update("Insert into CashReceipt (number,isMethod,userIdx) VALUES (?,?,?)",
                postCashReceiptReq.getNumber(),postCashReceiptReq.getIsMethod(),userIdx);
        return this.jdbcTemplate.queryForObject("select cashReceiptIdx,number,isMethod from CashReceipt where userIdx=? AND status='ACTIVE'",
                (rs,rownum) ->new PostCashReceiptRes(
                        rs.getInt("cashReceiptIdx"),
                        rs.getString("number"),
                        rs.getInt("isMethod")
                ),userIdx);
    }
    //기존 현금 영수증 등록 여부
    public int checkCashReceiptIdx(int userIdx){
        return this.jdbcTemplate.queryForObject("select exists(select cashReceiptIdx from CashReceipt where userIdx=? AND status='ACTIVE')",int.class,userIdx);

    }
    //기존 현금 영수증 inacitve
    public void alreadyCashReceiptInactive(int userIdx){
        this.jdbcTemplate.update("update CashReceipt SET status='INACTIVE' where userIdx=? AND status='ACTIVE'",userIdx);
    }
    //현금 영수증 수정 api
    public PostCashReceiptRes patchCashReceipt(int userIdx,PostCashReceiptReq postCashReceiptReq){
        this.jdbcTemplate.update("update CashReceipt SET number = ?,isMethod=? where userIdx=? AND status='ACTIVE'",
                postCashReceiptReq.getNumber(),postCashReceiptReq.getIsMethod(),userIdx);
        return this.jdbcTemplate.queryForObject("select cashReceiptIdx,number,isMethod from CashReceipt where userIdx=? AND status='ACTIVE'",
                (rs,rownum) ->new PostCashReceiptRes(
                        rs.getInt("cashReceiptIdx"),
                        rs.getString("number"),
                        rs.getInt("isMethod")
                ),userIdx);
    }

    //쿠팡 마이 배달 이츠 ~
    public GetmyeatsPartnerRes getmyeatsPartner() {
        return this.jdbcTemplate.queryForObject("select imgUrl,address,ceo,number,date from GatherDeliveryPartner",
                (rs,rownum) -> new GetmyeatsPartnerRes(
                        rs.getString("imgUrl"),
                        rs.getString("address"),
                        rs.getString("ceo"),
                        rs.getString("number"),
                        rs.getString("date")
                ));
    }

    //자주 묻는 질문
    public List<GetFrequentlyRes> getFrequently() {
        return this.jdbcTemplate.query("select QuestionCategory as name from FrequentlyQuestion",
                (rs,rownum)-> new GetFrequentlyRes(
                        rs.getString("name"),
                        this.jdbcTemplate.query("select Question,Answer from FrequentlyQuestion where QuestionCategory=? limit 1",
                                (rs2,rownum2) -> new GetFrequentlyDetailRes(
                                        rs2.getString("Question"),
                                        rs2.getString("Answer")
                                ),rs.getString("name"))
                ));
    }


}




    


