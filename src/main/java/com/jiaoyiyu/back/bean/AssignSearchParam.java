package com.jiaoyiyu.back.bean;

import java.io.Serializable;

/**
 * AssignSearchParam class
 *
 * @author maochaoying
 * @date 2019/12/4
 */

public class AssignSearchParam implements Serializable {

    private String categoryid;

    private String keyword;

    private String taskTimeTypeId;

    private String tasktype;

    public String getCategoryid() {
        return categoryid;
    }

    public void setCategoryid(String categoryid) {
        this.categoryid = categoryid;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getTaskTimeTypeId() {
        return taskTimeTypeId;
    }

    public void setTaskTimeTypeId(String taskTimeTypeId) {
        this.taskTimeTypeId = taskTimeTypeId;
    }

    public String getTasktype() {
        return tasktype;
    }

    public void setTasktype(String tasktype) {
        this.tasktype = tasktype;
    }
}
