package com.whayer.cloud.storage.component.device.resourceaccess;

import java.util.List;

import com.whayer.cloud.storage.business.base.data.QueryCondition;
import com.whayer.cloud.storage.business.base.data.res.Resource;

public interface IResourceAccess {
	<T extends Resource> boolean add(T t);

	<T extends Resource> boolean addAll(List<T> ts);

	<T extends Resource> boolean modify(T t);

	<T extends Resource> boolean modifyAll(List<T> t);

	<T extends Resource> boolean delete(List<QueryCondition> condition);

	int count(List<QueryCondition> condition);

	<T extends Resource> List<T> query(int start, int end, List<QueryCondition> condition);
}
