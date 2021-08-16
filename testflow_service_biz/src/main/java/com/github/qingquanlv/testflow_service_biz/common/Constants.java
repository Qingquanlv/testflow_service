package com.github.qingquanlv.testflow_service_biz.common;

public class Constants {
    public static final String MYSQL= "mysql";
    public static final String TIDB= "tidb";
    public static final String DRUID= "druid";
    public static final String PARSE = "parse";
    public static final String REQUEST = "request";
    public static final String COMPARE_VAL = "compare_val";
    public static final String COMPARE_PATH = "compare_path";
    public static final String COMPARE_OBJ = "compare_obj";
    public static final String STATUS_PASS = "1";
    public static final String STATUS_FAIL = "0";

    //Static value
    public static final String ENTITY_ROOT= "ROOT_NODE";

    public static final String MAPKEY= "key";
    public static final String MAPVALUE = "value";
    public static final String COLON = ":";

    public static final String SERVICES_CLASS_PATH = "com.github.qingquanlv.testflow_service_biz.stepdefinations.ParseValueFileName";
    public static final String PARSE_VALUE_FILE_NAME = "ParseValueFileName.java";
    public static final String METHOD_NAME = "parseValueVidStr";
    public static final String PARSE_VALUE_FILE_SOURCE = "package com.github.qingquanlv.testflow_service_biz.stepdefinations;" +
            "" +
            "import java.util.ArrayList;" +
            "import java.util.List;" +
            "import java.util.stream.Collectors;" +
            "import com.alibaba.fastjson.JSON;" +
            "import com.alibaba.fastjson.JSONArray;" +
            "import com.alibaba.fastjson.JSONObject;" +
            "import com.github.qingquanlv.testflow_service_biz.common.BufferManager;" +
            "\n" +
            "public class ParseValueFileName " +
            "{    \n" +
            "    public String parseValueVidStr(List<String> sourceData) throws Exception{\n        " +
            "        parameter" +
            "        method\n" +
            "    }" +
            "}" ;
    public static final String PARAMETER = "parameter";
    public static final String METHOD = "method";

    public static final String JSON_OBJECT_TYPE = "JSONObject";
    public static final String JSON_ARRAY_TYPE = "JSONArray";
    public static final String INDEX = "index";
    public static final String PARAMETER_TYPE = "parameterType";
    public static final String PARAMETER_NAME = "parameterName";

    public static final String QUETY_DB_FILE_NAME = "queryDB.xml";
    public static final String PQUETY_DB_FILE_SOURCE =
            "<?xml version=\"1.0\" encoding=\"UTF-8\" ?>\n" +
                    "<!DOCTYPE mapper\n" +
                    "        PUBLIC \"-//mybatis.org//DTD Mapper 3.0//EN\"\n" +
                    "        \"http://mybatis.org/dtd/mybatis-3-mapper.dtd\">\n" +
                    "<mapper namespace=\"key123\">\n" +
                    "    <select id=\"queryDataBase\" parameterType=\"java.util.Map\" resultType=\"type123\">\n" +
                    "        sql123\n" +
                    "    </select>\n" +
                    "</mapper>";

    public static final String DRUID_TEMPLATE = "{\n" +
            "    \"query\": \"$$${TP}\",\n" +
            "    \"context\": {\n" +
            "        \"sqlTimeZone\": \"Asia/Sinapore\"\n" +
            "    },\n" +
            "    \"resultFormat\": \"objectLines\",\n" +
            "    \"header\": false\n" +
            "}";
    public static final String DRUID_TEMPLATE_PARAME = "$$${TP}";


}
