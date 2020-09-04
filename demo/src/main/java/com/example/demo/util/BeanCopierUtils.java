package com.example.demo.util;

import net.sf.cglib.beans.BeanCopier;

import java.util.HashMap;
import java.util.Map;

/**
 * BeanCopier工具类
 * @Author: zhouwen
 * @Date: 2018/3/28 15:46
 */
public class BeanCopierUtils {

	private static Map<String, BeanCopier> beanCopierMap = new HashMap<>();


	/**
	 * 讲source对象中的属性拷贝到target对象中去
	 * @param source source对象
	 * @param target target对象
	 */
	public static void copyProperties(Object source, Object target) {
		String beanKey = source.getClass().toString() + target.getClass().toString();

		BeanCopier copier = null;
		if (!beanCopierMap.containsKey(copier)) {
			synchronized (BeanCopierUtils.class) {
				if (!beanCopierMap.containsKey(copier)) {
					copier = BeanCopier.create(source.getClass(), target.getClass(), false);
					beanCopierMap.put(beanKey, copier);
				} else {
					copier = beanCopierMap.get(beanKey);
				}
			}
		} else {
			copier = beanCopierMap.get(beanKey);
		}
		copier.copy(source, target, null);
	}
}
