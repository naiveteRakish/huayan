/**  
 * 元数据管理
 * @Title: MetaTimingCacheServiceImpl.java
 * @Package com.whayer.cloud.storage.component.foundation.meta.impl
 * @author Administrator
 * @date 2017年8月16日 上午11:37:52
 * @version v1.0.0
 */
package com.whayer.cloud.storage.component.foundation.meta.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import whayer.cloud.framework.data.db.dao.IDao;
import whayer.cloud.framework.data.db.dao.datasource.DataBaseException;
import whayer.cloud.framework.data.db.dao.datasource.IDbSession;
import whayer.cloud.framework.data.db.dao.datasource.IDbSessionFactory;
import whayer.cloud.utility.core.BeanService;

import com.whayer.cloud.component.objectcache.IObjectCacheService;
import com.whayer.cloud.component.objectcache.OCSCondition;
import com.whayer.cloud.storage.component.foundation.meta.IMetaCacheService;
import com.whayer.cloud.storage.component.foundation.meta.IMetaTimingCacheService;

/**
 * 定时同步器  metaCacheService属性必须注入否则会导致定时执行失败
 * @ClassName: MetaTimingCacheServiceImpl
 * @author Administrator
 * @date 2017年8月16日 上午11:37:52
 * @version v1.0.0
 * 
 */
public class MetaTimingCacheServiceImpl implements IMetaTimingCacheService {

	private int time;
	// session工厂
	private IDbSessionFactory factory;
	// 获取bean实例的接口
	private BeanService beanService;
	private LinkedList<Object> addLinkedBlockingQueue;
	private LinkedList<Object> updateLinkedBlockingQueue;
	private LinkedList<Object> deleteLinkedBlockingQueue;
	private Long delay = 0L;
	private Long period = 12L;
	private TimeUnit timeUnit = TimeUnit.HOURS;
	private IMetaCacheService metaCacheService;
	private ScheduledExecutorService service;
	private IObjectCacheService objectCacheService;

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
	 * @return the addLinkedBlockingQueue
	 */

	@Override
	public LinkedList<Object> getAddLinkedBlockingQueue() {
		return addLinkedBlockingQueue;
	}

	/**
	 * @return the service
	 */
	public ScheduledExecutorService getService() {
		return service;
	}

	/**
	 * @param service the service to set
	 */
	public void setService(ScheduledExecutorService service) {
		this.service = service;
	}

	/**
	 * @param addLinkedBlockingQueue the addLinkedBlockingQueue to set
	 */
	public void setAddLinkedBlockingQueue(LinkedList<Object> addLinkedBlockingQueue) {
		this.addLinkedBlockingQueue = addLinkedBlockingQueue;
	}

	/**
	 * @return the updateLinkedBlockingQueue
	 */
	@Override
	public LinkedList<Object> getUpdateLinkedBlockingQueue() {
		return updateLinkedBlockingQueue;
	}

	/**
	 * @param updateLinkedBlockingQueue the updateLinkedBlockingQueue to set
	 */
	public void setUpdateLinkedBlockingQueue(LinkedList<Object> updateLinkedBlockingQueue) {
		this.updateLinkedBlockingQueue = updateLinkedBlockingQueue;
	}

	/**
	 * @return the deleteLinkedBlockingQueue
	 */
	@Override
	public LinkedList<Object> getDeleteLinkedBlockingQueue() {
		return deleteLinkedBlockingQueue;
	}

	/**
	 * @param deleteLinkedBlockingQueue the deleteLinkedBlockingQueue to set
	 */
	public void setDeleteLinkedBlockingQueue(LinkedList<Object> deleteLinkedBlockingQueue) {
		this.deleteLinkedBlockingQueue = deleteLinkedBlockingQueue;
	}

	/**
	 * @return the delay
	 */
	public Long getDelay() {
		return delay;
	}

	/**
	 * @param delay the delay to set
	 */
	public void setDelay(Long delay) {
		this.delay = delay;
	}

	/**
	 * @return the period
	 */
	public Long getPeriod() {
		return period;
	}

	/**
	 * @param period the period to set
	 */
	public void setPeriod(Long period) {
		this.period = period;
	}

	/**
	 * @return the timeUnit
	 */
	public TimeUnit getTimeUnit() {
		return timeUnit;
	}

	/**
	 * @param timeUnit the timeUnit to set
	 */
	public void setTimeUnit(TimeUnit timeUnit) {
		this.timeUnit = timeUnit;
	}

	/**
	 * @return the metaCacheService
	 */
	public IMetaCacheService getMetaCacheService() {
		return metaCacheService;
	}

	/**
	 * @param metaCacheService the metaCacheService to set
	 */
	public void setMetaCacheService(IMetaCacheService metaCacheService) {
		this.metaCacheService = metaCacheService;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void init() {
		objectCacheService = beanService.getBean(IObjectCacheService.class);
		if (metaCacheService == null || factory == null || beanService == null || objectCacheService == null) {
			throw new RuntimeException("元数据管理缓存同步器所依赖的同步检查定时器, property has null value");
		}
		time = metaCacheService.getTime();
		// 需要测试 添加 和 获取并移除 还有读取长度 会不会出现线程问题 因为这是线程不安全的
		// addLinkedBlockingQueue = (LinkedList<Object>)
		// Collections.synchronizedList(addLinkedBlockingQueue);
		// updateLinkedBlockingQueue = (LinkedList<Object>)
		// Collections.synchronizedList(updateLinkedBlockingQueue);
		// deleteLinkedBlockingQueue = (LinkedList<Object>)
		// Collections.synchronizedList(deleteLinkedBlockingQueue);
		addLinkedBlockingQueue = addLinkedBlockingQueue == null ? new LinkedList() : addLinkedBlockingQueue;
		updateLinkedBlockingQueue = updateLinkedBlockingQueue == null ? new LinkedList() : updateLinkedBlockingQueue;
		deleteLinkedBlockingQueue = deleteLinkedBlockingQueue == null ? new LinkedList() : deleteLinkedBlockingQueue;
		service = service == null ? Executors.newSingleThreadScheduledExecutor() : service;
		initDddLinkedBlockingQueueTask();
		initUpadateLinkedBlockingQueueTask();
		initDeleteLinkedBlockingQueueTask();
	}

	/**
	 *初始化任务
	 * @Title: initDeleteLinkedBlockingQueueTask void
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private void initDeleteLinkedBlockingQueueTask() {
		service.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {

				if (!deleteLinkedBlockingQueue.isEmpty()) {
					for (int i = 0; i < deleteLinkedBlockingQueue.size(); i++) {
						Object item = deleteLinkedBlockingQueue.poll();
						if (item != null) {
							// 如果该元素存在 则进行删除
							Map<Integer, ?> map = getIndexMap(item);
							if (!(map == null || map.isEmpty())) {

								metaCacheService.deleteMetaCache(item);
							}
						}
					}
				}
			}

		}, delay, period, timeUnit);

	}

	private Map<Integer, ?> getIndexMap(Object itme) {
		List<OCSCondition> ocsConditions = new ArrayList<OCSCondition>();
		// 因为传入类型为泛型 并且没有基类 只能制定每个元数据对象 id 都是 "id" 名字
		try {
			ocsConditions.add(new OCSCondition("id", OCSCondition.OCSOperator.EQUAL, itme.getClass().getMethod("getId")
					.invoke(itme)));
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}

		String key = metaCacheService.getRedisKey(itme.getClass());
		int i = 0;
		while (i != time) {
			i++;
			try {
				return objectCacheService.indexListObjectItem(key, itme.getClass(), ocsConditions);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		throw new RuntimeException("redis请求异常");
	}

	/**
	 * 初始化任务
	 * @Title: initUpadateLinkedBlockingQueueTask void
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private void initUpadateLinkedBlockingQueueTask() {
		service.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				if (!updateLinkedBlockingQueue.isEmpty()) {
					for (int i = 0; i < updateLinkedBlockingQueue.size(); i++) {
						Object item = updateLinkedBlockingQueue.poll();
						if (item != null)

							// 查询一次 保证是最新的数据
							if ((item = directQuery(item)) != null) {
								metaCacheService.syncMetaCache(item);
							}
					}

				}
			}
		}, delay, period, timeUnit);

	}

	/**
	 * 初始化任务
	 * @Title: initDddLinkedBlockingQueueTask void
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private void initDddLinkedBlockingQueueTask() {
		service.scheduleAtFixedRate(new Runnable() {

			@Override
			public void run() {
				if (!addLinkedBlockingQueue.isEmpty()) {
					for (int i = 0; i < addLinkedBlockingQueue.size(); i++) {
						Object item = addLinkedBlockingQueue.poll();
						if (item != null)
							// 查询一次 保证是最新的数据
							if ((item = directQuery(item)) != null)
								metaCacheService.syncMetaCache(item);
					}
				}
			}
		}, delay, period, timeUnit);

	}

	private <T> T directQuery(T t) {
		IDbSession session = factory.getSession();
		IDao dao = beanService.getBean("hibernateDao");
		dao.setSession(session);
		try {
			t = dao.selectOne(t.getClass(), (String) t.getClass().getMethod("getId").invoke(t));
		} catch (DataBaseException | IllegalAccessException | IllegalArgumentException | InvocationTargetException
				| NoSuchMethodException | SecurityException e) {
			e.printStackTrace();
			return null;
		}
		return t;
	}

}
