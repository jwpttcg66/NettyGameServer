package com.snowcattle.game.service.dict;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiangwenping on 17/5/9.
 * map形数据字典，每个唯一
 */
public class DictMap implements IDictCollections{

    //存放map形数据字典
    private Map<Integer, IDict> dictMap;

    public DictMap(){
        this.dictMap = new ConcurrentHashMap<>();
    }

    public void put(int id, IDict iDict){
        this.dictMap.put(id, iDict);
    }

    /**
     * 获取数据字典
     * @param id
     * @return
     */
    public  IDict getDict(int id){
        return dictMap.get(id);
    }

    public Collection<IDict> getAllDicts(){
        return dictMap.values();
    }
}
