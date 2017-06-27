package com.snowcattle.game.service.dict;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.common.util.ResourceUtil;
import com.snowcattle.game.common.util.StringUtils;
import com.snowcattle.game.service.IService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiangwenping on 17/5/9.
 */
@Service
public class DictService implements IService{

    private Logger logger = Loggers.serverLogger;

    private Map<String, IDictCollections> collectionsMap;
    @Override
    public String getId() {
        return ServiceName.DictService;
    }

    @Override
    public void startup() throws Exception {
        Map<String, IDictCollections> collectionsMap = new ConcurrentHashMap<>();

        String filePath = GlobalConstants.ConfigFile.DICT_ROOT_FILE;
        String jsonString = ResourceUtil.getTextFormResourceNoException(filePath);
        if(!StringUtils.isEmpty(jsonString)) {
            JSONObject jsonObject = (JSONObject) JSONObject.parse(jsonString);
            String packages = jsonObject.getString(GlobalConstants.JSONFile.dict_package);
            JSONArray jsonArray = (JSONArray) jsonObject.getJSONArray(GlobalConstants.JSONFile.dict_fils);
            JSONArray[] dictModle = jsonArray.toArray(new JSONArray[0]);
            for(JSONArray dictModleJsonArray: dictModle){
                String enumString = dictModleJsonArray.get(0).toString();
                String path = dictModleJsonArray.get(1).toString();
                String className = dictModleJsonArray.get(2).toString();

//                logger.debug(dictModleJsonArray.toJSONString());
                //加载文件
                jsonString = ResourceUtil.getTextFormResource(path);
                if(!StringUtils.isEmpty(jsonString)){
                    JSONObject dictJsonObjects = (JSONObject) JSONObject.parse(jsonString);
                    //加载数据
                    String multiKeyString = dictJsonObjects.getString(GlobalConstants.JSONFile.multiKey);
                    JSONArray bodyJson= dictJsonObjects.getJSONArray(GlobalConstants.JSONFile.body);
                    boolean multiKey = Boolean.parseBoolean(multiKeyString);
                    if(bodyJson != null) {
                        Class classes = Class.forName(packages + "." + className);
                        if (multiKey) {
                            JSONArray[] dictModleJsonArrays = bodyJson.toArray(new JSONArray[0]);
                            DictArrayMaps dictMap = new DictArrayMaps();
                            for(JSONArray dictJsonArray: dictModleJsonArrays) {
//                                //多个数据
                                JSONObject[] dictModleJsonObjects = dictJsonArray.toArray(new JSONObject[0]);
                                List<IDict> dictList = new ArrayList<>();
                                int dictId = -1;
                                for(JSONObject dictJson: dictModleJsonObjects) {
                                    //唯一的数据
                                    Object object = JSONObject.toJavaObject(dictJson, classes);
                                    if(logger.isDebugEnabled()) {
                                        logger.debug("加载dict className:" +  className + dictJson.toJSONString());
                                    }
                                    IDict dict = (IDict) object;
                                    dictList.add(dict);
                                    dictId = dict.getID();
                                }
                                dictMap.put(dictId, dictList.toArray(new IDict[0]));
                            }
                            collectionsMap.put(enumString,dictMap);
                        } else {
                            JSONObject[] dictModleJsonObjects = bodyJson.toArray(new JSONObject[0]);
                            DictMap dictMap = new DictMap();
                            for(JSONObject dictJson: dictModleJsonObjects) {
                                //唯一的数据
                                Object object = JSONObject.toJavaObject(dictJson, classes);
                                if(logger.isDebugEnabled()) {
                                    logger.debug("加载dict className:" + className + dictJson.toJSONString());
                                }
                                IDict dict = (IDict) object;
                                dictMap.put(dict.getID(), dict);
                            }
                            collectionsMap.put(enumString,dictMap);
                        }
                    }
                }
            }
        }

        this.collectionsMap = collectionsMap;

    }

    @Override
    public void shutdown() throws Exception {

    }

    public <T> T getIDict(String dictModleType, int id, Class<T> t){
        IDictCollections iDictCollections = getIDictCollections(dictModleType);
        if(iDictCollections instanceof DictMap){
            DictMap dictMap = (DictMap) iDictCollections;
            return (T) dictMap.getDict(id);
        }
        return null;
    }

    public <T extends IDict> List<T> getIDictArray(String dictModleType, int id, Class<T> t){
        IDictCollections iDictCollections = getIDictCollections(dictModleType);
        if(iDictCollections instanceof DictArrayMaps){
            DictArrayMaps dictArrayMaps = (DictArrayMaps) iDictCollections;
            IDict[] iDictArrays = dictArrayMaps.getDictArary(id);
            List<T> list = new ArrayList<>();
            for(IDict iDict: iDictArrays){
                list.add((T) iDict);
            }
            return list;
        }
        return null;
    }

    /**
     * 获取数据集合
     * @param dictModleType
     * @param id
     * @return
     */
    public IDictCollections getIDictCollections(String dictModleType){
        if(!collectionsMap.containsKey(dictModleType)){
            return null;
        }

        IDictCollections iDictCollections = collectionsMap.get(dictModleType);
        return iDictCollections;
    }
}
