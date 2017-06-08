package com.snowcattle.game.common.scanner;

import com.snowcattle.game.common.constant.Loggers;
import org.slf4j.Logger;
import sun.net.www.protocol.file.FileURLConnection;

import java.io.File;
import java.net.JarURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * 包扫描器
 * @author C172
 *
 */
public class PackageScaner
{
	/**
	 * Logger for this class
	 */
	public static final Logger logger = Loggers.serverLogger;

	public static String[] scanNamespaceFiles(String namespace, String fileext,boolean isReturnCanonicalPath){
		return scanNamespaceFiles(namespace, fileext, isReturnCanonicalPath , false);
	}

	public static String[] scanNamespaceFiles(String namespace, String fileext,boolean isReturnCanonicalPath, boolean checkSub)
	{
		String respath = namespace.replace('.', '/');
		respath = respath.replace('.', '/');

		List<String> tmpNameList = new ArrayList<String>();
		try
		{
			URL url = null;
			logger.info("scan url path " + respath);
			if (!respath.startsWith("/"))
				url = PackageScaner.class.getResource("/" + respath);
			else
				url = PackageScaner.class.getResource(respath);

			URLConnection tmpURLConnection = url.openConnection();
			String tmpItemName;
			if (tmpURLConnection instanceof JarURLConnection)
			{
				JarURLConnection tmpJarURLConnection = (JarURLConnection) tmpURLConnection;
				int tmpPos;
				String tmpPath;
				JarFile jarFile = tmpJarURLConnection.getJarFile();
				Enumeration<JarEntry> entrys = jarFile.entries();				
				while (entrys.hasMoreElements())
				{
					JarEntry tmpJarEntry = entrys.nextElement();
					if (!tmpJarEntry.isDirectory())
					{
						tmpItemName = tmpJarEntry.getName();
						if (tmpItemName.indexOf('$') < 0
								&& (fileext == null || tmpItemName.endsWith(fileext)))
						{
							tmpPos = tmpItemName.lastIndexOf('/');
							if (tmpPos > 0)
							{
								tmpPath = tmpItemName.substring(0, tmpPos);
								if(checkSub){
									if (tmpPath.startsWith(respath))
									{
										
										String r = tmpItemName.substring(respath.length()+1).replaceAll("/", ".");
										tmpNameList.add(r);
									}	
								}else{
									if (respath.equals(tmpPath))
									{
										tmpNameList.add(tmpItemName.substring(tmpPos + 1));
									}
								}
								
							}
						}
					}
				}
				jarFile.close();
			}
			else if (tmpURLConnection instanceof FileURLConnection)
			{
				File file = new File(url.getFile());
				if (file.exists() && file.isDirectory())
				{
					File[] fileArray = file.listFiles();
					for (File f : fileArray)
					{
						if(f.isDirectory() && f.getName().indexOf(".")!=-1)
							continue;
						
						if(isReturnCanonicalPath)
							tmpItemName = f.getCanonicalPath();
						else
							tmpItemName = f.getName();
						if(f.isDirectory()){
							String[] inner = scanNamespaceFiles(namespace+"."+tmpItemName, fileext, isReturnCanonicalPath);
							if(inner == null){
								continue;
							}
							for(String i : inner){
								if(i!=null){
									tmpNameList.add(tmpItemName+"."+i);
								}
							}
						}else if(fileext == null || tmpItemName.endsWith(fileext) )
						{
							tmpNameList.add(tmpItemName);
						}else{
							continue;// 明确一下，不符合要求就跳过
						}
					}
				}
				else
				{
					logger.error("scaning stop,invalid package path:" + url.getFile());
				}
			}
		}
		catch (Exception e)
		{
			logger.error("scaning stop,invalid package path error" + e.toString());
		}
		
		
		if (tmpNameList.size() > 0)
		{
			String[] r = new String[tmpNameList.size()];
			tmpNameList.toArray(r);
			tmpNameList.clear();
			return r;
		}
		return null;
	}

	
//	public static void main(String[] args) throws IOException
//	{
		
//		JarPathLoader.getNewJarLoader("/dist/server-core-1.0.0.jar");
//		URLClassLoader c = JarClassLoader.getClassLoad("D:/t4game/workspace3/GameServerCore/dist", true);
//		
//		System.out.println(c.getResource("com/t4game/test/classloader/test.txt"));
//		String[] files = scanNamespaceFiles("com.t4game.test", "txt",false);
//		for (int i = 0; files != null && i < files.length; i++)
//		{
//			System.out.println(files[i]);
//		}
//		System.out.println("**********************");
//		files = scanNamespaceFiles("com.pwrd.game.action.activity", ".class");
//		for (int i = 0; files != null && i < files.length; i++)
//		{
//			System.out.println(files[i]);
//		}
		
//		JarFile jarFile = new JarFile("dist/server-core-1.0.0.jar");
//		Enumeration<JarEntry> entrys=jarFile.entries();    
//		
//	    while(entrys.hasMoreElements())   
//	    {    
//              JarEntry  entry = (JarEntry)entrys.nextElement();    
//              if   (entry.isDirectory())  
//            	  continue;    
//              System.out.println(entry.getName());
//              if(entry.getName().endsWith("Test1.class"))
//            	  
//              jarFile.getInputStream(entry);
//	    } 
//	}
}

