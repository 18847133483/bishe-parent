package com.xiaobai.gossip.timing;

import com.xiaobai.gossip.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Repository;

/**
 * 定时任务类
 */
//将对象放到spring容器中
@Repository
public class IndexWriterTiming {
    @Autowired
    private NewsService newsService;
    //设置这个方法是定时执行： 每隔15分钟执行一次
    @Scheduled(cron = "0 0/1 * * * ?")
    public void indexWriter(){
        try {
            newsService.newsIndexWriter();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
