package com.snowcattle.game.common.dict;

import com.snowcattle.game.TestStartUp;
import com.snowcattle.game.bootstrap.manager.LocalMananger;
import com.snowcattle.game.service.dict.DictService;
import com.snowcattle.game.service.dict.entity.Bullet;
import com.snowcattle.game.service.dict.entity.RoleSkill;

import java.util.List;

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
        dictModleType= "STATUS_ROLE_SKILL";
        id = 100;
        List<RoleSkill> roleSkills = dictService.getIDictArray(dictModleType, id, RoleSkill.class);
        System.out.println(roleSkills);
    }
}
