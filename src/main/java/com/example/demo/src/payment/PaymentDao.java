package com.example.demo.src.payment;


import com.example.demo.src.payment.model.GetPaymentRes;
import com.example.demo.src.store.model.GetStoreMenusRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class PaymentDao {

    private JdbcTemplate jdbcTemplate;
    private List<GetStoreMenusRes> getStoreMenusRes;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }



//인기 프랜차이즈
    public List<GetPaymentRes> getEvents(){
        String getEventsQuery = "select eventIdx,bannerImgUrl, concat(' ~ ',substring(DATE_FORMAT(endTime, '%Y-%m.%d %h:%m:%s'),6,6),'까지' ) as endTime From Event\n" +
                "where  Event.status='ACTIVE'\n" +
                "\n" +
                "order by Event.createdAt";

        return this.jdbcTemplate.query(getEventsQuery,
                (rs, rowNum) -> new GetPaymentRes(
                        rs.getInt("eventIdx"),
                        rs.getString("bannerImgUrl"),
                        rs.getString("endTime")

                )
                );
    }



}



