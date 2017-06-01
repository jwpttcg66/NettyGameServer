package com.snowcattle.game.db.common.loader.scanner;

/**
 * Created by jiangwenping on 17/4/10.
 */

/**
 * @author C172
 *  消息扫描器
 */
public class ClassScanner {
    public String[] scannerPackage(String namespace, String ext) throws Exception
    {
        String[] files = new PackageScaner().scanNamespaceFiles(namespace, ext, false, true);
        return files;
    }
}
