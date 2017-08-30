package com.whayer.cloud.storage.business.base.data;

public class BusinessEnum {
	/**
	 * 图片格式枚举
	 * @ClassName: ImageType
	 * @author fsong
	 * @date 2017年8月24日 下午5:26:21
	 * @version v1.0.0
	 * 
	 */
	public static enum ImageType {
		JPG, JPEG, PNG, BMP, GIF, TIFF
	}

	/**
	 * 码流类型枚举
	 * @ClassName: StreamType
	 * @author fsong
	 * @date 2017年8月24日 下午5:26:30
	 * @version v1.0.0
	 * 
	 */
	public static enum StreamType {
		PS, ES, PRIVATE
	}

	/**
	 * 编码格式
	 * @ClassName: CodeFormat
	 * @author fsong
	 * @date 2017年8月24日 下午5:27:35
	 * @version v1.0.0
	 * 
	 */
	public static enum CodeFormat {
		H264, G711, H264_G711
	}

	/**
	 * 录像（录音）文件数据类型
	 * @ClassName: RecordDataType
	 * @author fsong
	 * @date 2017年8月24日 下午5:28:53
	 * @version v1.0.0
	 * 
	 */
	public static enum RecordDataType {
		VIDEO_RECORD, AUDIO_RECORD, VIDEO_AUDIO_MIX_RECORD
	}
	
	/**
	 * 录像（录音）文件类型
	 * @ClassName: RecordType
	 * @author fsong
	 * @date 2017年8月24日 下午5:31:44
	 * @version v1.0.0
	 * 
	 */
	public static enum RecordType {
		MANAUL_RECORD, PLAN_RECORD, ALARM_EVENT_RECORD
	}
}
