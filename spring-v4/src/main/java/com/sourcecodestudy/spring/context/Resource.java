package com.sourcecodestudy.spring.context;

import java.io.File;

/**
 * 
 * @Description: 加载不同的xml配置文件得到最终产出流InputStream接口
 * 在具体类(FileSystemResource、ClassPathResource、UrlResource)里面实现
 * 
 * 
 * @author leeSmall
 * @date 2018年12月7日
 *
 */
public interface Resource extends InputStreamSource {

	String CLASS_PATH_PREFIX = "classpath:";

	String File_SYSTEM_PREFIX = "file:";

	boolean exists();

	boolean isReadable();

	boolean isOpen();

	File getFile();
}
