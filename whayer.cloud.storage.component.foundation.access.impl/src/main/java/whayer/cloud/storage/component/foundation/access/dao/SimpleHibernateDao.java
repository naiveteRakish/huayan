package whayer.cloud.storage.component.foundation.access.dao;

import java.util.List;

import whayer.cloud.framework.data.db.dao.HibernateDao;
import whayer.cloud.framework.data.db.dao.criteria.ICriteria;
import whayer.cloud.framework.data.db.dao.criteria.IQuery;
import whayer.cloud.framework.data.db.dao.datasource.DataBaseException;
import whayer.cloud.framework.data.db.dao.datasource.HibernateDbSessionFactory;

/**
 * 简单HibernateDao实现,只需要注入factory即可做简单操作
 * 本类所有函数都在一个事务中，跨事务操作可以：
 * 1.使用HibernateDao
 * 2.使用HibernateDao派生的其他派生类
 * 3.使用本类，但需显示从外部注入的session，由外部控制session的begin、commit、rollback
 * @ClassName: SimpleHibernateDao
 * @author lishibang
 * @date 2017年8月17日 上午11:45:11
 * @version v1.0.0
 */
public class SimpleHibernateDao extends HibernateDao {

	private HibernateDbSessionFactory sessionFactory;

	public void setSessionFactory(HibernateDbSessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
		super.setSession(sessionFactory.getSession());
	}

	public <T> T get(Class<T> type, String id) {
		try {
			return super.selectOne(type, id);
		} catch (DataBaseException e) {
			e.printStackTrace();
		}
		return null;
	}

	public <T> boolean save(T t, boolean isCommit) {
		try {
			if (isCommit) {
				getSession().clear();
				getSession().beginTransaction();
			}
			super.insert(t);
			if (isCommit) {
				getSession().commit();
			}
			return true;
		} catch (DataBaseException e) {
			e.printStackTrace();
			try {
				getSession().rollback();
			} catch (DataBaseException e1) {
				e1.printStackTrace();
				return false;
			}
			return false;
		}
	}

	public <T> boolean saveAll(List<T> ts, boolean isCommit) {
		try {
			if (isCommit) {
				getSession().clear();
				getSession().beginTransaction();
			}
			super.insert(ts.toArray());
			if (isCommit) {
				getSession().commit();
			}
			return true;
		} catch (DataBaseException e) {
			e.printStackTrace();
			try {
				getSession().rollback();
			} catch (DataBaseException e1) {
				e1.printStackTrace();
				return false;
			}
			return false;
		}
	}

	public <T> boolean update(T t, boolean isCommit) {
		try {
			if (isCommit) {
				getSession().clear();
				getSession().beginTransaction();
			}
			super.update(t);
			if (isCommit) {
				getSession().commit();
			}
			return true;
		} catch (DataBaseException e) {
			e.printStackTrace();
			try {
				getSession().rollback();
			} catch (DataBaseException e1) {
				e1.printStackTrace();
				return false;
			}
			return false;
		}
	}

	public <T> boolean update(List<T> ts, boolean isCommit) {
		try {
			if (isCommit) {
				getSession().clear();
				getSession().beginTransaction();
			}
			super.update(ts.toArray());
			if (isCommit) {
				getSession().commit();
			}
			return true;
		} catch (DataBaseException e) {
			e.printStackTrace();
			try {
				getSession().rollback();
			} catch (DataBaseException e1) {
				e1.printStackTrace();
				return false;
			}
			return false;
		}
	}

	public <T> boolean delete(Class<T> type, String id, boolean isCommit) {
		try {
			if (isCommit) {
				getSession().clear();
				getSession().beginTransaction();
			}
			super.delete(type, id);
			if (isCommit) {
				getSession().commit();
			}
			return true;
		} catch (DataBaseException e) {
			e.printStackTrace();
			try {
				getSession().rollback();
			} catch (DataBaseException e1) {
				e1.printStackTrace();
				return false;
			}
			return false;
		}
	}
	public <T> boolean delete(Class<T> type,ICriteria condtion,boolean isCommit){
		try {
			if (isCommit) {
				getSession().clear();
				getSession().beginTransaction();
			}
			super.delete(type, condtion);
			if (isCommit) {
				getSession().commit();
			}
			return true;
		} catch (DataBaseException e) {
			e.printStackTrace();
			try {
				getSession().rollback();
			} catch (DataBaseException e1) {
				e1.printStackTrace();
				return false;
			}
			return false;
		}
	}

	public <T> boolean delete(Class<T> type, List<String> ids, boolean isCommit) {
		try {
			if (isCommit) {
				getSession().clear();
				getSession().beginTransaction();
			}
			super.delete(type, ids.toArray());
			if (isCommit) {
				getSession().commit();
			}
			return true;
		} catch (DataBaseException e) {
			e.printStackTrace();
			try {
				getSession().rollback();
			} catch (DataBaseException e1) {
				e1.printStackTrace();
				return false;
			}
			return false;
		}
	}

	public <T> List<T> query(Class<T> type, IQuery where) {
		try {
			return super.selectList(type, where);
		} catch (DataBaseException e) {
			e.printStackTrace();
			return null;
		}
	}
	
}
