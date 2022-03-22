package com.example.demo.src.user;

//import org.json.simple.parser.JSONParser;

import com.example.demo.utils.ValidationRegex;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ser.Serializers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import com.example.demo.src.authentication.model.PostPhoneReq;
import com.example.demo.utils.ValidationRegex;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.parameters.P;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.view.RedirectView;

import java.io.UnsupportedEncodingException;
import java.net.URISyntaxException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.text.ParseException;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.*;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Array;
import java.util.*;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;


import static com.example.demo.config.BaseResponseStatus.*;
import static com.example.demo.utils.ValidationRegex.*;

@RestController
@RequestMapping("/app/users")  // 이 url을 입력하면 이렇게 되도록 맵핑함
public class UserController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //엮겠다.  // 대문자는 해당 클래스를 불러오고 소문자는 클래스에서 객체를 만들어서 사용함
    private final UserProvider userProvider;
    @Autowired
    private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    // autowired를 쓰려면 밑에 이렇게 한다음 클래스 전체에서 쓸수 있게 this로 해줘야한다??  // 생성자
    public UserController(UserProvider userProvider, UserService userService, JwtService jwtService) {
        this.userProvider = userProvider;   // userProvider를 클래스 전체에서 쓰겠당
        this.userService = userService;
        this.jwtService = jwtService;
    }

    /**
     * 회원가입 API
     * [POST] /users
     *
     * @return BaseResponse<PostUserRes>
     */
    // Body
    @ResponseBody
    @PostMapping("")
    public BaseResponse<PostUserRes> createUser(@RequestBody PostUserReq postUserReq) {
        //아이디 비밀번호 이름 폰번호 null 금지 처리
        if (postUserReq.getId() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_ID);
        }
        if (postUserReq.getPassword() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PASSWORD);
        }
        if (postUserReq.getName() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_NAME);
        }
        if (postUserReq.getPhoneNumber() == null) {
            return new BaseResponse<>(POST_USERS_EMPTY_PHONENUMBER);
        }

        //아이디 정규표현  아이디@네이버.com
        if (!isRegexEmail(postUserReq.getId())) {
            return new BaseResponse<>(POST_USERS_INVALID_EMAIL);
        }
        // 비밀번호 정규표현
//        if (!(isRegexPassowrd1(postUserReq.getPassword())||isRegexPassowrd2(postUserReq.getPassword())||isRegexPassowrd3(postUserReq.getPassword())||isRegexPassword4(postUserReq.getPassword()))) {
//            return new BaseResponse<>(POST_USERS_INVALID_COMBINE_PASSWORD);
//        }
        // System.out.println(postUserReq.getPassword());
        if (!(isRegexPassowrd1(postUserReq.getPassword()) || isRegexPassowrd2(postUserReq.getPassword()) || isRegexPassowrd3(postUserReq.getPassword()) || isRegexPassword4(postUserReq.getPassword()))) {
            return new BaseResponse<>(POST_USERS_INVALID_COMBINE_PASSWORD);
        }
        //비밀번호 3개문자 정규표햔
//        if (!isRegexPassword5(postUserReq.getPassword())){
//            return new BaseResponse<>(POST_USERS_INVALID_CONNECT_PASSWORD);
//        }

        //비밀번호 아이디와 중복된것 반환
        // if
        //휴대번호 정규 표현
        if (!isRegexPhone(postUserReq.getPhoneNumber())) {
            return new BaseResponse<>(POST_USERS_INVALID_PHONE);
        }

        try {
            PostUserRes postUserRes = userService.createUser(postUserReq);
            return new BaseResponse<>(postUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 로그인
     *
     * @param postLogInReq
     * @return
     */
    @ResponseBody
    @PostMapping("/logIn")
    public BaseResponse<PostLogInRes> LogIn(@RequestBody PostLogInReq postLogInReq) {
        try {
            PostLogInRes postLogInRes = userService.logIn(postLogInReq);
            return new BaseResponse<>(postLogInRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }
    }

    /**
     * myeats 화면 구성 autologin
     */
    @ResponseBody
    @GetMapping("/autologin/{userIdx}")
    public BaseResponse<GetMyEatsRes> myeats(@PathVariable("userIdx") int userIdx) {
//        try {
//            int userIdxJwt = jwtService.getuserIdx();
//        } catch (BaseException exception) {
//            return new BaseResponse<>(EMPTY_JWT);
//        }
//        try {
//            int userIdxJwt = jwtService.getuserIdx();

//            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
//                return new BaseResponse<>(INVALID_JWT);
//            }
            GetMyEatsRes getMyEatsRes = userProvider.myeats(userIdx);
            return new BaseResponse<>(getMyEatsRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//
//        }

    }

    /**
     * 회원삭제 api
     */
    @ResponseBody
    @DeleteMapping("/{userIdx}")
    public BaseResponse<DeleteUserRes> deleteUser(@PathVariable("userIdx") int userIdx) {
//        try {
//            int userIdxJwt = jwtService.getuserIdx();
//        } catch (BaseException exception) {
//            return new BaseResponse<>(EMPTY_JWT);
//        }
        try {
//            int userIdxJwt = jwtService.getuserIdx();
//
//            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
//                return new BaseResponse<>(INVALID_JWT);
//            }
            DeleteUserRes deleteUserRes = userService.deleteUser(userIdx);
            return new BaseResponse<>(deleteUserRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));
        }
    }

    /**
     * 즐겨찾기
     *
     * @param userIdx
     * @param likesort
     * @return
     */
    @ResponseBody
    @GetMapping("/like/{userIdx}")
    public BaseResponse<GetLikeRes> likeRes(@PathVariable("userIdx") int userIdx,
                                            @RequestParam(value = "likesort", required = false, defaultValue = "latelyOrder") String likesort) throws Exception {
        //System.out.println((likesort == "latelyOrder"));
//        try {
//            int userIdxJwt = jwtService.getuserIdx();
//        } catch (BaseException exception) {
//            return new BaseResponse<>(EMPTY_JWT);
//        }

//        try {
//
//            int userIdxJwt = jwtService.getuserIdx();
//
//            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
//                return new BaseResponse<>(INVALID_JWT);
//            }
            if ((likesort.equals("latelyOrder")) || (likesort.equals("latelyPlus"))) {
                GetLikeRes getLikeRes = userProvider.getlike(userIdx, likesort);
                return new BaseResponse<>(getLikeRes);
            } else {
                return new BaseResponse<>(INVALID_LIKESORT);

            }
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//        }
    }

    /**
     * 즐겨찾기 생성 API
     *
     * @param userIdx
     * @param storeIdx
     * @return
     * @throws Exception
     */

    @ResponseBody
    @PostMapping("/like/{userIdx}/{storeIdx}")
    public BaseResponse<PostLikeRes> likeAdd(@PathVariable("userIdx") int userIdx,
                                             @PathVariable("storeIdx") int storeIdx) throws Exception {
        try {
            int userIdxJwt = jwtService.getuserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        try {
            int userIdxJwt = jwtService.getuserIdx();

            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
                return new BaseResponse<>(INVALID_JWT);
            }
            PostLikeRes postLikeRes = userService.likeAdd(userIdx, storeIdx);
            return new BaseResponse<>(postLikeRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));

        }
    }

    //즐겨찾기 수정
    @ResponseBody
    @PatchMapping("/like/{userIdx}")
    public BaseResponse<PatchLikeRes> patchLike(@PathVariable("userIdx") int userIdx,
                                                @RequestBody PatchLikeReq patchLikeReq) throws BaseException {
        //   System.out.println(patchLikeReq.getStoreIdx());
        try {
            int userIdxJwt = jwtService.getuserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        try {
            int userIdxJwt = jwtService.getuserIdx();

            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
                return new BaseResponse<>(INVALID_JWT);
            }
            PatchLikeRes patchLikeRes = userService.patchLike(userIdx, patchLikeReq);
            return new BaseResponse<>(patchLikeRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));


        }


    }

//    //배달 주소 생성
//    @ResponseBody
//    @PostMapping("/address/{userIdx}")
//    public BaseResponse<PostAdressRes> postAdressRes(@PathVariable("userIdx") int userIdx,
//                                                     @RequestBody PostAdressReq postAdressReq) throws BaseException {
//        try {
//            int userIdxJwt = jwtService.getuserIdx();
//        } catch (BaseException exception) {
//            return new BaseResponse<>(EMPTY_JWT);
//        }
//
//        try {
//            int userIdxJwt = jwtService.getuserIdx();
//
//            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
//                return new BaseResponse<>(INVALID_JWT);
//            }
//            if (postAdressReq.getMainAdress() == null) {
//                throw new BaseException(INVALID_GETMAINADDRESS);
//            }
//
//            PostAdressRes postAdressRes = userService.postAdress(userIdx, postAdressReq);
//            return new BaseResponse<>(postAdressRes);
//        } catch (BaseException exception) {
//            return new BaseResponse<>((exception.getStatus()));
//
//
//        }
//
//
//    }


    @GetMapping("/kakao-login")
    public RedirectView index() {
        return new RedirectView("");
    }


    @GetMapping("/kakao")
    public @ResponseBody
    BaseResponse<PostLogInRes> kakaologin(String code) throws UnsupportedEncodingException, BaseException {
        try {
            RestTemplate rt = new RestTemplate();

            // HttpHeader 오브젝트 생성
            HttpHeaders headers = new HttpHeaders();
            headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

            // HttpBody 오브젝트 생성
            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "authorization_code");
            params.add("client_id", "");
            params.add("redirect_uri", "");
            params.add("code", code);


            // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
            HttpEntity<MultiValueMap<String, String>> kakaoTokenRequest =
                    new HttpEntity<>(params, headers);

            // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
            ResponseEntity<String> response = rt.exchange(

                    "https://kauth.kakao.com/oauth/token",
                    HttpMethod.POST,
                    kakaoTokenRequest,
                    String.class
            );

            // Gson, Json Simple, ObjectMapper
            ObjectMapper objectMapper = new ObjectMapper();
            OAuthToken oauthToken = null;
            try {
                oauthToken = objectMapper.readValue(response.getBody(), OAuthToken.class);
            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }


            RestTemplate rt2 = new RestTemplate();

            // HttpHeader 오브젝트 생성
            HttpHeaders headers2 = new HttpHeaders();
            headers2.add("Authorization", "Bearer " + oauthToken.getAccess_token());
            headers2.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");


            HttpEntity<MultiValueMap<String, String>> kakaoProfileRequest2 =
                    new HttpEntity<>(headers2);

            ResponseEntity<String> response2 = rt2.exchange(
                    "https://kapi.kakao.com/v2/user/me",
                    HttpMethod.POST,
                    kakaoProfileRequest2,
                    String.class
            );

            PostLogInRes postLogInRes;

            ObjectMapper objectMapper2 = new ObjectMapper();
            KakaoProfile kakaoProfile = null;
            try {

                kakaoProfile = objectMapper2.readValue(response2.getBody(), KakaoProfile.class);

            } catch (JsonMappingException e) {
                e.printStackTrace();
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }


            if (userProvider.checkId(kakaoProfile.getKakao_account().getEmail()) == 0) {

                postLogInRes = userService.kakaoCreateUser(kakaoProfile.getKakao_account().getEmail(), kakaoProfile.getProperties().getNickname());

            } else {
                postLogInRes = userService.kakaologIn(kakaoProfile.getKakao_account().getEmail());
            }

            return new BaseResponse<>(postLogInRes);
        } catch (BaseException exception) {
            return new BaseResponse<>(exception.getStatus());
        }

    }

    @ResponseBody
    @GetMapping("/address/{userIdx}")
    public BaseResponse<GetAddressTranferRes> getaddresstransfer(@PathVariable("userIdx") int userIdx,
                                                                 @RequestParam(required = true) float latitude,
                                                                 @RequestParam(required = true) float longitude) throws BaseException {

        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        headers.add("Authorization", "");
        String tolatitude = Float.toString(latitude);
        String tolongitude = Float.toString(longitude);
        String url = "https://dapi.kakao.com/v2/local/geo/coord2address.json?x=" + tolongitude + "&y=" + tolatitude + "&input_coord=WGS84";
        // System.out.println(url);
        //  String url = "https://dapi.kakao.com/v2/local/geo/coord2address.json?x=127.423084873712&y=37.0789561558879&input_coord=WGS84";
        //UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();
        ResponseEntity<String> resultMap = rt.exchange(
                url,
                HttpMethod.GET,
                entity,
                String.class
        );
        ObjectMapper objectMapper = new ObjectMapper();
        Addressfun addressfun = null;
        try {
            addressfun = objectMapper.readValue(resultMap.getBody(), Addressfun.class);
        } catch (JsonMappingException e) {
            e.printStackTrace();
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        String buildingAddress = new String();
        String mainAddress = new String();

        if (addressfun.getDocuments().get(0).getRoad_address() == null) { //도로명 주소가 null이며ㅑㄴ
            buildingAddress = addressfun.getDocuments().get(0).getAddress().getAddress_name();
            mainAddress = addressfun.getDocuments().get(0).getAddress().getAddress_name();
        } else {
            if ((addressfun.getDocuments().get(0).getRoad_address().getBuilding_name() == null) || (addressfun.getDocuments().get(0).getRoad_address().getBuilding_name().equals(""))) {  //도로명이 있는데 빌딩이 널이면
                buildingAddress = addressfun.getDocuments().get(0).getRoad_address().getAddress_name();
                mainAddress = addressfun.getDocuments().get(0).getRoad_address().getAddress_name();
            } else { //q빌딩 이름 다 있으면
                buildingAddress = addressfun.getDocuments().get(0).getRoad_address().getBuilding_name();
                mainAddress = addressfun.getDocuments().get(0).getRoad_address().getAddress_name();
            }

        }

        GetAddressTranferRes getAddressTranferRes = new GetAddressTranferRes(buildingAddress, mainAddress);
        return new BaseResponse<>(getAddressTranferRes);

    }
    // return new BaseResponse<>(addressfun);
    //  System.out.println(addressfun.getDocuments().get(0).getRoad_address().getAddress_name());


//            result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
//            result.put("header", resultMap.getHeaders()); //헤더 정보 확인
//            result.put("body", resultMap.getBody()); //실제 데이터 정보 확인
//
//            LinkedHashMap lm = (LinkedHashMap) resultMap.getBody().get("documents");
//            ArrayList<Map> add = (ArrayList<Map>) lm.get("road_address");
//            LinkedHashMap addList = new LinkedHashMap<>();
//            for (Map road : add) {
//                addList.put(road.get("address_name"), road.get("building_name"));
//            }
//            ObjectMapper mapper = new ObjectMapper();
//            List<> al =  mapper.convertValue(list, new TypeReference<List<DocumentTypeVO>>() {})
//
//            jsonInString = mapper.writeValueAsString(addList);
//            System.out.println(jsonInString);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException (e);
//        }

    //return jsonInString;
    //ObjectMapper mapper = new ObjectMapper();
    //jsonInString = mapper.writeValueAsString(resultMap.getBody());
    //  System.out.println(result.put("body" , resultMap.getBody()));

    //  LinkedHashMap lm = (LinkedHashMap)resultMap.getBody().get("documents");
//         ArrayList lm = (ArrayList)resultMap.getBody().get("documents");
////        System.out.println(lm.get(0));
//         Addressfun addressfun = new Addressfun(lm);

    //System.out.println(addressfun);
//            Object ob = new Object();
//            for (Object ar : lm) {
//
//            }
    //   addressfun.getRoad_address()


    //  return new BaseResponse<>(addressfun);
    @ResponseBody
    @GetMapping("/address/details/{userIdx}")
    public BaseResponse<GetdeliveryApi> delivery(@PathVariable("userIdx") int userIdx,
                                                 @RequestParam(required = true) float latitude,
                                                 @RequestParam(required = true) float longitude) throws BaseException {
        //String jsonInString = " ";

        HashMap<String, Object> result = new HashMap<String, Object>();
        RestTemplate rt = new RestTemplate();
        HttpHeaders headers = new HttpHeaders();
        HttpEntity<?> entity = new HttpEntity<>(headers);
        headers.add("Authorization", "");
        String tolatitude = Float.toString(latitude);
        String tolongitude = Float.toString(longitude);
        String url = "https://dapi.kakao.com/v2/local/geo/coord2address.json?x=" + tolongitude + "&y=" + tolatitude + "&input_coord=WGS84";
        // System.out.println(url);
        //  String url = "https://dapi.kakao.com/v2/local/geo/coord2address.json?x=127.423084873712&y=37.0789561558879&input_coord=WGS84";
        //UriComponents uri = UriComponentsBuilder.fromHttpUrl(url).build();
        ResponseEntity<Map> resultMap = rt.exchange(
                url,
                HttpMethod.GET,
                entity,
                Map.class
        );

//        ObjectMapper objectMapper = new ObjectMapper();
//        Addressfun addressfun = null;
//        try {
//            addressfun = objectMapper.readValue(resultMap.getBody().toString(), Addressfun.class);
//        } catch (JsonMappingException e) {
//            e.printStackTrace();
//        } catch (JsonProcessingException e) {
//            e.printStackTrace();
//        }
        //  System.out.println(addressfun.getDocuments().get(0).getRoad_address().getAddress_name());


//            result.put("statusCode", resultMap.getStatusCodeValue()); //http status code를 확인
//            result.put("header", resultMap.getHeaders()); //헤더 정보 확인
//            result.put("body", resultMap.getBody()); //실제 데이터 정보 확인
//
        //      LinkedHashMap lm = (LinkedHashMap) resultMap.getBody().get("documents");
//            ArrayList<Map> add = (ArrayList<Map>) lm.get("road_address");
//            LinkedHashMap addList = new LinkedHashMap<>();
//            for (Map road : add) {
//                addList.put(road.get("address_name"), road.get("building_name"));
//            }
//            ObjectMapper mapper = new ObjectMapper();
//            List<> al =  mapper.convertValue(list, new TypeReference<List<DocumentTypeVO>>() {})
//
//            jsonInString = mapper.writeValueAsString(addList);
//            System.out.println(jsonInString);
//        } catch (JsonProcessingException e) {
//            throw new RuntimeException (e);
//        }

        //return jsonInString;
        //ObjectMapper mapper = new ObjectMapper();
        //jsonInString = mapper.writeValueAsString(resultMap.getBody());
        //  System.out.println(result.put("body" , resultMap.getBody()));

        //  LinkedHashMap lm = (LinkedHashMap)resultMap.getBody().get("documents");
        ArrayList lm = (ArrayList) resultMap.getBody().get("documents");
        //Object sm = lm.get(0);
////        System.out.println(lm.get(0));
        //  Addressfun addressfun = new Addressfun(lm);
        GetdeliveryApi getdeliveryApi = new GetdeliveryApi(lm);

        //System.out.println(addressfun);
//            Object ob = new Object();
//            for (Object ar : lm) {
//
//            }
        //   addressfun.getRoad_address()


        return new BaseResponse<>(getdeliveryApi);


    }
//


    @PostMapping("/sms")
    public @ResponseBody
    BaseResponse<String> snsService(@RequestBody PostPhoneReq postPhoneReq) throws ParseException, JsonProcessingException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException, UnsupportedEncodingException, BaseException {
        try {
            String a = Integer.toString((int) (Math.random() * 10000));
            if (!isRegexPhone(postPhoneReq.getPhone())) {
                return new BaseResponse<>(POST_USERS_INVALID_PHONE);
            }
            String phone = postPhoneReq.getPhone();

            String aa = userService.sendSms(postPhoneReq.getPhone(), a);
            return new BaseResponse<>(aa);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));

        }

    }

    @PostMapping("/authentication")
    public @ResponseBody
    BaseResponse<String> snsCheck(@RequestBody PostCheckReq postCheckReq) throws ParseException, JsonProcessingException, UnsupportedEncodingException, InvalidKeyException, NoSuchAlgorithmException, URISyntaxException, UnsupportedEncodingException, BaseException {
        try {
            if (!isRegexPhone(postCheckReq.getPhone())) {
                return new BaseResponse<>(POST_USERS_INVALID_PHONE);
            }
            String check = postCheckReq.getCode();

            String aa = userService.Check(check, postCheckReq.getPhone());
            return new BaseResponse<>(aa);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));

        }

    }

    //배달 주소 생성
    @ResponseBody
    @PostMapping("/address/{userIdx}")
    public BaseResponse<PostAddressRes> postAdressRes(@PathVariable("userIdx") int userIdx,
                                                      @RequestBody PostAddressReq postAddressReq) throws BaseException {
        if (postAddressReq.getMainAddress() == null) {
            return new BaseResponse<>(INVALID_GETMAINADDRESS);
        }
        if (postAddressReq.getBuildingAddress() == null) {
            return new BaseResponse<>(INVALID_GETBUILDINGADDRESS);
        }
        if (postAddressReq.getIsWhere() == null) {
            return new BaseResponse<>(INVALID_GETISWHERE);
        }
        //  System.out.println(postAddressReq.getIsWhere());
        //(!((postAddressReq.getIsWhere() == 1) || (postAddressReq.getIsWhere() == 2) || (postAddressReq.getIsWhere() ==0)))
        if (!((postAddressReq.getIsWhere() == 1) || (postAddressReq.getIsWhere() == 2) || (postAddressReq.getIsWhere() == 0))) {
            return new BaseResponse<>(INVALID_GETISWHERECONDITION);
        }

        try {
            int userIdxJwt = jwtService.getuserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(EMPTY_JWT);
        }

        try {
            int userIdxJwt = jwtService.getuserIdx();

            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
                return new BaseResponse<>(INVALID_JWT);
            }
            if (postAddressReq.getMainAddress() == null) {
                throw new BaseException(INVALID_GETMAINADDRESS);
            }

            PostAddressRes postAdressRes = userService.postAdress(userIdx, postAddressReq);
            return new BaseResponse<>(postAdressRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));


        }


    }

    //배달주소 내역 조회
    @ResponseBody
    @GetMapping("/address/{userIdx}/history")
    public BaseResponse<List<GetAddressHistoryRes>> getAddressHistory(@PathVariable("userIdx") int userIdx) throws BaseException {
        try {
            int userIdxJwt = jwtService.getuserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        try {
            int userIdxJwt = jwtService.getuserIdx();

            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
                return new BaseResponse<>(INVALID_JWT);
            }
            List<GetAddressHistoryRes> getAddressHistoryRes = userProvider.getAddressHistory(userIdx);
            return new BaseResponse<>(getAddressHistoryRes);

        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));

        }
    }

    //배달주소 변경
    @ResponseBody
    @PatchMapping("/address/{userIdx}")
    public BaseResponse<PatchAddressRes> patchAddress(@PathVariable("userIdx") int userIdx,
                                                      @RequestBody PatchAddressReq patchAddressReq) throws BaseException {
        try {
            int userIdxJwt = jwtService.getuserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        try {
            int userIdxJwt = jwtService.getuserIdx();

            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
                return new BaseResponse<>(INVALID_JWT);
            }
            PatchAddressRes patchAddressRes = userService.patchAddress(userIdx, patchAddressReq);
            return new BaseResponse<>(patchAddressRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));


        }

    }

    //결제관리 조회 API
    @ResponseBody
    @GetMapping("myeats/payment/{userIdx}")
    public BaseResponse<GetCrditRes> getCrdit(@PathVariable("userIdx") int userIdx) throws BaseException {
        try {
            int userIdxJwt = jwtService.getuserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        try {
            int userIdxJwt = jwtService.getuserIdx();

            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
                return new BaseResponse<>(INVALID_JWT);
            }
            GetCrditRes getCrditRes = userProvider.getCrdit(userIdx);
            return new BaseResponse<>(getCrditRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));


        }

    }

    //결제관리 삭제 API
    @ResponseBody
    @DeleteMapping("/myeats/payment/{userIdx}/{paymentIdx}")
    public BaseResponse<DeleteCreditRes> deleteCredit(@PathVariable("userIdx") int userIdx,
                                                      @PathVariable("paymentIdx") int paymentIdx) throws BaseException {
        try {
            int userIdxJwt = jwtService.getuserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        try {
            int userIdxJwt = jwtService.getuserIdx();

            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
                return new BaseResponse<>(INVALID_JWT);
            }
            DeleteCreditRes deleteCreditRes = userService.deleteCredit(userIdx, paymentIdx);
            return new BaseResponse<>(deleteCreditRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));


        }


    }
    //현금 영수증 등록 API
    @ResponseBody
    @PostMapping("/myeats/payment/{userIdx}/cashReceipt")
    public BaseResponse<PostCashReceiptRes> postCashReceipt(@PathVariable("userIdx") int userIdx,
                                                            @RequestBody PostCashReceiptReq postCashReceiptReq) throws BaseException {
        if (!((postCashReceiptReq.getIsMethod() == 0) || (postCashReceiptReq.getIsMethod() == 1) || (postCashReceiptReq.getIsMethod() == 2) || (postCashReceiptReq.getIsMethod() == 3) ||(postCashReceiptReq.getIsMethod() == 4))) {
            throw new BaseException(INVALID_GETISMETHOD);
        }
        if (!isRegexPhone(postCashReceiptReq.getNumber())) {
            return new BaseResponse<>(POST_USERS_INVALID_PHONE);
        }
        try {
            int userIdxJwt = jwtService.getuserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        try {
            int userIdxJwt = jwtService.getuserIdx();

            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
                return new BaseResponse<>(INVALID_JWT);
            }
            PostCashReceiptRes postCashReceiptRes = userService.postCashReceipt(userIdx,postCashReceiptReq);
            return new BaseResponse<>(postCashReceiptRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));


        }


    }

    @ResponseBody
    @PatchMapping("/myeats/payment/{userIdx}/cashReceipt")
    public BaseResponse<PostCashReceiptRes> patchCashReceipt(@PathVariable("userIdx") int userIdx,
                                                            @RequestBody PostCashReceiptReq postCashReceiptReq) throws BaseException {
        if (!((postCashReceiptReq.getIsMethod() == 0) || (postCashReceiptReq.getIsMethod() == 1) || (postCashReceiptReq.getIsMethod() == 2) || (postCashReceiptReq.getIsMethod() == 3) ||(postCashReceiptReq.getIsMethod() == 4))) {
            throw new BaseException(INVALID_GETISMETHOD);
        }
        if (!isRegexPhone(postCashReceiptReq.getNumber())) {
            return new BaseResponse<>(POST_USERS_INVALID_PHONE);
        }
        try {
            int userIdxJwt = jwtService.getuserIdx();
        } catch (BaseException exception) {
            return new BaseResponse<>(EMPTY_JWT);
        }
        try {
            int userIdxJwt = jwtService.getuserIdx();

            if (userIdx != userIdxJwt) {   //pathvar userIdx랑 jwt에서 추출한 useridx랑 같지않다면 받아들이지않는다
                return new BaseResponse<>(INVALID_JWT);
            }
            PostCashReceiptRes postCashReceiptRes = userService.patchCashReceipt(userIdx,postCashReceiptReq);
            return new BaseResponse<>(postCashReceiptRes);
        } catch (BaseException exception) {
            return new BaseResponse<>((exception.getStatus()));


        }


    }
    @ResponseBody
    @GetMapping("myeats/deliveryPartner/{userIdx}")
    public BaseResponse<GetmyeatsPartnerRes> getmyeatsPartner(@PathVariable("userIdx") int userIdx) throws BaseException {
        GetmyeatsPartnerRes getmyeatsPartnerRes = userProvider.getmyeatsPartner();
        return new BaseResponse<>(getmyeatsPartnerRes);
    }
    //자주묻는 질문
    @ResponseBody
    @GetMapping("myeats/frequently/{userIdx}")
    public BaseResponse<List<GetFrequentlyRes>> getfrepuently(@PathVariable("userIdx") int userIdx) {
        List<GetFrequentlyRes> getFrequentlyRes = userProvider.getFrequently();
        return new BaseResponse<>(getFrequentlyRes);
     }
}


