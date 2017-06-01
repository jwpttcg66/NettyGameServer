package com.snowcattle.game.service.dict;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiangwenping on 17/5/9.
 * map形数据字典，每个对应一个数组
 */
public class DictArrayMaps implements IDictCollections{

    private Map<Integer, IDict[]> dictMap;

    public DictArrayMaps(){
        this.dictMap = new ConcurrentHashMap<>();
    }

    public void put(int id, IDict[] dicts){
        this.dictMap.put(id, dicts);
    }

    public IDict[] getDictArary(int id){
        return dictMap.get(id);
    }
}
