package com.snowcattle.game.common.config;

import com.snowcattle.game.common.constant.CommonErrorLogInfo;
import com.snowcattle.game.common.util.ErrorsUtil;

import java.io.File;


/**
 * {@link Config}的简单实现
 *
 */
public abstract class ServerConfig implements Config {

	/** 生产模式:0 调式模式:1 */
	protected int debug = 0;
	
	/** 配置文件是否加密*/
	protected boolean encryptResource = false;
	
	/** 系统的字符编码 */
	protected String charset;
	/** 系统配置的版本号 */
	protected String version;
	/** 系统配置的资源版本号 */
	protected String resourceVersion;
	/** 服务器ID 规则是:是 localHostId+serverIndexId,如1011,表示1区,1服的第一个服务器 */
	protected String serverId;
	/** 服务绑定的IP */
	protected String bindIp;
	/** 服务器监听的端口,多个商品以逗号","分隔 */
	protected String ports;
	/** 服务器的名称 */
	protected String serverName;
	/** 系统所使用的语言 */
	protected String language;
	/** 多语言资源所在的目录 */
	protected String i18nDir;
	/** 资源文件根目录 */
	protected String baseResourceDir;
	/** 脚本所在的目录 */
	protected String scriptDir;
	/** 脚本的头文件 */
	protected String scriptHeaderName;

	/** 物品编辑器自动生成的配置目录 */
	protected String exportDataDir;

	/** 数据库初始化类型： 0 Hibernate 1 Ibatis */
	protected int dbInitType = 0;
	/** 数据库配置文件路径 */
	protected String dbConfigName;
	/** 写死的，没在配置里 */
	protected String gameId = "shoot";


//	/** dirtyWorlds简版地址 */
//	protected String dirtyWordsPartUrl = "http://down.51rs.cn/games-common/dirtywords/part.csv";
//	/** dirtyWorlds完全版地址 */
//	protected String dirtyWordsFullUrl = "http://down.51rs.cn/games-common/dirtywords/full.csv";

	@Override
	public boolean getIsDebug() {
		return getDebug() == 1;
	}

	@Override
	public String getVersion() {
		return this.version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * 取得资源文件的绝对路径
	 *
	 * @param path
	 * @return
	 */
	public String getResourceFullPath(String path) {
		return this.baseResourceDir + File.separator + path;
	}

	public String getBaseResourceDir() {
		return baseResourceDir;
	}

	public void setBaseResourceDir(String baseResourceDir) {
		this.baseResourceDir = baseResourceDir;
	}

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public String getI18nDir() {
		return i18nDir;
	}

	public void setI18nDir(String dir) {
		i18nDir = dir;
	}

	public String getCharset() {
		return charset;
	}

	public void setCharset(String charset) {
		this.charset = charset;
	}

	/**
	 * @return the serverId
	 */
	public String getServerId() {
		return serverId;
	}
	
	public int getServerIdInt() {
		return Integer.parseInt(serverId);
	}

	/**
	 * @param serverId
	 *            the serverId to set
	 */
	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getBindIp() {
		return bindIp;
	}

	public void setBindIp(String bindIp) {
		this.bindIp = bindIp;
	}

	public String getPorts() {
		return ports;
	}

	public void setPorts(String ports) {
		this.ports = ports;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public int getPort(){
		String ports = getPorts();
		String[] splitPorts = ports.split(",");
		return Integer.parseInt(splitPorts[0]);
	}

	public void setDebug(int debug) {
		this.debug = debug;
	}

	public int getDebug() {
		return debug;
	}

	public String getScriptDir() {
		return scriptDir;
	}

	public void setScriptDir(String scriptDir) {
		this.scriptDir = scriptDir;
	}

	public int getDbInitType() {
		return dbInitType;
	}

	public void setDbInitType(int dbInitType) {
		this.dbInitType = dbInitType;
	}

	public String getDbConfigName() {
		return dbConfigName;
	}

	public void setDbConfigName(String dbConfigName) {
		this.dbConfigName = dbConfigName;
	}

	public void setExportDataDir(String exportDataDir) {
		this.exportDataDir = exportDataDir;
	}

	public String getExportDataDir() {
		return exportDataDir;
	}

	public void setScriptHeaderName(String scriptHeaderName) {
		this.scriptHeaderName = scriptHeaderName;
	}

	public String getScriptHeaderName() {
		return scriptHeaderName;
	}

	/**
	 * Log Server 配置
	 *
	 *
	 */
	public static class LogConfig {
		/** Log Server地址 */
		private String logServerIp;
		/** Log Server端口 */
		private int logServerPort;

		public void setLogServerIp(String logServerIp) {
			this.logServerIp = logServerIp;
		}

		public String getLogServerIp() {
			return logServerIp;
		}

		public void setLogServerPort(int logServerPort) {
			this.logServerPort = logServerPort;
		}

		public int getLogServerPort() {
			return logServerPort;
		}

	}

	@Override
	public void validate() {
//		if (serverType < 1) {
//			throw new IllegalArgumentException(
//					"The serverType must not be empty");
//		}

		if (this.ports == null || (ports = ports.trim()).length() == 0) {
			throw new IllegalArgumentException(ErrorsUtil.error(
					CommonErrorLogInfo.ARG_NOT_NULL_EXCEPT, "", "ports"));
		}
		
		// 版本号配置检查
		if (this.getVersion() == null) {
			throw new IllegalArgumentException("The version  must not be null");
		}

	}

	/**
	 * 获得脚本文件全目录
	 *
	 * @return
	 */
	public String getScriptDirFullPath() {
		return this.getResourceFullPath(this.getScriptDir());
	}

	public void setResourceVersion(String resourceVersion) {
		this.resourceVersion = resourceVersion;
	}

	public String getResourceVersion() {
		return resourceVersion;
	}

	public boolean isEncryptResource() {
		return encryptResource;
	}

	public void setEncryptResource(boolean encryptResource) {
		this.encryptResource = encryptResource;
	}

	public String getGameId() {
		return gameId;
	}

	public void setGameId(String gameId) {
		this.gameId = gameId;
	}
}
