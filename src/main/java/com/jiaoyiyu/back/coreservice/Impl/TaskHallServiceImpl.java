package com.jiaoyiyu.back.coreservice.Impl;

import com.jiaoyiyu.back.bean.*;
import com.jiaoyiyu.back.bean.es.AssignmentES;
import com.jiaoyiyu.back.coreservice.TaskHallService;
import io.searchbox.client.JestClient;
import io.searchbox.core.Search;
import io.searchbox.core.SearchResult;
import org.apache.commons.lang3.StringUtils;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.MatchQueryBuilder;
import org.elasticsearch.index.query.RangeQueryBuilder;
import org.elasticsearch.index.query.TermQueryBuilder;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * TaskHallServiceImpl class
 *
 * @author maochaoying
 * @date 2019/12/4
 */
@Service
public class TaskHallServiceImpl implements TaskHallService {

    @Autowired
    JestClient jestClient;

    /**
     * 根据条件查询es
     *
     * @param assignSearchParam
     * @return
     */
    @Override
    public List<AssignmentES> getAssignmentFromES(AssignSearchParam assignSearchParam, Integer currentPage, Integer recordNum) {

        String categoryid = assignSearchParam.getCategoryid();
        String taskTimeTypeId = assignSearchParam.getTaskTimeTypeId();
        String keyword = assignSearchParam.getKeyword();
        String tasktype = assignSearchParam.getTasktype();

        List<AssignmentES> assignmentESs = new ArrayList<>();
        //jest 的dsl 工具
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //bool
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if (StringUtils.isNotBlank(categoryid)) {
            //term
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("categoryId", categoryid);
            // filter
            boolQueryBuilder.filter(termQueryBuilder);
        }
        if (StringUtils.isNotBlank(tasktype)) {
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("taskDetailType", Integer.parseInt(tasktype) - 1);
            boolQueryBuilder.filter(termQueryBuilder);
        }
        if (StringUtils.isNotBlank(keyword)) {
            //must
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("title", keyword);
            boolQueryBuilder.must(matchQueryBuilder);
//            MatchQueryBuilder matchQueryBuilder1 = new MatchQueryBuilder("details", keyword);
//            boolQueryBuilder.must(matchQueryBuilder1);
        }
        if (StringUtils.isNotBlank(taskTimeTypeId)) {
            if (taskTimeTypeId.equals("1")) {
                //term
                RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("urgent");
                rangeQueryBuilder.gt(0);
                // filter
                boolQueryBuilder.filter(rangeQueryBuilder);
            }
            if (taskTimeTypeId.equals("2")){
                RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("differTimeHour");
                rangeQueryBuilder.lte(24);
                rangeQueryBuilder.gt(0);
                // filter
                boolQueryBuilder.filter(rangeQueryBuilder);
            }
            if (taskTimeTypeId.equals("3")){
                RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("differTimeHour");
                rangeQueryBuilder.lte(72);
                rangeQueryBuilder.gt(0);
                // filter
                boolQueryBuilder.filter(rangeQueryBuilder);
            }
            if (taskTimeTypeId.equals("4")){
                RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("differTimeHour");
                rangeQueryBuilder.lte(168);
                rangeQueryBuilder.gt(0);
                // filter
                boolQueryBuilder.filter(rangeQueryBuilder);
            }
            if (taskTimeTypeId.equals("5")){
                RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("differTimeHour");
                rangeQueryBuilder.lte(0);
                // filter
                boolQueryBuilder.filter(rangeQueryBuilder);
            }
        }
        //query
        searchSourceBuilder.query(boolQueryBuilder);
        //from
        if (currentPage == 1) {
            searchSourceBuilder.from(0);
        }else {
            searchSourceBuilder.from((currentPage - 1) * 10);
        }

        //size
        searchSourceBuilder.size(recordNum);
        // highlight
        searchSourceBuilder.highlighter(null);
        //sort
//        searchSourceBuilder.sort("browsePeopleNum", SortOrder.DESC);
        Search build = new Search.Builder(searchSourceBuilder.toString()).addIndex("jiaoyiyu").addType("assignment").build();
        SearchResult execute = null;
        try {
            execute = jestClient.execute(build);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SearchResult.Hit<AssignmentES, Void>> hits = execute.getHits(AssignmentES.class);
        Long total = execute.getTotal();
        for (SearchResult.Hit<AssignmentES, Void> hit : hits) {
            // 拿到assign es 对象
            AssignmentES source = hit.source;
            Map<String, List<String>> highlight = hit.highlight;
            if (StringUtils.isNotBlank(keyword)) {
//                String title = highlight.get("title").get(0);
//                source.setTitle(title);
            }
            assignmentESs.add(source);
        }

        return assignmentESs;
    }

    /**
     * 拼接url
     * @param assignSearchParam
     * @return
     */
    @Override
    public String getUrlParam(AssignSearchParam assignSearchParam) {
        String urlParam = "";
        String categoryid = assignSearchParam.getCategoryid();
        String taskTimeTypeId = assignSearchParam.getTaskTimeTypeId();
        String keyword = assignSearchParam.getKeyword();
        String tasktype = assignSearchParam.getTasktype();

        if (StringUtils.isNotBlank(categoryid)) {
            urlParam += ("categoryid=" + categoryid);
        }else {
            if (StringUtils.isNotBlank(keyword)) {
                urlParam += ("keyword=" + keyword);
            }
            if (StringUtils.isNotBlank(taskTimeTypeId)) {
                urlParam += ("&taskTimeTypeId=" + taskTimeTypeId);
            }
            if (StringUtils.isNotBlank(tasktype)) {
                urlParam += ("&tasktype=" + tasktype);
            }
            return urlParam;
        }

        if (StringUtils.isNotBlank(keyword)) {
            urlParam += ("&keyword=" + keyword);
        }
        if (StringUtils.isNotBlank(taskTimeTypeId)) {
            urlParam += ("&taskTimeTypeId=" + taskTimeTypeId);
        }
        if (StringUtils.isNotBlank(tasktype)) {
            urlParam += ("&tasktype=" + tasktype);
        }
        return urlParam;
    }

    @Override
    public List<SearchCromb> getSearchCrombs(AssignSearchParam assignSearchParam, List<Catalog1> catalog1, List<TbTaskModeType> taskModeType, List<TbTaskTimeType> taskTimeType) {
        List<SearchCromb> searchCrombs = new ArrayList<>();
        String categoryid = assignSearchParam.getCategoryid();
        String taskTimeTypeId = assignSearchParam.getTaskTimeTypeId();
        String tasktype = assignSearchParam.getTasktype();

        if (StringUtils.isNotBlank(categoryid)) {
            SearchCromb searchCromb = new SearchCromb();
            Iterator<Catalog1> iterator = catalog1.iterator();
            while (iterator.hasNext()) {
                Catalog1 next = iterator.next();
                Integer id = next.getId();
                if (id == Integer.parseInt(categoryid)) {
                    searchCromb.setValueName(next.getName());
                }
            }
            searchCromb.setValueId(categoryid);
            searchCromb.setUrlParam(getUrlParamByCrombCata(assignSearchParam));
            searchCrombs.add(searchCromb);
        }
        if (StringUtils.isNotBlank(taskTimeTypeId)) {
            SearchCromb searchCromb = new SearchCromb();
            Iterator<TbTaskTimeType> iterator = taskTimeType.iterator();
            while (iterator.hasNext()) {
                TbTaskTimeType next = iterator.next();
                Integer id = next.getId();
                if (id == Integer.parseInt(taskTimeTypeId)) {
                    searchCromb.setValueName(next.getTaskTimeType());
                }
            }
            searchCromb.setValueId(taskTimeTypeId);
            searchCromb.setUrlParam(getUrlParamByCrombTime(assignSearchParam));
            searchCrombs.add(searchCromb);
        }
        if (StringUtils.isNotBlank(tasktype)) {
            SearchCromb searchCromb = new SearchCromb();
            Iterator<TbTaskModeType> iterator = taskModeType.iterator();
            while (iterator.hasNext()) {
                TbTaskModeType next = iterator.next();
                Integer id = next.getId();
                if (id == Integer.parseInt(tasktype)) {
                    searchCromb.setValueName(next.getTaskModeType());
                }
            }
            searchCromb.setValueId(tasktype);
            searchCromb.setUrlParam(getUrlParamByCrombMode(assignSearchParam));
            searchCrombs.add(searchCromb);
        }
        return searchCrombs;
    }

    @Override
    public Long getAssignmentFromESTotal(AssignSearchParam assignSearchParam) {

        String categoryid = assignSearchParam.getCategoryid();
        String taskTimeTypeId = assignSearchParam.getTaskTimeTypeId();
        String keyword = assignSearchParam.getKeyword();
        String tasktype = assignSearchParam.getTasktype();

        List<AssignmentES> assignmentESs = new ArrayList<>();
        //jest 的dsl 工具
        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        //bool
        BoolQueryBuilder boolQueryBuilder = new BoolQueryBuilder();
        if (StringUtils.isNotBlank(categoryid)) {
            //term
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("categoryId", categoryid);
            // filter
            boolQueryBuilder.filter(termQueryBuilder);
        }
        if (StringUtils.isNotBlank(tasktype)) {
            TermQueryBuilder termQueryBuilder = new TermQueryBuilder("taskDetailType", Integer.parseInt(tasktype) - 1);
            boolQueryBuilder.filter(termQueryBuilder);
        }
        if (StringUtils.isNotBlank(keyword)) {
            //must
            MatchQueryBuilder matchQueryBuilder = new MatchQueryBuilder("title", keyword);
            boolQueryBuilder.must(matchQueryBuilder);
//            MatchQueryBuilder matchQueryBuilder1 = new MatchQueryBuilder("details", keyword);
//            boolQueryBuilder.must(matchQueryBuilder1);
        }
        if (StringUtils.isNotBlank(taskTimeTypeId)) {
            if (taskTimeTypeId.equals("1")) {
                //term
                RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("urgent");
                rangeQueryBuilder.gt(0);
                // filter
                boolQueryBuilder.filter(rangeQueryBuilder);
            }
            if (taskTimeTypeId.equals("2")){
                RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("differTimeHour");
                rangeQueryBuilder.lte(24);
                rangeQueryBuilder.gt(0);
                // filter
                boolQueryBuilder.filter(rangeQueryBuilder);
            }
            if (taskTimeTypeId.equals("3")){
                RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("differTimeHour");
                rangeQueryBuilder.lte(72);
                rangeQueryBuilder.gt(0);
                // filter
                boolQueryBuilder.filter(rangeQueryBuilder);
            }
            if (taskTimeTypeId.equals("4")){
                RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("differTimeHour");
                rangeQueryBuilder.lte(168);
                rangeQueryBuilder.gt(0);
                // filter
                boolQueryBuilder.filter(rangeQueryBuilder);
            }
            if (taskTimeTypeId.equals("5")){
                RangeQueryBuilder rangeQueryBuilder = new RangeQueryBuilder("differTimeHour");
                rangeQueryBuilder.lte(0);
                // filter
                boolQueryBuilder.filter(rangeQueryBuilder);
            }
        }
        //query
        searchSourceBuilder.query(boolQueryBuilder);
        //from
        // highlight
        searchSourceBuilder.highlighter(null);
        //sort
//        searchSourceBuilder.sort("browsePeopleNum", SortOrder.DESC);
        Search build = new Search.Builder(searchSourceBuilder.toString()).addIndex("jiaoyiyu").addType("assignment").build();
        SearchResult execute = null;
        try {
            execute = jestClient.execute(build);
        } catch (IOException e) {
            e.printStackTrace();
        }

        List<SearchResult.Hit<AssignmentES, Void>> hits = execute.getHits(AssignmentES.class);
        Long total = execute.getTotal();

        return total;
    }


    private String getUrlParamByCrombCata(AssignSearchParam assignSearchParam) {
        String urlParam = "";
        String categoryid = assignSearchParam.getCategoryid();
        String taskTimeTypeId = assignSearchParam.getTaskTimeTypeId();
        String keyword = assignSearchParam.getKeyword();
        String tasktype = assignSearchParam.getTasktype();

        if (StringUtils.isNotBlank(categoryid)) {
//            urlParam += ("categoryid=" + categoryid);
        }else {
            if (StringUtils.isNotBlank(keyword)) {
                urlParam += ("keyword=" + keyword);
            }
            if (StringUtils.isNotBlank(taskTimeTypeId)) {
                urlParam += ("&taskTimeTypeId=" + taskTimeTypeId);
            }
            if (StringUtils.isNotBlank(tasktype)) {
                urlParam += ("&tasktype=" + tasktype);
            }
            return urlParam;
        }

        if (StringUtils.isNotBlank(keyword)) {
            urlParam += ("&keyword=" + keyword);
        }
        if (StringUtils.isNotBlank(taskTimeTypeId)) {
            urlParam += ("&taskTimeTypeId=" + taskTimeTypeId);
        }
        if (StringUtils.isNotBlank(tasktype)) {
            urlParam += ("&tasktype=" + tasktype);
        }
        return urlParam;
    }
    private String getUrlParamByCrombMode(AssignSearchParam assignSearchParam) {
        String urlParam = "";
        String categoryid = assignSearchParam.getCategoryid();
        String taskTimeTypeId = assignSearchParam.getTaskTimeTypeId();
        String keyword = assignSearchParam.getKeyword();
        String tasktype = assignSearchParam.getTasktype();

        if (StringUtils.isNotBlank(categoryid)) {
            urlParam += ("categoryid=" + categoryid);
        }else {
            if (StringUtils.isNotBlank(keyword)) {
                urlParam += ("keyword=" + keyword);
            }
            if (StringUtils.isNotBlank(taskTimeTypeId)) {
                urlParam += ("&taskTimeTypeId=" + taskTimeTypeId);
            }
            if (StringUtils.isNotBlank(tasktype)) {
//                urlParam += ("&tasktype=" + tasktype);
            }
            return urlParam;
        }

        if (StringUtils.isNotBlank(keyword)) {
            urlParam += ("&keyword=" + keyword);
        }
        if (StringUtils.isNotBlank(taskTimeTypeId)) {
            urlParam += ("&taskTimeTypeId=" + taskTimeTypeId);
        }
        if (StringUtils.isNotBlank(tasktype)) {
//            urlParam += ("&tasktype=" + tasktype);
        }
        return urlParam;
    }
    private String getUrlParamByCrombTime(AssignSearchParam assignSearchParam) {
        String urlParam = "";
        String categoryid = assignSearchParam.getCategoryid();
        String taskTimeTypeId = assignSearchParam.getTaskTimeTypeId();
        String keyword = assignSearchParam.getKeyword();
        String tasktype = assignSearchParam.getTasktype();

        if (StringUtils.isNotBlank(categoryid)) {
            urlParam += ("categoryid=" + categoryid);
        }else {
            if (StringUtils.isNotBlank(keyword)) {
                urlParam += ("keyword=" + keyword);
            }
            if (StringUtils.isNotBlank(taskTimeTypeId)) {
//                urlParam += ("&taskTimeTypeId=" + taskTimeTypeId);
            }
            if (StringUtils.isNotBlank(tasktype)) {
                urlParam += ("&tasktype=" + tasktype);
            }
            return urlParam;
        }

        if (StringUtils.isNotBlank(keyword)) {
            urlParam += ("&keyword=" + keyword);
        }
        if (StringUtils.isNotBlank(taskTimeTypeId)) {
//            urlParam += ("&taskTimeTypeId=" + taskTimeTypeId);
        }
        if (StringUtils.isNotBlank(tasktype)) {
            urlParam += ("&tasktype=" + tasktype);
        }
        return urlParam;
    }
}
