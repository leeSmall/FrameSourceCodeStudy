package com.sourcecodestudy.spring.aop.advisor;

import java.util.List;

/**
 * 
 * @Description: Advisor注册接口
 * @author leeSamll
 * @date 2018年12月2日
 *
 */
public interface AdvisorRegistry {

	//注册Advisor
	public void registAdvisor(Advisor ad);

	//获取Advisor
	public List<Advisor> getAdvisors();
}
