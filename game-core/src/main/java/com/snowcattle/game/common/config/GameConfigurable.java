package com.snowcattle.game.common.config;

/**
 * Created by jiangwenping on 17/2/22.
 */
public interface GameConfigurable {
    public String getProperty(String key, String defaultVal);
    public int getProperty(String key, int defaultVal);
    public boolean getProperty(String key, boolean defaultVal);
    public long getProperty(String key, long defaultVal);
}