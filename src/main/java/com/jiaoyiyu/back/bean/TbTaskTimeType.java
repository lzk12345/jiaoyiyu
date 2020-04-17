package com.jiaoyiyu.back.bean;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * TbTaskTimeType class
 *
 * @author maochaoying
 * @date 2019/12/3
 */

public class TbTaskTimeType implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String taskTimeType;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTaskTimeType() {
        return taskTimeType;
    }

    public void setTaskTimeType(String taskTimeType) {
        this.taskTimeType = taskTimeType;
    }
}
