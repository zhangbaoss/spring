package com.zhangbaoss.bean;

import lombok.Data;

/**
 * 〈一句话功能简述〉<br>
 * 〈spring的bean〉
 *
 * @author a1638
 * @create 2019/1/11
 * @since 1.0.0
 */
@Data
public class SpringIocXmlBean {

	private String id;

	private String name;

	private Integer age;

	private Double price;

	public SpringIocXmlBean() {
		System.out.println("到达SpringIocXmlBean无参构造器");
	}
}
