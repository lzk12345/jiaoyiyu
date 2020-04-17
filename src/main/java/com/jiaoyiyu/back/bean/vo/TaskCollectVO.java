package com.jiaoyiyu.back.bean.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * TaskCollectVO class
 *
 * @author maochaoying
 * @date 2020/2/3
 */

public class TaskCollectVO implements Serializable {
    private Integer assignmentId;

    private String title;

    private BigDecimal totalMoney;

    private Integer tasktype;

    private Integer toubiaoType;

    private Integer xuanshangType;

    private Integer taskModeTypeId;

    private String collectionTime;

    private String endTime;

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

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }

    public Integer getTasktype() {
        return tasktype;
    }

    public void setTasktype(Integer tasktype) {
        this.tasktype = tasktype;
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

    public Integer getTaskModeTypeId() {
        return taskModeTypeId;
    }

    public void setTaskModeTypeId(Integer taskModeTypeId) {
        this.taskModeTypeId = taskModeTypeId;
    }

    public String getCollectionTime() {
        return collectionTime;
    }

    public void setCollectionTime(String collectionTime) {
        this.collectionTime = collectionTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }
}
