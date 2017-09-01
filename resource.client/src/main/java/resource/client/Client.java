/**  
 * 用一句话描述该文件做什么
 * @Title: Client.java
 * @Package resource.client
 * @author Administrator
 * @date 2017年8月24日 上午9:42:22
 * @version v1.0.0
 */
package resource.client;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.whayer.cloud.storage.business.base.data.res.Resource;
import com.whayer.cloud.storage.component.foundation.res.IResourceService;

/**
 * 用一句话描述该文件做什么
 * @ClassName: Client
 * @author Administrator
 * @date 2017年8月24日 上午9:42:22
 * @version v1.0.0
 * 
 */
public class Client {

	public static void main(String[] args) {

		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:client.xml");
		IResourceService resourceService = (IResourceService) applicationContext.getBean("resourceService");
		Resource resource = new Resource();
		resource.setCode("code");
		resource.setDesc("desc");
		resource.setId("asdf");
		resource.setParentId("as");
		resource.setStatus(0);
		resourceService.save(resource);

	}

}
