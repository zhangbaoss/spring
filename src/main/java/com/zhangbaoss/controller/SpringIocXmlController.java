package com.zhangbaoss.controller;

import com.zhangbaoss.ioc.annotation.ExtAutowired;
import com.zhangbaoss.ioc.annotation.ExtController;
import com.zhangbaoss.service.SpringIocXmlService;

/**
 * 〈一句话功能简述〉<br>
 * 〈Spring的控制器〉
 *
 * @author a1638
 * @create 2019/1/11
 * @since 1.0.0
 */
@ExtController
public class SpringIocXmlController {

	@ExtAutowired
	private SpringIocXmlService springIocXmlService;

	public void test() {
		System.out.println("到达controller层");
		springIocXmlService.test();
	}
}
