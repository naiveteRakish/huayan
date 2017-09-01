package whayer.cloud.dbSystemInfoDao;

import whayer.cloud.framework.data.db.dao.DaoMethod;

/**
 * 数据库系统信息
 */
public class DbSystemInfo {

	/**
	 * id (唯一标识)
	 */
	@DaoMethod(objectName="DbSystemInfo", propertyName="id")
	private Long id;
	/**
	 * 数据库系统名称
	 */
    private String name;
    /**
     * 数据库系统版本
     */
	private String version;
    
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getVersion() {
		return version;
	}

	public void setVersion(String version) {
		this.version = version;
	}

	@Override
    public String toString() {
        return "DbSystemInfo [id=" + id + ", name=" + name + ", version=" + version + "]";
    }

}