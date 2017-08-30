package whayer.cloud.dbSystemInfoDao;

import whayer.cloud.framework.data.db.dao.metadata.*;

public class DbSystemInfoTable extends DbTable {
	
	/**
	 * 默认构造函数
	 */
	public DbSystemInfoTable() {
		this.TableName = "DbSystemInfo";
		this.TableAlias = "数据库信息表";
		this.Fields.add(new TableField("id", "主键", TableFieldType.DBInteger, 8, 0, true, true, false, "", "" ));
		this.Fields.add(new TableField("name", "名称", TableFieldType.DBString, 500, 0, false, false, true, "", "" ));
		this.Fields.add(new TableField("version", "版本", TableFieldType.DBString, 500, 0, false, false, true, "", "" ));
	}
	
	/**
	 * 返回默认记录
	 * @return
	 */
	public DbSystemInfo[] getDefaultRecorders() {
		DbSystemInfo[] mInfos = new DbSystemInfo[1];
		
		DbSystemInfo mDbInfo = new DbSystemInfo();
		mDbInfo.setId(1L);
		mDbInfo.setName("数据库版本");
		mDbInfo.setVersion("1.0.0.0");
		
		mInfos[0] = mDbInfo;
		
		return mInfos;
	}

}
