package com.zhangbaoss.ioc;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;

public interface BeanFactory {

	/**
	 * 对FactoryBean的转义定义,因为如果使用bean的名字检索FactoryBean得到的对象
	 * 是工厂生成的对象,如果需要得到工厂本身,需要转义
	 */
	String FACTORY_BEAN_PREFIX = "&";

	/**
	 * 根据bean的名字,获取在IOC容器中得到的bean实例
	 * @param name bean的名字
	 * @return
	 * @throws BeansException
	 */
	Object getBean(String name) throws BeansException;

	/**
	 * 根据bean的名字和Class类型来得到bean的实例,增加了类型安全验证机制
	 * @param name bean的名字
	 * @param requiredType bean的类型
	 * @return
	 * @throws BeansException
	 */
	Object getBean(String name, Class requiredType) throws BeansException;

	/**
	 * 提供对bean的检索,看看是否在IOC容器中有这个名字的bean
	 * @param name bean名字
	 * @return
	 */
	boolean containsBean(String name);

	/**
	 * 根据bean名字得到bean实例,同时判断这个bean是不是单例
	 * @param name bean名字
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	boolean isSingleton(String name) throws NoSuchBeanDefinitionException;

	/**
	 * 得到bean实例的Class类型
	 * @param name bean的名字
	 * @return
	 * @throws NoSuchBeanDefinitionException
	 */
	Class getType(String name) throws NoSuchBeanDefinitionException;

	/**
	 * 得到bean的别名,如果根据别名检索,那么其原名也会被检索出来
	 * @param name bean的名字
	 * @return
	 */
	String[] getAliases(String name);

}
