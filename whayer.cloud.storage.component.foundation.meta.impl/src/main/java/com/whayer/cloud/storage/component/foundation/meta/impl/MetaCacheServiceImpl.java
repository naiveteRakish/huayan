/**  
 * 元数据同步器
 * @Title: MetaCacheServiceImpl.java
 * @Package com.whayer.cloud.storage.component.foundation.meta.impl
 * @author Administrator
 * @date 2017年8月14日 下午3:35:25
 * @version v1.0.0
 */
package com.whayer.cloud.storage.component.foundation.meta.impl;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;

import com.whayer.cloud.component.objectcache.IObjectCacheService;
import com.whayer.cloud.component.objectcache.OCSCondition;
import com.whayer.cloud.storage.component.foundation.meta.IMetaCacheService;
import com.whayer.cloud.storage.component.foundation.meta.IMetaTimingCacheService;

/**
 * 元数据缓存接口实现类  如果metaTimingCacheService实例为null，则该同步器不会捕获失败的元素，进行定时同步操作
 * @ClassName: MetaCacheServiceImpl
 * @author Administrator
 * @date 2017年8月14日 下午3:35:25
 * @version v1.0.0
 * 
 */
public class MetaCacheServiceImpl implements IMetaCacheService {

	// 重试次数
	private int time = 10;
	// redis服务接口
	private IObjectCacheService objectCacheService;
	// 同步redis的线程池
	private ThreadPoolExecutor threadPoolExecutor;
	// key前缀
	private String redisKeyPrefix = "COM.WHAYER.CLOUD.STORAGE.COMPONENT.FOUNDATION.META.IMPL.METADATAMANAGERIMPL";
	// 元素同步失败重试定时器 （注入对象则开启 否则不使用定时调度）
	private IMetaTimingCacheService metaTimingCacheService;

	/**
	 * @return the time
	 */
	public int getTime() {
		return time;
	}

	/**
	 * @param time the time to set
	 */
	public void setTime(int time) {
		this.time = time;
	}

	/**
	 * @return the redisKeyPrefix
	 */
	public String getRedisKeyPrefix() {
		return redisKeyPrefix;
	}

	/**
	 * @param redisKeyPrefix the redisKeyPrefix to set
	 */
	public void setRedisKeyPrefix(String redisKeyPrefix) {
		this.redisKeyPrefix = redisKeyPrefix;
	}

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
	 * @return the threadPoolExecutor
	 */
	public ThreadPoolExecutor getThreadPoolExecutor() {
		return threadPoolExecutor;
	}

	/**
	 * @param threadPoolExecutor the threadPoolExecutor to set
	 */
	public void setThreadPoolExecutor(ThreadPoolExecutor threadPoolExecutor) {
		this.threadPoolExecutor = threadPoolExecutor;
	}

	// bean 初始化检查
	public void checkBean() {
		if (objectCacheService == null) {
			throw new RuntimeException("redis缓存服务实例为null!");
		}
		if (threadPoolExecutor == null) {
			throw new RuntimeException("redis缓存同步器线程池实例为null!");
		}
	}

	public void execute(Runnable run) {
		if (MetaDataServiceImpl.redisStatus)
			threadPoolExecutor.execute(run);
	}

	@Override
	public <T> void syncMetaCache(final T item) {
		execute(new Runnable() {
			@Override
			public void run() {
				List<OCSCondition> ocsConditions = new ArrayList<OCSCondition>();
				// 因为传入类型为泛型 并且没有基类 只能制定每个元数据对象 id 都是 "id" 名字
				try {
					ocsConditions.add(new OCSCondition("id", OCSCondition.OCSOperator.EQUAL, item.getClass()
							.getMethod("getId").invoke(item)));
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
						| NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				String key = getRedisKey(item.getClass());
				Map<Integer, ?> map = getIndexListObjectItem(key, item.getClass(), ocsConditions);
				if (map == null || map.isEmpty()) {
					// key 不存在 进行添加
					if (!objectCacheService.addListObjectItem(key, item) && metaTimingCacheService != null) {
						metaTimingCacheService.getAddLinkedBlockingQueue().add(item);
					}

				} else if (map != null && !map.isEmpty()) {
					// key 存在 进行替换
					Integer keyInteger = null;

					for (Map.Entry<Integer, ?> entry : map.entrySet()) {
						// 按照类型和id 获取的结果只会有一个
						keyInteger = (Integer) entry.getKey();
						break;
					}

					if (!objectCacheService.replaceListObjectItem(key, keyInteger, item)
							&& metaTimingCacheService != null) {
						metaTimingCacheService.getUpdateLinkedBlockingQueue().add(item);
					}

				}

			}
		});

	}

	@Override
	public <T> void syncMetaCache(final List<T> items) {
		execute(new Runnable() {

			@Override
			public void run() {

				// 更新
				for (int i = 0; i < items.size(); i++) {
					List<OCSCondition> ocsConditions = new ArrayList<OCSCondition>();
					String key = getRedisKey(items.get(i).getClass());

					// 因为传入类型为泛型 并且没有基类 只能制定每个元数据对象 id 都是 "id" 名字
					try {
						ocsConditions.add(new OCSCondition("id", OCSCondition.OCSOperator.EQUAL, items.get(i)
								.getClass().getMethod("getId").invoke(items.get(i))));
					} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException
							| NoSuchMethodException | SecurityException e) {
						e.printStackTrace();
						System.exit(0);
					}
					Map<Integer, ?> map = getIndexListObjectItem(key, items.get(i).getClass(), ocsConditions);

					if (map != null && !map.isEmpty()) {
						// key 存在 进行更新
						Integer keyInteger = null;

						for (Map.Entry<Integer, ?> entry : map.entrySet()) {
							// 按照类型和id 获取的结果只会有一个
							keyInteger = (Integer) entry.getKey();
							break;
						}
						// 这里不进行单独分组替换失败的，因为该方法是添加方法，不会存在太多误操作
						if (!objectCacheService.replaceListObjectItem(key, keyInteger, items.get(i))
								&& metaTimingCacheService != null) {
							// 添加失败的，存入更新失败列表。
							metaTimingCacheService.getUpdateLinkedBlockingQueue().add(items.get(i));
						}
					}
				}

				// 添加
				for (int i = 0; i < items.size(); i++) {
					if (!objectCacheService.addListObjectItem(getRedisKey(items.get(i).getClass()), items.get(i))
							&& metaTimingCacheService != null) {
						// 添加失败的，存入失败列表。
						metaTimingCacheService.getAddLinkedBlockingQueue().add(items.get(i));
					}
				}
			}
		});

	}

	/**
	 * @Title: getIndexListObjectItem
	 * @param key
	 * @param class1
	 * @param ocsConditions
	 * @return Map<Integer,?>
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	protected Map<Integer, ?> getIndexListObjectItem(String key, Class<? extends Object> class1,
			List<OCSCondition> ocsConditions) {
		int i = 0;
		while (i != time) {
			i++;
			try {
				return objectCacheService.indexListObjectItem(key, class1, ocsConditions);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		throw new RuntimeException("redis请求异常");
	}

	@Override
	public <T> void deleteMetaCache(final T item) {
		String id = null;
		final List<OCSCondition> ocsConditions = new ArrayList<OCSCondition>();

		try {
			id = (String) item.getClass().getMethod("getId").invoke(item);
			// 因为传入类型为泛型 并且没有基类 只能制定每个元数据对象 id 都是 "id" 名字
			if (id != null) {
				ocsConditions.add(new OCSCondition("id", OCSCondition.OCSOperator.EQUAL, id));
			}
		} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchMethodException
				| SecurityException e) {
			e.printStackTrace();
		}
		if (id != null) {
			execute(new Runnable() {
				@Override
				public void run() {

					String key = getRedisKey(item.getClass());
					Map<Integer, ?> map = getIndexListObjectItem(key, item.getClass(), ocsConditions);

					if (map != null && !map.isEmpty()) {
						Integer keyInteger = null;
						for (Map.Entry<Integer, ?> entry : map.entrySet()) {
							// 按照类型和id 获取的结果只会有一个
							keyInteger = (Integer) entry.getKey();
							break;
						}

						if (!objectCacheService.removeListObjectItem(key, keyInteger) && metaTimingCacheService != null) {
							metaTimingCacheService.getDeleteLinkedBlockingQueue().add(item);
						}

					}
				}
			});
		} else {
			execute(new Runnable() {

				@Override
				public void run() {
					String key = getRedisKey(item.getClass());
					boolean judge = false;
					int i = 0;
					while (i != time) {
						i++;
						try {
							judge = objectCacheService.exist(key);
							break;
						} catch (Exception e) {
							e.printStackTrace();
						}
					}

					if (judge) {
						if (!objectCacheService.deleteListObject(key) && metaTimingCacheService != null) {
							metaTimingCacheService.getDeleteLinkedBlockingQueue().add(item);
						}
					}
				}
			});
		}
	}

	/**
	 * 生成该对象的rediskey
	 * @Title: getRedisKey
	 * @param item
	 * @return String
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	public <T> String getRedisKey(Class<T> item) {
		StringBuilder sb = new StringBuilder(redisKeyPrefix);
		sb.append(item.getName().toUpperCase());
		return sb.toString();
	}

}
