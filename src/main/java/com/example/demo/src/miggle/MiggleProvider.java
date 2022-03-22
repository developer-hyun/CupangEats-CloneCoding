package com.example.demo.src.miggle;

import com.example.demo.config.BaseException;
import com.example.demo.config.BaseResponse;
import com.example.demo.config.secret.Secret;
import com.example.demo.src.miggle.model.PostMiggleReq;
import com.example.demo.src.user.UserDao;
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
public class MiggleProvider {

    private final MiggleDao miggleDao;
    private final JwtService jwtService;

    private JdbcTemplate jdbcTemplate;

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }

    @Autowired
    public MiggleProvider(MiggleDao miggleDao, JwtService jwtService) {
        this.miggleDao = miggleDao;
        this.jwtService = jwtService;
    }

    //miggle 회원
    public int createUser(PostMiggleReq postMiggleReq){
        int userIdx = miggleDao.createUser(postMiggleReq);
        return userIdx;
    }

}
