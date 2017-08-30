package whayer.cloud.framework.io.test;

import org.testng.annotations.Test;

import whayer.cloud.utility.logs.Log;

public class LogTest {
	
	@Test
	public void testDebug() {
		//this.logger.debug("测试Debug信息！");
		Log.getLogger("test").debug("测试Debug信息！");
	}
	
	@Test
	public void testInfo() {
		//this.logger.debug("测试Debug信息！");
		Log.getLogger("test").info("测试Info信息！");
	}
	
	@Test
	public void testWarn() {
		//this.logger.debug("测试Debug信息！");
		Log.getLogger("test").warn("测试Warn信息！");
	}
	
	@Test
	public void testError() {
		//this.logger.debug("测试Debug信息！");
		Log.getLogger("test").error("测试Error信息！");
		Exception e = new Exception("异常测试！");
		Log.getLogger("test").error(e);
	}
	
	@Test
	public void testFatal() {
		//this.logger.debug("测试Debug信息！");
		Log.getLogger("test").fatal("测试Fatal信息！");
	}
}
