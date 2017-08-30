package com.whayer.cloud.storage.business.base.data;

public class QueryCondition {

	/**
	 * @Fields key : TODO (条件key)
	 */
	private String key;
	/**
	 * @Fields queryOperator : TODO (条件操作类型)
	 */
	private QueryOperator queryOperator;
	/**
	 * @Fields value : TODO (条件value，必须是基本数据类型)
	 */
	private Object value;

	/**
	 * 带参构造函数
	 * @author fsong
	 * @version v1.0.0
	 * 
	 * @param key	条件key
	 * @param queryOperator	条件操作类型
	 * @param value	条件value，必须是基本数据类型
	 */
	public QueryCondition(String key, QueryOperator queryOperator, Object value) {
		super();
		this.key = key;
		this.queryOperator = queryOperator;
		this.value = value;
	}
	
	public QueryCondition(){
		
	}
	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @param key the key to set
	 */
	public void setKey(String key) {
		this.key = key;
	}

	/**
	 * @return the queryOperator
	 */
	public QueryOperator getQueryOperator() {
		return queryOperator;
	}

	/**
	 * @param queryOperator the queryOperator to set
	 */
	public void setQueryOperator(QueryOperator queryOperator) {
		this.queryOperator = queryOperator;
	}

	/**
	 * @return the value
	 */
	public Object getValue() {
		return value;
	}

	/**
	 * @param value the value to set
	 */
	public void setValue(Object value) {
		this.value = value;
	}
}
