package com.jiaoyiyu.back.bean.vo;


import com.jiaoyiyu.back.bean.TbManuscriptUrl;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**
 * TaskDetailsAndUserInfo class
 *
 * @author maochaoying
 * @date 2019/12/20
 */

public class TaskDetailsAndUserInfo implements Serializable {
    private Integer manuscript_id;

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

    private Integer loginMemberId;

    private String username;

    private String password;

    private String phone;

    private String email;

    private Date created;

    private Date updated;

    private String sourceType;

    private String nickName;

    private Integer favorableRate;

    private String name;

    private String status;

    private String headPic;

    private String qq;

    private BigDecimal accountBalance;

    private Integer isMobileCheck;

    private Integer isEmailCheck;

    private String sex;

    private Integer userLevel;

    private Integer points;

    private Integer experienceValue;

    private String birthday;

    private Date lastLoginTime;

    private Integer age;

    private Integer allHideResidualTimes;

    private Integer taskHideResidualTimes;

    private Integer taskTopResidualTimes;

    private Integer taskUrgentResidualTimes;

    private String certifiedOrNot;

    private Integer vipType;

    private Integer isOpenStore;

    private Integer isPayStoreMoney;

    private Integer isDesign;

    public Integer getIsDesign() {
        return isDesign;
    }

    public void setIsDesign(Integer isDesign) {
        this.isDesign = isDesign;
    }


    public Integer getLoginMemberId() {
        return loginMemberId;
    }

    public void setLoginMemberId(Integer loginMemberId) {
        this.loginMemberId = loginMemberId;
    }

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

    public Integer getIsPayStoreMoney() {
        return isPayStoreMoney;
    }

    public void setIsPayStoreMoney(Integer isPayStoreMoney) {
        this.isPayStoreMoney = isPayStoreMoney;
    }

    private List<TbManuscriptUrl> tbManuscriptUrls;

    private Integer max_value;

    public Integer getMax_value() {
        return max_value;
    }

    public void setMax_value(Integer max_value) {
        this.max_value = max_value;
    }

    public List<TbManuscriptUrl> getTbManuscriptUrls() {
        return tbManuscriptUrls;
    }

    public void setTbManuscriptUrls(List<TbManuscriptUrl> tbManuscriptUrls) {
        this.tbManuscriptUrls = tbManuscriptUrls;
    }

    public String getCeshiDetails() {
        return ceshiDetails;
    }

    public void setCeshiDetails(String ceshiDetails) {
        this.ceshiDetails = ceshiDetails;
    }

    public Integer getManuscript_id() {
        return manuscript_id;
    }

    public void setManuscript_id(Integer manuscript_id) {
        this.manuscript_id = manuscript_id;
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

    public String getQuoteDetails() {
        return quoteDetails;
    }

    public void setQuoteDetails(String quoteDetails) {
        this.quoteDetails = quoteDetails;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public Integer getFavorableRate() {
        return favorableRate;
    }

    public void setFavorableRate(Integer favorableRate) {
        this.favorableRate = favorableRate;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq;
    }

    public BigDecimal getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(BigDecimal accountBalance) {
        this.accountBalance = accountBalance;
    }

    public Integer getIsMobileCheck() {
        return isMobileCheck;
    }

    public void setIsMobileCheck(Integer isMobileCheck) {
        this.isMobileCheck = isMobileCheck;
    }

    public Integer getIsEmailCheck() {
        return isEmailCheck;
    }

    public void setIsEmailCheck(Integer isEmailCheck) {
        this.isEmailCheck = isEmailCheck;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public Integer getUserLevel() {
        return userLevel;
    }

    public void setUserLevel(Integer userLevel) {
        this.userLevel = userLevel;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getExperienceValue() {
        return experienceValue;
    }

    public void setExperienceValue(Integer experienceValue) {
        this.experienceValue = experienceValue;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Integer getAllHideResidualTimes() {
        return allHideResidualTimes;
    }

    public void setAllHideResidualTimes(Integer allHideResidualTimes) {
        this.allHideResidualTimes = allHideResidualTimes;
    }

    public Integer getTaskHideResidualTimes() {
        return taskHideResidualTimes;
    }

    public void setTaskHideResidualTimes(Integer taskHideResidualTimes) {
        this.taskHideResidualTimes = taskHideResidualTimes;
    }

    public Integer getTaskTopResidualTimes() {
        return taskTopResidualTimes;
    }

    public void setTaskTopResidualTimes(Integer taskTopResidualTimes) {
        this.taskTopResidualTimes = taskTopResidualTimes;
    }

    public Integer getTaskUrgentResidualTimes() {
        return taskUrgentResidualTimes;
    }

    public void setTaskUrgentResidualTimes(Integer taskUrgentResidualTimes) {
        this.taskUrgentResidualTimes = taskUrgentResidualTimes;
    }

    public String getCertifiedOrNot() {
        return certifiedOrNot;
    }

    public void setCertifiedOrNot(String certifiedOrNot) {
        this.certifiedOrNot = certifiedOrNot;
    }

    public Integer getVipType() {
        return vipType;
    }

    public void setVipType(Integer vipType) {
        this.vipType = vipType;
    }

    public Integer getIsOpenStore() {
        return isOpenStore;
    }

    public void setIsOpenStore(Integer isOpenStore) {
        this.isOpenStore = isOpenStore;
    }
}
