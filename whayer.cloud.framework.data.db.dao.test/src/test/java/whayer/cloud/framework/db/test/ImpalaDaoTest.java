package whayer.cloud.framework.db.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import whayer.cloud.framework.data.db.dao.criteria.*;
import whayer.cloud.framework.data.db.dao.datasource.DataBaseException;
import whayer.cloud.framework.data.db.dao.datasource.IDbSession;
import whayer.cloud.framework.data.db.dao.datasource.IDbSessionFactory;
import whayer.cloud.framework.data.db.dao.metadata.*;
import whayer.cloud.utility.core.BeanService;

public class ImpalaDaoTest {
	
	private IDbSessionFactory factory = null;
	private BeanService beanService = null;
	private DbSystemInfo mDbSystemInfo = null;
	
	@BeforeSuite
	@SuppressWarnings("resource")
	public void testGetDao() {
		AbstractApplicationContext context = new ClassPathXmlApplicationContext("beans.xml");
    	
		factory = (IDbSessionFactory)context.getBean("impalaSessionFactory");
		
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
		
		IDbSystemInfoDAO dao = beanService.getBean(DbSystemInfoDaoImpalaImpl.class);
		dao.setSession(session);
		System.out.println("创建Dao成功！");
		
		DbSystemInfoTable table = new DbSystemInfoTable();
		DbTablesManager.registTable(table);
		System.out.println("注册DbSystemInfoTable成功！");
		
		try {
			dbAdmin.dropTable(table.TableName);
			System.out.println("删除DbSystemInfoTable成功！");
			
			dbAdmin.createExternalTable(table);
			System.out.println("创建DbSystemInfoTable成功！");

			dao = (IDbSystemInfoDAO)context.getBean("impalaDbSystemInfoDAO");
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
		
		IDbSystemInfoDAO dao = beanService.getBean(DbSystemInfoDaoImpalaImpl.class);
		dao.setSession(session);
		
		try {
			session.beginTransaction();
			
			dao.delete(DbSystemInfo.class, 99999999L);
			
			session.commit();
			
			System.out.println("删除id=99999999L的记录成功！");
		} 
		catch (DataBaseException e) {
			if(session != null) {
				
				try {
					session.rollback();
				} catch (DataBaseException e1) {
					e1.printStackTrace();
					Assert.fail("删除id=99999999L的记录失败，回滚事务失败！");
				}
			}
				
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
		
		IDbSystemInfoDAO dao = beanService.getBean(DbSystemInfoDaoImpalaImpl.class);
		dao.setSession(session);
		
		try {
			mDbSystemInfo = new DbSystemInfo();
			mDbSystemInfo.setId(99999999L);
			mDbSystemInfo.setName("数据库版本testNG");
			mDbSystemInfo.setVersion("1.0");
			
			session.beginTransaction();
			
			dao.insert(mDbSystemInfo);
			
			session.commit();
			
			System.out.println("插入记录成功！");
		} 
		catch (DataBaseException e) {
			if(session != null) {
				
				try {
					session.rollback();
				} catch (DataBaseException e1) {
					e1.printStackTrace();
					Assert.fail("插入记录失败，回滚事务失败！");
				}
			}

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
		
		IDbSystemInfoDAO dao = beanService.getBean(DbSystemInfoDaoImpalaImpl.class);
		dao.setSession(session);
		
		try {
			mDbSystemInfo.setVersion("1.0testNGupdate");
			
			session.beginTransaction();
			
			dao.update(mDbSystemInfo);
			
			session.commit();
			
			System.out.println("更新记录成功！");
		} 
		catch (DataBaseException e) {
			if(session != null) {
				
				try {
					session.rollback();
				} catch (DataBaseException e1) {
					e1.printStackTrace();
					Assert.fail("更新记录失败，回滚事务失败！");
				}
			}

			e.printStackTrace();
			Assert.fail("更新记录失败！");
		}
	}

	@BeforeClass 
	public void testUpdateWhere() {
		IDbSession session = null;
		try {
			session = factory.getSession();
		} catch (DataBaseException e2) {
			e2.printStackTrace();
			Assert.fail("创建DbSession失败！");
		}
		
		IDbSystemInfoDAO dao = beanService.getBean(DbSystemInfoDaoImpalaImpl.class);
		dao.setSession(session);
		
		try {
			Map<String, Object> fieldsAndValues = new HashMap<String, Object>();
			fieldsAndValues.put("version", "1.0testNGupdateWhere");
			
			Criteria mWhere = new Criteria();
    		mWhere.add(Restrictions.eq(DbSystemInfo.class, "id", 99999999L));
			
    		session.beginTransaction();
			
			dao.update(DbSystemInfo.class, fieldsAndValues, mWhere);
			
			session.commit();
			
			System.out.println("批量更新记录成功！");
		} 
		catch (DataBaseException e) {
			if(session != null) {
				
				try {
					session.rollback();
				} catch (DataBaseException e1) {
					e1.printStackTrace();
					Assert.fail("批量更新记录失败，回滚事务失败！");
				}
			}

			e.printStackTrace();
			Assert.fail("批量更新记录失败！");
		}
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
		
		IDbSystemInfoDAO dao = beanService.getBean(DbSystemInfoDaoImpalaImpl.class);
		dao.setSession(session);
		
		try {
			mDbSystemInfo = dao.selectOne(DbSystemInfo.class, mDbSystemInfo.getId());
			
			System.out.println("查询记录成功！");
			
			Assert.assertNotNull(mDbSystemInfo, "查询记录为空！");
			
			System.out.println(mDbSystemInfo);
		} 
		catch (DataBaseException e) {
			e.printStackTrace();
			Assert.fail("查询记录失败！");
		}
	}
	
	@Test
	public void testSelectList() {
		IDbSession session = null;
		try {
			session = factory.getSession();
		} catch (DataBaseException e2) {
			e2.printStackTrace();
			Assert.fail("创建DbSession失败！");
		}
		
		IDbSystemInfoDAO dao = beanService.getBean(DbSystemInfoDaoImpalaImpl.class);
		dao.setSession(session);
		
		try {
			Criteria mWhere = new Criteria();
    		mWhere.add(Restrictions.like(DbSystemInfo.class, "name", "%tes%"));
    		
			List<DbSystemInfo> mDbSystemInfos = dao.selectList(DbSystemInfo.class, mWhere);
			
			System.out.println("查询name like '%tes%'的记录成功！");
			
			Assert.assertTrue(mDbSystemInfos.size() > 0, "查询name like '%tes%'的记录为空！");
			
			for(int i = 0; i < mDbSystemInfos.size(); i++) {
        		System.out.println(mDbSystemInfos.get(i));
    		}
		} 
		catch (DataBaseException e) {
			e.printStackTrace();
			Assert.fail("查询name like '%tes%'的记录失败！");
		}
	}

	@Test
	public void testCount() {
		IDbSession session = null;
		try {
			session = factory.getSession();
		} catch (DataBaseException e2) {
			e2.printStackTrace();
			Assert.fail("创建DbSession失败！");
		}
		
		IDbSystemInfoDAO dao = beanService.getBean(DbSystemInfoDaoImpalaImpl.class);
		dao.setSession(session);
		
		try {
			Criteria mWhere = new Criteria();
    		mWhere.add(Restrictions.like(DbSystemInfo.class, "name", "%tes%"));
    		
			long mCount = dao.count(DbSystemInfo.class, mWhere);
			
			System.out.println("统计name like '%tes%'的记录成功！");
			
			System.out.println(mCount);
		} 
		catch (DataBaseException e) {
			e.printStackTrace();
			Assert.fail("统计name like '%tes%'的记录失败！");
		}
	}

	@Test
	public void testGroup() {
		IDbSession session = null;
		try {
			session = factory.getSession();
		} catch (DataBaseException e2) {
			e2.printStackTrace();
			Assert.fail("创建DbSession失败！");
		}
		
		IDbSystemInfoDAO dao = beanService.getBean(DbSystemInfoDaoImpalaImpl.class);
		dao.setSession(session);
		
		try {
			List<GroupProperty> groupFields = new ArrayList<GroupProperty>();
			groupFields.add(new GroupProperty(DbSystemInfo.class, "name", GroupOperation.None));
			groupFields.add(new GroupProperty(DbSystemInfo.class, "version", GroupOperation.Count));
			
			List<CriterionProperty> groupByFields = new ArrayList<CriterionProperty>();
			groupByFields.add(new CriterionProperty(DbSystemInfo.class, "name"));
    		
			List<DbSystemInfoGroupByName> rs = dao.group(DbSystemInfo.class, DbSystemInfoGroupByName.class, groupFields, groupByFields, null);
			
			System.out.println("分组统计成功！");
			
			for(int i = 0; i < rs.size(); i++) {
				System.out.println(rs.get(i));	
			}
		} 
		catch (DataBaseException e) {
			e.printStackTrace();
			Assert.fail("分组统计失败！");
		}
	}

	@AfterTest
	public void testDeleteWhere() {
		IDbSession session = null;
		try {
			session = factory.getSession();
		} catch (DataBaseException e2) {
			e2.printStackTrace();
			Assert.fail("创建DbSession失败！");
		}
		
		IDbSystemInfoDAO dao = beanService.getBean(DbSystemInfoDaoImpalaImpl.class);
		dao.setSession(session);
		
		try {
			Criteria mWhere = new Criteria();
    		mWhere.add(Restrictions.eq(DbSystemInfo.class, "id", 99999999L));
    		
    		session.beginTransaction();
			
			dao.delete(DbSystemInfo.class, mWhere);
			
			session.commit();
			
			System.out.println("删除id eq 99999999L的记录成功！");
		} 
		catch (DataBaseException e) {
			if(session != null) {
				
				try {
					session.rollback();
				} catch (DataBaseException e1) {
					e1.printStackTrace();
					Assert.fail("删除id eq 99999999L的记录失败，回滚事务失败！");
				}
			}
				
			e.printStackTrace();
			Assert.fail("删除id eq 99999999L的记录失败！");
		}
	}

	@AfterSuite
	public void testCloseConnection() {
		
	}

}
