package com.structure;

import java.util.HashMap;
import java.util.List;

/**
 * 计数器<br/>
 * PS:通过类的equals方法进行对比
 * */
public class Counter<T> {
	private HashMap<T, Integer> map;
	
	public Counter() {
		this.map = new HashMap<>();
	}
	
	public void add(T t){
		if(t == null){
			return;
		}
		boolean has = false;
		for(T _t : map.keySet()){
			if(_t.equals(t)){
				has = true;
				map.put(_t, map.get(_t) + 1);
				break;
			}
		}
		if(!has){
			map.put(t, 1);
		}
	}
	
	public void add(List<T> ts){
		if(ts != null && !ts.isEmpty()){
			for(T t : ts){
				add(t);
			}
		}
	}
	
	/**
	 * 获取统计后的结果
	 * @return key是某个对象 value是该对象出现的次数
	 * */
	public HashMap<T, Integer> getResult(){
		return map;
	}
}
