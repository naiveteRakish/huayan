package com.whayer.cloud.storage.business.base.data;

import java.util.List;

/**
 * 复杂查询条件对象
 * @ClassName: QueryComplexCondition
 * @author fsong
 * @date 2017年8月15日 下午5:20:58
 * @version v1.0.0
 * 
 */
public class QueryComplexCondition extends QueryCondition{

	public QueryComplexCondition(String key, QueryOperator queryOperator, Object value) {
		super(key, queryOperator, value);
		// TODO Auto-generated constructor stub
	}
	
	public QueryComplexCondition(){
		super();
	}
	
	public static enum RelationOperator {
		Relation_And, Relation_Or
	}
	
	/**
	 * 子条件间关系
	 * @Fields relationOpertaor
	 */
	private RelationOperator relationOpertaor;
	
	/**
	 * 子条件列表
	 * @Fields conditionLst
	 */
	private List<QueryComplexCondition> conditionLst;

	/**
	 * @return the relationOpertaor
	 */
	public RelationOperator getRelationOpertaor() {
		return relationOpertaor;
	}

	/**
	 * @param relationOpertaor the relationOpertaor to set
	 */
	public void setRelationOpertaor(RelationOperator relationOpertaor) {
		this.relationOpertaor = relationOpertaor;
	}

	/**
	 * @return the conditionLst
	 */
	public List<QueryComplexCondition> getConditionLst() {
		return conditionLst;
	}

	/**
	 * @param conditionLst the conditionLst to set
	 */
	public void setConditionLst(List<QueryComplexCondition> conditionLst) {
		this.conditionLst = conditionLst;
	}
	
	
}
