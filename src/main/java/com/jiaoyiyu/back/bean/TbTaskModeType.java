package com.jiaoyiyu.back.bean;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * TbTaskModeType class
 *
 * @author maochaoying
 * @date 2019/12/3
 */

public class TbTaskModeType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String taskModeType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskModeType() {
        return taskModeType;
    }

    public void setTaskModeType(String taskModeType) {
        this.taskModeType = taskModeType;
    }
}
