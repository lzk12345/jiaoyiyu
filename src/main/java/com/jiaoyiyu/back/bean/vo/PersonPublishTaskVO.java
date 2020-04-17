package com.jiaoyiyu.back.bean.vo;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.List;

/**
 * PersonPublishTaskVO class
 *
 * @author maochaoying
 * @date 2019/12/31
 */

public class PersonPublishTaskVO implements Serializable {
    private Integer id;

    private Integer tasktype;

    private String title;

    private Integer taskTimeTypeId;

    private String pubtime;

    private Integer taskStatus;

    private Byte testStatus;

    private Integer toubiaoType;

    private Integer xuanshangType;

    private Integer scheduleStatus;

    private Integer order_id;

    private String ordeno;

    private BigDecimal testMoneyNum;

    private Byte orderstatus;

    private Byte ispay;

    private BigDecimal totalMoney;

    private BigDecimal orderprice;

    private BigDecimal payprice;

    private List<Integer> tbJoinTaskDetailsId;

    private Integer taskModeTypeId;

    private Integer taskOrderStatus;

    public Integer getTaskOrderStatus() {
        return taskOrderStatus;
    }

    public void setTaskOrderStatus(Integer taskOrderStatus) {
        this.taskOrderStatus = taskOrderStatus;
    }

    public Integer getTaskModeTypeId() {
        return taskModeTypeId;
    }

    public void setTaskModeTypeId(Integer taskModeTypeId) {
        this.taskModeTypeId = taskModeTypeId;
    }

    public Byte getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(Byte testStatus) {
        this.testStatus = testStatus;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public BigDecimal getTestMoneyNum() {
        return testMoneyNum;
    }

    public void setTestMoneyNum(BigDecimal testMoneyNum) {
        this.testMoneyNum = testMoneyNum;
    }

    public List<Integer> getTbJoinTaskDetailsId() {
        return tbJoinTaskDetailsId;
    }

    public void setTbJoinTaskDetailsId(List<Integer> tbJoinTaskDetailsId) {
        this.tbJoinTaskDetailsId = tbJoinTaskDetailsId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getTasktype() {
        return tasktype;
    }

    public void setTasktype(Integer tasktype) {
        this.tasktype = tasktype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getTaskTimeTypeId() {
        return taskTimeTypeId;
    }

    public void setTaskTimeTypeId(Integer taskTimeTypeId) {
        this.taskTimeTypeId = taskTimeTypeId;
    }

    public String getPubtime() {
        return pubtime;
    }

    public void setPubtime(String pubtime) {
        this.pubtime = pubtime;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public Integer getToubiaoType() {
        return toubiaoType;
    }

    public void setToubiaoType(Integer toubiaoType) {
        this.toubiaoType = toubiaoType;
    }

    public Integer getXuanshangType() {
        return xuanshangType;
    }

    public void setXuanshangType(Integer xuanshangType) {
        this.xuanshangType = xuanshangType;
    }

    public Integer getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(Integer scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    public Integer getOrder_id() {
        return order_id;
    }

    public void setOrder_id(Integer order_id) {
        this.order_id = order_id;
    }

    public String getOrdeno() {
        return ordeno;
    }

    public void setOrdeno(String ordeno) {
        this.ordeno = ordeno;
    }

    public Byte getOrderstatus() {
        return orderstatus;
    }

    public void setOrderstatus(Byte orderstatus) {
        this.orderstatus = orderstatus;
    }

    public Byte getIspay() {
        return ispay;
    }

    public void setIspay(Byte ispay) {
        this.ispay = ispay;
    }

    public BigDecimal getOrderprice() {
        return orderprice;
    }

    public void setOrderprice(BigDecimal orderprice) {
        this.orderprice = orderprice;
    }

    public BigDecimal getPayprice() {
        return payprice;
    }

    public void setPayprice(BigDecimal payprice) {
        this.payprice = payprice;
    }
}
