package com.xiaobai.gossip.service.impl;

import com.alibaba.dubbo.config.annotation.Reference;
import com.xiaobai.gossip.mapper.NewsMapper;
import com.xiaobai.gossip.pojo.News;
import com.xiaobai.gossip.pojo.PageBean;
import com.xiaobai.gossip.pojo.ResultBean;
import com.xiaobai.gossip.service.NewsService;
import com.xiaobai.search.service.IndexSearcherService;
import com.xiaobai.search.service.IndexWriterService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class NewsServiceImpl implements NewsService {
    //从本地spring容器注入
    @Autowired
    private NewsMapper newsMapper;
    //从远程dubbo注入
    //timeout设置超时时间
    @Reference(timeout = 5000)
    private IndexWriterService indexWriterService;
    //索引搜索服务
    @Reference(timeout = 5000)
    private IndexSearcherService indexSearcherService;
    @Autowired
    private JedisPool jedisPool;

    /**
     * 1，获取数据库中的数据
     * 2. 调用远程索引写入服务，将新闻数据写入索引库
     * 3. 记录最大的id
     *
     * @throws Exception
     */
    @Override
    public void newsIndexWriter() throws Exception {
        //mysql表格的名字
        String tablecaijng="wynewscaijing";
        String tablekeji="wynewskeji";
        String tableshishang="wynewsshishang";
        String tabletiyu="wynewstiyu";
        String tablexinwen="wynewsxinwen";
        String tableyule="wynewsyule";
        //jedis的名字
        String jediscaijing="spider:wy:caijingid";
        String jedisshishang="spider:wy:shishangid";
        String jediskeji="spider:wy:kejiid";
        String jedistiyu="spider:wy:tiyuid";
        String jedisxinwen="spider:wy:xinwenid";
        String jedisyule="spider:wy:yuleid";
        //1.访问redis获取最大的id：maxid
        Jedis jedis = jedisPool.getResource();
        String caijingmaxid = jedis.get(jediscaijing);
        String kejimaxid = jedis.get(jediskeji);
        String shishangmaxid = jedis.get(jedisshishang);
        String tiyumaxid = jedis.get(jedistiyu);
        String xinwenmaxid = jedis.get(jedisxinwen);
        String yulemaxid = jedis.get(jedisyule);
        jedis.close();
        writenews(tablecaijng,jediscaijing,caijingmaxid);
        writenews(tablekeji,jediskeji,kejimaxid);
        writenews(tableshishang,jedisshishang,shishangmaxid);
        writenews(tabletiyu,jedistiyu,tiyumaxid);
        writenews(tablexinwen,jedisxinwen,xinwenmaxid);
        writenews(tableyule,jedisyule,yulemaxid);
    }

    private void writenews(String table, String jedisname, String maxid) throws Exception {
        if (StringUtils.isEmpty(maxid)) {
            maxid = "0";
        }
        while (true) {
            //2.调用dao，获取新闻列表数据
            List<News> newsList = newsMapper.querytonews(table,maxid);
            //跳出循环的逻辑
            if (newsList == null || newsList.size() <= 0) {
                //将nextMaxId写入redis中，为下次使用
                Jedis jedis = jedisPool.getResource();
                jedis.set(jedisname, maxid);
                jedis.close();
                break;
            }
            //3. 调用索引写入服务，将新闻数据写入索引库
            SimpleDateFormat formatOld = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat formatNew = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            for (News news : newsList) {
                //从数据库中获取的日期：2019-01-14 09:52:53
                String timeOld = news.getTime();
                if (StringUtils.isEmpty(timeOld)) {
                    timeOld = "2000-01-21 01:01:01";
                }
                //转成日期
                Date oldDate = formatOld.parse(timeOld);
                //格式化成solr需要的格式
                String timeNew = formatNew.format(oldDate);
                news.setTime(timeNew);

            }
            indexWriterService.saveBeans(newsList,table);
            //4. 获取这一页数据的最大id
            maxid = newsMapper.maxid(table,maxid);
            //System.out.println(table+"写入索引库："+ newsList.size());
        }
    }

    /**
     * 根据关键字进行索引库搜索
     *
     * @param resultBean ： 查询条件的封装类
     * @return
     * @throws Exception
     */
    @Override
    public List<News> findByKeywords(ResultBean resultBean) throws Exception {
        //1. 调用索引搜索服务，完成关键字搜索
        List<News> newsList = indexSearcherService.findByKeywords(resultBean);


        //处理新闻列表：内容如果太多，只显示70个字
        for (News news : newsList) {
            String content = news.getContent();

            if (StringUtils.isNotEmpty(content) && (content.length() > 70)) {
                content = content.substring(0, 69) + "...";
                news.setContent(content);
            }
        }
        //返回新闻列表
        return newsList;
    }

    @Override
    public News finfid(String id, String fenlei) throws Exception {
        News news = indexSearcherService.findid(id, fenlei);
        return news;
    }

    /**
     * 分页查询
     *
     * @param resultBean :  查询条件及分页条件
     * @return 分页的数据及条件
     * @throws Exception
     */
    @Override
    public PageBean findByPageQuery(ResultBean resultBean) throws Exception {
        //1. 调用索引搜索服务，完成关键字搜索
        PageBean pageBean = indexSearcherService.findByPageQuery(resultBean);
        //处理新闻列表：内容如果太多，只显示70个字
        for (News news : pageBean.getNewsList()) {
            String content = news.getContent();

            if (StringUtils.isNotEmpty(content) && (content.length() > 250)) {
                content = content.substring(0, 250) + "...";
                news.setContent(content);
            }
        }
        //返回新闻分页数据
        return pageBean;
    }
}
