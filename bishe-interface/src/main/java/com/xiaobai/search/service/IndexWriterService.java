package com.xiaobai.search.service;

import com.xiaobai.gossip.pojo.News;

import java.util.List;

/**
 * @program: bishe-parent
 * @description: 索引写入接口
 * @author: xiaobai
 **/
public interface IndexWriterService {
    /**
     * 多个新闻写入
     *
     * @param newsList
     * @throws Exception
     */
    public void saveBeans(List<News> newsList,String fenlei) throws Exception;

    /**
     * 单个新闻写入
     *
     * @param news
     * @throws Exception
     */
    public void savenews(News news,String fenlei) throws Exception;
}
