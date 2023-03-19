package com.github.qingquanlv.testops_service_biz.utilities;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @Author: qingquan.lv
 * @Date: 2022/2/9 下午7:27
 */
public class ParamUtil {

        public static Pattern paramPattern = Pattern.compile("http\\:\\/\\/127\\.0\\.0\\.1\\:9000\\/dashboard.*");


        public static String getSonarResultUrl(String val) throws Exception {
                String str = "";
                //获取字符串中所有参数
                List<String> paramList = catchParamList(paramPattern, val);
                if (paramList.size() > 0) {
                        str = paramList.get(0);
                }
                return str;
        }


        /**
         * 获取字符串中的参数值
         *
         * @param value : 参数字符串
         * @return List<String> ：返回匹配的所有参数
         */
        private static List<String> catchParamList(Pattern pattern, String value){
                Matcher matcher = pattern.matcher(value);
                List<String> list = new ArrayList<>();
                while (matcher.find()) {
                        list.add(matcher.group());
                }
                return list;
        }
}
