package com.xiaobai.search.service.impl;

import com.alibaba.dubbo.config.annotation.Service;
import com.xiaobai.gossip.pojo.News;
import com.xiaobai.gossip.pojo.PageBean;
import com.xiaobai.gossip.pojo.ResultBean;
import com.xiaobai.search.service.IndexSearcherService;
import org.apache.commons.lang3.StringUtils;
import org.apache.solr.client.solrj.SolrQuery;
import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.client.solrj.response.QueryResponse;
import org.apache.solr.common.SolrDocument;
import org.apache.solr.common.SolrDocumentList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
//注册到dubbo
@Service
public class IndexSearcherServiceImpl implements IndexSearcherService {


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
     * 根据关键词进行搜索
     *
     * @param resultBean ： 查询条件的封装类： 关键字  起始时间 结束时间  来源  编辑
     * @return 新闻列表
     * @throws Exception
     */
    @Override
    public List<News> findByKeywords(ResultBean resultBean) throws Exception {

        //1. 封装查询条件
        SolrQuery solrQuery = new SolrQuery("text:" + resultBean.getKeywords());

        //1.1 添加高亮的内容
        //开启高亮
        solrQuery.setHighlight(true);
        //设置高亮字段
        solrQuery.addHighlightField("title");
        solrQuery.addHighlightField("content");
        //设置前缀和后缀
        solrQuery.setHighlightSimplePre("<em style='color:red'>");
        solrQuery.setHighlightSimplePost("</em>");


        //1.2 组装查询工具条里面的条件：起始时间 结束时间  来源  编辑
        String sourceQ = resultBean.getSource();
        if (StringUtils.isNotEmpty(sourceQ)) {
            solrQuery.addFilterQuery("source:" + sourceQ);
        }
        String editorQ = resultBean.getEditor();
        if (StringUtils.isNotEmpty(editorQ)) {
            solrQuery.addFilterQuery("editor:" + editorQ);
        }

        String startDate = resultBean.getStartDate();
        String endDate = resultBean.getEndDate();

        //前端传递日期格式：01/21/2019 11:53:32  MM/dd/yyyy HH:mm:ss
        if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            //时间范围查询
            //日期范围查询:  UTC格式:  yyyy-MM-dd'T'HH:mm:ss'Z'
            //  12/12/2018 15:25:52 :  MM/dd/yyyy HH:mm:ss
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


            //转换成日期类型
            Date startDate2 = format2.parse(startDate);
            //转换成我们想要的utf格式的字符串
            startDate = format1.format(startDate2);

            Date endDate2 = format2.parse(endDate);
            endDate = format1.format(endDate2);


            solrQuery.addFilterQuery("time:[" + startDate + " TO " + endDate + "]");
        }


        //1.3 排序条件： 日期倒序排列
        solrQuery.setSort("time", SolrQuery.ORDER.desc);


        //2. 执行查询
        String fenlei = resultBean.getFenlei();
        System.out.println(fenlei);
        QueryResponse response = null;
        if (fenlei == "caijing") {
            response = caijingSolrServer.query(solrQuery);
        } else if (fenlei == "keji") {
            response = kejiSolrServer.query(solrQuery);
        } else if (fenlei == "shishang") {
            response = shishangSolrServer.query(solrQuery);
        } else if (fenlei == "tiyu") {
            response = tiyuSolrServer.query(solrQuery);
        } else if (fenlei == "xinwen") {
            response = xinwenSolrServer.query(solrQuery);
        } else if (fenlei == "yule") {
            response = yuleSolrServer.query(solrQuery);
        } else {
            response = caijingSolrServer.query(solrQuery);
        }
        //2.1 获取结果(不带高亮的内容)
        SolrDocumentList documentList = response.getResults();
        //System.out.println(documentList.toString());
        //System.out.println("查询了一下");


        //2.2 获取高亮的结果内容
        Map<String, Map<String, List<String>>> map = response.getHighlighting();

        //处理结果：减去8小时
//        SimpleDateFormat solrFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ArrayList<News> newsList = new ArrayList<>();
        for (SolrDocument document : documentList) {

            News news = new News();
            String id = (String) document.get("id");
            String title = (String) document.get("title");
            String content = (String) document.get("content");
            String editor = (String) document.get("editor");
            String url = (String) document.get("url");
            String source = (String) document.get("source");

            //获取高亮的内容
            Map<String, List<String>> mapList = map.get(id);

            //获取title的高亮内容
            List<String> titleList = mapList.get("title");
            if (titleList != null && titleList.size() > 0) {
                title = titleList.get(0);
            }

            //获取content的高亮内容
            List<String> contentList = mapList.get("content");
            if (contentList != null && contentList.size() > 0) {
                content = contentList.get(0);
            }

            news.setId(id);
            news.setTitle(title);
            news.setContent(content);
            news.setEditor(editor);
            news.setUrl(url);
            news.setSource(source);

            //获取时间
            Date time = (Date) document.get("time");
            //将solr的格式转换成日期
            //减去8小时
            time = new Date(time.getTime() - 1000 * 60 * 60 * 8);
            //格式化成我们想要格式
            String timeString = format.format(time);

            //重新设置会新闻对象上面
            news.setTime(timeString);

            //添加到新闻列表中
            newsList.add(news);
        }

        //3. 返回结果
        return newsList;
    }

    /**
     * 分页条件查询的方法
     *
     * @param resultBean ：  查询条件和分页条件
     * @return 分页条件及分页数据javabean对象
     * @throws Exception
     */
    @Override
    public PageBean findByPageQuery(ResultBean resultBean) throws Exception {

        //1. 封装查询条件
        SolrQuery solrQuery = new SolrQuery("text:" + resultBean.getKeywords());

        //1.1 添加高亮的内容
        //开启高亮
        solrQuery.setHighlight(true);
        //设置高亮字段
        solrQuery.addHighlightField("title");
        solrQuery.addHighlightField("content");
        //设置前缀和后缀
        solrQuery.setHighlightSimplePre("<em style='color:red'>");
        solrQuery.setHighlightSimplePost("</em>");


        //1.2 组装查询工具条里面的条件：起始时间 结束时间  来源  编辑
        String sourceQ = resultBean.getSource();
        if (StringUtils.isNotEmpty(sourceQ)) {
            solrQuery.addFilterQuery("source:" + sourceQ);
        }
        String editorQ = resultBean.getEditor();
        if (StringUtils.isNotEmpty(editorQ)) {
            solrQuery.addFilterQuery("editor:" + editorQ);
        }

        String startDate = resultBean.getStartDate();
        String endDate = resultBean.getEndDate();

        //前端传递日期格式：01/21/2019 11:53:32  MM/dd/yyyy HH:mm:ss
        if (StringUtils.isNotEmpty(startDate) && StringUtils.isNotEmpty(endDate)) {
            //时间范围查询
            //日期范围查询:  UTC格式:  yyyy-MM-dd'T'HH:mm:ss'Z'
            //  12/12/2018 15:25:52 :  MM/dd/yyyy HH:mm:ss
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
            SimpleDateFormat format2 = new SimpleDateFormat("MM/dd/yyyy HH:mm:ss");


            //转换成日期类型
            Date startDate2 = format2.parse(startDate);
            //转换成我们想要的utf格式的字符串
            startDate = format1.format(startDate2);

            Date endDate2 = format2.parse(endDate);
            endDate = format1.format(endDate2);


            solrQuery.addFilterQuery("time:[" + startDate + " TO " + endDate + "]");
        }


        //1.3 排序条件： 日期倒序排列
        //solrQuery.setSort("time", SolrQuery.ORDER.desc);


        //1.4 分页条件 ： start  rows
        //当前页
        Integer page = resultBean.getPageBean().getPage();
        Integer pageSize = resultBean.getPageBean().getPageSize();
        Integer start = (page - 1) * pageSize;
        solrQuery.setStart(start);
        solrQuery.setRows(pageSize);


        //2. 执行查询
        String fenlei = resultBean.getFenlei();
        System.out.println(fenlei);
        QueryResponse response = null;
        if (fenlei.equals("caijing")) {
            response = caijingSolrServer.query(solrQuery);
            System.out.println("索引库:财经");
        }
        if (fenlei.equals("keji")) {
            response = kejiSolrServer.query(solrQuery);
            System.out.println("索引库:科技");
        }
        if (fenlei.equals("shishang")) {
            response = shishangSolrServer.query(solrQuery);
            System.out.println("索引库:时尚");
        }
        if (fenlei.equals("tiyu")) {
            response = tiyuSolrServer.query(solrQuery);
            System.out.println("索引库:体育");
        }
        if (fenlei.equals("xinwen")) {
            response = xinwenSolrServer.query(solrQuery);
            System.out.println("索引库:新闻");
        }
        if (fenlei.equals("yule")) {
            response = yuleSolrServer.query(solrQuery);
            System.out.println("索引库:娱乐");
        }
        //2.1 获取结果(不带高亮的内容)
        SolrDocumentList documentList = response.getResults();
        System.out.println(documentList.getNumFound());
        System.out.println("查询了一下");


        //2.2 获取高亮的结果内容
        Map<String, Map<String, List<String>>> map = response.getHighlighting();

        //处理结果：减去8小时
//        SimpleDateFormat solrFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        ArrayList<News> newsList = new ArrayList<>();
        for (SolrDocument document : documentList) {

            News news = new News();
            String id = (String) document.get("id");
            String title = (String) document.get("title");
            String content = (String) document.get("content");
            String editor = (String) document.get("editor");
            String url = (String) document.get("url");
            String source = (String) document.get("source");

            //获取高亮的内容
            Map<String, List<String>> mapList = map.get(id);

            //获取title的高亮内容
            List<String> titleList = mapList.get("title");
            if (titleList != null && titleList.size() > 0) {
                title = titleList.get(0);
            }

            //获取content的高亮内容
            List<String> contentList = mapList.get("content");
            if (contentList != null && contentList.size() > 0) {
                content = contentList.get(0);
            }

            news.setId(id);
            news.setTitle(title);
            news.setContent(content);
            news.setEditor(editor);
            news.setUrl(url);
            news.setSource(source);

            //获取时间
            Date time = (Date) document.get("time");
            //将solr的格式转换成日期
            //减去8小时
            time = new Date(time.getTime() - 1000 * 60 * 60 * 8);
            //格式化成我们想要格式
            String timeString = format.format(time);

            //重新设置会新闻对象上面
            news.setTime(timeString);

            //添加到新闻列表中
            newsList.add(news);
        }

        //3. 返回结果:PageBean
        PageBean pageBean = resultBean.getPageBean();
        //当前页的数据列表
        pageBean.setNewsList(newsList);
        //总条数
        Long pageCount = documentList.getNumFound();
        pageBean.setPageCount(pageCount.intValue());

        //总页数： 总条数 /  pageSize
        Double pageNumber = Math.ceil(1.0 * pageCount / pageSize);
        pageBean.setPageNumber(pageNumber.intValue());

        System.out.println(pageBean.toString());
        return pageBean;
    }

    @Override
    public News findid(String id,String fenlei) throws Exception {
        //1. 封装查询条件
        SolrQuery solrQuery = new SolrQuery("id:" + id);

        //2. 执行查询
        QueryResponse response = null;
        if (fenlei.equals("caijing")) {
            response = caijingSolrServer.query(solrQuery);
            System.out.println("索引库:财经");
        }
        if (fenlei.equals("keji")) {
            response = kejiSolrServer.query(solrQuery);
            System.out.println("索引库:科技");
        }
        if (fenlei.equals("shishang")) {
            response = shishangSolrServer.query(solrQuery);
            System.out.println("索引库:时尚");
        }
        if (fenlei.equals("tiyu")) {
            response = tiyuSolrServer.query(solrQuery);
            System.out.println("索引库:体育");
        }
        if (fenlei.equals("xinwen")) {
            response = xinwenSolrServer.query(solrQuery);
            System.out.println("索引库:新闻");
        }
        if (fenlei.equals("yule")) {
            response = yuleSolrServer.query(solrQuery);
            System.out.println("索引库:娱乐");
        }
        //2.1 获取结果(不带高亮的内容)
        SolrDocumentList documentList = response.getResults();
        System.out.println(documentList.getNumFound());
        System.out.println("查询了一下");


        //处理结果：减去8小时
        //SimpleDateFormat solrFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

        News news = new News();
        for (SolrDocument document : documentList) {
            id = (String) document.get("id");
            String title = (String) document.get("title");
            String content = (String) document.get("content");
            String editor = (String) document.get("editor");
            String url = (String) document.get("url");
            String source = (String) document.get("source");
            news.setId(id);
            news.setTitle(title);
            news.setContent(content);
            news.setEditor(editor);
            news.setUrl(url);
            news.setSource(source);
            //获取时间
            Date time = (Date) document.get("time");
            //将solr的格式转换成日期
            //减去8小时
            time = new Date(time.getTime() - 1000 * 60 * 60 * 8);
            //格式化成我们想要格式
            String timeString = format.format(time);
            //重新设置会新闻对象上面
            news.setTime(timeString);
            //添加到新闻列表中
        }

        return news;
    }
}
