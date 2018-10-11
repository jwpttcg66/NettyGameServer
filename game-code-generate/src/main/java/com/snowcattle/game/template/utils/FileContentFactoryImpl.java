package com.snowcattle.game.template.utils;

public class FileContentFactoryImpl implements FileConentFactory {
    @Override
    public FileContent create(String fileName, String content, String filePath) {
        FileContent fileContent = new FileContent();
        fileContent.setFileName(fileName);
        fileContent.setContent(content);
        fileContent.setFilePath(filePath);
        return fileContent;
    }
}
