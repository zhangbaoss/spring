package com.zhangbaoss.service.impl;

import com.zhangbaoss.dao.SpringIocXmlDao;
import com.zhangbaoss.ioc.annotation.ExtAutowired;
import com.zhangbaoss.ioc.annotation.ExtService;
import com.zhangbaoss.service.SpringIocXmlService;

/**
 * 〈一句话功能简述〉<br>
 * 〈Spring的Service层〉
 *
 * @author a1638
 * @create 2019/1/11
 * @since 1.0.0
 */
@ExtService("springIocXmlService")
public class SpringIocXmlServiceImpl implements SpringIocXmlService {

	@ExtAutowired
	private SpringIocXmlDao springIocXmlDao;

	@Override
	public void test() {
		System.out.println("到达service层");
		springIocXmlDao.test();
	}
}
