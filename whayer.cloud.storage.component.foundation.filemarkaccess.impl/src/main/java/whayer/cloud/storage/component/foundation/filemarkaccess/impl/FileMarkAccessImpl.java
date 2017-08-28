/**  
 * 文件描述信息和文件标注访问DataBase实现
 * @Title: FileMarkAccessImpl.java
 * @Package whayer.cloud.storage.component.foundation.filemarkaccess.impl
 * @author Administrator
 * @date 2017年8月25日 上午10:52:28
 * @version v1.0.0
 */
package whayer.cloud.storage.component.foundation.filemarkaccess.impl;

import java.lang.reflect.Field;
import java.util.List;

import whayer.cloud.framework.data.db.dao.IDao;
import whayer.cloud.framework.data.db.dao.criteria.Criteria;
import whayer.cloud.framework.data.db.dao.criteria.ICriterion;
import whayer.cloud.framework.data.db.dao.criteria.Restrictions;
import whayer.cloud.framework.data.db.dao.datasource.DataBaseException;
import whayer.cloud.framework.data.db.dao.datasource.HibernateDbSessionFactory;
import whayer.cloud.framework.data.db.dao.datasource.IDbSession;
import whayer.cloud.storage.component.foundation.filemarkaccess.dao.SimpleHibernateDao;
import whayer.cloud.utility.core.BeanService;
import whayer.cloud.utility.core.JavaClass;

import com.whayer.cloud.storage.business.base.data.QueryComplexCondition;
import com.whayer.cloud.storage.business.base.data.QueryComplexCondition.RelationOperator;
import com.whayer.cloud.storage.business.base.data.QueryOperator;
import com.whayer.cloud.storage.foundation.filemarkaccess.IFileMarkAccess;
import com.whayer.cloud.storage.foundation.filemarkaccess.beans.BaseFileInfoPojo;
import com.whayer.cloud.storage.foundation.filemarkaccess.beans.FileMarkInfoPojo;
import com.whayer.cloud.storage.foundation.filemarkaccess.beans.ImageFileInfoPojo;
import com.whayer.cloud.storage.foundation.filemarkaccess.beans.RecordFileInfoPojo;
import com.whayer.cloud.storage.foundation.filemarkaccess.beans.RecordFileMarkInfoPojo;

/**
 * IFileMarkAccess接口实现
 * @ClassName: FileMarkAccessImpl
 * @author Administrator
 * @date 2017年8月25日 上午10:52:28
 * @version v1.0.0
 * 
 */
public class FileMarkAccessImpl implements IFileMarkAccess {
	// 获取bean实例的接口
	private BeanService beanService;

	public void init() {
		if (beanService == null) {
			throw new RuntimeException(this.getClass().getSimpleName() + "实例的beanService is null");
		}
	}

	/**
	 * @param beanService the beanService to set
	 */
	public void setBeanService(BeanService beanService) {
		this.beanService = beanService;
	}

	/**
	 * 新增文件描述对象
	 * @Title: addFileInfo
	 * @param fileInfo	文件描述对象
	 * @return boolean	返回是否成功添加
	 * @author fsong
	 */
	public <T extends BaseFileInfoPojo> boolean addFileInfo(T fileInfo) {

		if (checkObjct(fileInfo)) {
			return false;
		}
		return ((SimpleHibernateDao) beanService.getBean("simpleHibernateDao")).save(fileInfo, true);
	}

	/**
	 * 修改文件描述对象（id字段作为修改标识）
	 * @Title: modifyFileInfo
	 * @param fileInfo	文件描述对象
	 * @return boolean	返回是否成功修改
	 * @author fsong
	 */
	public <T extends BaseFileInfoPojo> boolean modifyFileInfo(T fileInfo) {
		if (checkObjct(fileInfo)) {
			return false;
		}
		return ((SimpleHibernateDao) beanService.getBean("simpleHibernateDao")).update(fileInfo, true);
	}

	/**
	 * 根据条件删除文件描述对象（关联的标注对象也将删除）
	 * @Title: deleteFileInfo
	 * @param type		文件描述对象类型
	 * @param queryComplexCondition	复杂组合条件
	 * @return boolean	返回是否成功删除
	 * @author fsong
	 */
	public <T extends BaseFileInfoPojo> boolean deleteFileInfo(Class<T> type,
			QueryComplexCondition queryComplexCondition) {
		IDbSession session = null;
		boolean result = false;
		if (type == null || queryComplexCondition == null) {
			return result;
		}

		Criteria where = changeCondtion(queryComplexCondition, type);

		try {
			IDao dao = beanService.getBean("simpleHibernateDao");
			session = dao.getSession();
			session.beginTransaction();
			List<BaseFileInfoPojo> fileInfoPojo = dao.selectList(type, where);
			dao.delete(type, fileInfoPojo.toArray());
			// 批量删除其子资源
			if (fileInfoPojo != null) {
				for (int i = 0; i < fileInfoPojo.size(); i++) {
					String id = (String) fileInfoPojo.get(i).getClass().getMethod("getId").invoke(fileInfoPojo.get(i));
					// 获取对应标注 class 字符串
					String className = queryPairMarkInfo(type);
					try {
						dao.delete(Class.forName(className), id);
					} catch (ClassNotFoundException e) {

					}
				}
			}
			session.commit();
			session.clear();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (session != null) {
					session.rollback();
				}
			} catch (DataBaseException dataBaseException) {

				dataBaseException.printStackTrace();
			}
		}
		return result;
	}

	/**
	* 查询满足条件的文件描述对象数量
	* @Title: queryFileInfoSize
	* @param type	文件描述对象类型
	* @param queryComplexCondition	复杂组合条件
	* @return int	返回文件描述对象数，返回-1表示异常
	* @author fsong
	*/
	public <T extends BaseFileInfoPojo> int queryFileInfoSize(Class<T> type, QueryComplexCondition queryComplexCondition) {
		return querySize(type, queryComplexCondition);
	}

	/**
	* 查询满足条件的文件描述对象
	* @Title: queryFileInfo
	* @param type		文件描述对象类型
	* @param queryComplexCondition	复杂组合条件
	* @param start	记录开始位置
	* @param end	记录结束位置
	* @return List<T>	返回文件描述对象集合
	* @author fsong
	*/
	public <T extends BaseFileInfoPojo> List<T> queryFileInfo(Class<T> type,
			QueryComplexCondition queryComplexCondition, int start, int end) {
		return queryByCondtion(type, queryComplexCondition, start, end);
	}

	/**
	 * 新增文件标注对象
	 * @Title: addFileMarkInfo
	 * @param fileMarkInfo	文件标注对象
	 * @return boolean	返回是否成功添加
	 * @author fsong
	 */
	@SuppressWarnings("unchecked")
	public <T extends FileMarkInfoPojo> boolean addFileMarkInfo(T fileMarkInfo) {
		boolean result = false;
		if (checkObjct(fileMarkInfo)) {
			return result;
		}
		// 判断信息id 是否存在
		HibernateDbSessionFactory factory = ((HibernateDbSessionFactory) beanService
				.getBean("hibernateDbSessionFactory"));
		Field field = JavaClass.getAllFields(fileMarkInfo.getClass(), "fileInfoId");
		field.setAccessible(true);
		Criteria where = new Criteria();
		try {
			where.add(Restrictions.eq(fileMarkInfo.getClass(), "id", field.get(fileMarkInfo)));
		} catch (IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
			return result;
		}
		try {
			IDbSession session = factory.getSession();
			IDao dao = beanService.getBean("hibernateDao");
			session.beginTransaction();
			session.clear();
			dao.setSession(session);
			// 获取信息对象 class 字符串
			String className = queryPairFileInfo(fileMarkInfo);
			List<?> list = dao.selectList(Class.forName(className), where, true);
			if (list == null || list.size() == 0) {
				// return result;
			}
			dao.insert(fileMarkInfo);
			session.commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 修改文件标注对象（id字段作为修改标识）
	 * @Title: modifyFileMarkInfo
	 * @param fileMarkInfo	文件标注对象
	 * @return boolean	返回是否成功修改
	 * @author fsong
	 */
	@SuppressWarnings("unchecked")
	public <T extends FileMarkInfoPojo> boolean modifyFileMarkInfo(T fileMarkInfo) {
		boolean result = false;
		if (checkObjct(fileMarkInfo)) {
			return result;
		}
		// 判断信息id 是否存在
		HibernateDbSessionFactory factory = ((HibernateDbSessionFactory) beanService
				.getBean("hibernateDbSessionFactory"));
		Field field = JavaClass.getAllFields(fileMarkInfo.getClass(), "fileInfoId");
		field.setAccessible(true);
		Criteria where = new Criteria();
		try {
			where.add(Restrictions.eq(fileMarkInfo.getClass(), "id", field.get(fileMarkInfo)));
		} catch (IllegalArgumentException | IllegalAccessException e1) {
			e1.printStackTrace();
			return result;
		}
		try {
			IDbSession session = factory.getSession();
			IDao dao = beanService.getBean("hibernateDao");
			session.beginTransaction();
			session.clear();
			dao.setSession(session);
			// 获取信息对象 class 字符串
			String className = queryPairFileInfo(fileMarkInfo);
			List<?> list = dao.selectList(Class.forName(className), where, true);
			if (list == null || list.size() == 0) {
				// return result;
			}
			dao.update(fileMarkInfo);
			session.commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 根据条件删除文件标注对象
	 * @Title: deleteFileMarkInfo
	 * @param type		文件标注对象类型
	 * @param queryComplexCondition	复杂组合条件
	 * @return boolean	返回是否成功删除
	 * @author fsong
	 */
	public <T extends FileMarkInfoPojo> boolean deleteFileMarkInfo(Class<T> type,
			QueryComplexCondition queryComplexCondition) {
		IDbSession session = null;
		boolean result = false;
		if (type == null || queryComplexCondition == null) {
			return result;
		}

		Criteria where = changeCondtion(queryComplexCondition, type);

		try {
			SimpleHibernateDao dao = beanService.getBean("simpleHibernateDao");
			session = dao.getSession();
			session.beginTransaction();
			dao.delete(type, where);
			// 批量删除其子资源
			session.commit();
			session.clear();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			try {
				if (session != null) {
					session.rollback();
				}
			} catch (DataBaseException dataBaseException) {

				dataBaseException.printStackTrace();
			}
		}
		return result;

	}

	/**
	 * 查询满足条件的文件标注对象数量
	 * @Title: queryFileInfoMarkSize
	 * @param type	文件标注对象类型
	 * @param queryComplexCondition	复杂组合条件
	 * @return int	返回文件标注对象数，返回-1表示异常
	 * @author fsong
	 */
	public <T extends FileMarkInfoPojo> int queryFileInfoMarkSize(Class<T> type,
			QueryComplexCondition queryComplexCondition) {

		return querySize(type, queryComplexCondition);
	}

	/**
	 * 查询满足条件的文件标注对象
	 * @Title: queryFileInfo
	 * @param type		文件标注对象类型
	 * @param queryComplexCondition	复杂组合条件
	 * @param start	记录开始位置
	 * @param end	记录结束位置
	 * @return List<T>	返回文件标注对象集合
	 * @author fsong
	 */
	public <T extends FileMarkInfoPojo> List<T> queryFileMarkInfo(Class<T> type,
			QueryComplexCondition queryComplexCondition, int start, int end) {
		return queryByCondtion(type, queryComplexCondition, start, end);
	}

	/**
	 * 按条件查询某对象集合  
	 * @Title: queryByCondtion
	 * @param type
	 * @param queryComplexCondition
	 * @param start
	 * @param end
	 * @return List<T>
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private <T> List<T> queryByCondtion(Class<T> type, QueryComplexCondition queryComplexCondition, int start, int end) {
		if (type == null || queryComplexCondition == null || start < end || start < 0 || end <= 0) {
			return null;
		}
		Criteria where = changeCondtion(queryComplexCondition, type);
		return ((SimpleHibernateDao) beanService.getBean("simpleHibernateDao")).query(type, where);
	}

	/**
	 * 查询某对象总数
	 * @Title: querySize
	 * @param type
	 * @param queryComplexCondition
	 * @return int
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private <T> int querySize(Class<T> type, QueryComplexCondition queryComplexCondition) {
		int result = -1;
		if (type == null || queryComplexCondition == null) {
			return result;
		}

		Criteria where = changeCondtion(queryComplexCondition, type);
		try {
			return ((SimpleHibernateDao) beanService.getBean("simpleHibernateDao")).count(type, where).intValue();
		} catch (DataBaseException e) {
			e.printStackTrace();
			return result;
		}

	}

	/**
	 * 转换复合条件
	 * @Title: changeCondtion
	 * @param queryComplex
	 * @param where void
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private Criteria changeCondtion(QueryComplexCondition queryComplex, Class<?> type) {
		// 校验数据
		if (queryComplex == null || !checkCondtionData(queryComplex)) {
			return null;
		}
		Criteria criteria = new Criteria();
		if (queryComplex.getConditionLst() != null && queryComplex.getConditionLst().size() != 0) {
			criteria.add(recursion(queryComplex, type));
		} else {
			criteria.add(changeCommonCondtion(type, queryComplex));
		}
		return criteria;

	}

	/**
	 * 
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
				return Restrictions.gt(type, c.getKey(), c.getValue().toString());
			} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.GREATER_OR_EQUAL) {
				return Restrictions.ge(type, c.getKey(), c.getValue().toString());
			} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LESS) {
				return Restrictions.lt(type, c.getKey(), c.getValue().toString());
			} else if (c.getQueryOperator().getOperator() == QueryOperator.Operator.LESS_OR_EQUAL) {
				return Restrictions.le(type, c.getKey(), c.getValue().toString());
			}
		}
		return null;
	}

	/**
	 * 校验对象属性的必要值
	 * @Title: checkObjct
	 * @param object
	 * @return boolean
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private boolean checkObjct(Object object) {
		if (object == null) {
			return true;
		}
		try {
			if (IdIsNull(object)) {
				return true;
			}
		} catch (IllegalArgumentException | IllegalAccessException e) {
			e.printStackTrace();
			return true;
		}
		if (object.getClass().getName()
				.equals("com.whayer.cloud.storage.foundation.filemarkaccess.beans.ImageFileInfoPojo")) {
			ImageFileInfoPojo imageFileInfoPojo = (ImageFileInfoPojo) object;
			if (imageFileInfoPojo.getWidth() == 0) {
				imageFileInfoPojo.setWidth(-1);
			}
			if (imageFileInfoPojo.getHeight() == 0) {
				imageFileInfoPojo.setHeight(-1);
			}
			if (imageFileInfoPojo.getPath() == null) {
				return true;
			}

		} else if (object.getClass().getName()
				.equals("com.whayer.cloud.storage.foundation.filemarkaccess.beans.RecordFileInfoPojo")) {
			RecordFileInfoPojo recordFileInfoPojo = (RecordFileInfoPojo) object;
			if (recordFileInfoPojo.getRecordDataType() < 0) {
				return true;
			}
		} else if (object.getClass().getName()
				.equals("com.whayer.cloud.storage.foundation.filemarkaccess.beans.RecordFileMarkInfoPojo")) {
			RecordFileMarkInfoPojo recordFileMarkInfoPojo = (RecordFileMarkInfoPojo) object;
			if (recordFileMarkInfoPojo.getFileInfoId() == null) {
				return true;
			}
			if (recordFileMarkInfoPojo.getFileOwnUserId() == null) {
				return true;
			}
		} else {
			return true;
		}
		return false;
	}

	/**
	 * 判断id是否为空
	 * @Title: IdIsNull
	 * @param obj
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException boolean
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private <T> boolean IdIsNull(T obj) throws IllegalArgumentException, IllegalAccessException {
		Field field = JavaClass.getAllFields(obj.getClass(), "id");
		field.setAccessible(true);
		if (field.get(obj) == null) {
			return true;
		}
		return false;
	}

	/**
	 * 按照这个命名规则 那么以后为某个文件属性添加标注 不需要更改代码 会根据该命名规则准确的级联删除对应的标注信息
	 * @Title: queryPairFileInfo
	 * @param fileMarkInfo
	 * @return String
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private <T> String queryPairFileInfo(T fileMarkInfo) {
		return fileMarkInfo.getClass().getName().replaceAll("FileMarkInfo", "FileInfo");
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

	/**
	 * @Title: queryPairFileInfo
	 * @param fileMarkInfo
	 * @return String
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private <T> String queryPairMarkInfo(T type) {
		return type.getClass().getName().replaceAll("FileInfo", "FileMarkInfo");
	}
}
