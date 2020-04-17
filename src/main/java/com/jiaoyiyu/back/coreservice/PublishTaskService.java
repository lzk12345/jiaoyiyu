package com.jiaoyiyu.back.coreservice;

import com.jiaoyiyu.back.bean.*;
import com.jiaoyiyu.back.bean.vo.PersonPublishTaskVO;
import com.jiaoyiyu.back.bean.vo.TaskCollectVO;
import com.jiaoyiyu.back.bean.vo.TaskDetailsAndUserInfo;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

/**
 * PublishTaskService class
 *
 * @author maochaoying
 * @date 2019/11/7
 */
public interface PublishTaskService {
    User getUserInfoByMemberId(Integer memberId);

    int saveIneedAndPhoneNumToDB(String ineed, String phonenum, Integer memberId1);

    int saveIneedAndPhoneNumToDBWithOutId(String ineed, String phonenum);

    User getUserInfoByPhoneNum(String phonenum);

    Assignment getAssignmentInfoById(String assignmentId);

    int saveFirstPageInfoByAssignmentId(String title, String details, Integer cata3val, String assignmentId);

    int saveIncludeFileUrl(String url, String originalFilename, String extName, String assignmentId, BigDecimal sizeBig);

    int saveIncludeFileUrl1(String url, String originalFilename, String extName, String assignmentId, String testId, BigDecimal sizeBig);

    int saveTouBiaoWithoutCeshiMoney(BigDecimal yusuanmoney, String closingDate, String assignmentId);

    int saveCeShiTi(String ceshititle, String describe, BigDecimal moneynum, String assignmentId);

    int saveTouBiaoWithCeshiMoney(BigDecimal yusuanmoney, String closingDate, String assignmentId);

    int savePriceRangeWithoutCeShiTi(Integer yusuanvalmin, Integer yusuanvalmax, String closingDate, String assignmentId);

    int savePriceRangeWithCeShiTi(Integer yusuanvalmin, Integer yusuanvalmax, String closingDate, String assignmentId);

    int saveSingleReward(String closingDate, BigDecimal renwumoney, String assignmentId);

    int saveMoreRewardDateAndMoney(String closingDate, BigDecimal renwumoney, String assignmentId);

    int saveMoreRewardWhenNumEqualsTwo(BigDecimal firstprizemoney, BigDecimal zhanbi, BigDecimal secondprizemoney, String assignmentId);

    int saveMoreRewardWhenNumGreaterThanTwo(Integer thirdprizenum, String assignmentId, Integer secondprizenum, Integer peoplenum, BigDecimal firstprizemoney, BigDecimal zhanbi, BigDecimal secondprizemoney, BigDecimal thirdprizemoney);

    int saveGaoJian(BigDecimal gaojianSinglePrice, Integer gaojiannum, String assignmentId);

    int saveGaoJianDateAndMoney(String closingDate, BigDecimal renwumoney, String assignmentId);

    int updateTop(String assignmentId, Integer top);

    int updateUrgent(String assignmentId, Integer urgent);

    int updateAllHide(String assignmentId, Integer allHide);

    int saveTuoGuanMoney(BigDecimal money, String assignmentId);

    int saveTopMoney(BigDecimal money, String assignmentId);

    int saveUrgentMoney(BigDecimal money, String assignmentId);

    int saveHideMoney(BigDecimal money, String assignmentId);

    int saveTotalMoney(BigDecimal money, String assignmentId);

    int makeSettlementNull(String assignmentId);

    List<IncludefileUrl> getReferenceFileWithTestId(String assignmentId, String testId);

    List<IncludefileUrl> getReferenceFileWithoutTestId(String assignmentId);

    Settlement getSettlementInfoByAssId(String assignmentId);

    Map<String, BigDecimal> getSettlementByAssIdToMap(Integer assignmentId);

    int saveRemark(String remark, String assignmentId);

    TestQuestion getTestQuestionByAssignmentId(String assignmentId);

    RewardMore getRewareMoreByAssignmentId(String assignmentId);

    int catAndUpdateTaskStatus(String assignmentId);

    void AddBrowsePeopleNumAssId(String needToAddBrowsePeopleNumAssId);

    int saveDifferTime(String assignmentId);

    List<Assignment> getAllAssignmentInfo();

    AssignDifferTimeHour getAssignDifferTimeHourByAssignmentId(Integer id);

    int removeFile(String filename, String assignmentId);

    int removeFile1(String filename, String assignmentId, String testId);

    int getManuScriptId(String assignmentId, Integer uId);

    int saveTouBiaoModeDetails(String manuscriptId, Integer memberId, Integer assignmentId, Integer workPeriod, BigDecimal projectQuotation, String editor2html, Integer checkStatus, BigDecimal ensuremoneyinput);

    int saveCeShiTouBiaoModeDetails(String manuscriptId, Integer memberId, Integer assignmentId, Integer workPeriod, BigDecimal projectQuotation, String editor2html, Integer checkStatus, BigDecimal ensuremoneyinput, String editor3html);

    int savemanuscriptIdUrl(String url, String originalFilename, String extName, String assignmentId, BigDecimal sizeBig, String manuscriptId);

    int removeFileBymanuscriptId(String filename, String manuscriptId);

    int saveSingleXuanShangModeDetails(String manuscriptId, Integer memberId, String assignmentId, Integer checkStatus, String editor4html);

    int getTaskDetailsCountByAssignmentId(String assignmentId);

    int savemanuscriptIdUrlGaoJian(Integer sign, String url, String originalFilename, String extName, String assignmentId, BigDecimal sizeBig, String manuscriptId);

    int removeFileBymanuscriptIdGaoJian(Integer sign, String filename, String manuscriptId);

    int saveSingleXuanShangModeDetails1(String manuscriptId, Integer memberId, String assignmentId, Integer checkStatus);

    int saveSingleXuanShangModeDetails2(String manuscriptId, Integer memberId, String assignmentId, Integer checkStatus, String editor4html);

    List<TbManuscriptUrl> getUrlByManuscriptId(String manuscriptId);

    int doIsSelf(String assignmentId, Integer memberId);

    List<TbJoinTaskDetails> getJoinTaskDetailByAssignment(String assignmentId);

    List<TaskDetailsAndUserInfo> transTaskDetailsToVO(List<TbJoinTaskDetails> tbJoinTaskDetailsList, Integer memberId);

    TbJoinTaskDetails getJoinTaskDetailByAssignmentAndUId(String assignmentId, Integer memberId);

    int updateBianHao(Integer bianhao);

    int getIsCatByBianHao(Integer bianhao);

    int saveorder(BigDecimal totalMoney, String assignmentId, String orderCodeSn);

    Taskorder getTaskPriceFromTaskOrderByOrderSn(String orderSn);

    void setIsBiddingByManuScriptId(String manuscriptId);

    int setAssignmentScheduleStatus(String assignmentId);

    int IsBiddingByManuScriptIdAndScheduleStatus(String assignmentId, String manuscriptId);

    int updateXuangaoTime(String assignmentId);

    int setAssignmentScheduleStatus1(String assignmentId);

    int updateFirstStatus(String assignmentId);

    int setTaskStatus(String manuscriptId);

    int updateFirstStatus2(String assignmentId);

    int setTaskStatus2(String manuscriptId);

    int updateFirstStatus3(String assignmentId);

    int setTaskStatus3(String manuscriptId);

    int updateGaoJianStatus(String assignmentId);

    int updateUrlStatus(String manuscriptId, String gaojianSign);

    int updateJijianHegeNum(String manuscriptId, String gaojianSign);

    int IsBiddingByManuScriptIdAndScheduleStatus2(String assignmentId, String manuscriptId);

    int savemanuscriptIdUrlss(String url, String originalFilename, String extName, String assignmentId, BigDecimal sizeBig, String manuscriptId);

    int removeFileBymanuscriptIdss(String filename, String manuscriptId);

    List<Assignment> getAssignmentInfoByMemberId(Integer memberId);

    List<PersonPublishTaskVO> transAssignmentToPersonPublishTaskVO(List<Assignment> assignmentList);

    int clickcollectToDB(Integer assignmentId, Integer memberId);

    int canclecollectToDB(Integer assignmentId, Integer memberId);

    int getTaskCollect(Integer assignmentId, Integer memberId);

    List<TaskCollect> getTaskCollectByMemberId(Integer memberId);

    List<TaskCollectVO> transTaskCollectToVO(List<TaskCollect> taskCollectList);
}
