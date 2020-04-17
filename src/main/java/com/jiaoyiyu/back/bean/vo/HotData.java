package com.jiaoyiyu.back.bean.vo;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * HotData class
 *
 * @author maochaoying
 * @date 2020/2/6
 */

public class HotData implements Serializable {
    private Integer id;

    private Integer assignmentId;

    private String imgUrl;

    private Integer status;

    private String title;

    private String details;

    private Integer taskModeTypeId;

    private BigDecimal totalMoney;

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

    public String getImgUrl() {
        return imgUrl;
    }

    public void setImgUrl(String imgUrl) {
        this.imgUrl = imgUrl;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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

    public Integer getTaskModeTypeId() {
        return taskModeTypeId;
    }

    public void setTaskModeTypeId(Integer taskModeTypeId) {
        this.taskModeTypeId = taskModeTypeId;
    }

    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
}
