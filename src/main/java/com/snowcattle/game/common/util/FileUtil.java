package com.snowcattle.game.common.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 *
 */
public class FileUtil {
	/**
	 * 创建一个目录
	 *
	 * @param dir
	 * @exception RuntimeException
	 *                ,创建目录失败会抛出此异常
	 */
	public static void createDir(File dir) {
		if (!dir.exists() && !dir.mkdirs()) {
			throw new RuntimeException("Can't create the dir [" + dir + "]");
		}
	}

	/**
	 * 删除一个文件或者目录
	 *
	 * @param file
	 */
	public static void delete(File file) {
		if (file.isFile()) {
			file.delete();
		} else if (file.isDirectory()) {
			File[] _files = file.listFiles();
			for (File _f : _files) {
				delete(_f);
			}
			file.delete();
		}
	}

	/**
	 * 删除一个目录下的除exculde指定的后缀名外的所有子文件或子目录
	 *
	 * @param file
	 */
	public static void cleanFolder(File file,String exculde)
	{
		if(!file.isDirectory()) return;

		File[] _files = file.listFiles();
		for (File _f : _files) {
			if(_f.getName().endsWith(exculde)) continue;
			delete(_f);
		}
	}

	/**
	 * 压缩zip文件
	 * @param file
	 * @param dest
	 * @throws IOException
	 */
	public static void zip(File file, File dest) throws IOException {
		ZipOutputStream _zip1 = new ZipOutputStream(new FileOutputStream(dest));
		zipFiles(file, _zip1, file.getAbsolutePath());
		_zip1.close();
	}

	private static void zipFiles(File file, ZipOutputStream out, String root) throws IOException {
		String _entryName = file.getAbsolutePath();
		if (_entryName.equals(root)) {
			//
			_entryName="";
		} else {
			int _ri = _entryName.indexOf(root);
			_entryName = _entryName.substring(_ri + root.length()+1);
			_entryName = _entryName.replace('\\', '/');
		}
		if (file.isFile()) {
			out.putNextEntry(new ZipEntry(_entryName));
			FileInputStream _fin = new FileInputStream(file);
			byte[] _b = new byte[1024];
			int len = 0;
			while ((len = _fin.read(_b)) != -1) {
				out.write(_b, 0, len);
			}
			_fin.close();
		} else {
			out.putNextEntry(new ZipEntry(_entryName + "/"));
			File[] _files = file.listFiles();
			for (File _f : _files) {
				zipFiles(_f, out, root);
			}
		}
	}
	
	public static URL getConfigURL(String fileName)
	{
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		return classLoader.getResource(fileName);
	}
	
	public static String getConfigPath(String fileName)
	{
		ClassLoader classLoader = Thread.currentThread()
				.getContextClassLoader();
		return classLoader.getResource(fileName).getPath();
	}

	public static File getFile(String filePath)
	{
		URL url= getConfigURL(filePath);
		if(url != null){
			return new File(url.getFile());
		}
		return null;
	}
}
