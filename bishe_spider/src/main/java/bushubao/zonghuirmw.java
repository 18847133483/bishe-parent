package bushubao;

import dao.daojrtt;
import rmw.*;

/**
 * @program: bishe-parent
 * @description: 人民网总爬取
 * @author: xiaobai
 * @create: 2020-04-23 10:20
 **/
public class zonghuirmw {
    private static daojrtt daojrtt=new daojrtt();

    public static void main(String[] args) throws Exception {
        rmwkeji.xiaomain(daojrtt);
        rmwcaijing.xiaomain(daojrtt);
        rmwshishang.xiaomain(daojrtt);
    }
}
