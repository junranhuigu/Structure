package com.structure;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Descirption: 线段管理者
 * 
 * @author jiawei29 
 * @date 2020-02-03 19:14
 * */
public class Line<T> {
    /**
     * 线段部分
     */
	private ArrayList<Part> lines = new ArrayList<>();
	/**
	 * 线段总值
	 */
	@Getter
	private int total = 0;
	
	/**
	 * 添加一个对象
	 * */
	public boolean add(int length, T attachment){
		if(length <= 0){
			return false;
		}
		Part line = new Part(length, attachment);
		total += length;
		lines.add(line);
		return true;
	}
	
	/**
	 * 传入一个值，删除在此范围的Part
	 *  @param value : value >= 0 && value < getTotal()
	 *  @return 被删除的附件
	 * */
	public T remove(int value){
		Part part = getPart(value);
		if(part != null){
			lines.remove(part);
			total -= part.getLength();
			return part.getAttachment();
		} else {
			return null;
		}
	}
	
	/**
	 * 删除Part
	 * */
	public boolean remove(Part part){
		return lines.remove(part);
	}
	
	/**
	 * 传入一个值，重定义在此范围的Part的长度
	 * @param changedValue : value >= 0 && value < getTotal()
	 * */
	public boolean resetLength(Part part, int changedValue){
		if(part != null){
			int afterLength = changedValue + part.getLength();
			if(afterLength > 0){
				part.setLength(afterLength);
				total += changedValue;
			} else {
				remove(part);
			}
			return true;
		} else {
			return false;
		}
	}
	
	/**
	 * 根据传入的附件获取Part
	 * */
	public Part getPart(T attachment){
		for(Part part : lines){
			if(attachment.equals(part.getAttachment())){
				return part;
			}
		}
		return null;
	}
	
	/**
	 * 添加对象个数
	 */
	public int size(){
		return lines.size();
	}

	/**
	 * 传入一个值，获取在此范围的附件
	 * @param value : value >= 0 && value < getTotal()
	 * */
	public T get(int value){
		Part part = getPart(value);
		return part == null ? null : part.getAttachment();
	}
	
	/**
	 * 传入一个值，获取在此范围的end值
	 * @param value : value >= 0 && value < getTotal()
	 * */
	public int getEnd(int value){
		int end = 0;
		Part part = getPart(value);
		if(part != null){
			for(Part p : lines){
				end += p.getLength();
				if(p.equals(part)){
					break;
				}
			}
			end -= 1;
		}
		return end;
	}
	
	/**
	 * 传入一个值，获取在此范围的begin值
	 * @param value : value >= 0 && value < getTotal()
	 * */
	public int getBegin(int value){
		int begin = 0;
		Part part = getPart(value);
		if(part != null){
			for(Part p : lines){
				if(p.equals(part)){
					break;
				}
				begin += p.getLength();
			}
		}
		return begin;
	}
	
	/**
	 * 传入一个值，获取在此范围的Part的索引值
	 * */
	public int getIndex(int value){
		Part part = getPart(value);
		if(part != null){
			for(int i = 0; i < lines.size(); ++ i){
				if(lines.get(i).equals(part)){
					return i;
				}
			}
		}
		return -1;
	}
	
	/**
	 * 传入一个值，获取在此范围的Part
	 * */
	public Part getPart(int value){
		++ value;
		if(value > 0 && value <= total && lines.size() > 0){
			for(Part line : lines){
				value -= line.getLength();
				if(value <= 0){
					return line;
				}
			}
		}
		if(value > total && lines.size() > 0){// 超出最大值 返回最后一个候选项
			return lines.get(lines.size() - 1);
		}
		return null;
	}
	
	/**
	 * 传入两个值，获取在此范围的所有附件
	 * @param end -1默认到最后
	 * */
	public List<T> gets(int begin, int end){
		if(begin >= end){
			return Collections.emptyList();
		}
		List<T> result = new ArrayList<>();
		int beginIndex = getIndex(begin);
		int endIndex = getIndex(end);//为-1默认到最后
		if(beginIndex >= 0){
			if(endIndex < 0){
				endIndex = lines.size() - 1;
			}
			for(int i = beginIndex; i <= endIndex; ++ i){
				result.add(lines.get(i).getAttachment());
			}
		}
		return result;
	}
	
	/**
	 * 随机获取一个线段
	 */
	public T random(){
		int random = (int) (Math.random() * total);
		return get(random);
	}
	
	/**
	 * 随机获取一个线段并删除待选项
	 */
	public T randomAndRemove(){
		int random = (int) (Math.random() * total);
		T result = get(random);
		remove(random);
		return result;
	}
	
	/**
	 * 部分： 数据结构 
	 * @author jiawei
     * @date 2020-02-03 19:16
	 * */
	@Data
    @AllArgsConstructor
	public class Part {
	    /**
	     * 长度
	     */
		int length;
		/**
		 * 附件
		 */
		T attachment;
	}
}
