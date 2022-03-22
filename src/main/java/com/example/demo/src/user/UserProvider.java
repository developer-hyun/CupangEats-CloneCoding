package com.example.demo.src.user;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.user.model.*;
import com.example.demo.utils.AES128;
import com.example.demo.utils.JwtService;
import com.sun.el.parser.AstFalse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;

import javax.sql.DataSource;
import java.util.List;

import static com.example.demo.config.BaseResponseStatus.*;

//Provider : Read의 비즈니스 로직 처리
@Service
public class UserProvider {

    private final UserDao userDao;
    private final JwtService jwtService;

    private JdbcTemplate jdbcTemplate;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public UserProvider(UserDao userDao, JwtService jwtService) {
        this.userDao = userDao;
        this.jwtService = jwtService;
    }

    /**
     * 아이디 중복 체크 부분
     *
     * @param id
     * @return
     */
    public int checkId(String id) {
        return userDao.checkId(id);
    }
    /** 휴대폰 번호 중복 체크 부분
     *
     */
    public int checkPhoneNumber(String phoneNumber) {return userDao.checkPhoneNumber(phoneNumber);}

    /** auto logIn myeats API
     *
     */
    public GetMyEatsRes myeats(int userIdx){
        GetMyEatsRes getMyEatsRes = userDao.myeats(userIdx);
        return getMyEatsRes;
    }

    /** 즐겨찾기 API
     *
     * @param userIdx
     * @return
     */
    public GetLikeRes getlike(int userIdx,String likesort) throws BaseException {

        try {
            if (likesort.equals("latelyPlus")) {
                GetLikeRes getLikeRes = userDao.likeReslatelyPlus(userIdx);
                return getLikeRes;
            } else {
                GetLikeRes getLikeRes = userDao.likeReslatelyOrder(userIdx);  // 기본값은 주문 순서 주문순서가 아닌 글자는 controller에서 처리
                return getLikeRes;
            }

        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    //배달주소 내역조회
    public List<GetAddressHistoryRes> getAddressHistory(int userIdx) throws BaseException{
        try{

            List<GetAddressHistoryRes> getAddressHistoryRes = userDao.getAddressHistory(userIdx);
            return getAddressHistoryRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //결제관리 조회 apu
    public GetCrditRes getCrdit(int userIdx) throws BaseException {
        try{
            GetCrditRes  getCrditRes= userDao.getCrdit(userIdx);
        return getCrditRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }
    //배달 파트너
    public GetmyeatsPartnerRes getmyeatsPartner() throws BaseException {
        try{

            GetmyeatsPartnerRes getmyeatsPartnerRes = userDao.getmyeatsPartner();
            return getmyeatsPartnerRes;
        } catch (Exception exception) {
            throw new BaseException(DATABASE_ERROR);
        }
    }

    //자주묻는 질문
    public List<GetFrequentlyRes> getFrequently() {
        List<GetFrequentlyRes> getFrequentlyRes =  userDao.getFrequently();
        return getFrequentlyRes;
    }
    }


