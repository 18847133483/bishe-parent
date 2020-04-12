package com.xiaobai.gossip.service;

import com.xiaobai.gossip.pojo.News;
import com.xiaobai.gossip.pojo.PageBean;
import com.xiaobai.gossip.pojo.ResultBean;

import java.util.List;

/**
 * 新闻服务层
 */
public interface NewsService {
    /**
     * 1，获取数据库中的数据
     * 2. 调用远程索引写入服务，将新闻数据写入索引库
     * 3. 记录最大的id
     *
     * @throws Exception
     */
    public void newsIndexWriter() throws Exception;

    /**
     * 根据关键字进行索引库搜索
     *
     * @param resultBean ： 封装查询条件
     * @return
     * @throws Exception
     */
    public List<News> findByKeywords(ResultBean resultBean) throws Exception;

    /**
     * 根据id获取一条新闻
     *
     * @param id
     * @return
     * @throws Exception
     */
    public News finfid(String id, String fenlei) throws Exception;

    /**
     * 分页查询
     *
     * @param resultBean :  查询条件及分页条件
     * @return 分页的数据及条件
     * @throws Exception
     */
    public PageBean findByPageQuery(ResultBean resultBean) throws Exception;
}
