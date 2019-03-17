package com.sourcecodestudy.spring.context;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 
 * @Description: 从文件系统里面加载xml配置文件得到InputStream
 * 
 * 
 * @author leeSmall
 * @date 2018年12月7日
 *
 */
public class FileSystemResource implements Resource {

	//文件
	private File file;

	public FileSystemResource(String fileName) {
		this.file = new File(fileName);
	}

	public FileSystemResource(File file) {
		super();
		this.file = file;
	}

	//得到InputStream
	@Override
	public InputStream getInputStream() throws IOException {
		return new FileInputStream(this.file);
	}

	@Override
	public boolean exists() {
		return this.file == null ? false : file.exists();
	}

	@Override
	public boolean isReadable() {
		return this.file == null ? false : file.canRead();
	}

	@Override
	public boolean isOpen() {
		return false;
	}

	@Override
	public File getFile() {
		return file;
	}

}
