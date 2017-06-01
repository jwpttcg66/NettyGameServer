package com.snowcattle.game.db.service.redis;

/**
 * Created by jiangwenping on 17/4/6.
 * 异步处处的redis key
 */
public enum  AsyncRedisKeyEnum {
    ASYNC_DB("ay_db#"),
    ;

    private String key;

    AsyncRedisKeyEnum(String key){
        this.key = key;
    }

    public static AsyncRedisKeyEnum getAsyncRedisKeyEnum(String key){
        AsyncRedisKeyEnum result = null;
        for(AsyncRedisKeyEnum temp: AsyncRedisKeyEnum.values()){
            if(temp.getKey().equals(key)){
                result = temp;
                break;
            }
        }
        return result;
    }

    public String getKey(){
        return this.key;
    }
}
