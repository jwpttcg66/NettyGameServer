package com.snowcattle.game.db.service.common.fastjson;

import com.alibaba.fastjson.parser.DefaultJSONParser;
import com.alibaba.fastjson.parser.JSONLexer;
import com.alibaba.fastjson.parser.deserializer.ObjectDeserializer;

import java.lang.reflect.Type;

/**
 * Created by jiangwenping on 17/4/6.
 */
public class StatusDeserializer implements ObjectDeserializer {

    public <T> T deserialze(DefaultJSONParser parser, Type type,
                            Object fieldName) {
        JSONLexer lexer = parser.getLexer();
        String value = lexer.stringVal();
        return (T) Status.create(value);
    }

    public int getFastMatchToken() {
        // TODO Auto-generated method stub
        return 0;
    }
}
