package com.jiaoyiyu.back.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * AllWorks class
 *
 * @author maochaoying
 * @date 2020/4/5
 */

public class AllWorks implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer uId;

    private String title;

    private Integer cataId;

    private Integer classOfIndustryId;

    private String detail;

    private String coverUrl;

    private Integer sellState;

    private BigDecimal price;

    private String creatDate;


    private Integer comments;

    private Integer likeNum;

    private Integer pageView;

    public Integer getComments() {
        return comments;
    }

    public void setComments(Integer comments) {
        this.comments = comments;
    }

    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    public Integer getPageView() {
        return pageView;
    }

    public void setPageView(Integer pageView) {
        this.pageView = pageView;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getCataId() {
        return cataId;
    }

    public void setCataId(Integer cataId) {
        this.cataId = cataId;
    }

    public Integer getClassOfIndustryId() {
        return classOfIndustryId;
    }

    public void setClassOfIndustryId(Integer classOfIndustryId) {
        this.classOfIndustryId = classOfIndustryId;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public String getCoverUrl() {
        return coverUrl;
    }

    public void setCoverUrl(String coverUrl) {
        this.coverUrl = coverUrl;
    }

    public Integer getSellState() {
        return sellState;
    }

    public void setSellState(Integer sellState) {
        this.sellState = sellState;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getCreatDate() {
        return creatDate;
    }

    public void setCreatDate(String creatDate) {
        this.creatDate = creatDate;
    }
}
