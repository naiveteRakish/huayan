/**  
 *  设备存取服务
 * @Title: DeviceAccessImpl.java
 * @Package whayer.cloud.storage.component.device.deviceaccess.impl
 * @author Administrator
 * @date 2017年8月10日 上午10:35:23
 * @version v1.0.0
 */
package whayer.cloud.storage.component.device.deviceaccess.impl;

import java.util.List;

import whayer.cloud.framework.data.db.dao.HibernateDao;
import whayer.cloud.framework.data.db.dao.IDao;
import whayer.cloud.framework.data.db.dao.criteria.Criteria;
import whayer.cloud.framework.data.db.dao.criteria.Restrictions;
import whayer.cloud.framework.data.db.dao.datasource.DataBaseException;
import whayer.cloud.framework.data.db.dao.datasource.IDbSession;
import whayer.cloud.framework.data.db.dao.datasource.IDbSessionFactory;
import whayer.cloud.utility.core.BeanService;

import com.whayer.cloud.storage.business.base.data.QueryCondition;
import com.whayer.cloud.storage.business.base.data.QueryOperator;
import com.whayer.cloud.storage.component.device.deviceaccess.IDeviceAccess;
import com.whayer.cloud.storage.component.device.deviceaccess.beans.BaseDeviceInfoPojo;

/**
 * 设备存取服务
 * @ClassName: DeviceAccessImpl
 * @author Administrator
 * @date 2017年8月10日 上午10:35:23
 * @version v1.0.0
 * 
 */
public class DeviceAccessImpl implements IDeviceAccess {
	// session工厂
	private IDbSessionFactory factory;
	// 获取bean实例的接口
	private BeanService beanService;

	/**
	 * @return the factory
	 */
	public IDbSessionFactory getFactory() {
		return factory;
	}

	/**
	 * @param factory the factory to set
	 */
	public void setFactory(IDbSessionFactory factory) {
		this.factory = factory;
	}

	/**
	 * @return the beanService
	 */
	public BeanService getBeanService() {
		return beanService;
	}

	/**
	 * @param beanService the beanService to set
	 */
	public void setBeanService(BeanService beanService) {
		this.beanService = beanService;
	}

	@Override
	public boolean addDeviceInfo(BaseDeviceInfoPojo deviceInfo) {
		IDbSession session = null;
		boolean result = false;
		if (deviceInfo == null) {
			return result;
		}
		try {
			session = factory.getSession();
			IDao dao = beanService.getBean(HibernateDao.class);
			dao.setSession(session);
			session.clear();
			session.beginTransaction();
			dao.insert(deviceInfo);
			session.commit();
			result = true;
		} catch (DataBaseException e) {
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

	@Override
	public boolean addDeviceInfos(List<BaseDeviceInfoPojo> deviceInfos) {
		IDbSession session = null;
		boolean result = false;
		List<BaseDeviceInfoPojo> baseDeviceInfoPojos;
		if (deviceInfos == null || deviceInfos.size() == 0) {
			return result;
		}
		try {
			session = factory.getSession();
			IDao dao = beanService.getBean(HibernateDao.class);
			dao.setSession(session);
			session.beginTransaction();
			dao.insert(deviceInfos.toArray());
			session.commit();
			result = true;
		} catch (DataBaseException e) {
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

	@Override
	public boolean modifyDeviceInfo(BaseDeviceInfoPojo deviceInfo) {
		IDbSession session = null;
		boolean result = false;

		if (deviceInfo == null) {
			return result;
		}
		try {
			session = factory.getSession();
			session.clear();
			session.beginTransaction();
			IDao dao = beanService.getBean(HibernateDao.class);
			dao.setSession(session);
			dao.update(deviceInfo);
			session.commit();
			result = true;
		} catch (DataBaseException e) {
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

	@Override
	public boolean deleteDeviceInfo(List<QueryCondition> condition) {

		IDbSession session = null;
		boolean result = false;

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
						where.add(Restrictions.eq(BaseDeviceInfoPojo.class, c.getKey(), c.getValue()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LIKE) {
						where.add(Restrictions.like(BaseDeviceInfoPojo.class, c.getKey(), c.getValue().toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.IN) {
						where.add(Restrictions.in(BaseDeviceInfoPojo.class, c.getKey(), c.getValue().toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.GREATER) {
						where.add(Restrictions.gt(BaseDeviceInfoPojo.class, c.getKey(), c.getValue().toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.GREATER_OR_EQUAL) {
						where.add(Restrictions.ge(BaseDeviceInfoPojo.class, c.getKey(), c.getValue().toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LESS) {
						where.add(Restrictions.lt(BaseDeviceInfoPojo.class, c.getKey(), c.getValue().toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LESS_OR_EQUAL) {
						where.add(Restrictions.le(BaseDeviceInfoPojo.class, c.getKey(), c.getValue().toString()));
					} else {
						return false;
					}
				}
			}
		}
		try {
			session = factory.getSession();
			session.clear();
			session.beginTransaction();
			IDao dao = beanService.getBean(HibernateDao.class);
			dao.setSession(session);
			dao.delete(BaseDeviceInfoPojo.class, where);
			session.commit();
			result = true;
		} catch (DataBaseException e) {
			result = false;
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

	@Override
	public int queryDeviceInfoSize(List<QueryCondition> condition) {
		IDbSession session = null;
		int result = -1;
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
						where.add(Restrictions.eq(BaseDeviceInfoPojo.class, c.getKey(), c.getValue()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LIKE) {
						where.add(Restrictions.like(BaseDeviceInfoPojo.class, c.getKey(), c.getValue().toString()));
					} else {
						// 还没有这个比较条件
						return result;
					}
				}
			}

		}

		try {
			session = factory.getSession();
			IDao dao = beanService.getBean(HibernateDao.class);
			dao.setSession(session);
			Long l = dao.count(BaseDeviceInfoPojo.class, where);
			result = l == null ? -1 : l.intValue();
		} catch (DataBaseException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public List<BaseDeviceInfoPojo> queryDeviceInfo(int start, int end, List<QueryCondition> condition) {
		IDbSession session = null;
		List<BaseDeviceInfoPojo> result = null;
		Criteria where = new Criteria();
		// 检验输入是否合法
		if (start > end || (start < 0 && start != -1) || (end < 1 && end != -1)) {
			return result;
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
						where.add(Restrictions.eq(BaseDeviceInfoPojo.class, c.getKey(), c.getValue()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LIKE) {
						where.add(Restrictions.like(BaseDeviceInfoPojo.class, c.getKey(), c.getValue().toString()));
					} else {
						// 还没有这个比较条件
						return result;
					}
				}
			}

		}

		try {
			session = factory.getSession();
			IDao dao = beanService.getBean(HibernateDao.class);
			dao.setSession(session);
			result = dao.selectList(BaseDeviceInfoPojo.class, where);
		} catch (DataBaseException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean modifyDeviceInfos(List<BaseDeviceInfoPojo> deviceInfo) {
		IDbSession session = null;
		boolean result = false;

		if (deviceInfo == null || deviceInfo.size() == 0) {
			return result;
		}
		try {
			session = factory.getSession();
			session.clear();
			session.beginTransaction();
			IDao dao = beanService.getBean(HibernateDao.class);
			dao.setSession(session);
			dao.update(deviceInfo.toArray());
			session.commit();
			result = true;
		} catch (DataBaseException e) {
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
}
