package com.xiaobai.gossip.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xiaobai.gossip.pojo.News;
import com.xiaobai.gossip.pojo.minnews;
import com.xiaobai.gossip.service.addnewsservice;
import com.xiaobai.gossip.util.TimeUtil;
import com.xiaobai.search.service.IndexWriterService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.Date;


@Service
public class addnewsserviceimpl implements addnewsservice {
    @Reference(timeout = 5000)
    private IndexWriterService indexWriterService;
    @Autowired
    private JedisPool jedisPool;
    @Override
    public News savenews(minnews minnews) throws Exception {
        Jedis jedis = jedisPool.getResource();
        String userdefind = jedis.get("user-defined");
        jedis.close();
        News news = new News();
        String id = TimeUtil.suijishu();
        news.setId(id);
        news.setTitle(minnews.getTitle());
        String time = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'").format(new Date()).toString();;
        news.setTime(time);
        news.setContent(minnews.getContent());
        news.setEditor("UD_"+minnews.getEditor());
        news.setSource("UD_"+minnews.getSource());
        news.setUrl("未定义");
        indexWriterService.savenews(news);
        return news;
    }
}
