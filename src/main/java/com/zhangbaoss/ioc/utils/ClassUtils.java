package com.zhangbaoss.ioc.utils;

import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.List;

/**
 * 〈一句话功能简述〉<br>
 * 〈Class类工具类〉
 *
 * @author a1638
 * @create 2019/1/12
 * @since 1.0.0
 */
public class ClassUtils {

	private static List<String> classes;

	private static String packageName = null;

	private static final String CLASS_SUFFIX = ".class";

	static {
		classes = new ArrayList<>();
	}

	public static List<String> getClassesFormPackage(String xmlPath) throws Exception {
		//1.获取包名
		packageName = getPackageName(xmlPath);
		if (StringUtils.isEmpty(packageName)) {
			throw new Exception("获取需要扫描的包失败!");
		}
		//2.获取扫描包路径
		String packagePath = getPackagePath();
		if (StringUtils.isEmpty(packagePath)) {
			throw new Exception("获取需要扫描的包的路径失败!");
		}
		//3.获取该包下所有类,放入集合内
		getClassForPath(new File(packagePath), getPath());
		return classes;
	}

	/**
	 * 获取需要扫描的包
	 * @param path
	 * @return
	 * @throws Exception
	 */
	private static String getPackageName(String path) throws Exception {
		SAXReader reader = new SAXReader();
		//读取配置文件
		Document document = reader.read(getResourceAsStream(path));
		if (document == null) {
			throw new Exception("配置文件读取失败!");
		}
		//获取根节点
		Element rootElement = document.getRootElement();
		if (rootElement == null) {
			throw new Exception("配置文件的根节点获取失败!");
		}
		//获取根节点下名字为"component-scan"的子节点
		Element element = rootElement.element("component-scan");
		if (element == null) {
			throw new Exception("没有需要扫描的包路径!");
		}
		return getAttributeValue(element);
	}

	/**
	 * 获取需要扫描的包地址
	 * @param element
	 * @return
	 */
	private static String getAttributeValue(Element element) {
		return element.attributeValue("base-package");
	}

	/**
	 * 获取项目路径
	 * @return
	 */
	private static String getPath() throws UnsupportedEncodingException {
		String classPath = ClassUtils.class.getResource("/").getPath();
		//解决path乱码
		classPath = URLDecoder.decode(classPath, "utf-8");
		return classPath;
	}

	private static InputStream getResourceAsStream(String path) {
		return ClassUtils.class.getClassLoader().getResourceAsStream(path);
	}

	/**
	 * 获取扫描包路径
	 * @return
	 */
	private static String getPackagePath() throws UnsupportedEncodingException {
		//1.获取项目类路径
		String classPath = getPath();
		//2.将包名转换为类名
		String packagePath = packageName.replace(".", File.separator);
		//将项目路径和包路径合在一起
		packagePath = classPath + packagePath;
		return packagePath;
	}

	/**
	 * 获取该包下所有类,放入集合内
	 * @param packageFile
	 */
	private static void getClassForPath(File packageFile, String packagePath) {
		if (packageFile.isDirectory()) {
			File[] files = packageFile.listFiles();
			for (File file : files) {
				getClassForPath(file, packagePath);
			}
		} else {
			String classPath = packageFile.getPath();
			if (packageFile.getName().endsWith(CLASS_SUFFIX)) {
				String className = classPath.replace(packagePath.replace("/","\\").replaceFirst("\\\\",""),"").replace("\\",".").replace(".class","");
				classes.add(className);
			}
		}
	}
}
