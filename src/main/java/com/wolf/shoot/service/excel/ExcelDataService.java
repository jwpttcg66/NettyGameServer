package com.wolf.shoot.service.excel;

import java.io.File;
import java.io.FileFilter;
import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Service;

import com.snowcattle.game.common.constant.ServiceName;
import com.snowcattle.game.service.IService;
import com.wolf.shoot.service.excel.manager.D_BulletLoaderManager;
import com.wolf.shoot.service.excel.manager.DataLoaderManager;
import com.wolf.shoot.service.excel.manager.EntityLoaderManager;
import com.wolf.shoot.service.excel.manager.SkillLoaderManager;
import com.wolf.shoot.service.excel.manager.StatusLoaderManager;

@Service
public class ExcelDataService implements IService {

	public String getId() {
		return ServiceName.EXCELDATASERVICE;
	}

	public void startup() throws Exception {
		D_BulletLoaderManager.getInstance().load();
		DataLoaderManager.getInstance().load();
		EntityLoaderManager.getInstance().load();
		SkillLoaderManager.getInstance().load();
		StatusLoaderManager.getInstance().load();
		/*Set<Class<?>> classes = getClasses("com.wolf.shoot.service.excel");
		List<File> filelist = new ArrayList<>();
		getResourceFiles(getClass().getClassLoader().getResource("data").getPath(),filelist);*/
		
	}


	public void shutdown() throws Exception {

	}

	public static void main(String[] args) {
		getClasses("com.wolf.shoot.service.excel.entity");
	}
	public static Set<Class<?>> getClasses(String pack) {
		Set<Class<?>> classes = new LinkedHashSet<Class<?>>();
		boolean recursive = true;
		String packageName = pack;
		String packageDirName = packageName.replace('.', '/');
		Enumeration<URL> dirs;
		try {
			dirs = Thread.currentThread().getContextClassLoader().getResources(packageDirName);
			while (dirs.hasMoreElements()) {
				URL url = dirs.nextElement();
				String protocol = url.getProtocol();
				if ("file".equals(protocol)) {
					String filePath = URLDecoder.decode(url.getFile(), "UTF-8");
					findAndAddClassesInPackageByFile(packageName, filePath,recursive, classes);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return classes;
	}

	public static void findAndAddClassesInPackageByFile(String packageName,String packagePath, final boolean recursive, Set<Class<?>> classes) {
		File dir = new File(packagePath);
		if (!dir.exists() || !dir.isDirectory()) {
			return;
		}
		File[] dirfiles = dir.listFiles(new FileFilter() {
			public boolean accept(File file) {
				return (recursive && file.isDirectory())|| (file.getName().endsWith(".class"));
			}
		});
		for (File file : dirfiles) {
			if (file.isDirectory()) {
				findAndAddClassesInPackageByFile(packageName + "." + file.getName(),file.getAbsolutePath(), recursive, classes);
			} else {
				String className = file.getName().substring(0,file.getName().length() - 6);
				try {
					classes.add(Thread.currentThread().getContextClassLoader().loadClass(packageName + '.' + className));
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
				}
			}
		}
	}
	public static List<File> getResourceFiles(String strPath,List<File> filelist) {
        File dir = new File(strPath);
        File[] files = dir.listFiles();
        if (files != null) {
            for (int i = 0; i < files.length; i++) {
                String fileName = files[i].getName();
                if (files[i].isDirectory()) {
                	getResourceFiles(files[i].getAbsolutePath(),filelist);
                } else if (fileName.endsWith("json")) {
                	System.err.println(files[i].getParent());
                	System.err.println("-"+files[i].getPath());
                	System.err.println("--"+files[i].getAbsolutePath());
                	System.err.println(fileName);
                    filelist.add(files[i]);
                } else {
                    continue;
                }
            }
        }
        return filelist;
    }
}
