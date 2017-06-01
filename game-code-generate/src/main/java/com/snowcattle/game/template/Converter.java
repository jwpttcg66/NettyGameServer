package com.snowcattle.game.template;


import com.snowcattle.game.template.xml.MacroObject;
import com.snowcattle.game.template.xml.MessageObject;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.Velocity;
import org.apache.velocity.app.VelocityEngine;

import java.io.StringWriter;



public class Converter {
	private String vmPath;
	
	// 创建引擎  
    VelocityEngine ve = null;  
	String encode = "UTF-8";
    
	public Converter(String vmPath, String encode){
		this.vmPath = vmPath;
		ve = new VelocityEngine();
		// 设置模板加载路径，这里设置的是class下  
	    ve.setProperty(Velocity.RESOURCE_LOADER, "class");
	    ve.setProperty("class.resource.loader.class", "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");
	    this.encode = encode;
	}
	
	public String convert(MessageObject format){
        try {
            // 进行初始化操作  
            ve.init();
            // 加载模板，设定模板编码  
            Template template = ve.getTemplate(vmPath + format.getVmFileName(), encode);  
            // 设置初始化数据
            VelocityContext context = new VelocityContext();  
            context.put("msg", format);
            // 输出
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            return writer.toString();
        } catch (Exception e) {
        	e.printStackTrace();
            return null;
        }
	}

    public String convert(MacroObject format){
        try {

            String defaultVm = "macros.vm";
            // 进行初始化操作
            ve.init();
            // 加载模板，设定模板编码
            Template template = ve.getTemplate(vmPath + defaultVm, encode);
            // 设置初始化数据
            VelocityContext context = new VelocityContext();
            context.put("msg", format);
            // 输出
            StringWriter writer = new StringWriter();
            template.merge(context, writer);
            return writer.toString();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
