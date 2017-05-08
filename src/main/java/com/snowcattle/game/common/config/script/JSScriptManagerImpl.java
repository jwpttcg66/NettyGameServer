package com.snowcattle.game.common.config.script;

import java.io.File;
import java.io.IOException;
import java.util.Map;

import javax.script.Bindings;
import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import javax.script.SimpleBindings;

import com.snowcattle.game.common.constant.CommonErrorLogInfo;
import com.snowcattle.game.common.util.ErrorsUtil;
import org.apache.commons.io.FileUtils;


/**
 * JS脚本执行管理器
 *
 *
 */
public class JSScriptManagerImpl implements IScriptEngine {
    private final String charset;
    private final ScriptEngine engine;

    /**
     * @param charset
     *            脚本默认的字符编码
     */
    public JSScriptManagerImpl(String charset) {
        this.charset = charset;
        ScriptEngineManager factory = new ScriptEngineManager();
        engine = factory.getEngineByName("JavaScript");
    }

    /**
     * @param charset
     *            如果为空,则使用JSScriptMangerImpl的charset
     * @exception RuntimeException
     */
    @Override
    public Object runScript(Map<String, Object> binding, String scriptFile, String charset) {
        String content = null;
        try {
            Bindings _bindings = new SimpleBindings(binding);
            content = FileUtils.readFileToString(new File(scriptFile), charset != null ? charset : this.charset);
            return engine.eval(content, _bindings);
        } catch (IOException e) {
            throw new RuntimeException(ErrorsUtil.error(CommonErrorLogInfo.FILE_IO_FAIL, "Throw Exception",
                    scriptFile), e);
        } catch (ScriptException se) {
            throw new RuntimeException(ErrorsUtil.error(CommonErrorLogInfo.SCRITP_EXECUTE_FAIL,
                    "Throw Exception", scriptFile + " content:" + content), se);
        }
    }

    /**
     *
     * @exception RuntimeException
     */
    @Override
    public Object runScript(Map<String, Object> binding, String scriptContent) {
        try {
            if (binding == null || binding.isEmpty()) {
                return engine.eval(scriptContent);
            } else {
                Bindings _bindings = new SimpleBindings(binding);
                return engine.eval(scriptContent, _bindings);
            }
        } catch (ScriptException se) {
            throw new RuntimeException(ErrorsUtil.error(CommonErrorLogInfo.SCRITP_EXECUTE_FAIL,
                    "Throw Exception", scriptContent), se);
        }
    }

    /**
     * 获得脚本的内容
     *
     * @param scriptFile
     * @param charset
     * @return
     */
    public String getScriptContent(String scriptFile, String charset) {
        try {
            return FileUtils.readFileToString(new File(scriptFile), charset != null ? charset : this.charset);
        } catch (IOException e) {
            throw new RuntimeException(ErrorsUtil.error(CommonErrorLogInfo.FILE_IO_FAIL, "Throw Exception",
                    scriptFile), e);
        }
    }
    /**
     * 逻辑计算条件表达式
     *
     * @param context
     * @param exp
     *            表达式
     * @return
     * @throws AIException
     */
    public boolean doLogicEval(Map<String, Object> binding, String exp) {
        if (exp == null || exp.length() == 0) {
            throw new IllegalArgumentException("条件表达式不能为空");
        }
        try {
            Bindings _bindings = new SimpleBindings(binding);
            Object _ret = engine.eval(exp, _bindings);
            if (_ret instanceof Boolean) {
                return (Boolean) _ret;
            } else {
                throw new RuntimeException("无效的逻辑运算表达式:" + exp);
            }
        } catch (ScriptException se) {
            throw new RuntimeException(ErrorsUtil.error(CommonErrorLogInfo.SCRITP_EXECUTE_FAIL,
                    "Throw Exception", exp), se);
        }
    }

    /**
     *
     * @param binding
     * @param exp
     * @return
     */
    public double doMathEval(Map<String, Object> binding, String exp) {
        if (exp == null || exp.length() == 0) {
            throw new IllegalArgumentException("条件表达式不能为空");
        }

        try {
            Bindings _bindings = new SimpleBindings(binding);
            Object _ret = engine.eval(exp, _bindings);
            if (_ret instanceof Number) {
                return ((Number) _ret).doubleValue();
            } else {
                throw new RuntimeException("无效的逻辑运算表达式:" + exp);
            }
        } catch (ScriptException se) {
            throw new RuntimeException(ErrorsUtil.error(CommonErrorLogInfo.SCRITP_EXECUTE_FAIL,
                    "Throw Exception", exp), se);
        }
    }
}
