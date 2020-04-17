package com.jiaoyiyu.back.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * TestQuestion class
 *
 * @author maochaoying
 * @date 2019/11/18
 */

public class TestQuestion implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer assignmentId;

    private String title;

    private String describeDetails;

    private BigDecimal moneyNum;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescribeDetails() {
        return describeDetails;
    }

    public void setDescribeDetails(String describeDetails) {
        this.describeDetails = describeDetails;
    }

    public BigDecimal getMoneyNum() {
        return moneyNum;
    }

    public void setMoneyNum(BigDecimal moneyNum) {
        this.moneyNum = moneyNum;
    }
}
