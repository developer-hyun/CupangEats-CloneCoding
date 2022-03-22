package com.example.demo.src.miggle;


//import org.json.simple.parser.JSONParser;

import com.example.demo.src.miggle.model.GetDartRes;
import com.example.demo.src.miggle.model.PostMiggleReq;
import com.example.demo.src.miggle.model.PostMiggleRes;
import com.example.demo.src.user.UserProvider;
import com.example.demo.src.user.UserService;
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
import org.springframework.http.*;
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
import java.security.cert.CertPathValidatorException;
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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponents;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.HashMap;
import java.util.Map;


@RestController
@RequestMapping("/app/users")  // 이 url을 입력하면 이렇게 되도록 맵핑함
public class MiggleController {
    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired //엮겠다.  // 대문자는 해당 클래스를 불러오고 소문자는 클래스에서 객체를 만들어서 사용함
    private final MiggleProvider miggleProvider;
    //@Autowired
    //private final UserService userService;
    @Autowired
    private final JwtService jwtService;

    // autowired를 쓰려면 밑에 이렇게 한다음 클래스 전체에서 쓸수 있게 this로 해줘야한다??  // 생성자
    public MiggleController(MiggleProvider miggleProvider, JwtService jwtService) {
        this.miggleProvider = miggleProvider;   // userProvider를 클래스 전체에서 쓰겠당
        //this.userService = userService;
        this.jwtService = jwtService;
    }
    //miggle 회원가입 테스트
    @ResponseBody
    @PostMapping("/miggle")
    public BaseResponse<PostMiggleRes> createUser(@RequestBody PostMiggleReq postMiggleReq)throws Exception{
        int userIdx = miggleProvider.createUser(postMiggleReq);
        PostMiggleRes postMiggleRes = new PostMiggleRes(userIdx);
        return new BaseResponse<>(postMiggleRes);
    }

    @ResponseBody
    @GetMapping("/miggle/dart")
    public BaseResponse<GetDartRes> dartData(@RequestParam(required = true) String crtfc_key,
                                             @RequestParam(required = true) Integer corp_code,
                                             @RequestParam(required = true) int bsns_year,
                                             @RequestParam(required = true) int reprt_code) throws BaseException {
        //타임아웃 설정가능
        RestTemplate rt = new RestTemplate();

        //String tocorp_code = Integer.toString(corp_code);
        String totocorp_code = String.format("%08d",corp_code);
        String tobsns_year = Integer.toString(bsns_year);
        String toreprt_code = Integer.toString(reprt_code);

       // System.out.print(crtfc_key);
       // System.out.print(totocorp_code);
       // System.out.print(tobsns_year);
       // System.out.print(toreprt_code);
        String url = "https://opendart.fss.or.kr/api/fnlttSinglAcnt.json?crtfc_key="+crtfc_key+"&corp_code="+totocorp_code+"&bsns_year="+tobsns_year+"&reprt_code="+toreprt_code;
        ResponseEntity<Map> resultMap = rt.exchange(url,HttpMethod.GET,null,Map.class);

        ObjectMapper objectMapper = new ObjectMapper();
        //System.out.print(resultMap.getBody());

        ArrayList<Map> lm =(ArrayList<Map>)resultMap.getBody().get("list");

        //System.out.print(lm);
        String plusmoney = new String();

        LinkedHashMap mnList = new LinkedHashMap<>();
        for (Map obj : lm) {
            //System.out.println(obj.get("thstrm_amount"));
            if ((obj.get("fs_nm").equals("재무제표")) && (obj.get("account_nm").equals("이익잉여금"))) {
               plusmoney = String.valueOf(obj.get("thstrm_amount"));
               // mnList.put(obj.get("account_nm"),obj.get("thstrm_amount"));
               // System.out.print(plusmoney);
            }
            else{
                continue;
            }

        }
        GetDartRes getDartRes = new GetDartRes(plusmoney);
        return new BaseResponse<>(getDartRes);

//        int a = 1;
//        return a;

    }


    //야후 실시간 데이터

}
