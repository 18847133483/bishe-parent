package com.xiaobai.gossip.mapper;

import com.xiaobai.gossip.pojo.News;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 新闻mapper对象
 */
public interface NewsMapper {
    /**
     * 查询新闻数据(一次取100条数据)
     *
     * @param id ： 获取数据的起始id（上100条数据的最大id）
     * @return sql: select * from news where id > 参数  limit 0,100
     */
    public List<News> querytonews(@Param("table") String table, @Param("id")String id);
    /**
     * 获取当前100数据的最大id值
     *
     * @param id 最大的id
     * @return select max(id) from (select * from news where id > 参数  limit 0,100 ) as temp
     */
    public String maxid(@Param("table") String table, @Param("id")String id);

}
