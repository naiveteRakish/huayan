package com.whayer.cloud.storage.business.alarm.data.subscribe;

import com.whayer.cloud.storage.business.base.data.msg.MsgSubscriber;

/**
 * @ClassName: AlarmSubscriber
 * TODO (告警消息订阅者对象)
 * @author fsong
 * @date 2017年7月20日 下午3:09:09
 * @version v1.0.0
 * 
 */
public class AlarmSubscriber extends MsgSubscriber{
	
	/**
	 * @Fields userId : TODO (业务用户Code)
	 */
	private String userCode;
	
	/**
	 * @Fields systemID : TODO (服务实例标识)
	 */
	private String serverInstanceId;

	/**
	 * @return the userCode
	 */
	public String getUserCode() {
		return userCode;
	}

	/**
	 * @param userCode the userCode to set
	 */
	public void setUserCode(String userCode) {
		this.userCode = userCode;
	}

	/**
	 * @return the serverInstanceId
	 */
	public String getServerInstanceId() {
		return serverInstanceId;
	}

	/**
	 * @param serverInstanceId the serverInstanceId to set
	 */
	public void setServerInstanceId(String serverInstanceId) {
		this.serverInstanceId = serverInstanceId;
	}
	
}
