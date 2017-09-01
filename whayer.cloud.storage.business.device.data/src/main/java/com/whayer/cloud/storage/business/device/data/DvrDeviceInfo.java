package com.whayer.cloud.storage.business.device.data;

/**
 * dvr终端配置信息
 * @ClassName: DvrTerminalConfigInfo
 * @author fsong
 * @date 2017年8月15日 下午2:58:30
 * @version v1.0.0
 * 
 */
public class DvrDeviceInfo extends BaseDeviceInfo {
	
	/**
	 * 登录网络信息，protocol字段不为空时，此字段不能为空
	 * @Fields netInfo
	 */
	private LoginNetInfo netInfo;
	
	/**
	 * 登录账户信息，protocol字段不为空时，此字段不能为空
	 * @Fields accountInfo
	 */
	private LoginAccountInfo accountInfo;

	/**
	 * @return the netInfo
	 */
	public LoginNetInfo getNetInfo() {
		return netInfo;
	}

	/**
	 * @param netInfo the netInfo to set
	 */
	public void setNetInfo(LoginNetInfo netInfo) {
		this.netInfo = netInfo;
	}

	/**
	 * @return the accountInfo
	 */
	public LoginAccountInfo getAccountInfo() {
		return accountInfo;
	}

	/**
	 * @param accountInfo the accountInfo to set
	 */
	public void setAccountInfo(LoginAccountInfo accountInfo) {
		this.accountInfo = accountInfo;
	}
	
}
