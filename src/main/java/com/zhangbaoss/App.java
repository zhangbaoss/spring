package com.zhangbaoss;

import com.zhangbaoss.controller.SpringIocXmlController;
import com.zhangbaoss.ioc.ExtClassPathXmlApplicationContext;

/**
 * 〈一句话功能简述〉<br>
 * 〈程序入口类〉
 *
 * @author a1638
 * @create 2019/1/12
 * @since 1.0.0
 */
public class App {

	public static void main(String[] args) throws Exception {
		ExtClassPathXmlApplicationContext app =
				new ExtClassPathXmlApplicationContext("applicationContext.xml");
		SpringIocXmlController springIocXmlController =
				(SpringIocXmlController) app.getBean("springIocXmlController");
		springIocXmlController.test();
	}
}
