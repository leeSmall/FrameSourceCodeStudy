package com.study.leesmall.spring.ext;

import java.io.IOException;

import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.MetadataReaderFactory;
import org.springframework.core.type.filter.TypeFilter;

import com.study.leesmall.spring.service.Abean;

public class MyTypeFilter implements TypeFilter {

	@Override
	public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory)
			throws IOException {
		// 使用metadataReader中的类信息、注解信息来进行你的过滤判断逻辑
		return metadataReader.getClassMetadata().getClassName().equals(Abean.class.getName());
	}

}
