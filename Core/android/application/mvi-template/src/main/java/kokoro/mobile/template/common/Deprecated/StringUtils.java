package kokoro.mobile.template.common.Deprecated;


import java.net.URLDecoder;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.regex.Pattern;

public class StringUtils {
    public static boolean isNullOrEmpty(String str) {
        if (str == null) {
            return true;
        }
        String trim = str.trim();
        return trim.length() == 0 || trim.equalsIgnoreCase("null");
    }

    public static boolean isUrl(String str) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        return Pattern.compile("(http|ftp|https):\\/\\/[\\w\\-_]+(\\.[\\w\\-_]+)+([\\w\\-\\.,@?^=%&amp;:/~\\+#]*[\\w\\-\\@?^=%&amp;/~\\+#])?").matcher(str).matches();
    }

    public static boolean isTelephone(String str) {
        if (isNullOrEmpty(str)) {
            return false;
        }
        return Pattern.compile("((\\d{11})|^((\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})|(\\d{4}|\\d{3})-(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1})|(\\d{7,8})-(\\d{4}|\\d{3}|\\d{2}|\\d{1}))$)").matcher(str).matches();
    }
    /**
     * 比较当前时间和服务器返回时间大小
     *
     * @param nowDate
     * @param compareDate
     * @return
     */
    public static boolean compareDate(String nowDate, String compareDate) {
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date now = df.parse(nowDate);
            Date compare = df.parse(compareDate);
            return now.before(compare);
        } catch (ParseException e) {
            e.printStackTrace();
            return false;
        }
    }
    /**
     * 判断给定字符串时间是否为今日
     * @param sdate
     * @return boolean
     */
    public static boolean isToday(String sdate){
        boolean b = false;
        Date time = toDate(sdate);
        Date today = new Date();
        if(time != null){
            String nowDate = dateFormater2.get().format(today);
            String timeDate = dateFormater2.get().format(time);
            if(nowDate.equals(timeDate)){
                b = true;
            }
        }
        return b;
    }
    /**
     * 将字符串转位日期类型
     * @param sdate
     * @return
     */
    public static Date toDate(String sdate) {
        try {
            return dateFormater.get().parse(sdate);
        } catch (ParseException e) {
            return null;
        }
    }
    public static String dateDiff(Date startTime, Date endTime) {
        //按照传入的格式生成一个simpledateformate对象
        SimpleDateFormat sd = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        long nd = 1000 * 24 * 60 * 60;//一天的毫秒数
        long nh = 1000 * 60 * 60;//一小时的毫秒数
        long nm = 1000 * 60;//一分钟的毫秒数
        long ns = 1000;//一秒钟的毫秒数long diff;
        //获得两个时间的毫秒时间差异
        long diff;
        //diff = sd.parse(endTime).getTime() - sd.parse(startTime).getTime();
        diff = endTime.getTime() - startTime.getTime();
        String day = diff / nd +"";//计算差多少天
        String hour = diff % nd / nh +"";//计算差多少小时
        String min = diff % nd % nh / nm +"";//计算差多少分钟
        String sec = diff % nd % nh % nm / ns +"";//计算差多少秒//输出结果
        if(day.equals("0")){
            return  hour + "小时" + min + "分钟";
        }else{
            return  day + "天" + hour + "小时";
        }

    }
    private final static ThreadLocal<SimpleDateFormat> dateFormater = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        }
    };

    private final static ThreadLocal<SimpleDateFormat> dateFormater2 = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat("yyyy-MM-dd");
        }
    };

    public static String getLanguage(String language){
        String res = "";
        if(isEnglish(language)){
            res = "英文";
        }else if(isChinese(language)){
            res = "汉字";
        }else if(isNumber(language)){
            res = "数字";
        }
        return res;
    }

    /**
     * 是否是英文
     * @param str
     * @return
     */
    private static boolean isEnglish(String str){
        return str.matches("^[a-zA-Z]*");
    }

    /**
     * 是否是汉字
     * @param str
     * @return
     */
    private static boolean isChinese(String str){
        return str.matches("[\u4e00-\u9fa5]+");
    }

    /**
     * 是否是数字
     * @param str
     * @return
     */
    private static boolean isNumber(String str){
        return Character.isDigit(str.charAt(0));
    }

    /*对int非空判断*/
    public static int emptyIfNull(Integer str) {
        return str == null ? 0 : str;
    }

    /*对String非空判断*/
    public static String emptyIfNull(String str) {
        return str == null ? "" : str;
    }

    /*对List非空判断*/
    public static List emptyIfNull(List list) {
        if(list != null && !list.isEmpty()){
            return list;
        }
        return null;
    }

    /**
     * 获取文字中有多少数字
     * @param s
     * @return
     */
    public static String findNumber(String s){
        String result = "";
        for (int i = 0; i < s.length(); i++) {
            int code = s.codePointAt(i);
            if(code >=48 && code <= 57){
                result += (char)code;
            }
        }
        return result;
    }

    public static int changePage(int currt){
        int totalPage;
        totalPage = (currt / 10) + ((currt % 10) > 0 ? 1 : 0);
        return totalPage;
    }

    /**
     * 转换阿拉伯数字到中文字符
     *
     * @param num
     * @return
     */
    public static String cnNumConvertor(int num) {
        if (num==0) {
            return "零";
        }
        final String illegalPrefix = "一十";
        final String[] units = {"", "万", "亿", "兆"};
        String prefix = "";
        if (num < 0) {
            prefix = "负";
            num = -num;
        }
        String numStr = String.valueOf(num);
        final int metaLen = 4;
        int r = numStr.length() - 1;
        int l = Math.max(0, r - metaLen + 1);
        int unitIndex = 0;
        StringBuilder builder = new StringBuilder();
        while (r >= 0 && l >= 0) {
            builder.insert(0, cnNumMetaConvertor(numStr.substring(l, r + 1)) + units[unitIndex++]);
            r = l - 1;
            l = Math.max(0, r - metaLen + 1);
        }
        String res = builder.toString();
        if (res.startsWith(illegalPrefix)) {
            res = res.substring(1);
        }
        return prefix + res;
    }

    /**
     * 按照中文阅读习惯，处理最高4位的 meta 数字集
     *
     * @param num
     * @return
     */
    private static String cnNumMetaConvertor(String num) {
        final int metaLen = 4;
        final String zeroStr = "0";
        final String[] cnNums = {"零", "一", "二", "三", "四", "五", "六", "七", "八", "九"};
        final String[] units = {"千", "百", "十", ""};
        StringBuilder builder = new StringBuilder();
        int N = num.length();
        boolean canNotJudge = N > metaLen || (N < metaLen && num.startsWith(zeroStr));
        if (canNotJudge) {
            return "";
        }
        boolean isPreZero = false;
        int unitIndex = metaLen - num.length() - 1;
        for (int i = 0; i < N; i++) {
            ++unitIndex;
            final int currNum = num.charAt(i) - '0';
            final String currNumCn = cnNums[currNum];
            final String currUnit = units[unitIndex];
            if (currNum == 0) {
                if (isPreZero) {
                    continue;
                }
                isPreZero = true;
                builder.append(currNumCn);
            } else {
                isPreZero = false;
                builder.append(currNumCn);
                builder.append(currUnit);
            }
        }
        String res = builder.toString();
        return res.endsWith("零") ? res.substring(0, res.length() - 1) : res;
    }

    //UTF8汉字编码还原成汉字
    public static String fromHexString(String hexString){
        String result = "";
        try {
            result = URLDecoder.decode(hexString, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 是否加密身份证
     * @param isCard 是否
     * @param cardId 身份证
     * @return
     */
    public static String isEncryptionCard(boolean isCard,String cardId){
        if(isCard){
            if(cardId.length() == 4){
                return cardId;
            }else if(cardId.length() > 4){
                if(cardId.length() == 18){
                    String one = "";
                    String tow = "";
                    one = cardId.substring(0,3);
                    tow = cardId.substring(cardId.length() - 4);
                    String card = one + "***********" + tow;
                    return card;
                }else {
                    String one = "";
                    for (int i = 0; i < cardId.length(); i++) {
                        if(i > 2 && i < cardId.length() -2 ){
                            one = one + "*";
                        }
                    }
                    String tow = "";
                    String three = "";
                    tow = cardId.substring(0,2);
                    three = cardId.substring(cardId.length() - 2);
                    String card = tow + one + three;
                    return card;
                }
            }
        }
        return cardId;
    }

    /**
     * 是否加密姓名
     * @param isName 是否
     * @param name 姓名
     * @return
     */
    public static String isEncryptionName(boolean isName,String name){
        if(isName){
            String one = "";
            String tow = "";
            for (int i = 0; i < name.length(); i++) {
                one = one + "*";
            }
            one = one.substring(0,one.length() - 1);
            tow = name.substring(name.length() - 1);
            String card = one + tow;
            return card;
        }
        return name;
    }

    /**
     * 是否加密手机号
     * @param isPhone 是否
     * @param phone 手机号
     * @return
     */
    public static String isEncryptionPhone(boolean isPhone,String phone){
        if(isPhone){
            String one = "";
            String tow = "";
            one = phone.substring(0,3);
            tow = phone.substring(phone.length() - 3);
            String card = one + "*****" + tow;
            return card;
        }
        return phone;
    }

    public static String getListAndString(List<String> mList){
        String result = "";
        for (String data: mList){
            result =  result + data + "、";
        }
        return result;
    }

    public static String getSubName(String name){
        String result = "";
        if(name.length() >= 10){
            result = name.substring(0,4) + "..." + name.substring(name.length() - 4 ,name.length());
        }else{
            result = name;
        }
        return result;
    }


}
