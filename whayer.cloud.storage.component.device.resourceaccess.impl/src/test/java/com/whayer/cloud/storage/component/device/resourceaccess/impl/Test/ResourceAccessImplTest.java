/**  
 * 用一句话描述该文件做什么
 * @Title: DeviceAccessImplTest.java
 * @Package whayer.cloud.storage.component.device.deviceaccess.impl
 * @author Administrator
 * @date 2017年8月10日 上午11:29:49
 * @version v1.0.0
 */
package com.whayer.cloud.storage.component.device.resourceaccess.impl.Test;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.whayer.cloud.storage.business.base.data.QueryCondition;
import com.whayer.cloud.storage.business.base.data.QueryOperator;
import com.whayer.cloud.storage.component.device.resourceaccess.IResourceAccess;
import com.whayer.cloud.storage.component.device.resourceaccess.beans.ResourcePojo;

@Test
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class ResourceAccessImplTest extends AbstractTestNGSpringContextTests {
	// @Autowired
	private IResourceAccess resourceAccess;
	private List<ResourcePojo> list;

	@SuppressWarnings({ "static-access" })
	@BeforeTest
	public void beforeTest() {
		// 数据操作组件环境初始化
		@SuppressWarnings("resource")
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		resourceAccess = (IResourceAccess) context.getBean(IResourceAccess.class);

		// 模拟前端系统创建多个对象
		list = new ArrayList<ResourcePojo>();
		UUID uuid = UUID.randomUUID();
		for (int i = 0; i < 20; i++) {
			ResourcePojo af = new ResourcePojo();
			af.setId(String.valueOf(uuid.randomUUID()));
			af.setDesc("描述" + i);
			af.setCode("类型编码" + i);
			af.setName("名称" + i);
			af.setParentId("parentId" + i);
			af.setProtocolId("protocolId" + i);
			af.setResourceTypeId("resourceTypeId" + i);
			af.setTypeId("typeId" + i);
			list.add(af);
		}
		System.out.println("prepare to complete, start test......");
		list = Collections.synchronizedList(list);
	}

	// 单个保存测试
	@Test(groups = { "add" })
	public void savleAlarmMessageLevelMetaTest() {

		assertTrue(resourceAccess.addResource(list.get(0)), "单个保存失败!");
		list.remove(0);

	}

	// 多个保存测试
	@Test(groups = { "add1" }, dependsOnGroups = { "add" }, enabled = true)
	public void savleAlarmMessageLevelMetaListTest() {
		assertTrue(resourceAccess.addResources(list), "批量保存失败");
	}

	// 更新测试
	@Test(dependsOnGroups = { "add" }, groups = "update", enabled = true)
	public void updateListTest() {

		list.get(0).setName("修改后的名字");
		assertTrue(resourceAccess.modifyResource(list.get(0)));

	}

	// 条件查询测试
	@Test(dependsOnGroups = { "add", "update" }, groups = "query", enabled = true)
	public void searchAlarmInfoListTest() {
		List<QueryCondition> list = new ArrayList<>();
		list.add(new QueryCondition("name", new QueryOperator(QueryOperator.Operator.EQUAL), "修改后的名字"));
		assertEquals(resourceAccess.queryResource(0, 1, list).get(0).getName(), "修改后的名字");
		assertNotNull(resourceAccess.queryResource(-1, -1, list));
		assertNotNull(resourceAccess.queryResourceSize(null));
		assertNotNull(resourceAccess.queryResourceSize(list));
	}

	// 单个元数据删除测试
	@Test(dependsOnGroups = { "update", "query" }, groups = "singleDelete", enabled = true)
	public void deleteAlarmInfoListTest() {
		List<QueryCondition> list = new ArrayList<>();
		list.add(new QueryCondition("name", new QueryOperator(QueryOperator.Operator.EQUAL), "修改后的名字"));
		assertTrue(resourceAccess.deleteResource(list));

	}

	// 删除全部数据
	@Test(dependsOnGroups = { "update", "query", "singleDelete" }, enabled = false)
	public void deleteAlarmInfoTest() {
		assertTrue(resourceAccess.deleteResource(null));
	}

}
