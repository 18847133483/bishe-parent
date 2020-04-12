package com.xiaobai.gossip.controller;

import com.xiaobai.gossip.pojo.News;
import com.xiaobai.gossip.pojo.PageBean;
import com.xiaobai.gossip.pojo.ResultBean;
import com.xiaobai.gossip.service.NewsService;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 分页查询和关键词查询
 */
@Controller
public class NewsController {

    @Autowired
    private NewsService newsService;


    /**
     * 根据关键字进行搜索查询
     *
     * @param resultBean 查询条件的封装类： 关键字  起始时间  结束时间   来源  编辑
     * @return
     */
    @ResponseBody
    @RequestMapping("/s")
    public List<News> findByKeywords(ResultBean resultBean) {

        try {
            //1. 获取前端传递的参数
            if (resultBean == null) {
                return null;
            }

            //2. 判断前端的参数是否传递正确
            if (StringUtils.isEmpty(resultBean.getKeywords())) {
                //返回首页: 如果返回的数据是一个null值, 跳转首页
                return null;
            }

            //3.调用服务层进行查询
            List<News> newsList = newsService.findByKeywords(resultBean);

            return newsList;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    /**
     * 分页查询的方法
     *
     * @param resultBean ： 前端页面传递的查询条件和分页条件
     * @return pageBean  : 分页数据对象(分页条件和分页数据)
     */
    @RequestMapping("/ps")
    @ResponseBody
    public PageBean findByPageQuery(ResultBean resultBean) {

        try {
            //1. 获取前端传递的参数
            if (resultBean == null) {
                return null;
            }

            //2. 判断前端的参数是否传递正确
            if (StringUtils.isEmpty(resultBean.getKeywords())) {
                //返回首页: 如果返回的数据是一个null值, 跳转首页
                return null;
            }
            String fenlei = resultBean.getFenlei();
            System.out.println(fenlei);

            //如果没有分页条件，需要自己实例化pagebean对象
            if (resultBean.getPageBean() == null) {
                PageBean pageBean = new PageBean();
                //设置到resultBean里面
                resultBean.setPageBean(pageBean);
            }

            //3.调用服务层进行查询
            PageBean pageBean = newsService.findByPageQuery(resultBean);
            System.out.println(pageBean.toString());
            return pageBean;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }
}
