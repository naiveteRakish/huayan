package com.whayer.cloud.storage.business.device.data;

/**
 * @author fs
 * 设备对象基类
 * 
 */
public class BaseStatusInfo {
	public enum CommonStatus {
		normal, fault, maintain, poweroff, unknown
	}

	/**
	 * 设备通用状态
	 */
	private CommonStatus commonStatus;

	/**
	 * 设备是否在线
	 */
	private boolean bOnline;

	/**
	 * 获取
	 * @return commonStatus
	 */
	public CommonStatus getCommonStatus() {
		return commonStatus;
	}

	/**
	 * 设置
	 * @param commonStatus
	 */
	public void setCommonStatus(CommonStatus commonStatus) {
		this.commonStatus = commonStatus;
	}

	/**
	 * 获取
	 * @return bOnline
	 */
	public boolean isbOnline() {
		return bOnline;
	}

	/**
	 * 设置
	 * @param bOnline
	 */
	public void setbOnline(boolean bOnline) {
		this.bOnline = bOnline;
	}

}
