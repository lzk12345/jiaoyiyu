package com.jiaoyiyu.back.coreservice;

import com.jiaoyiyu.back.bean.*;
import com.jiaoyiyu.back.bean.es.AssignmentES;
import org.springframework.stereotype.Service;

import java.util.List;
public interface TaskHallService {
    List<AssignmentES> getAssignmentFromES(AssignSearchParam assignSearchParam, Integer currentPage, Integer recordNum);

    String getUrlParam(AssignSearchParam assignSearchParam);

    List<SearchCromb> getSearchCrombs(AssignSearchParam assignSearchParam, List<Catalog1> catalog1, List<TbTaskModeType> taskModeType, List<TbTaskTimeType> taskTimeType);

    Long getAssignmentFromESTotal(AssignSearchParam assignSearchParam);
}
