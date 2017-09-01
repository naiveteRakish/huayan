package com.whayer.cloud.storage.business.device.data;

/**
 * @ClassName: DeviceNetInfo
 * TODO (登录网络信息对象)
 * @author fsong
 * @date 2017年7月19日 上午10:24:17
 * @version v1.0.0
 * 
 */
public class LoginNetInfo {
	/**
	 * 设备ip
	 */
	private String ip;

	/**
	 * 设备端口
	 */
	private String port;

	/**
	 * 获取
	 * @return ip
	 */
	public String getIp() {
		return ip;
	}

	/**
	 * 设置
	 * @param ip
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}

	/**
	 * 获取
	 * @return port
	 */
	public String getPort() {
		return port;
	}

	/**
	 * 设置
	 * @param port
	 */
	public void setPort(String port) {
		this.port = port;
	}

}
