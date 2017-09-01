/**  
 * 用一句话描述该文件做什么
 * @Title: DeviceAccessImplTest.java
 * @Package whayer.cloud.storage.component.device.deviceaccess.impl
 * @author Administrator
 * @date 2017年8月10日 上午11:29:49
 * @version v1.0.0
 */
package whayer.cloud.storage.component.device.deviceaccess.impl;

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
import com.whayer.cloud.storage.component.device.deviceaccess.IDeviceAccess;
import com.whayer.cloud.storage.component.device.deviceaccess.beans.BaseDeviceInfoPojo;

@Test
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class DeviceAccessImplTest extends AbstractTestNGSpringContextTests {
	// @Autowired
	private IDeviceAccess deviceAccessImpl;
	private List<BaseDeviceInfoPojo> list;

	@SuppressWarnings({ "static-access" })
	@BeforeTest
	public void beforeTest() {
		// 数据操作组件环境初始化
		@SuppressWarnings("resource")
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		deviceAccessImpl = (IDeviceAccess) context.getBean("deviceAccessImpl");

		// 模拟前端系统创建多个对象
		list = new ArrayList<BaseDeviceInfoPojo>();
		UUID uuid = UUID.randomUUID();
		for (int i = 0; i < 20; i++) {
			BaseDeviceInfoPojo af = new BaseDeviceInfoPojo();
			af.setId(String.valueOf(uuid.randomUUID()));
			af.setDesc("描述" + i);
			af.setCode("类型编码" + i);
			af.setName("名称" + i);
			af.setAddress("地址" + i);
			af.setChannel("通道" + i);
			af.setRemark("备注" + i);
			af.setParentId("父id" + i);
			af.setVender("设备厂商" + i);
			af.setVersion("设备型号" + i);
			af.setProtocolId("protocolId" + i);
			af.setResourceTypeId("resourceTypeId" + i);
			af.setTypeId("typeId" + i);
			list.add(af);
		}
		list = Collections.synchronizedList(list);
		System.out.println("prepare to complete, start test......");
	}

	// 单个保存测试
	@Test(groups = { "add" })
	public void savleAlarmMessageLevelMetaTest() {

		assertTrue(deviceAccessImpl.addDeviceInfo(list.get(0)), "单个保存失败!");
		list.remove(0);

	}

	// 多个保存测试
	@Test(groups = { "add1" }, dependsOnGroups = { "add" }, enabled = true)
	public void savleAlarmMessageLevelMetaListTest() {
		assertTrue(deviceAccessImpl.addDeviceInfos(list), "批量保存失败");
	}

	// 更新测试
	@Test(dependsOnGroups = { "add" }, groups = "update", enabled = true)
	public void updateListTest() {

		list.get(0).setAddress("修改后的地址");
		assertTrue(deviceAccessImpl.modifyDeviceInfo(list.get(0)));

	}

	// 条件查询测试
	@Test(dependsOnGroups = { "add", "update" }, groups = "query", enabled = true)
	public void searchAlarmInfoListTest() {
		List<QueryCondition> list = new ArrayList<>();
		list.add(new QueryCondition("address", new QueryOperator(QueryOperator.Operator.EQUAL), "修改后的地址"));
		assertEquals(deviceAccessImpl.queryDeviceInfo(0, 1, list).get(0).getAddress(), "修改后的地址");
		assertNotNull(deviceAccessImpl.queryDeviceInfo(-1, -1, list));
		assertNotNull(deviceAccessImpl.queryDeviceInfoSize(null));
	}

	// 单个元数据删除测试
	@Test(dependsOnGroups = { "update", "query" }, groups = "singleDelete", enabled = true)
	public void deleteAlarmInfoListTest() {
		List<QueryCondition> list = new ArrayList<>();
		list.add(new QueryCondition("address", new QueryOperator(QueryOperator.Operator.EQUAL), "修改后的地址"));
		assertTrue(deviceAccessImpl.deleteDeviceInfo(list));

	}

	// 删除全部数据
	@Test(dependsOnGroups = { "update", "query", "singleDelete" }, enabled = false)
	public void deleteAlarmInfoTest() {
		assertTrue(deviceAccessImpl.deleteDeviceInfo(null));
	}

	// /**
	// * 封装查询结果
	// * @param baseDeviceInfoPojos
	// * @param dao
	// * @Title: packagingBaseDeviceInfoList void
	// * @see
	// * @throws
	// * @author Administrator
	// */
	// private List<BaseDeviceInfo>
	// queryBaseDeviceInfoChild(List<BaseDeviceInfoPojo> baseDeviceInfoPojos,
	// IDao dao,
	// List<BaseDeviceInfo> result) {
	// BaseDeviceInfo baseDeviceInfo = null;
	// String id = null;
	// for (int i = 0; i < baseDeviceInfoPojos.size(); i++) {
	// BaseDeviceInfoPojo bdip = baseDeviceInfoPojos.get(i);
	//
	// baseDeviceInfo = packagingBaseDeviceInfo(bdip);
	// id = bdip.getResourceTypeId();
	// MessageSourceTypeMetaPojo messageSourceTypeMetaPojo = null;
	// if (id != null) {
	// messageSourceTypeMetaPojo =
	// dao.selectOne(MessageSourceTypeMetaPojo.class, id);
	// baseDeviceInfo.setResourceType(packagingMessageSourceTypeMeta(messageSourceTypeMetaPojo));
	// }
	// id = bdip.getProtocolId();
	// if (id != null) {
	// ProtocolTypeMetaPojo protocolTypeMetaPojo =
	// dao.selectOne(ProtocolTypeMetaPojo.class, id);
	// baseDeviceInfo.setProtocol(packagingProtocolTypeMeta(protocolTypeMetaPojo));
	// }
	// id = bdip.getTypeId();
	// if (id != null && messageSourceTypeMetaPojo != null) {
	// if (baseDeviceInfo.getName() == "设备") {
	// DeviceTypeMetaPojo deviceTypeMetaPojo =
	// dao.selectOne(DeviceTypeMetaPojo.class, id);
	// baseDeviceInfo.setType(packagingDeviceTypeMeta(deviceTypeMetaPojo));
	// } else if (baseDeviceInfo.getName() == "普通") {
	// // TODO
	// }
	// }
	// result.add(baseDeviceInfo);
	// }
	// return result;
	// }
	//
	// /**
	// * 转换对象
	// * @Title: packagingDeviceTypeMeta
	// * @param deviceTypeMetaPojo
	// * @return Type
	// * @see
	// * @throws
	// * @author Administrator
	// */
	// private DeviceTypeMeta packagingDeviceTypeMeta(DeviceTypeMetaPojo
	// deviceTypeMetaPojo) {
	// if (deviceTypeMetaPojo == null)
	// return null;
	// DeviceTypeMeta deviceTypeMeta = new DeviceTypeMeta();
	// deviceTypeMeta.setCode(deviceTypeMetaPojo.getCode());
	// deviceTypeMeta.setDesc(deviceTypeMetaPojo.getDesc());
	// deviceTypeMeta.setId(deviceTypeMetaPojo.getId());
	// deviceTypeMeta.setName(deviceTypeMetaPojo.getName());
	// deviceTypeMeta.setParentId(deviceTypeMetaPojo.getParentId());
	// return deviceTypeMeta;
	// }
	//
	// /**
	// * 转换对象
	// * @Title: packagingProtocolTypeMeta
	// * @param protocolTypeMetaPojo
	// * @return ProtocolTypeMeta
	// * @see
	// * @throws
	// * @author Administrator
	// */
	// private ProtocolTypeMeta packagingProtocolTypeMeta(ProtocolTypeMetaPojo
	// protocolTypeMetaPojo) {
	// if (protocolTypeMetaPojo == null)
	// return null;
	// ProtocolTypeMeta protocolTypeMeta = new ProtocolTypeMeta();
	// protocolTypeMeta.setCode(protocolTypeMetaPojo.getCode());
	// protocolTypeMeta.setDesc(protocolTypeMetaPojo.getDesc());
	// protocolTypeMeta.setId(protocolTypeMetaPojo.getId());
	// protocolTypeMeta.setName(protocolTypeMetaPojo.getName());
	// return protocolTypeMeta;
	// }
	//
	// /**
	// * 转换对象
	// * @Title: packagingBaseDeviceInfo
	// * @param baseDeviceInfoPojo
	// * @return BaseDeviceInfo
	// * @see
	// * @throws
	// * @author Administrator
	// */
	// private BaseDeviceInfo packagingBaseDeviceInfo(BaseDeviceInfoPojo
	// baseDeviceInfoPojo) {
	// if (baseDeviceInfoPojo == null)
	// return null;
	// BaseDeviceInfo baseDeviceInfo = new BaseDeviceInfo();
	// baseDeviceInfo.setAddress(baseDeviceInfoPojo.getAddress());
	// baseDeviceInfo.setChannel(baseDeviceInfoPojo.getChannel());
	// baseDeviceInfo.setCode(baseDeviceInfoPojo.getCode());
	// baseDeviceInfo.setDesc(baseDeviceInfoPojo.getDesc());
	// baseDeviceInfo.setId(baseDeviceInfoPojo.getId());
	// baseDeviceInfo.setName(baseDeviceInfoPojo.getName());
	// baseDeviceInfo.setParentId(baseDeviceInfoPojo.getParentId());
	// baseDeviceInfo.setRemark(baseDeviceInfoPojo.getRemark());
	// baseDeviceInfo.setStatus(baseDeviceInfoPojo.getStatus());
	// baseDeviceInfo.setVender(baseDeviceInfoPojo.getVender());
	// baseDeviceInfo.setVersion(baseDeviceInfoPojo.getVersion());
	// return baseDeviceInfo;
	// }
	//
	// /**
	// * 转换对象
	// * @Title: packagingMessageSourceTypeMeta
	// * @param messageSourceTypeMetaPojo
	// * @return MessageSourceTypeMeta
	// * @see
	// * @throws
	// * @author Administrator
	// */
	// private MessageSourceTypeMeta
	// packagingMessageSourceTypeMeta(MessageSourceTypeMetaPojo
	// messageSourceTypeMetaPojo) {
	// if (messageSourceTypeMetaPojo == null)
	// return null;
	// MessageSourceTypeMeta messageSourceTypeMeta = new
	// MessageSourceTypeMeta();
	// messageSourceTypeMeta.setCode(messageSourceTypeMetaPojo.getCode());
	// messageSourceTypeMeta.setDesc(messageSourceTypeMetaPojo.getDesc());
	// messageSourceTypeMeta.setId(messageSourceTypeMetaPojo.getId());
	// messageSourceTypeMeta.setName(messageSourceTypeMetaPojo.getName());
	// messageSourceTypeMeta.setParentId(messageSourceTypeMetaPojo.getParentId());
	// messageSourceTypeMeta.setResouceClassName(messageSourceTypeMetaPojo.getResouceClassName());
	// return messageSourceTypeMeta;
	// }
	//
	// /**
	// * 转换对象
	// * @Title: packagingObj
	// * @param deviceInfo
	// * @return BaseDeviceInfoPojo
	// * @see
	// * @throws
	// * @author Administrator
	// */
	// private BaseDeviceInfoPojo packagingPojo(BaseDeviceInfo deviceInfo) {
	// BaseDeviceInfoPojo baseDeviceInfoPojo = new BaseDeviceInfoPojo();
	// baseDeviceInfoPojo.setAddress(deviceInfo.getAddress());
	// baseDeviceInfoPojo.setChannel(deviceInfo.getChannel());
	// baseDeviceInfoPojo.setCode(deviceInfo.getCode());
	// baseDeviceInfoPojo.setDesc(deviceInfo.getDesc());
	// baseDeviceInfoPojo.setId(deviceInfo.getId());
	// baseDeviceInfoPojo.setName(deviceInfo.getName());
	// baseDeviceInfoPojo.setParentId(deviceInfo.getParentId());
	// baseDeviceInfoPojo.setProtocolId(deviceInfo.getProtocol().getId());
	// baseDeviceInfoPojo.setRemark(deviceInfo.getRemark());
	// baseDeviceInfoPojo.setResourceTypeId(deviceInfo.getResourceType().getId());
	// baseDeviceInfoPojo.setStatus(deviceInfo.getStatus());
	// baseDeviceInfoPojo.setTypeId(deviceInfo.getType().getId());
	// baseDeviceInfoPojo.setVender(deviceInfo.getVender());
	// baseDeviceInfoPojo.setVersion(deviceInfo.getVersion());
	// return baseDeviceInfoPojo;
	// }

}
