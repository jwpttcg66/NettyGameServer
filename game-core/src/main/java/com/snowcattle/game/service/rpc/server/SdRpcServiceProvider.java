package com.snowcattle.game.service.rpc.server;

import com.snowcattle.game.common.enums.BOEnum;
import org.jdom2.DataConversionException;
import org.jdom2.Element;

import java.util.BitSet;

/**
 * Created by jiangwenping on 17/3/31.
 * rpc服务提供模块
 */
public class SdRpcServiceProvider {

    //开放功能模块
    private BitSet bitSet = new BitSet();

    public void load(Element element) throws DataConversionException {
        String boenumString = element.getAttribute("boenum").getValue();
        BOEnum boEnum = BOEnum.valueOf(boenumString.toUpperCase());
        bitSet.set(boEnum.getBoId(), true);
    }

    //是否世界开放
    public boolean isWorldOpen(){
        return bitSet.get(BOEnum.WORLD.getBoId());
    }

    public boolean isGameOpen(){
        return bitSet.get(BOEnum.GAME.getBoId());
    }

    public boolean isDbOpen(){
        return bitSet.get(BOEnum.DB.getBoId());
    }

    public boolean validServer(int boId){
        return bitSet.get(boId);
    }


}
