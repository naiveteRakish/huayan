package com.whayer.cloud.storage.business.base.data.res;

import com.whayer.cloud.storage.business.base.data.type.MultiLevelType;

/**
 * 资源类型（系统、设备、普通）支持扩展，目前已定义普通、设备
 * @ClassName: ResourceTypeMeta
 * @author lishibang
 * @date 2017年8月16日 下午5:17:52
 * @version v1.0.0
 * 
 */
public class ResourceTypeMeta extends MultiLevelType {

	/**
	 * 描述当前资源类型承载的资源对象型别，如：
	 * 普通资源：
	 * resouceClassName = com.whayer.cloud.storage.business.base.data.res.Resource
	 * Resource.type = null
	 * 通用设备：
	 * resouceClassName = com.whayer.cloud.storage.business.device.data.BaseDeviceInfo
	 * Resource.type = 具体设备类型
	 * 制定类型设备：
	 * resouceClassName = null
	 * Resource.DeviceType.resouceClassName = com.whayer.cloud.storage.business.device.data.DvrDeviceInfo
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
