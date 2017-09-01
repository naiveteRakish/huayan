package com.whayer.cloud.storage.component.foundation.meta.impl;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import whayer.cloud.framework.data.db.dao.IDao;
import whayer.cloud.framework.data.db.dao.criteria.Criteria;
import whayer.cloud.framework.data.db.dao.criteria.Restrictions;
import whayer.cloud.framework.data.db.dao.datasource.DataBaseException;
import whayer.cloud.framework.data.db.dao.datasource.IDbSession;
import whayer.cloud.framework.data.db.dao.datasource.IDbSessionFactory;
import whayer.cloud.utility.core.BeanService;
import whayer.cloud.utility.core.JavaClass;
import whayer.component.core.IService;

import com.whayer.cloud.component.objectcache.IOCSStatusNotifyCallback;
import com.whayer.cloud.component.objectcache.IObjectCacheService;
import com.whayer.cloud.component.objectcache.OCSCondition;
import com.whayer.cloud.component.objectcache.OCSStatus;
import com.whayer.cloud.storage.business.base.data.QueryCondition;
import com.whayer.cloud.storage.business.base.data.QueryOperator;
import com.whayer.cloud.storage.component.foundation.meta.IMetaCacheService;
import com.whayer.cloud.storage.component.foundation.meta.IMetaDataService;
import com.whayer.cloud.storage.component.foundation.meta.IMetaTimingCacheService;

/**
 * 
 * 这是一个元数据管理服务(如果多个线程使用单一实例进行调用，是没有问题的，但是如果一个线程调用了stop方法，那么整个服务就会停止，另外的线程也将无法使用该服务。除非再次调用start方法启动本服务)
 * 所有的元数据 主键 必须命名为 "id"
 * 
 * @ClassName: MetaDataManagerImpl
 * @author Administrator
 * @date 2017年8月9日 下午2:31:44
 * @version v1.0.0
 *
 */
public class MetaDataServiceImpl implements IMetaDataService, IService {

	// session工厂
	private IDbSessionFactory factory;
	// 获取bean实例的接口
	private BeanService beanService;
	// redis服务接口
	private IObjectCacheService objectCacheService;
	// 同步redis的线程池
	private ThreadPoolExecutor threadPoolExecutor;
	// 该服务接口可接收的对象类型
	private List<String> typeList;
	// redis连接状态
	public static boolean redisStatus;
	// redis订阅id
	private String subscribeId;
	// 线程池核心线程数
	private int corePoolSize;
	// redis 任务同步服务
	private IMetaCacheService iMetaCacheService;
	private IMetaTimingCacheService metaTimingCacheService;
	// key前缀 可配置
	private final String daoId = "hibernateDao";

	/**
	 * @return the metaTimingCacheService
	 */
	public IMetaTimingCacheService getMetaTimingCacheService() {
		return metaTimingCacheService;
	}

	/**
	 * @param metaTimingCacheService the metaTimingCacheService to set
	 */
	public void setMetaTimingCacheService(IMetaTimingCacheService metaTimingCacheService) {
		this.metaTimingCacheService = metaTimingCacheService;
	}

	/**
	 * @return the redisStatus
	 */
	public boolean isRedisStatus() {
		return redisStatus;
	}

	/**
	 * @param redisStatus the redisStatus to set
	 */
	@SuppressWarnings("static-access")
	public void setRedisStatus(boolean redisStatus) {
		this.redisStatus = redisStatus;
	}

	/**
	 * @return the typeList
	 */
	public List<String> getTypeList() {
		return typeList;
	}

	/**
	 * @param typeList the typeList to set
	 */
	public void setTypeList(List<String> typeList) {
		this.typeList = typeList;
	}

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
	 * @return the objectCacheService
	 */
	public IObjectCacheService getObjectCacheService() {
		return objectCacheService;
	}

	/**
	 * @param objectCacheService the objectCacheService to set
	 */
	public void setObjectCacheService(IObjectCacheService objectCacheService) {
		this.objectCacheService = objectCacheService;
	}

	/**
	 * @return the executorService
	 */
	public ThreadPoolExecutor getExecutorService() {
		return threadPoolExecutor;
	}

	/**
	 * @param threadPoolExecutor the threadPoolExecutor to set
	 */
	public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
		this.threadPoolExecutor = threadPoolExecutor;
	}

	/**
	 * @return the iMetaCacheService
	 */
	public IMetaCacheService getiMetaCacheService() {
		return iMetaCacheService;
	}

	/**
	 * @param iMetaCacheService the iMetaCacheService to set
	 */
	public void setiMetaCacheService(IMetaCacheService iMetaCacheService) {
		this.iMetaCacheService = iMetaCacheService;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> boolean addMetaData(final T item) {
		IDbSession session = null;
		boolean result = false;
		if (item == null) {
			return result;
		}
		// 校验类型
		if (checkType(item.getClass())) {
			return result;

		}

		try {
			session = factory.getSession();
			IDao dao = beanService.getBean(daoId);
			session.beginTransaction();
			dao.setSession(session);
			dao.insert(item);
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
		if (result && redisStatus) {
			// 操作redis
			iMetaCacheService.syncMetaCache(item);
		}

		return result;
	}

	@SuppressWarnings("unused")
	@Override
	public <T> boolean addMetaDatas(final List<T> items) {

		IDbSession session = null;
		boolean result = false;

		if (items == null || items.size() == 0) {
			return result;
		}

		for (int i = 0; i < items.size(); i++) {
			// 校验类型
			if (checkType(items.get(i).getClass())) {
				return result;
			}
			// 因为这里说校验第一个就可以了所以跳出 ， 如果后面需要校验所有元素 注释break 就ok
			break;
		}

		try {
			session = factory.getSession();
			IDao dao = beanService.getBean(daoId);
			dao.setSession(session);
			session.beginTransaction();
			dao.insert(items.toArray());
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
		if (result && redisStatus) {
			// 操作redis
			iMetaCacheService.syncMetaCache(items);
		}

		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public <T> boolean modifyMetaData(final T item) {
		IDbSession session = null;
		boolean result = false;

		if (item == null) {
			return result;
		}
		// 校验类型
		if (checkType(item.getClass())) {
			return result;
		}

		try {
			session = factory.getSession();
			session.clear();
			session.beginTransaction();
			IDao dao = beanService.getBean(daoId);
			dao.setSession(session);
			dao.update(item);
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
		if (result && redisStatus) {
			// 操作redis
			iMetaCacheService.syncMetaCache(item);
		}

		return result;
	}

	@Override
	public <T> boolean deleteMetaData(final Class<T> type, final String id) {
		IDbSession session = null;
		boolean result = false;
		if (type == null || id == null || "".equals(id.trim())) {
			return result;
		}

		// 校验类型
		if (checkType(type)) {
			return result;
		}

		try {
			// dao操作
			session = factory.getSession();
			session.clear();
			session.beginTransaction();
			IDao dao = beanService.getBean(daoId);
			dao.setSession(session);
			dao.delete(type, id);
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

		if (result && redisStatus) {
			// 操作redis
			T item = null;
			// 再次声明 元数据的主键名 必须是 "id"
			try {
				// item = type.newInstance();
				// type.getMethod("setId").invoke(item, id);
				item = type.newInstance();
				Field field = JavaClass.getAllFields(type, "id");
				field.setAccessible(true);
				field.set(item, id);
			} catch (IllegalArgumentException | SecurityException | InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
			iMetaCacheService.deleteMetaCache(item);
		}

		return result;
	}

	@Override
	public <T> boolean deleteMetaData(final Class<T> type) {
		IDbSession session = null;
		boolean result = false;
		if (type == null) {
			return result;
		}
		// 校验类型
		if (checkType(type)) {
			return result;
		}

		try {
			session = factory.getSession();
			session.clear();
			session.beginTransaction();
			IDao dao = beanService.getBean(daoId);
			dao.setSession(session);
			dao.delete(type, (Criteria) null);
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
		if (result && redisStatus) {
			// 操作redis
			try {
				iMetaCacheService.deleteMetaCache(type.newInstance());
			} catch (InstantiationException | IllegalAccessException e) {
				e.printStackTrace();
			}
		}
		return result;
	}

	@Override
	public <T> T getMetaData(Class<T> type, String id) {
		IDbSession session = null;
		T result = null;
		final String key = iMetaCacheService.getRedisKey(type);
		// 数据是否来自数据库
		boolean isDataBase = false;
		if (type == null || id == null || "".equals(id.trim())) {
			return result;
		}
		// 校验类型
		if (checkType(type)) {
			return result;
		}
		// 查询缓存
		if (redisStatus) {

			List<OCSCondition> ocsConditions = new ArrayList<OCSCondition>();
			ocsConditions.add(new OCSCondition("id", OCSCondition.OCSOperator.EQUAL, id));
			Map<Integer, T> map = objectCacheService.indexListObjectItem(key, type, ocsConditions);
			if (map != null && !map.isEmpty()) {
				for (Map.Entry<Integer, T> entry : map.entrySet()) {
					// 按照类型和id查询 结果只会有一条
					result = entry.getValue();
					break;
				}
			}
		}

		if (result == null) {
			try {
				session = factory.getSession();
				IDao dao = beanService.getBean(daoId);
				dao.setSession(session);
				result = dao.selectOne(type, id);
				isDataBase = true;
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
			if (result != null && redisStatus && isDataBase) {
				// 操作redis
				iMetaCacheService.syncMetaCache(result);
			}
		}

		return result;
	}

	@Override
	public <T> List<T> getMetaData(Class<T> type, List<QueryCondition> condition) {
		IDbSession session = null;
		List<T> result = null;
		final String key = iMetaCacheService.getRedisKey(type);
		// 数据是否来自数据库
		boolean isDataBase = false;
		if (type == null) {
			return result;
		}
		if (checkType(type)) {
			return result;
		}

		Criteria where = null;
		List<OCSCondition> ocsConditions = null;
		if (condition != null && condition.size() > 0) {
			ocsConditions = new ArrayList<OCSCondition>();
			// queryCondition中任一属性为null，则丢弃该项匹配条件
			for (QueryCondition c : condition) {
				if (c != null && c.getKey() != null && !c.getKey().equals("") && c.getQueryOperator() != null
						&& c.getValue() != null) {
					if (where == null) {
						where = new Criteria();
					}
					if (c.getQueryOperator().getOperator() == QueryOperator.Operator.EQUAL) {
						where.add(Restrictions.eq(type, c.getKey(), c.getValue()));
						ocsConditions.add(new OCSCondition(c.getKey(), OCSCondition.OCSOperator.EQUAL, c.getValue()
								.toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LIKE) {
						where.add(Restrictions.like(type, c.getKey(), c.getValue().toString()));
						ocsConditions.add(new OCSCondition(c.getKey(), OCSCondition.OCSOperator.LIKE, c.getValue()
								.toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.GREATER) {
						where.add(Restrictions.gt(type, c.getKey(), c.getValue().toString()));
						ocsConditions.add(new OCSCondition(c.getKey(), OCSCondition.OCSOperator.GREATER, c.getValue()
								.toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.GREATER_OR_EQUAL) {
						where.add(Restrictions.ge(type, c.getKey(), c.getValue().toString()));
						ocsConditions.add(new OCSCondition(c.getKey(), OCSCondition.OCSOperator.GREATER_OR_EQUAL, c
								.getValue().toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LESS) {
						where.add(Restrictions.lt(type, c.getKey(), c.getValue().toString()));
						ocsConditions.add(new OCSCondition(c.getKey(), OCSCondition.OCSOperator.LESS, c.getValue()
								.toString()));
					} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LESS_OR_EQUAL) {
						where.add(Restrictions.le(type, c.getKey(), c.getValue().toString()));
						ocsConditions.add(new OCSCondition(c.getKey(), OCSCondition.OCSOperator.LESS_OR_EQUAL, c
								.getValue().toString()));
					} else {
						return result;
					}

				}
			}

		}
		if (redisStatus && where != null) {
			// 按条件查询缓存
			Map<Integer, T> map = objectCacheService.indexListObjectItem(key, type, ocsConditions);
			result = new ArrayList<T>();
			if (map != null && !map.isEmpty()) {
				for (Map.Entry<Integer, T> entry : map.entrySet()) {
					// 封装结果
					result.add(entry.getValue());
				}
			}
		} else if (redisStatus) {
			// 获取整个业务链表对象
			result = objectCacheService.getListObject(key, type);
		}

		if (result == null || result.size() == 0) {

			try {
				session = factory.getSession();
				IDao dao = beanService.getBean(daoId);
				dao.setSession(session);
				result = dao.selectList(type, where);
				isDataBase = true;
			} catch (DataBaseException e) {
				e.printStackTrace();
			}
			if (result != null && result.size() != 0 && isDataBase && redisStatus) {
				// 操作redis
				iMetaCacheService.syncMetaCache(result);
			}
		}

		return result;
	}

	/**
	 * 检查类型是否合法
	 * @Title: checkType
	 * @param type
	 * @return boolean 返回true 表示非法， false表示合法
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private <T> boolean checkType(Class<T> type) {
		boolean boo = true;

		for (String typeItem : typeList) {
			if ((typeItem.equals(type.getName()))) {
				boo = false;
				break;
			}
		}
		return boo;
	}

	/**
	 * 服务启动
	 */
	@Override
	public synchronized void start() {

		// 线程池初始化
		if (threadPoolExecutor.getCorePoolSize() != 0) {
			corePoolSize = threadPoolExecutor.getCorePoolSize();
		} else {
			threadPoolExecutor.setCorePoolSize(corePoolSize);
		}

		((IService) this.objectCacheService).start();

		// 订阅redis通知
		subscribeId = this.objectCacheService.subscribeStatus(new IOCSStatusNotifyCallback() {

			@Override
			public void notifyStatus(OCSStatus status) {
				if (OCSStatus.OFF_LINE == status) {
					// 下线 清空任务队列
					redisStatus = false;
					threadPoolExecutor.getQueue().clear();
					// 减少多余线程回收资源
					threadPoolExecutor.setCorePoolSize(0);
				} else {
					// 上线
					redisStatus = true;
					initListObject();
					threadPoolExecutor.setCorePoolSize(corePoolSize);
				}
			}

		});
		redisStatus = initListObject();
	}

	/**
	 * 服务关闭
	 */
	@Override
	public synchronized void stop() {
		redisStatus = false;
		// 取消订阅
		this.objectCacheService.unsubscribeStatus(subscribeId);
		threadPoolExecutor.shutdown();
		metaTimingCacheService.getService().shutdown();
		((IService) this.objectCacheService).stop();

	}

	/**
	 * 将数据库所有元数据表缓存到redis中待用
	 * @throws ClassNotFoundException 
	 * @Title: initList void
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private boolean initListObject() {

		// TODO 将初始化失败的添加到失败列表，每次进行redis 操作 先判断当前类型是否存在与失败列表 如果存在 则进行一次初始化操作，
		// 初始化成功 从失败列表删除 初始化失败跳过同步
		for (String type : typeList) {
			try {
				if (!objectCacheService.saveListObject(iMetaCacheService.getRedisKey(Class.forName(type)),
						this.getMetaData(Class.forName(type)))) {
					return false;
				}
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}
		}
		return true;
	}

	/**
	 * 初始化同步对象
	 * @Title: getMetaData
	 * @param forName
	 * @return List<Object>
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private <T> List<T> getMetaData(Class<T> type) {
		IDbSession session = null;
		List<T> result = new ArrayList<T>();
		if (type == null) {
			return result;
		}
		if (checkType(type)) {
			return result;
		}
		try {
			session = factory.getSession();
			IDao dao = beanService.getBean(daoId);
			dao.setSession(session);
			result = dao.selectList(type, (Criteria) null);
		} catch (DataBaseException e) {
			e.printStackTrace();
		}
		return result;
	}
}
