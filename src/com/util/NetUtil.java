package com.util;

import java.util.regex.Pattern;

public class NetUtil {
	/**
	 * 判断ip地址是否为内网
	 * */
	public static boolean isIntranetIPv4(String ip){
		if(!isIPv4(ip)){
			return false;
		}
		String[] ips = ip.split("\\.");
		/**
		 * tcp/ip协议中，专门保留了三个IP地址区域作为私有地址，其地址范围如下：
		 * 10.0.0.0/8：10.0.0.0～10.255.255.255 
		 * 172.16.0.0/12：172.16.0.0～172.31.255.255 
		 * 192.168.0.0/16：192.168.0.0～192.168.255.255
		 * */
		switch (Integer.parseInt(ips[0])) {
		case 10:
			return true;
		case 172:
			if(Integer.parseInt(ips[1]) >= 16 && Integer.parseInt(ips[1]) <= 31){
				return true;
			}
		case 192:
			if(Integer.parseInt(ips[1]) == 168){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 判定是否是合法的ip地址
	 * */
	public static boolean isIPv4(String ip){
		if(!Pattern.matches("(\\d{1,3}\\.){3,3}\\d{1,3}", ip)){
			return false;
		}
		String[] ips = ip.split("\\.");
		for(String i : ips){
			int _i = Integer.parseInt(i);
			if(_i > 255 || _i < 0){
				return false;
			}
		}
		return true;
	}
	
	public static void main(String[] args) {
		System.out.println(isIPv4("256.1.1.1"));
		System.out.println("256.1.1.1".split("\\.").length);
	}
}
