/**  
 * @Title: FileMarkAccessTest.java
 * @Package whayer.cloud.storage.component.foundation.filemarkaccess.test
 * @author Administrator
 * @date 2017年8月25日 下午4:17:28
 * @version v1.0.0
 */
package whayer.cloud.storage.component.foundation.filemarkaccess.test;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.whayer.cloud.storage.business.base.data.QueryComplexCondition;
import com.whayer.cloud.storage.business.base.data.QueryComplexCondition.RelationOperator;
import com.whayer.cloud.storage.business.base.data.QueryOperator;
import com.whayer.cloud.storage.foundation.filemarkaccess.IFileMarkAccess;
import com.whayer.cloud.storage.foundation.filemarkaccess.beans.BaseFileInfoPojo;
import com.whayer.cloud.storage.foundation.filemarkaccess.beans.FileMarkInfoPojo;
import com.whayer.cloud.storage.foundation.filemarkaccess.beans.ImageFileInfoPojo;
import com.whayer.cloud.storage.foundation.filemarkaccess.beans.RecordFileInfoPojo;
import com.whayer.cloud.storage.foundation.filemarkaccess.beans.RecordFileMarkInfoPojo;

/**
 * @ClassName: FileMarkAccessTest
 * @author Administrator
 * @date 2017年8月25日 下午4:17:28
 * @version v1.0.0
 * 
 */
@Test
public class FileMarkAccessTest {

	private IFileMarkAccess fileMarkAccess;
	private List<BaseFileInfoPojo> recordFileInfoPojos;
	private List<BaseFileInfoPojo> imageFileInfoPojos;
	private List<FileMarkInfoPojo> fileMarkInfoPojos;

	@SuppressWarnings({ "static-access" })
	@BeforeTest
	public void beforeTest() {
		// 数据操作组件环境初始化
		@SuppressWarnings("resource")
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("classpath:application-context.xml");
		fileMarkAccess = (IFileMarkAccess) context.getBean(IFileMarkAccess.class);
		imageFileInfoPojos = new ArrayList<BaseFileInfoPojo>();
		recordFileInfoPojos = new ArrayList<BaseFileInfoPojo>();
		fileMarkInfoPojos = new ArrayList<FileMarkInfoPojo>();

		for (int i = 1; i < 5; i++) {
			ImageFileInfoPojo imageFileInfoPojo = new ImageFileInfoPojo();
			RecordFileInfoPojo recordFileInfoPojo = new RecordFileInfoPojo();
			RecordFileMarkInfoPojo recordFileMarkInfoPojo = new RecordFileMarkInfoPojo();
			recordFileInfoPojos.add(recordFileInfoPojo);
			imageFileInfoPojos.add(imageFileInfoPojo);
			fileMarkInfoPojos.add(recordFileMarkInfoPojo);
			imageFileInfoPojo.setId(UUID.randomUUID().toString());
			recordFileInfoPojo.setId(UUID.randomUUID().toString());
			recordFileMarkInfoPojo.setId(UUID.randomUUID().toString());

			imageFileInfoPojo.setHeight(i);
			imageFileInfoPojo.setWidth(i);
			imageFileInfoPojo.setCreateTime(new Date());
			imageFileInfoPojo.setImageType(1);
			imageFileInfoPojo.setMarkDesc("markDesc" + i);
			// imageFileInfoPojo.setModifyTime(new Date().toString());
			imageFileInfoPojo.setName("文件名" + i);
			imageFileInfoPojo.setOwn("上传者" + i);
			imageFileInfoPojo.setPath("路径" + i);
			imageFileInfoPojo.setSize(i);
			imageFileInfoPojo.setVersion("version" + i);

			recordFileInfoPojo.setCodeFormat(2);
			recordFileInfoPojo.setCreateTime(new Date());
			recordFileInfoPojo.setDeviceId("设备id" + i);
			recordFileInfoPojo.setEndTime(new Date());
			recordFileInfoPojo.setId(UUID.randomUUID().toString());
			// recordFileInfoPojo.setModifyTime(modifyTime);
			recordFileInfoPojo.setName("name" + i);
			recordFileInfoPojo.setOwn("上传者" + i);
			recordFileInfoPojo.setPath("路径" + i);
			recordFileInfoPojo.setRecordDataType(1);
			recordFileInfoPojo.setSize(i);
			recordFileInfoPojo.setStartTime(new Date());
			recordFileInfoPojo.setStreamType(1);
			recordFileInfoPojo.setVersion("版本" + i);

			recordFileMarkInfoPojo.setId(UUID.randomUUID().toString());
			recordFileMarkInfoPojo.setDeviceId("deviceId" + i);
			recordFileMarkInfoPojo.setEndTime(new Date());
			recordFileMarkInfoPojo.setFileInfoId(recordFileInfoPojo.getId());
			recordFileMarkInfoPojo.setFileOwnUserId("fileOwnUserId" + i);
			recordFileMarkInfoPojo.setMarkDesc("markDesc" + i);
			recordFileMarkInfoPojo.setMessageInfoId("messageInfoId" + i);
			recordFileMarkInfoPojo.setRecordType(2);
			recordFileMarkInfoPojo.setSize(i);
			recordFileMarkInfoPojo.setStartTime(new Date());
			if (i % 2 == 0) {
				recordFileMarkInfoPojo.setFileInfoId(imageFileInfoPojo.getId());
			} else {
				recordFileMarkInfoPojo.setFileInfoId(recordFileInfoPojo.getId());
			}
		}

	}

	// 单个保存测试
	@Test(groups = { "add" })
	public void sigleSave() {
		for (int i = 0; i < 4; i++) {
			assertTrue(fileMarkAccess.addFileInfo(recordFileInfoPojos.get(i)), "单个保存失败!");
			assertTrue(fileMarkAccess.addFileInfo(imageFileInfoPojos.get(i)), "单个保存失败!");
			assertTrue(fileMarkAccess.addFileMarkInfo(fileMarkInfoPojos.get(i)), "单个保存失败!");

		}
	}

	/**
	 * 修改文件描述对象（id字段作为修改标识）
	 * @Title: modifyFileInfo
	 * @param fileInfo	文件描述对象
	 * @return boolean	返回是否成功修改
	 * @author fsong
	 */
	// 单个修改测试
	@Test(groups = { "update" }, dependsOnGroups = { "add" })
	public void modify() {
		BaseFileInfoPojo baseFileInfoPojo = recordFileInfoPojos.get(0);
		baseFileInfoPojo.setName("名字改变了");
		BaseFileInfoPojo baseFileInfoPojo1 = imageFileInfoPojos.get(0);
		baseFileInfoPojo1.setName("名字改变了");
		FileMarkInfoPojo fileMarkInfo = fileMarkInfoPojos.get(0);
		fileMarkInfo.setMarkDesc("更新后我是描述");
		assertTrue(fileMarkAccess.modifyFileInfo(baseFileInfoPojo));
		assertTrue(fileMarkAccess.modifyFileInfo(baseFileInfoPojo1));
		assertTrue(fileMarkAccess.modifyFileMarkInfo(fileMarkInfo));
		assertFalse(fileMarkAccess.modifyFileInfo(null));
		assertFalse(fileMarkAccess.modifyFileInfo(null));
		assertFalse(fileMarkAccess.modifyFileMarkInfo(null));
	}

	/**
	 * 根据条件删除文件描述对象（关联的标注对象也将删除）
	 * @Title: deleteFileInfo
	 * @param type		文件描述对象类型
	 * @param queryComplexCondition	复杂组合条件
	 * @return boolean	返回是否成功删除
	 * @author fsong
	 */
	// 查询测试
	@Test(groups = { "delete" }, dependsOnGroups = { "query" })
	public void deleteFileInfo() {

		Date endTime = new Date();
		List<QueryComplexCondition> conditions1 = new ArrayList<QueryComplexCondition>();
		List<QueryComplexCondition> conditions2 = new ArrayList<QueryComplexCondition>();
		List<QueryComplexCondition> conditions3 = new ArrayList<QueryComplexCondition>();
		List<QueryComplexCondition> conditions = new ArrayList<QueryComplexCondition>();
		// path = 路径1 or name = name2
		QueryComplexCondition condition1 = new QueryComplexCondition();
		condition1.setKey("path");
		condition1.setQueryOperator(new QueryOperator(QueryOperator.Operator.EQUAL));
		condition1.setValue("路径1");
		QueryComplexCondition condition11 = new QueryComplexCondition();
		condition11.setKey("name");
		condition11.setQueryOperator(new QueryOperator(QueryOperator.Operator.GREATER_OR_EQUAL));
		condition11.setValue("name2");
		conditions1.add(condition1);
		conditions1.add(condition11);
		QueryComplexCondition conditionA = new QueryComplexCondition();
		conditionA.setConditionLst(conditions1);
		conditionA.setRelationOpertaor(RelationOperator.Relation_Or);

		// dbStartTime >= beginTime &&dbStartTime <= endTime &&dbEndTime
		// >=endTime（右包含）
		QueryComplexCondition condition2 = new QueryComplexCondition();
		condition2.setKey("deviceId");
		condition2.setQueryOperator(new QueryOperator(QueryOperator.Operator.GREATER_OR_EQUAL));
		condition2.setValue("设备id2");
		QueryComplexCondition condition22 = new QueryComplexCondition();
		condition22.setKey("startTime");
		condition22.setQueryOperator(new QueryOperator(QueryOperator.Operator.LESS_OR_EQUAL));
		condition22.setValue(endTime);
		conditions2.add(condition2);
		conditions2.add(condition22);
		QueryComplexCondition conditionB = new QueryComplexCondition();
		conditionB.setConditionLst(conditions2);
		conditionB.setRelationOpertaor(RelationOperator.Relation_And);

		// dbStartTime <= beginTime &&dbEndTime >= beginTime &&dbEndTime <=
		// endTime（左包含）
		QueryComplexCondition condition3 = new QueryComplexCondition();
		condition3.setKey("size");
		condition3.setQueryOperator(new QueryOperator(QueryOperator.Operator.EQUAL));
		condition3.setValue(8);
		QueryComplexCondition condition33 = new QueryComplexCondition();
		condition33.setKey("path");
		condition33.setQueryOperator(new QueryOperator(QueryOperator.Operator.EQUAL));
		condition33.setValue("路径");
		conditions3.add(condition3);
		conditions3.add(condition33);
		QueryComplexCondition conditionC = new QueryComplexCondition();
		conditionC.setConditionLst(conditions3);
		conditionC.setRelationOpertaor(RelationOperator.Relation_Or);

		QueryComplexCondition condition = new QueryComplexCondition();
		conditions.add(conditionA);
		conditions.add(conditionB);
		conditions.add(conditionC);
		condition.setConditionLst(conditions);
		condition.setRelationOpertaor(RelationOperator.Relation_Or);
		assertTrue(fileMarkAccess.deleteFileInfo(RecordFileInfoPojo.class, condition));
	}

	/**
	* 查询满足条件的文件描述对象数量
	* @Title: queryFileInfoSize
	* @param type	文件描述对象类型
	* @param queryComplexCondition	复杂组合条件
	* @return int	返回文件描述对象数，返回-1表示异常
	* @author fsong
	*/
	// 查询测试
	@Test(groups = { "query" }, dependsOnGroups = { "update" })
	public void queryFileInfoSize() {
		Date endTime = new Date();
		List<QueryComplexCondition> conditions1 = new ArrayList<QueryComplexCondition>();
		List<QueryComplexCondition> conditions2 = new ArrayList<QueryComplexCondition>();
		List<QueryComplexCondition> conditions3 = new ArrayList<QueryComplexCondition>();
		List<QueryComplexCondition> conditions = new ArrayList<QueryComplexCondition>();
		// path = 路径1 or name = name2
		QueryComplexCondition condition1 = new QueryComplexCondition();
		condition1.setKey("path");
		condition1.setQueryOperator(new QueryOperator(QueryOperator.Operator.EQUAL));
		condition1.setValue("路径1");
		QueryComplexCondition condition11 = new QueryComplexCondition();
		condition11.setKey("name");
		condition11.setQueryOperator(new QueryOperator(QueryOperator.Operator.GREATER_OR_EQUAL));
		condition11.setValue("name2");
		conditions1.add(condition1);
		conditions1.add(condition11);
		QueryComplexCondition conditionA = new QueryComplexCondition();
		conditionA.setConditionLst(conditions1);
		conditionA.setRelationOpertaor(RelationOperator.Relation_Or);

		// dbStartTime >= beginTime &&dbStartTime <= endTime &&dbEndTime
		// >=endTime（右包含）
		QueryComplexCondition condition2 = new QueryComplexCondition();
		condition2.setKey("deviceId");
		condition2.setQueryOperator(new QueryOperator(QueryOperator.Operator.GREATER_OR_EQUAL));
		condition2.setValue("设备id2");
		QueryComplexCondition condition22 = new QueryComplexCondition();
		// condition22.setKey("startTime");
		// condition22.setQueryOperator(new
		// QueryOperator(QueryOperator.Operator.LESS_OR_EQUAL));
		// condition22.setValue(new java.sql.Date(new Date().getTime()));
		condition22.setKey("version");
		condition22.setQueryOperator(new QueryOperator(QueryOperator.Operator.EQUAL));
		condition22.setValue("版本2");

		conditions2.add(condition2);
		conditions2.add(condition22);
		QueryComplexCondition conditionB = new QueryComplexCondition();
		conditionB.setConditionLst(conditions2);
		conditionB.setRelationOpertaor(RelationOperator.Relation_And);

		// dbStartTime <= beginTime &&dbEndTime >= beginTime &&dbEndTime <=
		// endTime（左包含）
		QueryComplexCondition condition3 = new QueryComplexCondition();
		condition3.setKey("size");
		condition3.setQueryOperator(new QueryOperator(QueryOperator.Operator.EQUAL));
		condition3.setValue(8);
		QueryComplexCondition condition33 = new QueryComplexCondition();
		condition33.setKey("path");
		condition33.setQueryOperator(new QueryOperator(QueryOperator.Operator.EQUAL));
		condition33.setValue("路径");
		conditions3.add(condition3);
		conditions3.add(condition33);
		QueryComplexCondition conditionC = new QueryComplexCondition();
		conditionC.setConditionLst(conditions3);
		conditionC.setRelationOpertaor(RelationOperator.Relation_Or);

		QueryComplexCondition condition = new QueryComplexCondition();
		conditions.add(conditionA);
		conditions.add(conditionB);
		conditions.add(conditionC);
		condition.setConditionLst(conditions);
		condition.setRelationOpertaor(RelationOperator.Relation_Or);
		assertTrue(fileMarkAccess.queryFileInfoSize(RecordFileInfoPojo.class, condition) != -1);

	}

}
