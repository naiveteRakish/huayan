package com.whayer.cloud.storage.foundation.filemarkaccess.beans;


/**
 * 文件标注对象
 * @ClassName: FileMarkInfoPojo
 * @author fsong
 * @date 2017年8月24日 上午10:38:51
 * @version v1.0.0
 * 
 */
public class FileMarkInfoPojo {

	/**
	 * 标注对象ID（必填)
	 * @Fields id
	 */
	private String id;
	
	/**
	 * 文件描述对象ID（必填)
	 * @Fields fileInfoId
	 */
	private String fileInfoId;	
	
	/**
	 * 标注记录大小（选填）
	 * @Fields size
	 */
	private int size;	
	
	/**
	 * 标注对象所属用户Id,可以是系统或业务用户ID（必填)
	 * @Fields fileOwnUserId
	 */
	private String fileOwnUserId;
	
	/**
	 * 自定义备注信息（选填）
	 * @Fields markDesc
	 */
	private String markDesc;

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
	 * @return the fileInfoId
	 */
	public String getFileInfoId() {
		return fileInfoId;
	}

	/**
	 * @param fileInfoId the fileInfoId to set
	 */
	public void setFileInfoId(String fileInfoId) {
		this.fileInfoId = fileInfoId;
	}

	/**
	 * @return the size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * @param size the size to set
	 */
	public void setSize(int size) {
		this.size = size;
	}

	/**
	 * @return the fileOwnUserId
	 */
	public String getFileOwnUserId() {
		return fileOwnUserId;
	}

	/**
	 * @param fileOwnUserId the fileOwnUserId to set
	 */
	public void setFileOwnUserId(String fileOwnUserId) {
		this.fileOwnUserId = fileOwnUserId;
	}

	/**
	 * @return the markDesc
	 */
	public String getMarkDesc() {
		return markDesc;
	}

	/**
	 * @param markDesc the markDesc to set
	 */
	public void setMarkDesc(String markDesc) {
		this.markDesc = markDesc;
	}
	
	
	
}
