package wang.moshu.message.demo;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * spring bean工具类
 * 
 */
public class SpringBeanUtils implements ApplicationContextAware
{
	private static ApplicationContext applicationContext;

	/*** bean初始化时会被调用 **/
	public void setApplicationContext(ApplicationContext appContext) throws BeansException
	{
		applicationContext = appContext;
	}

	public static Object getBean(String name)
	{
		return applicationContext.getBean(name);
	}

}
