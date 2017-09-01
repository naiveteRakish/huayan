package com.whayer.cloud.storage.business.base.data.res;

import com.whayer.cloud.storage.business.base.data.Business;
import com.whayer.cloud.storage.business.base.data.type.Type;

public class Resource extends Business {

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
	private ResourceTypeMeta resourceType;

	/**
	 * 资源具体类型（如果是设备就对应设备类型元数据，如果是系统就对应系统类型元数据）
	 * @Fields remark
	 */
	private Type type;

	/**
	 * 资源协议类型（GB28181，海康SDK，大华SDK等等，以一种方式接入一个设备就产生一种协议类型）
	 * 不能通过某种协议与对应的设备构建终端的资源，此字段必须为空
	 * @Fields protocol对应ProtocolTypeMeta
	 */
	private ProtocolTypeMeta protocol;

	/**
	 * 资源状态(1:在线可用 ;0:离线;2:不可用)
	 * @Fields status
	 */
	private int status;

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public ResourceTypeMeta getResourceType() {
		return resourceType;
	}

	public void setResourceType(ResourceTypeMeta resourceType) {
		this.resourceType = resourceType;
	}

	public Type getType() {
		return type;
	}

	public void setType(Type type) {
		this.type = type;
	}

	public ProtocolTypeMeta getProtocol() {
		return protocol;
	}

	public void setProtocol(ProtocolTypeMeta protocol) {
		this.protocol = protocol;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
}
