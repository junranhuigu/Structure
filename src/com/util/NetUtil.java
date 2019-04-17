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
	
     /**
     * 获取客户端真实IP地址
     * @param request
     * @return
     */
    public static String getIpAddr(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }

        // 2015-12-24 liudong 增加 从HTTP_CLIENT_IP和HTTP_X_FORWARDED_FOR 获得 ip
        // HTTP_X_FORWARDED_FOR，如果通过了多级反向代理的话，X-Forwarded-For的值并不止一个，而是一串ip值，取X-Forwarded-For中第一个非unknown的有效IP字符串
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }

        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        if(checkRealIp(ip) && ip.indexOf(",") > 0){
            // ip字符串，取第一个正常的ip
            for(String ipStr : ip.split(",")){
                if(checkRealIp(ipStr)){
                    ip = ipStr;
                    return ip;
                }
            }
        }

        return ip;
    }
	
	public static void main(String[] args) {
		System.out.println(isIPv4("256.1.1.1"));
		System.out.println("256.1.1.1".split("\\.").length);
	}
}
