package com.jiaoyiyu.back.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * Settlement class
 *
 * @author maochaoying
 * @date 2019/11/23
 */

public class Settlement implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer assignmentId;

    private BigDecimal hostingReward;

    private BigDecimal topService;

    private BigDecimal urgentService;

    private BigDecimal allhideService;

    private BigDecimal totalAmount;

    private Integer isPay;

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

    public BigDecimal getHostingReward() {
        return hostingReward;
    }

    public void setHostingReward(BigDecimal hostingReward) {
        this.hostingReward = hostingReward;
    }

    public BigDecimal getTopService() {
        return topService;
    }

    public void setTopService(BigDecimal topService) {
        this.topService = topService;
    }

    public BigDecimal getUrgentService() {
        return urgentService;
    }

    public void setUrgentService(BigDecimal urgentService) {
        this.urgentService = urgentService;
    }

    public BigDecimal getAllhideService() {
        return allhideService;
    }

    public void setAllhideService(BigDecimal allhideService) {
        this.allhideService = allhideService;
    }

    public BigDecimal getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(BigDecimal totalAmount) {
        this.totalAmount = totalAmount;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }
}
