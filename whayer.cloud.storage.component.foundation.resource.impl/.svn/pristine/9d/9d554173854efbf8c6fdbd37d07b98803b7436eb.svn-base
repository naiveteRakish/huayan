package com.whayer.cloud.storage.component.foundation.res.implTest;

import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.whayer.cloud.storage.business.base.data.res.AreaTypeMeta;
import com.whayer.cloud.storage.business.base.data.res.ProtocolTypeMeta;
import com.whayer.cloud.storage.business.base.data.res.Resource;
import com.whayer.cloud.storage.business.base.data.res.ResourceTypeMeta;
import com.whayer.cloud.storage.business.device.data.BaseDeviceInfo;
import com.whayer.cloud.storage.business.device.data.DeviceTypeMeta;
import com.whayer.cloud.storage.component.foundation.access.IAccessService;
import com.whayer.cloud.storage.component.foundation.meta.IMetaDataService;
import com.whayer.cloud.storage.component.foundation.res.IResourceService;

@Test
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class ResourceServiceImplTest {

	private IResourceService resourceServiceImpl;
	private IAccessService accessService;
	private List<Resource> list;
	private IMetaDataService metaDateService;
	private List<String> resourceTypeMetas;
	private List<String> protocolTypeMetas;
	private List<String> deviceTypeMetas;
	private List<String> areaTypeMetas;

	@SuppressWarnings({ "static-access" })
	@BeforeTest
	public void beforeTest() {
		// 数据操作组件环境初始化
		@SuppressWarnings("resource")
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		resourceServiceImpl = (IResourceService) context.getBean(IResourceService.class);
		accessService = (IAccessService) context.getBean(IAccessService.class);
		metaDateService = (IMetaDataService) context.getBean(IMetaDataService.class);
		resourceTypeMetas = new ArrayList<String>();
		protocolTypeMetas = new ArrayList<String>();
		deviceTypeMetas = new ArrayList<String>();
		areaTypeMetas = new ArrayList<String>();

		for (int i = 0; i < 20; i++) {
			deviceTypeMetas.add("000");
			areaTypeMetas.add("000");
		}

		for (int i = 0; i < 20; i++) {
			ResourceTypeMeta resourceTypeMeta = new ResourceTypeMeta();
			resourceTypeMeta.setCode(UUID.randomUUID() + "");
			resourceTypeMeta.setDesc("描述1");
			resourceTypeMeta.setId(UUID.randomUUID() + "");
			resourceTypeMeta.setName("name");
			resourceTypeMeta.setParentId("parentid");
			if (i % 2 == 0) {
				resourceTypeMeta.setResouceClassName(DeviceTypeMeta.class.getName());
				DeviceTypeMeta deviceTypeMeta = new DeviceTypeMeta();
				deviceTypeMeta.setCode(UUID.randomUUID() + "");
				deviceTypeMeta.setDesc("描述1");
				deviceTypeMeta.setId(UUID.randomUUID() + "");
				deviceTypeMeta.setName("name");
				metaDateService.addMetaData(resourceTypeMeta);
				deviceTypeMetas.set(i, resourceTypeMeta.getId() + "");
			} else {
				resourceTypeMeta.setResouceClassName(AreaTypeMeta.class.getName());
				AreaTypeMeta areaTypeMeta = new AreaTypeMeta();
				areaTypeMeta.setCode(String.valueOf(UUID.randomUUID()));
				areaTypeMeta.setId(String.valueOf(UUID.randomUUID()));
				areaTypeMeta.setName("name" + i);
				areaTypeMeta.setDesc("desc");
				areaTypeMeta.setParentId("parentId" + i);
				metaDateService.addMetaData(areaTypeMeta);
				areaTypeMetas.set(i, areaTypeMeta.getId());
			}
			metaDateService.addMetaData(resourceTypeMeta);
			resourceTypeMetas.add(resourceTypeMeta.getId() + "");
			ProtocolTypeMeta protocolTypeMeta = new ProtocolTypeMeta();
			protocolTypeMeta.setCode(UUID.randomUUID() + "");
			protocolTypeMeta.setDesc("描述1");
			protocolTypeMeta.setId(UUID.randomUUID() + "");
			protocolTypeMeta.setName("name");
			metaDateService.addMetaData(resourceTypeMeta);
			protocolTypeMetas.add(resourceTypeMeta.getId() + "");

		}

		// 模拟前端系统创建多个对象
		list = new ArrayList<Resource>();
		UUID uuid = UUID.randomUUID();
		Resource af = null;
		String parentId = null;
		for (int i = 0; i < 20; i++) {
			if (i % 2 == 0) {
				// 普通资源
				af = new Resource();
				af.setId(String.valueOf(uuid.randomUUID()));
				af.setDesc("描述" + i);
				af.setCode(String.valueOf(uuid.randomUUID()));
				af.setName("名称" + i);
				parentId = af.getId();
				af.setParentId("parentId" + af.getId());
				ProtocolTypeMeta protocol = new ProtocolTypeMeta();
				protocol.setId(protocolTypeMetas.get(i));
				af.setProtocol(protocol);
				ResourceTypeMeta resourceType = new ResourceTypeMeta();
				resourceType.setId(resourceTypeMetas.get(i));
				resourceType.setCode("code" + resourceType.getId());
				resourceType.setParentId("parentId" + i);
				af.setResourceType(resourceType);
				resourceType.setResouceClassName(af.getClass().getName());
			} else {
				// 设备
				BaseDeviceInfo badf = new BaseDeviceInfo();
				badf.setId(String.valueOf(uuid.randomUUID()));
				badf.setDesc("描述" + i);
				badf.setCode(String.valueOf(uuid.randomUUID()));
				badf.setName("名称" + i);
				badf.setParentId("parentId" + parentId);
				badf.setAddress("地址" + i);
				badf.setChannel("通道" + i);
				badf.setRemark("备注" + i);
				badf.setVender("设备厂商" + i);
				badf.setVersion("设备型号" + i);
				ProtocolTypeMeta protocol = new ProtocolTypeMeta();
				protocol.setId(protocolTypeMetas.get(i));
				badf.setProtocol(protocol);
				ResourceTypeMeta resourceType = new ResourceTypeMeta();
				resourceType.setId(resourceTypeMetas.get(i));
				resourceType.setResouceClassName(af.getClass().getName());
				badf.setResourceType(resourceType);
				DeviceTypeMeta deviceTypeMeta = new DeviceTypeMeta();
				deviceTypeMeta.setId(deviceTypeMetas.get(i));
				deviceTypeMeta.setCode("code" + deviceTypeMeta.getId());
				deviceTypeMeta.setDesc("描述" + i);
				deviceTypeMeta.setName("名称" + i);
				deviceTypeMeta.setParentId("parentId" + i);
				badf.setType(deviceTypeMeta);
				af = badf;
			}

			// 制定设备类型
			// DvrDeviceInfo ddif = new DvrDeviceInfo();
			// ddif.setAccountInfo(accountInfo);

			list.add(af);
		}
		list = Collections.synchronizedList(list);
		System.out.println("prepare to complete, start test......");
	}

	/**
	 * 添加资源
	 * @Title: save
	 * @param resource
	 * @return boolean
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	@Test(groups = { "add1" }, enabled = true)
	public void save() {
		assertTrue(resourceServiceImpl.save(list.get(0)));
		list.remove(list.get(0));
	}

	/**
	 * 添加同级资源，父资源必须存在，且在resouce对象携带对应父资源ID
	 * @Title: save
	 * @param resources
	 * @return boolean
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	@Test(groups = { "add2" }, dependsOnGroups = { "add1" }, enabled = true)
	public void save1() {

		assertTrue(resourceServiceImpl.save(list));
	}

	/**
	 * 更新资源
	 * @Title: update
	 * @param resource
	 * @return boolean
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	@Test(groups = { "update" }, dependsOnGroups = { "add2" }, enabled = true)
	public void update() {
		list.get(0).setName("更新资源");
		// 单个更新
		assertTrue(resourceServiceImpl.update(list.get(0)));

	}

	/**
	 * 更新资源状态
	 * @Title: update
	 * @param status
	 * @return boolean
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	@Test(groups = { "update" }, dependsOnGroups = { "add2" }, enabled = true)
	public void update1() {

		assertTrue(resourceServiceImpl.update(list.get(0).getId(), 1));
	}

	/**
	 * 更新同级资源，父资源必须存在，且在resouce对象携带对应父资源ID
	 * @Title: update
	 * @param resources
	 * @return boolean
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	@Test(groups = { "update" }, dependsOnGroups = { "add2" }, enabled = true)
	public void update2() {
		// 批量更新
		for (int i = 0; i < list.size(); i++) {
			list.get(0).setDesc("批量更新后的描述");
		}
		assertTrue(resourceServiceImpl.update(list));
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
	@Test(groups = { "delete" }, dependsOnGroups = { "query" }, enabled = true)
	public void delete() {
		assertTrue(resourceServiceImpl.delete(list.get(0).getId()));
	}

	/**
	 * 根据ID获取一个资源对象，可以是一个Resouce或者其子类
	 * @Title: get
	 * @param id
	 * @return T
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	@Test(groups = { "query" }, dependsOnGroups = { "update" }, enabled = true)
	public void get() {
		assertNotNull(resourceServiceImpl.get(list.get(0).getId()));
		assertNotNull(resourceServiceImpl.getTerminalByDeviceCode(list.get(0).getCode()));
		assertNotNull(resourceServiceImpl.getTerminalByDeviceId(list.get(0).getId()));
	}

	/**
	 *根据code获取一个资源对象，可以是一个Resouce或者其子类
	 * @Title: getByCode
	 * @param code
	 * @return T
	 * @see 
	 * @throws 
	 * @author lishibang
	 */

	@Test(groups = { "query" }, dependsOnGroups = { "update" }, enabled = true)
	public void getByCode() {
		assertNotNull(resourceServiceImpl.getByCode(list.get(0).getCode()));

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
	@Test(groups = { "query" }, dependsOnGroups = { "update" }, enabled = true)
	public void find() {
		// 根据父id 查询子资源
		resourceServiceImpl.find(list.get(0).getId(), -1, -1);
		resourceServiceImpl.find(list.get(0).getId(), 0, 5);
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
	@Test(groups = { "query" }, dependsOnGroups = { "update" }, enabled = true)
	public void count() {

		resourceServiceImpl.count(list.get(0).getId());
	}

}
