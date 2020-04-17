package com.jiaoyiyu.back.bean.es;

import java.io.Serializable;

/**
 * AssignmentES class
 *
 * @author maochaoying
 * @date 2019/12/4
 */

public class AssignmentES implements Serializable {
    private String id;
    private String categoryId;
    private String tasktype;
    private String title;
    private String details;
    private String toubiaoPrice;
    private String endtime;
    private String taskStatus;
    private String testStatus;
    private String toubiaoType;
    private String xuanshangPrice;
    private String taskTimeTypeId;
    private String xuanshangType;
    private String minMoney;
    private String maxMoney;
    private String testprice;
    private String browsePeopleNum;
    private String partPeopleNum;
    private String top;
    private String urgent;
    private String allhidden;
    private String taskDetailType;
    private String differTimeHour;
    private String category1Name;
    private String category2Name;

    public String getCategory1Name() {
        return category1Name;
    }

    public void setCategory1Name(String category1Name) {
        this.category1Name = category1Name;
    }

    public String getCategory2Name() {
        return category2Name;
    }

    public void setCategory2Name(String category2Name) {
        this.category2Name = category2Name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getTasktype() {
        return tasktype;
    }

    public void setTasktype(String tasktype) {
        this.tasktype = tasktype;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getToubiaoPrice() {
        return toubiaoPrice;
    }

    public void setToubiaoPrice(String toubiaoPrice) {
        this.toubiaoPrice = toubiaoPrice;
    }

    public String getEndtime() {
        return endtime;
    }

    public void setEndtime(String endtime) {
        this.endtime = endtime;
    }

    public String getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(String taskStatus) {
        this.taskStatus = taskStatus;
    }

    public String getTestStatus() {
        return testStatus;
    }

    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }

    public String getToubiaoType() {
        return toubiaoType;
    }

    public void setToubiaoType(String toubiaoType) {
        this.toubiaoType = toubiaoType;
    }

    public String getXuanshangPrice() {
        return xuanshangPrice;
    }

    public void setXuanshangPrice(String xuanshangPrice) {
        this.xuanshangPrice = xuanshangPrice;
    }

    public String getTaskTimeTypeId() {
        return taskTimeTypeId;
    }

    public void setTaskTimeTypeId(String taskTimeTypeId) {
        this.taskTimeTypeId = taskTimeTypeId;
    }

    public String getXuanshangType() {
        return xuanshangType;
    }

    public void setXuanshangType(String xuanshangType) {
        this.xuanshangType = xuanshangType;
    }

    public String getMinMoney() {
        return minMoney;
    }

    public void setMinMoney(String minMoney) {
        this.minMoney = minMoney;
    }

    public String getMaxMoney() {
        return maxMoney;
    }

    public void setMaxMoney(String maxMoney) {
        this.maxMoney = maxMoney;
    }

    public String getTestprice() {
        return testprice;
    }

    public void setTestprice(String testprice) {
        this.testprice = testprice;
    }

    public String getBrowsePeopleNum() {
        return browsePeopleNum;
    }

    public void setBrowsePeopleNum(String browsePeopleNum) {
        this.browsePeopleNum = browsePeopleNum;
    }

    public String getPartPeopleNum() {
        return partPeopleNum;
    }

    public void setPartPeopleNum(String partPeopleNum) {
        this.partPeopleNum = partPeopleNum;
    }

    public String getTop() {
        return top;
    }

    public void setTop(String top) {
        this.top = top;
    }

    public String getUrgent() {
        return urgent;
    }

    public void setUrgent(String urgent) {
        this.urgent = urgent;
    }

    public String getAllhidden() {
        return allhidden;
    }

    public void setAllhidden(String allhidden) {
        this.allhidden = allhidden;
    }

    public String getTaskDetailType() {
        return taskDetailType;
    }

    public void setTaskDetailType(String taskDetailType) {
        this.taskDetailType = taskDetailType;
    }

    public String getDifferTimeHour() {
        return differTimeHour;
    }

    public void setDifferTimeHour(String differTimeHour) {
        this.differTimeHour = differTimeHour;
    }

}
