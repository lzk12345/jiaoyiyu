package com.jiaoyiyu.back.coreservice.Impl;

import com.jiaoyiyu.back.bean.AllWorks;
import com.jiaoyiyu.back.coredao.AllWorksMapper;
import com.jiaoyiyu.back.coreservice.UploadWorkService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;

/**
 * UploadWorkServiceImpl class
 *
 * @author maochaoying
 * @date 2020/4/6
 */
@Service
public class UploadWorkServiceImpl implements UploadWorkService {

    @Autowired
    AllWorksMapper allWorksMapper;


    @Override
    public int saveWork(Integer memberId, int i, String time, BigDecimal price
            , String worksTitle, Integer workTypeV, Integer industryV,
                        String workDetail, Integer radioV) {
        AllWorks allWorks1 = new AllWorks();
        allWorks1.setId(i);
        AllWorks allWorks2 = allWorksMapper.selectOne(allWorks1);
        if (allWorks2 == null) {
            AllWorks allWorks = new AllWorks();
            allWorks.setId(i);
            allWorks.setCataId(industryV);
            allWorks.setClassOfIndustryId(workTypeV);
            allWorks.setCreatDate(time);
            allWorks.setDetail(workDetail);
            allWorks.setPrice(price);
            allWorks.setSellState(radioV);
            allWorks.setuId(memberId);
            allWorks.setTitle(worksTitle);
            return allWorksMapper.insertSelective(allWorks);
        }else {
            AllWorks allWorks = new AllWorks();
            allWorks.setCataId(industryV);
            allWorks.setClassOfIndustryId(workTypeV);
            allWorks.setCreatDate(time);
            allWorks.setDetail(workDetail);
            allWorks.setPrice(price);
            allWorks.setSellState(radioV);
            allWorks.setuId(memberId);
            allWorks.setTitle(worksTitle);
            Example example = new Example(AllWorks.class);
            example.createCriteria().andEqualTo("id",i);
            return allWorksMapper.updateByExampleSelective(allWorks, example);
        }
    }

    @Override
    public void uploaderCover(String workId, String url) {
        AllWorks allWorks1 = new AllWorks();
        allWorks1.setId(Integer.parseInt(workId));
        AllWorks allWorks2 = allWorksMapper.selectOne(allWorks1);
        if (allWorks2 == null) {
            allWorks1.setCoverUrl(url);
            allWorksMapper.insertSelective(allWorks1);
        }else {
            AllWorks allWorks = new AllWorks();
            allWorks.setCoverUrl(url);
            Example example = new Example(AllWorks.class);
            example.createCriteria().andEqualTo("id",workId);
            allWorksMapper.updateByExampleSelective(allWorks, example);
        }
    }
}
