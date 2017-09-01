package com.whayer.cloud.storage.business.base.data.type;

/**
 * @author fs 通用类型对象
 * 
 */

public class Type {
	/**
	 * 类型ID
	 */
	private String id;
	/**
	 * 类型编码
	 */
	private String code;
	/**
	 * 类型名称
	 */
	private String name;
	/**
	 * 类型名称描述
	 */
	private String desc;

	/**
	 * 获取
	 * 
	 * @return id
	 */
	public String getId() {
		return id;
	}

	/**
	 * 设置
	 * 
	 * @param id
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * 获取
	 * 
	 * @return code
	 */
	public String getCode() {
		return code;
	}

	/**
	 * 设置
	 * 
	 * @param code
	 */
	public void setCode(String code) {
		this.code = code;
	}

	/**
	 * 获取
	 * 
	 * @return name
	 */
	public String getName() {
		return name;
	}

	/**
	 * 设置
	 * 
	 * @param name
	 */
	public void setName(String name) {
		this.name = name;
	}

	/**
	 * 获取
	 * 
	 * @return desc
	 */
	public String getDesc() {
		return desc;
	}

	/**
	 * 设置
	 * 
	 * @param desc
	 */
	public void setDesc(String desc) {
		this.desc = desc;
	}
}
