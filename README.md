# 如何手写IOC容器 #
## 一、准备工作 ##
### 1.配置文件中添加扫描包路径 ###
#### applicationContext.xml文件中添加扫描包路径 ####
    <context:component-scan base-package="com.zhangbaoss"/>
### 2.实现自己的注解类 ###
#### @ExtController ####
    @Target({ElementType.TYPE})
	@Retention(RetentionPolicy.RUNTIME)
	public @interface ExtController {
		String value() default "";
	}
## 二、获取扫描包下所有类(ClassUtils.java) ##
    List<String> classes = ClassUtils.getClassesFormPackage(path);
### 1.根据配置文件路径获取配置文件中需要扫描的包名 ###
    packageName = getPackageName(xmlPath);
### 2.获取需要扫描的包的路径 ###
    String packagePath = getPackagePath();
### 3.将需要扫描的包下的所有类放入集合中 ###
    getClassForPath(new File(packagePath), getPath());
## 三、查看类上是否有特定注解并初始化map ##
    initClassMap(classes);
### initClassMap(List<String> classes) ###
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
## 四、初始化字段值 ##
    initField();
### initField() ###
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
## 五、测试方法 ##
    public static void main(String[] args) throws Exception {
		ExtClassPathXmlApplicationContext app =
				new ExtClassPathXmlApplicationContext("applicationContext.xml");
		SpringIocXmlController springIocXmlController =
				(SpringIocXmlController) app.getBean("springIocXmlController");
		springIocXmlController.test();
	}