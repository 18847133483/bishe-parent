package com.xiaobai.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xiaobai.gossip.pojo.News;
import com.xiaobai.search.service.IndexWriterService;
import org.apache.solr.client.solrj.SolrServer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

//将索引写入服务注册到dubbo上去
@Service
public class IndexWriterServiceImpl implements IndexWriterService {

    //从spring容器中注入cloudSolrServer
    @Autowired
    @Qualifier("caijing")
    private SolrServer solrServer;

    /**
     * 索引写入的方法
     *
     * @param newsList 新闻列表
     * @throws Exception
     */
    @Override
    public void saveBeans(List<News> newsList) throws Exception {

        solrServer.addBeans(newsList);
        solrServer.commit();
    }

    @Override
    public void savenews(News news) throws Exception {
        solrServer.addBean(news);
        solrServer.commit();
    }
}
