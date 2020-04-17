package com.jiaoyiyu.back.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;

/**
 * TbJoinTaskDetails class
 *
 * @author maochaoying
 * @date 2019/12/10
 */

public class TbJoinTaskDetails implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer uId;

    private Integer assignmentId;

    private BigDecimal ensureMoney;

    private Integer isWinTheBidding;

    private Integer taskType;

    private String uploadTime;

    private Integer isCat;

    private Integer isHide;

    private Integer workPeriod;

    private BigDecimal projectQuotation;

    private Integer isPay;

    private String quoteDetails;

    private String ceshiDetails;

    private Integer jijianHegeFirst;
    private Integer jijianHegeSecond;
    private Integer jijianHegeThird;
    private Integer jijianHegeFour;
    private Integer jijianHegeFive;



    public Integer getJijianHegeFirst() {
        return jijianHegeFirst;
    }

    public void setJijianHegeFirst(Integer jijianHegeFirst) {
        this.jijianHegeFirst = jijianHegeFirst;
    }

    public Integer getJijianHegeSecond() {
        return jijianHegeSecond;
    }

    public void setJijianHegeSecond(Integer jijianHegeSecond) {
        this.jijianHegeSecond = jijianHegeSecond;
    }

    public Integer getJijianHegeThird() {
        return jijianHegeThird;
    }

    public void setJijianHegeThird(Integer jijianHegeThird) {
        this.jijianHegeThird = jijianHegeThird;
    }

    public Integer getJijianHegeFour() {
        return jijianHegeFour;
    }

    public void setJijianHegeFour(Integer jijianHegeFour) {
        this.jijianHegeFour = jijianHegeFour;
    }

    public Integer getJijianHegeFive() {
        return jijianHegeFive;
    }

    public void setJijianHegeFive(Integer jijianHegeFive) {
        this.jijianHegeFive = jijianHegeFive;
    }

    public String getQuoteDetails() {
        return quoteDetails;
    }

    public void setQuoteDetails(String quoteDetails) {
        this.quoteDetails = quoteDetails;
    }

    public String getCeshiDetails() {
        return ceshiDetails;
    }

    public void setCeshiDetails(String ceshiDetails) {
        this.ceshiDetails = ceshiDetails;
    }

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

    public Integer getAssignmentId() {
        return assignmentId;
    }

    public void setAssignmentId(Integer assignmentId) {
        this.assignmentId = assignmentId;
    }

    public BigDecimal getEnsureMoney() {
        return ensureMoney;
    }

    public void setEnsureMoney(BigDecimal ensureMoney) {
        this.ensureMoney = ensureMoney;
    }

    public Integer getIsWinTheBidding() {
        return isWinTheBidding;
    }

    public void setIsWinTheBidding(Integer isWinTheBidding) {
        this.isWinTheBidding = isWinTheBidding;
    }

    public Integer getTaskType() {
        return taskType;
    }

    public void setTaskType(Integer taskType) {
        this.taskType = taskType;
    }

    public String getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(String uploadTime) {
        this.uploadTime = uploadTime;
    }

    public Integer getIsCat() {
        return isCat;
    }

    public void setIsCat(Integer isCat) {
        this.isCat = isCat;
    }

    public Integer getIsHide() {
        return isHide;
    }

    public void setIsHide(Integer isHide) {
        this.isHide = isHide;
    }

    public Integer getWorkPeriod() {
        return workPeriod;
    }

    public void setWorkPeriod(Integer workPeriod) {
        this.workPeriod = workPeriod;
    }

    public BigDecimal getProjectQuotation() {
        return projectQuotation;
    }

    public void setProjectQuotation(BigDecimal projectQuotation) {
        this.projectQuotation = projectQuotation;
    }

    public Integer getIsPay() {
        return isPay;
    }

    public void setIsPay(Integer isPay) {
        this.isPay = isPay;
    }
}
