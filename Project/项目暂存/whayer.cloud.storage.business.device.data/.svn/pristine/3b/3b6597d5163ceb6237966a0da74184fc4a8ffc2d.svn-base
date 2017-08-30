package com.whayer.cloud.storage.business.device.data;

import com.whayer.cloud.storage.business.base.data.type.MultiLevelType;

/**
 * @author fs
 * 设备类型元数据业务对象
 * 
 */
public class DeviceTypeMeta extends MultiLevelType {
	
	/**
	 * 描述当前设备的设备类型，只有当前设备类型是BaseDeviceInfo的子类时，才需要填写此字段，此字段不为空，代表此设备具备比通用设备更多的属性，一般代表此设备可以构建终端
	 * 如：
	 * resouceClassName = com.whayer.cloud.storage.business.device.data.DvrDeviceInfo
	 * @Fields resouceClassName
	 */
	private String resouceClassName;

	public String getResouceClassName() {
		return resouceClassName;
	}

	public void setResouceClassName(String resouceClassName) {
		this.resouceClassName = resouceClassName;
	}
}
