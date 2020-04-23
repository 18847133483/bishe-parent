package rmw;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import utils.httpclientutils01;


/**
 * @program: bishe-parent
 * @description: 分页遍历测试
 * @author: xiaobai
 * @create: 2020-04-23 11:33
 **/
public class test02 {
    public static void main(String[] args) throws Exception {
        String s = httpclientutils01.doGet("http://fashion.people.com.cn/GB/158617/index1.html");
        Document parse = Jsoup.parse(s);
        Elements elements = parse.select("ul li b a");
        for (Element element : elements) {
            System.out.println(element);
        }
    }

}
