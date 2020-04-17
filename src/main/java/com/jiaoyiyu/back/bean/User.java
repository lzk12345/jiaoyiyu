package com.jiaoyiyu.back.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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

    private Integer isRealNameCheck;

    private Integer isPayCheck;

    private BigDecimal earnTheAmount;

    private String aboutTheAuthor;

    private String weixin;

    private String firstAddr;

    private String secondAddr;

    private String thirdAddr;

    private String detailAddr;

    private Integer payPassword;

    private Integer reputationValue;

    public Integer getReputationValue() {
        return reputationValue;
    }

    public void setReputationValue(Integer reputationValue) {
        this.reputationValue = reputationValue;
    }

    public Integer getPayPassword() {
        return payPassword;
    }

    public void setPayPassword(Integer payPassword) {
        this.payPassword = payPassword;
    }

    public String getFirstAddr() {
        return firstAddr;
    }

    public void setFirstAddr(String firstAddr) {
        this.firstAddr = firstAddr;
    }

    public String getSecondAddr() {
        return secondAddr;
    }

    public void setSecondAddr(String secondAddr) {
        this.secondAddr = secondAddr;
    }

    public String getThirdAddr() {
        return thirdAddr;
    }

    public void setThirdAddr(String thirdAddr) {
        this.thirdAddr = thirdAddr;
    }

    public String getDetailAddr() {
        return detailAddr;
    }

    public void setDetailAddr(String detailAddr) {
        this.detailAddr = detailAddr;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getWeixin() {
        return weixin;
    }

    public void setWeixin(String weixin) {
        this.weixin = weixin;
    }

    public String getAboutTheAuthor() {
        return aboutTheAuthor;
    }

    public void setAboutTheAuthor(String aboutTheAuthor) {
        this.aboutTheAuthor = aboutTheAuthor;
    }

    public BigDecimal getEarnTheAmount() {
        return earnTheAmount;
    }

    public void setEarnTheAmount(BigDecimal earnTheAmount) {
        this.earnTheAmount = earnTheAmount;
    }

    public Integer getIsPayCheck() {
        return isPayCheck;
    }

    public void setIsPayCheck(Integer isPayCheck) {
        this.isPayCheck = isPayCheck;
    }

    public Integer getIsRealNameCheck() {
        return isRealNameCheck;
    }

    public void setIsRealNameCheck(Integer isRealNameCheck) {
        this.isRealNameCheck = isRealNameCheck;
    }

    public Integer getIsPayStoreMoney() {
        return isPayStoreMoney;
    }

    public void setIsPayStoreMoney(Integer isPayStoreMoney) {
        this.isPayStoreMoney = isPayStoreMoney;
    }

    public Integer getFavorableRate() {
        return favorableRate;
    }

    public void setFavorableRate(Integer favorableRate) {
        this.favorableRate = favorableRate;
    }

    public Integer getIsOpenStore() {
        return isOpenStore;
    }

    public void setIsOpenStore(Integer isOpenStore) {
        this.isOpenStore = isOpenStore;
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

    public Integer getVipType() {
        return vipType;
    }

    public void setVipType(Integer vipType) {
        this.vipType = vipType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username == null ? null : username.trim();
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone == null ? null : phone.trim();
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
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
        this.sourceType = sourceType == null ? null : sourceType.trim();
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName == null ? null : nickName.trim();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic == null ? null : headPic.trim();
    }

    public String getQq() {
        return qq;
    }

    public void setQq(String qq) {
        this.qq = qq == null ? null : qq.trim();
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
        this.sex = sex == null ? null : sex.trim();
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

    public String getCertifiedOrNot() {
        return certifiedOrNot;
    }

    public void setCertifiedOrNot(String certifiedOrNot) {
        this.certifiedOrNot = certifiedOrNot == null ? null : certifiedOrNot.trim();
    }
}