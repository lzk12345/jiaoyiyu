package com.jiaoyiyu.back;

import com.jiaoyiyu.back.bean.*;
import com.jiaoyiyu.back.bean.es.AssignmentES;
import com.jiaoyiyu.back.coreservice.CatalogService;
import com.jiaoyiyu.back.coreservice.PublishTaskService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Index;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class DemoApplicationTests {
//    @Autowired
//    PublishTaskService publishTaskService;
//    @Autowired
//    CatalogService catalogService;
//    @Autowired
//    JestClient jestClient;
//
//
//    @Test
//    void contextLoads() throws IOException {
//        List<Assignment> assignments = publishTaskService.getAllAssignmentInfo();
//
//        List<AssignmentES> assignmentESs = new ArrayList<>();
//        // 转化正常的assignment对象未es对象
//        for (Assignment assignment : assignments) {
//            if (assignment.getTaskStatus() == 1) {
//                AssignmentES assignmentES = new AssignmentES();
//                assignmentES.setId(assignment.getId() + "");
//                // 该categories为第二 通过它获得第一
//                Catalog2 catalog2ById = catalogService.getCatalog2ById(assignment.getCategoryid());
//                Catalog1 catalog1ById = catalogService.getCatalog1ById(catalog2ById.getCatalog1Id());
//                assignmentES.setCategory1Name(catalog1ById.getName());
//                assignmentES.setCategory2Name(catalog2ById.getName());
//                assignmentES.setCategoryId(catalog2ById.getCatalog1Id() + "");
//                assignmentES.setTasktype(assignment.getTasktype() + "");
//                assignmentES.setTitle(assignment.getTitle());
//                assignmentES.setDetails(assignment.getDetails());
//                assignmentES.setToubiaoPrice(assignment.getToubiaoPrice() + "");
//                assignmentES.setEndtime(assignment.getEndtime());
//                assignmentES.setTaskStatus(assignment.getTaskStatus() + "");
//                assignmentES.setTestStatus(Integer.valueOf(assignment.getTestStatus()) + "");
//                assignmentES.setToubiaoType(assignment.getToubiaoType() + "");
//                assignmentES.setXuanshangPrice(assignment.getXuanshangPrice() + "");
//                assignmentES.setTaskTimeTypeId(assignment.getTaskTimeTypeId() + "");
//                assignmentES.setXuanshangType(assignment.getXuanshangType() + "");
//                assignmentES.setMinMoney(assignment.getMinMoney() + "");
//                assignmentES.setMaxMoney(assignment.getMaxMoney() + "");
//                assignmentES.setBrowsePeopleNum(assignment.getBrowsePeopleNum() + "");
//                assignmentES.setPartPeopleNum(assignment.getPartPeopleNum() + "");
//                assignmentES.setTop(Integer.valueOf(assignment.getTop()) + "");
//                assignmentES.setUrgent(Integer.valueOf(assignment.getUrgent()) + "");
//                assignmentES.setAllhidden(Integer.valueOf(assignment.getAllhidden()) + "");
//                Integer tasktype = assignment.getTasktype();
//                // 0: 投标模式
//                // 1: 测试投标
//                // 2: 单人模式   assignmentES.setTaskDetailType(2);
//                // 3: 多人模式
//                // 4: 稿件模式
//                if (tasktype == 0) {
//                    //说明是投标
//                    // 看是区间投标还是准确价格投标
//                    Integer toubiaoType = assignment.getToubiaoType();
//                    Byte testStatus = assignment.getTestStatus();
//                    if (testStatus == 0) {
//                        // 没有测试题
//                        assignmentES.setTestprice(null);
//                        assignmentES.setTaskDetailType(0 + "");
//                    }
//                    if (testStatus == 1) {
//                        // 有测试题
//                        TestQuestion testQuestionByAssignmentId = publishTaskService.getTestQuestionByAssignmentId(assignment.getId().toString());
//                        if (testQuestionByAssignmentId != null) {
//                            assignmentES.setTestprice(testQuestionByAssignmentId.getMoneyNum() + "");
//                        }
//                        assignmentES.setTaskDetailType(1 + "");
//                    }
//                }
//                if (tasktype == 1){
//                    // 说明是悬赏模式
//                    Integer xuanshangType = assignment.getXuanshangType();
//                    if (xuanshangType == 0) {
//                        // 单人
//                        assignmentES.setTaskDetailType(2 + "");
//                    }
//                    if (xuanshangType == 1) {
//                        // 多人
//                        assignmentES.setTaskDetailType(3 + "");
//                    }
//                    if (xuanshangType == 2) {
//                        // 稿件
//                        assignmentES.setTaskDetailType(4 + "");
//                    }
//                }
//                AssignDifferTimeHour assignDifferTimeHour = publishTaskService.getAssignDifferTimeHourByAssignmentId(assignment.getId());
//                if (assignDifferTimeHour != null) {
//                    assignmentES.setDifferTimeHour(assignDifferTimeHour.getDifferTimeHour() + "");
//                }else {
//                    assignmentES.setDifferTimeHour("0");
//                    assignmentES.setTaskStatus("0");
//                }
//                assignmentESs.add(assignmentES);
//            }
//        }
//        // 执行插入到es的逻辑
//        for (AssignmentES eSs : assignmentESs) {
//            Index put = new Index.Builder(eSs).index("jiaoyiyu").type("assignment").id(eSs.getId().toString()).build();
//            jestClient.execute(put);
//        }
//
//    }
//
//
//    /**
//     * 复杂查询
//     */
//    @Test
//    public void complexQuery() throws IOException {
//        List<AssignmentES> assignmentESs = new ArrayList<>();
//        //jest 的dsl 工具
//        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
//        //bool
//        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
//
//        //term
//        TermQueryBuilder termQueryBuilder = new TermQueryBuilder("categoryId","3");
//        // filter
//        boolQueryBuilder.filter(termQueryBuilder);
//        //must
//        MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("title","测试");
//        boolQueryBuilder.must(matchQueryBuilder);
//        //query
//        searchSourceBuilder.query(boolQueryBuilder);
//        //from
//        searchSourceBuilder.from(0);
//        //size
//        searchSourceBuilder.size(20);
//        // highlight
//        searchSourceBuilder.highlighter(null);
//        //sort
////        searchSourceBuilder.sort("browsePeopleNum", SortOrder.DESC);
//        Search build = new Search.Builder(searchSourceBuilder.toString()).addIndex("jiaoyiyu").addType("assignment").build();
//        SearchResult execute = jestClient.execute(build);
//
//        List<SearchResult.Hit<AssignmentES, Void>> hits = execute.getHits(AssignmentES.class);
//        for (SearchResult.Hit<AssignmentES, Void> hit : hits) {
//            // 拿到assign es 对象
//            AssignmentES source = hit.source;
//            assignmentESs.add(source);
//        }
//        for (AssignmentES eSs : assignmentESs) {
//            System.out.println(eSs);
//        }
//    }

}
