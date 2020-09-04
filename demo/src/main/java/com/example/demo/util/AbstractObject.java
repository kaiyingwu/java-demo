package com.example.demo.util;

/**
 * 基础POJO类
 * @Author: hzzhouwen
 * @Date: 2018/3/28 15:57
 */
public class AbstractObject {

	/**
	 * 浅度克隆
	 */
	public <T> T clone(Class<T> clazz) throws Exception {
		T target = clazz.newInstance();
		BeanCopierUtils.copyProperties(this, target);
		return target;
	}

	/**
	 * 浅度克隆
	 */
	public <T> T clone(T target) throws Exception {
		BeanCopierUtils.copyProperties(this, target);
		return target;
	}
}
