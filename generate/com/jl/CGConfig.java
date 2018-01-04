package com.jl;

import com.jl.utils.PropertiesUtil;

/**
 * @ClassName: CGConfig
 * @Description: �Զ��������ɳ�������
 * @author: Jinlong He
 * @date: 2017��6��20�� ����4:23:44
 */
public class CGConfig {
	
	/**
	 * @fieldName: source_root_package
	 * @fieldType: String
	 * @Description: �����Ŀ¼
	 */
	public static String source_root_package = "/src";
	
	/**
	 * @fieldName: business_package
	 * @fieldType: String
	 * @Description: ҵ�����Ŀ¼
	 */
	public static String business_package = "com.hnjing.code";
	
	/**
	 * @fieldName: system_encoding
	 * @fieldType: String
	 * @Description: �ַ�����
	 */
	public static String system_encoding = "utf-8";
	
	public static String template_path = "/resources/template";
	
	//��ʼ������
	static {
		PropertiesUtil pu = PropertiesUtil.Util(null);
		source_root_package = pu.readValue("source_root_package");
		business_package = pu.readValue("business_package");
		system_encoding = pu.readValue("system_encoding");
		
	}
	
	/** 
	* @Title: projectSystemPath 
	* @Description: ϵͳ·��
	* @return  String    �������� 
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
