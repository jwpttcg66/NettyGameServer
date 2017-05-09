package com.snowcattle.game.service.dict;

import com.snowcattle.game.common.constant.GlobalConstants;
import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.service.IService;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;

import java.io.File;

/**
 * Created by jiangwenping on 17/5/9.
 */
public class DictService implements IService{
    @Override
    public String getId() {
        return ServiceName.DictService;
    }

    @Override
    public void startup() throws Exception {
        String filePath = GlobalConstants.ConfigFile.dict_root_file;
        DefaultResourceLoader defaultResourceLoader = new DefaultResourceLoader();
        Resource resource = defaultResourceLoader.getResource(filePath);
        if(resource != null){
            File file = resource.getFile();

        }
    }

    @Override
    public void shutdown() throws Exception {

    }
}
