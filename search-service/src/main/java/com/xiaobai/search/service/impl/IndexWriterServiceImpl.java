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
    private SolrServer caijingSolrServer;
    @Autowired
    @Qualifier("keji")
    private SolrServer kejiSolrServer;
    @Autowired
    @Qualifier("shishang")
    private SolrServer shishangSolrServer;
    @Autowired
    @Qualifier("tiyu")
    private SolrServer tiyuSolrServer;
    @Autowired
    @Qualifier("xinwen")
    private SolrServer xinwenSolrServer;
    @Autowired
    @Qualifier("yule")
    private SolrServer yuleSolrServer;

    /**
     * 索引写入的方法
     *
     * @param newsList 新闻列表
     * @throws Exception
     */
    @Override
    public void saveBeans(List<News> newsList,String fenlei) throws Exception {
        if (fenlei.equals("wynewscaijing")) {
            caijingSolrServer.addBeans(newsList);
            caijingSolrServer.commit();
        }
        if (fenlei.equals("wynewskeji")) {
            kejiSolrServer.addBeans(newsList);
            kejiSolrServer.commit();
        }
        if (fenlei.equals("wynewsshishang")) {
            shishangSolrServer.addBeans(newsList);
            shishangSolrServer.commit();
        }
        if (fenlei.equals("wynewstiyu")) {
            tiyuSolrServer.addBeans(newsList);
            tiyuSolrServer.commit();
        }
        if (fenlei.equals("wynewsxinwen")) {
            xinwenSolrServer.addBeans(newsList);
            xinwenSolrServer.commit();
        }
        if (fenlei.equals("wynewsyule")) {
            yuleSolrServer.addBeans(newsList);
            yuleSolrServer.commit();
        }

    }

    @Override
    public void savenews(News news,String fenlei) throws Exception {
        if (fenlei.equals("caijing")) {
            caijingSolrServer.addBean(news);
            caijingSolrServer.commit();
        }
        if (fenlei.equals("keji")) {
            kejiSolrServer.addBean(news);
            kejiSolrServer.commit();
        }
        if (fenlei.equals("shishang")) {
            shishangSolrServer.addBean(news);
            shishangSolrServer.commit();
        }
        if (fenlei.equals("tiyu")) {
            tiyuSolrServer.addBean(news);
            tiyuSolrServer.commit();
        }
        if (fenlei.equals("xinwen")) {
            xinwenSolrServer.addBean(news);
            xinwenSolrServer.commit();
        }
        if (fenlei.equals("yule")) {
            yuleSolrServer.addBean(news);
            yuleSolrServer.commit();
        }
    }
}
