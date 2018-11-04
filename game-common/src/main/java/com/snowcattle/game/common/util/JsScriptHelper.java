package com.snowcattle.game.common.util;

import org.apache.commons.io.FileUtils;
import org.slf4j.LoggerFactory;

import javax.script.*;
import java.io.*;
import java.util.Map;

public final class JsScriptHelper {
	private static final org.slf4j.Logger logger = LoggerFactory
			.getLogger(JsScriptHelper.class);

	private static final ScriptEngineManager manager = new ScriptEngineManager();
	public static final String STRING_CHARSET = "UTF-8";

    private JsScriptHelper() {
    }

    /**
	 * 执行一个脚本文件
	 *
	 * @param path		脚本文件的路径
	 * @param params	执行参数
	 * @return			脚本执行结果的返回值
	 */
	public static Object executeScriptFile(String path,
			Map<String, Object> params) {
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		if (params != null) {
			for (Map.Entry<String, Object> pair : params.entrySet()) {
				engine.put(pair.getKey(), pair.getValue());
			}
		}
		InputStreamReader reader = null;
		try {
			reader = new InputStreamReader(new FileInputStream(path),
					STRING_CHARSET);
			return engine.eval(reader);
		} catch (FileNotFoundException | UnsupportedEncodingException | ScriptException e) {
			logger.error("", e);
		} finally {
			if (reader != null) {
				try {
					reader.close();
				} catch (IOException e) {
					logger.error("", e);
				}
			}
		}
		return null;
	}

	public static Object executeCompiledScriptFile(String path,
			Map<String, Object> params) {
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		if (params != null) {
			for (Map.Entry<String, Object> pair : params.entrySet()) {
				engine.put(pair.getKey(), pair.getValue());
			}
		}
		try {
			Compilable compiler = (Compilable) engine;
			CompiledScript script = compiler.compile(FileUtils
					.readFileToString(new File(path), STRING_CHARSET));
			return script.eval();
		} catch (IOException | ScriptException e) {
			logger.error("", e);
		}
        return null;

	}

	/**
	 * 执行指定的脚本内容
	 *
	 * @param content	脚本内容
	 * @param params	执行参数
	 * @return			脚本执行结果的返回值
	 */
	public static Object executeScriptContent(String content,
			Map<String, Object> params) {
		ScriptEngine engine = manager.getEngineByName("JavaScript");
		if (params != null) {
			for (Map.Entry<String, Object> pair : params.entrySet()) {
				engine.put(pair.getKey(), pair.getValue());
			}
		}
		try {
			return engine.eval(content);
		} catch (ScriptException e) {
			logger.error("", e);
		}
		return null;
	}

//	public static void main(String[] args) {
//		Properties p = new Properties();
//		System.out.println(p.getProperty("b"));
//		String script = "prop.setProperty(\"a\", \"b\");";
//		Map<String, Object> params = new HashMap<String, Object>();
//		params.put("prop", p);
//		System.out.println(executeScriptContent(script, params));
//		System.out.println(p);
//
//		String scriptFile = "test.script";
//		script = "prop.setProperty(\"c\", \"d\");return";
//
//		try {
//			FileUtils.writeStringToFile(new File(scriptFile), script,
//					STRING_CHARSET);
//		} catch (IOException e) {
//			logger.error("", e);
//		}
//
//		executeScriptFile(scriptFile, params);
//
//		System.out.println(p);
//
//		System.out.println(new File(scriptFile).delete());
//
//	}

}
