package com.snowcattle.game.bootstrap;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 服务器状态日志
 *
 *
 *
 */
public class ServerStatusLog {
    /** 服务器正在启动 */
    private static final int STARTING = 0;
    /** 服务器启动失败,已经终止 */
    private static final int START_FAIL = 1;
    /** 服务器启动成功,正在运行 */
    private static final int RUNNING = 2;
    /** 服务器正在停止 */
    private static final int STOPPING = 3;
    /** 服务器已经停止 */
    private static final int STOPPED = 4;
    /** 服务器状态日志保存地路径 */
    private final String serverStatusLog;
    /** 默认的系统状态保存地址,保存地址为:玩家当前所在目录/server_status */
    private static final ServerStatusLog instance = new ServerStatusLog(System
            .getProperty("user.dir")
            + File.separator + "logs" + File.separator + "server_status");

    /**
     *
     * @param serverStatusLog
     */
    public ServerStatusLog(String serverStatusLog) {
        if (serverStatusLog == null
                || (serverStatusLog = serverStatusLog.trim()).length() == 0) {
            throw new IllegalArgumentException(
                    "The threadNumberPerPort must not be null ");
        }
        this.serverStatusLog = serverStatusLog;
        this.checkLogDir();
    }

    /**
     * 服务器正在启动
     */
    public void logStarting() {
        this.writeStatusLog(STARTING);
    }

    /**
     * 服务器启动失败
     */
    public void logStartFail() {
        this.writeStatusLog(START_FAIL);
    }

    /**
     * 服务器正在运行
     */
    public void logRunning() {
        this.writeStatusLog(RUNNING);
    }

    /**
     * 服务器正在停止
     */
    public void logStoppping() {
        this.writeStatusLog(STOPPING);
    }

    /**
     * 服务器已经停止
     */
    public void logStopped() {
        this.writeStatusLog(STOPPED);
    }

    /**
     * 取得默认的服务器状态日志
     *
     * @return
     */
    public static ServerStatusLog getDefaultLog() {
        return instance;
    }

    private void writeStatusLog(int status) {
        File _logFile = new File(this.serverStatusLog);
        FileOutputStream _out = null;
        try {
            _out = new FileOutputStream(_logFile);
            String _time = new SimpleDateFormat("yyyy-MM-dd HH:mm")
                    .format(new Date());
            String _status = status + "\t" + _time + "\t";
            _out.write(_status.getBytes(Charset.forName("ISO8859-1")));
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (_out != null) {
                try {
                    _out.close();
                } catch (IOException e) {
                }
            }
        }
    }

    private void checkLogDir() {
        File _logFile = new File(this.serverStatusLog).getAbsoluteFile();
        if (_logFile.exists()) {
            return;
        }
        File _parentFile = _logFile.getParentFile();
        if (_parentFile.exists()) {
            return;
        }
        if (_parentFile.mkdirs()) {
            return;
        } else {
            System.err.println("Can't create the server status log dir["
                    + _parentFile + "]");
        }
    }
}
