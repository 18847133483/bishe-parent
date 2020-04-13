package utils;


import java.text.SimpleDateFormat;
import java.util.Date;

public class TimeUtil {
    /**
     * 输入日期格式，返回格式内的内容，输入格式以-分隔
     *
     * @param dateFormat
     * @return
     **/
    public static String getTimeYearMonthDay(String dateFormat) {
        String[] strNow = new SimpleDateFormat(dateFormat).format(new Date()).toString().split("-");
        String str = "";
        for (String string : strNow) {
            str = str + string;
        }
        return str;
    }
    public static String suijishu(){
        String time = getTimeYearMonthDay("yyyyMMddHHmmss");
        int i = (int) ((Math.random() * 9 + 1) * 10000);
//        System.out.println(i);
        String time1 = time + i;
        return time1;
    }
}
