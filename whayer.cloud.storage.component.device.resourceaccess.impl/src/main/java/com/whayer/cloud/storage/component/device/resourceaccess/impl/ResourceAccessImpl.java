package com.whayer.cloud.storage.component.device.resourceaccess.impl;

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
import com.whayer.cloud.storage.component.device.resourceaccess.IResourceAccess;
import com.whayer.cloud.storage.component.device.resourceaccess.beans.ResourcePojo;

/**
 * 前段区域(普通)存取接口
 * @ClassName: IDeviceAccess
 * @author 
 * @date 2017年8月15日 下午15:07:01
 * @version v1.0.0
 * 
 */
public class ResourceAccessImpl implements IResourceAccess {
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

	/**
	 * 添加前端区域数据
	 * @Title: addAreaInfo
	 * @param deviceInfo	设备数据对象
	 * @return boolean		是否成功添加
	 * @see 
	 * @throws
	 * @author 
	 */
	public boolean addResource(ResourcePojo resourcePojo) {
		IDbSession session = null;
		boolean result = false;
		if (resourcePojo == null) {
			return result;
		}

		try {
			session = factory.getSession();
			IDao dao = beanService.getBean(HibernateDao.class);
			dao.setSession(session);
			session.beginTransaction();
			dao.insert(resourcePojo);
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

	/**
	 * 批量添加前端设备数据
	 * @Title: addAreaInfos
	 * @param deviceInfos	设备数据对象集
	 * @return boolean	是否成功添加
	 * @see 
	 * @throws
	 * @author 
	 */
	public boolean addResources(List<ResourcePojo> resourcePojos) {
		IDbSession session = null;
		boolean result = false;

		if (resourcePojos == null || resourcePojos.size() == 0) {
			return result;
		}

		try {
			session = factory.getSession();
			IDao dao = beanService.getBean(HibernateDao.class);
			dao.setSession(session);
			session.beginTransaction();
			dao.insert(resourcePojos.toArray());
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

	/**
	 * 修改设备数据对象
	 * @Title: modifyAreaInfo
	 * @param AreaInfo	设备数据对象
	 * @return boolean		是否成功修改
	 * @see 
	 * @throws
	 * @author 
	 */
	public boolean modifyResource(ResourcePojo resourcePojo) {
		IDbSession session = null;
		boolean result = false;

		if (resourcePojo == null) {
			return result;
		}

		try {
			session = factory.getSession();
			session.clear();
			session.beginTransaction();
			IDao dao = beanService.getBean(HibernateDao.class);
			dao.setSession(session);
			dao.update(resourcePojo);
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

	/**
	 * 根据条件删除设备对象
	 * @Title: deleteAreaInfo
	 * @param condition	条件
	 * @return boolean 是否成功删除
	 * @see 
	 * @throws
	 * @author 
	 */
	public boolean deleteResource(List<QueryCondition> condition) {
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
						where.add(Restrictions.eq(ResourcePojo.class, c.getKey(), c.getValue()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LIKE) {
						where.add(Restrictions.like(ResourcePojo.class, c.getKey(), c.getValue().toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.IN) {
						where.add(Restrictions.in(ResourcePojo.class, c.getKey(), c.getValue().toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.GREATER) {
						where.add(Restrictions.gt(ResourcePojo.class, c.getKey(), c.getValue().toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.GREATER_OR_EQUAL) {
						where.add(Restrictions.ge(ResourcePojo.class, c.getKey(), c.getValue().toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LESS) {
						where.add(Restrictions.lt(ResourcePojo.class, c.getKey(), c.getValue().toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LESS_OR_EQUAL) {
						where.add(Restrictions.le(ResourcePojo.class, c.getKey(), c.getValue().toString()));
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
			List<ResourcePojo> resourcePojos = dao.selectList(ResourcePojo.class, where);
			dao.delete(ResourcePojo.class, where);
			// 批量删除其子资源
			if (resourcePojos != null && resourcePojos.size() != 0) {
				for (int i = 0; i < resourcePojos.size(); i++) {
					Criteria childWhere = new Criteria();
					where.add(Restrictions.eq(ResourcePojo.class, "parentId", resourcePojos.get(0).getId()));
					dao.delete(ResourcePojo.class, childWhere);
				}
			}
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

	/**
	 * 获取满足条件的设备数据对象记录数
	 * @Title: queryAreaInfoSize
	 * @param condition		查询条件
	 * @return int			符合条件的设备记录数
	 * @see 
	 * @throws
	 * @author 
	 */
	public Long queryResourceSize(List<QueryCondition> condition) {
		IDbSession session = null;
		Long result = null;
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
						where.add(Restrictions.eq(ResourcePojo.class, c.getKey(), c.getValue()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LIKE) {
						where.add(Restrictions.like(ResourcePojo.class, c.getKey(), c.getValue().toString()));
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
			result = dao.count(ResourcePojo.class, where);
		} catch (DataBaseException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 分页查询设备数据对象记录
	 * @Title: queryAreaINfo
	 * @param start		开始位置
	 * @param end  		结束位置
	 * @param condition	条件
	 * @return List<BaseDeviceInfo>
	 * @see 
	 * @throws
	 * @author 
	 */
	public List<ResourcePojo> queryResource(int start, int end, List<QueryCondition> condition) {
		IDbSession session = null;
		List<ResourcePojo> result = null;
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
						where.add(Restrictions.eq(ResourcePojo.class, c.getKey(), c.getValue()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LIKE) {
						where.add(Restrictions.like(ResourcePojo.class, c.getKey(), c.getValue().toString()));
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
			result = dao.selectList(ResourcePojo.class, where);
		} catch (DataBaseException e) {
			e.printStackTrace();
		}
		return result;
	}

	@Override
	public boolean modifyResources(List<ResourcePojo> resourcePojos) {
		IDbSession session = null;
		boolean result = false;

		if (resourcePojos == null || resourcePojos.size() == 0) {
			return result;
		}

		try {
			session = factory.getSession();
			session.clear();
			session.beginTransaction();
			IDao dao = beanService.getBean(HibernateDao.class);
			dao.setSession(session);
			dao.update(resourcePojos.toArray());
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
}
