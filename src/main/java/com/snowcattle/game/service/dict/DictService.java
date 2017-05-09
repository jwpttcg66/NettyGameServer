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

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by jiangwenping on 17/5/9.
 */
@Service
public class DictService implements IService{

    private Logger logger = Loggers.serverLogger;

    private Map<IDictModleType, IDictCollections> collectionsMap;
    @Override
    public String getId() {
        return ServiceName.DictService;
    }

    @Override
    public void startup() throws Exception {
        Map<IDictModleType, IDictCollections> collectionsMap = new ConcurrentHashMap<>();

        String filePath = GlobalConstants.ConfigFile.dict_root_file;
        String jsonString = ResourceUtil.getTextFormResource(filePath);
        if(!StringUtils.isEmpty(jsonString)) {
            JSONObject jsonObject = (JSONObject) JSONObject.parse(jsonString);
            String packages = jsonObject.getString(GlobalConstants.JSONFile.dict_package);
            JSONArray jsonArray = (JSONArray) jsonObject.getJSONArray(GlobalConstants.JSONFile.dict_fils);
            JSONArray[] dictModle = jsonArray.toArray(new JSONArray[0]);
            for(JSONArray dictModleJsonArray: dictModle){
                String enumString = dictModleJsonArray.get(0).toString();
                String path = dictModleJsonArray.get(1).toString();
                String ClassName = dictModleJsonArray.get(2).toString();

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
                        if (multiKey) {

                        } else {
                            Class classes = Class.forName(packages + "." + ClassName);
                            JSONObject[] dictModleJsonObjects = bodyJson.toArray(new JSONObject[0]);
                            for(JSONObject dictJson: dictModleJsonObjects) {
                                //唯一的数据
                                Object object = JSONObject.toJavaObject(dictJson, classes);
                                logger.debug("加载" + dictJson.toJSONString());
                            }
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
}
