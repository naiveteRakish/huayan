package com.whayer.cloud.storage.component.device.deviceaccess;

import java.util.List;

import com.whayer.cloud.storage.business.base.data.QueryCondition;
import com.whayer.cloud.storage.component.device.deviceaccess.beans.BaseDeviceInfoPojo;

/**
 * 前端设备存取接口
 * @ClassName: IDeviceAccess
 * @author fsong
 * @date 2017年8月9日 下午6:15:01
 * @version v1.0.0
 * 
 */
public interface IDeviceAccess {
	/**
	 * 添加前端设备数据
	 * @Title: addDeviceInfo
	 * @param deviceInfo	设备数据对象
	 * @return boolean		是否成功添加
	 * @author fsong
	 */
	boolean addDeviceInfo(BaseDeviceInfoPojo deviceInfo);

	/**
	 * 批量添加前端设备数据
	 * @Title: addDeviceInfos	
	 * @param deviceInfos	设备数据对象集
	 * @return boolean	是否成功添加
	 * @author fsong
	 */
	boolean addDeviceInfos(List<BaseDeviceInfoPojo> deviceInfos);

	/**
	 * 修改设备数据对象
	 * @Title: modifyDeviceInfo
	 * @param deviceInfo	设备数据对象
	 * @return boolean		是否成功修改
	 * @author fsong
	 */
	boolean modifyDeviceInfo(BaseDeviceInfoPojo deviceInfo);

	/**
	 * 批量修改设备数据对象
	 * @Title: modifyDeviceInfo
	 * @param deviceInfo	设备数据对象
	 * @return boolean		是否成功修改
	 * @author fsong
	 */
	boolean modifyDeviceInfos(List<BaseDeviceInfoPojo> deviceInfo);

	/**
	 * 根据条件删除设备对象
	 * @Title: deleteDeviceInfo
	 * @param condition	条件
	 * @return boolean 是否成功删除
	 * @author fsong
	 */
	boolean deleteDeviceInfo(List<QueryCondition> condition);

	/**
	 * 获取满足条件的设备数据对象记录数
	 * @Title: queryDeviceInfoSize
	 * @param condition		查询条件
	 * @return int			符合条件的设备记录数
	 * @author fsong
	 */
	int queryDeviceInfoSize(List<QueryCondition> condition);

	/**
	 * 分页查询设备数据对象记录
	 * @Title: queryDeviceINfo
	 * @param start		开始位置
	 * @param end  		结束位置
	 * @param condition	条件
	 * @return List<BaseDeviceInfo>	 返回查询设备数据对象记录
	 * @author fsong
	 */
	List<BaseDeviceInfoPojo> queryDeviceInfo(int start, int end, List<QueryCondition> condition);
}
