package com.snowcattle.game.db.util;


import com.alibaba.fastjson.JSON;
import com.snowcattle.game.db.common.Loggers;
import org.slf4j.Logger;

import java.util.Map;

/**
 * Created by jwp on 2017/2/28.
 */
public class JsonUtils {

    /** 日志 */
    public static final Logger logger = Loggers.dbLogger;

    /**
     * 获取json字符串
     * @param map
     * @return
     */
    public static String getJsonStr(Map<String, String> map){
        return JSON.toJSONString(map);
    }

    @SuppressWarnings("unchecked")
    public static Map<String,String> getMapFromJson(String json){
        return JSON.parseObject(json, Map.class);
    }

}

