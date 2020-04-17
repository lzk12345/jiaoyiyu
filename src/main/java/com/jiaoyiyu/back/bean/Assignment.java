package com.jiaoyiyu.back.bean;


import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;

public class Assignment implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer categoryid;

    private Integer tasktype;

    private Integer needtypeId;

    private String title;

    private Integer taskTimeTypeId;

    private String details;

    private String accessoryUrl;

    private BigDecimal subprice;

    private BigDecimal toubiaoPrice;

    @Column
    private String closingDate;

    private String pubtime;

    private String endtime;

    private Integer uid;

    private Integer taskStatus;

    private String remark;

    private Integer toubiaoType;

    private BigDecimal xuanshangPrice;

    private Integer xuanshangType;

    private String needs;

    private Byte testStatus;

    private Integer minMoney;

    private Integer maxMoney;

    private Byte top;

    private String topstart;

    private Integer singleStatus;

    private Byte urgent;

    private String urgentstart;

    private Integer remarkStatus;

    private Byte allhidden;

    private Integer partPeopleNum;

    private Integer browsePeopleNum;

    private BigDecimal closeaccount;

    private Integer gaojianNum;

    private Integer gaojianStatus;

    private BigDecimal gaojianSinglePrice;

    // 加入阶段的状态和时间
    private Integer scheduleStatus;

    private String tuoguanTaskmoneyDate;

    private String xuangaoDate;

    private String submitSourceDate;

    private String publictiyDate;

    private String endOrEvaluateDate;

    private Integer taskOrderStatus;

    public Integer getTaskOrderStatus() {
        return taskOrderStatus;
    }

    public void setTaskOrderStatus(Integer taskOrderStatus) {
        this.taskOrderStatus = taskOrderStatus;
    }

    public Integer getTaskTimeTypeId() {
        return taskTimeTypeId;
    }

    public void setTaskTimeTypeId(Integer taskTimeTypeId) {
        this.taskTimeTypeId = taskTimeTypeId;
    }

    public Integer getPartPeopleNum() {
        return partPeopleNum;
    }

    public void setPartPeopleNum(Integer partPeopleNum) {
        this.partPeopleNum = partPeopleNum;
    }

    public Integer getBrowsePeopleNum() {
        return browsePeopleNum;
    }

    public void setBrowsePeopleNum(Integer browsePeopleNum) {
        this.browsePeopleNum = browsePeopleNum;
    }

    public Integer getScheduleStatus() {
        return scheduleStatus;
    }

    public void setScheduleStatus(Integer scheduleStatus) {
        this.scheduleStatus = scheduleStatus;
    }

    public String getTuoguanTaskmoneyDate() {
        return tuoguanTaskmoneyDate;
    }

    public void setTuoguanTaskmoneyDate(String tuoguanTaskmoneyDate) {
        this.tuoguanTaskmoneyDate = tuoguanTaskmoneyDate;
    }

    public String getXuangaoDate() {
        return xuangaoDate;
    }

    public void setXuangaoDate(String xuangaoDate) {
        this.xuangaoDate = xuangaoDate;
    }

    public String getSubmitSourceDate() {
        return submitSourceDate;
    }

    public void setSubmitSourceDate(String submitSourceDate) {
        this.submitSourceDate = submitSourceDate;
    }

    public String getPublictiyDate() {
        return publictiyDate;
    }

    public void setPublictiyDate(String publictiyDate) {
        this.publictiyDate = publictiyDate;
    }

    public String getEndOrEvaluateDate() {
        return endOrEvaluateDate;
    }

    public void setEndOrEvaluateDate(String endOrEvaluateDate) {
        this.endOrEvaluateDate = endOrEvaluateDate;
    }

    public Integer getGaojianNum() {
        return gaojianNum;
    }

    public void setGaojianNum(Integer gaojianNum) {
        this.gaojianNum = gaojianNum;
    }

    public Integer getGaojianStatus() {
        return gaojianStatus;
    }

    public void setGaojianStatus(Integer gaojianStatus) {
        this.gaojianStatus = gaojianStatus;
    }

    public BigDecimal getGaojianSinglePrice() {
        return gaojianSinglePrice;
    }

    public void setGaojianSinglePrice(BigDecimal gaojianSinglePrice) {
        this.gaojianSinglePrice = gaojianSinglePrice;
    }

    public Integer getSingleStatus() {
        return singleStatus;
    }

    public void setSingleStatus(Integer singleStatus) {
        this.singleStatus = singleStatus;
    }

    public Integer getRemarkStatus() {
        return remarkStatus;
    }

    public void setRemarkStatus(Integer remarkStatus) {
        this.remarkStatus = remarkStatus;
    }

    public Integer getNeedtypeId() {
        return needtypeId;
    }

    public void setNeedtypeId(Integer needtypeId) {
        this.needtypeId = needtypeId;
    }

    public Integer getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(Integer minMoney) {
        this.minMoney = minMoney;
    }

    public Integer getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(Integer maxMoney) {
        this.maxMoney = maxMoney;
    }

    public String getNeeds() {
        return needs;
    }

    public void setNeeds(String needs) {
        this.needs = needs;
    }

    public BigDecimal getToubiaoPrice() {
        return toubiaoPrice;
    }

    public void setToubiaoPrice(BigDecimal toubiaoPrice) {
        this.toubiaoPrice = toubiaoPrice;
    }

    public Integer getToubiaoType() {
        return toubiaoType;
    }

    public void setToubiaoType(Integer toubiaoType) {
        this.toubiaoType = toubiaoType;
    }

    public BigDecimal getXuanshangPrice() {
        return xuanshangPrice;
    }

    public void setXuanshangPrice(BigDecimal xuanshangPrice) {
        this.xuanshangPrice = xuanshangPrice;
    }

    public Integer getXuanshangType() {
        return xuanshangType;
    }

    public void setXuanshangType(Integer xuanshangType) {
        this.xuanshangType = xuanshangType;
    }

    public Byte getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(Byte testStatus) {
        this.testStatus = testStatus;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(Integer categoryid) {
        this.categoryid = categoryid;
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
        this.title = title == null ? null : title.trim();
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details == null ? null : details.trim();
    }

    public String getAccessoryUrl() {
        return accessoryUrl;
    }

    public void setAccessoryUrl(String accessoryUrl) {
        this.accessoryUrl = accessoryUrl == null ? null : accessoryUrl.trim();
    }

    public BigDecimal getSubprice() {
        return subprice;
    }

    public void setSubprice(BigDecimal subprice) {
        this.subprice = subprice;
    }



    public Integer getUid() {
        return uid;
    }

    public void setUid(Integer uid) {
        this.uid = uid;
    }

    public Integer getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(Integer taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }



    public Byte getTop() {
        return top;
    }

    public void setTop(Byte top) {
        this.top = top;
    }


    public Byte getUrgent() {
        return urgent;
    }

    public void setUrgent(Byte urgent) {
        this.urgent = urgent;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public String getPubtime() {
        return pubtime;
    }

    public void setPubtime(String pubtime) {
        this.pubtime = pubtime;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getTopstart() {
        return topstart;
    }

    public void setTopstart(String topstart) {
        this.topstart = topstart;
    }

    public String getUrgentstart() {
        return urgentstart;
    }

    public void setUrgentstart(String urgentstart) {
        this.urgentstart = urgentstart;
    }

    public Byte getAllhidden() {
        return allhidden;
    }

    public void setAllhidden(Byte allhidden) {
        this.allhidden = allhidden;
    }

    public BigDecimal getCloseaccount() {
        return closeaccount;
    }

    public void setCloseaccount(BigDecimal closeaccount) {
        this.closeaccount = closeaccount;
    }
}