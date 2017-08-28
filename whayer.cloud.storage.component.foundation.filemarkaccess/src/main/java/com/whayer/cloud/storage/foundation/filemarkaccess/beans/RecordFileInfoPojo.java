package com.whayer.cloud.storage.foundation.filemarkaccess.beans;

import java.util.Date;

/**
 * 录像（录音）文件描述对象
 * @ClassName: RecordFileInfoPojo
 * @author fsong
 * @date 2017年8月24日 上午10:17:29
 * @version v1.0.0
 * 
 */
public class RecordFileInfoPojo extends BaseFileInfoPojo {

	/**
	 * 录像设备ID（选填）
	 * @Fields deviceId
	 */
	private String deviceId;

	/**
	 * 记录类型（必填）0:VIDEO_RECORD, 1:AUDIO_RECORD, 2:VIDEO_AUDIO_MIX_RECORD
	 * @Fields recordDataType
	 */
	private int recordDataType;

	/**
	 * 录像开始时间（选填）
	 * @Fields startTime
	 */
	private Date startTime;

	/**
	 * 录像结束时间（选填）
	 * @Fields endTime
	 */
	private Date endTime;

	/**
	 * 录像（录音）编码格式（选填，取值CodeFormat枚举）
	 * @Fields codeFormat
	 */
	private int codeFormat;

	/**
	 * 码流类型（选填，取值StreamType枚举）
	 * @Fields streamType
	 */
	private int streamType;

	/**
	 * @return the deviceId
	 */
	public String getDeviceId() {
		return deviceId;
	}

	/**
	 * @param deviceId the deviceId to set
	 */
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}

	/**
	 * @return the recordDataType
	 */
	public int getRecordDataType() {
		return recordDataType;
	}

	/**
	 * @param recordDataType the recordDataType to set
	 */
	public void setRecordDataType(int recordDataType) {
		this.recordDataType = recordDataType;
	}

	/**
	 * @return the startTime
	 */
	public Date getStartTime() {
		return startTime;
	}

	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(Date startTime) {
		this.startTime = startTime;
	}

	/**
	 * @return the endTime
	 */
	public Date getEndTime() {
		return endTime;
	}

	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(Date endTime) {
		this.endTime = endTime;
	}

	/**
	 * @return the codeFormat
	 */
	public int getCodeFormat() {
		return codeFormat;
	}

	/**
	 * @param codeFormat the codeFormat to set
	 */
	public void setCodeFormat(int codeFormat) {
		this.codeFormat = codeFormat;
	}

	/**
	 * @return the streamType
	 */
	public int getStreamType() {
		return streamType;
	}

	/**
	 * @param streamType the streamType to set
	 */
	public void setStreamType(int streamType) {
		this.streamType = streamType;
	}

}
