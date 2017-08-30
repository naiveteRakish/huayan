package com.whayer.cloud.storage.business.device.data;

/**
 * @ClassName: DeviceSerialInfo
 * TODO (登录串口信息对象)
 * @author fsong
 * @date 2017年7月19日 上午10:29:13
 * @version v1.0.0
 * 
 */
public class LoginSerialInfo {
	/**
	 * 串口端口号
	 */
	private int port;

	/**
	 * 波特率
	 */
	private int bandRate;

	/**
	 * 数据位
	 */
	private int dataBits;

	/**
	 * 停止位
	 */
	private int stopBits;

	/**
	 * 奇偶校验
	 */
	private int parity;

	/**
	 * 获取
	 * @return port
	 */
	public int getPort() {
		return port;
	}

	/**
	 * 设置
	 * @param port
	 */
	public void setPort(int port) {
		this.port = port;
	}

	/**
	 * 获取
	 * @return bandRate
	 */
	public int getBandRate() {
		return bandRate;
	}

	/**
	 * 设置
	 * @param bandRate
	 */
	public void setBandRate(int bandRate) {
		this.bandRate = bandRate;
	}

	/**
	 * 获取
	 * @return dataBits
	 */
	public int getDataBits() {
		return dataBits;
	}

	/**
	 * 设置
	 * @param dataBits
	 */
	public void setDataBits(int dataBits) {
		this.dataBits = dataBits;
	}

	/**
	 * 获取
	 * @return stopBits
	 */
	public int getStopBits() {
		return stopBits;
	}

	/**
	 * 设置
	 * @param stopBits
	 */
	public void setStopBits(int stopBits) {
		this.stopBits = stopBits;
	}

	/**
	 * 获取
	 * @return parity
	 */
	public int getParity() {
		return parity;
	}

	/**
	 * 设置
	 * @param parity
	 */
	public void setParity(int parity) {
		this.parity = parity;
	}

}
