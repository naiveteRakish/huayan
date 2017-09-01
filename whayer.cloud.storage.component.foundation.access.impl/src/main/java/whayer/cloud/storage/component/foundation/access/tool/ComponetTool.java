/**  
 * 用一句话描述该文件做什么
 * @Title: ComponetTool.java
 * @Package whayer.cloud.storage.component.foundation.filemarkaccess.dao
 * @author Administrator
 * @date 2017年8月29日 下午1:59:32
 * @version v1.0.0
 */
package whayer.cloud.storage.component.foundation.access.tool;

import java.util.List;

import whayer.cloud.framework.data.db.dao.criteria.Criteria;
import whayer.cloud.framework.data.db.dao.criteria.ICriterion;
import whayer.cloud.framework.data.db.dao.criteria.Restrictions;

import com.whayer.cloud.storage.business.base.data.QueryComplexCondition;
import com.whayer.cloud.storage.business.base.data.QueryComplexCondition.RelationOperator;
import com.whayer.cloud.storage.business.base.data.QueryOperator;

/**
 * 用一句话描述该文件做什么
 * @ClassName: ComponetTool
 * @author Administrator
 * @date 2017年8月29日 下午1:59:32
 * @version v1.0.0
 * 
 */
public class ComponetTool {

	/**
	 * 转换复合条件
	 * @Title: changeCondtion
	 * @param queryComplex
	 * @param where void
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	public Criteria changeCondtion(QueryComplexCondition queryComplex, Class<?> type) {
		Criteria criteria = null;
		if(queryComplex==null){
			return criteria;
		}
		criteria = new Criteria();
		
		if (queryComplex.getConditionLst() != null && queryComplex.getConditionLst().size() != 0) {
			criteria.add(recursion(queryComplex, type));
		} else {
			criteria.add(changeCommonCondtion(type, queryComplex));
		}
		return criteria;
	}

	/**
	 * 递归解析传入条件 封装dao条件
	 * @Title: recursion
	 * @param relationOpertaor void
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private ICriterion recursion(QueryComplexCondition queryComplex, Class<?> type) {
		boolean sign = false;
		List<QueryComplexCondition> list = queryComplex.getConditionLst();
		ICriterion criterion = null;
		int size = list.size();
		if (queryComplex.getRelationOpertaor() == RelationOperator.Relation_And) {
			// and 关系
			for (int i = 0; i < size - 1; i = i + 2) {
				QueryComplexCondition element = queryComplex.getConditionLst().get(i);
				QueryComplexCondition element1 = queryComplex.getConditionLst().get(i + 1);
				if ((element != null && checkCondtionData(element))
						&& (element1 != null && checkCondtionData(element1))) {
					if (criterion != null) {
						criterion = Restrictions.and(criterion,
								Restrictions.and(recursion(element, type), recursion(element1, type)));
					} else {
						criterion = Restrictions.and(recursion(element, type), recursion(element1, type));
					}

				} else {
					if (criterion != null) {
						criterion = Restrictions.and(changeCommonCondtion(type, element),
								changeCommonCondtion(type, element1));
					} else {
						criterion = Restrictions.and(changeCommonCondtion(type, element),
								changeCommonCondtion(type, element1));
					}
					sign = true;
				}
			}

		} else {
			// or 关系
			for (int i = 0; i < size - 1; i = i + 2) {
				QueryComplexCondition element = queryComplex.getConditionLst().get(i);
				QueryComplexCondition element1 = queryComplex.getConditionLst().get(i + 1);
				if ((element != null && checkCondtionData(element))
						&& (element1 != null && checkCondtionData(element1))) {

					if (criterion != null) {
						criterion = Restrictions.or(criterion,
								Restrictions.or(recursion(element, type), recursion(element1, type)));
					} else {
						criterion = Restrictions.or(recursion(element, type), recursion(element1, type));
					}
				} else {
					if (criterion != null) {
						criterion = Restrictions.or(changeCommonCondtion(type, element),
								changeCommonCondtion(type, element1));
					} else {
						criterion = Restrictions.or(changeCommonCondtion(type, element),
								changeCommonCondtion(type, element1));
					}
					sign = true;
				}
			}
		}
		if (size == 1) {
			return criterion;
		}
		if (size % 2 != 0 && sign) {
			if (queryComplex.getRelationOpertaor() == RelationOperator.Relation_And) {
				criterion = Restrictions.and(criterion, changeCommonCondtion(type, list.get(size - 1)));
			} else {
				criterion = Restrictions.or(criterion, changeCommonCondtion(type, list.get(size - 1)));
			}
		} else if (size % 2 != 0) {
			if (queryComplex.getRelationOpertaor() == RelationOperator.Relation_And) {
				criterion = Restrictions.and(criterion, recursion(list.get(size - 1), type));
			} else {
				criterion = Restrictions.or(criterion, recursion(list.get(size - 1), type));
			}
		}

		return criterion;

	}

	/**
	 * 
	 * 转换普通条件
	 * @Title: changeCondtion
	 * @param type
	 * @param c
	 * @param where void
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private ICriterion changeCommonCondtion(Class<?> type, QueryComplexCondition c) {
		if (type != null && c != null && c.getKey() != null && !c.getKey().equals("") && c.getQueryOperator() != null
				&& c.getValue() != null) {
			if (c.getQueryOperator().getOperator() == QueryOperator.Operator.EQUAL) {
				return Restrictions.eq(type, c.getKey(), c.getValue());
			} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LIKE) {
				return Restrictions.like(type, c.getKey(), c.getValue().toString());
			} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.IN) {
				return Restrictions.in(type, c.getKey(), c.getValue().toString());
			} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.GREATER) {
				return Restrictions.gt(type, c.getKey(), c.getValue());
			} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.GREATER_OR_EQUAL) {
				return Restrictions.ge(type, c.getKey(), c.getValue());
			} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LESS) {
				return Restrictions.lt(type, c.getKey(), c.getValue());
			} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LESS_OR_EQUAL) {
				return Restrictions.le(type, c.getKey(), c.getValue());
			}
		}
		return null;
	}

	/**
	 * 检验条件属性 
	 * @Title: checkCondtionData
	 * @param queryComplex
	 * @return boolean
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private boolean checkCondtionData(QueryComplexCondition queryComplex) {
		if (queryComplex.getConditionLst() == null || queryComplex.getConditionLst().size() == 0
				|| queryComplex.getRelationOpertaor() == null) {
			return false;
		}
		return true;
	}

}
