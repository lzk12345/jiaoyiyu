package com.jiaoyiyu.back.coreservice.Impl;

import com.jiaoyiyu.back.bean.*;
import com.jiaoyiyu.back.bean.vo.PersonPublishTaskVO;
import com.jiaoyiyu.back.bean.vo.TaskCollectVO;
import com.jiaoyiyu.back.bean.vo.TaskDetailsAndUserInfo;
import com.jiaoyiyu.back.coredao.*;
import com.jiaoyiyu.back.coreservice.PublishTaskService;
import com.jiaoyiyu.util.IdWorker;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * PublicTaskServiceImpl class
 *
 * @author maochaoying
 * @date 2019/11/7
 */
@Service
public class PublicTaskServiceImpl implements PublishTaskService {

    @Autowired
    AssignmentMapper assignmentMapper;
    @Autowired
    RewardMoreMapper rewardMoreMapper;
    @Autowired
    IncludefileUrlMapper includefileUrlMapper;
    @Autowired
    IdWorker idWorker;
    @Autowired
    PublishUserMapper publishUserMapper;
    @Autowired
    TestQuestionMapper testQuestionMapper;
    @Autowired
    SettlementMapper settlementMapper;
    @Autowired
    AssignDifferTimeHourMapper assignDifferTimeHourMapper;
    @Autowired
    TbJoinTaskDetailsMapper tbJoinTaskDetailsMapper;
    @Autowired
    TbManuscriptUrlMapper tbManuscriptUrlMapper;
    @Autowired
    TaskCollectMapper taskCollectMapper;
    @Autowired
    TaskorderMapper taskorderMapper;

    @Override
    public User getUserInfoByMemberId(Integer memberId) {
        User user = new User();
        user.setId(memberId);
        User user1 = publishUserMapper.selectOne(user);
        return user1;
    }

    @Override
    public int saveIneedAndPhoneNumToDB(String ineed, String phonenum, Integer memberId1) {
        User user = new User();
        user.setPhone(phonenum);
        user.setId(memberId1);
        publishUserMapper.updateByPrimaryKeySelective(user);
        Assignment assignment = new Assignment();
        assignment.setId(Integer.parseInt(RandomStringUtils.randomNumeric(8)));
        assignment.setUid(memberId1);
        assignment.setNeeds(ineed);
        int i1 = assignmentMapper.insertSelective(assignment);
        Integer id = assignment.getId();
        return id;
    }

    @Override
    public int saveIneedAndPhoneNumToDBWithOutId(String ineed, String phonenum) {
        User user = new User();
        user.setPhone(phonenum);
        int i1 = 0;
        User user1 = publishUserMapper.selectOne(user);
        if (user1 != null) {
            user.setId(user1.getId());
            publishUserMapper.updateByPrimaryKeySelective(user);
            Assignment assignment = new Assignment();
            assignment.setId(Integer.parseInt(RandomStringUtils.randomNumeric(8)));
            assignment.setUid(user1.getId());
            assignment.setNeeds(ineed);
            assignmentMapper.insertSelective(assignment);
            i1 = assignment.getId();
        }
        return i1;
    }

    @Override
    public User getUserInfoByPhoneNum(String phonenum) {
        User user = new User();
        user.setPhone(phonenum);
        User user1 = publishUserMapper.selectOne(user);
        return user1;
    }

    @Override
    public Assignment getAssignmentInfoById(String assignmentId) {
        Assignment assignment = new Assignment();
        if (assignment != null || !assignment.equals("")) {
            assignment.setId(Integer.parseInt(assignmentId));
            Assignment assignment1 = assignmentMapper.selectOne(assignment);
            return assignment1;
        }
       return null;
    }


    /**
     * 根据assignmentId 存信息
     *
     * @param title
     * @param details
     * @param cata3val     其实是catalog2 id  因为前期使用了三级分类 后期改为了二级分类  参数名没有改变
     * @param assignmentId
     * @return
     */
    @Override
    public int saveFirstPageInfoByAssignmentId(String title, String details, Integer cata3val, String assignmentId) {
        Assignment assignment = new Assignment();
        assignment.setCategoryid(cata3val);
        assignment.setDetails(details);
        assignment.setTitle(title);
        Example example = new Example(Assignment.class);
        example.createCriteria().andEqualTo("id", Integer.parseInt(assignmentId));
        int res = assignmentMapper.updateByExampleSelective(assignment, example);
        return res;
    }

    @Override
    public int saveIncludeFileUrl(String url, String originalFilename, String extName, String assignmentId, BigDecimal sizeBig) {
        IncludefileUrl includefileUrl = new IncludefileUrl();
        includefileUrl.setAssignmentId(Integer.parseInt(assignmentId));
        includefileUrl.setStatus(0);
        includefileUrl.setUrl(url);
        includefileUrl.setTestId(-1);
        includefileUrl.setFileSize(sizeBig);
        includefileUrl.setExtendName(extName);
        includefileUrl.setFileName(originalFilename);
        int i = includefileUrlMapper.insertSelective(includefileUrl);
        return i;
    }

    @Override
    public int saveIncludeFileUrl1(String url, String originalFilename, String extName, String assignmentId, String testId, BigDecimal sizeBig) {
        IncludefileUrl includefileUrl = new IncludefileUrl();
        includefileUrl.setAssignmentId(Integer.parseInt(assignmentId));
        includefileUrl.setStatus(0);
        includefileUrl.setUrl(url);
        includefileUrl.setFileSize(sizeBig);
        includefileUrl.setExtendName(extName);
        includefileUrl.setFileName(originalFilename);
        includefileUrl.setTestId(Integer.parseInt(testId.substring(0, 6)));
        int i = includefileUrlMapper.insertSelective(includefileUrl);
        return i;
    }


    /**
     * 获取现在时间
     *
     * @return返回字符串格式 yyyy-MM-dd HH:mm:ss
     */
    public String getStringDate() {
        Date currentTime = new Date(System.currentTimeMillis());
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }

    /**
     * 投标模式没有测试题（具体预算）
     *
     * @param yusuanmoney
     * @param closingDate
     * @param assignmentId
     * @return
     */
    @Override
    public int saveTouBiaoWithoutCeshiMoney(BigDecimal yusuanmoney, String closingDate, String assignmentId) {
        closingDate = closingDate + " 00:00:00";
        // 保存到数据库
        Assignment assignment = new Assignment();
        // 投标固定金额
        assignment.setToubiaoPrice(yusuanmoney);
        // 时间使用了String存储 感觉后面还需要改动 现在先放这里
        // TODO
        assignment.setClosingDate(closingDate);
        assignment.setPubtime(getStringDate());
        assignment.setEndtime(closingDate);
        // 有具体金额
        assignment.setToubiaoType(0);
        // 0 表示没有测试题
        assignment.setTestStatus((byte) 0);
        // 0 表示投标模式
        assignment.setTasktype(0);
        // 0 表示任务保存状态 未发布

        Example example = new Example(Assignment.class);
        example.createCriteria().andEqualTo("id", Integer.parseInt(assignmentId));
        int i = assignmentMapper.updateByExampleSelective(assignment, example);
        return i;
    }

    /**
     * 测试题表的存储
     *
     * @param ceshititle
     * @param describe
     * @param moneynum
     * @param assignmentId
     * @return
     */
    @Override
    public int saveCeShiTi(String ceshititle, String describe, BigDecimal moneynum, String assignmentId) {

        TestQuestion testQuestion1 = new TestQuestion();
        testQuestion1.setAssignmentId(Integer.parseInt(assignmentId));
        TestQuestion testQuestion2 = testQuestionMapper.selectOne(testQuestion1);
        int res = 0;
        if (testQuestion2 == null) {
            TestQuestion testQuestion = new TestQuestion();
            long l = idWorker.nextId();
            String s = l + "";
            testQuestion.setId(Integer.parseInt(s.substring(0, 7)));
            testQuestion.setAssignmentId(Integer.parseInt(assignmentId));
            testQuestion.setTitle(ceshititle);
            testQuestion.setDescribeDetails(describe);
            testQuestion.setMoneyNum(moneynum);
            res = testQuestionMapper.insertSelective(testQuestion);
        } else {
            TestQuestion testQuestion = new TestQuestion();
            testQuestion.setTitle(ceshititle);
            testQuestion.setDescribeDetails(describe);
            testQuestion.setMoneyNum(moneynum);
            Example example = new Example(TestQuestion.class);
            example.createCriteria().andEqualTo("assignmentId", Integer.parseInt(assignmentId));
            res = testQuestionMapper.updateByExampleSelective(testQuestion, example);
        }
        return res;
    }

    /**
     * 有测试题的投标模式assignment表的存储（具体预算）
     *
     * @param yusuanmoney
     * @param closingDate
     * @param assignmentId
     * @return
     */
    @Override
    public int saveTouBiaoWithCeshiMoney(BigDecimal yusuanmoney, String closingDate, String assignmentId) {
        closingDate = closingDate + " 00:00:00";
        Assignment assignment = new Assignment();
        assignment.setToubiaoPrice(yusuanmoney);
        assignment.setToubiaoType(0);
        assignment.setTestStatus((byte) 1);
        assignment.setTasktype(0);
        assignment.setClosingDate(closingDate);
        assignment.setPubtime(getStringDate());
        assignment.setEndtime(closingDate);
        Example example = new Example(Assignment.class);
        example.createCriteria().andEqualTo("id", Integer.parseInt(assignmentId));
        int i = assignmentMapper.updateByExampleSelective(assignment, example);
        return i;
    }

    /**
     * 存入区间范围 没有测试题
     *
     * @param yusuanvalmin
     * @param yusuanvalmax
     * @param closingDate
     * @param assignmentId
     * @return
     */
    @Override
    public int savePriceRangeWithoutCeShiTi(Integer yusuanvalmin, Integer yusuanvalmax, String closingDate, String assignmentId) {
        closingDate = closingDate + " 00:00:00";
        Assignment assignment = new Assignment();
        assignment.setMinMoney(yusuanvalmin);
        assignment.setMaxMoney(yusuanvalmax);
        // 1 表示区间
        assignment.setToubiaoType(1);
        assignment.setTestStatus((byte) 0);
        assignment.setTasktype(0);
        assignment.setClosingDate(closingDate);
        assignment.setPubtime(getStringDate());
        assignment.setEndtime(closingDate);
        Example example = new Example(Assignment.class);
        example.createCriteria().andEqualTo("id", Integer.parseInt(assignmentId));
        int i = assignmentMapper.updateByExampleSelective(assignment, example);
        return i;
    }

    /**
     * 存入区间范围 有測試題
     *
     * @param yusuanvalmin
     * @param yusuanvalmax
     * @param closingDate
     * @param assignmentId
     * @return
     */
    @Override
    public int savePriceRangeWithCeShiTi(Integer yusuanvalmin, Integer yusuanvalmax, String closingDate, String assignmentId) {
        closingDate = closingDate + " 00:00:00";
        Assignment assignment = new Assignment();
        assignment.setMinMoney(yusuanvalmin);
        assignment.setMaxMoney(yusuanvalmax);
        assignment.setToubiaoType(1);
        assignment.setTestStatus((byte) 1);
        assignment.setTasktype(0);
        assignment.setClosingDate(closingDate);
        assignment.setPubtime(getStringDate());
        assignment.setEndtime(closingDate);
        Example example = new Example(Assignment.class);
        example.createCriteria().andEqualTo("id", Integer.parseInt(assignmentId));
        int i = assignmentMapper.updateByExampleSelective(assignment, example);
        return i;
    }

    /**
     * 单人悬赏
     *
     * @param closingDate
     * @param renwumoney
     * @param assignmentId
     * @return
     */
    @Override
    public int saveSingleReward(String closingDate, BigDecimal renwumoney, String assignmentId) {
        closingDate = closingDate + " 00:00:00";
        Assignment assignment = new Assignment();
        assignment.setSingleStatus(0);
        assignment.setXuanshangPrice(renwumoney);
        assignment.setXuanshangType(0);
        assignment.setTasktype(1);
        assignment.setClosingDate(closingDate);
        assignment.setPubtime(getStringDate());
        assignment.setEndtime(closingDate);
        Example example = new Example(Assignment.class);
        example.createCriteria().andEqualTo("id", Integer.parseInt(assignmentId));
        int i = assignmentMapper.updateByExampleSelective(assignment, example);
        return i;
    }

    /**
     * 多人悬赏存入任务金额和时间   控制数据库中存的方式字段
     *
     * @param closingDate
     * @param renwumoney
     * @param assignmentId
     * @return
     */
    @Override
    public int saveMoreRewardDateAndMoney(String closingDate, BigDecimal renwumoney, String assignmentId) {
        closingDate = closingDate + " 00:00:00";
        Assignment assignment = new Assignment();
        assignment.setXuanshangPrice(renwumoney);
        assignment.setXuanshangType(1);
        assignment.setTasktype(1);
        assignment.setClosingDate(closingDate);
        assignment.setPubtime(getStringDate());
        assignment.setEndtime(closingDate);
        Example example = new Example(Assignment.class);
        example.createCriteria().andEqualTo("id", Integer.parseInt(assignmentId));
        int i = assignmentMapper.updateByExampleSelective(assignment, example);
        return i;
    }

    /**
     * 悬赏多人模式（两人）数据的保存
     *
     * @param firstprizemoney
     * @param zhanbi
     * @param secondprizemoney
     * @param assignmentId
     * @return
     */
    @Override
    public int saveMoreRewardWhenNumEqualsTwo(BigDecimal firstprizemoney, BigDecimal zhanbi, BigDecimal secondprizemoney, String assignmentId) {
        RewardMore rewardMore = new RewardMore();
        long l = idWorker.nextId();
        String s = l + "";
        rewardMore.setId(Integer.parseInt(s.substring(0, 7)));
        rewardMore.setAssignmentId(Integer.parseInt(assignmentId));
        rewardMore.setPeopleNum(2);
        rewardMore.setFirstPrize(firstprizemoney);
        rewardMore.setSecondPrize(secondprizemoney);
        rewardMore.setFirstPrizeNum(1);
        rewardMore.setSecondPrizeNum(1);
        rewardMore.setThirdPrizeNum(0);
        rewardMore.setZhanbi(zhanbi);
        rewardMore.setFirstStatus(1);
        rewardMore.setSecondStatus(1);
        rewardMore.setThirdStatus(0);
        int i = rewardMoreMapper.insertSelective(rewardMore);
        return i;
    }

    /**
     * 悬赏多人模式（3，4，5人）数据的保存
     *
     * @param thirdprizenum    三等奖人数
     * @param assignmentId     任务id
     * @param secondprizenum   二等奖人数
     * @param peoplenum        总悬赏人数
     * @param firstprizemoney  一等奖金
     * @param zhanbi           占比
     * @param secondprizemoney 二等奖金
     * @param thirdprizemoney  三等奖金
     * @return
     */
    @Override
    public int saveMoreRewardWhenNumGreaterThanTwo(Integer thirdprizenum, String assignmentId, Integer secondprizenum, Integer peoplenum, BigDecimal firstprizemoney, BigDecimal zhanbi, BigDecimal secondprizemoney, BigDecimal thirdprizemoney) {
        RewardMore rewardMore = new RewardMore();
        long l = idWorker.nextId();
        String s = l + "";
        rewardMore.setId(Integer.parseInt(s.substring(0, 7)));
        rewardMore.setAssignmentId(Integer.parseInt(assignmentId));
        rewardMore.setPeopleNum(peoplenum);
        rewardMore.setFirstPrize(firstprizemoney);
        rewardMore.setSecondPrize(secondprizemoney);
        rewardMore.setThirdPrize(thirdprizemoney);
        rewardMore.setFirstPrizeNum(1);
        rewardMore.setSecondPrizeNum(secondprizenum);
        rewardMore.setThirdPrizeNum(thirdprizenum);
        rewardMore.setZhanbi(zhanbi);
        rewardMore.setFirstStatus(1);
        rewardMore.setSecondStatus(secondprizenum);
        rewardMore.setThirdStatus(thirdprizenum);
        int i = rewardMoreMapper.insertSelective(rewardMore);
        return i;
    }

    /**
     * 保存稿件数据
     *
     * @param gaojianSinglePrice
     * @param gaojiannum
     * @param assignmentId
     * @return
     */
    @Override
    public int saveGaoJian(BigDecimal gaojianSinglePrice, Integer gaojiannum, String assignmentId) {
        Assignment assignment = new Assignment();
        assignment.setGaojianNum(gaojiannum);
        assignment.setGaojianStatus(gaojiannum);
        assignment.setGaojianSinglePrice(gaojianSinglePrice);
        assignment.setXuanshangType(2);
        assignment.setTasktype(1);
        Example example = new Example(Assignment.class);
        example.createCriteria().andEqualTo("id", Integer.parseInt(assignmentId));
        int i = assignmentMapper.updateByExampleSelective(assignment, example);
        return i;
    }

    /**
     * @param closingDate
     * @param renwumoney
     * @param assignmentId
     * @return
     */
    @Override
    public int saveGaoJianDateAndMoney(String closingDate, BigDecimal renwumoney, String assignmentId) {
        closingDate = closingDate + " 00:00:00";
        Assignment assignment = new Assignment();
        assignment.setXuanshangPrice(renwumoney);
        assignment.setXuanshangType(2);
        assignment.setTasktype(1);
        assignment.setClosingDate(closingDate);
        assignment.setPubtime(getStringDate());
        assignment.setEndtime(closingDate);
        Example example = new Example(Assignment.class);
        example.createCriteria().andEqualTo("id", Integer.parseInt(assignmentId));
        int i = assignmentMapper.updateByExampleSelective(assignment, example);
        return i;
    }

    /**
     * 更新置顶道具使用状态
     *
     * @param assignmentId
     * @param top
     * @return
     */
    @Override
    public int updateTop(String assignmentId, Integer top) {
        Assignment assignment = new Assignment();
        assignment.setTop((byte) top.intValue());
        Example example = new Example(Assignment.class);
        example.createCriteria().andEqualTo("id", Integer.parseInt(assignmentId));
        int i = assignmentMapper.updateByExampleSelective(assignment, example);
        return i;
    }

    /**
     * 更新加急道具使用状态
     *
     * @param assignmentId
     * @param urgent
     * @return
     */
    @Override
    public int updateUrgent(String assignmentId, Integer urgent) {
        Assignment assignment = new Assignment();
        assignment.setUrgent((byte) urgent.intValue());
        Example example = new Example(Assignment.class);
        example.createCriteria().andEqualTo("id", Integer.parseInt(assignmentId));
        int i = assignmentMapper.updateByExampleSelective(assignment, example);
        return i;
    }

    /**
     * @param assignmentId
     * @param allHide
     * @return
     */
    @Override
    public int updateAllHide(String assignmentId, Integer allHide) {
        Assignment assignment = new Assignment();
        assignment.setAllhidden((byte) allHide.intValue());
        Example example = new Example(Assignment.class);
        example.createCriteria().andEqualTo("id", Integer.parseInt(assignmentId));
        int i = assignmentMapper.updateByExampleSelective(assignment, example);
        return i;
    }

    /**
     * 保存托管金额
     *
     * @param money
     * @param assignmentId
     * @return
     */
    @Override
    public int saveTuoGuanMoney(BigDecimal money, String assignmentId) {
        Settlement settlement = new Settlement();
        settlement.setHostingReward(money);
        Example example = new Example(Settlement.class);
        example.createCriteria().andEqualTo("assignmentId", Integer.parseInt(assignmentId));
        int i = settlementMapper.updateByExampleSelective(settlement, example);
        return i;
    }

    /**
     * 保存置顶服务金额
     *
     * @param money
     * @param assignmentId
     * @return
     */
    @Override
    public int saveTopMoney(BigDecimal money, String assignmentId) {
        Settlement settlement = new Settlement();
        settlement.setTopService(money);
        Example example = new Example(Settlement.class);
        example.createCriteria().andEqualTo("assignmentId", Integer.parseInt(assignmentId));
        int i = settlementMapper.updateByExampleSelective(settlement, example);
        return i;
    }

    /**
     * 保存加急服务金额
     *
     * @param money
     * @param assignmentId
     * @return
     */
    @Override
    public int saveUrgentMoney(BigDecimal money, String assignmentId) {
        Settlement settlement = new Settlement();
        settlement.setUrgentService(money);
        Example example = new Example(Settlement.class);
        example.createCriteria().andEqualTo("assignmentId", Integer.parseInt(assignmentId));
        int i = settlementMapper.updateByExampleSelective(settlement, example);
        return i;
    }

    /**
     * 保存全部隐藏服务金额
     *
     * @param money
     * @param assignmentId
     * @return
     */
    @Override
    public int saveHideMoney(BigDecimal money, String assignmentId) {
        Settlement settlement = new Settlement();
        settlement.setAllhideService(money);
        Example example = new Example(Settlement.class);
        example.createCriteria().andEqualTo("assignmentId", Integer.parseInt(assignmentId));
        int i = settlementMapper.updateByExampleSelective(settlement, example);
        return i;
    }

    /**
     * 保存总金额
     *
     * @param money
     * @param assignmentId
     * @return
     */
    @Override
    public int saveTotalMoney(BigDecimal money, String assignmentId) {
        Settlement settlement = new Settlement();
        settlement.setTotalAmount(money);
        Example example = new Example(Settlement.class);
        example.createCriteria().andEqualTo("assignmentId", Integer.parseInt(assignmentId));
        int i = settlementMapper.updateByExampleSelective(settlement, example);
        return i;
    }

    /**
     * 创建一条assignmentId空记录
     *
     * @param assignmentId
     * @return
     */
    @Override
    public int makeSettlementNull(String assignmentId) {

        Settlement settlement1 = new Settlement();
        settlement1.setAssignmentId(Integer.parseInt(assignmentId));
        Settlement settlement2 = settlementMapper.selectOne(settlement1);
        int i = 0;
        if (settlement2 == null) {
            Settlement settlement = new Settlement();
            long l = idWorker.nextId();
            String s = l + "";
            settlement.setId(Integer.parseInt(s.substring(0, 7)));
            settlement.setAssignmentId(Integer.parseInt(assignmentId));
            i = settlementMapper.insertSelective(settlement);
        } else {
            // 什么也不干
        }
        return i;
    }

    /**
     * 从数据库中查询该测试题的文件相关信息
     *
     * @param assignmentId 任务id
     * @param testId       测试题id
     * @return
     */
    @Override
    public List<IncludefileUrl> getReferenceFileWithTestId(String assignmentId, String testId) {
        IncludefileUrl includefileUrl = new IncludefileUrl();
        includefileUrl.setAssignmentId(Integer.parseInt(assignmentId));
        List<IncludefileUrl> res = null;
        List<String> testIds = null;
        if (StringUtils.isBlank(testId)) {
            testIds = includefileUrlMapper.selectTestIdExistsByAssignmentId(Integer.parseInt(assignmentId));
        }
        if (testIds != null) {
            for (String id : testIds) {
                if (!id.equals("-1")) {
                    includefileUrl.setTestId(Integer.parseInt(testId.substring(0, 6)));
                    includefileUrl.setStatus(0);
                    res = includefileUrlMapper.select(includefileUrl);
                }
            }
        }
        return res;
    }

    /**
     * 从数据库中查询参考文件的相关信息(此时任务id为null)
     *
     * @param assignmentId 任务id
     * @return
     */
    @Override
    public List<IncludefileUrl> getReferenceFileWithoutTestId(String assignmentId) {
        IncludefileUrl includefileUrl = new IncludefileUrl();
        includefileUrl.setAssignmentId(Integer.parseInt(assignmentId));
        includefileUrl.setTestId(-1);
        includefileUrl.setStatus(0);
        List<IncludefileUrl> res = includefileUrlMapper.select(includefileUrl);
        return res;
    }

    /**
     * 通过assignmentId 获得Settlement表的信息
     *
     * @param assignmentId
     * @return
     */
    @Override
    public Settlement getSettlementInfoByAssId(String assignmentId) {
        Settlement settlement = new Settlement();
        settlement.setAssignmentId(Integer.parseInt(assignmentId));
        Settlement settlement1 = settlementMapper.selectOne(settlement);
        return settlement1;
    }

    /**
     * 根据assignmentId来获得settlement对象并封装为map
     *
     * @param assignmentId
     * @return
     */
    @Override
    public Map<String, BigDecimal> getSettlementByAssIdToMap(Integer assignmentId) {
        Map<String, BigDecimal> map = new HashMap<>();
        Settlement settlement = new Settlement();
        settlement.setAssignmentId(assignmentId);
        Settlement settlement1 = settlementMapper.selectOne(settlement);
        if (settlement1 != null) {
            BigDecimal urgentService = settlement1.getUrgentService();
            BigDecimal totalAmount = settlement1.getTotalAmount();
            BigDecimal topService = settlement1.getTopService();
            BigDecimal hostingReward = settlement1.getHostingReward();
            BigDecimal allhideService = settlement1.getAllhideService();
            // -2 表示成功
            map.put("status", new BigDecimal(-2));
            map.put("urgentService", urgentService);
            map.put("totalAmount", totalAmount);
            map.put("topService", topService);
            map.put("hostingReward", hostingReward);
            map.put("allhideService", allhideService);
        } else {
            // -3 表示失败
            map.put("status", new BigDecimal(-3));
        }
        return map;
    }

    /**
     * 保存留言
     *
     * @param remark
     * @param assignmentId
     * @return
     */
    @Override
    public int saveRemark(String remark, String assignmentId) {
        // 首先先查询 如果查到了则说明已经留言，则不让留言
        int res = -3;
        Assignment assignment1 = new Assignment();
        assignment1.setId(Integer.parseInt(assignmentId));
        assignment1.setRemarkStatus(1);
        Assignment assignment2 = assignmentMapper.selectOne(assignment1);
        if (assignment2 == null) {
            Assignment assignment = new Assignment();
            assignment.setRemarkStatus(1);
            assignment.setRemark(remark);
            Example example = new Example(Assignment.class);
            example.createCriteria().andEqualTo("id", Integer.parseInt(assignmentId));
            res = assignmentMapper.updateByExampleSelective(assignment, example);
        } else {
            res = 5;
        }
        return res;
    }

    @Override
    public TestQuestion getTestQuestionByAssignmentId(String assignmentId) {
        TestQuestion testQuestion = new TestQuestion();
        testQuestion.setAssignmentId(Integer.parseInt(assignmentId));
        TestQuestion testQuestion1 = testQuestionMapper.selectOne(testQuestion);
        return testQuestion1;
    }

    @Override
    public RewardMore getRewareMoreByAssignmentId(String assignmentId) {
        RewardMore rewardMore = new RewardMore();
        rewardMore.setAssignmentId(Integer.parseInt(assignmentId));
        RewardMore rewardMore1 = rewardMoreMapper.selectOne(rewardMore);
        return rewardMore1;
    }

    @Override
    public int catAndUpdateTaskStatus(String assignmentId) {
        Assignment assignment = new Assignment();
        assignment.setId(Integer.parseInt(assignmentId));
        Assignment assignment1 = assignmentMapper.selectOne(assignment);
        Integer taskStatus = assignment1.getTaskStatus();
        if (taskStatus == 0) {
            // 还未发布  更改其状态
            Assignment assignment2 = new Assignment();
            assignment2.setId(Integer.parseInt(assignmentId));
            assignment2.setTaskStatus(1);
            Example example = new Example(Assignment.class);
            example.createCriteria().andEqualTo("id", Integer.parseInt(assignmentId));
            int res = assignmentMapper.updateByExampleSelective(assignment2, example);
            if (res == 1) {
                // 更新成功
                return 1;
            }
        }
        if (taskStatus == 1) {
            // 已经发布 但未支付
            return 2;
        }
        return 0;
    }

    /**
     * 访问页面时让用户的浏览量加一
     *
     * @param needToAddBrowsePeopleNumAssId assignmentId
     */
    @Override
    public void AddBrowsePeopleNumAssId(String needToAddBrowsePeopleNumAssId) {
        if (needToAddBrowsePeopleNumAssId != null && needToAddBrowsePeopleNumAssId != "") {
            // 首先使用该assignmentId查询Assignment对象
            Assignment assignment2 = new Assignment();
            assignment2.setId(Integer.parseInt(needToAddBrowsePeopleNumAssId));
            Assignment assignment1 = assignmentMapper.selectOne(assignment2);
            if (assignment1 != null) {
                Integer browsePeopleNum = assignment1.getBrowsePeopleNum();
                Assignment assignment = new Assignment();
                assignment.setBrowsePeopleNum(browsePeopleNum + 1);
                Example example = new Example(Assignment.class);
                example.createCriteria().andEqualTo("id", Integer.parseInt(needToAddBrowsePeopleNumAssId));
                assignmentMapper.updateByExampleSelective(assignment, example);
            }
        }
    }

    /**
     * @param assignmentId
     * @return
     */
    @Override
    public int saveDifferTime(String assignmentId) {
        return assignDifferTimeHourMapper.saveDifferTime(Integer.parseInt(assignmentId));
    }

    @Override
    public List<Assignment> getAllAssignmentInfo() {
        List<Assignment> assignments = assignmentMapper.selectAll();
        return assignments;
    }

    @Override
    public AssignDifferTimeHour getAssignDifferTimeHourByAssignmentId(Integer id) {
        AssignDifferTimeHour assignDifferTimeHour = new AssignDifferTimeHour();
        assignDifferTimeHour.setAssignment(id);
        AssignDifferTimeHour assignDifferTimeHour1 = assignDifferTimeHourMapper.selectOne(assignDifferTimeHour);
        return assignDifferTimeHour1;
    }

    /**
     * 移除附件
     *
     * @param filename
     * @param assignmentId
     * @return
     */
    @Override
    public int removeFile(String filename, String assignmentId) {
        IncludefileUrl includefileUrl = new IncludefileUrl();
        includefileUrl.setFileName(filename);
        includefileUrl.setAssignmentId(Integer.parseInt(assignmentId));
        int delete = includefileUrlMapper.delete(includefileUrl);
        return delete;
    }

    /**
     * @param filename
     * @param assignmentId
     * @param testId
     * @return
     */
    @Override
    public int removeFile1(String filename, String assignmentId, String testId) {
        IncludefileUrl includefileUrl = new IncludefileUrl();
        includefileUrl.setFileName(filename);
        includefileUrl.setAssignmentId(Integer.parseInt(assignmentId));
        includefileUrl.setTestId(Integer.parseInt(testId.substring(0, 6)));
        int delete = includefileUrlMapper.delete(includefileUrl);
        return delete;
    }

    /**
     * 创建新的稿件记录 并返回稿件id
     *
     * @return
     */
    @Override
    public int getManuScriptId(String assignmentId, Integer uId) {
        TbJoinTaskDetails tbJoinTaskDetails1 = new TbJoinTaskDetails();
        tbJoinTaskDetails1.setuId(uId);
        tbJoinTaskDetails1.setAssignmentId(Integer.parseInt(assignmentId));
        TbJoinTaskDetails tbJoinTaskDetails2 = tbJoinTaskDetailsMapper.selectOne(tbJoinTaskDetails1);
        Integer manuScriptId = null;
        if (tbJoinTaskDetails2 == null) {
            TbJoinTaskDetails tbJoinTaskDetails = new TbJoinTaskDetails();
            long l = idWorker.nextId();
            String s = l + "";
            tbJoinTaskDetails.setId(Integer.parseInt(s.substring(0, 7)));
            tbJoinTaskDetails.setuId(uId);
            tbJoinTaskDetails.setAssignmentId(Integer.parseInt(assignmentId));
            int res = tbJoinTaskDetailsMapper.insertSelective(tbJoinTaskDetails);
            manuScriptId = tbJoinTaskDetails.getId();
        } else {
            manuScriptId = tbJoinTaskDetails2.getId();
        }
        return manuScriptId;
    }

    /**
     * 投标模式下稿件存入数据库
     *
     * @param memberId         用户id
     * @param assignmentId     任务id
     * @param workPeriod       工作周期
     * @param projectQuotation 项目报价
     * @param editor2html      报价说明
     * @param checkStatus      是否使用隐藏道具
     * @param ensuremoneyinput 保证金额
     * @return
     */
    @Override
    public int saveTouBiaoModeDetails(String manuscriptId, Integer memberId, Integer assignmentId, Integer workPeriod, BigDecimal projectQuotation, String editor2html, Integer checkStatus, BigDecimal ensuremoneyinput) {
        TbJoinTaskDetails tbJoinTaskDetails = new TbJoinTaskDetails();
        tbJoinTaskDetails.setQuoteDetails(editor2html);
        tbJoinTaskDetails.setEnsureMoney(ensuremoneyinput);
        tbJoinTaskDetails.setTaskType(0);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tbJoinTaskDetails.setUploadTime(sdf.format(d));
        tbJoinTaskDetails.setIsHide(checkStatus);
        tbJoinTaskDetails.setWorkPeriod(workPeriod);
        tbJoinTaskDetails.setProjectQuotation(projectQuotation);
        Example example = new Example(TbJoinTaskDetails.class);
        example.createCriteria().andEqualTo("uId", memberId).andEqualTo("assignmentId", assignmentId).andEqualTo("id", manuscriptId);
        int i = tbJoinTaskDetailsMapper.updateByExampleSelective(tbJoinTaskDetails, example);
        return i;
    }

    @Override
    public int saveCeShiTouBiaoModeDetails(String manuscriptId, Integer memberId, Integer assignmentId, Integer workPeriod, BigDecimal projectQuotation, String editor2html, Integer checkStatus, BigDecimal ensuremoneyinput, String editor3html) {
        TbJoinTaskDetails tbJoinTaskDetails = new TbJoinTaskDetails();
        tbJoinTaskDetails.setQuoteDetails(editor2html);
        tbJoinTaskDetails.setEnsureMoney(ensuremoneyinput);
        tbJoinTaskDetails.setCeshiDetails(editor3html);
        tbJoinTaskDetails.setTaskType(1);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tbJoinTaskDetails.setUploadTime(sdf.format(d));
        tbJoinTaskDetails.setIsHide(checkStatus);
        tbJoinTaskDetails.setWorkPeriod(workPeriod);
        tbJoinTaskDetails.setProjectQuotation(projectQuotation);
        Example example = new Example(TbJoinTaskDetails.class);
        example.createCriteria().andEqualTo("uId", memberId).andEqualTo("assignmentId", assignmentId).andEqualTo("id", manuscriptId);
        int i = tbJoinTaskDetailsMapper.updateByExampleSelective(tbJoinTaskDetails, example);
        return i;
    }

    @Override
    public int savemanuscriptIdUrl(String url, String originalFilename, String extName, String assignmentId, BigDecimal sizeBig, String manuscriptId) {
        TbManuscriptUrl tbManuscriptUrl = new TbManuscriptUrl();
        tbManuscriptUrl.setAssignmentId(Integer.parseInt(assignmentId));
        tbManuscriptUrl.setUrl(url);
        tbManuscriptUrl.setStatus(0);
        tbManuscriptUrl.setManuscriptId(Integer.parseInt(manuscriptId));
        tbManuscriptUrl.setFileName(originalFilename);
        tbManuscriptUrl.setExtendName(extName);
        tbManuscriptUrl.setFileSize(sizeBig);
        int i = tbManuscriptUrlMapper.insertSelective(tbManuscriptUrl);
        return i;
    }

    @Override
    public int removeFileBymanuscriptId(String filename, String manuscriptId) {
        TbManuscriptUrl tbManuscriptUrl = new TbManuscriptUrl();
        tbManuscriptUrl.setManuscriptId(Integer.parseInt(manuscriptId));
        tbManuscriptUrl.setFileName(filename);
        int delete = tbManuscriptUrlMapper.delete(tbManuscriptUrl);
        return delete;
    }

    @Override
    public int saveSingleXuanShangModeDetails(String manuscriptId, Integer memberId, String assignmentId, Integer checkStatus, String editor4html) {
        TbJoinTaskDetails tbJoinTaskDetails = new TbJoinTaskDetails();
        tbJoinTaskDetails.setQuoteDetails(editor4html);
        tbJoinTaskDetails.setTaskType(2);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tbJoinTaskDetails.setUploadTime(sdf.format(d));
        tbJoinTaskDetails.setIsHide(checkStatus);
        Example example = new Example(TbJoinTaskDetails.class);
        example.createCriteria().andEqualTo("uId", memberId).andEqualTo("assignmentId", assignmentId).andEqualTo("id", manuscriptId);
        int i = tbJoinTaskDetailsMapper.updateByExampleSelective(tbJoinTaskDetails, example);
        return i;
    }

    /**
     * 查询当前提交稿件数量
     *
     * @param assignmentId
     * @return
     */
    @Override
    public int getTaskDetailsCountByAssignmentId(String assignmentId) {
        return tbJoinTaskDetailsMapper.getTaskDetailsCountByAssignmentId(Integer.parseInt(assignmentId));
    }

    @Override
    public int savemanuscriptIdUrlGaoJian(Integer sign, String url, String originalFilename, String extName, String assignmentId, BigDecimal sizeBig, String manuscriptId) {
        TbManuscriptUrl tbManuscriptUrl = new TbManuscriptUrl();
        tbManuscriptUrl.setAssignmentId(Integer.parseInt(assignmentId));
        tbManuscriptUrl.setUrl(url);
        tbManuscriptUrl.setStatus(0);
        tbManuscriptUrl.setGaojianSign(sign);
        tbManuscriptUrl.setManuscriptId(Integer.parseInt(manuscriptId));
        tbManuscriptUrl.setFileName(originalFilename);
        tbManuscriptUrl.setExtendName(extName);
        tbManuscriptUrl.setFileSize(sizeBig);
        int i = tbManuscriptUrlMapper.insertSelective(tbManuscriptUrl);
        return i;
    }

    @Override
    public int removeFileBymanuscriptIdGaoJian(Integer sign, String filename, String manuscriptId) {
        TbManuscriptUrl tbManuscriptUrl = new TbManuscriptUrl();
        tbManuscriptUrl.setManuscriptId(Integer.parseInt(manuscriptId));
        tbManuscriptUrl.setFileName(filename);
        tbManuscriptUrl.setGaojianSign(sign);
        int delete = tbManuscriptUrlMapper.delete(tbManuscriptUrl);
        return delete;
    }

    @Override
    public int saveSingleXuanShangModeDetails1(String manuscriptId, Integer memberId, String assignmentId, Integer checkStatus) {
        TbJoinTaskDetails tbJoinTaskDetails = new TbJoinTaskDetails();
        tbJoinTaskDetails.setTaskType(4);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tbJoinTaskDetails.setUploadTime(sdf.format(d));
        tbJoinTaskDetails.setIsHide(checkStatus);
        Example example = new Example(TbJoinTaskDetails.class);
        example.createCriteria().andEqualTo("uId", memberId).andEqualTo("assignmentId", assignmentId).andEqualTo("id", manuscriptId);
        int i = tbJoinTaskDetailsMapper.updateByExampleSelective(tbJoinTaskDetails, example);
        return i;
    }

    @Override
    public int saveSingleXuanShangModeDetails2(String manuscriptId, Integer memberId, String assignmentId, Integer checkStatus, String editor4html) {
        TbJoinTaskDetails tbJoinTaskDetails = new TbJoinTaskDetails();
        tbJoinTaskDetails.setQuoteDetails(editor4html);
        tbJoinTaskDetails.setTaskType(3);
        Date d = new Date();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        tbJoinTaskDetails.setUploadTime(sdf.format(d));
        tbJoinTaskDetails.setIsHide(checkStatus);
        Example example = new Example(TbJoinTaskDetails.class);
        example.createCriteria().andEqualTo("uId", memberId).andEqualTo("assignmentId", assignmentId).andEqualTo("id", manuscriptId);
        int i = tbJoinTaskDetailsMapper.updateByExampleSelective(tbJoinTaskDetails, example);
        return i;
    }

    @Override
    public List<TbManuscriptUrl> getUrlByManuscriptId(String manuscriptId) {
        TbManuscriptUrl tbManuscriptUrl = new TbManuscriptUrl();
        if (StringUtils.isNotBlank(manuscriptId)) {
            tbManuscriptUrl.setManuscriptId(Integer.parseInt(manuscriptId.trim()));
            return tbManuscriptUrlMapper.select(tbManuscriptUrl);
        }
        return null;
    }

    /**
     * 如果返回一说明显示发包方页面
     * 如果返回0 显示设计师页面
     *
     * @param assignmentId
     * @param memberId
     * @return
     */
    @Override
    public int doIsSelf(String assignmentId, Integer memberId) {
        Assignment assignment = new Assignment();
        assignment.setId(Integer.parseInt(assignmentId));
        Assignment assignment1 = assignmentMapper.selectOne(assignment);
        if (assignment1 != null) {
            Integer uid = assignment1.getUid();
            if (uid == memberId) {
                return 1;
            }
        }
        return 0;
    }

    @Override
    public List<TbJoinTaskDetails> getJoinTaskDetailByAssignment(String assignmentId) {


        TbJoinTaskDetails tbJoinTaskDetails = new TbJoinTaskDetails();
        tbJoinTaskDetails.setAssignmentId(Integer.parseInt(assignmentId));
        tbJoinTaskDetails.setIsWinTheBidding(1);
        List<TbJoinTaskDetails> select1 = tbJoinTaskDetailsMapper.select(tbJoinTaskDetails);
        if (select1 != null && select1.size() > 0) {
            return select1;
        } else {
            TbJoinTaskDetails tbJoinTaskDetails2 = new TbJoinTaskDetails();
            tbJoinTaskDetails2.setAssignmentId(Integer.parseInt(assignmentId));
            tbJoinTaskDetails2.setIsWinTheBidding(3);
            List<TbJoinTaskDetails> select2 = tbJoinTaskDetailsMapper.select(tbJoinTaskDetails2);
            if (select2 != null && select2.size() > 0) {
                return select2;
            }
            Example example = new Example(TbJoinTaskDetails.class);
            example.setOrderByClause("upload_time DESC");
            example.createCriteria().andEqualTo("assignmentId", Integer.parseInt(assignmentId));
            List<TbJoinTaskDetails> select = tbJoinTaskDetailsMapper.selectByExample(example);
            return select;
        }
    }

    @Override
    public List<TaskDetailsAndUserInfo> transTaskDetailsToVO(List<TbJoinTaskDetails> tbJoinTaskDetailsList, Integer memberId) {
        List<TaskDetailsAndUserInfo> list = new ArrayList<>();
        for (TbJoinTaskDetails tbJoinTaskDetails : tbJoinTaskDetailsList) {
            // 查询是否有源文件
            TbManuscriptUrl tbManuscriptUrl2 = new TbManuscriptUrl();
            tbManuscriptUrl2.setIsOrigin(1);
            tbManuscriptUrl2.setManuscriptId(tbJoinTaskDetails.getId());
            List<TbManuscriptUrl> select1 = tbManuscriptUrlMapper.select(tbManuscriptUrl2);

            Integer uId = tbJoinTaskDetails.getuId();
            User user = new User();
            user.setId(uId);
            User user1 = publishUserMapper.selectOne(user);
            TaskDetailsAndUserInfo taskDetailsAndUserInfo = new TaskDetailsAndUserInfo();
            taskDetailsAndUserInfo.setCeshiDetails(tbJoinTaskDetails.getCeshiDetails());
            taskDetailsAndUserInfo.setManuscript_id(tbJoinTaskDetails.getId());
            taskDetailsAndUserInfo.setuId(tbJoinTaskDetails.getuId());
            taskDetailsAndUserInfo.setAssignmentId(tbJoinTaskDetails.getAssignmentId());
            taskDetailsAndUserInfo.setEnsureMoney(tbJoinTaskDetails.getEnsureMoney());
            taskDetailsAndUserInfo.setIsWinTheBidding(tbJoinTaskDetails.getIsWinTheBidding());
            taskDetailsAndUserInfo.setTaskType(tbJoinTaskDetails.getTaskType());
            taskDetailsAndUserInfo.setUploadTime(tbJoinTaskDetails.getUploadTime());
            taskDetailsAndUserInfo.setIsCat(tbJoinTaskDetails.getIsCat());
            taskDetailsAndUserInfo.setIsHide(tbJoinTaskDetails.getIsHide());
            taskDetailsAndUserInfo.setWorkPeriod(tbJoinTaskDetails.getWorkPeriod());
            taskDetailsAndUserInfo.setProjectQuotation(tbJoinTaskDetails.getProjectQuotation());
            taskDetailsAndUserInfo.setIsPay(tbJoinTaskDetails.getIsPay());
            taskDetailsAndUserInfo.setQuoteDetails(tbJoinTaskDetails.getQuoteDetails());
            taskDetailsAndUserInfo.setUsername(user1.getUsername());
            taskDetailsAndUserInfo.setPassword(user1.getPassword());
            taskDetailsAndUserInfo.setPhone(user1.getPhone());
            taskDetailsAndUserInfo.setEmail(user1.getEmail());
            taskDetailsAndUserInfo.setCreated(user1.getCreated());
            taskDetailsAndUserInfo.setUpdated(user1.getUpdated());
            taskDetailsAndUserInfo.setSourceType(user1.getSourceType());
            taskDetailsAndUserInfo.setNickName(user1.getNickName());
            taskDetailsAndUserInfo.setFavorableRate(user1.getFavorableRate());
            taskDetailsAndUserInfo.setName(user1.getName());
            taskDetailsAndUserInfo.setStatus(user1.getStatus());
            taskDetailsAndUserInfo.setHeadPic(user1.getHeadPic());
            taskDetailsAndUserInfo.setQq(user1.getQq());
            taskDetailsAndUserInfo.setAccountBalance(user1.getAccountBalance());
            taskDetailsAndUserInfo.setIsMobileCheck(user1.getIsMobileCheck());
            taskDetailsAndUserInfo.setIsEmailCheck(user1.getIsEmailCheck());
            taskDetailsAndUserInfo.setSex(user1.getSex());
            taskDetailsAndUserInfo.setUserLevel(user1.getUserLevel());
            taskDetailsAndUserInfo.setPoints(user1.getPoints());
            taskDetailsAndUserInfo.setExperienceValue(user1.getExperienceValue());
            taskDetailsAndUserInfo.setBirthday(user1.getBirthday());
            taskDetailsAndUserInfo.setLastLoginTime(user1.getLastLoginTime());
            taskDetailsAndUserInfo.setAge(user1.getAge());
            taskDetailsAndUserInfo.setAllHideResidualTimes(user1.getAllHideResidualTimes());
            taskDetailsAndUserInfo.setTaskHideResidualTimes(user1.getTaskHideResidualTimes());
            taskDetailsAndUserInfo.setTaskTopResidualTimes(user1.getTaskTopResidualTimes());
            taskDetailsAndUserInfo.setTaskUrgentResidualTimes(user1.getTaskUrgentResidualTimes());
            taskDetailsAndUserInfo.setCertifiedOrNot(user1.getCertifiedOrNot());
            taskDetailsAndUserInfo.setVipType(user1.getVipType());
            taskDetailsAndUserInfo.setLoginMemberId(memberId);
            taskDetailsAndUserInfo.setIsOpenStore(user1.getIsOpenStore());
            taskDetailsAndUserInfo.setIsPayStoreMoney(user1.getIsPayStoreMoney());
            Integer max_value = tbManuscriptUrlMapper.getMaxValue(tbJoinTaskDetails.getId());
            taskDetailsAndUserInfo.setMax_value(max_value);
            taskDetailsAndUserInfo.setJijianHegeFirst(tbJoinTaskDetails.getJijianHegeFirst());
            taskDetailsAndUserInfo.setJijianHegeSecond(tbJoinTaskDetails.getJijianHegeSecond());
            taskDetailsAndUserInfo.setJijianHegeThird(tbJoinTaskDetails.getJijianHegeThird());
            taskDetailsAndUserInfo.setJijianHegeFour(tbJoinTaskDetails.getJijianHegeFour());
            taskDetailsAndUserInfo.setJijianHegeFive(tbJoinTaskDetails.getJijianHegeFive());
            if (select1 != null && select1.size() > 0) {
                taskDetailsAndUserInfo.setIsDesign(1);
            } else {
                taskDetailsAndUserInfo.setIsDesign(0);
            }
            TbManuscriptUrl tbManuscriptUrl = new TbManuscriptUrl();
            tbManuscriptUrl.setManuscriptId(tbJoinTaskDetails.getId());
            List<TbManuscriptUrl> select = tbManuscriptUrlMapper.select(tbManuscriptUrl);
            taskDetailsAndUserInfo.setTbManuscriptUrls(select);
            list.add(taskDetailsAndUserInfo);
        }
        return list;
    }

    @Override
    public TbJoinTaskDetails getJoinTaskDetailByAssignmentAndUId(String assignmentId, Integer memberId) {
        TbJoinTaskDetails tbJoinTaskDetails = new TbJoinTaskDetails();
        tbJoinTaskDetails.setAssignmentId(Integer.parseInt(assignmentId));
        tbJoinTaskDetails.setuId(memberId);
        TbJoinTaskDetails tbJoinTaskDetails1 = tbJoinTaskDetailsMapper.selectOne(tbJoinTaskDetails);
        return tbJoinTaskDetails1;
    }

    /**
     * 更改是否查看的状态
     *
     * @param bianhao
     * @return
     */
    @Override
    public int updateBianHao(Integer bianhao) {
        TbJoinTaskDetails tbJoinTaskDetails = new TbJoinTaskDetails();
        tbJoinTaskDetails.setIsCat(1);
        Example example = new Example(TbJoinTaskDetails.class);
        example.createCriteria().andEqualTo("id", bianhao);
        return tbJoinTaskDetailsMapper.updateByExampleSelective(tbJoinTaskDetails, example);
    }

    @Override
    public int getIsCatByBianHao(Integer bianhao) {
        TbJoinTaskDetails tbJoinTaskDetails = new TbJoinTaskDetails();
        tbJoinTaskDetails.setId(bianhao);
        TbJoinTaskDetails tbJoinTaskDetails1 = tbJoinTaskDetailsMapper.selectOne(tbJoinTaskDetails);
        Integer isCat = tbJoinTaskDetails1.getIsCat();
        return isCat;
    }

    @Override
    public int saveorder(BigDecimal totalMoney, String assignmentId, String orderCodeSn) {
        Taskorder taskorder = new Taskorder();
        long l = idWorker.nextId();
        String s = l + "";
        taskorder.setId(Integer.parseInt(s.substring(0, 7)));
        taskorder.setAssid(Integer.parseInt(assignmentId));
        taskorder.setOrdeno(orderCodeSn);
        taskorder.setOrderprice(totalMoney);
        int i = taskorderMapper.insertSelective(taskorder);
        return i;
    }

    @Override
    public Taskorder getTaskPriceFromTaskOrderByOrderSn(String orderSn) {
        Taskorder taskorder = new Taskorder();
        taskorder.setOrdeno(orderSn);
        Taskorder taskorder1 = taskorderMapper.selectOne(taskorder);
        return taskorder1;
    }

    @Override
    public void setIsBiddingByManuScriptId(String manuscriptId) {
        TbJoinTaskDetails tbJoinTaskDetails = new TbJoinTaskDetails();
        tbJoinTaskDetails.setIsWinTheBidding(1);
        Example example = new Example(TbJoinTaskDetails.class);
        example.createCriteria().andEqualTo("id", manuscriptId);
        tbJoinTaskDetailsMapper.updateByExampleSelective(tbJoinTaskDetails, example);
    }

    @Override
    public int setAssignmentScheduleStatus(String assignmentId) {
        Assignment assignment = new Assignment();
        assignment.setScheduleStatus(2);
        assignment.setSubmitSourceDate(getStringDate());
        Example example = new Example(Assignment.class);
        example.createCriteria().andEqualTo("id", assignmentId);
        int i = assignmentMapper.updateByExampleSelective(assignment, example);
        return i;
    }

    @Override
    public int IsBiddingByManuScriptIdAndScheduleStatus(String assignmentId, String manuscriptId) {
        Assignment assignment = new Assignment();
        assignment.setScheduleStatus(2);
        assignment.setSubmitSourceDate(getStringDate());
        Example example = new Example(Assignment.class);
        example.createCriteria().andEqualTo("id", assignmentId);
        int i = assignmentMapper.updateByExampleSelective(assignment, example);
        TbJoinTaskDetails tbJoinTaskDetails = new TbJoinTaskDetails();
        tbJoinTaskDetails.setIsWinTheBidding(3);
        Example example1 = new Example(TbJoinTaskDetails.class);
        example1.createCriteria().andEqualTo("id", manuscriptId);
        int i1 = tbJoinTaskDetailsMapper.updateByExampleSelective(tbJoinTaskDetails, example1);
        return i1;
    }

    @Override
    public int updateXuangaoTime(String assignmentId) {
        Assignment assignment = new Assignment();
        assignment.setId(Integer.parseInt(assignmentId));
        Assignment assignment1 = assignmentMapper.selectOne(assignment);
        int res = 0;
        if (assignment1.getXuangaoDate() == null) {
            Assignment assignment2 = new Assignment();
            String stringDate = getStringDate();
            assignment2.setXuangaoDate(stringDate);
            Example example = new Example(Assignment.class);
            example.createCriteria().andEqualTo("id", assignmentId);
            int i = assignmentMapper.updateByExampleSelective(assignment2, example);
        }
        return res;
    }

    @Override
    public int setAssignmentScheduleStatus1(String assignmentId) {
        Assignment assignment1 = new Assignment();
        assignment1.setId(Integer.parseInt(assignmentId));
        Assignment assignment2 = assignmentMapper.selectOne(assignment1);
        if (assignment2.getXuangaoDate() == null) {
            Assignment assignment = new Assignment();
            assignment.setScheduleStatus(1);
            Example example = new Example(Assignment.class);
            example.createCriteria().andEqualTo("id", assignmentId);
            int i = assignmentMapper.updateByExampleSelective(assignment, example);
            return i;
        } else {
            return 0;
        }
    }

    @Override
    public int updateFirstStatus(String assignmentId) {
        RewardMore rewardMore1 = new RewardMore();
        rewardMore1.setAssignmentId(Integer.parseInt(assignmentId));
        RewardMore rewardMore2 = rewardMoreMapper.selectOne(rewardMore1);
        Integer firstStatus = rewardMore2.getFirstStatus();
        RewardMore rewardMore = new RewardMore();
        rewardMore.setFirstStatus(firstStatus - 1);
        Example example = new Example(RewardMore.class);
        example.createCriteria().andEqualTo("assignmentId", assignmentId);
        int i = rewardMoreMapper.updateByExampleSelective(rewardMore, example);
        return i;
    }

    @Override
    public int setTaskStatus(String manuscriptId) {
        TbJoinTaskDetails tbJoinTaskDetails = new TbJoinTaskDetails();
        tbJoinTaskDetails.setIsWinTheBidding(4);
        Example example1 = new Example(TbJoinTaskDetails.class);
        example1.createCriteria().andEqualTo("id", manuscriptId);
        int i1 = tbJoinTaskDetailsMapper.updateByExampleSelective(tbJoinTaskDetails, example1);
        return i1;
    }

    @Override
    public int updateFirstStatus2(String assignmentId) {
        RewardMore rewardMore1 = new RewardMore();
        rewardMore1.setAssignmentId(Integer.parseInt(assignmentId));
        RewardMore rewardMore2 = rewardMoreMapper.selectOne(rewardMore1);
        Integer firstStatus = rewardMore2.getSecondStatus();
        RewardMore rewardMore = new RewardMore();
        rewardMore.setSecondStatus(firstStatus - 1);
        Example example = new Example(RewardMore.class);
        example.createCriteria().andEqualTo("assignmentId", assignmentId);
        int i = rewardMoreMapper.updateByExampleSelective(rewardMore, example);
        return i;
    }

    @Override
    public int setTaskStatus2(String manuscriptId) {
        TbJoinTaskDetails tbJoinTaskDetails = new TbJoinTaskDetails();
        tbJoinTaskDetails.setIsWinTheBidding(5);
        Example example1 = new Example(TbJoinTaskDetails.class);
        example1.createCriteria().andEqualTo("id", manuscriptId);
        int i1 = tbJoinTaskDetailsMapper.updateByExampleSelective(tbJoinTaskDetails, example1);
        return i1;
    }

    @Override
    public int updateFirstStatus3(String assignmentId) {
        RewardMore rewardMore1 = new RewardMore();
        rewardMore1.setAssignmentId(Integer.parseInt(assignmentId));
        RewardMore rewardMore2 = rewardMoreMapper.selectOne(rewardMore1);
        Integer firstStatus = rewardMore2.getThirdStatus();
        RewardMore rewardMore = new RewardMore();
        rewardMore.setThirdStatus(firstStatus - 1);
        Example example = new Example(RewardMore.class);
        example.createCriteria().andEqualTo("assignmentId", assignmentId);
        int i = rewardMoreMapper.updateByExampleSelective(rewardMore, example);
        return i;
    }

    @Override
    public int setTaskStatus3(String manuscriptId) {
        TbJoinTaskDetails tbJoinTaskDetails = new TbJoinTaskDetails();
        tbJoinTaskDetails.setIsWinTheBidding(6);
        Example example1 = new Example(TbJoinTaskDetails.class);
        example1.createCriteria().andEqualTo("id", manuscriptId);
        int i1 = tbJoinTaskDetailsMapper.updateByExampleSelective(tbJoinTaskDetails, example1);
        return i1;
    }

    @Override
    public int updateGaoJianStatus(String assignmentId) {
        Assignment assignment = new Assignment();
        assignment.setId(Integer.parseInt(assignmentId));
        Assignment assignment1 = assignmentMapper.selectOne(assignment);
        Integer gaojianStatus = assignment1.getGaojianStatus();
        Assignment assignment2 = new Assignment();
        assignment2.setGaojianStatus(gaojianStatus - 1);
        Example example1 = new Example(Assignment.class);
        example1.createCriteria().andEqualTo("id", assignmentId);
        return assignmentMapper.updateByExampleSelective(assignment2, example1);
    }

    @Override
    public int updateUrlStatus(String manuscriptId, String gaojianSign) {
        TbManuscriptUrl tbManuscriptUrl = new TbManuscriptUrl();
        tbManuscriptUrl.setStatus(1);
        Example example = new Example(TbManuscriptUrl.class);
        example.createCriteria().andEqualTo("manuscriptId", manuscriptId).andEqualTo("gaojianSign", gaojianSign);
        return tbManuscriptUrlMapper.updateByExample(tbManuscriptUrl, example);
    }

    @Override
    public int updateJijianHegeNum(String manuscriptId, String gaojianSign) {
        TbJoinTaskDetails tbJoinTaskDetails = new TbJoinTaskDetails();
        if (gaojianSign.equals("1")) {
            tbJoinTaskDetails.setJijianHegeFirst(1);
        }
        if (gaojianSign.equals("2")) {
            tbJoinTaskDetails.setJijianHegeSecond(1);
        }
        if (gaojianSign.equals("3")) {
            tbJoinTaskDetails.setJijianHegeThird(1);
        }
        if (gaojianSign.equals("4")) {
            tbJoinTaskDetails.setJijianHegeFour(1);
        }
        if (gaojianSign.equals("5")) {
            tbJoinTaskDetails.setJijianHegeFive(1);
        }
        Example example = new Example(TbJoinTaskDetails.class);
        example.createCriteria().andEqualTo("id", manuscriptId);
        return tbJoinTaskDetailsMapper.updateByExampleSelective(tbJoinTaskDetails, example);
    }

    @Override
    public int IsBiddingByManuScriptIdAndScheduleStatus2(String assignmentId, String manuscriptId) {
        Assignment assignment = new Assignment();
        assignment.setScheduleStatus(2);
        assignment.setSubmitSourceDate(getStringDate());
        Example example = new Example(Assignment.class);
        example.createCriteria().andEqualTo("id", assignmentId);
        int i = assignmentMapper.updateByExampleSelective(assignment, example);
        TbJoinTaskDetails tbJoinTaskDetails = new TbJoinTaskDetails();
        tbJoinTaskDetails.setIsWinTheBidding(7);
        Example example1 = new Example(TbJoinTaskDetails.class);
        example1.createCriteria().andEqualTo("id", manuscriptId);
        int i1 = tbJoinTaskDetailsMapper.updateByExampleSelective(tbJoinTaskDetails, example1);
        return i1;
    }

    @Override
    public int savemanuscriptIdUrlss(String url, String originalFilename, String extName, String assignmentId, BigDecimal sizeBig, String manuscriptId) {
        TbManuscriptUrl tbManuscriptUrl = new TbManuscriptUrl();
        tbManuscriptUrl.setAssignmentId(Integer.parseInt(assignmentId));
        tbManuscriptUrl.setUrl(url);
        tbManuscriptUrl.setStatus(0);
        tbManuscriptUrl.setManuscriptId(Integer.parseInt(manuscriptId));
        tbManuscriptUrl.setFileName(originalFilename);
        tbManuscriptUrl.setExtendName(extName);
        tbManuscriptUrl.setFileSize(sizeBig);
        tbManuscriptUrl.setIsOrigin(1);
        int i = tbManuscriptUrlMapper.insertSelective(tbManuscriptUrl);
        return i;
    }

    @Override
    public int removeFileBymanuscriptIdss(String filename, String manuscriptId) {
        TbManuscriptUrl tbManuscriptUrl = new TbManuscriptUrl();
        tbManuscriptUrl.setManuscriptId(Integer.parseInt(manuscriptId));
        tbManuscriptUrl.setFileName(filename);
        tbManuscriptUrl.setIsOrigin(1);
        int delete = tbManuscriptUrlMapper.delete(tbManuscriptUrl);
        return delete;
    }

    @Override
    public List<Assignment> getAssignmentInfoByMemberId(Integer memberId) {
        Assignment assignment = new Assignment();
        assignment.setUid(memberId);
        // 排除一些空数据和垃圾数据
        // 使用迭代器 ，否则会发生list的移除异常
        List<Assignment> select = assignmentMapper.select(assignment);
        if (select != null) {
            Iterator<Assignment> iterator = select.iterator();
            while (iterator.hasNext()) {
                Assignment next = iterator.next();
                if (next.getPubtime() == null || next.getPubtime().equals("")) {
                    iterator.remove();
                }
//                if (next.getCategoryid() == null) {
//                    iterator.remove();
//                }
//                if (next.getTasktype() == null) {
//                    iterator.remove();
//                }
            }
        }
        return select;
    }

    @Override
    public List<PersonPublishTaskVO> transAssignmentToPersonPublishTaskVO(List<Assignment> assignmentList) {
        List<PersonPublishTaskVO> personPublishTaskVOList = new ArrayList<>();
        for (Assignment assignment : assignmentList) {
            PersonPublishTaskVO personPublishTaskVO = new PersonPublishTaskVO();

            TbJoinTaskDetails tbJoinTaskDetails = new TbJoinTaskDetails();
            tbJoinTaskDetails.setAssignmentId(assignment.getId());
            List<TbJoinTaskDetails> tbJoinTaskDetailsList = tbJoinTaskDetailsMapper.select(tbJoinTaskDetails);
            List<Integer> joinTaskDetailsIds = new ArrayList<>();
            for (TbJoinTaskDetails joinTaskDetails : tbJoinTaskDetailsList) {
                joinTaskDetailsIds.add(joinTaskDetails.getId());
            }
            // 稿件表查询
            personPublishTaskVO.setTbJoinTaskDetailsId(joinTaskDetailsIds);
            //封装assignment的信息
            personPublishTaskVO.setId(assignment.getId());
            personPublishTaskVO.setTasktype(assignment.getTasktype());
            personPublishTaskVO.setTitle(assignment.getTitle());
            personPublishTaskVO.setTaskTimeTypeId(assignment.getTaskTimeTypeId());
            personPublishTaskVO.setPubtime(assignment.getPubtime());
            personPublishTaskVO.setTaskStatus(assignment.getTaskStatus());
            personPublishTaskVO.setToubiaoType(assignment.getToubiaoType());
            personPublishTaskVO.setXuanshangType(assignment.getXuanshangType());
            personPublishTaskVO.setScheduleStatus(assignment.getScheduleStatus());
            personPublishTaskVO.setTestStatus(assignment.getTestStatus());


            Taskorder taskorder = new Taskorder();
            taskorder.setAssid(assignment.getId());
            Taskorder taskorder1 = taskorderMapper.selectOne(taskorder);
            if (taskorder1 != null) {
                // 封装订单表信息。
                personPublishTaskVO.setOrder_id(taskorder1.getId());
                personPublishTaskVO.setOrdeno(taskorder1.getOrdeno());
                personPublishTaskVO.setOrderstatus(taskorder1.getOrderstatus());
                personPublishTaskVO.setIspay(taskorder1.getIspay());
                personPublishTaskVO.setOrderprice(taskorder1.getOrderprice());
                personPublishTaskVO.setPayprice(taskorder1.getPayprice());
            }


            // 通过assignmentId查询测试金
            TestQuestion testQuestion = new TestQuestion();
            testQuestion.setAssignmentId(assignment.getId());
            TestQuestion testQuestion1 = testQuestionMapper.selectOne(testQuestion);
            if (testQuestion1 != null) {
                personPublishTaskVO.setTestMoneyNum(testQuestion1.getMoneyNum());
            }

            //封装taskModeTypeId；
            if (assignment.getTasktype() != null) {
                if (assignment.getTasktype() == 0) {
                    //投标
                    if (assignment.getTestStatus() != null) {
                        if (assignment.getTestStatus() == 0) {
                            // 没有测试题
                            personPublishTaskVO.setTaskModeTypeId(1);
                        }
                        if (assignment.getTestStatus() == 1) {
                            // 有测试题
                            personPublishTaskVO.setTaskModeTypeId(2);
                        }
                    }
                    //带测试题
                }
                if (assignment.getTasktype() == 1) {
                    if (assignment.getXuanshangType() != null) {
                        //悬赏
                        if (assignment.getXuanshangType() == 0) {
                            //单人悬赏
                            personPublishTaskVO.setTaskModeTypeId(3);
                        }
                        if (assignment.getXuanshangType() == 1) {
                            //多人悬赏
                            personPublishTaskVO.setTaskModeTypeId(4);
                        }
                        if (assignment.getXuanshangType() == 2) {
                            //计件悬赏
                            personPublishTaskVO.setTaskModeTypeId(5);
                        }
                    }
                }
            }

            //订单状态





            // 查settlement表
            Settlement settlement = new Settlement();
            settlement.setAssignmentId(assignment.getId());
            Settlement settlement1 = settlementMapper.selectOne(settlement);
            if (assignment.getTestStatus() == 1 && testQuestion1 == null) {
                //测试金表中没有数据但是是有测试题模式  即发生数据错误时。
            } else {
                if (settlement1 != null) {
                    if (settlement1.getTotalAmount().compareTo(new BigDecimal(0)) == 0 && assignment.getTasktype() == 1) {

                    }else {
                        personPublishTaskVO.setTotalMoney(settlement1.getTotalAmount());
                        if (settlement1.getIsPay() == 0) {
                            // 未支付
                            // 待支付
                            personPublishTaskVO.setTaskOrderStatus(0);
                            if (assignment.getTasktype() == 0 && assignment.getTestStatus() == 0 && settlement1.getTotalAmount().compareTo(new BigDecimal(0)) == 0) {
                                personPublishTaskVO.setTaskOrderStatus(1);
                            }
                        }
                        personPublishTaskVOList.add(personPublishTaskVO);
                    }
                } else {
                    // 说明订单表中没有该assignment的数据 不将该数据添加进List
                }

            }
        }
        return personPublishTaskVOList;
    }

    @Override
    public int clickcollectToDB(Integer assignmentId, Integer memberId) {
        TaskCollect taskCollect = new TaskCollect();
        taskCollect.setCollectionTime(getStringDate());
        taskCollect.setTaskid(assignmentId);
        taskCollect.setUid(memberId);
        //插入数据库
        int i = taskCollectMapper.insert(taskCollect);
        return i;
    }

    @Override
    public int canclecollectToDB(Integer assignmentId, Integer memberId) {
        // 从数据库中删除
        TaskCollect taskCollect = new TaskCollect();
        Example example = new Example(TaskCollect.class);
        example.createCriteria().andEqualTo("uid",memberId).andEqualTo("taskid",assignmentId);
        int i = taskCollectMapper.deleteByExample(example);
        return i;
    }

    @Override
    public int getTaskCollect(Integer assignmentId, Integer memberId) {
        TaskCollect taskCollect = new TaskCollect();
        Example example = new Example(TaskCollect.class);
        example.createCriteria().andEqualTo("uid",memberId).andEqualTo("taskid",assignmentId);
        List<TaskCollect> taskCollects = taskCollectMapper.selectByExample(example);
        if (taskCollects == null || taskCollects.size() == 0) {
            // 没有收藏
            return 0;
        }else {
            // 收藏
            return 1;
        }
    }

    @Override
    public List<TaskCollect> getTaskCollectByMemberId(Integer memberId) {
        TaskCollect taskCollect = new TaskCollect();
        taskCollect.setUid(memberId);
        List<TaskCollect> select = taskCollectMapper.select(taskCollect);
        return select;
    }

    @Override
    public List<TaskCollectVO> transTaskCollectToVO(List<TaskCollect> taskCollectList) {
        List<TaskCollectVO> taskCollectVOList = new ArrayList<>();
        for (TaskCollect taskCollect : taskCollectList) {
            TaskCollectVO taskCollectVO = new TaskCollectVO();
            Integer taskid = taskCollect.getTaskid();
            Assignment assignmentInfoById = getAssignmentInfoById(taskid + "");
            taskCollectVO.setAssignmentId(taskid);
            taskCollectVO.setTitle(assignmentInfoById.getTitle());
            taskCollectVO.setTasktype(assignmentInfoById.getTasktype());
            taskCollectVO.setToubiaoType(assignmentInfoById.getToubiaoType());
            taskCollectVO.setXuanshangType(assignmentInfoById.getXuanshangType());
            taskCollectVO.setCollectionTime(taskCollect.getCollectionTime());
            taskCollectVO.setEndTime(assignmentInfoById.getEndtime());

            //封装taskModeTypeId；
            if (assignmentInfoById.getTasktype() != null) {
                if (assignmentInfoById.getTasktype() == 0) {
                    //投标
                    if (assignmentInfoById.getTestStatus() != null) {
                        if (assignmentInfoById.getTestStatus() == 0) {
                            // 没有测试题
                            taskCollectVO.setTaskModeTypeId(1);
                        }
                        if (assignmentInfoById.getTestStatus() == 1) {
                            // 有测试题
                            taskCollectVO.setTaskModeTypeId(2);
                        }
                    }
                    //带测试题
                }
                if (assignmentInfoById.getTasktype() == 1) {
                    if (assignmentInfoById.getXuanshangType() != null) {
                        //悬赏
                        if (assignmentInfoById.getXuanshangType() == 0) {
                            //单人悬赏
                            taskCollectVO.setTaskModeTypeId(3);
                        }
                        if (assignmentInfoById.getXuanshangType() == 1) {
                            //多人悬赏
                            taskCollectVO.setTaskModeTypeId(4);
                        }
                        if (assignmentInfoById.getXuanshangType() == 2) {
                            //计件悬赏
                            taskCollectVO.setTaskModeTypeId(5);
                        }
                    }
                }
            }

            // 通过assignmentId查询测试金
            TestQuestion testQuestion = new TestQuestion();
            testQuestion.setAssignmentId(assignmentInfoById.getId());
            TestQuestion testQuestion1 = testQuestionMapper.selectOne(testQuestion);

            // 查settlement表
            Settlement settlement = new Settlement();
            settlement.setAssignmentId(assignmentInfoById.getId());
            Settlement settlement1 = settlementMapper.selectOne(settlement);
            if (assignmentInfoById.getTestStatus() == 1 && testQuestion1 == null) {
                //测试金表中没有数据但是是有测试题模式  即发生数据错误时。
            } else {
                if (settlement1 != null) {
                    if (settlement1.getTotalAmount().compareTo(new BigDecimal(0)) == 0 && assignmentInfoById.getTasktype() == 1) {

                    }else {
                        taskCollectVO.setTotalMoney(settlement1.getTotalAmount());
                        taskCollectVOList.add(taskCollectVO);
                    }
                } else {
                    // 说明订单表中没有该assignment的数据 不将该数据添加进List
                }
            }
        }
        return taskCollectVOList;
    }
}
