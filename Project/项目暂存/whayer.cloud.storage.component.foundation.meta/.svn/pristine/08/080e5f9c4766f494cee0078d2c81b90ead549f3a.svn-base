/**  
 * 元数据管理
 * @Title: IMetaTimingCacheService.java
 * @Package com.whayer.cloud.storage.component.foundation.meta
 * @author Administrator
 * @date 2017年8月16日 上午11:24:07
 * @version v1.0.0
 */
package com.whayer.cloud.storage.component.foundation.meta;

import java.util.LinkedList;
import java.util.concurrent.ScheduledExecutorService;

/**
 * 定时同步器
 * @ClassName: IMetaTimingCacheService
 * @author Administrator
 * @date 2017年8月16日 上午11:24:07
 * @version v1.0.0
 * 
 */
public interface IMetaTimingCacheService {
	LinkedList<Object> getAddLinkedBlockingQueue();

	LinkedList<Object> getUpdateLinkedBlockingQueue();

	LinkedList<Object> getDeleteLinkedBlockingQueue();

	ScheduledExecutorService getService();

	void init();

}
