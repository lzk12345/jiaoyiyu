package com.jiaoyiyu.back.coreservice.Impl;

import com.jiaoyiyu.back.bean.TbHeadPicUrl;
import com.jiaoyiyu.back.coredao.TbHeadPicUrlMapper;
import com.jiaoyiyu.back.coreservice.TbHeadPicUrlService;
import com.jiaoyiyu.back.coreservice.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.util.List;

/**
 * TbHeadPicUrlServiceImpl class
 *
 * @author maochaoying
 * @date 2020/2/4
 */
@Service
public class TbHeadPicUrlServiceImpl implements TbHeadPicUrlService {

    @Autowired
    TbHeadPicUrlMapper tbHeadPicUrlMapper;
    @Autowired
    UserService userService;

    @Override
    public int savePicUrl(String url, String originalFilename, String extName, BigDecimal sizeBig, Integer memberId) {
        Example example = new Example(TbHeadPicUrl.class);
        example.createCriteria().andEqualTo("uId",memberId);
        List<TbHeadPicUrl> tbHeadPicUrls = tbHeadPicUrlMapper.selectByExample(example);
        int i = 0;
        if (tbHeadPicUrls == null || tbHeadPicUrls.size() <= 0) {
            // 数据库没有该对象的头像
            TbHeadPicUrl tbHeadPicUrl1 = new TbHeadPicUrl();
            tbHeadPicUrl1.setuId(memberId);
            tbHeadPicUrl1.setUrl(url);
            tbHeadPicUrl1.setFileName(originalFilename);
            tbHeadPicUrl1.setExtendName(extName);
            tbHeadPicUrl1.setFileSize(sizeBig);
            i = tbHeadPicUrlMapper.insertSelective(tbHeadPicUrl1);
        }else {
            TbHeadPicUrl tbHeadPicUrl2 = new TbHeadPicUrl();
            tbHeadPicUrl2.setuId(memberId);
            tbHeadPicUrl2.setUrl(url);
            tbHeadPicUrl2.setFileName(originalFilename);
            tbHeadPicUrl2.setExtendName(extName);
            tbHeadPicUrl2.setFileSize(sizeBig);
            i = tbHeadPicUrlMapper.updateByExample(tbHeadPicUrl2, example);
        }

        // 更改user
        int res = userService.updateHeadPicByMemberId(memberId,url);



        return i;
    }
}
