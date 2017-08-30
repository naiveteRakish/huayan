package whayer.cloud.dbSystemInfoDao;

public class DbSystemInfoGroupByName {
	/**
	 * 数据库系统名称
	 */
    public String name;
    /**
     * 数据库系统版本个数
     */
    public Long version;
    
    @Override
    public String toString() {
        return "DbSystemInfoGroupByName [name=" + name + ", version=" + version + "]";
    }
}
