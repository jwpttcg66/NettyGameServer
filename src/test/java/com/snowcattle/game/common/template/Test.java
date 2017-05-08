package com.snowcattle.game.common.template;

/**
 * Created by jiangwenping on 17/4/10.
 */
public class Test {

    public static void main(String[] args) {
//        ChileTemplate<String> chileTemplate = new ChileTemplate<String>();
        ChileTemplate chileTemplate = new ChileTemplate();
        System.out.println(chileTemplate.getTClass(0));

        UserDao dao = new UserDao();
    }
}
