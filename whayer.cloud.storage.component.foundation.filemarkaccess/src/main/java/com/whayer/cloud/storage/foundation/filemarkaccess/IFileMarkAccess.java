package com.whayer.cloud.storage.foundation.filemarkaccess;

import java.util.List;

import com.whayer.cloud.storage.business.base.data.QueryComplexCondition;
import com.whayer.cloud.storage.foundation.filemarkaccess.beans.BaseFileInfoPojo;
import com.whayer.cloud.storage.foundation.filemarkaccess.beans.FileMarkInfoPojo;

/**
 * 文件标注数据访问组件，本组件存放文件描述对象与文件标注对象，一个文件描述对象关联多个文件标注对象
 * ，文件标注对象业务上属于文件描述对象的扩展对象，因此添加标注对象时文件描述对象必须存在，删除文件描述对象时必须删
 * 除关联的标注对象
 * @ClassName: IFileMarkAccess
 * @author fsong
 * @date 2017年8月23日 下午3:00:46
 * @version v1.0.0
 * 
 */
public interface IFileMarkAccess {

	/**
	 * 新增文件描述对象
	 * @Title: addFileInfo
	 * @param fileInfo	文件描述对象
	 * @return boolean	返回是否成功添加
	 * @author fsong
	 */
	<T extends BaseFileInfoPojo> boolean addFileInfo(T fileInfo);

	/**
	 * 修改文件描述对象（id字段作为修改标识）
	 * @Title: modifyFileInfo
	 * @param fileInfo	文件描述对象
	 * @return boolean	返回是否成功修改
	 * @author fsong
	 */
	<T extends BaseFileInfoPojo> boolean modifyFileInfo(T fileInfo);

	/**
	 * 根据条件删除文件描述对象（关联的标注对象也将删除）
	 * @Title: deleteFileInfo
	 * @param type		文件描述对象类型
	 * @param queryComplexCondition	复杂组合条件
	 * @return boolean	返回是否成功删除
	 * @author fsong
	 */
	<T extends BaseFileInfoPojo> boolean deleteFileInfo(Class<T> type, QueryComplexCondition queryComplexCondition);

	/**
	* 查询满足条件的文件描述对象数量
	* @Title: queryFileInfoSize
	* @param type	文件描述对象类型
	* @param queryComplexCondition	复杂组合条件
	* @return int	返回文件描述对象数，返回-1表示异常
	* @author fsong
	*/
	<T extends BaseFileInfoPojo> int queryFileInfoSize(Class<T> type, QueryComplexCondition queryComplexCondition);

	/**
	* 查询满足条件的文件描述对象
	* @Title: queryFileInfo
	* @param type		文件描述对象类型
	* @param queryComplexCondition	复杂组合条件
	* @param start	记录开始位置
	* @param end	记录结束位置
	* @return List<T>	返回文件描述对象集合
	* @author fsong
	*/
	<T extends BaseFileInfoPojo> List<T> queryFileInfo(Class<T> type, QueryComplexCondition queryComplexCondition,
			int start, int end);

	/**
	 * 新增文件标注对象
	 * @Title: addFileMarkInfo
	 * @param fileMarkInfo	文件标注对象
	 * @return boolean	返回是否成功添加
	 * @author fsong
	 */
	<T extends FileMarkInfoPojo> boolean addFileMarkInfo(T fileMarkInfo);

	/**
	 * 修改文件标注对象（id字段作为修改标识）
	 * @Title: modifyFileMarkInfo
	 * @param fileMarkInfo	文件标注对象
	 * @return boolean	返回是否成功修改
	 * @author fsong
	 */
	<T extends FileMarkInfoPojo> boolean modifyFileMarkInfo(T fileMarkInfo);

	/**
	 * 根据条件删除文件标注对象
	 * @Title: deleteFileMarkInfo
	 * @param type		文件标注对象类型
	 * @param queryComplexCondition	复杂组合条件
	 * @return boolean	返回是否成功删除
	 * @author fsong
	 */
	<T extends FileMarkInfoPojo> boolean deleteFileMarkInfo(Class<T> type, QueryComplexCondition queryComplexCondition);

	/**
	 * 查询满足条件的文件标注对象数量
	 * @Title: queryFileInfoMarkSize
	 * @param type	文件标注对象类型
	 * @param queryComplexCondition	复杂组合条件
	 * @return int	返回文件标注对象数，返回-1表示异常
	 * @author fsong
	 */
	<T extends FileMarkInfoPojo> int queryFileInfoMarkSize(Class<T> type, QueryComplexCondition queryComplexCondition);

	/**
	 * 查询满足条件的文件标注对象
	 * @Title: queryFileInfo
	 * @param type		文件标注对象类型
	 * @param queryComplexCondition	复杂组合条件
	 * @param start	记录开始位置
	 * @param end	记录结束位置
	 * @return List<T>	返回文件标注对象集合
	 * @author fsong
	 */
	<T extends FileMarkInfoPojo> List<T> queryFileMarkInfo(Class<T> type, QueryComplexCondition queryComplexCondition,
			int start, int end);
}
