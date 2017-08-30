package com.whayer.cloud.storage.component.foundation.meta;

import java.util.List;

import com.whayer.cloud.storage.business.base.data.QueryCondition;

/**
 * @ClassName: IMetaDataManager
 * TODO (元数据管理器)
 * @author fsong
 * @date 2017年8月2日 下午2:14:32
 * @version v1.0.0
 * 
 */
public interface IMetaDataService {
	/**
	 * @Title: addMetaData
	 * TODO (添加元数据记录项)
	 * @param item  元数据记录项
	 * @return boolean 添加结果
	 * @see 
	 * @throws
	 * @author fsong
	 */
	<T> boolean addMetaData(T item);

	/**
	 * @Title: addMetaDatas
	 * TODO (批量添加元数据记录项)
	 * @param items 元数据记录集
	 * @return boolean 添加结果
	 * @see 
	 * @throws
	 * @author fsong
	 */
	<T> boolean addMetaDatas(List<T> items);

	/**
	 * @Title: modifyMetaData
	 * TODO (修改元数据记录项)
	 * @param item 元数据记录项
	 * @return boolean 修改结果
	 * @see 
	 * @throws
	 * @author fsong
	 */
	<T> boolean modifyMetaData(T item);

	/**
	 * @Title: deleteMetaData
	 * TODO (删除元数据记录项)
	 * @param type	元数据类型
	 * @param id	元数据记录ID
	 * @return boolean	删除结果
	 * @see 
	 * @throws
	 * @author fsong
	 */
	<T> boolean deleteMetaData(Class<T> type, String id);

	/**
	 * @Title: deleteMetaData
	 * TODO (删除某种元数据记录项)
	 * @param type 	元数据类型
	 * @return boolean	删除结果
	 * @see 
	 * @throws
	 * @author fsong
	 */
	<T> boolean deleteMetaData(Class<T> type);

	/**
	 * @Title: getMetaData
	 * TODO (获取元数据记录项)
	 * @param type	元数据类型
	 * @param id	元数据记录ID
	 * @return T	元数据记录
	 * @see 
	 * @throws
	 * @author fsong
	 */
	<T> T getMetaData(Class<T> type, String id);

	/**
	 * @Title: getMetaData
	 * TODO (获取元数据记录项)
	 * @param type 	元数据类型
	 * @param condition 查询条件(注：QueryOperator暂时支持EQUAL、LIKE字段)
	 * @return List<T>
	 * @see 
	 * @throws
	 * @author fsong
	 */
	<T> List<T> getMetaData(Class<T> type, List<QueryCondition> condition);
}
