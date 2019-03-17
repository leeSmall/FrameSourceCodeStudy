package v2;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import com.sourcecodestudy.spring.beans.BeanReference;
import com.sourcecodestudy.spring.beans.GenericBeanDefinition;
import com.sourcecodestudy.spring.beans.PreBuildBeanFactory;
import com.sourcecodestudy.spring.beans.PropertyValue;
import com.sourcecodestudy.spring.samples.ABean;
import com.sourcecodestudy.spring.samples.CBean;
import com.sourcecodestudy.spring.samples.FBean;

/**
 * 
 * @Description: 属性依赖测试
 * @author leeSmall
 * @date 2018年12月1日
 *
 */
public class PropertyDItest {
	static PreBuildBeanFactory bf = new PreBuildBeanFactory();

	@Test
	public void testPropertyDI() throws Exception {

		//构造参数依赖
		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setBeanClass(ABean.class);
		List<Object> args = new ArrayList<>();
		args.add("abean01");
		args.add(new BeanReference("cbean"));
		bd.setConstructorArgumentValues(args);
		bf.registerBeanDefinition("abean", bd);

		//构造参数依赖
		bd = new GenericBeanDefinition();
		bd.setBeanClass(CBean.class);
		args = new ArrayList<>();
		args.add("cbean01");
		bd.setConstructorArgumentValues(args);
		bf.registerBeanDefinition("cbean", bd);

		//属性依赖
		bd = new GenericBeanDefinition();
		bd.setBeanClass(FBean.class);
		List<PropertyValue> propertyValues = new ArrayList<>();
		propertyValues.add(new PropertyValue("name", "FFBean01"));
		propertyValues.add(new PropertyValue("age", 18));
		propertyValues.add(new PropertyValue("aBean", new BeanReference("abean")));
		bd.setPropertyValues(propertyValues);
		bf.registerBeanDefinition("fbean", bd);

		bf.preInstantiateSingletons();

		FBean fbean = (FBean) bf.getBean("fbean");
		System.out.println("设置的属性依赖值: " + fbean.getName() + " " + fbean.getAge());
		fbean.getaBean().doSomthing();
	}

}
