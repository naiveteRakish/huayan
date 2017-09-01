package whayer.cloud.framework.db.test;

import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.testng.Assert;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import whayer.cloud.dbSystemInfoDao.*;
import whayer.cloud.framework.data.db.dao.datasource.DataBaseException;
import whayer.cloud.framework.data.db.dao.datasource.IDbSession;
import whayer.cloud.framework.data.db.dao.datasource.IDbSessionFactory;
import whayer.cloud.framework.data.db.dao.metadata.*;
import whayer.cloud.utility.core.BeanService;

public class HBaseDaoTest {

	private IDbSessionFactory factory = null;
	private BeanService beanService = null;
	private DbSystemInfo dbSystemInfo = null;
	
	@BeforeSuite
	@SuppressWarnings("resource")
	public void testGetDao() {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    	
		factory = (IDbSessionFactory)context.getBean("hBaseSessionFactory");
		
		beanService = (BeanService)context.getBean("beanService");

		IDbSession session = null;
		try {
			session = factory.getSession();
		} catch (DataBaseException e2) {
			e2.printStackTrace();
			Assert.fail("创建DbSession失败！");
		}

		IDbAdmin dbAdmin = beanService.getBean(MyBatisDbAdmin.class);
		try {
			dbAdmin.setSession(session);
		} catch (DataBaseException e2) {
			e2.printStackTrace();
			Assert.fail("关联Session失败！");
		}
		
		IDbSystemInfoDAO dao = beanService.getBean(DbSystemInfoDaoHBaseImpl.class);
		dao.setSession(session);
		System.out.println("创建Dao成功！");
		
		DbSystemInfoTable table = new DbSystemInfoTable();
		DbTablesManager.registTable(table);
		System.out.println("注册DbSystemInfoTable成功！");
		
		try {
			dbAdmin.dropTable(table.TableName);
			System.out.println("删除DbSystemInfoTable成功！");
			
			dbAdmin.createTable(table);
			System.out.println("创建DbSystemInfoTable成功！");

			dao = (IDbSystemInfoDAO)context.getBean("hBaseDbSystemInfoDAO");
			dao.setSession(session);
			System.out.println("创建Dao成功！");
			
			dao.insert(table.getDefaultRecorders());
		} 
		catch (DataBaseException e) {
			e.printStackTrace();
			Assert.fail("创建Dao失败！");
		}
	}

	@BeforeTest
	public void testDeleteOne() {
		IDbSession session = null;
		try {
			session = factory.getSession();
		} catch (DataBaseException e2) {
			e2.printStackTrace();
			Assert.fail("创建DbSession失败！");
		}

		IDbSystemInfoDAO dao = beanService.getBean(DbSystemInfoDaoHBaseImpl.class);
		dao.setSession(session);
		
		try {
			dao.delete(DbSystemInfo.class, 99999999L);
			
			System.out.println("删除id=99999999L的记录成功！");
		} 
		catch (DataBaseException e) {
			e.printStackTrace();
			Assert.fail("删除id=99999999L的记录失败！");
		}
	}

	@BeforeClass 
	public void testInsert() {
		IDbSession session = null;
		try {
			session = factory.getSession();
		} catch (DataBaseException e2) {
			e2.printStackTrace();
			Assert.fail("创建DbSession失败！");
		}

		IDbSystemInfoDAO dao = beanService.getBean(DbSystemInfoDaoHBaseImpl.class);
		dao.setSession(session);
		
		try {
			dbSystemInfo = new DbSystemInfo();
			dbSystemInfo.setId(99999999L);
			dbSystemInfo.setName("数据库版本testNG");
			dbSystemInfo.setVersion("1.0");
			
			dao.insert(dbSystemInfo);
			
			System.out.println("插入记录成功！");
		} 
		catch (DataBaseException e) {
			e.printStackTrace();
			Assert.fail("插入记录失败！");
		}
	}
	
	@BeforeClass 
	public void testUpdateOne() {
		IDbSession session = null;
		try {
			session = factory.getSession();
		} catch (DataBaseException e2) {
			e2.printStackTrace();
			Assert.fail("创建DbSession失败！");
		}

		IDbSystemInfoDAO dao = beanService.getBean(DbSystemInfoDaoHBaseImpl.class);
		dao.setSession(session);
		
		try {
			dbSystemInfo.setVersion("1.0testNGupdate");
			
			dao.update(dbSystemInfo);

			System.out.println("更新记录成功！");
		} 
		catch (DataBaseException e) {
			e.printStackTrace();
			Assert.fail("更新记录失败！");
		}
	}

	@BeforeClass 
	public void testUpdateWhere() {
//		try {
//			Map<String, Object> fieldsAndValues = new HashMap<String, Object>();
//			fieldsAndValues.put("version", "1.0testNGupdateWhere");
//			
//			HyCriteria mWhere = new HyCriteria();
//    		mWhere.add(HyRestrictions.eq("id", 99999999L));
//			
//			mDao.update(DbSystemInfo.class, fieldsAndValues, mWhere);
//			
//			System.out.println("批量更新记录成功！");
//		} 
//		catch (DataBaseException e) {
//			Assert.fail("批量更新记录失败！");
//			e.printStackTrace();
//		}
	}
	
	@Test
	public void testSelectOne() {
		IDbSession session = null;
		try {
			session = factory.getSession();
		} catch (DataBaseException e2) {
			e2.printStackTrace();
			Assert.fail("创建DbSession失败！");
		}

		IDbSystemInfoDAO dao = beanService.getBean(DbSystemInfoDaoHBaseImpl.class);
		dao.setSession(session);
		
		try {
			dbSystemInfo = dao.selectOne(DbSystemInfo.class, dbSystemInfo.getId());
			
			System.out.println("查询记录成功！");
			
			Assert.assertNotNull(dbSystemInfo, "查询记录为空！");
			
			System.out.println(dbSystemInfo);
		} 
		catch (DataBaseException e) {
			e.printStackTrace();
			Assert.fail("查询记录失败！");
		}
	}
	
	@Test
	public void testSelectList() {
//		try {
//			HyCriteria mWhere = new HyCriteria();
//    		mWhere.add(HyRestrictions.like("name", "%tes%"));
//    		
//			List<DbSystemInfo> mDbSystemInfos = mDao.selectList(DbSystemInfo.class, mWhere);
//			
//			System.out.println("查询name like '%tes%'的记录成功！");
//			
//			Assert.assertTrue(mDbSystemInfos.size() > 0, "查询name like '%tes%'的记录为空！");
//			
//			for(int i = 0; i < mDbSystemInfos.size(); i++) {
//        		System.out.println(mDbSystemInfos.get(i));
//    		}
//		} 
//		catch (DataBaseException e) {
// 		    e.printStackTrace();
//			Assert.fail("查询name like '%tes%'的记录失败！");
//		}
	}

	@Test
	public void testCount() {
//		try {
//			HyCriteria mWhere = new HyCriteria();
//    		mWhere.add(HyRestrictions.like("name", "%tes%"));
//    		
//			long mCount = mDao.count(DbSystemInfo.class, mWhere);
//			
//			System.out.println("统计name like '%tes%'的记录成功！");
//			
//			System.out.println(mCount);
//		} 
//		catch (DataBaseException e) {
//		    e.printStackTrace();
//			Assert.fail("统计name like '%tes%'的记录失败！");
//		}
	}

	@Test
	public void testGroup() {
//		try {
//			List<GroupProperty> groupFields = new ArrayList<GroupProperty>();
//			groupFields.add(new GroupProperty("name", GroupOperation.None));
//			groupFields.add(new GroupProperty("version", GroupOperation.Count));
//			
//			List<String> groupByFields = new ArrayList<String>();
//			groupByFields.add("name");
//    		
//			List<DbSystemInfoGroupByName> rs = mDao.group(DbSystemInfo.class, DbSystemInfoGroupByName.class, groupFields, groupByFields, null);
//			
//			System.out.println("分组统计成功！");
//			
//			for(int i = 0; i < rs.size(); i++) {
//				System.out.println(rs.get(i));	
//			}
//		} 
//		catch (DataBaseException e) {
//			e.printStackTrace();
//			Assert.fail("分组统计失败！");
//		}
	}

	@AfterTest
	public void testDeleteWhere() {
//		try {
//			HyCriteria mWhere = new HyCriteria();
//    		mWhere.add(HyRestrictions.eq("id", 99999999L));
//    		
//			mDao.delete(DbSystemInfo.class, mWhere);
//			
//			System.out.println("删除id eq 99999999L的记录成功！");
//		} 
//		catch (DataBaseException e) {
//		    e.printStackTrace();
//			Assert.fail("删除id eq 99999999L的记录失败！");
//		}
		this.testDeleteOne();
	}

	@AfterSuite
	public void testCloseConnection() {
		
	}

}
