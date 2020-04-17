package com.jiaoyiyu.back.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * UserAndTag class
 * 该类用于用户id和技能标签id的中间表的对应。
 * @author maochaoying
 * @date 2020/4/2
 */

public class UserAndTag implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer uId;

    private Integer catalogId;

    private String theBestBusiness;

    public String getTheBestBusiness() {
        return theBestBusiness;
    }

    public void setTheBestBusiness(String theBestBusiness) {
        this.theBestBusiness = theBestBusiness;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getuId() {
        return uId;
    }

    public void setuId(Integer uId) {
        this.uId = uId;
    }

    public Integer getCatalogId() {
        return catalogId;
    }

    public void setCatalogId(Integer catalogId) {
        this.catalogId = catalogId;
    }
}
