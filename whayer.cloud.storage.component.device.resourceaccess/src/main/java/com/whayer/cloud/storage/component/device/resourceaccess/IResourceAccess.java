package com.whayer.cloud.storage.component.device.resourceaccess;

import java.util.List;

import com.whayer.cloud.storage.business.base.data.QueryCondition;
import com.whayer.cloud.storage.component.device.resourceaccess.beans.ResourcePojo;

/**
 * 前段区域(普通)存取接口
 * @ClassName: IDeviceAccess
 * @author 
 * @date 2017年8月15日 下午15:07:01
 * @version v1.0.0
 * 
 */
public interface IResourceAccess {
	/**
	 * 添加前端区域数据
	 * @Title: addAreaInfo
	 * @param deviceInfo	设备数据对象
	 * @return boolean		是否成功添加
	 * @see 
	 * @throws
	 * @author 
	 */
	boolean addResource(ResourcePojo resourcePojo);

	/**
	 * 批量添加前端设备数据
	 * @Title: addAreaInfos
	 * @param deviceInfos	设备数据对象集
	 * @return boolean	是否成功添加
	 * @see 
	 * @throws
	 * @author 
	 */
	boolean addResources(List<ResourcePojo> areaInfos);

	/**
	 * 修改设备数据对象
	 * @Title: modifyAreaInfo
	 * @param AreaInfo	设备数据对象
	 * @return boolean		是否成功修改
	 * @see 
	 * @throws
	 * @author 
	 */
	boolean modifyResource(ResourcePojo areaResourceInfo);

	/**
	 * 批量修改设备数据对象
	 * @Title: modifyAreaInfo
	 * @param AreaInfo	设备数据对象
	 * @return boolean		是否成功修改
	 * @see 
	 * @throws
	 * @author 
	 */
	boolean modifyResources(List<ResourcePojo> areaResourceInfo);

	/**
	 * 根据条件删除设备对象
	 * @Title: deleteAreaInfo
	 * @param condition	条件
	 * @return boolean 是否成功删除
	 * @see 
	 * @throws
	 * @author 
	 */
	boolean deleteResource(List<QueryCondition> condition);

	/**
	 * 获取满足条件的设备数据对象记录数
	 * @Title: queryAreaInfoSize
	 * @param condition		查询条件
	 * @return int			符合条件的设备记录数
	 * @see 
	 * @throws
	 * @author 
	 */
	Long queryResourceSize(List<QueryCondition> condition);

	/**
	 * 分页查询设备数据对象记录
	 * @Title: queryAreaINfo
	 * @param start		开始位置
	 * @param end  		结束位置
	 * @param condition	条件
	 * @return List<BaseDeviceInfo>
	 * @see 
	 * @throws
	 * @author 
	 */
	List<ResourcePojo> queryResource(int start, int end, List<QueryCondition> condition);
}
