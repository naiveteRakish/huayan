/**  
 * 元数据管理测试
 * @Title: WhayerMetaDataManagerImplTest.java
 * @Package com.whayer.cloud.storage.component.foundation.meta.impl
 * @author Administrator
 * @date 2017年8月2日 下午5:36:21
 * @version v1.0.0
 */
package com.whayer.cloud.storage.component.foundation.meta.impl;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertNull;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.whayer.cloud.storage.business.alarm.data.AlarmMessageLevelMeta;
import com.whayer.cloud.storage.business.alarm.data.AlarmMessageTypeMeta;
import com.whayer.cloud.storage.business.base.data.QueryCondition;
import com.whayer.cloud.storage.business.base.data.QueryOperator;
import com.whayer.cloud.storage.business.base.data.msg.MessageSourceIficationTypeMeta;
import com.whayer.cloud.storage.business.base.data.msg.MessageSourceTypeMeta;
import com.whayer.cloud.storage.business.base.data.type.Type;

/**
 * 元数据管理测试
 * @ClassName: WhayerMetaDataManagerImplTest
 * @author Administrator
 * @date 2017年8月2日 下午5:36:21
 * @version v1.0.0
 * 
 */
@Test
@ContextConfiguration(locations = { "classpath:beans.xml" })
public class WhayerMetaDataManagerImplTest {

	private MetaDataServiceImpl metaDataManager;

	private List<Type> list;

	@AfterTest
	public void afterTese() {
		try {
			// 需要多线程debug 就解开注释
			Thread.sleep(Long.MAX_VALUE);
			metaDataManager.stop();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@SuppressWarnings({ "static-access", "resource" })
	@BeforeTest
	public void beforeTest() {
		// 数据操作组件环境初始化
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
		metaDataManager = (MetaDataServiceImpl) context.getBean("metaDataManagerImpl");
		metaDataManager.start();

		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		// 模拟前端系统创建多个对象
		list = new ArrayList<Type>();
		UUID uuid = UUID.randomUUID();
		for (int i = 0; i < 20; i++) {
			Type aa = null;
			if (i % 2 == 0) {
				AlarmMessageLevelMeta af = new AlarmMessageLevelMeta();
				af.setId(String.valueOf(uuid.randomUUID()));
				af.setDesc("描述" + i);
				af.setCode("类型编码" + i);
				af.setName("名称" + i);
				aa = af;
				list.add(aa);
			}
			if (i % 3 == 0) {
				AlarmMessageTypeMeta af = new AlarmMessageTypeMeta();
				af.setId(String.valueOf(uuid.randomUUID()));
				af.setDesc("描述" + i);
				af.setCode("类型编码" + i);
				af.setName("名称" + i);
				af.setParentId("parentId" + i);
				aa = af;
				list.add(aa);
			}
			if (i % 4 == 0) {
				MessageSourceIficationTypeMeta af = new MessageSourceIficationTypeMeta();
				af.setId(String.valueOf(uuid.randomUUID()));
				af.setDesc("描述" + i);
				af.setCode("类型编码" + i);
				af.setName("名称" + i);
				af.setParentId("parentId" + i);
				aa = af;
				list.add(aa);
			}
			if (i % 5 == 0) {
				MessageSourceTypeMeta af = new MessageSourceTypeMeta();
				af.setId(String.valueOf(uuid.randomUUID()));
				af.setDesc("描述" + i);
				af.setCode("类型编码" + i);
				af.setName("名称" + i);
				af.setParentId("parentId" + i);
				aa = af;
				list.add(aa);
			}
		}
		System.out.println("prepare to complete, start test......");
	}

	// 单个保存测试
	@Test(groups = { "add" }, enabled = true)
	public void savleAlarmMessageLevelMetaTest() {

		boolean su = metaDataManager.addMetaData(list.get(0));
		assertTrue(su, "单个保存测试失败!");
	}

	// 多个保存测试
	@Test(groups = { "add" }, enabled = true)
	public void savleAlarmMessageLevelMetaListTest() {
		assertTrue(metaDataManager.addMetaDatas(list), "批量保存失败");
	}

	// 四种类型 单个更新测试
	@Test(dependsOnGroups = { "add" }, groups = { "update" }, enabled = true)
	public void updateListTest() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		String nameValueString = "更新后的名字";
		List<QueryCondition> list = new ArrayList<QueryCondition>();
		list.add(new QueryCondition("name", new QueryOperator(QueryOperator.Operator.EQUAL), "名称5"));
		List<MessageSourceTypeMeta> messageSourceTypeMeta = metaDataManager.getMetaData(MessageSourceTypeMeta.class,
				list);
		messageSourceTypeMeta.get(0).setName(nameValueString);
		assertTrue(metaDataManager.modifyMetaData(messageSourceTypeMeta.get(0)));

		list.set(0, new QueryCondition("name", new QueryOperator(QueryOperator.Operator.EQUAL), "名称4"));
		List<MessageSourceIficationTypeMeta> messageSourceIficationTypeMeta = metaDataManager.getMetaData(
				MessageSourceIficationTypeMeta.class, list);
		messageSourceIficationTypeMeta.get(0).setName(nameValueString);
		assertTrue(metaDataManager.modifyMetaData(messageSourceIficationTypeMeta.get(0)));

		list.set(0, new QueryCondition("name", new QueryOperator(QueryOperator.Operator.EQUAL), "名称9"));
		List<AlarmMessageTypeMeta> alarmMessageTypeMeta = metaDataManager.getMetaData(AlarmMessageTypeMeta.class, list);
		alarmMessageTypeMeta.get(0).setName(nameValueString);
		assertTrue(metaDataManager.modifyMetaData(messageSourceIficationTypeMeta.get(0)));

		list.set(0, new QueryCondition("name", new QueryOperator(QueryOperator.Operator.EQUAL), "名称2"));
		List<AlarmMessageLevelMeta> alarmMessageLevelMeta = metaDataManager.getMetaData(AlarmMessageLevelMeta.class,
				list);
		alarmMessageLevelMeta.get(0).setName(nameValueString);
		assertTrue(metaDataManager.modifyMetaData(messageSourceIficationTypeMeta.get(0)));
	}

	// 条件查询测试
	@Test(dependsOnGroups = { "add", "update" }, threadPoolSize = 4, groups = "query", enabled = true)
	public void searchAlarmInfoListTest() {
		String nameValueString = "更新后的名字";
		List<QueryCondition> list = new ArrayList<>();
		list.add(new QueryCondition("name", new QueryOperator(QueryOperator.Operator.EQUAL), nameValueString));
		// 创建条件查询对象
		List<MessageSourceTypeMeta> alist = metaDataManager.getMetaData(MessageSourceTypeMeta.class, list);
		for (int i = 0; i < alist.size(); i++) {
			assertEquals(alist.get(i).getName(), nameValueString);
		}
		// 按照类型查询
		assertNotNull(metaDataManager.getMetaData(MessageSourceTypeMeta.class, (ArrayList<QueryCondition>) null),
				"按照类型查询失败!");

		List<MessageSourceIficationTypeMeta> messageSourceIficationTypeMetaList = metaDataManager.getMetaData(
				MessageSourceIficationTypeMeta.class, list);
		for (int i = 0; i < messageSourceIficationTypeMetaList.size(); i++) {
			assertEquals(messageSourceIficationTypeMetaList.get(i).getName(), nameValueString);
		}

		List<AlarmMessageTypeMeta> alarmMessageTypeMetaList = metaDataManager.getMetaData(AlarmMessageTypeMeta.class,
				list);
		for (int i = 0; i < alarmMessageTypeMetaList.size(); i++) {
			assertEquals(alarmMessageTypeMetaList.get(i).getName(), nameValueString);
		}

		List<AlarmMessageLevelMeta> alarmMessageLevelMetaList = metaDataManager.getMetaData(
				AlarmMessageLevelMeta.class, list);
		for (int i = 0; i < alarmMessageLevelMetaList.size(); i++) {
			assertEquals(alarmMessageLevelMetaList.get(i).getName(), nameValueString);
		}

	}

	// 单个元数据删除测试
	@Test(dependsOnGroups = { "update" }, groups = "singleDelete", enabled = true)
	public void deleteAlarmInfoListTest() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		int j = 0;
		boolean MessageSourceTypeMeta = true;
		boolean MessageSourceIficationTypeMeta = true;
		boolean AlarmMessageTypeMeta = true;
		boolean AlarmMessageLevelMeta = true;
		for (int i = 0; i < list.size(); i++) {
			if (list.get(i) instanceof MessageSourceTypeMeta && MessageSourceTypeMeta) {
				metaDataManager.deleteMetaData(MessageSourceTypeMeta.class, list.get(i).getId());
				assertNull(metaDataManager.getMetaData(MessageSourceTypeMeta.class, list.get(i).getId()));
				j++;
				MessageSourceTypeMeta = false;
			}
			if (list.get(i) instanceof MessageSourceIficationTypeMeta && MessageSourceIficationTypeMeta) {
				metaDataManager.deleteMetaData(MessageSourceIficationTypeMeta.class, list.get(i).getId());
				assertNull(metaDataManager.getMetaData(MessageSourceIficationTypeMeta.class, list.get(i).getId()));
				j++;
				MessageSourceIficationTypeMeta = false;
			}
			if (list.get(i) instanceof AlarmMessageTypeMeta && AlarmMessageTypeMeta) {
				metaDataManager.deleteMetaData(AlarmMessageTypeMeta.class, list.get(i).getId());
				assertNull(metaDataManager.getMetaData(AlarmMessageTypeMeta.class, list.get(i).getId()));
				j++;
				AlarmMessageTypeMeta = false;
			}
			if (list.get(i) instanceof AlarmMessageLevelMeta && AlarmMessageLevelMeta) {
				metaDataManager.deleteMetaData(AlarmMessageLevelMeta.class, list.get(i).getId());
				assertNull(metaDataManager.getMetaData(AlarmMessageLevelMeta.class, list.get(i).getId()));
				j++;
				AlarmMessageLevelMeta = false;
			}
			if (j == 4) {
				break;
			}

		}

	}

	// 按照元数据类型删除测试
	@Test(dependsOnGroups = { "update", "query", "singleDelete" }, enabled = false)
	public void deleteAlarmInfoTest() {
		try {
			Thread.sleep(5000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		assertTrue(metaDataManager.deleteMetaData(MessageSourceTypeMeta.class), "按照MessageSourceTypeMeta类型删除失败!");

		assertTrue(metaDataManager.deleteMetaData(MessageSourceIficationTypeMeta.class),
				"按照MessageSourceIficationTypeMeta类型删除失败!");

		assertTrue(metaDataManager.deleteMetaData(AlarmMessageTypeMeta.class), "按照AlarmMessageTypeMeta类型删除失败!");

		assertTrue(metaDataManager.deleteMetaData(AlarmMessageLevelMeta.class), "按照AlarmMessageLevelMeta类型删除失败!");

	}

}
