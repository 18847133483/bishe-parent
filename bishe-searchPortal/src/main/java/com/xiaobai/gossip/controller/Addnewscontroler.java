package com.xiaobai.gossip.controller;

import com.xiaobai.gossip.pojo.News;
import com.xiaobai.gossip.pojo.minnews;
import com.xiaobai.gossip.service.addnewsservice;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 * 从网页田间单个新闻对象
 */
@Controller
public class Addnewscontroler {
    @Autowired
    private addnewsservice addnewsservice;

    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public ModelAndView addnews(minnews minnews, ModelAndView model) throws Exception {
        //1. 获取前端传递的参数
        if (minnews == null) {
            model.setViewName("forward:add02.html");
            return model;
        }

        //2. 判断前端的参数是否传递正确
        if (StringUtils.isEmpty(minnews.getTitle())) {
            model.setViewName("forward:add02.html");
            return model;
        }

        //3.调用服务层进行查询
        System.out.println(minnews);
        News savenews = addnewsservice.savenews(minnews);
        model.addObject(savenews);
        model.setViewName("forward:shownews.jsp");
        return model;
    }

}
