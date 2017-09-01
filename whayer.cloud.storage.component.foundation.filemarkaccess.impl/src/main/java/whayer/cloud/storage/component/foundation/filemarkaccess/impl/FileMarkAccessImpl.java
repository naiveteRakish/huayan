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

import javax.sound.midi.SysexMessage;

import whayer.cloud.framework.data.db.dao.IDao;
import whayer.cloud.framework.data.db.dao.criteria.Criteria;
import whayer.cloud.framework.data.db.dao.criteria.Restrictions;
import whayer.cloud.framework.data.db.dao.datasource.DataBaseException;
import whayer.cloud.framework.data.db.dao.datasource.IDbSession;
import whayer.cloud.storage.component.foundation.access.dao.SimpleHibernateDao;
import whayer.cloud.storage.component.foundation.access.tool.ComponetTool;
import whayer.cloud.utility.core.BeanService;
import whayer.cloud.utility.core.JavaClass;

import com.whayer.cloud.storage.business.base.data.QueryComplexCondition;
import com.whayer.cloud.storage.component.foundation.access.IAccessService;
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
	// 条件转换工具
	private ComponetTool componetTool;
	//公共存取接口
	private IAccessService accessService;

	public IAccessService getAccessService() {
		return accessService;
	}

	public void setAccessService(IAccessService accessService) {
		this.accessService = accessService;
	}

	/**
	 * @return the componetTool
	 */
	public ComponetTool getComponetTool() {
		return componetTool;
	}

	/**
	 * @param componetTool the componetTool to set
	 */
	public void setComponetTool(ComponetTool componetTool) {
		this.componetTool = componetTool;
	}

	public void init() {
		if (beanService == null || componetTool == null || accessService==null) {
			throw new RuntimeException(this.getClass().getName() + ">>>>>>存在未注入对象");
		}
	}

	/**
	 * @return the beanService
	 */
	public BeanService getBeanService() {
		return beanService;
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
		return accessService.add(fileInfo);
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
		return accessService.update(fileInfo);
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
		if (type == null) {
			return result;
		}
		Criteria where = componetTool.changeCondtion(queryComplexCondition, type);
		try {
			IDao dao = beanService.getBean(SimpleHibernateDao.class);
			session = dao.getSession();
			session.beginTransaction();
			if(!type.getName().equals(ImageFileInfoPojo.class.getName())){
				List<BaseFileInfoPojo> fileInfoPojos = dao.selectList(type, where);
				// 获取对应标注 class 字符串
				String className = queryPairMarkInfo(type);
				// 批量删除资源
				if (fileInfoPojos != null) {
					for (int i = 0; i < fileInfoPojos.size(); i++) {
						String id = fileInfoPojos.get(i).getId();
						dao.delete(type, id);
						where = new Criteria();
						where.add(Restrictions.eq(Class.forName(className), "fileInfoId", id));
						dao.delete(Class.forName(className), where);
					}
				}
			}else {
				//图片不存在标注对象 所以直接删除
				dao.delete(type, where);
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
	 * 
	 * @Title: addFileMarkInfo
	 * @param fileMarkInfo
	 *            文件标注对象
	 * @return boolean 返回是否成功添加
	 * @author fsong
	 */
	@SuppressWarnings("unchecked")
	public <T extends FileMarkInfoPojo> boolean addFileMarkInfo(T fileMarkInfo) {
		IDbSession session = null;
		boolean result = false;

		if (checkObjct(fileMarkInfo)) {
			return result;
		}

		// 判断信息id 是否存在
		Criteria where = new Criteria();
		String className = queryPairFileInfo(fileMarkInfo);
		try {
			where.add(Restrictions.eq(Class.forName(className), "id",
					fileMarkInfo.getFileInfoId()));
			SimpleHibernateDao simpleHibernateDao = beanService
					.getBean(SimpleHibernateDao.class);
			session = simpleHibernateDao.getSession();
			session.beginTransaction();
			// 获取信息对象 class 字符串
			List<?> list = simpleHibernateDao.selectList(
					Class.forName(className), where, true);
			if (list == null || list.size() == 0) {
				session.rollback();
				return result;
			}
			simpleHibernateDao.insert(fileMarkInfo);
			session.commit();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				try {
					session.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
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
		IDbSession session = null;
		boolean result = false;
		if (fileMarkInfo == null || fileMarkInfo.getId() == null) {
			return result;
		}
		// 判断信息id 是否存在
		Criteria where = new Criteria();
		String className = queryPairFileInfo(fileMarkInfo);
		try {
			where.add(Restrictions.eq(Class.forName(className), "id", fileMarkInfo.getFileInfoId()));
			SimpleHibernateDao simpleHibernateDao = beanService.getBean(SimpleHibernateDao.class);
			session = simpleHibernateDao.getSession();
			session.beginTransaction();
			// 获取信息对象 class 字符串
			List<?> list = simpleHibernateDao.selectList(Class.forName(className), where, true);
			if (list == null || list.size() == 0) {
					session.rollback();
					return result;
			}
			simpleHibernateDao.update(fileMarkInfo);
			session.commit();
			session.clear();
			result = true;
		} catch (Exception e) {
			e.printStackTrace();
			if (session != null) {
				try {
					session.rollback();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			}
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
		if (type == null) {
			return false;
		}

		Criteria where = componetTool.changeCondtion(queryComplexCondition, type);
		return beanService.getBean(SimpleHibernateDao.class).delete(type, where,true);

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
		if (type == null || queryComplexCondition == null || start > end || start < 0 || end < 0) {
			return null;
		}
		Criteria where = componetTool.changeCondtion(queryComplexCondition, type);
		if(where==null){
		where =	new Criteria();
		where.setFirstResult(start);
		where.setMaxResults(++end);
		}
		return beanService.getBean(SimpleHibernateDao.class).query(type, where);
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
		if (type == null) {
			return result;
		}
		Criteria where = componetTool.changeCondtion(queryComplexCondition, type);
		try {
			return beanService.getBean(SimpleHibernateDao.class).count(type, where).intValue();
		} catch (DataBaseException e) {
			e.printStackTrace();
			return result;
		}

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
	 * @Title: queryPairFileInfo
	 * @param fileMarkInfo
	 * @return String
	 * @see 
	 * @throws
	 * @author Administrator
	 */
	private <T> String queryPairMarkInfo(Class<T> type) {
		return type.getName().replaceAll("FileInfo", "FileMarkInfo");
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
		if (object instanceof ImageFileInfoPojo) {
			ImageFileInfoPojo imageFileInfoPojo = (ImageFileInfoPojo) object;
			if (imageFileInfoPojo.getWidth() == 0) {
				imageFileInfoPojo.setWidth(-1);
			}
			if (imageFileInfoPojo.getHeight() == 0) {
				imageFileInfoPojo.setHeight(-1);
			}
			if (imageFileInfoPojo.getPath() == null || "".equals(imageFileInfoPojo.getPath().trim())) {
				return true;
			}

		} else if (object instanceof RecordFileInfoPojo) {
			RecordFileInfoPojo recordFileInfoPojo = (RecordFileInfoPojo) object;
			if (recordFileInfoPojo.getRecordDataType() < 0) {
				return true;
			}
			if(recordFileInfoPojo.getPath()==null||"".equals(recordFileInfoPojo.getPath().trim())){
				return true;
			}
		} else if (object instanceof RecordFileMarkInfoPojo) {
			RecordFileMarkInfoPojo recordFileMarkInfoPojo = (RecordFileMarkInfoPojo) object;
			if (recordFileMarkInfoPojo.getFileInfoId() == null || "".equals(recordFileMarkInfoPojo.getFileInfoId().trim())) {
				return true;
			}
			if (recordFileMarkInfoPojo.getFileOwnUserId() == null|| "".equals(recordFileMarkInfoPojo.getFileOwnUserId().trim())) {
				return true;
			}
		} else {
			//没添加的也可以进行存储 交给数据库去判断 字段是否正确
			System.err.println("该类未添加有效的验证信息!");
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
		if (field.get(obj) == null || "".equals(field.get(obj).toString().trim())) {
			return true;
		}
		return false;
	}
}
