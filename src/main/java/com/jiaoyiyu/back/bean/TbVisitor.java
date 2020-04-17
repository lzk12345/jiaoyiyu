package com.jiaoyiyu.back.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Transient;
import java.io.Serializable;
import java.util.List;

/**
 * TbVisitor class
 *
 * @author maochaoying
 * @date 2020/4/2
 */

public class TbVisitor implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer originUid;

    private Integer visitorUid;

    private String visitTime;

    @Transient
    private String nickName;
    @Transient
    private String headPic;
    @Transient
    private long dayage;

    public long getDayage() {
        return dayage;
    }

    public void setDayage(long dayage) {
        this.dayage = dayage;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getHeadPic() {
        return headPic;
    }

    public void setHeadPic(String headPic) {
        this.headPic = headPic;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getOriginUid() {
        return originUid;
    }

    public void setOriginUid(Integer originUid) {
        this.originUid = originUid;
    }

    public Integer getVisitorUid() {
        return visitorUid;
    }

    public void setVisitorUid(Integer visitorUid) {
        this.visitorUid = visitorUid;
    }

    public String getVisitTime() {
        return visitTime;
    }

    public void setVisitTime(String visitTime) {
        this.visitTime = visitTime;
    }
}
