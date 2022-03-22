package com.example.demo.src.miggle;

import com.example.demo.src.miggle.model.PostMiggleReq;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MiggleDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    //miggle 가입
    public int createUser(PostMiggleReq postMiggleReq){
        this.jdbcTemplate.update("insert into miggle (id,password) VALUES (?,?)",
                new Object[]{postMiggleReq.getId(),postMiggleReq.getPassword()});
        return this.jdbcTemplate.queryForObject("select last_insert_id()", int.class);
    }


}