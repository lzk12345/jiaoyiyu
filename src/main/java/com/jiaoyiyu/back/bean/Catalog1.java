package com.jiaoyiyu.back.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @param
 * @return
 */
public class Catalog1 implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;

    @Transient
    private List<Catalog2> catalog2s;

    public List<Catalog2> getCatalog2s() {
        return catalog2s;
    }

    public void setCatalog2s(List<Catalog2> catalog2s) {
        this.catalog2s = catalog2s;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}

