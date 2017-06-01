package com.snowcattle.game.db.service.common.fastjson;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.ParserConfig;
import com.alibaba.fastjson.serializer.SerializeConfig;

/**
 * Created by jiangwenping on 17/4/6.
 */
public class Test {

    private Status status;

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public static void main(String[] args) {
        SerializeConfig config=new SerializeConfig();
//        config.put(Status.class, new StatusSerializer());
        Test test = new Test();
        test.setStatus(Status.Completed);
        String jsonStr= JSON.toJSONString(test, config);
        ParserConfig.getGlobalInstance().putDeserializer(Status.class, new StatusDeserializer());
        Test test1=JSON.parseObject(jsonStr, Test.class);
        System.out.println(test1);
    }
}
