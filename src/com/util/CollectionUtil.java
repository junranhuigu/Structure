package com.util;

import java.util.ArrayList;
import java.util.List;

public class CollectionUtil {
	/**
	 * 集合A是否是集合U的子集
	 * */
	public static <T> boolean isSubset(List<T> a, List<T> u){
		for(T at : a){
			boolean has = false;
			for(T ut : u){
				if(at.equals(ut)){
					has = true;
					break;
				}
			}
			if(!has){
				return false;
			}
		}
		return true;
	}
	/**
	 * 集合A与集合B的并集
	 * */
	public static <T> List<T> union(List<T> a, List<T> b){
		List<T> result = new ArrayList<>();
		List<T> alls = new ArrayList<>(a);
		alls.addAll(b);
		for(T t : alls){
			boolean has = false;
			for(T r : result){
				if(r.equals(t)){
					has = true;
					break;
				}
			}
			if(!has){
				result.add(t);
			}
		}
		return result;
	}
	/**
	 * 集合A与集合B的交集
	 * */
	public static <T> List<T> intersection(List<T> a, List<T> b){
		List<T> result = new ArrayList<>();
		for(T t : a){
			for(T t2 : b){
				if(t.equals(t2)){
					result.add(t);
					break;
				}
			}
		}
		return result;
	}
	/**
	 * 集合A与集合B的对称差，即只在集合A及B中的其中一个出现，没有在其交集中出现的元素
	 * */
	public static <T> List<T> symmetryDifference(List<T> a, List<T> b){
		List<T> result = difference(a, b);
		result.addAll(difference(b, a));
		return result;
	}
	/**
	 * 集合A与集合B的差集，即只在集合A中出现，没有在集合B中出现的元素
	 * */
	public static <T> List<T> difference(List<T> a, List<T> b){
		List<T> result = new ArrayList<>();
		for(T at : a){
			boolean has = false;
			for(T bt : b){
				if(at.equals(bt)){
					has = true;
					break;
				}
			}
			if(!has){
				result.add(at);
			}
		}
		return result;
	}
}
