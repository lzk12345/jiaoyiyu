package com.jiaoyiyu.back.coreservice;

import com.jiaoyiyu.back.bean.TbHotHomepageData;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * HotDataService class
 *
 * @author maochaoying
 * @date 2020/2/6
 */
public interface HotDataService {
    List<TbHotHomepageData> getHotData();
}
