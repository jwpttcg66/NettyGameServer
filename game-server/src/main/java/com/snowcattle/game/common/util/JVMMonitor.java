package com.snowcattle.game.common.util;

import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

import javax.management.MBeanServer;

import com.sun.management.OperatingSystemMXBean;

/**
 * 通过JVM JMX Bean取得监测数据
 *
  *
 *
 */
public class JVMMonitor {
	private final MBeanServer server;
	private final OperatingSystemMXBean osm;
	private final RuntimeMXBean runtimeMXBean;
	private long preUpTime = 0;
	private long preCpuTime = 0;

	public static final JVMMonitor instance = new JVMMonitor();

	private JVMMonitor() {
		server = ManagementFactory.getPlatformMBeanServer();
		runtimeMXBean = ManagementFactory.getRuntimeMXBean();
		OperatingSystemMXBean _inst = null;
		try {
			_inst = ManagementFactory.newPlatformMXBeanProxy(server, ManagementFactory.OPERATING_SYSTEM_MXBEAN_NAME,
					OperatingSystemMXBean.class);
		} catch (IOException e) {
			e.printStackTrace();
		}
		osm = _inst;
		preUpTime = ManagementFactory.getRuntimeMXBean().getUptime();
		if (osm != null) {
			preCpuTime = osm.getProcessCpuTime();
		}
	}

	/**
	 * 取得CPU的使用率 TODO 经测试,由JVM JMX BEAN算得出CPU使用率通常比Linux系统的TOP得到CPU使用率要大一些,比较奇怪
	 *
	 * @return
	 */
	public double getCpuUsage() {
		if (osm == null) {
			return -1;
		}
		final long _nowCpuTime = osm.getProcessCpuTime();
		if (_nowCpuTime < 0) {
			return -1;
		}
		final long _nowUpTime = runtimeMXBean.getUptime();
		final long elapseCput = _nowCpuTime - preCpuTime;
		final long elapseTime = _nowUpTime - preUpTime;
		if (elapseTime <= 0) {
			return -1;
		}
		final double _cpur = elapseCput / (elapseTime * 1000f * osm.getAvailableProcessors());
		this.preCpuTime = _nowCpuTime;
		this.preUpTime = _nowUpTime;
		return _cpur;
	}

	/**
	 * 取得JVM的可用CPU个数
	 *
	 * @return
	 */
	public int getAvailableProcessors() {
		if (osm == null) {
			return -1;
		}
		return osm.getAvailableProcessors();
	}

	/**
	 * @return the instance
	 */
	public JVMMonitor getInstance() {
		return instance;
	}
}
