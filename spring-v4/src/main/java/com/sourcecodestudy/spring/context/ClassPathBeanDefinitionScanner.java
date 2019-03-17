package com.sourcecodestudy.spring.context;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.PathMatcher;

import com.sourcecodestudy.spring.beans.BeanDefinitionRegistry;

/**
 * 
 * @Description: 
 * 扫描指定包下的类(包含子孙包下的类),
 * 通过反射获取bean定义信息、创建bean定义对象、注册bean定义对象到bean工厂
 * 
 * @author leeSmall
 * @date 2018年12月7日
 *
 */
public class ClassPathBeanDefinitionScanner {

	private static Log logger = LogFactory.getLog(ClassPathBeanDefinitionScanner.class);

	//bean定义注册器
	private BeanDefinitionRegistry registry;

	//bean定义读取器
	private BeanDefinitionReader reader;

	private PathMatcher pathMatcher = new AntPathMatcher();

	private String resourcePatter = "**/*.class";

	public ClassPathBeanDefinitionScanner(BeanDefinitionRegistry registry) {
		super();
		this.registry = registry;
		this.reader = new AnnotationBeanDefintionReader(this.registry);
	}

	//扫描加载bean定义
	public void scan(String... basePackages) throws Throwable {
		if (basePackages != null && basePackages.length > 0) {
			for (String p : basePackages) {
				this.reader.loadBeanDefintions(this.doScan(p));
			}
		}
	}

	//扫描指定包下的类得到Resource
	private Resource[] doScan(String basePackage) throws IOException {
		// 扫描包下的类
		// 构造初步匹配模式串，= 给入的包串 + / + **/*.class，替换里面的.为/
		String pathPattern = StringUtils.replace(basePackage, ".", "/") + "/" + this.resourcePatter;
		if (pathPattern.charAt(0) != '/') {
			pathPattern = "/" + pathPattern;
		}
		// 找出模式的根包路径
		String rootPath = this.determineRootDir(pathPattern);
		// 得到文件名匹配的绝对路径模式
		String fullPattern = this.getClass().getResource("/").toString() + pathPattern;
		// 根据根包理解得到根包对应的目录
		File rootDir = new File(this.getClass().getResource(rootPath).toString());
		// 存放找到的类文件的resource集合
		Set<Resource> scanedClassFileResources = new HashSet<>();
		// 调用doRetrieveMatchingFiles来扫描class文件
		this.doRetrieveMatchingFiles(fullPattern, rootDir, scanedClassFileResources);
		return (Resource[]) scanedClassFileResources.toArray();
	}

	private String determineRootDir(String location) {
		int rootDirEnd = location.length();
		rootDirEnd = location.indexOf('*');
		int zi = location.indexOf('?');
		if (zi != -1 && zi < rootDirEnd) {
			rootDirEnd = location.lastIndexOf('/', zi);
		}
		if (rootDirEnd != -1) {
			return location.substring(0, rootDirEnd);
		} else {
			return location;
		}
	}

	/**
	 * 递归找指定目录下的所有类，匹配模式的加入到结果中。
	 * 
	 * @param fullPattern
	 * @param dir
	 * @param result
	 * @throws IOException
	 */
	protected void doRetrieveMatchingFiles(String fullPattern, File dir, Set<Resource> result) throws IOException {
		if (logger.isTraceEnabled()) {
			logger.trace("Searching directory [" + dir.getAbsolutePath() + "] for files matching pattern ["
					+ fullPattern + "]");
		}
		for (File content : listDirectory(dir)) {
			String currPath = StringUtils.replace(content.getAbsolutePath(), File.separator, "/");
			if (content.isDirectory() && getPathMatcher().matchStart(fullPattern, currPath + "/")) {
				if (!content.canRead()) {
					if (logger.isDebugEnabled()) {
						logger.debug("Skipping subdirectory [" + dir.getAbsolutePath()
								+ "] because the application is not allowed to read the directory");
					}
				} else {
					doRetrieveMatchingFiles(fullPattern, content, result);
				}
			}
			if (getPathMatcher().match(fullPattern, currPath)) {
				result.add(new FileSystemResource(content));
			}
		}
	}

	protected File[] listDirectory(File dir) {
		File[] files = dir.listFiles();
		if (files == null) {
			if (logger.isInfoEnabled()) {
				logger.info("Could not retrieve contents of directory [" + dir.getAbsolutePath() + "]");
			}
			return new File[0];
		}
		Arrays.sort(files, Comparator.comparing(File::getName));
		return files;
	}

	public BeanDefinitionRegistry getRegistry() {
		return registry;
	}

	public void setRegistry(BeanDefinitionRegistry registry) {
		this.registry = registry;
	}

	public BeanDefinitionReader getReader() {
		return reader;
	}

	public void setReader(BeanDefinitionReader reader) {
		this.reader = reader;
	}

	public PathMatcher getPathMatcher() {
		return pathMatcher;
	}

	public void setPathMatcher(PathMatcher pathMatcher) {
		this.pathMatcher = pathMatcher;
	}

	public String getResourcePatter() {
		return resourcePatter;
	}

	public void setResourcePatter(String resourcePatter) {
		this.resourcePatter = resourcePatter;
	}
}
