/**  
 *dao调用类
 * @Title: SqlFunctionDao.java
 * @Package com.whayer.cloud.storage.component.foundation.res.dao
 * @author Administrator
 * @date 2017年8月21日 下午6:34:33
 * @version v1.0.0
 */
package com.whayer.cloud.storage.component.foundation.res.dao;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import whayer.cloud.framework.data.db.dao.datasource.HibernateDbSession;

import com.whayer.cloud.storage.component.foundation.access.beans.TableRowPojo;

/**
 * 执行函数
 * @ClassName: SqlFunctionDao
 * @author Administrator
 * @date 2017年8月21日 下午6:34:33
 * @version v1.0.0
 * 
 */
public class SqlFunctionDaoImpl implements ISqlFunctionDao {

	private HibernateDbSession hibernateDbSession;

	/**
	 * @param hibernateDbSession the hibernateDbSession to set
	 */
	public void setHibernateDbSession(HibernateDbSession hibernateDbSession) {
		this.hibernateDbSession = hibernateDbSession;
	}

	public TableRowPojo getRowByCode(String code) {
		Session session = hibernateDbSession.getSession();
		SQLQuery sqlQuery = session.createSQLQuery("select getParentByCode(?)");
		sqlQuery.setParameter(0, code);
		String id = (String) sqlQuery.uniqueResult();
		TableRowPojo tableRowPojo = null;
		if (id != null && !"".equals(id)) {
			StringBuilder s = new StringBuilder("from ");
			s.append(TableRowPojo.class.getSimpleName());
			s.append(" where id =:paramId");
			Query query = session.createQuery(s.toString());
			query.setParameter("paramId", id);
			tableRowPojo = (TableRowPojo) query.uniqueResult();
		}
		return tableRowPojo;

	}

	public TableRowPojo getRowById(String id) {
		Session session = hibernateDbSession.getSession();
		SQLQuery sqlQuery = session.createSQLQuery("select getParentById(?)");
		sqlQuery.setParameter(0, id);
		id = (String) sqlQuery.uniqueResult();
		TableRowPojo tableRowPojo = null;
		if (id != null || !"".equals(id)) {
			StringBuilder s = new StringBuilder("from ");
			s.append(TableRowPojo.class.getSimpleName());
			s.append(" where id = :id");
			Query query = session.createQuery(s.toString());
			query.setParameter("id", id);
			tableRowPojo = (TableRowPojo) query.uniqueResult();
		}

		return tableRowPojo;
	}
}
