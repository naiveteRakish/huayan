/**  
 * 用一句话描述该文件做什么
 * @Title: DeviceAccessImplTest.java
 * @Package whayer.cloud.storage.component.device.deviceaccess.impl
 * @author Administrator
 * @date 2017年8月10日 上午11:29:49
 * @version v1.0.0
 */
package whayer.cloud.storage.component.foundation.access.impl.test;

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
import com.whayer.cloud.storage.component.foundation.access.IAccessService;
import com.whayer.cloud.storage.component.foundation.access.beans.BaseDeviceInfoPojo;
import com.whayer.cloud.storage.component.foundation.access.beans.DvrDeviceInfoPojo;
import com.whayer.cloud.storage.component.foundation.access.beans.ResourcePojo;
import com.whayer.cloud.storage.component.foundation.access.beans.TableRowPojo;

@Test
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class AccessImplTest extends AbstractTestNGSpringContextTests {
	private IAccessService accessService;
	private List<ResourcePojo> resourcePojos;
	private List<BaseDeviceInfoPojo> baseDeviceInfoPojo;
	private List<DvrDeviceInfoPojo> dvrDeviceInfoPojo;

	@SuppressWarnings({ "static-access" })
	@BeforeTest
	public void beforeTest() {
		// 数据操作组件环境初始化
		@SuppressWarnings("resource")
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		accessService = (IAccessService) context.getBean(IAccessService.class);

		// 模拟前端系统创建多个对象
		resourcePojos = new ArrayList<ResourcePojo>();
		baseDeviceInfoPojo = new ArrayList<BaseDeviceInfoPojo>();
		dvrDeviceInfoPojo = new ArrayList<DvrDeviceInfoPojo>();
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
			af.setStatus(1);
			af.setTypeId("typeId" + i);
			BaseDeviceInfoPojo baseDeviceInfoPojo = new BaseDeviceInfoPojo();
			baseDeviceInfoPojo.setId(String.valueOf(uuid.randomUUID()));
			baseDeviceInfoPojo.setDesc("描述" + i);
			baseDeviceInfoPojo.setCode("类型编码" + i);
			baseDeviceInfoPojo.setName("名称" + i);
			baseDeviceInfoPojo.setAddress("地址" + i);
			baseDeviceInfoPojo.setChannel("通道" + i);
			baseDeviceInfoPojo.setRemark("备注" + i);
			baseDeviceInfoPojo.setParentId("父id" + i);
			baseDeviceInfoPojo.setVender("设备厂商" + i);
			baseDeviceInfoPojo.setVersion("设备型号" + i);
			baseDeviceInfoPojo.setProtocolId("protocolId" + i);
			baseDeviceInfoPojo.setResourceTypeId("resourceTypeId" + i);
			baseDeviceInfoPojo.setTypeId("typeId" + i);
			baseDeviceInfoPojo.setStatus(2);
			DvrDeviceInfoPojo dverDeviceInfoPojo = new DvrDeviceInfoPojo();
			dverDeviceInfoPojo.setId(String.valueOf(uuid.randomUUID()));
			dverDeviceInfoPojo.setDesc("描述" + i);
			dverDeviceInfoPojo.setCode("类型编码" + i);
			dverDeviceInfoPojo.setName("名称" + i);
			dverDeviceInfoPojo.setAddress("地址" + i);
			dverDeviceInfoPojo.setChannel("通道" + i);
			dverDeviceInfoPojo.setRemark("备注" + i);
			dverDeviceInfoPojo.setParentId("父id" + i);
			dverDeviceInfoPojo.setVender("设备厂商" + i);
			dverDeviceInfoPojo.setVersion("设备型号" + i);
			dverDeviceInfoPojo.setProtocolId("protocolId" + i);
			dverDeviceInfoPojo.setResourceTypeId("resourceTypeId" + i);
			dverDeviceInfoPojo.setTypeId("typeId" + i);
			dverDeviceInfoPojo.setStatus(3);
			this.resourcePojos.add(af);
			this.baseDeviceInfoPojo.add(baseDeviceInfoPojo);
			this.dvrDeviceInfoPojo.add(dverDeviceInfoPojo);
		}
		System.out.println("prepare to complete, start test......");
		resourcePojos = Collections.synchronizedList(resourcePojos);
		baseDeviceInfoPojo = Collections.synchronizedList(baseDeviceInfoPojo);
		dvrDeviceInfoPojo = Collections.synchronizedList(dvrDeviceInfoPojo);
	}

	// 单个保存测试
	@Test(groups = { "add" }, invocationCount = 2)
	public void savleAlarmMessageLevelMetaTest() {

		assertTrue(accessService.add(resourcePojos.get(0)), "单个保存失败!");
		resourcePojos.remove(0);
		assertTrue(accessService.add(baseDeviceInfoPojo.get(0)), "单个保存失败!");
		baseDeviceInfoPojo.remove(0);
		assertTrue(accessService.add(dvrDeviceInfoPojo.get(0)), "单个保存失败!");
		dvrDeviceInfoPojo.remove(0);

	}

	// 多个保存测试
	@Test(groups = { "add1" }, dependsOnGroups = { "add" }, enabled = true)
	public void savleAlarmMessageLevelMetaListTest() {
		assertTrue(accessService.addAll(resourcePojos), "批量保存失败");
		assertTrue(accessService.addAll(baseDeviceInfoPojo), "批量保存失败");
		assertTrue(accessService.addAll(dvrDeviceInfoPojo), "批量保存失败");
	}

	// 更新测试
	@Test(dependsOnGroups = { "add" }, groups = "update", enabled = true)
	public void updateListTest() {

		resourcePojos.get(0).setName("修改后的名字");
		assertTrue(accessService.update(resourcePojos.get(0)));
		baseDeviceInfoPojo.get(0).setName("修改后的名字");
		assertTrue(accessService.update(baseDeviceInfoPojo.get(0)));
		dvrDeviceInfoPojo.get(0).setName("修改后的名字");
		assertTrue(accessService.update(dvrDeviceInfoPojo.get(0)));

	}

	// 条件查询测试
	@Test(dependsOnGroups = { "add", "update" }, groups = "query", enabled = true, threadPoolSize = 2, invocationCount = 2)
	public void searchAlarmInfoListTest() {

		assertNotNull(accessService.get(ResourcePojo.class, resourcePojos.get(0).getId()));
		List<QueryCondition> list = new ArrayList<>();
		list.add(new QueryCondition("name", new QueryOperator(QueryOperator.Operator.EQUAL), "修改后的名字"));
		assertEquals(((ResourcePojo) accessService.query(ResourcePojo.class, 0, 1, list).get(0)).getName(), "修改后的名字");
		assertNotNull(accessService.query(ResourcePojo.class, -1, -1, list));
		assertNotNull(accessService.count(ResourcePojo.class, null));
		assertNotNull(accessService.count(ResourcePojo.class, list));

		assertEquals(((ResourcePojo) accessService.query(ResourcePojo.class, 0, 1, list).get(0)).getName(), "修改后的名字");
		assertNotNull(accessService.query(ResourcePojo.class, -1, -1, list));
		assertNotNull(accessService.count(ResourcePojo.class, null));
		assertNotNull(accessService.count(ResourcePojo.class, list));

		assertEquals(((ResourcePojo) accessService.query(ResourcePojo.class, 0, 1, list).get(0)).getName(), "修改后的名字");
		assertNotNull(accessService.query(ResourcePojo.class, -1, -1, list));
		assertNotNull(accessService.count(ResourcePojo.class, null));
		assertNotNull(accessService.count(ResourcePojo.class, list));
	}

	// 单个元数据删除测试
	@Test(dependsOnGroups = { "update", "query" }, groups = "singleDelete", enabled = true)
	public void deleteAlarmInfoListTest() {
		List<QueryCondition> list = new ArrayList<>();
		list.add(new QueryCondition("name", new QueryOperator(QueryOperator.Operator.EQUAL), "修改后的名字"));

		// TODO 删除的sql 语法有问题 过会问问老赵
		assertTrue(accessService.delete(TableRowPojo.class, list));

	}

	// 删除全部数据
	@Test(dependsOnGroups = { "update", "query", "singleDelete" }, enabled = false)
	public void deleteAlarmInfoTest() {
		assertTrue(accessService.delete(ResourcePojo.class, null));
		assertTrue(accessService.delete(BaseDeviceInfoPojo.class, null));
		assertTrue(accessService.delete(DvrDeviceInfoPojo.class, null));
	}

}
