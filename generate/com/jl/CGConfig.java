package com.jl;

import com.jl.utils.PropertiesUtil;

/**
 * @ClassName: CGConfig
 * @Description: 自动代码生成常量参数定义
 * @author: Jinlong He
 * @date: 2017年6月20日 下午4:23:44
 */
public class CGConfig {
	
	/**
	 * @fieldName: system_encoding
	 * @fieldType: String
	 * @Description: 字符编码
	 */
	public static String system_encoding = "utf-8";
	
	/**
	 * @fieldName: source_root_package
	 * @fieldType: String
	 * @Description: 代码根目录
	 */
	public static String source_root_package = "/src";
	
	/**
	 * @fieldName: business_package
	 * @fieldType: String
	 * @Description: 业务类包目录-后台
	 */
	public static String business_package = "com.hnjing.code";
	
	/**
	 * @fieldName: business_package
	 * @fieldType: String
	 * @Description: 业务类包目录-前台
	 */
	public static String business_package_web = "/web";
	
	/**
	 * @fieldName: business_package_total
	 * @fieldType: String
	 * @Description: 业务类包目录-统计
	 */
	public static String business_package_total = "";	
	
	/**
	 * @fieldName: template_path
	 * @fieldType: String
	 * @Description: 模板存放目录
	 */
	public static String template_path = "/resources/template";
	
	/**
	 * @fieldName: insertCloumn
	 * @fieldType: String
	 * @Description: 新增时间字段
	 */
	public static String insertCloumn = "";
	/**
	 * @fieldName: updateCloumn
	 * @fieldType: String
	 * @Description: 修订时间字段
	 */
	public static String updateCloumn = "";
	/**
	 * @fieldName: baseCloumn
	 * @fieldType: String
	 * @Description: 基础字段定义- 
	 */
	public static String baseCloumn = "";
	
	
	public static String author = "";
	public static String email = "";
	
	public static String table_info = "";
	
	public static String templateInfo = "";
	public static String templateInfoWeb = "";
	public static String templateInfoTotal = "";
	
	/** 
	* @Title: isInsertCloumn 
	* @Description: 是否新增时间字段
	* @param cloumnName
	* @return  boolean    返回类型 
	* @throws 
	*/
	public static boolean isInsertCloumn(String cloumnName){
		if(insertCloumn!=null && insertCloumn.length()>0 && insertCloumn.toLowerCase().equals(cloumnName.toLowerCase())){
			return true;
		}
		return false;
	}
	
	/** 
	* @Title: isUpdateCloumnCloumn 
	* @Description: 是否更新时间字段
	* @param cloumnName
	* @return  boolean    返回类型 
	* @throws 
	*/
	public static boolean isUpdateCloumnCloumn(String cloumnName){
		if(updateCloumn!=null && updateCloumn.length()>0 && updateCloumn.toLowerCase().equals(cloumnName.toLowerCase())){
			return true;
		}
		return false;
	}
	
	/** 
	* @Title: isBaseCloumnCloumn
	* @Description: 是否基本字段
	* @param cloumnName
	* @return  boolean    返回类型 
	* @throws 
	*/
	public static boolean isBaseCloumnCloumn(String cloumnName){
		if(baseCloumn!=null && baseCloumn.length()>0){
			if((","+baseCloumn.toLowerCase()+",").indexOf(","+cloumnName.toLowerCase()+",")!=-1){
				return true;
			}
		}
		return false;
	}
	
	//初始化加载
	static {
		PropertiesUtil pu = PropertiesUtil.Util(null);
		system_encoding = pu.readValue("system_encoding");
		source_root_package = pu.readValue("source_root_package");
		
		business_package = pu.readValue("business_package");		
		business_package_web = pu.readValue("business_package_web");
		business_package_total = pu.readValue("business_package_total");
		
		insertCloumn = pu.readValue("insertCloumn");
		updateCloumn = pu.readValue("updateCloumn");
		baseCloumn = pu.readValue("baseCloumn");
		
		author=pu.readValue("author");
		email=pu.readValue("email");

		table_info=pu.readValue("table_info");

		templateInfo=pu.readValue("template_info");
		templateInfoWeb=pu.readValue("template_info_web");
		templateInfoTotal=pu.readValue("template_info_total");
		
		
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
	

	/** 
	* @Title: getConfigAsJsonString 
	* @Description: 以JSON格式返回配置
	* @return  String    返回类型 
	* @throws 
	*/
	public static String getConfigAsJsonString(){
		StringBuffer sb = new StringBuffer();
		sb.append("{");
		sb.append("\"author\":\"").append(author).append("\",");
		sb.append("\"email\":\"").append(email).append("\",");
		sb.append("\"path\":\"").append(projectSystemPath()).append("\",");
		sb.append("\"system_encoding\":\"").append(system_encoding).append("\",");
		
		sb.append("\"source_root_package\":\"").append(source_root_package).append("\",");
		
		sb.append("\"business_package\":\"").append(business_package).append("\",");
		sb.append("\"business_package_web\":\"").append(business_package_web).append("\",");
		sb.append("\"business_package_total\":\"").append(business_package_total).append("\",");
		
		sb.append("\"insertCloumn\":\"").append(insertCloumn).append("\",");
		sb.append("\"updateCloumn\":\"").append(updateCloumn).append("\",");
		sb.append("\"baseCloumn\":\"").append(baseCloumn).append("\",");
		
		sb.append("\"templateInfo\":\"").append(templateInfo).append("\",");
		sb.append("\"templateInfoWeb\":\"").append(templateInfoWeb).append("\",");
		sb.append("\"templateInfoTotal\":\"").append(templateInfoTotal).append("\",");
		
		sb.append("\"table_info\":\"").append(table_info).append("\"");
		sb.append("}");
		return sb.toString();
	}

}
