package com.ruoyi.system.controller;

import cn.hutool.http.HttpStatus;
import com.ruoyi.common.core.domain.R;
import com.ruoyi.common.core.web.controller.BaseController;
import com.ruoyi.common.log.annotation.Log;
import com.ruoyi.common.log.enums.BusinessType;
import com.ruoyi.common.mybatis.core.page.PageQuery;
import com.ruoyi.common.mybatis.core.page.TableDataInfo;
import com.ruoyi.system.domain.EsSysUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.elasticsearch.index.query.BoolQueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.search.sort.SortBuilders;
import org.elasticsearch.search.sort.SortOrder;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.elasticsearch.core.ElasticsearchRestTemplate;
import org.springframework.data.elasticsearch.core.SearchHit;
import org.springframework.data.elasticsearch.core.SearchHits;
import org.springframework.data.elasticsearch.core.query.NativeSearchQuery;
import org.springframework.data.elasticsearch.core.query.NativeSearchQueryBuilder;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * 在线用户监控
 *
 * @author Lion Li
 */
@Api(value = "用户搜索", tags = {"用户搜索"})
@RestController
@RequestMapping("/es")
public class UserSearchController extends BaseController {

    @Resource
    ElasticsearchRestTemplate elasticsearchRestTemplate ;


    @ApiOperation("新增或者修改用户")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/add")
    public R<Void> add( @RequestBody EsSysUser user) {
         elasticsearchRestTemplate.save(user);
        return R.ok();
    }


 /*   @ApiOperation("修改用户")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/update")
    public R<Void> update( @RequestBody EsSysUser user) {
       // EsSysUser temp =  elasticsearchRestTemplate.update();
        return R.ok();
    }*/

    @ApiOperation("删除")
    @Log(title = "用户管理", businessType = BusinessType.INSERT)
    @PostMapping("/delete")
    public R<Void> delete( @RequestBody EsSysUser user) {
        elasticsearchRestTemplate.delete(user.getId().toString(),EsSysUser.class);
        return R.ok();
    }


    /**
     * 构建条件
     * @param user
     * @return
     */
    private BoolQueryBuilder buildBoolQueryBuilder(EsSysUser user) {
        BoolQueryBuilder boolQueryBuilder = QueryBuilders.boolQuery();
        if (Objects.nonNull(user.getUserType())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("user_type",user.getUserType()));
        }
        /**
         * 完全匹配
         */
       /* if (Objects.nonNull(user.getNickName())) {
            boolQueryBuilder.must(QueryBuilders.termQuery("nick_name", user.getNickName()));
        }*/
        /**
         * 模糊搜索
         */
        if (Objects.nonNull(user.getUserName())) {
            boolQueryBuilder.must(QueryBuilders.wildcardQuery("user_name","*"+user.getUserName()+"*"));
        }

        return boolQueryBuilder;
    }

    @ApiOperation("搜索列表")
    @GetMapping("/list")
    public TableDataInfo<EsSysUser> list(EsSysUser user,PageQuery pageQuery) {
        BoolQueryBuilder query  =  buildBoolQueryBuilder(user);
        NativeSearchQuery nativeSearchQuery = new NativeSearchQueryBuilder()
           .withQuery(query)
            .withPageable(PageRequest.of(pageQuery.getPageNum()-1,pageQuery.getPageSize()))
            .withSorts(SortBuilders.fieldSort("id").order(SortOrder.DESC))
            .build();
        SearchHits<EsSysUser> result  = elasticsearchRestTemplate.search(nativeSearchQuery, EsSysUser.class);
        long count =  elasticsearchRestTemplate.count(nativeSearchQuery,EsSysUser.class);
        List<SearchHit<EsSysUser>> temp = 	result.getSearchHits();
        List<EsSysUser> resultList = temp.stream().map(SearchHit::getContent).collect(Collectors.toList());
        TableDataInfo<EsSysUser> rspData = new TableDataInfo<>();
        rspData.setCode(HttpStatus.HTTP_OK);
        rspData.setMsg("查询成功");
        rspData.setRows(resultList);
        rspData.setTotal(count);
        return rspData;
    }

}
