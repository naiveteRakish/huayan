package com.whayer.cloud.storage.business.device.data;

import com.whayer.cloud.storage.business.base.data.res.Resource;

/**
 * @ClassName: BaseDeviceInfo
 * TODO (设备配置信息对象基类)
 * @author fsong
 * @date 2017年7月18日 下午4:21:50
 * @version v1.0.0
 * 
 */
public class BaseDeviceInfo extends Resource {
	
	/**
	 * 通道号
	 * @Fields channel
	 */
	private String channel;

	
	/**
	 * 设备备注
	 * @Fields remark
	 */
	private String remark;

	/**
	 * 设备厂商
	 * @Fields vender
	 */
	private String vender;

	/**
	 * 设备型号
	 * @Fields version
	 */
	private String version;

	/**
	 * 安装地址
	 * @Fields address
	 */
	private String address;

	/**
	 * @return the channel
	 */
	public String getChannel() {
		return channel;
	}

	/**
	 * @param channel the channel to set
	 */
	public void setChannel(String channel) {
		this.channel = channel;
	}

	/**
	 * @return the remark
	 */
	public String getRemark() {
		return remark;
	}

	/**
	 * @param remark the remark to set
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}

	/**
	 * @return the vender
	 */
	public String getVender() {
		return vender;
	}

	/**
	 * @param vender the vender to set
	 */
	public void setVender(String vender) {
		this.vender = vender;
	}

	/**
	 * @return the version
	 */
	public String getVersion() {
		return version;
	}

	/**
	 * @param version the version to set
	 */
	public void setVersion(String version) {
		this.version = version;
	}

	/**
	 * @return the address
	 */
	public String getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address) {
		this.address = address;
	}	

	

}
