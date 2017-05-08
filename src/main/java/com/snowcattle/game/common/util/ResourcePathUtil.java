package com.snowcattle.game.common.util;

public class ResourcePathUtil {

	/**
	 * 在指定资源路径找到给定资源文件，并返回资源文件的完整路径
	 * 查找顺序如下
	 * 1. 查找目标.jar的压缩包内路径
	 * 2. 在程序的启动目录查找
	 * @param resourceFileName
	 * @return
	 */
	public static String getRootPathWithoutSlash(String resourceFileName){
		try{
			String result = ResourcePathUtil.class.getResource("/"+resourceFileName).getPath();
			return result;
		}
		catch(Exception e){
			return System.getProperty("user.dir")+"/"+resourceFileName;
		}
	}

}
