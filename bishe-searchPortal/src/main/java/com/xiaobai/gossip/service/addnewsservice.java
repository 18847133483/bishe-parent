package com.xiaobai.gossip.service;


import com.xiaobai.gossip.pojo.News;
import com.xiaobai.gossip.pojo.minnews;

public interface addnewsservice {
    /**
     * 获取前段用户给的新闻内容
     *
     * @param minnews
     * @return
     * @throws Exception
     */
    public News savenews(minnews minnews) throws Exception;
}
