package com.jiaoyiyu.back.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * UserAndOtherBusiness class
 *
 * @author maochaoying
 * @date 2020/4/4
 */

public class UserAndOtherBusiness implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer uId;

    private String otherBusiness;

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

    public String getOtherBusiness() {
        return otherBusiness;
    }

    public void setOtherBusiness(String otherBusiness) {
        this.otherBusiness = otherBusiness;
    }
}
