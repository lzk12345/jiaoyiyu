package com.jiaoyiyu.back.bean;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @param
 * @return
 */
public class Catalog2 implements Serializable {
    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column
    private String name;
    @Column
    private Integer catalog1Id;

    @Transient
    private List<Catalog3> catalog3List;

    public List<Catalog3> getCatalog3List() {
        return catalog3List;
    }

    public void setCatalog3List(List<Catalog3> catalog3List) {
        this.catalog3List = catalog3List;
    }


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

    public Integer getCatalog1Id() {
        return catalog1Id;
    }

    public void setCatalog1Id(Integer catalog1Id) {
        this.catalog1Id = catalog1Id;
    }
}
