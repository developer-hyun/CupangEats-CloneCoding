package com.example.demo.src.menu;


import com.example.demo.src.menu.model.GetMenuRes;
import com.example.demo.src.menu.model.GetSideCategoryRes;
import com.example.demo.src.menu.model.GetSubSideCategoryRes;
import com.example.demo.src.store.model.GetStoreDetailRes;
import com.example.demo.src.store.model.GetStoreRes;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class MenuDao {

    private JdbcTemplate jdbcTemplate;
    private List<GetSubSideCategoryRes> getSubSideCategoryRes;

    @Autowired
    public void setDataSource(DataSource dataSource){
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    public List<GetSideCategoryRes> getSideCategory( int menuIdx){



        String getVideoWatchingQuery = "select SideCategory.name, isNecessary,isCheckBox,minCheck,maxCheck ,SideCategory.sideCategoryIdx,SideCategory.sideCategoryIdx from SideCategory left join MenuSideCateg MSC on SideCategory.sideCategoryIdx = MSC.sideCategoryIdx where MSC.menuIdx=?";
        Object[] getVideoWatchingParams = new Object[]{menuIdx};

        return this.jdbcTemplate.query(getVideoWatchingQuery,
                (rs, rowNum) -> new GetSideCategoryRes(

                        rs.getString("name"),
                        rs.getString("isNecessary"),
                       rs.getString("isCheckBox"),
                       rs.getInt("minCheck"),
                       rs.getInt("maxCheck"),
                       rs.getInt("sideCategoryIdx"),

                       getSubSideCategoryRes=this.jdbcTemplate.query("select SSC.name, SSC.price,SSC.subSideCategoryIdx from SideCategory left join SubSideCategory SSC on SideCategory.sideCategoryIdx = SSC.sideCategoryIdx where SSC.sideCategoryIdx=?",
                               (rk,rownum) -> new GetSubSideCategoryRes(
                                       rk.getString("name"),
                                       rk.getString("price"),
                                       rk.getInt("subSideCategoryIdx")

                               ),rs.getInt("sideCategoryIdx"))
                        //menuIdx 를 동적으로 받을 수 없을까?


                ),getVideoWatchingParams
        );

    }
    //스토어의 정보-비로그인
    public GetMenuRes getMenu(int menuIdx){
        String getStoreQuery = "select imgUrl1, imgUrl2,name,information,price from Menu where menuIdx=?";
        Object[] getStoreParams = new Object[]{menuIdx};
        return this.jdbcTemplate.queryForObject(getStoreQuery,
                (rs, rowNum) -> new GetMenuRes(
                        rs.getString("imgUrl1"),
                        rs.getString("imgUrl2"),
                        rs.getString("name"),
                        rs.getString("information"),
                        rs.getInt("price")),
                getStoreParams);
    }


    public int checkMenuIdx(int  menuIdx){
        String checkCategoryQuery = "select exists(select menuIdx from Menu where menuIdx = ?)";
        int checkCategoryParams = menuIdx;
        return this.jdbcTemplate.queryForObject(checkCategoryQuery,
                int.class,
                checkCategoryParams);
    }
    public int checkMenuStatus(int  menuIdx){
        String checkCategoryQuery = "select case when status='ACTIVE' then 1 else 0 end from Menu where menuIdx=?";
        int checkCategoryParams =menuIdx;
        return this.jdbcTemplate.queryForObject(checkCategoryQuery,
                int.class,
                checkCategoryParams);
    }

    }




