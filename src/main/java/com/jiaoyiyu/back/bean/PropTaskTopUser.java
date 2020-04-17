package com.jiaoyiyu.back.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * PropTaskTopUser class
 * 任务置顶
 * @author maochaoying
 * @date 2019/11/15
 */

public class PropTaskTopUser implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    //消耗积分
    private Integer integralUsed;
    //话费金钱
    private Integer moneyUsed;
    // 用户id
    private Integer userId;
    // 任务id
    private Integer assignmentId;
    //折扣
    private BigDecimal discount;
    // 折扣开始时间
    private Date beginTime;
    // 折扣结束时间
    private Date endTime;

    //原价
    private BigDecimal originalCost;

    public BigDecimal getOriginalCost() {
        return originalCost;
    }

    public void setOriginalCost(BigDecimal originalCost) {
        this.originalCost = originalCost;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getIntegralUsed() {
        return integralUsed;
    }

    public void setIntegralUsed(Integer integralUsed) {
        this.integralUsed = integralUsed;
    }

    public Integer getMoneyUsed() {
        return moneyUsed;
    }

    public void setMoneyUsed(Integer moneyUsed) {
        this.moneyUsed = moneyUsed;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public BigDecimal getDiscount() {
        return discount;
    }

    public void setDiscount(BigDecimal discount) {
        this.discount = discount;
    }

    public Date getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(Date beginTime) {
        this.beginTime = beginTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

}
