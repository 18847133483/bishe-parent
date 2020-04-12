package com.xiaobai.search.service;

import com.xiaobai.gossip.pojo.News;
import com.xiaobai.gossip.pojo.PageBean;
import com.xiaobai.gossip.pojo.ResultBean;

import java.util.List;

/**
 * @program: bishe-parent
 * @description: 索引查询接口
 * @author: xiaobai
 **/
public interface IndexSearcherService {
    /**
     * 根据关键词查询
     *
     * @param resultBean
     * @return
     * @throws Exception
     */
    public List<News> findByKeywords(ResultBean resultBean) throws Exception;

    /**
     * 根据分页对象查询
     *
     * @param resultBean
     * @return
     * @throws Exception
     */
    public PageBean findByPageQuery(ResultBean resultBean) throws Exception;

    /**
     * 根据id进行查询
     *
     * @param id     新闻的id
     * @param fenlei 新闻索引分类
     * @return
     * @throws Exception
     */
    public News findid(String id, String fenlei) throws Exception;
}
