package com.snowcattle.game.common.dict;

import com.snowcattle.game.TestStartUp;
import com.snowcattle.game.manager.LocalMananger;
import com.snowcattle.game.service.dict.DictService;
import com.snowcattle.game.service.dict.entity.Bullet;

/**
 * Created by jiangwenping on 17/5/9.
 */
public class DictServiceTest {
    public static void main(String[] args) throws Exception {
        TestStartUp.startUpWithSpring();
        DictService dictService = LocalMananger.getInstance().getLocalSpringServiceManager().getDictService();
        String dictModleType= "BULLET_BULLET";
        int id = 1;
        Bullet bullet = dictService.getIDict(dictModleType, id, Bullet.class);
        System.out.println(bullet);
    }
}
