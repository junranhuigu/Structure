package com.util;

import java.lang.reflect.Array;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

public class ClassUtil {
	private static ClassUtil instance;
	private List<Class> compareClsList1;//用于排列 属性优先级的队列
	private static Class[] cs = {String.class, Short.class, Byte.class, Integer.class, Long.class, Boolean.class, Character.class, Float.class, Double.class, byte.class, short.class, int.class, long.class, double.class, float.class, char.class, boolean.class};
	
	private ClassUtil() {
		compareClsList1 = new ArrayList<>();
		compareClsList1.add(byte.class);
		compareClsList1.add(short.class);
		compareClsList1.add(int.class);
		compareClsList1.add(float.class);
		compareClsList1.add(long.class);
		compareClsList1.add(String.class);
	}

	public static ClassUtil getInstance() {
		if(instance == null){
			instance = new ClassUtil();
		}
		return instance;
	}
	
	/**
	 * 根据输入的内容判断可能是什么类型
	 * @param pack 是否需要封装类
	 * */
	public Class<?> baseClass(String content, boolean pack){
		if(Pattern.matches("-?[0-9]{1,}(\\.[0-9]{1,})?", content)){//数值类型
			if(content.contains(".")){
				return pack ? Float.class : float.class;
			}
			long _l = Long.parseLong(content);
			if(_l >= Integer.MAX_VALUE || _l <= Integer.MIN_VALUE){
				return pack ? Long.class : long.class;
			} else {
				return pack ? Integer.class : int.class;
			}
		} else {
			if("true".equals(content) || "false".equals(content)){
				return pack ? Boolean.class : boolean.class;
			}
			return String.class;
		}
	}
	
	/**
	 * 类型优先级
	 * */
	public Class<?> compareBathClass(Class<?> c1, Class<?> c2){
		int index1 = compareClsList1.indexOf(c1);
		int index2 = compareClsList1.indexOf(c2);
		return index1 > index2 ? c1 : c2;
	}
	
	/**
	 * 判断某个类是否为基础类的封装类或者String
	 * */
	public boolean inSimpleEncapsolution(Class<?> cls){
		for(Class<?> c : cs){
			if(c == cls){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判断某个对象是否存在闭环<br/>
	 * 如果存在List、Map或者数组，该方法仅能支持一重嵌套的检测，多重嵌套的闭环无法检测
	 * */
	public boolean hasCircle(Object obj, HashSet<Object> sets) {
		if(obj == null || inSimpleEncapsolution(obj.getClass())){
			return false;
		}
		if(!sets.add(obj)){
			return true;
		}
		for(Field field : obj.getClass().getDeclaredFields()){
			try {
				Method method = obj.getClass().getMethod("get" + StringUtil.upperFirstLetter(field.getName()), null);
				Object o = method.invoke(obj, null);
				if(o != null){
					if(o.getClass().isArray()){
						int len = Array.getLength(o);
						for(int i = 0; i < len; ++ i){
							Object _o = Array.get(o, i);
							if(hasCircle(_o, sets)){
								return true;
							}
						}
					} else if(o instanceof Collection){
						Collection c = (Collection) o;
						for(Object _o : c){
							if(hasCircle(_o, sets)){
								return true;
							}
						}
					} else if(o instanceof Map){
						Map map = (Map) o;
						for(Object k : map.keySet()){
							if(hasCircle(k, sets)){
								return true;
							}
						}
						for(Object v : map.values()){
							if(hasCircle(v, sets)){
								return true;
							}
						}
					} else {
						if(hasCircle(o, sets)){
							return true;
						}
					}
				}
			} catch (Exception e) {}
		}
		return false;
	}
	
}
