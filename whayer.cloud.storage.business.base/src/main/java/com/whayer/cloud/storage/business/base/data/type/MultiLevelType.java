package com.whayer.cloud.storage.business.base.data.type;


/**
 * @author fs 多层级类型对象
 * 
 */

public class MultiLevelType extends Type {
	/**
	 * 父类型ID
	 */
	private String parentId;

	/**
	 * 获取
	 * 
	 * @return parentId
	 */
	public String getParentId() {
		return parentId;
	}

	/**
	 * 设置
	 * 
	 * @param parentId
	 */
	public void setParentId(String parentId) {
		this.parentId = parentId;
	}
}
