package com.jiaoyiyu.back.bean.vo;

import java.io.Serializable;
import java.util.Date;

/**
 * ScoreGroupVO class
 *
 * @author maochaoying
 * @date 2019/11/12
 */

public class ScoreGroupVO implements Serializable {
    private Integer sign;

    private Date createDate;

    private String remarks;
    //来源
    private String source;
    //来源积分数
    private Integer num;

    public Integer getSign() {
        return sign;
    }

    public void setSign(Integer sign) {
        this.sign = sign;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }
}
