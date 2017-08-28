package com.whayer.cloud.storage.foundation.filemarkaccess;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertNotEquals;
import static org.testng.Assert.assertNotNull;
import static org.testng.Assert.assertTrue;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.testng.annotations.Test;

import com.whayer.cloud.storage.business.base.data.BusinessEnum.CodeFormat;
import com.whayer.cloud.storage.business.base.data.BusinessEnum.ImageType;
import com.whayer.cloud.storage.business.base.data.BusinessEnum.RecordType;
import com.whayer.cloud.storage.business.base.data.QueryComplexCondition;
import com.whayer.cloud.storage.business.base.data.QueryComplexCondition.RelationOperator;
import com.whayer.cloud.storage.business.base.data.QueryOperator;
import com.whayer.cloud.storage.foundation.filemarkaccess.beans.ImageFileInfoPojo;
import com.whayer.cloud.storage.foundation.filemarkaccess.beans.RecordFileInfoPojo;
import com.whayer.cloud.storage.foundation.filemarkaccess.beans.RecordFileMarkInfoPojo;

@Test
@ContextConfiguration(locations = { "classpath:application-context.xml" })
public class IFileMarkAccessTest {
	@Autowired
	private IFileMarkAccess fileMarkAccess;
	String recordFileInfoId;
	String recordFileMarkInfoId;
	String imageFileInfoId;

	@Test
	void addFileInfoTest() {
		RecordFileInfoPojo recordFileInfo = new RecordFileInfoPojo();
		ImageFileInfoPojo imageFileInfo = new ImageFileInfoPojo();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		recordFileInfoId = UUID.randomUUID().toString();
		recordFileInfo.setId(recordFileInfoId);
		recordFileInfo.setName("CAMERA1-VIDEO");
		recordFileInfo.setCodeFormat(CodeFormat.H264.ordinal());
		recordFileInfo.setDeviceId("123456789");
		recordFileInfo.setOwn("SYSTEM");
		recordFileInfo.setPath("/aiondisk/123456789/CAMERA1-VIDEO");
		recordFileInfo.setRecordDataType(1);

		assertTrue(fileMarkAccess.addFileInfo(recordFileInfo));

		imageFileInfoId = UUID.randomUUID().toString();
		imageFileInfo.setId(imageFileInfoId);
		imageFileInfo.setName("CAMERA1-VIDEO-IMAGE");
		imageFileInfo.setCreateTime(new Date());
		imageFileInfo.setModifyTime(new Date());
		imageFileInfo.setImageType(ImageType.JPG.ordinal());
		imageFileInfo.setMarkDesc("this is  image descripte object add test");
		imageFileInfo.setSize(123556);
		imageFileInfo.setVersion("v1.0.0");
		imageFileInfo.setWidth(400);
		imageFileInfo.setHeight(500);
		imageFileInfo.setOwn("USER1");
		imageFileInfo.setPath("/aiondisk/image/CAMERA1-VIDEO-IMAGE.jpg");
		assertTrue(fileMarkAccess.addFileInfo(recordFileInfo));
	}

	@Test(dependsOnMethods = "addFileInfoTest")
	void modifyFileInfoTest() {
		RecordFileInfoPojo recordFileInfo = new RecordFileInfoPojo();
		ImageFileInfoPojo imageFileInfo = new ImageFileInfoPojo();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		recordFileInfo.setId(recordFileInfoId);
		recordFileInfo.setName("CAMERA1-VIDEO-modify");
		recordFileInfo.setCodeFormat(CodeFormat.H264.ordinal());
		recordFileInfo.setDeviceId("123456789");
		recordFileInfo.setOwn("SYSTEM");
		recordFileInfo.setPath("");
		recordFileInfo.setStartTime(new Date());
		recordFileInfo.setVersion("v1.0.0");
		recordFileInfo.setRecordDataType(2);

		assertFalse(fileMarkAccess.modifyFileInfo(recordFileInfo));

		imageFileInfo.setId(imageFileInfoId);
		imageFileInfo.setName("CAMERA1-VIDEO-IMAGE");
		imageFileInfo.setCreateTime(new Date());
		imageFileInfo.setModifyTime(new Date());
		imageFileInfo.setImageType(ImageType.JPG.ordinal());
		imageFileInfo.setMarkDesc("this is  image descripte object modify test");
		imageFileInfo.setSize(123556);
		imageFileInfo.setVersion("v1.0.0");
		imageFileInfo.setWidth(200);
		imageFileInfo.setHeight(600);
		imageFileInfo.setOwn("USER1");
		imageFileInfo.setPath("/aiondisk/image/CAMERA1-VIDEO-IMAGE.jpg");
		assertTrue(fileMarkAccess.modifyFileInfo(recordFileInfo));
	}

	@Test(dependsOnMethods = "modifyFileInfoTest")
	void deleteFileInfoTest() {
		QueryComplexCondition condition = new QueryComplexCondition();
		condition.setKey("id");
		condition.setQueryOperator(new QueryOperator(QueryOperator.Operator.EQUAL));
		condition.setValue(recordFileInfoId);
		assertTrue(fileMarkAccess.deleteFileInfo(RecordFileInfoPojo.class, condition));

		condition.setKey("id");
		condition.setValue(imageFileInfoId);
		assertTrue(fileMarkAccess.deleteFileInfo(ImageFileInfoPojo.class, condition));

	}

	@Test()
	void queryFileInfoTest() {
		RecordFileInfoPojo recordFileInfo = new RecordFileInfoPojo();
		ImageFileInfoPojo imageFileInfo = new ImageFileInfoPojo();
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		recordFileInfo.setId(UUID.randomUUID().toString());
		recordFileInfo.setName("CAMERA2-VIDEO");
		recordFileInfo.setCodeFormat(CodeFormat.H264.ordinal());
		recordFileInfo.setDeviceId("1234567890");
		recordFileInfo.setOwn("SYSTEM");
		recordFileInfo.setPath("/aiondisk/1234567890/CAMERA1-VIDEO");
		recordFileInfo.setRecordDataType(0);

		imageFileInfo.setId(UUID.randomUUID().toString());
		imageFileInfo.setName("CAMERA2-VIDEO-IMAGE");
		imageFileInfo.setCreateTime(new Date());
		imageFileInfo.setModifyTime(new Date());
		imageFileInfo.setImageType(ImageType.JPG.ordinal());
		imageFileInfo.setMarkDesc("this is  image descripte object add test");
		imageFileInfo.setSize(123516);
		imageFileInfo.setVersion("v1.0.0");
		imageFileInfo.setWidth(300);
		imageFileInfo.setHeight(200);
		imageFileInfo.setOwn("USER2");
		imageFileInfo.setPath("/aiondisk/image/CAMERA2-VIDEO-IMAGE.jpg");

		QueryComplexCondition condition1 = new QueryComplexCondition();
		condition1.setKey("name");
		condition1.setQueryOperator(new QueryOperator(QueryOperator.Operator.EQUAL));
		condition1.setValue("CAMERA2-VIDEO");
		int size = fileMarkAccess.queryFileInfoSize(RecordFileInfoPojo.class, condition1);
		assertNotEquals(size, 0);
		List<RecordFileInfoPojo> ret1 = fileMarkAccess.queryFileInfo(RecordFileInfoPojo.class, condition1, 0, size - 1);
		assertNotNull(ret1);

		QueryComplexCondition condition2 = new QueryComplexCondition();
		condition2.setKey("width");
		condition2.setQueryOperator(new QueryOperator(QueryOperator.Operator.EQUAL));
		condition2.setValue(300);
		QueryComplexCondition condition3 = new QueryComplexCondition();
		condition3.setKey("height");
		condition3.setQueryOperator(new QueryOperator(QueryOperator.Operator.EQUAL));
		condition3.setValue(200);
		QueryComplexCondition condition4 = new QueryComplexCondition();
		List<QueryComplexCondition> conditionLst = new ArrayList<QueryComplexCondition>();
		conditionLst.add(condition2);
		conditionLst.add(condition3);
		condition4.setConditionLst(conditionLst);
		condition4.setRelationOpertaor(RelationOperator.Relation_And);
		size = fileMarkAccess.queryFileInfoSize(ImageFileInfoPojo.class, condition4);
		assertNotEquals(size, 0);
		List<ImageFileInfoPojo> ret2 = fileMarkAccess.queryFileInfo(ImageFileInfoPojo.class, condition4, 0, size - 1);
		assertNotNull(ret2);
		assertTrue(fileMarkAccess.deleteFileInfo(RecordFileInfoPojo.class, condition1));
		assertTrue(fileMarkAccess.deleteFileInfo(ImageFileInfoPojo.class, condition4));
	}

	@Test(dependsOnMethods = "addFileInfoTest")
	void addFileMarkInfoTest() {
		RecordFileMarkInfoPojo recordFileMarkInfo = new RecordFileMarkInfoPojo();
		recordFileMarkInfoId = UUID.randomUUID().toString();
		recordFileMarkInfo.setId(recordFileMarkInfoId);
		recordFileMarkInfo.setDeviceId("123456789");
		recordFileMarkInfo.setStartTime(new Date());
		recordFileMarkInfo.setFileOwnUserId("TESTUSER1");
		recordFileMarkInfo.setMarkDesc("this is test video record of mark");
		recordFileMarkInfo.setRecordType(RecordType.ALARM_EVENT_RECORD.ordinal());
		recordFileMarkInfo.setMessageInfoId("13243546653");
		recordFileMarkInfo.setSize(31267600);
		assertFalse(fileMarkAccess.addFileMarkInfo(recordFileMarkInfo));
		recordFileMarkInfo.setFileInfoId(recordFileInfoId);
		assertTrue(fileMarkAccess.addFileMarkInfo(recordFileMarkInfo));
	}

	@Test(dependsOnMethods = "addFileMarkInfoTest")
	void modifyFileMarkInfoTest() {
		QueryComplexCondition condition = new QueryComplexCondition();
		condition.setKey("id");
		condition.setQueryOperator(new QueryOperator(QueryOperator.Operator.EQUAL));
		condition.setValue(recordFileMarkInfoId);
		List<RecordFileMarkInfoPojo> ret = fileMarkAccess.queryFileMarkInfo(RecordFileMarkInfoPojo.class, condition, 0,
				0);
		assertNotNull(ret);
		RecordFileMarkInfoPojo recordFileMarkInfo = ret.get(0);
		recordFileMarkInfo.setEndTime(new Date());
		assertTrue(fileMarkAccess.modifyFileMarkInfo(recordFileMarkInfo));
	}

	@Test(dependsOnMethods = "modifyFileMarkInfoTest", priority = 2)
	void deleteFileMarkInfoTest() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm::SS");
		Date beginTime = null;
		try {
			beginTime = format.parse("2017-09-01 00:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date endTime = new Date();

		List<QueryComplexCondition> conditions1 = new ArrayList<QueryComplexCondition>();
		List<QueryComplexCondition> conditions2 = new ArrayList<QueryComplexCondition>();
		List<QueryComplexCondition> conditions3 = new ArrayList<QueryComplexCondition>();
		List<QueryComplexCondition> conditions = new ArrayList<QueryComplexCondition>();
		// dbStartTime <= beginTime && dbEndTime >=endTime(完全包含)
		QueryComplexCondition condition1 = new QueryComplexCondition();
		condition1.setKey("startTime");
		condition1.setQueryOperator(new QueryOperator(QueryOperator.Operator.LESS_OR_EQUAL));
		condition1.setValue(beginTime);
		QueryComplexCondition condition11 = new QueryComplexCondition();
		condition11.setKey("endTime");
		condition11.setQueryOperator(new QueryOperator(QueryOperator.Operator.GREATER_OR_EQUAL));
		condition11.setValue(endTime);
		conditions1.add(condition1);
		conditions1.add(condition11);
		QueryComplexCondition conditionA = new QueryComplexCondition();
		conditionA.setConditionLst(conditions1);
		conditionA.setRelationOpertaor(RelationOperator.Relation_And);

		// dbStartTime >= beginTime &&dbStartTime <= endTime &&dbEndTime
		// >=endTime（右包含）
		QueryComplexCondition condition2 = new QueryComplexCondition();
		condition2.setKey("startTime");
		condition2.setQueryOperator(new QueryOperator(QueryOperator.Operator.GREATER_OR_EQUAL));
		condition2.setValue(beginTime);
		QueryComplexCondition condition22 = new QueryComplexCondition();
		condition22.setKey("startTime");
		condition22.setQueryOperator(new QueryOperator(QueryOperator.Operator.LESS_OR_EQUAL));
		condition22.setValue(endTime);
		QueryComplexCondition condition222 = new QueryComplexCondition();
		condition222.setKey("endTime");
		condition222.setQueryOperator(new QueryOperator(QueryOperator.Operator.GREATER_OR_EQUAL));
		condition222.setValue(endTime);
		conditions2.add(condition2);
		conditions2.add(condition22);
		conditions2.add(condition222);
		QueryComplexCondition conditionB = new QueryComplexCondition();
		conditionB.setConditionLst(conditions2);
		conditionB.setRelationOpertaor(RelationOperator.Relation_And);

		// dbStartTime <= beginTime &&dbEndTime >= beginTime &&dbEndTime <=
		// endTime（左包含）
		QueryComplexCondition condition3 = new QueryComplexCondition();
		condition3.setKey("startTime");
		condition3.setQueryOperator(new QueryOperator(QueryOperator.Operator.LESS_OR_EQUAL));
		condition3.setValue(beginTime);
		QueryComplexCondition condition33 = new QueryComplexCondition();
		condition33.setKey("endTime");
		condition33.setQueryOperator(new QueryOperator(QueryOperator.Operator.GREATER_OR_EQUAL));
		condition33.setValue(beginTime);
		QueryComplexCondition condition333 = new QueryComplexCondition();
		condition333.setKey("endTime");
		condition333.setQueryOperator(new QueryOperator(QueryOperator.Operator.LESS_OR_EQUAL));
		condition333.setValue(endTime);
		conditions3.add(condition3);
		conditions3.add(condition33);
		conditions3.add(condition333);
		QueryComplexCondition conditionC = new QueryComplexCondition();
		conditionC.setConditionLst(conditions3);
		conditionC.setRelationOpertaor(RelationOperator.Relation_And);

		QueryComplexCondition condition = new QueryComplexCondition();
		conditions.add(conditionA);
		conditions.add(conditionB);
		conditions.add(conditionC);
		condition.setConditionLst(conditions);
		condition.setRelationOpertaor(RelationOperator.Relation_Or);
		assertTrue(fileMarkAccess.deleteFileMarkInfo(RecordFileMarkInfoPojo.class, condition));
		assertTrue(fileMarkAccess.deleteFileMarkInfo(RecordFileMarkInfoPojo.class, null));
	}

	@Test(dependsOnMethods = "modifyFileMarkInfoTest")
	void queryFileMarkInfoTest() {
		DateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm::SS");
		Date beginTime = null;
		try {
			beginTime = format.parse("2017-09-01 00:00:00");
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Date endTime = new Date();

		List<QueryComplexCondition> conditions1 = new ArrayList<QueryComplexCondition>();
		List<QueryComplexCondition> conditions2 = new ArrayList<QueryComplexCondition>();
		List<QueryComplexCondition> conditions3 = new ArrayList<QueryComplexCondition>();
		List<QueryComplexCondition> conditions = new ArrayList<QueryComplexCondition>();
		// dbStartTime <= beginTime && dbEndTime >=endTime(完全包含)
		QueryComplexCondition condition1 = new QueryComplexCondition();
		condition1.setKey("startTime");
		condition1.setQueryOperator(new QueryOperator(QueryOperator.Operator.LESS_OR_EQUAL));
		condition1.setValue(beginTime);
		QueryComplexCondition condition11 = new QueryComplexCondition();
		condition11.setKey("endTime");
		condition11.setQueryOperator(new QueryOperator(QueryOperator.Operator.GREATER_OR_EQUAL));
		condition11.setValue(endTime);
		conditions1.add(condition1);
		conditions1.add(condition11);
		QueryComplexCondition conditionA = new QueryComplexCondition();
		conditionA.setConditionLst(conditions1);
		conditionA.setRelationOpertaor(RelationOperator.Relation_And);

		// dbStartTime >= beginTime &&dbStartTime <= endTime &&dbEndTime
		// >=endTime（右包含）
		QueryComplexCondition condition2 = new QueryComplexCondition();
		condition2.setKey("startTime");
		condition2.setQueryOperator(new QueryOperator(QueryOperator.Operator.GREATER_OR_EQUAL));
		condition2.setValue(beginTime);
		QueryComplexCondition condition22 = new QueryComplexCondition();
		condition22.setKey("startTime");
		condition22.setQueryOperator(new QueryOperator(QueryOperator.Operator.LESS_OR_EQUAL));
		condition22.setValue(endTime);
		QueryComplexCondition condition222 = new QueryComplexCondition();
		condition222.setKey("endTime");
		condition222.setQueryOperator(new QueryOperator(QueryOperator.Operator.GREATER_OR_EQUAL));
		condition222.setValue(endTime);
		conditions2.add(condition2);
		conditions2.add(condition22);
		conditions2.add(condition222);
		QueryComplexCondition conditionB = new QueryComplexCondition();
		conditionB.setConditionLst(conditions2);
		conditionB.setRelationOpertaor(RelationOperator.Relation_And);

		// dbStartTime <= beginTime &&dbEndTime >= beginTime &&dbEndTime <=
		// endTime（左包含）
		QueryComplexCondition condition3 = new QueryComplexCondition();
		condition3.setKey("startTime");
		condition3.setQueryOperator(new QueryOperator(QueryOperator.Operator.LESS_OR_EQUAL));
		condition3.setValue(beginTime);
		QueryComplexCondition condition33 = new QueryComplexCondition();
		condition33.setKey("endTime");
		condition33.setQueryOperator(new QueryOperator(QueryOperator.Operator.GREATER_OR_EQUAL));
		condition33.setValue(beginTime);
		QueryComplexCondition condition333 = new QueryComplexCondition();
		condition333.setKey("endTime");
		condition333.setQueryOperator(new QueryOperator(QueryOperator.Operator.LESS_OR_EQUAL));
		condition333.setValue(endTime);
		conditions3.add(condition3);
		conditions3.add(condition33);
		conditions3.add(condition333);
		QueryComplexCondition conditionC = new QueryComplexCondition();
		conditionC.setConditionLst(conditions3);
		conditionC.setRelationOpertaor(RelationOperator.Relation_And);

		QueryComplexCondition condition = new QueryComplexCondition();
		conditions.add(conditionA);
		conditions.add(conditionB);
		conditions.add(conditionC);
		condition.setConditionLst(conditions);
		condition.setRelationOpertaor(RelationOperator.Relation_Or);

		int size = fileMarkAccess.queryFileInfoMarkSize(RecordFileMarkInfoPojo.class, condition);
		assertNotEquals(size, 0);
		List<RecordFileMarkInfoPojo> ret = fileMarkAccess.queryFileMarkInfo(RecordFileMarkInfoPojo.class, condition, 0,
				size - 1);
		assertNotNull(ret);
	}
}
