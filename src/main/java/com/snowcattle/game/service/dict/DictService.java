package com.snowcattle.game.service.dict;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.common.util.ResourceUtil;
import com.snowcattle.game.common.util.StringUtils;
import com.snowcattle.game.service.IService;
import org.springframework.stereotype.Service;

/**
 * Created by jiangwenping on 17/5/9.
 */
@Service
public class DictService implements IService{
    @Override
    public String getId() {
        return ServiceName.DictService;
    }

    @Override
    public void startup() throws Exception {
        String filePath = GlobalConstants.ConfigFile.dict_root_file;
        String jsonString = ResourceUtil.getTextFormResource(filePath);
        if(!StringUtils.isEmpty(jsonString)) {
            JSON json = (JSON) JSONObject.parse(jsonString);
        }

    }

    @Override
    public void shutdown() throws Exception {

    }
}
