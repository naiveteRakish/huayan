package com.whayer.cloud.storage.component.foundation.res.impl;

import java.util.ArrayList;
import java.util.List;

import whayer.component.core.IService;

import com.whayer.cloud.storage.business.base.data.QueryCondition;
import com.whayer.cloud.storage.business.base.data.QueryOperator;
import com.whayer.cloud.storage.business.base.data.res.ProtocolTypeMeta;
import com.whayer.cloud.storage.business.base.data.res.Resource;
import com.whayer.cloud.storage.business.base.data.res.ResourceTypeMeta;
import com.whayer.cloud.storage.component.foundation.access.IAccessService;
import com.whayer.cloud.storage.component.foundation.access.IChangeObjectType;
import com.whayer.cloud.storage.component.foundation.access.beans.ResourcePojo;
import com.whayer.cloud.storage.component.foundation.access.beans.TableRowPojo;
import com.whayer.cloud.storage.component.foundation.meta.IMetaDataService;
import com.whayer.cloud.storage.component.foundation.res.IResourceService;
import com.whayer.cloud.storage.component.foundation.res.dao.ISqlFunctionDao;

public class ResourceServiceImpl implements IResourceService, IService {

	private IAccessService accessService;

	private IChangeObjectType changeObjectType;

	private IMetaDataService metaDataService;

	private ISqlFunctionDao sqlFunctionDao;

	/**
	 * @param metaDataService the metaDataService to set
	 */
	public void setMetaDataService(IMetaDataService metaDataService) {
		this.metaDataService = metaDataService;
	}

	/**
	 * @param sqlFunctionDao the sqlFunctionDao to set
	 */
	public void setSqlFunctionDao(ISqlFunctionDao sqlFunctionDao) {
		this.sqlFunctionDao = sqlFunctionDao;
	}

	/**
	 * @return the changeObjectType
	 */
	public IChangeObjectType getChangeObjectType() {
		return changeObjectType;
	}

	/**
	 * @param changeObjectType the changeObjectType to set
	 */
	public void setChangeObjectType(IChangeObjectType changeObjectType) {
		this.changeObjectType = changeObjectType;
	}

	/**
	 * @return the accessService
	 */
	public IAccessService getAccessService() {
		return accessService;
	}

	/**
	 * @param accessService the accessService to set
	 */
	public void setAccessService(IAccessService accessService) {
		this.accessService = accessService;
	}

	/**
	 * 添加资源,必须同时将T的className存储到Resouce对象对应的pojo对象的字段中
	 * （Resource及其子类的pojo对象必须都有一个resouceTypeClassName字段）
	 * @Title: save
	 * @param resource
	 * @return boolean
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	public <T extends Resource> boolean save(T resource) {

		return sigleResourceOperation(resource, true);

	}

	/**
	 * 添加同级资源，父资源必须存在，且在resouce对象携带对应父资源ID,必须同时将T的className存储到Resouce对象对应的pojo对象的字段中
	 * （Resource及其子类的pojo对象必须都有一个resouceTypeClassName字段）
	 * @Title: save
	 * @param resources
	 * @return boolean
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	public <T extends Resource> boolean save(List<T> resources) {
		return listOperation(resources, true);
	}

	/**
	 * 更新资源,必须同时将T的className存储到Resouce对象对应的pojo对象的字段中
	 * （Resource及其子类的pojo对象必须都有一个resouceTypeClassName字段）
	 * @Title: update
	 * @param resource
	 * @return boolean
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	public <T extends Resource> boolean update(T resource) {

		return sigleResourceOperation(resource, false);

	}

	/**
	 * 更新资源状态,必须同时将T的className存储到Resouce对象对应的pojo对象的字段中
	 * （Resource及其子类的pojo对象必须都有一个resouceTypeClassName字段）
	 * @Title: update
	 * @param status
	 * @return boolean
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	public boolean update(String id, int status) {
		if (id == null) {
			return false;
		}
		List<QueryCondition> condition = new ArrayList<>();
		condition.add(new QueryCondition("id", new QueryOperator(QueryOperator.Operator.EQUAL), id));
		List<ResourcePojo> resource = accessService.query(ResourcePojo.class, -1, -1, condition);
		if (resource != null && resource.size() != 0) {
			resource.get(0).setStatus(status);
			return accessService.update(resource.get(0));
		}
		return false;
	}

	/**
	 * 更新同级资源，父资源必须存在，且在resouce对象携带对应父资源ID,必须同时将T的className存储到Resouce对象对应的pojo对象的字段中
	 * （Resource及其子类的pojo对象必须都有一个resouceTypeClassName字段）
	 * @Title: update
	 * @param resources
	 * @return boolean
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	public <T extends Resource> boolean update(List<T> resources) {

		return listOperation(resources, false);
	}

	/**
	 * 根据ID删除资源，同时删除该资源下所有子资源
	 * @Title: delete
	 * @param id
	 * @return boolean
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	public boolean delete(String id) {
		if (id == null)
			return false;
		List<QueryCondition> condition = new ArrayList<>();
		condition.add(new QueryCondition("id", new QueryOperator(QueryOperator.Operator.EQUAL), id));
		return accessService.delete(ResourcePojo.class, condition);
	}

	/**
	 * 根据ID获取一个资源对象，必须是一个Resouce或者其子类
	 * 必须根据对应的pojo对象的字段中的resouceTypeClassName字段返回具体子类对象
	 * @Title: get
	 * @param id
	 * @return T
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	public Resource get(String id) {
		Resource resource = null;
		List<QueryCondition> list = new ArrayList<>();
		list.add(new QueryCondition("id", new QueryOperator(QueryOperator.Operator.EQUAL), id));
		List<TableRowPojo> resources = accessService.query(TableRowPojo.class, -1, -1, list);
		if (resources != null && resources.size() == 1) {
			TableRowPojo tableRowPojo = resources.get(0);
			resource = changeObjectType.PojoToDto(tableRowPojo);
			assembly(tableRowPojo, resource);
		}
		return resource;
	}

	/**
	 * 根据code获取一个资源对象，可以是一个Resouce或者其子类,必须是一个Resouce或者其子类
	 * 必须根据对应的pojo对象的字段中的resouceTypeClassName字段返回具体子类对象
	 * @Title: getByCode
	 * @param code
	 * @return T
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	public Resource getByCode(String code) {
		Resource resource = null;
		List<QueryCondition> list = new ArrayList<>();
		list.add(new QueryCondition("code", new QueryOperator(QueryOperator.Operator.EQUAL), code));
		List<TableRowPojo> resources = accessService.query(TableRowPojo.class, -1, -1, list);
		if (resources != null) {
			resource = (Resource) changeObjectType.PojoToDto(resources.get(0));
			assembly(resources.get(0), resource);
		}
		return resource;
	}

	/**
	 * 根据目标子设备的编码，向上递归查找目标子设备所属的终端资源对象
	 * （递归查询，向上查询目标设备的第一个协议字段不为空的记录）
	 * 返回必须是一个Resouce或者其子类
	 * 必须根据对应的pojo对象的字段中的resouceTypeClassName字段返回具体子类对象
	 * @Title: getTerminalIdByDeviceCode
	 * @param code
	 * @return String
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	public Resource getTerminalByDeviceCode(String code) {
		Resource resource = null;
		if (code != null) {
			TableRowPojo tableRowPojo = sqlFunctionDao.getRowByCode(code);
			if (tableRowPojo != null) {

				resource = changeObjectType.PojoToDto(tableRowPojo);
			}
		}
		return resource;
	}

	/**
	 * 根据目标子设备的编码，向上递归查找目标子设备所属的终端资源对象
	 * （递归查询，向上查询目标设备的第一个协议字段不为空的记录）
	 * 返回必须是一个Resouce或者其子类
	 * 必须根据对应的pojo对象的字段中的resouceTypeClassName字段返回具体子类对象
	 * @Title: getTerminalIdByDeviceId
	 * @param id
	 * @return String
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	public Resource getTerminalByDeviceId(String id) {
		Resource resource = null;
		if (id != null) {
			TableRowPojo tableRowPojo = sqlFunctionDao.getRowById(id);
			if (tableRowPojo != null) {
				resource = changeObjectType.PojoToDto(tableRowPojo);
			}
		}
		return resource;
	}

	/**
	 * 返回所有终端对象，必须是一个Resouce或者其子类
	 * 必须根据对应的pojo对象的字段中的resouceTypeClassName字段返回具体子类对象
	 * @Title: getTerminals
	 * @param formIndex
	 * @param toIndex
	 * @return List<Resource>
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	public List<Resource> getTerminals() {

		List<Resource> returnResource = null;
		List<TableRowPojo> resources = accessService.query(TableRowPojo.class, -1, -1, null);
		if (resources != null && resources.size() != 0) {
			returnResource = new ArrayList<Resource>();
			for (int i = 0; i < resources.size(); i++) {
				Resource resource = changeObjectType.PojoToDto(resources.get(i));
				returnResource.add(resource);
				assembly(resources.get(i), resource);
			}
		}
		return returnResource;
	}

	/**
	 * 分页查询资源，用于资源树维护
	 * @Title: find
	 * @param parentCode
	 * @param formIndex
	 * @param toIndex
	 * @return List<Resource>
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	public List<Resource> find(String parentCode, int formIndex, int toIndex) {
		List<Resource> returnResource = null;
		List<QueryCondition> list = new ArrayList<>();
		list.add(new QueryCondition("parentId", new QueryOperator(QueryOperator.Operator.EQUAL), parentCode));
		List<TableRowPojo> resources = accessService.query(TableRowPojo.class, formIndex, toIndex, list);
		if (resources != null && resources.size() != 0) {
			returnResource = new ArrayList<Resource>();
			for (int i = 0; i < resources.size(); i++) {
				Resource resource = (Resource) changeObjectType.PojoToDto(resources.get(i));
				returnResource.add(resource);
				assembly(resources.get(i), resource);
			}
		}
		return returnResource;
	}

	/**
	 * 查询资源条数，用于资源树维护
	 * @Title: count
	 * @param parentCode
	 * @return int
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	public int count(String parentCode) {

		List<QueryCondition> list = new ArrayList<>();
		list.add(new QueryCondition("parentId", new QueryOperator(QueryOperator.Operator.EQUAL), parentCode));
		int count = accessService.count(ResourcePojo.class, list);
		return count;
	}

	/**
	 * 
	 * 该方法包含资源对象的 save和update功能  (包含了类型判断、属性检查、类型转换、dao操作) 
	 * @Title: sigleResourceOperation
	 * @param resources
	 * @param sign 添加=true   修改=update
	 * @return boolean
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private <T extends Resource> boolean sigleResourceOperation(T resource, boolean sign) {

		// 判断父资源必须存在
		if (checkItime(resource)) {
			return false;

		}

		// 普通资源：
		Resource resource1 = (Resource) resource;
		// 检查字段
		if (checkProperty(resource1)) {
			return false;
		}
		// 转换对象
		Object resourcePojo = changeObjectType.DtoToPojo(resource1);

		if (sign) {
			return accessService.add(resourcePojo);
		} else {
			return accessService.update(resourcePojo);
		}
	}

	/**
	 * 
	 * 该方法包含资源集合的 save和update功能  (包含了类型判断、属性检查、类型转换、dao操作) 
	 * @Title: listOperation
	 * @param resources
	 * @param sign
	 * @return boolean
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private <T extends Resource> boolean listOperation(List<T> resources, boolean sign) {
		List<Object> resourcePojos = null;
		T resource = null;
		// 普通资源
		resourcePojos = new ArrayList<Object>();
		for (int i = 0; i < resources.size(); i++) {
			resource = resources.get(i);
			// 检查是否存在父资源
			if (checkItime(resource)) {
				return false;
			}
			Resource resource1 = (Resource) resource;
			// 检查属性
			if (checkProperty(resource1)) {
				return false;
			}
			// 转换对象
			Object resourcePojo = changeObjectType.DtoToPojo(resource1);

			resourcePojos.add(resourcePojo);
		}
		if (sign) {
			return accessService.addAll(resourcePojos);

		} else {
			return accessService.updateAll(resourcePojos);
		}
	}

	/**
	 * 对象检查
	 * @Title: checkType
	 * @param resource1 void
	 * @return 
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private boolean checkProperty(Resource resource1) {
		// 1.父id 字段必须存在
		return false;

	}

	/**
	 * 检查元素父类资源是否存在
	 * @Title: checkItime
	 * @return boolean
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private <T extends Resource> boolean checkItime(T resource) {
		boolean result = false;
		// if (resource == null) {
		// return true;
		// }
		// // 判断父资源必须存在
		// if (resource.getParentId() != null) {
		// List<QueryCondition> condition = new ArrayList<>();
		// condition.add(new QueryCondition("id", new
		// QueryOperator(QueryOperator.Operator.EQUAL), resource
		// .getParentId()));
		// if (result = (accessService.count(ResourcePojo.class, condition) ==
		// 0)) {
		//
		// return result;
		//
		// }
		// }
		return result;
	}

	/**
	 * 组装对象
	 * @Title: assembly
	 * @param tableRowPojo
	 * @param resource void
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private void assembly(TableRowPojo tableRowPojo, Resource resource) {
		if (tableRowPojo.getProtocolId() != null) {
			resource.setProtocol(metaDataService.getMetaData(ProtocolTypeMeta.class, tableRowPojo.getProtocolId()));
		}

		if (tableRowPojo.getResourceTypeId() != null) {
			resource.setResourceType(metaDataService.getMetaData(ResourceTypeMeta.class,
					tableRowPojo.getResourceTypeId()));
			if (tableRowPojo.getTypeId() != null) {
				changeObjectType.queryTypeMeta(resource.getResourceType(), metaDataService, resource, tableRowPojo);

			}
		}
	}

	@Override
	public void start() {
		((IService) metaDataService).start();

	}

	@Override
	public void stop() {
		((IService) metaDataService).stop();
	}
}
