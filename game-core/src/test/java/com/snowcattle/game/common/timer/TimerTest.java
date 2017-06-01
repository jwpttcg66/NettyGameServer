package com.snowcattle.game.common.timer;

import java.util.Timer;
import java.util.TimerTask;

/**
 * Created by jiangwenping on 17/3/27.
 */
public class TimerTest {
    public static void main(String[] args) {
        Timer timer = new Timer();
        timer.schedule(new TestTimeTask(), 0, 10);
    }

}

class TestTimeTask extends TimerTask{

    private int i = 0;
    @Override
    public void run() {
        i++;
        System.out.println("测试" + i + "时间" + System.currentTimeMillis());
        try {
            Thread.sleep(30 * 1000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

    }
}
