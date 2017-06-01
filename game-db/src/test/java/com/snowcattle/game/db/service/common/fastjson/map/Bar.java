package com.snowcattle.game.db.service.common.fastjson.map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import java.util.Date;
import java.util.Random;

/**
 * Created by jiangwenping on 17/4/10.
 */
public class Bar {
    public static SerializeConfig mapping = new SerializeConfig();
    private String barName;
    private int barAge;
    private Date barDate = new Date();
    static {
        mapping.put(Date.class, new SimpleDateFormatSerializer("yyyy-MM-dd"));
    }
    {
        Random r = new Random();
        barName = "sss_"+String.valueOf(r.nextFloat());
        barAge = r.nextInt();
    }

    public static void main(String[] args) {
        Object obj = JSON.toJSON(new Bar());
        String x1 = JSON.toJSONString(new Bar(), true);
        System.out.println(x1);
        String x2 = JSON.toJSONString(new Bar(), mapping);
        System.out.println(x2);
    }

    public String getBarName() {
        return barName;
    }

    public void setBarName(String barName) {
        this.barName = barName;
    }

    public int getBarAge() {
        return barAge;
    }

    public void setBarAge(int barAge) {
        this.barAge = barAge;
    }

    public Date getBarDate() {
        return barDate;
    }

    public void setBarDate(Date barDate) {
        this.barDate = barDate;
    }

    @Override
    public String toString() {
        return "Bar{" +
                "barName='" + barName + '\'' +
                ", barAge=" + barAge +
                ", barDate=" + barDate +
                '}';
    }
}
