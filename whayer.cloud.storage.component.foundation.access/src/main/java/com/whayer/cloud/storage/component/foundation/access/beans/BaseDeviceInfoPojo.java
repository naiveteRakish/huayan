/**  
 * 设备存取
 * @Title: BaseDeviceInfoPojo.java
 * @Package com.whayer.cloud.storage.component.device.deviceaccess.beans
 * @author Administrator
 * @date 2017年8月16日 下午3:14:48
 * @version v1.0.0
 */
package com.whayer.cloud.storage.component.foundation.access.beans;

/**
 * 设备pojo
 * @ClassName: BaseDeviceInfoPojo
 * @author Administrator
 * @date 2017年8月16日 下午3:14:48
 * @version v1.0.0
 * 
 */
public class BaseDeviceInfoPojo {
	/**
	 * 业务对象ID
	 */
	private String id;
	/**
	 * 资源编码
	 * @Fields code
	 */
	private String code;

	/**
	 * 父资源ID
	 * @Fields parentId
	 */
	private String parentId;

	/**
	 * 资源名称
	 * @Fields name
	 */
	private String name;

	/**
	 * 资源描述
	 * @Fields desc
	 */
	private String desc;

	/**
	 * 资源类型（普通、系统、设备）对应MessageSourceTypeMeta
	 * @Fields remark
	 */
	private String resourceTypeId;

	/**
	 * 资源具体类型（如果是设备就对应设备类型元数据，如果是系统就对应系统类型元数据）
	 * @Fields remark
	 */
	private String typeId;

	/**
	 * 资源协议类型（GB28181，海康SDK，大华SDK等等，以一种方式接入一个设备就产生一种协议类型）
	 * @Fields protocol对应ProtocolTypeMeta
	 */
	private String protocolId;

	/**
	 * 资源类型字段 用于确定资源的具体类型
	 */
	private String resouceTypeClassName;
	/**
	 * 资源状态(1:在线可用 ;0:离线;2:不可用)
	 * @Fields status
	 */
	private Integer status;
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
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * @return the parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * @param parentId the parentId to set
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @return the desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * @param desc the desc to set
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}

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
	 * @return the status
	 */
	public Integer getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(Integer status) {
		this.status = status;
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

	/**
	 * @return the resourceTypeId
	 */
	public String getResourceTypeId() {
		return resourceTypeId;
	}

	/**
	 * @param resourceTypeId the resourceTypeId to set
	 */
	public void setResourceTypeId(String resourceTypeId) {
		this.resourceTypeId = resourceTypeId;
	}

	/**
	 * @return the typeId
	 */
	public String getTypeId() {
		return typeId;
	}

	/**
	 * @param typeId the typeId to set
	 */
	public void setTypeId(String typeId) {
		this.typeId = typeId;
	}

	/**
	 * @return the protocolId
	 */
	public String getProtocolId() {
		return protocolId;
	}

	/**
	 * @param protocolId the protocolId to set
	 */
	public void setProtocolId(String protocolId) {
		this.protocolId = protocolId;
	}

	/**
	 * @return the resouceTypeClassName
	 */
	public String getResouceTypeClassName() {
		return resouceTypeClassName;
	}

	/**
	 * @param resouceTypeClassName the resouceTypeClassName to set
	 */
	public void setResouceTypeClassName(String resouceTypeClassName) {
		this.resouceTypeClassName = resouceTypeClassName;
	}

}
