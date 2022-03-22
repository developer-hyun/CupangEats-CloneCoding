package com.example.demo.src.user;
import java.sql.Timestamp;
import com.example.demo.config.BaseException;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.util.codec.binary.Base64;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.sql.DataSource;



import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.config.BaseResponseStatus.DELETE_USERS_EMPTY_USERIDX;
//import static com.example.demo.config.BaseResponseStatus.POST_USERS_EXISTS_EMAIL;

// Service Create, Update, Delete 의 로직 처리
@Service
public class UserService {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final UserDao userDao;
    private final UserProvider userProvider;
    private final JwtService jwtService;
    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public UserService(UserDao userDao, UserProvider userProvider, JwtService jwtService) {
        this.userDao = userDao;
        this.userProvider = userProvider;
        this.jwtService = jwtService;

    }

    /**
     * 유저 생성 API
     *
     * @param postUserReq
     * @return
     * @throws BaseException
     */
    //POST
    public PostUserRes createUser(PostUserReq postUserReq) throws BaseException {
        //중복
        if (userProvider.checkId(postUserReq.getId()) == 1) {
            throw new BaseException(POST_USERS_EXISTS_USERID);
        }
        //휴대폰 번호 중복 체크
        if (userProvider.checkPhoneNumber(postUserReq.getPhoneNumber()) == 1) {
            throw new BaseException(POST_USERS_EXISTS_PHONENUMBER);
        }

//        String pwd;
//        try {
//            //암호화
//            pwd = new AES128(Secret.USER_INFO_PASSWORD_KEY).encrypt(postUserReq.getPassword());
//            postUserReq.setPassword(pwd);
//        } catch (Exception ignored) {
//            throw new BaseException(PASSWORD_ENCRYPTION_ERROR);
//        }

        int userIdx = userDao.createUser(postUserReq);

        //jwt발급
        String jwt = jwtService.createJwt(userIdx);
        return new PostUserRes(jwt, userIdx);
    }

    /**
     * 로그인 api
     * ////     *
     * ////
     */
    public PostLogInRes logIn(PostLogInReq postLogInReq) throws BaseException {
        // PostLogInRes postLogInRes = userDao.logIn(postLogInReq.);
//        System.out.print(getUserRes.getPassword());
//        String password;
//        try {
//           // System.out.println("1");
//            password = new AES128(Secret.USER_INFO_PASSWORD_KEY).decrypt(getUserRes.getPassword()); // userinfo key 는 암호화할때 비번
//           // System.out.println("2");
//        } catch (Exception ignored) {
//            throw new BaseException(PASSWORD_DECRYPTION_ERROR);
//        }
//      //  System.out.println("3");
//        System.out.println(password);
//        System.out.println(postLogInReq.getPassword());
//        if (postLogInReq.getPassword().equals(password)) {
//        int userIdx = userDao.logIn(postLogInReq).getUserIdx();
//        String jwt = jwtService.createJwt(userIdx);
//        return new PostLogInRes(jwt, userIdx);
//        }
//        else{
//            throw new BaseException(FAILED_TO_LOGIN);
//        }
//    }
        GetLogInInfo getLogInInfo = userDao.logIn(postLogInReq);
        System.out.println(getLogInInfo.getPassword());
        if (postLogInReq.getPassword().equals(getLogInInfo.getPassword())) {
            int userIdx = getLogInInfo.getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            return new PostLogInRes(jwt, userIdx);
        } else {
            throw new BaseException(FAILED_TO_LOGIN);
        }
    }

    /**
     * 유저 삭제 API
     *
     * @param userIdx
     * @return
     * @throws BaseException
     */
    public DeleteUserRes deleteUser(int userIdx) throws BaseException {
        if (userDao.checkuserIdx(userIdx) == 0) {
            throw new BaseException(DELETE_USERS_EMPTY_USERIDX);
        }
        try {
            userDao.deleteUser(userIdx);
            return new DeleteUserRes(userIdx);

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }

    public PostLikeRes likeAdd(int userIdx, int storeIdx) throws BaseException {
        //System.out.println(userDao.likeExist(userIdx,storeIdx));
        if (userDao.checkStoreIdx(storeIdx) == 0) {
            throw new BaseException(INVALID_STOREIDX);
        }

        try {
            if (userDao.likeExist(userIdx, storeIdx) == 1) {
                if (userDao.likeCheckStatus(userIdx, storeIdx).equals("ACTIVE")) {
                    int likeIdx = userDao.likeChangeStatusInactive(userIdx, storeIdx);  //acitve니까 inactive바꾸고 현재 상태를 리턴
                    String status = userDao.likeCheckStatus(userIdx, storeIdx);
                    PostLikeRes postLikeRes = new PostLikeRes(status, likeIdx);
                    return postLikeRes;
                } else {
                    int likeIdx = userDao.likeChangeStatusActive(userIdx, storeIdx);
                    String status = userDao.likeCheckStatus(userIdx, storeIdx);
                    PostLikeRes postLikeRes = new PostLikeRes(status, likeIdx);
                    return postLikeRes;
                }
            } else {
                int likeIdx = userDao.likeAdd(userIdx, storeIdx);
                String status = userDao.likeCheckStatus(userIdx, storeIdx);
                PostLikeRes postLikeRes = new PostLikeRes(status, likeIdx);
                return postLikeRes;
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);

        }
    }

    //즐겨찾기 수정
    public PatchLikeRes patchLike(int userIdx, PatchLikeReq patchLikeReq) throws BaseException {
        try {


            for (Integer storeIdx : patchLikeReq.getStoreIdx()) {
                if (userDao.checkStoreIdx(storeIdx) == 0) {
                    throw new BaseException(INVALID_STOREIDX);
                }
                userDao.patchLike(userIdx, storeIdx);
                // return patchLikeRes;
            }
            PatchLikeRes patchLikeRes = new PatchLikeRes(userIdx, patchLikeReq.getStoreIdx());
            return patchLikeRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

//    // 배달 주소 생성
//    public PostAdressRes postAdress(int userIdx, PostAdressReq postAdressReq) throws BaseException {
//
//        try {
//            PostAdressRes postAdressRes = userDao.postAdress(userIdx, postAdressReq);
//            return postAdressRes;
//        } catch (Exception exception) {
//            throw new BaseException(DATABASE_ERROR);
//        }
//    }

    public PostLogInRes kakaoCreateUser(String email, String name) throws BaseException {

        try {
            int userIdx = userDao.createKakaoUser(email, name);
            //jwt 발급.
            String jwt = jwtService.createJwt(userIdx);
            return new PostLogInRes(jwt, userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);


        }
    }

    public PostLogInRes kakaologIn(String userId) throws BaseException {

//try-catch 문
        GetLogInInfo getLogInInfo = userDao.kakaologIn(userId);

        try {
            int userIdx = getLogInInfo.getUserIdx();
            String jwt = jwtService.createJwt(userIdx);
            return new PostLogInRes(jwt, userIdx);
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);


        }
    }

    public String Check(String code, String phone) throws BaseException {


        try {
            if (userDao.Check(code, phone) == 0) {
                userDao.patchCheck(phone);
                return "F";
            } else
                userDao.patchCheck(phone);
            return "T";
        } catch (Exception exception) {
            throw new BaseException(FAILED_TO_PHONENUMBER);

        }
    }


    public String sendSms(String recipientPhoneNumber, String content) throws ParseException, JsonProcessingException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException, BaseException {
        Long time = new Timestamp(System.currentTimeMillis()).getTime();
        //   Long time2 = new DateCreator().getTimestamp().getTime();

        List<MessageReq> messages = new ArrayList<>();

        messages.add(new MessageReq(recipientPhoneNumber, content));


        // 보내는 사람에게 내용을 보냄.

        SmsReq smsRequestDto = new SmsReq("SMS", "COMM", "82", "", "MangoLtd", messages);
        ObjectMapper objectMapper = new ObjectMapper();

        //보안의 이유로 삭제
        return "123";
    }




    // 배달 주소 생성
    public PostAddressRes postAdress(int userIdx, PostAddressReq postAdressReq) throws BaseException {

        try {
            if (postAdressReq.getIsWhere() == 1) {
                if (userDao.checkHouseExists(userIdx) == 1) {
                    int mainAddress = userDao.mainAddress(userIdx);
                    userDao.changeMainAddressN(userIdx, mainAddress);
                    userDao.alreadlyexistHouseChangeInactive(userIdx);
                    PostAddressRes postAddressRes = userDao.postAddress(userIdx, postAdressReq);
                    return postAddressRes;
                } else {  //집이 없어
                    int mainAddress = userDao.mainAddress(userIdx);
                    userDao.changeMainAddressN(userIdx, mainAddress);
                    PostAddressRes postAddressRes = userDao.postAddress(userIdx, postAdressReq);
                    return postAddressRes;
                }
            } else if (postAdressReq.getIsWhere() == 2) {
                if (userDao.checkCompanyExists(userIdx) == 1) {
                    int mainAddress = userDao.mainAddress(userIdx);
                    userDao.changeMainAddressN(userIdx, mainAddress);
                    userDao.alreadlyexistCompanyChangeInactive(userIdx);
                    PostAddressRes postAddressRes = userDao.postAddress(userIdx, postAdressReq);
                    return postAddressRes;
                } else { //회사가없어
                    int mainAddress = userDao.mainAddress(userIdx);
                    userDao.changeMainAddressN(userIdx, mainAddress);
                    PostAddressRes postAddressRes = userDao.postAddress(userIdx, postAdressReq);
                    return postAddressRes;
                }
            } else {
                int mainAddress = userDao.mainAddress(userIdx);
                userDao.changeMainAddressN(userIdx, mainAddress);
                PostAddressRes postAddressRes = userDao.postAddress(userIdx, postAdressReq);
                return postAddressRes;
            }

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    //배달 주소 변경
    public PatchAddressRes patchAddress(int userIdx,PatchAddressReq patchAddressReq) throws BaseException {
        if (userDao.checkMainAddress(userIdx, patchAddressReq) == 0) { //클라로 받은 idx가 메인주소가 아닐경우
            throw new BaseException(FAILED_TO_MAINADDRESS);
        }
        if (userDao.checkexistsAddressIdx(userIdx, patchAddressReq) == 0) { //메인주소 dix가 없는 idx
            throw new BaseException(FAILED_TO_INVAILDEXISTSADDRESS);
        }
        if (userDao.checkchangeAddressIdx(userIdx, patchAddressReq) == 0) {
            throw new BaseException(FAILED_TO_INVALIDCHANGEADDRESS);
        }
        try {
            PatchAddressRes patchAddressRes = userDao.patchAddress(userIdx, patchAddressReq);
            return patchAddressRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
       }

    }

    //결제 관리 카드 삭제
    public DeleteCreditRes deleteCredit(int userIdx,int paymentIdx)throws BaseException {
        if (userDao.checkPaymentIdx(userIdx, paymentIdx) == 0) { //없는 idx인지 확인
            throw new BaseException(FAILDE_TO_GETPAYMENTIDX);
        }
        try {
            DeleteCreditRes deleteCreditRes = userDao.deleteCredit(userIdx, paymentIdx);
            return deleteCreditRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    //현금 영수증 등록
    public PostCashReceiptRes postCashReceipt(int userIdx,PostCashReceiptReq postCashReceiptReq)throws BaseException{
        try {
            if (userDao.checkCashReceiptIdx(userIdx) == 1) {
                userDao.alreadyCashReceiptInactive(userIdx); //기존꺼 inactive
                PostCashReceiptRes postCashReceiptRes = userDao.postCashReceipt(userIdx, postCashReceiptReq);
                return postCashReceiptRes;
            } else {
                PostCashReceiptRes postCashReceiptRes = userDao.postCashReceipt(userIdx, postCashReceiptReq);
                return postCashReceiptRes;
            }
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }
    //현금영수증 수정 API
    public PostCashReceiptRes patchCashReceipt(int userIdx,PostCashReceiptReq postCashReceiptReq)throws BaseException{
        try {

                PostCashReceiptRes postCashReceiptRes = userDao.patchCashReceipt(userIdx, postCashReceiptReq);
                return postCashReceiptRes;

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }

    }
}