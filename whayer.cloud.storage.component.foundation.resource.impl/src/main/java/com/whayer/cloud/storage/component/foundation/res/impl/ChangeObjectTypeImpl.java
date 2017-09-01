/**  
 *资源管理
 * @Title: ChangeObjectTypeImpl.java
 * @Package com.whayer.cloud.storage.component.foundation.res.impl
 * @author Administrator
 * @date 2017年8月21日 下午2:14:28
 * @version v1.0.0
 */
package com.whayer.cloud.storage.component.foundation.res.impl;

import com.whayer.cloud.storage.business.base.data.res.AreaTypeMeta;
import com.whayer.cloud.storage.business.base.data.res.ProtocolTypeMeta;
import com.whayer.cloud.storage.business.base.data.res.Resource;
import com.whayer.cloud.storage.business.base.data.res.ResourceTypeMeta;
import com.whayer.cloud.storage.business.base.data.type.Type;
import com.whayer.cloud.storage.business.device.data.BaseDeviceInfo;
import com.whayer.cloud.storage.business.device.data.DeviceTypeMeta;
import com.whayer.cloud.storage.business.device.data.DvrDeviceInfo;
import com.whayer.cloud.storage.business.device.data.LoginAccountInfo;
import com.whayer.cloud.storage.business.device.data.LoginNetInfo;
import com.whayer.cloud.storage.component.foundation.access.IChangeObjectType;
import com.whayer.cloud.storage.component.foundation.access.beans.BaseDeviceInfoPojo;
import com.whayer.cloud.storage.component.foundation.access.beans.DvrDeviceInfoPojo;
import com.whayer.cloud.storage.component.foundation.access.beans.ResourcePojo;
import com.whayer.cloud.storage.component.foundation.access.beans.TableRowPojo;
import com.whayer.cloud.storage.component.foundation.meta.IMetaDataService;

/**
 * 对象转换工具
 * @ClassName: ChangeObjectTypeImpl
 * @author Administrator
 * @date 2017年8月21日 下午2:14:28
 * @version v1.0.0
 * 
 */
public class ChangeObjectTypeImpl implements IChangeObjectType {

	@Override
	public <T> Resource PojoToDto(TableRowPojo t) {
		Resource resource = null;
		if (t == null) {
			return resource;
		}
		String className = t.getResouceTypeClassName();
		if (Resource.class.getName().equals(className)) {
			resource = changeResourceType(t);
		} else if (BaseDeviceInfo.class.getName().equals(className)) {
			resource = changeBaseDeviceInfoType(t);
		} else if (DvrDeviceInfo.class.getName().equals(className)) {
			resource = changeDvrDeviceInfoType(t);
		}

		return resource;
	}

	@Override
	public <T> Object DtoToPojo(T t) {
		Object object = null;
		if (t == null)
			return object;
		String className = t.getClass().getName();
		if (Resource.class.getName().equals(className)) {
			object = changeResourcePojoType((Resource) t);
		} else if (BaseDeviceInfo.class.getName().equals(className)) {
			object = changeBaseDeviceInfoPojoType((BaseDeviceInfo) t);
		} else if (DvrDeviceInfo.class.getName().equals(className)) {
			object = changeDvrDeviceInfoPojoType((DvrDeviceInfo) t);
		}
		return object;
	}

	/**
	 * 类型转换
	 * @Title: changeType
	 * @param resource
	 * @return ResourcePojo
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private ResourcePojo changeResourcePojoType(Resource resource) {
		ResourcePojo resourcePojo = new ResourcePojo();
		resourcePojo.setCode(resource.getCode());
		resourcePojo.setDesc(resource.getDesc());
		resourcePojo.setId(resource.getId());
		resourcePojo.setName(resource.getName());
		resourcePojo.setParentId(resource.getParentId());
		resourcePojo.setStatus(resource.getStatus());
		resourcePojo.setResouceTypeClassName(resource.getClass().getName());
		ProtocolTypeMeta protocolTypeMeta = resource.getProtocol();
		ResourceTypeMeta resourceTypeMeta = resource.getResourceType();
		Type type = resource.getType();
		if (protocolTypeMeta != null) {
			resourcePojo.setProtocolId(protocolTypeMeta.getId());
		}
		if (resourceTypeMeta != null) {
			resourcePojo.setResourceTypeId(resourceTypeMeta.getId());
			if (type != null) {
				resourcePojo.setTypeId(type.getId());
			}
		}
		return resourcePojo;
	}

	/**
	 * 类型转换
	 * @Title: changeType
	 * @param baseDeviceInfo
	 * @return ResourcePojo
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private BaseDeviceInfoPojo changeBaseDeviceInfoPojoType(BaseDeviceInfo baseDeviceInfo) {
		BaseDeviceInfoPojo baseDeviceInfoPojo = new BaseDeviceInfoPojo();
		baseDeviceInfoPojo.setCode(baseDeviceInfo.getCode());
		baseDeviceInfoPojo.setDesc(baseDeviceInfo.getDesc());
		baseDeviceInfoPojo.setId(baseDeviceInfo.getId());
		baseDeviceInfoPojo.setName(baseDeviceInfo.getName());
		baseDeviceInfoPojo.setParentId(baseDeviceInfo.getParentId());
		baseDeviceInfoPojo.setStatus(baseDeviceInfo.getStatus());
		baseDeviceInfoPojo.setAddress(baseDeviceInfo.getAddress());
		baseDeviceInfoPojo.setChannel(baseDeviceInfo.getChannel());
		baseDeviceInfoPojo.setRemark(baseDeviceInfo.getRemark());
		baseDeviceInfoPojo.setVender(baseDeviceInfo.getVender());
		baseDeviceInfoPojo.setVersion(baseDeviceInfo.getVersion());
		baseDeviceInfoPojo.setResouceTypeClassName(baseDeviceInfo.getClass().getName());
		ProtocolTypeMeta protocolTypeMeta = baseDeviceInfo.getProtocol();
		ResourceTypeMeta resourceTypeMeta = baseDeviceInfo.getResourceType();
		Type type = baseDeviceInfo.getType();
		if (protocolTypeMeta != null) {

			baseDeviceInfoPojo.setProtocolId(protocolTypeMeta.getId());

		}
		if (resourceTypeMeta != null) {

			baseDeviceInfoPojo.setResourceTypeId(resourceTypeMeta.getId());

			if (type != null) {

				baseDeviceInfoPojo.setTypeId(type.getId());
			}
		}
		return baseDeviceInfoPojo;
	}

	/**
	 * 
	 * 类型转换
	 * @Title: changeType
	 * @param dvrDeviceInfo
	 * @return DvrDeviceInfoPojo
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private DvrDeviceInfoPojo changeDvrDeviceInfoPojoType(DvrDeviceInfo dvrDeviceInfo) {

		DvrDeviceInfoPojo dvrDeviceInfoPojo = new DvrDeviceInfoPojo();
		dvrDeviceInfoPojo.setAddress(dvrDeviceInfo.getAddress());
		dvrDeviceInfoPojo.setChannel(dvrDeviceInfo.getChannel());
		dvrDeviceInfoPojo.setCode(dvrDeviceInfoPojo.getCode());
		dvrDeviceInfoPojo.setDesc(dvrDeviceInfo.getDesc());
		dvrDeviceInfoPojo.setId(dvrDeviceInfo.getId());
		dvrDeviceInfoPojo.setName(dvrDeviceInfo.getName());
		dvrDeviceInfoPojo.setParentId(dvrDeviceInfo.getParentId());
		dvrDeviceInfoPojo.setRemark(dvrDeviceInfo.getRemark());
		dvrDeviceInfoPojo.setResouceTypeClassName(dvrDeviceInfo.getClass().getName());
		dvrDeviceInfoPojo.setStatus(dvrDeviceInfo.getStatus());
		dvrDeviceInfoPojo.setVender(dvrDeviceInfo.getVender());
		dvrDeviceInfoPojo.setVersion(dvrDeviceInfo.getVersion());
		ProtocolTypeMeta protocolTypeMeta = dvrDeviceInfo.getProtocol();
		ResourceTypeMeta resourceTypeMeta = dvrDeviceInfo.getResourceType();
		Type type = dvrDeviceInfo.getType();

		if (dvrDeviceInfo.getNetInfo() != null) {
			dvrDeviceInfoPojo.setIp(dvrDeviceInfo.getNetInfo().getIp());
			dvrDeviceInfoPojo.setPort(dvrDeviceInfo.getNetInfo().getPort());
		}
		if (dvrDeviceInfo.getAccountInfo() != null) {
			dvrDeviceInfoPojo.setUserName(dvrDeviceInfo.getAccountInfo().getName());
			dvrDeviceInfoPojo.setPassword(dvrDeviceInfo.getAccountInfo().getPassword());
			dvrDeviceInfoPojo.setEncryptMethod(dvrDeviceInfo.getAccountInfo().getEncryptMethod());
		}
		if (protocolTypeMeta != null) {

			dvrDeviceInfoPojo.setProtocolId(protocolTypeMeta.getId());

		}
		if (resourceTypeMeta != null) {

			dvrDeviceInfoPojo.setResourceTypeId(resourceTypeMeta.getId());

			if (type != null) {

				dvrDeviceInfoPojo.setTypeId(type.getId());
			}
		}
		return dvrDeviceInfoPojo;
	}

	/**
	 * 
	 * 类型转换
	 * pojo转换为传输对象
	 * @Title: changeType
	 * @param resourcePojo
	 * @return Resource
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private Resource changeResourceType(TableRowPojo resourcePojo) {
		Resource resource = new Resource();
		resource.setCode(resourcePojo.getCode());
		resource.setDesc(resourcePojo.getDesc());
		resource.setId(resourcePojo.getId());
		resource.setName(resourcePojo.getName());
		resource.setParentId(resourcePojo.getParentId());
		resource.setStatus(resourcePojo.getStatus());

		return resource;
	}

	/**
	 * 
	 * 类型转换
	 * pojo转换为传输对象
	 * @Title: changeType
	 * @param resourcePojo
	 * @return Resource
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private BaseDeviceInfo changeBaseDeviceInfoType(TableRowPojo baseDeviceInfoPojo) {
		BaseDeviceInfo baseDeviceInfo = new BaseDeviceInfo();
		baseDeviceInfo.setCode(baseDeviceInfoPojo.getCode());
		baseDeviceInfo.setDesc(baseDeviceInfoPojo.getDesc());
		baseDeviceInfo.setId(baseDeviceInfoPojo.getId());
		baseDeviceInfo.setName(baseDeviceInfoPojo.getName());
		baseDeviceInfo.setParentId(baseDeviceInfoPojo.getParentId());

		baseDeviceInfo.setStatus(baseDeviceInfoPojo.getStatus());
		baseDeviceInfo.setAddress(baseDeviceInfoPojo.getAddress());
		baseDeviceInfo.setChannel(baseDeviceInfoPojo.getChannel());
		baseDeviceInfo.setRemark(baseDeviceInfoPojo.getRemark());
		baseDeviceInfo.setVender(baseDeviceInfoPojo.getVender());
		baseDeviceInfo.setVersion(baseDeviceInfoPojo.getVersion());
		return baseDeviceInfo;
	}

	/**
	 * 
	 * 类型转换
	 * pojo转换为传输对象
	 * @Title: changeType
	 * @param resourcePojo
	 * @return Resource
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private DvrDeviceInfo changeDvrDeviceInfoType(TableRowPojo dvrDeviceInfoPojo) {
		DvrDeviceInfo dvrDeviceInfo = new DvrDeviceInfo();
		dvrDeviceInfo.setCode(dvrDeviceInfoPojo.getCode());
		dvrDeviceInfo.setDesc(dvrDeviceInfoPojo.getDesc());
		dvrDeviceInfo.setId(dvrDeviceInfoPojo.getId());
		dvrDeviceInfo.setName(dvrDeviceInfoPojo.getName());
		dvrDeviceInfo.setParentId(dvrDeviceInfoPojo.getParentId());

		dvrDeviceInfo.setStatus(dvrDeviceInfoPojo.getStatus());
		dvrDeviceInfo.setAddress(dvrDeviceInfoPojo.getAddress());
		dvrDeviceInfo.setChannel(dvrDeviceInfoPojo.getChannel());
		dvrDeviceInfo.setRemark(dvrDeviceInfoPojo.getRemark());
		dvrDeviceInfo.setVender(dvrDeviceInfoPojo.getVender());
		dvrDeviceInfo.setVersion(dvrDeviceInfoPojo.getVersion());
		if (dvrDeviceInfoPojo.getEncryptMethod() != null || dvrDeviceInfoPojo.getUserName() != null
				|| dvrDeviceInfoPojo.getPassword() != null) {
			LoginAccountInfo acc = new LoginAccountInfo();
			dvrDeviceInfo.setAccountInfo(acc);
			acc.setEncryptMethod(dvrDeviceInfoPojo.getEncryptMethod());
			acc.setName(dvrDeviceInfoPojo.getUserName());
			acc.setPassword(dvrDeviceInfoPojo.getPassword());
		}
		if (dvrDeviceInfoPojo.getIp() != null || dvrDeviceInfoPojo.getPort() != null) {
			LoginNetInfo netInfo = new LoginNetInfo();
			netInfo.setIp(dvrDeviceInfoPojo.getIp());
			netInfo.setPort(dvrDeviceInfoPojo.getPort());
			dvrDeviceInfo.setNetInfo(netInfo);
		}
		return dvrDeviceInfo;
	}

	@Override
	public void queryTypeMeta(ResourceTypeMeta resourceTypeMeta, IMetaDataService metaDataService, Resource resource,
			TableRowPojo tableRowPojo) {
		if (Resource.class.getName().equals(resourceTypeMeta.getResouceClassName())) {
			resource.setType(metaDataService.getMetaData(AreaTypeMeta.class, tableRowPojo.getTypeId()));
		} else if (DeviceTypeMeta.class.getName().equals(resourceTypeMeta.getResouceClassName())) {
			resource.setType(metaDataService.getMetaData(DeviceTypeMeta.class, tableRowPojo.getTypeId()));
		}

	}

}
