package com.jl;

import com.jl.utils.PropertiesUtil;

/**
 * @ClassName: CGConfig
 * @Description: 自动代码生成常量定义
 * @author: Jinlong He
 * @date: 2017年6月20日 下午4:23:44
 */
public class CGConfig {
	
	/**
	 * @fieldName: source_root_package
	 * @fieldType: String
	 * @Description: 代码根目录
	 */
	public static String source_root_package = "/src";
	
	/**
	 * @fieldName: business_package
	 * @fieldType: String
	 * @Description: 业务类包目录
	 */
	public static String business_package = "com.hnjing.code";
	
	/**
	 * @fieldName: system_encoding
	 * @fieldType: String
	 * @Description: 字符编码
	 */
	public static String system_encoding = "utf-8";
	
	public static String template_path = "/resources/template";
	
	//初始化加载
	static {
		PropertiesUtil pu = PropertiesUtil.Util(null);
		source_root_package = pu.readValue("source_root_package");
		business_package = pu.readValue("business_package");
		system_encoding = pu.readValue("system_encoding");
		
	}
	
	/** 
	* @Title: projectSystemPath 
	* @Description: 系统路径
	* @return  String    返回类型 
	* @throws 
	*/
	public static String projectSystemPath() {
		String path = System.getProperty("user.dir").replace("\\", "/");
		return path;
	}
	
	
	public static void main(String[] arg){		
		System.out.println(projectSystemPath());
	}

}
