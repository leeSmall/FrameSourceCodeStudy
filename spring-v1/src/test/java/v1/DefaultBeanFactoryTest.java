package v1;

import org.junit.AfterClass;
import org.junit.Test;

import com.sourcecodestudy.spring.beans.BeanDefinition;
import com.sourcecodestudy.spring.beans.DefaultBeanFactory;
import com.sourcecodestudy.spring.beans.GenericBeanDefinition;
import com.sourcecodestudy.spring.samples.ABean;
import com.sourcecodestudy.spring.samples.ABeanFactory;

/**
 * 
 * @Description: 测试IOC容器（bean工厂）创建bean实例ABean
 * @author leeSmall
 * @date 2018年11月29日
 *
 */
public class DefaultBeanFactoryTest {

	static DefaultBeanFactory bf = new DefaultBeanFactory();

	//测试构造方法方式创建bean实例
	@Test
	public void testRegist() throws Exception {

		//创建bean定义
		GenericBeanDefinition bd = new GenericBeanDefinition();

		//设置bean的类名
		bd.setBeanClass(ABean.class);
		//设置是否单例
		bd.setScope(BeanDefinition.SCOPE_SINGLETION);
		// bd.setScope(BeanDefinition.SCOPE_PROTOTYPE);

		//设置bean的初始化方法
		bd.setInitMethodName("init");
		//设置bean的销毁方法
		bd.setDestroyMethodName("destroy");

		//把bean定义注册到bean工厂DefaultBeanFactory bf
		bf.registerBeanDefinition("aBean", bd);

	}

	//静态工厂方法的方式创建bean实例
	@Test
	public void testRegistStaticFactoryMethod() throws Exception {
		//创建bean定义
		GenericBeanDefinition bd = new GenericBeanDefinition();
		//设置工厂bean的名字
		bd.setBeanClass(ABeanFactory.class);
		//设置工厂方法名
		bd.setFactoryMethodName("getABean");
		//把bean定义注册到bean工厂DefaultBeanFactory bf
		bf.registerBeanDefinition("staticAbean", bd);
	}

	//工厂bean的方式创建bean实例
	@Test
	public void testRegistFactoryMethod() throws Exception {
		//创建工厂bean定义
		GenericBeanDefinition bd = new GenericBeanDefinition();
		//设置工厂bean的名字
		bd.setBeanClass(ABeanFactory.class);
		String fbname = "factory";
		//把工厂bean注册到bean工厂DefaultBeanFactory bf
		bf.registerBeanDefinition(fbname, bd);

		//创建bean定义
		bd = new GenericBeanDefinition();
		//设置工厂bean的名字
		bd.setFactoryBeanName(fbname);
		//设置工厂bean的方法名
		bd.setFactoryMethodName("getABean2");
		//设置是否是单列
		bd.setScope(BeanDefinition.SCOPE_PROTOTYPE);

		//把bean定义注册到bean工厂DefaultBeanFactory bf
		bf.registerBeanDefinition("factoryAbean", bd);
	}

	//获取bean实例并调用里面的方法
	@AfterClass
	public static void testGetBean() throws Exception {
		System.out.println("构造方法方式------------");
		for (int i = 0; i < 3; i++) {
			ABean ab = (ABean) bf.getBean("aBean");
			ab.doSomthing();
		}

		System.out.println("静态工厂方法方式------------");
		for (int i = 0; i < 3; i++) {
			ABean ab = (ABean) bf.getBean("staticAbean");
			ab.doSomthing();
		}

		System.out.println("工厂方法方式------------");
		for (int i = 0; i < 3; i++) {
			ABean ab = (ABean) bf.getBean("factoryAbean");
			ab.doSomthing();
		}

		//销毁
		bf.close();
	}
}
