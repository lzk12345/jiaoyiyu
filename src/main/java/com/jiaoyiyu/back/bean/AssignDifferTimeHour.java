package com.jiaoyiyu.back.bean;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * AssignDifferTimeHour class
 *
 * @author maochaoying
 * @date 2019/12/4
 */

public class AssignDifferTimeHour implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Integer assignment;

    private Integer differTimeHour;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }


    public Integer getAssignment() {
        return assignment;
    }

    public void setAssignment(Integer assignment) {
        this.assignment = assignment;
    }

    public Integer getDifferTimeHour() {
        return differTimeHour;
    }

    public void setDifferTimeHour(Integer differTimeHour) {
        this.differTimeHour = differTimeHour;
    }
}
