package com.whayer.cloud.storage.component.foundation.access;

import java.util.List;

import com.whayer.cloud.storage.business.base.data.QueryCondition;

public interface IAccessService {

	<T> T get(Class<T> type, String id);

	<T> boolean add(T t);

	<T> boolean addAll(List<T> ts);

	<T> boolean update(T t);

	<T> boolean updateAll(List<T> t);

	<T> boolean delete(Class<T> type, List<QueryCondition> condition);

	<T> int count(Class<T> type, List<QueryCondition> condition);

	<T> List<T> query(Class<T> type, int start, int end, List<QueryCondition> condition);
}
