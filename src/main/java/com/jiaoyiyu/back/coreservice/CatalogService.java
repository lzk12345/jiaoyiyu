package com.jiaoyiyu.back.coreservice;

import com.jiaoyiyu.back.bean.*;
import org.springframework.stereotype.Service;

import java.util.List;
public interface CatalogService {
    List<Catalog1> getCatalog1();

    List<Catalog2> getCatalog2(Integer catalog1Id);

    List<Catalog3> getCatalog3(Integer catalog2Id);

    Catalog2 getCatalog2ById(Integer category2id);

    Catalog1 getCatalog1ById(Integer catalog1Id);

    List<TbTaskTimeType> getTaskTimeType();

    List<TbTaskModeType> getTaskModeType();

    Catalog1 getCatalog1ByName(String cataName);

}
