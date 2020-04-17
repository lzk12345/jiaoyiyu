package com.jiaoyiyu.back.coreservice.Impl;

import com.jiaoyiyu.back.bean.*;
import com.jiaoyiyu.back.coredao.*;
import com.jiaoyiyu.back.coreservice.CatalogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * CatalogServiceImpl class
 *
 * @author maochaoying
 * @date 2019/11/13
 */
@Service
public class CatalogServiceImpl implements CatalogService {

    @Autowired
    Catalog1Mapper catalog1Mapper;
    @Autowired
    Catalog2Mapper catalog2Mapper;
    @Autowired
    Catalog3Mapper catalog3Mapper;
    @Autowired
    TbTaskTimeTypeMapper tbTaskTimeTypeMapper;
    @Autowired
    TbTaskModeTypeMapper tbTaskModeTypeMapper;
    @Override
    public List<Catalog1> getCatalog1() {
        return catalog1Mapper.selectAll();
    }

    @Override
    public List<Catalog2> getCatalog2(Integer catalog1Id) {
        Catalog2 catalog2 = new Catalog2();
        catalog2.setCatalog1Id(catalog1Id);
        return catalog2Mapper.select(catalog2);
    }

    @Override
    public List<Catalog3> getCatalog3(Integer catalog2Id) {
        Catalog3 catalog3 = new Catalog3();
        catalog3.setCatalog2Id(catalog2Id);
        return catalog3Mapper.select(catalog3);
    }

    @Override
    public Catalog2 getCatalog2ById(Integer category2id) {
        Catalog2 catalog2 = new Catalog2();
        catalog2.setId(category2id);
        Catalog2 catalog21 = catalog2Mapper.selectOne(catalog2);
        return catalog21;
    }

    @Override
    public Catalog1 getCatalog1ById(Integer catalog1Id) {
        Catalog1 catalog1 = new Catalog1();
        catalog1.setId(catalog1Id);
        Catalog1 catalog11 = catalog1Mapper.selectOne(catalog1);
        return catalog11;
    }

    @Override
    public List<TbTaskTimeType> getTaskTimeType() {
        List<TbTaskTimeType> tbTaskTimeTypes = tbTaskTimeTypeMapper.selectAll();
        return tbTaskTimeTypes;
    }

    @Override
    public List<TbTaskModeType> getTaskModeType() {
        List<TbTaskModeType> tbTaskModeTypes = tbTaskModeTypeMapper.selectAll();
        return tbTaskModeTypes;
    }

    @Override
    public Catalog1 getCatalog1ByName(String cataName) {
        Catalog1 catalog1 = new Catalog1();
        catalog1.setName(cataName);
        Catalog1 catalog11 = catalog1Mapper.selectOne(catalog1);
        return catalog11;
    }

}
