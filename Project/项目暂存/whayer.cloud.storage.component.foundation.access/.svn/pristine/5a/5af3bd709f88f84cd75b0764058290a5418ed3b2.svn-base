/**  
 * 资源管理
 * @Title: ChangeObjectType.java
 * @Package com.whayer.cloud.storage.component.foundation.res
 * @author Administrator
 * @date 2017年8月21日 下午2:12:58
 * @version v1.0.0
 */
package com.whayer.cloud.storage.component.foundation.access;

import com.whayer.cloud.storage.business.base.data.res.Resource;
import com.whayer.cloud.storage.business.base.data.res.ResourceTypeMeta;
import com.whayer.cloud.storage.component.foundation.access.beans.TableRowPojo;
import com.whayer.cloud.storage.component.foundation.meta.IMetaDataService;

/**
 * 资源管理对象转换工具接口
 * @ClassName: ChangeObjectType
 * @author Administrator
 * @date 2017年8月21日 下午2:12:58
 * @version v1.0.0
 * 
 */
public interface IChangeObjectType {
	/**
	 * 
	 * 数据库对象转传输对象
	 * @Title: PojoToDto
	 * @return Object
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	public <T> Resource PojoToDto(TableRowPojo t);

	/**
	 * 传输对象转数据库对象
	 * @Title: DtoToPojo
	 * @return Object
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	public <T> Object DtoToPojo(T t);

	/**
	 * 查询具体的typeMeta类型 并封装
	 * @Title: queryTypeMeta
	 * @param resourceType
	 * @param metaDataService
	 * @param resource void
	 * @param tableRowPojo 
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	public void queryTypeMeta(ResourceTypeMeta resourceType, IMetaDataService metaDataService, Resource resource,
			TableRowPojo tableRowPojo);;
}
