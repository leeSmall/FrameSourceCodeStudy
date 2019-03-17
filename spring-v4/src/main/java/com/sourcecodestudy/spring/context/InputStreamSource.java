package com.sourcecodestudy.spring.context;

import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @Description: 加载不同的xml配置文件得到最终产出流InputStream的借口 
 * 在Resource接口里面实现
 * 
 * 
 * @author leeSmall
 * @date 2018年12月7日
 *
 */
public interface InputStreamSource {
	InputStream getInputStream() throws IOException;
}
