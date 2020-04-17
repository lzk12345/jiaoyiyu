package com.jiaoyiyu.back.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * RewardMore class
 *
 * @author maochaoying
 * @date 2019/11/18
 */

public class RewardMore implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer assignmentId;

    private Integer peopleNum;

    private BigDecimal firstPrize;
    private BigDecimal secondPrize;
    private BigDecimal thirdPrize;
    private Integer firstPrizeNum;
    private Integer secondPrizeNum;
    private Integer thirdPrizeNum;
    private BigDecimal zhanbi;

    private Integer firstStatus;

    private Integer secondStatus;

    private Integer thirdStatus;

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

    public Integer getPeopleNum() {
        return peopleNum;
    }

    public void setPeopleNum(Integer peopleNum) {
        this.peopleNum = peopleNum;
    }

    public BigDecimal getFirstPrize() {
        return firstPrize;
    }

    public void setFirstPrize(BigDecimal firstPrize) {
        this.firstPrize = firstPrize;
    }

    public BigDecimal getSecondPrize() {
        return secondPrize;
    }

    public void setSecondPrize(BigDecimal secondPrize) {
        this.secondPrize = secondPrize;
    }

    public BigDecimal getThirdPrize() {
        return thirdPrize;
    }

    public void setThirdPrize(BigDecimal thirdPrize) {
        this.thirdPrize = thirdPrize;
    }

    public Integer getFirstPrizeNum() {
        return firstPrizeNum;
    }

    public void setFirstPrizeNum(Integer firstPrizeNum) {
        this.firstPrizeNum = firstPrizeNum;
    }

    public Integer getSecondPrizeNum() {
        return secondPrizeNum;
    }

    public void setSecondPrizeNum(Integer secondPrizeNum) {
        this.secondPrizeNum = secondPrizeNum;
    }

    public Integer getThirdPrizeNum() {
        return thirdPrizeNum;
    }

    public void setThirdPrizeNum(Integer thirdPrizeNum) {
        this.thirdPrizeNum = thirdPrizeNum;
    }

    public BigDecimal getZhanbi() {
        return zhanbi;
    }

    public void setZhanbi(BigDecimal zhanbi) {
        this.zhanbi = zhanbi;
    }

    public Integer getFirstStatus() {
        return firstStatus;
    }

    public void setFirstStatus(Integer firstStatus) {
        this.firstStatus = firstStatus;
    }

    public Integer getSecondStatus() {
        return secondStatus;
    }

    public void setSecondStatus(Integer secondStatus) {
        this.secondStatus = secondStatus;
    }

    public Integer getThirdStatus() {
        return thirdStatus;
    }

    public void setThirdStatus(Integer thirdStatus) {
        this.thirdStatus = thirdStatus;
    }
}
