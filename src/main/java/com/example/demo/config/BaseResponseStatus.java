package com.example.demo.config;

import lombok.Getter;

/**
 * 에러 코드 관리
 */
@Getter
public enum BaseResponseStatus {
    /**
     * 1000 : 요청 성공
     */
    SUCCESS(true, 1000, "요청에 성공하였습니다."),
    SUCCESS_POST_USER(true, 1001, "회원가입을 성공했습니다"),
    SUCCESS_POST_LOGIN(true, 1002, "로그인을 성공했습니다"),
    SUCCESS_POST_POSTING(true,1003,"작성 완료했습니다"),
    SUCCESS_PATCH(true,1004,"정보를 수정했습니다"),
    SUCCESS_GET(true, 1005, "조회를 성공했습니다"),
    SUCCESS_DELETED(true,1006,"삭제되었습니다"),
    /**
     * 2000 : Request 오류
     */
    // Common
    REQUEST_ERROR(false, 2000, "입력값을 확인해주세요."),

    EMPTY_JWT(false, 2001, "JWT를 입력해주세요."),
    INVALID_JWT(false, 2002, "유효하지 않은 JWT입니다."),
    INVALID_USER_JWT(false,2003,"권한이 없는 유저의 접근입니다."),
    INVALID_USER_PASSWORD(false, 2004,"비밀번호가 일치하지 않습니다. 재입력하세요"),
    INVALID_USERIDX(false, 2005,"존재하지 않는 아이디입니다"),
    EMPTY_INPUT(false,2006, "입력값이 없습니다. 확인해주세요"),
    NO_RESULT_SEARCH(false, 2028, "검색 결과가 없습니다." +
            "다른 검색어로 검색해보세요"),
    INVALID_TYPE(false, 2029, "입력값의 타입을 확인해주세요."),


    //Get

    INVALID_STOREIDX(false,2040,"존재하지 않는 스토어입니다"),
    INVALID_MENUIDX(false,2041,"존재하지 않는 메뉴입니다"),
    INVALID_ORDERIDX(false,2042,"존재하지 않는 주문입니다"),
    INVALID_ADDRESSIDX(false,2043,"존재하지 않는 주소입니다"),
    INVALID_SUBSIDECATEGORYIDX(false,2044,"존재하지않는 사이드카테고리입니다"),
    INVALID_PAYMENTIDX(false,2045,"존재하지않는 결제수단입니다"),
    INVALID_LIKESORT(false,2046,"likesort입력이 잘못되었습니다."),
    INVALID_CATEGORYIDX(false,2047,"존재하지 않는 카테고리입니다"),
    INVALID_REVIEWPHOTO(false,2048,"리뷰포토 조건을 잘못입력했습니다."),
    INVALID_REVIEWSORT(false,2049,"리뷰정렬조건을 잘못입력했습니다."),
    INVALID_GETORDERIDX(false,2050,"orderIdx를 입력해주세요"),
    INVALID_GETISPHOTO(false,2051,"isPhoto를 입력해주세요"),
    INVALID_GETISTEXT(false,2052,"isText를 입력해주세요"),
    INVALID_CURRNETSTATUS(false,2053,"현재 도움 상태가 잘못되었습니다."),
    INVALID_PUSHSTATUS(false,2054,"요청하신 도움 상태가 잘못되었습니다."),
    INVALID_GETMAINADDRESS(false,2056,"메인주소를 입력해주세요."),
    INVALID_GETBUILDINGADDRESS(false,2057,"건물주소를 입력해주세요"),
    INVALID_GETISWHERE(false,2058,"집,회사,기타 조건을 입력해주세요"),
    INVALID_GETISWHERECONDITION(false,2059,"허용되지 않은 집,회사,기타 조건입니다."),
    INVALID_GETISMETHOD(false,2060,"isMethod범위를 확인해주시기 바랍니다."),
    //post
    POST_USERS_EMPTY_ID(false, 2215, "아이디를 입력해주세요."),
    POST_USERS_EMPTY_PASSWORD(false, 2216, "비밀번호를 입력해주세요."),
    POST_USERS_EMPTY_NAME(false, 2217, "이름을 입력해주세요."),
    POST_USERS_EMPTY_PHONENUMBER(false, 2218, "휴대전화번호를 입력해주세요."),
    POST_USERS_EXISTS_USERID(false,2219,"중복된 아이디입니다"),
    POST_USERS_INVALID_COMBINE_PASSWORD(false, 2220, "비밀번호 형식을 확인해주세요.(문자,숫자,특수문자중 2개이상)"),
    POST_USERS_EXISTS_PHONENUMBER(false,2221,"중복된 휴대폰번호입니다."),
    POST_USERS_INVALID_CONNECT_PASSWORD(false,2222,"비밀번호에 연속된 문자가 사용되었습니다."),



    POST_USERS_INVALID_EMAIL(false, 2016, "이메일 형식을 확인해주세요."),
    //   POST_USERS_EXISTS_EMAIL(false,2017,"중복된 이메일입니다."),
    POST_USERS_EMPTY_USER(false, 2017, "아이디를 입력해주세요."),
    //  POST_USERS_EXISTS_userId(false,2019,"중복된 아이디입니다"),

    POST_USERS_EMPTY_AGE(false, 2020, "생년월일을 입력해주세요."),
    POST_USERS_INVALID_PHONE(false, 2022,"휴대번호 형식을 확인해주세요"),
    POST_USERS_EMPTY_ADDRESS(false, 2023, "주소를 입력해주세요."),


    POST_ORDERS_EMPTY_ADDRESSIDX(false, 2100, "주소를 입력해주세요."),
    POST_ORDERS_EMPTY_ORDERPRICE(false, 2101, "주문금액을 입력해주세요."),
    POST_ORDERS_EMPTY_DELIVERYCOST(false, 2102, "배달비를 입력해주세요."),


    POST_ORDERS_EMPTY_PAYMENTIDX(false,2103,"결제방법을 입력해주세요"),
    POST_ORDERS_EMPTY_ISDISPOSABLE(false,2104,"일회용품 선택 여부를 입력해주세요."),
    POST_ORDERS_EMPTY_TODELIVERYRIDER(false,2105,"배달기사님께 요청사항 입력해주세요."),
    POST_ORDERS_EMPTY_MENUIDX(false, 2110, "메뉴를 입력해주세요."),
    POST_ORDERS_EMPTY_COUNT(false, 2111, "수량을 입력해주세요."),
    POST_ORDERS_EMPTY_DISCOUNT(false, 2103, "할인금액을 입력해주세요."),



    POST_ORDERS_EMPTY_SUBSIDECATEGORYIDX(false, 2120, "사이드카테고리를 입력해주세요."),


    //patch
    PATCH_ORDERS_EMPTY_ORDERIDX(false,2201,"주문번호를 입력하세요"),



    INVALID_ORDER(false,2210,"order 입력을 확인해주세요."),
    INVALID_COUPON(false, 2211,"coupon 입력을 확인해주세요."),
    INVALID_KEYWORD(false, 2212,"검색 결과가 없습니다."),


    /**
     * 3000 : Response 오류
     */
    // Common
    RESPONSE_ERROR(false, 3000, "값을 불러오는데 실패하였습니다."),

    FAILED_TO_LOGIN(false,3014,"현재 존재하지 않는 계정입니다."),
    ERROR_PASSWORD(false,3015,"비밀번호가 틀렸습니다."),


    DUPLICATED_EMAIL(false, 3020, "중복된 이메일입니다."),
    DUPLICATED_USERID(false, 3021, "중복된 닉네임니다."),

// GET실 패
FAILED_TO_GETSTORE(false,3030,"현재 주문할 수 없는 스토입니다."),
    FAILED_TO_GETMENU(false,3031,"현재 주문할 수 없는 메뉴입니다."),
    FAILED_TO_GETORDER(false,3032,"현재 조회할 수 없는 주문입니다."),
    FAILED_TO_PATCHORDER(false,3033,"취소된 주문입니다."),
    FAILED_TO_ADDRESS(false,3034,"현재 유효하지 않은 주소입니다"),
    FAILED_TO_GETORDERIDX(false,3035,"존재하지않는 주문 번호입니다."),

    FALIED_TO_GETREVIEW_DEADLINE(false,3037,"리뷰작성기간 초과로 인해 작성할 수 없습니다."),
    FAILED_TO_GETREVIEWIDX(false,3138,"존재하지 않는 리뷰 입니다."),

    FAILED_TO_GETDELIVERY(false,3036,"배달 완료된 주문입니다"),
    FAILED_TO_USECOUPON(false,3038,"사용할 수 없는 쿠폰입니다"),
    NOT_EQUAL_REVIEWLIKEDSTATUS(false,3039,"요청하신 도움 상태와 DB에 저장된 도움의 상태가 일치하지 않습니다."),
    FAILED_TO_CATEGORY(false,3041,"현재 유효하지 않은 카테고리입니다"),
    FAILED_TO_PAYMENT(false,3042,"현재 유효하지 않은 결제입니다"),

    FAILED_TO_PHONENUMBER(false,3043,"요청하지 않은 번호입니다"),
    FAILED_TO_MAINADDRESS(false,3044,"요청하신 addressIdx는 현재 설정된 메인주소 addressIdx가 아닙니다. 다시 요청해주십시오."),
    FAILED_TO_INVAILDEXISTSADDRESS(false,3045,"요청하신 현재 설정된 메인주소 addressIdx는 없는 addressIdx입니다."),
    FAILED_TO_INVALIDCHANGEADDRESS(false,3046,"요청하신 변경하고자 하는 주소 addressIdx는 없는 addressIdx입니다."),
    FAILDE_TO_GETPAYMENTIDX(false,3047,"요청하신 paymentIdx는 존재하지 않습니다."),
    // delete실패
    DELETE_USERS_EMPTY_USERIDX(false,3037,"존재하지 않는 유저입니다."),
    /**
     * 4000 : Database, Server 오류
     */
    DATABASE_ERROR(false, 4000, "데이터베이스 연결에 실패하였습니다."),
    SERVER_ERROR(false, 4001, "서버와의 연결에 실패하였습니다."),
    //[POST]/users,questions


    PASSWORD_ENCRYPTION_ERROR(false, 4030, "비밀번호 암호화에 실패하였습니다."),
    PASSWORD_DECRYPTION_ERROR(false, 4031, "비밀번호 복호화에 실패하였습니다.");



    // 5000 : 필요시 만들어서 쓰세요
    // 6000 : 필요시 만들어서 쓰세요


    private final boolean isSuccess;
    private final int code;
    private final String message;

    private BaseResponseStatus(boolean isSuccess, int code, String message) {
        this.isSuccess = isSuccess;
        this.code = code;
        this.message = message;
    }
}

