package rmw;

import org.apache.commons.lang3.StringUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import utils.TimeUtil;
import utils.httpclientutils01;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @program: bishe-parent
 * @description: 测试循环得到新闻url
 * @author: xiaobai
 * @create: 2020-04-22 16:48
 **/
public class test01 {
    public static void main(String[] args) throws Exception {
        String url="http://finance.people.com.cn/n1/2020/0416/c1004-31676275-3.html";
        String s = httpclientutils01.doGet(url);
        String content = StringUtils.substringBetween(s, "<div class=\"box_pic\"></div>", "<div class=\"zdfy clearfix\">");
        Document document = Jsoup.parse(s);
        if(content==null){
            content=document.select(".box_con p").text();
        }

        String[] split = document.select(".box01").text().split("来源：");
        String time=null;
        String source=null;
        if(split.length==2){
            time = document.select(".box01").text().split("来源：")[0];
            source = document.select(".box01").text().split("来源：")[1];
        }else{
            time = document.select(".box01").text().split("来源：")[0];
            source = document.select(".author").text().replace("来源：","");
        }
        if(StringUtils.isEmpty(time)){
            time="2020年04月04日11:11  ";
        }

        String title = document.select("h1").text();
        SimpleDateFormat saveformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        SimpleDateFormat rmwformat=new SimpleDateFormat("yyyy'年'MM'月'dd'日'HH:mm ");
        Date da = rmwformat.parse(time);
        time = saveformat.format(da);
        String id = TimeUtil.suijishu();
        String editor=null;
        if(document.select(".box_con div").last()!=null){
            editor = StringUtils.substringBetween(document.select(".box_con div").last().text(),"：",")");
        }
        //System.out.println(content);

        System.out.println(id);
        System.out.println(title);
        System.out.println(time);
        System.out.println(source);
        System.out.println(content);
        System.out.println(editor);
        System.out.println(url);

    }
}
