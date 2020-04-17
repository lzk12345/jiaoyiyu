package com.jiaoyiyu.back.bean;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;

/**
 * Catalog3 class
 *
 * @author maochaoying
 * @date 2019/8/31
 */

public class Catalog3 implements Serializable {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer catalog2Id;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getCatalog2Id() {
        return catalog2Id;
    }

    public void setCatalog2Id(Integer catalog2Id) {
        this.catalog2Id = catalog2Id;
    }
}
