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

public class PropertyDItest {
	static PreBuildBeanFactory bf = new PreBuildBeanFactory();

	@Test
	public void testPropertyDI() throws Throwable {

		GenericBeanDefinition bd = new GenericBeanDefinition();
		bd.setBeanClass(ABean.class);
		List<Object> args = new ArrayList<>();
		args.add("abean01");
		args.add(new BeanReference("cbean"));
		bd.setConstructorArgumentValues(args);
		bf.registerBeanDefinition("abean", bd);

		bd = new GenericBeanDefinition();
		bd.setBeanClass(CBean.class);
		args = new ArrayList<>();
		args.add("cbean01");
		bd.setConstructorArgumentValues(args);
		bf.registerBeanDefinition("cbean", bd);

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
		System.out.println(fbean.getName() + " " + fbean.getAge());
		fbean.getaBean().doSomthing();
	}

}
