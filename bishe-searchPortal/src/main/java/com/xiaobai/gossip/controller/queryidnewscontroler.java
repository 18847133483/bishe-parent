package com.xiaobai.gossip.controller;

import com.xiaobai.gossip.pojo.News;
import com.xiaobai.gossip.service.NewsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 根据id查找新闻返回给前端
 */
@Controller
public class queryidnewscontroler {
    @Autowired
    private NewsService newsService;

    @ResponseBody
    @RequestMapping(value = "/queryid", method = RequestMethod.GET)
    public ModelAndView querynews(String id, String fenlei, ModelAndView model) throws Exception {
        //1. 获取前端传递的参数
        if (id == null) {
            model.setViewName("forward:add02.html");
            return model;
        }
        //3.调用服务层进行查询
        News news = newsService.finfid(id, fenlei);
        model.addObject(news);
        model.setViewName("forward:shownews.jsp");
        return model;
    }
}
