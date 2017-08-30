package com.whayer.cloud.storage.component.foundation.res;

import java.util.List;

import com.whayer.cloud.storage.business.base.data.res.Resource;

public interface IResourceService {

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
	public <T extends Resource> boolean save(T resource);

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
	public <T extends Resource> boolean save(List<T> resources);

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
	public <T extends Resource> boolean update(T resource);

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
	public boolean update(String id, int status);

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
	public <T extends Resource> boolean update(List<T> resources);

	/**
	 * 根据ID删除资源，同时删除该资源下所有子资源
	 * @Title: delete
	 * @param id
	 * @return boolean
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	public boolean delete(String id);

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
	public Resource get(String id);

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
	public Resource getByCode(String code);

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
	public Resource getTerminalByDeviceCode(String code);

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
	public Resource getTerminalByDeviceId(String id);

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
	public List<Resource> getTerminals();

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
	public List<Resource> find(String parentCode, int formIndex, int toIndex);

	/**
	 * 查询资源条数，用于资源树维护
	 * @Title: count
	 * @param parentCode
	 * @return int
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	public int count(String parentCode);

}
