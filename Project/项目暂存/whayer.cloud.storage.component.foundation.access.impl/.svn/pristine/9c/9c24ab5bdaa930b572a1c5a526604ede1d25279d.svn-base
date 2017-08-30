/**  
 * 实现包
 * @Title: AccessServiceImpl.java
 * @Package whayer.cloud.storage.component.foundation.access.impl
 * @author Administrator
 * @date 2017年8月21日 上午9:44:33
 * @version v1.0.0
 */
package whayer.cloud.storage.component.foundation.access.impl;

import java.util.ArrayList;
import java.util.List;

import whayer.cloud.framework.data.db.dao.IDao;
import whayer.cloud.framework.data.db.dao.criteria.Criteria;
import whayer.cloud.framework.data.db.dao.criteria.Restrictions;
import whayer.cloud.framework.data.db.dao.datasource.DataBaseException;
import whayer.cloud.framework.data.db.dao.datasource.IDbSession;
import whayer.cloud.framework.data.db.dao.datasource.IDbSessionFactory;
import whayer.cloud.storage.component.foundation.access.dao.SimpleHibernateDao;
import whayer.cloud.utility.core.BeanService;

import com.whayer.cloud.storage.business.base.data.QueryCondition;
import com.whayer.cloud.storage.business.base.data.QueryOperator;
import com.whayer.cloud.storage.component.foundation.access.IAccessService;
import com.whayer.cloud.storage.component.foundation.access.beans.ResourcePojo;
import com.whayer.cloud.storage.component.foundation.access.beans.TableRowPojo;

/**
 * accessPOJO存取实现
 * @ClassName: AccessServiceImpl
 * @author Administrator
 * @date 2017年8月21日 上午9:44:33
 * @version v1.0.0
 * 
 */
public class AccessServiceImpl implements IAccessService {

	// session工厂
	private IDbSessionFactory factory;
	// 获取bean实例的接口
	private BeanService beanService;

	/**
	 * @param factory the factory to set
	 */
	public void setFactory(IDbSessionFactory factory) {
		this.factory = factory;
	}

	/**
	 * @param beanService the beanService to set
	 */
	public void setBeanService(BeanService beanService) {
		this.beanService = beanService;
	}

	@Override
	public <T> T get(Class<T> type, String id) {
		return beanService.getBean(SimpleHibernateDao.class).get(type, id);
	}

	@Override
	public <T> boolean add(T t) {
		if (t == null) {
			return false;
		}
		return beanService.getBean(SimpleHibernateDao.class).save(t, true);
	}

	@Override
	public <T> boolean addAll(List<T> ts) {
		if (ts == null || ts.size() == 0) {
			return false;
		}
		return beanService.getBean(SimpleHibernateDao.class).saveAll(ts, true);
	}

	@Override
	public <T> boolean update(T t) {
		if (t == null) {
			return false;
		}
		// simpleHibernateDao.setSession(factory.getSession());
		return beanService.getBean(SimpleHibernateDao.class).update(t, true);
	}

	@Override
	public <T> boolean updateAll(List<T> ts) {
		if (ts == null || ts.size() == 0) {
			return false;
		}
		// simpleHibernateDao.setSession(factory.getSession());
		return beanService.getBean(SimpleHibernateDao.class).update(ts, true);
	}

	/**
	 * 条件必须指定删除类型的classname
	 */
	@Override
	public <T> boolean delete(Class<T> type, List<QueryCondition> condition) {
		IDbSession session = null;
		boolean result = false;

		Criteria where = null;// where = new Criteria();
		if (condition != null && condition.size() > 0) {
			// queryCondition中任一属性为null，则丢弃该项匹配条件
			for (QueryCondition c : condition) {
				if (c != null && c.getKey() != null && !c.getKey().equals("") && c.getQueryOperator() != null
						&& c.getValue() != null) {
					if (where == null) {
						where = new Criteria();
					}
					if (c.getQueryOperator().getOperator() == QueryOperator.Operator.EQUAL) {
						where.add(Restrictions.eq(type, c.getKey(), c.getValue()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LIKE) {
						where.add(Restrictions.like(type, c.getKey(), c.getValue().toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.IN) {
						where.add(Restrictions.in(type, c.getKey(), c.getValue().toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.GREATER) {
						where.add(Restrictions.gt(type, c.getKey(), c.getValue().toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.GREATER_OR_EQUAL) {
						where.add(Restrictions.ge(type, c.getKey(), c.getValue().toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LESS) {
						where.add(Restrictions.lt(type, c.getKey(), c.getValue().toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LESS_OR_EQUAL) {
						where.add(Restrictions.le(type, c.getKey(), c.getValue().toString()));
					} else {
						return false;
					}
				}
			}
		}

		try {
			IDao dao = beanService.getBean(SimpleHibernateDao.class);
			session = dao.getSession();
			session.beginTransaction();
			List<?> resourcePojos = dao.selectList(type, where);
			// 批量删除其子资源
			List<TableRowPojo> reList = new ArrayList<TableRowPojo>();
			for (int i = 0; i < resourcePojos.size(); i++) {
				String id = null;
				id = (String) resourcePojos.get(i).getClass().getMethod("getId").invoke(resourcePojos.get(i));
				dao.delete(ResourcePojo.class, id);
				where = new Criteria();
				where.add(Restrictions.eq(TableRowPojo.class, "parentId", id));
				List<TableRowPojo> list = dao.selectList(TableRowPojo.class, where);
				reList.addAll(list);
			}
			if (reList.size() != 0) {
				deleteChild(reList, dao);
			}
			session.commit();
			session.clear();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (session != null) {
					session.rollback();
				}
			} catch (DataBaseException dataBaseException) {

				dataBaseException.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 递归删除子资源 
	 * @Title: deleteChild
	 * @param resourcePojos
	 * @param where 
	 * @param dao 
	 * @return List<TableRowPojo>
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private void deleteChild(List<TableRowPojo> resourcePojos, IDao dao) {

		List<TableRowPojo> reList = new ArrayList<TableRowPojo>();
		Criteria where = null;
		for (int i = 0; i < resourcePojos.size(); i++) {
			dao.delete(TableRowPojo.class, resourcePojos.get(i).getId());
			where = new Criteria();
			where.add(Restrictions.eq(TableRowPojo.class, "parentId", resourcePojos.get(i).getId()));
			List<TableRowPojo> list = dao.selectList(TableRowPojo.class, where);
			reList.addAll(list);
		}
		if (reList.size() != 0) {
			deleteChild(reList, dao);
		}
	}

	@Override
	public <T> int count(Class<T> type, List<QueryCondition> condition) {
		int result = 0;
		Criteria where = null;
		if (condition != null && condition.size() > 0) {
			// queryCondition中任一属性为null，则丢弃该项匹配条件
			for (QueryCondition c : condition) {
				if (c != null && c.getKey() != null && !c.getKey().equals("") && c.getQueryOperator() != null
						&& c.getValue() != null) {
					if (where == null) {
						where = new Criteria();
					}
					if (c.getQueryOperator().getOperator() == QueryOperator.Operator.EQUAL) {
						where.add(Restrictions.eq(type, c.getKey(), c.getValue()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LIKE) {
						where.add(Restrictions.like(type, c.getKey(), c.getValue().toString()));
					} else {
						// 还没有这个比较条件
						return result;
					}
				}
			}
		}
		try {
			return beanService.getBean(SimpleHibernateDao.class).count(type, where).intValue();
		} catch (DataBaseException e) {
			e.printStackTrace();

			return result;
		}
	}

	@Override
	public <T> List<T> query(Class<T> type, int start, int end, List<QueryCondition> condition) {
		Criteria where = new Criteria();
		// 检验输入是否合法
		if (start > end || (start < 0 && start != -1) || (end < 1 && end != -1)) {
			return null;
		}
		if (start != -1 && end != -1) {
			// 设置limit
			where.setFirstResult(start);
			where.setMaxResults(end);
		}
		if (condition != null && condition.size() > 0) {
			// queryCondition中任一属性为null，则丢弃该项匹配条件
			for (QueryCondition c : condition) {
				if (c != null && c.getKey() != null && !c.getKey().equals("") && c.getQueryOperator() != null
						&& c.getValue() != null) {
					if (c.getQueryOperator().getOperator() == QueryOperator.Operator.EQUAL) {
						where.add(Restrictions.eq(type, c.getKey(), c.getValue()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LIKE) {
						where.add(Restrictions.like(type, c.getKey(), c.getValue().toString()));
					} else {
						// 还没有这个比较条件
						return null;
					}
				}
			}

		}
		return beanService.getBean(SimpleHibernateDao.class).query(type, where);
	}

}
