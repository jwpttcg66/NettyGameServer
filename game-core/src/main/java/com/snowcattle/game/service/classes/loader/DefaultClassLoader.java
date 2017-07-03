package com.snowcattle.game.service.classes.loader;

/**
 * Created by jiangwenping on 17/2/8.
 */

import com.snowcattle.game.common.constant.Loggers;
import com.snowcattle.game.service.IService;
import org.slf4j.Logger;
import org.springframework.stereotype.Service;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.jar.JarFile;

/**
 * @author jwp
 * 系统默认classloader
 */
@Service
public class DefaultClassLoader implements IService {
    public static final Logger logger = Loggers.serverLogger;

    private FileClassLoader fileClassLoader;

    private DynamicGameClassLoader dynamicGameClassLoader;

    private boolean linux;

    /**
     * 是否采用jar包加载
     */
    private boolean jarLoad;

    @Override
    public String getId() {
        return "DefaultClassLoader";
    }

    @Override
    public void startup() throws Exception {

        String os = System.getProperty("os.name");
        logger.info("os:" + os);
        linux = os.toLowerCase().matches("linux");
        logger.info("os linux:" + linux);

        String name = DefaultClassLoader.class.getName().replace('.', '/');
        name = name.replace('.', '/');
        name = name + ".class";

        URL loadurl = DefaultClassLoader.class.getResource("/" + name);
        File file =new File(loadurl.getPath());

        logger.info("class load DefaultClassLoader " + loadurl.getPath() + "  load class " + name);

        URLConnection tmpURLConnection = loadurl.openConnection();
        FileClassLoader defaultFileClassLoader = null;
        if(tmpURLConnection instanceof JarURLConnection){
            jarLoad = true;
            logger.info("class load jarFlag " + jarLoad);
        }

        URL url = DefaultClassLoader.class.getResource("/");
        logger.info("DefaultClassLoader root path " + url.getPath());
        fileClassLoader = new FileClassLoader(new File(url.getPath()));

        dynamicGameClassLoader = new DynamicGameClassLoader();
    }

    @Override
    public void shutdown() throws Exception {

    }

    public synchronized void initClassLoaderPath(String realClass, String ext) throws Exception{
        String destRespath = realClass.replace('.', '/');
        destRespath = destRespath.replace('.', '/');
        destRespath = destRespath + ext;

        URL url = DefaultClassLoader.class.getResource("/" + destRespath);
        File file =new File(url.getPath());

        logger.info("DefaultClassLoader root path" + url.getPath() + "  load class " + realClass);

        URLConnection tmpURLConnection = url.openConnection();
        FileClassLoader defaultFileClassLoader = null;
        if(tmpURLConnection instanceof JarURLConnection){
            logger.info("DefaultClassLoader root path jar " + url.getPath() + "  load class " + realClass);
            JarFile jarFile = ((JarURLConnection)tmpURLConnection).getJarFile();
            fileClassLoader.initJarPath(jarFile);
        }else{
            logger.info("DefaultClassLoader root path class " + url.getPath() + "  load class " + realClass);
            fileClassLoader.initClassPath(file, realClass);
        }

    }

    public synchronized FileClassLoader getDefaultClassLoader() throws Exception{
        return fileClassLoader;
    }

    public synchronized void resetClassLoader() throws Exception{
        URL url = DefaultClassLoader.class.getResource("/");
        logger.info("DefaultClassLoader reset root path " + url.getPath());
        fileClassLoader = new FileClassLoader(new File(url.getPath()));
    }

    public void setClassLoader(FileClassLoader fileClassLoader) throws Exception{
        this.fileClassLoader = fileClassLoader;
    }


    public DynamicGameClassLoader getDynamicGameClassLoader() {
        return dynamicGameClassLoader;
    }

    public synchronized void resetDynamicGameClassLoader() throws Exception{
        dynamicGameClassLoader = new DynamicGameClassLoader();
    }

    public boolean isLinux() {
        return linux;
    }

    public void setLinux(boolean linux) {
        this.linux = linux;
    }

    public boolean isJarLoad() {
        return jarLoad;
    }

    public void setJarLoad(boolean jarLoad) {
        this.jarLoad = jarLoad;
    }
}

