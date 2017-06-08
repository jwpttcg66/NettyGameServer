package com.snowcattle.game.service.classloader.loader.scanner;


/**
 * @author C172
 *  消息扫描器
 */
public class ClassScanner {
	public String[] scannerPackage(String namespace, String ext) throws Exception
	{
//		String ext = ".class";
		String[] files = new PackageScaner().scanNamespaceFiles(namespace, ext, false, true);
		return files;
	}
	
//	public static void main(String[] args) throws Exception {
//		new MessageScanner("com.game.wolf.common.msg").scannerPackage();
//	}
}
