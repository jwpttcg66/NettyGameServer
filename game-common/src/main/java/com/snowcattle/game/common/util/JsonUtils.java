package com.snowcattle.game.common.util;


import com.alibaba.fastjson.JSON;

import java.util.Map;

/**
 * Created by jwp on 2017/2/28.
 */
public final class JsonUtils {

    private JsonUtils() {
    }

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

