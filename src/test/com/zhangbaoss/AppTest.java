package com.zhangbaoss;

import com.zhangbaoss.bean.SpringIocXmlBean;
import com.zhangbaoss.ioc.ExtClassPathXmlApplicationContext;
import com.zhangbaoss.ioc.utils.ClassUtils;
import org.junit.Test;

/**
 * 〈一句话功能简述〉<br>
 * 〈测试类〉
 *
 * @author a1638
 * @create 2019/1/11
 * @since 1.0.0
 */
public class AppTest {

	@Test
	public void test1() throws Exception {
		ClassUtils.getClassesFormPackage("applicationContext.xml");
	}
}
