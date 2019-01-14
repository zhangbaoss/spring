package com.zhangbaoss.ioc;

import com.zhangbaoss.ioc.annotation.*;
import com.zhangbaoss.ioc.utils.ClassUtils;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 〈一句话功能简述〉<br>
 * 〈ioc容器〉
 *
 * @author a1638
 * @create 2019/1/11
 * @since 1.0.0
 */
public class ExtClassPathXmlApplicationContext {

	/**配置文件路径*/
	private String path;

	private static ConcurrentHashMap<String, Object> classMap;

	public ExtClassPathXmlApplicationContext(String path) throws Exception {
		this.path = path;
		classMap = new ConcurrentHashMap<>();
		initBeans();
	}

	/**
	 * 初始化所有bean
	 * @throws Exception
	 */
	private void initBeans() throws Exception {
		//1.获取当下包中所有类
		List<String> classes = ClassUtils.getClassesFormPackage(path);
		if (classes == null || classes.isEmpty()) {
			throw new Exception("该包下没有任何类");
		}
		//2.查看类上是否有特定注解并初始化map
		initClassMap(classes);
		//3.初始化字段值
		initField();
	}

	/**
	 * 初始化map
	 * @param classes
	 * @throws Exception
	 */
	private void initClassMap(List<String> classes) throws Exception {
		for (String className : classes) {
			Class clazz = Class.forName(className);
			if (clazz.isAnnotationPresent(ExtController.class)) {
				String value = ((ExtController) clazz.getAnnotation(ExtController.class)).value();
				classMapPutValue(value, className, clazz);
			} else if (clazz.isAnnotationPresent(ExtService.class)) {
				String value = ((ExtService) clazz.getAnnotation(ExtService.class)).value();
				classMapPutValue(value, className, clazz);
			} else if (clazz.isAnnotationPresent(ExtRepository.class)) {
				String value = ((ExtRepository) clazz.getAnnotation(ExtRepository.class)).value();
				classMapPutValue(value, className, clazz);
			} else if (clazz.isAnnotationPresent(ExtComponent.class)) {
				String value = ((ExtComponent) clazz.getAnnotation(ExtComponent.class)).value();
				classMapPutValue(value, className, clazz);
			}
		}
	}

	/**
	 * 初始化字段值
	 * @throws IllegalAccessException
	 */
	private void initField() throws IllegalAccessException {
		Set<Map.Entry<String, Object>> entries = classMap.entrySet();
		for (Map.Entry<String, Object> entity : entries) {
			Object object = entity.getValue();
			Field[] fields = entity.getValue().getClass().getDeclaredFields();
			if (fields != null && fields.length > 0) {
				//注入对象
				for (Field field : fields) {
					if (field.isAnnotationPresent(ExtAutowired.class)) {
						//设置private字段可赋值
						field.setAccessible(true);
						field.set(object, classMap.get(field.getName()));
					}
				}
			}
		}

	}

	/**
	 * 填充map
	 * @param value
	 * @param className
	 * @param clazz
	 * @throws Exception
	 */
	private void classMapPutValue(String value, String className, Class clazz) throws Exception {
		value = value == null || "".equals(value) ?
				toLowerCaseFirstOne(
						className.substring(className.lastIndexOf(".") + 1)) : value;
		Object object = clazz.newInstance();
		classMap.put(value, object);
	}

	/**
	 * 首字母转小写
	 * @param str
	 * @return
	 */
	private static String toLowerCaseFirstOne(String str){
		if(Character.isLowerCase(str.charAt(0))) {
			return str;
		}
		else {
			return (new StringBuilder())
					.append(Character.toLowerCase(str.charAt(0)))
					.append(str.substring(1))
					.toString();
		}
	}

	/**
	 * 首字母转大写
	 * @param str
	 * @return
	 */
	private static String toUpperCaseFirstOne(String str){
		if(Character.isUpperCase(str.charAt(0))) {
			return str;
		}
		else {
			return (new StringBuilder())
					.append(Character.toUpperCase(str.charAt(0)))
					.append(str.substring(1))
					.toString();
		}
	}

	public Object getBean(String beanName) {
		return classMap.get(beanName);
	}
}
