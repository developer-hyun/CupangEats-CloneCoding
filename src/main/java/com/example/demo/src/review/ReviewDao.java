package com.example.demo.src.review;


import com.example.demo.src.review.model.*;
import com.example.demo.src.user.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;

@Repository
public class ReviewDao {

    private JdbcTemplate jdbcTemplate;

    @Autowired
    public void setDataSource(DataSource dataSource) {
        this.jdbcTemplate = new JdbcTemplate(dataSource);
    }


    //리뷰들 보여주기 (default page)
    public GetReviewRes getlatelyReview(int userIdx, int storeIdx) {
        String storeName = this.jdbcTemplate.queryForObject("select name as storeName from Store where storeIdx=?",
                String.class, storeIdx);

        List<GetReviewStatusRes> getReivewStatusRes = this.jdbcTemplate.query("select ReviewLike.reviewIdx,statusHelp from ReviewLike\n" +
                "inner join Review R on ReviewLike.reviewIdx = R.reviewIdx\n" +
                "inner join Orders O on R.orderIdx = O.orderIdx\n" +
                "    inner join User U on O.userIdx = U.userIdx\n" +
                "    inner join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                "    inner join OrderSM OS on OM.orderMenuIdx = OS.orderMenuIdx\n" +
                "    inner join SubSideCategory SSC on OS.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                "    inner join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                "    inner join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                "    inner join Menu M on MSC.menuIdx = M.menuIdx\n" +
                "    inner join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                "    inner join Store S on MC.storeIdx = S.storeIdx\n" +
                "\n" +
                "where S.storeIdx = ? AND ReviewLike.userIdx= ? AND R.status = 'ACTIVE' AND ReviewLike.status = 'ACTIVE' AND (ReviewLike.statusHelp='YES' OR ReviewLike.statusHelp='NO')\n" +
                "group by R.reviewIdx order by R.createdAt DESC",
                (rs,rownum) -> new GetReviewStatusRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("statusHelp"))
                 , storeIdx,userIdx);

        List<GetReviewsRes> getReviewsRes = this.jdbcTemplate.query("select distinct Review.reviewIdx , U.name as userName , star as reviewStar, imgUrl, reviewText as reviewText,\n" +
                        "                case when TIMESTAMPDIFF(HOUR , Review.createdAt, now()) <24\n" +
                        "            then concat('오늘')\n" +
                        "            else\n" +
                        "                concat(DATEDIFF(now(),Review.createdAt),'일 전')\n" +
                        "            end as reviewDate,\n" +
                        "            CONCAT(M.name ,'+', SSC.name) as reviewMenu,\n" +
                        "                isText as isText,\n" +
                        "            count(distinct RL.reviewLikeIdx) as reviewLikeCount\n" +
                        "from Review\n" +
                        "    left join ReviewLike RL on Review.reviewIdx = RL.reviewIdx\n" +
                        "    inner join Orders O on Review.orderIdx = O.orderIdx\n" +
                        "    inner join User U on O.userIdx = U.userIdx\n" +
                        "    inner join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                        "    inner join OrderSM OS on OM.orderMenuIdx = OS.orderMenuIdx\n" +
                        "    inner join SubSideCategory SSC on OS.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                        "    inner join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                        "    inner join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                        "    inner join Menu M on MSC.menuIdx = M.menuIdx\n" +
                        "    inner join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                        "    inner join Store S on MC.storeIdx = S.storeIdx\n" +
                        "\n" +
                        "where S.storeIdx = ?\n AND Review.status = 'ACTIVE'" +
                        "group by Review.reviewIdx order by Review.createdAt DESC",
                (rs, rownum) -> new GetReviewsRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("userName"),
                        rs.getFloat("reviewStar"),
                        rs.getString("imgUrl"),
                        rs.getString("reviewText"),
                        rs.getString("reviewDate"),
                        rs.getString("reviewMenu"),
                        rs.getString("isText"),
                        rs.getInt("reviewLikeCount")),
                storeIdx);
        GetReviewRes getReviewRes = new GetReviewRes(storeName,getReviewsRes,getReivewStatusRes);
        return getReviewRes;
    }

    //포토리뷰만 클릭
    public GetReviewRes getlatelyReviewPhoto(int userIdx, int storeIdx) {
        String storeName = this.jdbcTemplate.queryForObject("select name as storeName from Store where storeIdx=?",
                String.class, storeIdx);

        List<GetReviewStatusRes> getReivewStatusRes = this.jdbcTemplate.query("select ReviewLike.reviewIdx,statusHelp from ReviewLike\n" +
                        "inner join Review R on ReviewLike.reviewIdx = R.reviewIdx\n" +
                        "inner join Orders O on R.orderIdx = O.orderIdx\n" +
                        "    inner join User U on O.userIdx = U.userIdx\n" +
                        "    inner join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                        "    inner join OrderSM OS on OM.orderMenuIdx = OS.orderMenuIdx\n" +
                        "    inner join SubSideCategory SSC on OS.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                        "    inner join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                        "    inner join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                        "    inner join Menu M on MSC.menuIdx = M.menuIdx\n" +
                        "    inner join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                        "    inner join Store S on MC.storeIdx = S.storeIdx\n" +
                        "\n" +
                        "where S.storeIdx = ? AND ReviewLike.userIdx= ? AND R.imgUrl IS NOT NULL AND R.status = 'ACTIVE' AND ReviewLike.status = 'ACTIVE' AND (ReviewLike.statusHelp='YES' OR ReviewLike.statusHelp='NO')\n" +
                        "group by R.reviewIdx order by R.createdAt DESC",
                (rs,rownum) -> new GetReviewStatusRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("statusHelp"))
                , storeIdx,userIdx);

        List<GetReviewsRes> getReviewsRes = this.jdbcTemplate.query("select distinct Review.reviewIdx , U.name as userName , star as reviewStar, imgUrl, reviewText as reviewText,\n" +
                        "                case when TIMESTAMPDIFF(HOUR , Review.createdAt,now()) <24\n" +
                        "            then concat('오늘')\n" +
                        "            else\n" +
                        "                concat(DATEDIFF(now(),Review.createdAt),'일 전')\n" +
                        "            end as reviewDate,\n" +
                        "            CONCAT(M.name ,'+', SSC.name) as reviewMenu,\n" +
                        "                isText as isText,\n" +
                        "            count(distinct RL.reviewLikeIdx) as reviewLikeCount\n" +
                        "from Review\n" +
                        "    left join ReviewLike RL on Review.reviewIdx = RL.reviewIdx\n" +
                        "    inner join Orders O on Review.orderIdx = O.orderIdx\n" +
                        "    inner join User U on O.userIdx = U.userIdx\n" +
                        "    inner join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                        "    inner join OrderSM OS on OM.orderMenuIdx = OS.orderMenuIdx\n" +
                        "    inner join SubSideCategory SSC on OS.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                        "    inner join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                        "    inner join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                        "    inner join Menu M on MSC.menuIdx = M.menuIdx\n" +
                        "    inner join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                        "    inner join Store S on MC.storeIdx = S.storeIdx\n" +
                        "where S.storeIdx = ? AND imgUrl IS NOT NULL AND Review.status = 'ACTIVE'\n" +
                        "group by reviewIdx order by Review.createdAt DESC;",
                (rs, rownum) -> new GetReviewsRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("userName"),
                        rs.getFloat("reviewStar"),
                        rs.getString("imgUrl"),
                        rs.getString("reviewText"),
                        rs.getString("reviewDate"),
                        rs.getString("reviewMenu"),
                        rs.getString("isText"),
                        rs.getInt("reviewLikeCount")),
                storeIdx);
        GetReviewRes getReviewRes = new GetReviewRes(storeName,getReviewsRes,getReivewStatusRes);
        return getReviewRes;
    }

    //디폴트 리뷰 + 도움순
    public GetReviewRes getReviewHelp(int userIdx, int storeIdx) {
        String storeName = this.jdbcTemplate.queryForObject("select name as storeName from Store where storeIdx=?",
                String.class, storeIdx);

        List<GetReviewStatusRes> getReivewStatusRes = this.jdbcTemplate.query("select ReviewLike.reviewIdx,statusHelp from ReviewLike\n" +
                        "inner join Review R on ReviewLike.reviewIdx = R.reviewIdx\n" +
                        "inner join Orders O on R.orderIdx = O.orderIdx\n" +
                        "    inner join User U on O.userIdx = U.userIdx\n" +
                        "    inner join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                        "    inner join OrderSM OS on OM.orderMenuIdx = OS.orderMenuIdx\n" +
                        "    inner join SubSideCategory SSC on OS.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                        "    inner join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                        "    inner join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                        "    inner join Menu M on MSC.menuIdx = M.menuIdx\n" +
                        "    inner join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                        "    inner join Store S on MC.storeIdx = S.storeIdx\n" +
                        "\n" +
                        "where S.storeIdx = ? AND ReviewLike.userIdx= ? AND R.status = 'ACTIVE' AND ReviewLike.status = 'ACTIVE' AND (ReviewLike.statusHelp='YES' OR ReviewLike.statusHelp='NO')\n" +
                        "group by R.reviewIdx\n" +
                        "ORDER BY count(distinct ReviewLike.reviewLikeIdx) DESC",
                (rs, rownum) -> new GetReviewStatusRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("statusHelp"))
                , storeIdx, userIdx);

        List<GetReviewsRes> getReviewsRes = this.jdbcTemplate.query("select distinct Review.reviewIdx , U.name as userName , star as reviewStar, imgUrl, reviewText as reviewText,\n" +
                        "                case when TIMESTAMPDIFF(HOUR , Review.createdAt,now()) <24\n" +
                        "            then concat('오늘')\n" +
                        "            else\n" +
                        "                concat(DATEDIFF(now(),Review.createdAt),'일 전')\n" +
                        "            end as reviewDate,\n" +
                        "            CONCAT(M.name ,'+', SSC.name) as reviewMenu,\n" +
                        "                isText as isText,\n" +
                        "            count(distinct RL.reviewLikeIdx) as reviewLikeCount\n" +
                        "from Review\n" +
                        "    left join ReviewLike RL on Review.reviewIdx = RL.reviewIdx\n" +
                        "    inner join Orders O on Review.orderIdx = O.orderIdx\n" +
                        "    inner join User U on O.userIdx = U.userIdx\n" +
                        "    inner join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                        "    inner join OrderSM OS on OM.orderMenuIdx = OS.orderMenuIdx\n" +
                        "    inner join SubSideCategory SSC on OS.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                        "    inner join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                        "    inner join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                        "    inner join Menu M on MSC.menuIdx = M.menuIdx\n" +
                        "    inner join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                        "    inner join Store S on MC.storeIdx = S.storeIdx\n" +
                        "where S.storeIdx = ? AND Review.status = 'ACTIVE'\n" +
                        "group by reviewIdx\n" +
                        "ORDER BY count(distinct RL.reviewLikeIdx) DESC",
                (rs, rownum) -> new GetReviewsRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("userName"),
                        rs.getFloat("reviewStar"),
                        rs.getString("imgUrl"),
                        rs.getString("reviewText"),
                        rs.getString("reviewDate"),
                        rs.getString("reviewMenu"),
                        rs.getString("isText"),
                        rs.getInt("reviewLikeCount")),
                storeIdx);
        GetReviewRes getReviewRes = new GetReviewRes(storeName, getReviewsRes, getReivewStatusRes);
        return getReviewRes;
    }
    //포토 리뷰 + 도움순
    public GetReviewRes getReviewHelpPhoto(int userIdx, int storeIdx) {
        String storeName = this.jdbcTemplate.queryForObject("select name as storeName from Store where storeIdx=?",
                String.class, storeIdx);

        List<GetReviewStatusRes> getReivewStatusRes = this.jdbcTemplate.query("select ReviewLike.reviewIdx,statusHelp from ReviewLike\n" +
                        "inner join Review R on ReviewLike.reviewIdx = R.reviewIdx\n" +
                        "inner join Orders O on R.orderIdx = O.orderIdx\n" +
                        "    inner join User U on O.userIdx = U.userIdx\n" +
                        "    inner join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                        "    inner join OrderSM OS on OM.orderMenuIdx = OS.orderMenuIdx\n" +
                        "    inner join SubSideCategory SSC on OS.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                        "    inner join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                        "    inner join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                        "    inner join Menu M on MSC.menuIdx = M.menuIdx\n" +
                        "    inner join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                        "    inner join Store S on MC.storeIdx = S.storeIdx\n" +
                        "\n" +
                        "where S.storeIdx = ? AND ReviewLike.userIdx= ?  AND R.imgUrl IS NOT NULL AND R.status = 'ACTIVE' AND ReviewLike.status = 'ACTIVE' AND (ReviewLike.statusHelp='YES' OR ReviewLike.statusHelp='NO')\n" +
                        "group by R.reviewIdx\n" +
                        "ORDER BY count(distinct ReviewLike.reviewLikeIdx) DESC",
                (rs, rownum) -> new GetReviewStatusRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("statusHelp"))
                , storeIdx, userIdx);

        List<GetReviewsRes> getReviewsRes = this.jdbcTemplate.query("select distinct Review.reviewIdx , U.name as userName , star as reviewStar, imgUrl, reviewText as reviewText,\n" +
                        "                case when TIMESTAMPDIFF(HOUR , Review.createdAt,now()) <24\n" +
                        "            then concat('오늘')\n" +
                        "            else\n" +
                        "                concat(DATEDIFF(now(),Review.createdAt),'일 전')\n" +
                        "            end as reviewDate,\n" +
                        "            CONCAT(M.name ,'+', SSC.name) as reviewMenu,\n" +
                        "                isText as isText,\n" +
                        "            count(distinct RL.reviewLikeIdx) as reviewLikeCount\n" +
                        "from Review\n" +
                        "    left join ReviewLike RL on Review.reviewIdx = RL.reviewIdx\n" +
                        "    inner join Orders O on Review.orderIdx = O.orderIdx\n" +
                        "    inner join User U on O.userIdx = U.userIdx\n" +
                        "    inner join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                        "    inner join OrderSM OS on OM.orderMenuIdx = OS.orderMenuIdx\n" +
                        "    inner join SubSideCategory SSC on OS.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                        "    inner join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                        "    inner join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                        "    inner join Menu M on MSC.menuIdx = M.menuIdx\n" +
                        "    inner join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                        "    inner join Store S on MC.storeIdx = S.storeIdx\n" +
                        "where S.storeIdx = ? AND Review.imgUrl IS NOT NULL AND Review.status = 'ACTIVE'\n" +
                        "\n" +
                        "group by reviewIdx\n" +
                        "ORDER BY count(distinct RL.reviewLikeIdx) DESC",
                (rs, rownum) -> new GetReviewsRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("userName"),
                        rs.getFloat("reviewStar"),
                        rs.getString("imgUrl"),
                        rs.getString("reviewText"),
                        rs.getString("reviewDate"),
                        rs.getString("reviewMenu"),
                        rs.getString("isText"),
                        rs.getInt("reviewLikeCount")),
                storeIdx);
        GetReviewRes getReviewRes = new GetReviewRes(storeName, getReviewsRes, getReivewStatusRes);
        return getReviewRes;
    }

    //디폴트 리뷰 + 평점 높은 순
    public GetReviewRes getHighStar(int userIdx, int storeIdx) {
        String storeName = this.jdbcTemplate.queryForObject("select name as storeName from Store where storeIdx=?",
                String.class, storeIdx);

        List<GetReviewStatusRes> getReivewStatusRes = this.jdbcTemplate.query("select ReviewLike.reviewIdx,statusHelp from ReviewLike\n" +
                        "inner join Review R on ReviewLike.reviewIdx = R.reviewIdx\n" +
                        "inner join Orders O on R.orderIdx = O.orderIdx\n" +
                        "    inner join User U on O.userIdx = U.userIdx\n" +
                        "    inner join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                        "    inner join OrderSM OS on OM.orderMenuIdx = OS.orderMenuIdx\n" +
                        "    inner join SubSideCategory SSC on OS.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                        "    inner join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                        "    inner join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                        "    inner join Menu M on MSC.menuIdx = M.menuIdx\n" +
                        "    inner join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                        "    inner join Store S on MC.storeIdx = S.storeIdx\n" +
                        "\n" +
                        "where S.storeIdx = ? AND ReviewLike.userIdx= ? AND R.status = 'ACTIVE' AND ReviewLike.status = 'ACTIVE' AND (ReviewLike.statusHelp='YES' OR ReviewLike.statusHelp='NO')\n" +
                        "group by R.reviewIdx\n" +
                        "ORDER BY R.star DESC",
                (rs, rownum) -> new GetReviewStatusRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("statusHelp"))
                , storeIdx, userIdx);

        List<GetReviewsRes> getReviewsRes = this.jdbcTemplate.query("select distinct Review.reviewIdx , U.name as userName , star as reviewStar, imgUrl, reviewText as reviewText,\n" +
                        "                case when TIMESTAMPDIFF(HOUR , Review.createdAt,now()) <24\n" +
                        "            then concat('오늘')\n" +
                        "            else\n" +
                        "                concat(DATEDIFF(now(),Review.createdAt),'일 전')\n" +
                        "            end as reviewDate,\n" +
                        "            CONCAT(M.name ,'+', SSC.name) as reviewMenu,\n" +
                        "                isText as isText,\n" +
                        "            count(distinct RL.reviewLikeIdx) as reviewLikeCount\n" +
                        "from Review\n" +
                        "    left join ReviewLike RL on Review.reviewIdx = RL.reviewIdx\n" +
                        "    inner join Orders O on Review.orderIdx = O.orderIdx\n" +
                        "    inner join User U on O.userIdx = U.userIdx\n" +
                        "    inner join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                        "    inner join OrderSM OS on OM.orderMenuIdx = OS.orderMenuIdx\n" +
                        "    inner join SubSideCategory SSC on OS.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                        "    inner join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                        "    inner join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                        "    inner join Menu M on MSC.menuIdx = M.menuIdx\n" +
                        "    inner join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                        "    inner join Store S on MC.storeIdx = S.storeIdx\n" +
                        "where S.storeIdx = ? AND Review.status = 'ACTIVE'\n" +
                        "group by reviewIdx\n" +
                        "ORDER BY Review.star DESC",
                (rs, rownum) -> new GetReviewsRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("userName"),
                        rs.getFloat("reviewStar"),
                        rs.getString("imgUrl"),
                        rs.getString("reviewText"),
                        rs.getString("reviewDate"),
                        rs.getString("reviewMenu"),
                        rs.getString("isText"),
                        rs.getInt("reviewLikeCount")),
                storeIdx);
        GetReviewRes getReviewRes = new GetReviewRes(storeName, getReviewsRes, getReivewStatusRes);
        return getReviewRes;
    }
    //포토 리뷰+ 평점높은 순
    public GetReviewRes getHighStarPhoto(int userIdx, int storeIdx) {
        String storeName = this.jdbcTemplate.queryForObject("select name as storeName from Store where storeIdx=?",
                String.class, storeIdx);

        List<GetReviewStatusRes> getReivewStatusRes = this.jdbcTemplate.query("select ReviewLike.reviewIdx,statusHelp from ReviewLike\n" +
                        "inner join Review R on ReviewLike.reviewIdx = R.reviewIdx\n" +
                        "inner join Orders O on R.orderIdx = O.orderIdx\n" +
                        "    inner join User U on O.userIdx = U.userIdx\n" +
                        "    inner join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                        "    inner join OrderSM OS on OM.orderMenuIdx = OS.orderMenuIdx\n" +
                        "    inner join SubSideCategory SSC on OS.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                        "    inner join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                        "    inner join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                        "    inner join Menu M on MSC.menuIdx = M.menuIdx\n" +
                        "    inner join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                        "    inner join Store S on MC.storeIdx = S.storeIdx\n" +
                        "\n" +
                        "where S.storeIdx = ? AND ReviewLike.userIdx= ? AND  R.imgUrl IS NOT NULL AND R.status = 'ACTIVE' AND ReviewLike.status = 'ACTIVE' AND (ReviewLike.statusHelp='YES' OR ReviewLike.statusHelp='NO')\n" +
                        "group by R.reviewIdx\n" +
                        "ORDER BY R.star DESC",
                (rs, rownum) -> new GetReviewStatusRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("statusHelp"))
                , storeIdx, userIdx);

        List<GetReviewsRes> getReviewsRes = this.jdbcTemplate.query("select distinct Review.reviewIdx , U.name as userName , star as reviewStar, imgUrl, reviewText as reviewText,\n" +
                        "                case when TIMESTAMPDIFF(HOUR , Review.createdAt,now()) <24\n" +
                        "            then concat('오늘')\n" +
                        "            else\n" +
                        "                concat(DATEDIFF(now(),Review.createdAt),'일 전')\n" +
                        "            end as reviewDate,\n" +
                        "            CONCAT(M.name ,'+', SSC.name) as reviewMenu,\n" +
                        "                isText as isText,\n" +
                        "            count(distinct RL.reviewLikeIdx) as reviewLikeCount\n" +
                        "from Review\n" +
                        "    left join ReviewLike RL on Review.reviewIdx = RL.reviewIdx\n" +
                        "    inner join Orders O on Review.orderIdx = O.orderIdx\n" +
                        "    inner join User U on O.userIdx = U.userIdx\n" +
                        "    inner join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                        "    inner join OrderSM OS on OM.orderMenuIdx = OS.orderMenuIdx\n" +
                        "    inner join SubSideCategory SSC on OS.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                        "    inner join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                        "    inner join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                        "    inner join Menu M on MSC.menuIdx = M.menuIdx\n" +
                        "    inner join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                        "    inner join Store S on MC.storeIdx = S.storeIdx\n" +
                        "where S.storeIdx = ? AND Review.imgUrl IS NOT NULL AND Review.status = 'ACTIVE'\n" +
                        "group by reviewIdx\n" +
                        "ORDER BY Review.star DESC",
                (rs, rownum) -> new GetReviewsRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("userName"),
                        rs.getFloat("reviewStar"),
                        rs.getString("imgUrl"),
                        rs.getString("reviewText"),
                        rs.getString("reviewDate"),
                        rs.getString("reviewMenu"),
                        rs.getString("isText"),
                        rs.getInt("reviewLikeCount")),
                storeIdx);
        GetReviewRes getReviewRes = new GetReviewRes(storeName, getReviewsRes, getReivewStatusRes);
        return getReviewRes;
    }

    //디폴트 리뷰 + 평점낮은 순
    public GetReviewRes getLowStar(int userIdx, int storeIdx) {
        String storeName = this.jdbcTemplate.queryForObject("select name as storeName from Store where storeIdx=?",
                String.class, storeIdx);

        List<GetReviewStatusRes> getReivewStatusRes = this.jdbcTemplate.query("select ReviewLike.reviewIdx,statusHelp from ReviewLike\n" +
                        "inner join Review R on ReviewLike.reviewIdx = R.reviewIdx\n" +
                        "inner join Orders O on R.orderIdx = O.orderIdx\n" +
                        "    inner join User U on O.userIdx = U.userIdx\n" +
                        "    inner join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                        "    inner join OrderSM OS on OM.orderMenuIdx = OS.orderMenuIdx\n" +
                        "    inner join SubSideCategory SSC on OS.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                        "    inner join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                        "    inner join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                        "    inner join Menu M on MSC.menuIdx = M.menuIdx\n" +
                        "    inner join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                        "    inner join Store S on MC.storeIdx = S.storeIdx\n" +
                        "\n" +
                        "where S.storeIdx = ? AND ReviewLike.userIdx= ? AND R.status = 'ACTIVE' AND ReviewLike.status = 'ACTIVE' AND (ReviewLike.statusHelp='YES' OR ReviewLike.statusHelp='NO')\n" +
                        "group by R.reviewIdx\n" +
                        "ORDER BY R.star",
                (rs, rownum) -> new GetReviewStatusRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("statusHelp"))
                , storeIdx, userIdx);

        List<GetReviewsRes> getReviewsRes = this.jdbcTemplate.query("select distinct Review.reviewIdx , U.name as userName , star as reviewStar, imgUrl, reviewText as reviewText,\n" +
                        "                case when TIMESTAMPDIFF(HOUR , Review.createdAt,now()) <24\n" +
                        "            then concat('오늘')\n" +
                        "            else\n" +
                        "                concat(DATEDIFF(now(),Review.createdAt),'일 전')\n" +
                        "            end as reviewDate,\n" +
                        "            CONCAT(M.name ,'+', SSC.name) as reviewMenu,\n" +
                        "                isText as isText,\n" +
                        "            count(distinct RL.reviewLikeIdx) as reviewLikeCount\n" +
                        "from Review\n" +
                        "    left join ReviewLike RL on Review.reviewIdx = RL.reviewIdx\n" +
                        "    inner join Orders O on Review.orderIdx = O.orderIdx\n" +
                        "    inner join User U on O.userIdx = U.userIdx\n" +
                        "    inner join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                        "    inner join OrderSM OS on OM.orderMenuIdx = OS.orderMenuIdx\n" +
                        "    inner join SubSideCategory SSC on OS.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                        "    inner join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                        "    inner join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                        "    inner join Menu M on MSC.menuIdx = M.menuIdx\n" +
                        "    inner join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                        "    inner join Store S on MC.storeIdx = S.storeIdx\n" +
                        "where S.storeIdx = ? AND Review.status = 'ACTIVE'\n" +
                        "group by reviewIdx\n" +
                        "ORDER BY Review.star",
                (rs, rownum) -> new GetReviewsRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("userName"),
                        rs.getFloat("reviewStar"),
                        rs.getString("imgUrl"),
                        rs.getString("reviewText"),
                        rs.getString("reviewDate"),
                        rs.getString("reviewMenu"),
                        rs.getString("isText"),
                        rs.getInt("reviewLikeCount")),
                storeIdx);
        GetReviewRes getReviewRes = new GetReviewRes(storeName, getReviewsRes, getReivewStatusRes);
        return getReviewRes;
    }
    //포토 리뷰 + 평점 낮은 순
    public GetReviewRes getLowStarPhoto(int userIdx, int storeIdx) {
        String storeName = this.jdbcTemplate.queryForObject("select name as storeName from Store where storeIdx=?",
                String.class, storeIdx);

        List<GetReviewStatusRes> getReivewStatusRes = this.jdbcTemplate.query("select ReviewLike.reviewIdx, statusHelp from ReviewLike\n" +
                        "inner join Review R on ReviewLike.reviewIdx = R.reviewIdx\n" +
                        "inner join Orders O on R.orderIdx = O.orderIdx\n" +
                        "    inner join User U on O.userIdx = U.userIdx\n" +
                        "    inner join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                        "    inner join OrderSM OS on OM.orderMenuIdx = OS.orderMenuIdx\n" +
                        "    inner join SubSideCategory SSC on OS.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                        "    inner join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                        "    inner join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                        "    inner join Menu M on MSC.menuIdx = M.menuIdx\n" +
                        "    inner join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                        "    inner join Store S on MC.storeIdx = S.storeIdx\n" +
                        "\n" +
                        "where S.storeIdx = ? AND ReviewLike.userIdx= ? AND R.imgUrl IS NOT NULL AND R.status = 'ACTIVE' AND ReviewLike.status = 'ACTIVE' AND (ReviewLike.statusHelp='YES' OR ReviewLike.statusHelp='NO')\n" +
                        "group by R.reviewIdx\n" +
                        "ORDER BY R.star",
                (rs, rownum) -> new GetReviewStatusRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("statusHelp"))
                , storeIdx, userIdx);

        List<GetReviewsRes> getReviewsRes = this.jdbcTemplate.query("select distinct Review.reviewIdx , U.name as userName , star as reviewStar, imgUrl, reviewText as reviewText,\n" +
                        "                case when TIMESTAMPDIFF(HOUR , Review.createdAt,now()) <24\n" +
                        "            then concat('오늘')\n" +
                        "            else\n" +
                        "                concat(DATEDIFF(now(),Review.createdAt),'일 전')\n" +
                        "            end as reviewDate,\n" +
                        "            CONCAT(M.name ,'+', SSC.name) as reviewMenu,\n" +
                        "                isText as isText,\n" +
                        "            count(distinct RL.reviewLikeIdx) as reviewLikeCount\n" +
                        "from Review\n" +
                        "    left join ReviewLike RL on Review.reviewIdx = RL.reviewIdx\n" +
                        "    inner join Orders O on Review.orderIdx = O.orderIdx\n" +
                        "    inner join User U on O.userIdx = U.userIdx\n" +
                        "    inner join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                        "    inner join OrderSM OS on OM.orderMenuIdx = OS.orderMenuIdx\n" +
                        "    inner join SubSideCategory SSC on OS.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                        "    inner join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                        "    inner join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                        "    inner join Menu M on MSC.menuIdx = M.menuIdx\n" +
                        "    inner join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                        "    inner join Store S on MC.storeIdx = S.storeIdx\n" +
                        "where S.storeIdx = ? AND Review.imgUrl IS NOT NULL AND Review.status = 'ACTIVE'\n" +
                        "group by reviewIdx\n" +
                        "ORDER BY Review.star",
                (rs, rownum) -> new GetReviewsRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("userName"),
                        rs.getFloat("reviewStar"),
                        rs.getString("imgUrl"),
                        rs.getString("reviewText"),
                        rs.getString("reviewDate"),
                        rs.getString("reviewMenu"),
                        rs.getString("isText"),
                        rs.getInt("reviewLikeCount")),
                storeIdx);
        GetReviewRes getReviewRes = new GetReviewRes(storeName, getReviewsRes, getReivewStatusRes);
        return getReviewRes;
    }



    //스토어 체크
    public int checkStoreIdx(int  storeIdx){
        String checkCategoryQuery = "select exists(select storeIdx from Store where storeIdx = ?)";
        int checkCategoryParams = storeIdx;
        return this.jdbcTemplate.queryForObject(checkCategoryQuery,
                int.class,
                checkCategoryParams);
    }

    //리뷰 생성 화면
    public GetReviewViewRes getReviewView(int orderIdx){
        return this.jdbcTemplate.queryForObject("select Store.name as storeName,M.name as menuName, M.menuIdx as menuIdx from Store\n" +
                "inner join MenuCategory MC on Store.storeIdx = MC.storeIdx\n" +
                "inner join Menu M on MC.menuCategoryIdx = M.menuCategoryIdx\n" +
                "inner join MenuSideCateg MSC on M.menuIdx = MSC.menuIdx\n" +
                "inner join SideCategory SC on MSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                "inner join SubSideCategory SSC on SC.sideCategoryIdx = SSC.sideCategoryIdx\n" +
                "inner join OrderSM OS on SSC.subSideCategoryIdx = OS.subSideCategoryIdx\n" +
                "inner join OrderMenu OM on M.menuIdx = OM.menuIdx\n" +
                "inner join Orders O on OM.orderIdx = O.orderIdx\n" +
                "inner join User U on O.userIdx = U.userIdx\n" +
                "where O.orderIdx = ?\n" +
                "group by Store.storeIdx",
                (rs,rownum)-> new GetReviewViewRes(
                        rs.getString("storeName"),
                        rs.getString("menuName"),
                        rs.getInt("menuIdx")
                ),orderIdx);
    }
    //oridx존재 확인
    public int checkOrderIdx(int orderIdx){
        return this.jdbcTemplate.queryForObject("select Exists(select orderIdx from Orders where orderIdx=?)",
                int.class,orderIdx);
    }

    //리뷰 생성
    public int postReview(int orderIdx , int menuIdx, PostReviewReq postReviewReq) {
        this.jdbcTemplate.update("Insert into Review (orderIdx,star,imgUrl,reviewText,isPhoto,isText,deliveryLike,deliveryOpinion) VALUES (?,?,?,?,?,?,?,?)",
                postReviewReq.getOrderIdx(),postReviewReq.getStar(),postReviewReq.getImgUrl(),postReviewReq.getReviewText(),postReviewReq.getIsPhoto(),postReviewReq.getIsText(),
                postReviewReq.getDeliveryLike(),postReviewReq.getDeliveryOpinion());
        this.jdbcTemplate.update("update OrderMenu SET liked = ? , menuOpinion = ? where orderIdx = ? AND menuIdx =?",
                postReviewReq.getLiked(),postReviewReq.getMenuOpinion(),orderIdx,menuIdx);
        return this.jdbcTemplate.queryForObject("select reviewIdx from Review where orderIdx=?",
                int.class,orderIdx);
    }

    //리뷰 수정 화면
    public GetReviewModifyRes getReviewModify(int orderIdx) {
        return this.jdbcTemplate.queryForObject("select S.name as storeName,Review.star, Review.reviewText, Review.imgUrl, case when TIMESTAMPDIFF(HOUR , Review.createdAt, now()) < 24\n" +
                "            then concat('오늘')\n" +
                "            else\n" +
                "                concat(DATEDIFF(now(),Review.createdAt),'일 전')\n" +
                "            end as reviewDate ,\n" +
                "       M.name as reviewMenu,  count(distinct RL.reviewLikeIdx) as reviewLikeCount ,\n" +
                "        case when 14 -   TIMESTAMPDIFF(DAY , O.createdAt, now()) < 14 then CONCAT('리뷰 수정기간이 ',14 -   TIMESTAMPDIFF(DAY , O.createdAt, now()),'일 남았습니다')\n" +
                "        else '리뷰 쓰기 기한이 지났습니다' end as reviewPatchDate\n" +
                "\n" +
                "from Review\n" +
                "    left join ReviewLike RL on Review.reviewIdx = RL.reviewIdx\n" +
                "inner join Orders O on Review.orderIdx = O.orderIdx\n" +
                "    inner join User U on O.userIdx = U.userIdx\n" +
                "    inner join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                "    inner join OrderSM OS on OM.orderMenuIdx = OS.orderMenuIdx\n" +
                "    inner join SubSideCategory SSC on OS.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                "    inner join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                "    inner join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                "    inner join Menu M on MSC.menuIdx = M.menuIdx\n" +
                "    inner join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                "    inner join Store S on MC.storeIdx = S.storeIdx\n" +
                "where Review.orderIdx = ? AND Review.status = 'ACTIVE'\n" +
                "group by Review.reviewIdx",
                (rs,rownum) -> new GetReviewModifyRes(
                        rs.getString("storeName"),
                        rs.getFloat("star"),
                        rs.getString("reviewText"),
                        rs.getString("imgUrl"),
                        rs.getString("reviewDate"),
                        rs.getString("reviewMenu"),
                        rs.getInt("reviewLikeCount"),
                        rs.getString("reviewPatchDate")),
                        orderIdx
                );
    }
    //리뷰 기간 확인 부분
    public int checkReviewDeadLine(int orderIdx){
        return this.jdbcTemplate.queryForObject("select SIGN(14 -   TIMESTAMPDIFF(DAY , O.createdAt, now()))\n" +
                "from Orders as O where orderIdx =?",
                int.class,orderIdx);
    }

    //리뷰 수정 클릭 했을 때 화면
    public GetReviewModifyViewRes getReviewModifyView(int orderIdx){
        return this.jdbcTemplate.queryForObject("select reviewIdx,S.name as storeName, Review.star , Review.imgUrl,Review.reviewText , isPhoto,isText,deliveryLike,deliveryOpinion,\n" +
                "       OM.liked , OM.menuOpinion , M.name as menuName , M.menuIdx\n" +
                "from Review\n" +
                "inner join Orders O on Review.orderIdx = O.orderIdx\n" +
                "    inner join User U on O.userIdx = U.userIdx\n" +
                "    inner join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                "    inner join OrderSM OS on OM.orderMenuIdx = OS.orderMenuIdx\n" +
                "    inner join SubSideCategory SSC on OS.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                "    inner join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                "    inner join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                "    inner join Menu M on MSC.menuIdx = M.menuIdx\n" +
                "    inner join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                "    inner join Store S on MC.storeIdx = S.storeIdx\n" +
                "where Review.status = 'ACTIVE' AND O.orderIdx =?\n" +
                "group by Review.reviewIdx",
                (rs,rownum) -> new GetReviewModifyViewRes(
                        rs.getInt("reviewIdx"),
                        rs.getString("storeName"),
                        rs.getFloat("star"),
                        rs.getString("imgUrl"),
                        rs.getString("reviewText"),
                        rs.getString("isPhoto"),
                        rs.getString("isText"),
                        rs.getInt("deliveryLike"),
                        rs.getString("deliveryOpinion"),
                        rs.getString("menuName"),
                        rs.getInt("menuIdx"),
                        rs.getInt("liked"),
                        rs.getString("menuOpinion")),
                orderIdx);
    }
    //리뷰 인덱스 존재 확인
    public int checkReviewIdx(int orderIdx){
        return this.jdbcTemplate.queryForObject("select EXISTS(select reviewIdx from Review inner join Orders O on Review.orderIdx = O.orderIdx where O.orderIdx=?)",
                int.class,orderIdx);
    }

    //리뷰 수정 부분
    public int patchReview(int orderIdx , int menuIdx, PostReviewReq postReviewReq) {
        this.jdbcTemplate.update("update Review SET orderIdx=?,star=?,imgUrl=?,reviewText=?,isPhoto=?,isText=?,deliveryLike=?,deliveryOpinion=? where orderIdx=? AND status = 'ACTIVE'",
                postReviewReq.getOrderIdx(),postReviewReq.getStar(),postReviewReq.getImgUrl(),postReviewReq.getReviewText(),postReviewReq.getIsPhoto(),postReviewReq.getIsText(),
                postReviewReq.getDeliveryLike(),postReviewReq.getDeliveryOpinion(),orderIdx);
        this.jdbcTemplate.update("update OrderMenu SET liked = ? , menuOpinion = ? where orderIdx = ? AND menuIdx =?",
                postReviewReq.getLiked(),postReviewReq.getMenuOpinion(),orderIdx,menuIdx);
        return this.jdbcTemplate.queryForObject("select reviewIdx from Review where orderIdx=?",
                int.class,orderIdx);
    }

    //리뷰 삭제 부분
    public int deleteReview(int orderIdx){
        this.jdbcTemplate.update("update Review inner join Orders O on Review.orderIdx = O.orderIdx SET Review.status = 'INACTIVE' where Review.orderIdx=?",
                orderIdx);
        return this.jdbcTemplate.queryForObject("select reviewIdx from Review inner join Orders O on Review.orderIdx = O.orderIdx where Review.orderIdx = ?",
                int.class,orderIdx);
    }


    //리뷰 도움이 돼요 안돼요 아이콘 클릭 부ㅡ분
    //테이블 존재 확인
    public int checkReviewHelp(int userIdx,int reviewIdx){
        return this.jdbcTemplate.queryForObject("select EXISTS(select reviewIdx from ReviewLike where userIdx=? AND reviewIdx=?)",
                int.class,userIdx,reviewIdx);
    }
    //테이블이 없다면 그중 돼요를 눌렀다면
    public void fristCreateReviewHelpPushYes(int userIdx,int reviewIdx){
        this.jdbcTemplate.update("INSERT into ReviewLike (reviewIdx, userIdx,statusHelp) VALUES (?,?,'YES')",
                reviewIdx,userIdx);
    }
    //테이블이 없다면 그중 안돼요를 눌렀다면
    public void fristCreateReviewHelpPushNo(int userIdx,int reviewIdx){
        this.jdbcTemplate.update("INSERT into ReviewLike (reviewIdx, userIdx,statusHelp) VALUES (?,?,'NO')",
                reviewIdx,userIdx);
    }
    //테이블이 이미 생성되어 있고 돼요가 눌러져있으면 다시 클릭하였을 때 ZERO
    public void alreadyCreateReviewHelpYesPushZero(int userIdx,int reviewIdx){
        this.jdbcTemplate.update("update ReviewLike SET ReviewLike.statusHelp = 'ZERO' where userIdx=? AND reviewIdx=? AND ReviewLike.statusHelp='YES'",
                userIdx,reviewIdx);
    }

    //테이블이 이미 생성되어 있고 안돼요가 눌러져있으면 다시 클릭했을 때 zero
    public void alreadyCreateReviewHelpNoPushZero(int userIdx,int reviewIdx){
        this.jdbcTemplate.update("update ReviewLike SET ReviewLike.statusHelp = 'ZERO' where userIdx=? AND reviewIdx=? AND ReviewLike.statusHelp='NO'",
                userIdx,reviewIdx);
    }
    // 테이블이 이미 존재하고 아무것도 안눌러져있는데 좋아요를 누르면 YEs
    public void alreadyCreateReviewHelpZeroPushYes(int userIdx,int reviewIdx){
        this.jdbcTemplate.update("update ReviewLike SET ReviewLike.statusHelp = 'YES' where userIdx=? AND reviewIdx=? AND ReviewLike.statusHelp='ZERO'",
                userIdx,reviewIdx);
    }
    // 테이블이 이미 존재하고 아무것도 안눌러져있는데 안돼요를 누르면 YEs
    public void alreadyCreateReviewHelpZeroPushNo(int userIdx,int reviewIdx){
        this.jdbcTemplate.update("update ReviewLike SET ReviewLike.statusHelp = 'NO' where userIdx=? AND reviewIdx=? AND ReviewLike.statusHelp='ZERO'",
                userIdx,reviewIdx);
    }
    //돼요 상태에서 안돼요 상태로 누를때
    public void alreadyCreateReviewHelpYesPushNo(int userIdx,int reviewIdx){
        this.jdbcTemplate.update("update ReviewLike SET ReviewLike.statusHelp = 'NO' where userIdx=? AND reviewIdx=? AND ReviewLike.statusHelp='YES'",
                userIdx,reviewIdx);
    }
    //안돼요 상태에서 돼요 상태로 누를때
    public void alreadyCreateReviewHelpNoPushYes(int userIdx,int reviewIdx){
        this.jdbcTemplate.update("update ReviewLike SET ReviewLike.statusHelp = 'YES' where userIdx=? AND reviewIdx=? AND ReviewLike.statusHelp='NO'",
                userIdx,reviewIdx);
    }
    //응답값으로 현재 몇명에게 도움이 되었는지 내 상태가 어떠한지
    public GetReviewStatusHelp getReviewStatusHelp(int userIdx,int reviewIdx){
        return this.jdbcTemplate.queryForObject("select ReviewLike.reviewIdx,statusHelp,\n" +
                "       (select count(distinct ReviewLike.reviewLikeIdx) from ReviewLike inner join Review R2 on ReviewLike.reviewIdx = R2.reviewIdx where R2.reviewIdx =? AND ReviewLike.statusHelp='YES') as reviewLikeCount\n" +
                "       from ReviewLike\n" +
                "inner join Review R on ReviewLike.reviewIdx = R.reviewIdx\n" +
                "inner join Orders O on R.orderIdx = O.orderIdx\n" +
                "    inner join User U on O.userIdx = U.userIdx\n" +
                "    inner join OrderMenu OM on O.orderIdx = OM.orderIdx\n" +
                "    inner join OrderSM OS on OM.orderMenuIdx = OS.orderMenuIdx\n" +
                "    inner join SubSideCategory SSC on OS.subSideCategoryIdx = SSC.subSideCategoryIdx\n" +
                "    inner join SideCategory SC on SSC.sideCategoryIdx = SC.sideCategoryIdx\n" +
                "    inner join MenuSideCateg MSC on SC.sideCategoryIdx = MSC.sideCategoryIdx\n" +
                "    inner join Menu M on MSC.menuIdx = M.menuIdx\n" +
                "    inner join MenuCategory MC on M.menuCategoryIdx = MC.menuCategoryIdx\n" +
                "    inner join Store S on MC.storeIdx = S.storeIdx\n" +
                "\n" +
                "where ReviewLike.userIdx= ? AND ReviewLike.reviewIdx=?\n" +
                "group by R.reviewIdx",
                (rs,rownum) -> new GetReviewStatusHelp(
                        rs.getInt("reviewIdx"),
                        rs.getString("statusHelp"),
                        rs.getInt("reviewLikeCount")),
                reviewIdx,userIdx,reviewIdx
                );
    }

    //param 으로 받은 돼요/안됑 ㅛ상태와 DB에 저장된 상태와의 비교를 위하여
    public String compareStatusHelp(int userIdx,int reviewIdx) {
        return this.jdbcTemplate.queryForObject("select ReviewLike.statusHelp from ReviewLike where ReviewLike.userIdx=? AND ReviewLike.reviewIdx=?",
                String.class,userIdx,reviewIdx);
    }

    //reviewIdx존재 여부 확인 reviewLike측면에서
    public int checkReviewIdxSideReviewLike(int userIdx,int reviewIdx){
        return this.jdbcTemplate.queryForObject("select exists(select ReviewLike.reviewIdx from ReviewLike where userIdx=? AND reviewIdx=?)",
                int.class,userIdx,reviewIdx);
    }

 

}



