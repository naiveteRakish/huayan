/**  
 * 用一句话描述该文件做什么
 * @Title: ResourcePojo.java
 * @Package com.whayer.cloud.storage.component.device.resourceaccess.beans
 * @author Administrator
 * @date 2017年8月17日 下午2:12:01
 * @version v1.0.0
 */
package com.whayer.cloud.storage.component.foundation.access.beans;

/**
 * 用一句话描述该文件做什么
 * @ClassName: ResourcePojo
 * @author Administrator
 * @date 2017年8月17日 下午2:12:01
 * @version v1.0.0
 * 
 */
public class ResourcePojo {

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
	 * 资源类型（普通、系统、设备）对应ResourceTypeMeta
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
	 * 不能通过某种协议与对应的设备构建终端的资源，此字段必须为空
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
	private int status;

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
	 * @return the status
	 */
	public int getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(int status) {
		this.status = status;
	}

}
