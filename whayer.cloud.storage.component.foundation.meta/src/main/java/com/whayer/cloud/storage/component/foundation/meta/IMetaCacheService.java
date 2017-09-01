package com.whayer.cloud.storage.component.foundation.meta;

import java.util.List;

public interface IMetaCacheService {
	/**
	 * 将具体对象同步到缓存，可以包含更新和添加
	 * @Title: syncMetaCache
	 * @param @param item
	 * @return void
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	public <T> void syncMetaCache(T item);

	/**
	 * 将具体对象批量同步到缓存，可以包含更新和添加
	 * @Title: syncMetaCache
	 * @param @param item
	 * @return void
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	public <T> void syncMetaCache(List<T> items);

	/**
	 * 从缓存中删除制定对象
	 * @Title: deleteMetaCache
	 * @param @param item 如果item是一个新建的空对象，则删除该对象类型所有数据， 如果item对象包含"id"属性值，则按照id 进行删除该类型对象的数据
	 * @return void
	 * @see 
	 * @throws 
	 * @author lishibang
	 */
	public <T> void deleteMetaCache(T item);

	/**
	 * 
	 * 获取redis Key
	 * @Title: getRedisKey
	 * @param item
	 * @return String
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	public <T> String getRedisKey(Class<T> item);

	/**
	 * 重试次数
	 * 这里用一句话描述这个方法的作用
	 * @Title: getTime
	 * @return int
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	public int getTime();
}
