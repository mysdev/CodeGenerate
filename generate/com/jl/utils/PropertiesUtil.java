package com.jl.utils;

import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
//import java.io.UnsupportedEncodingException;
//import java.util.Enumeration;
import java.util.Properties;
import org.apache.log4j.Logger;

/**
 * @ClassName: PropertiesUtil
 * @Description: 配置文件信息读取类
 * @author: Jinlong He
 * @date: 2017年6月4日 上午10:27:20
 */
public class PropertiesUtil {
	private static Logger log = Logger.getLogger(PropertiesUtil.class);
	private String file;
	private Properties props;
	public static PropertiesUtil instance; //唯一实例
	
	public static PropertiesUtil Util(String filePath){
		if(instance==null){
			if(filePath==null || filePath.length()==0){
				instance = new PropertiesUtil();
			}else{
				instance = new PropertiesUtil(filePath);
			}
		}		
		return instance;
	}
	
	/**
	 * 默认FilePath="resources/code_config.properties"
	 */
	public PropertiesUtil(){	
		file = "./resources/code_config.properties";
		if(props==null){
			loadPropertiesInfo();
		}		
	}
	
	/**
	 * 指定设置配置文件
	 * @param filePath
	 */
	public PropertiesUtil(String filePath){	
		file = filePath;
		//if(props==null){
			loadPropertiesInfo();
		//}		
	}
	
	/**
	 * 读取配置文件
	 */
	private void loadPropertiesInfo(){
		try {		
			props = new Properties();
			InputStream in = new BufferedInputStream(new FileInputStream(file));	
			props.load(in);
		}catch(FileNotFoundException e){
			log.error("读取配置文件出错，原因：没有找到配置文件"+file);
		}catch (Exception e) {
			log.error("读取配置文件出错，原因："+e.getMessage());
		}
	}
	
	
	
	/**
	 * 读取指定key对应的值
	 * @param key
	 * @return 没有取到相关值时返回""
	 */
	public String readValue(String key){
		if(props==null){
			loadPropertiesInfo();
		}
		String ret = props.getProperty(key);
		try {
			System.out.println("PU:"+ret);
			ret = new String(ret.getBytes("utf-8"),"utf-8");
		}catch (UnsupportedEncodingException e) {
			log.error("获取配置参数"+key+"时转码出错："+e.getMessage());
		}catch(Exception e){
			log.error("获取配置参数"+key+"时出错："+e.getMessage());
		}
		return ret==null?"":ret;
	}
	

	/**
	 * 读取所有配置参数
	 * @return 
	 */
//	@SuppressWarnings("unchecked")
//	public void readProperties() {		
//		Enumeration<String> en = (Enumeration<String>) props.propertyNames();		
//		while (en.hasMoreElements()) {
//			String key = en.nextElement();
//			String Property = props.getProperty(key);
//			try {
//				Property = new String(Property.getBytes("ISO8859_1"),"UTF-8");
//			}catch (UnsupportedEncodingException e) {
//				log.error("获取配置参数"+key+"时转码出错："+e.getMessage());
//			}catch(Exception e){
//				log.error("获取配置参数"+key+"时出错："+e.getMessage());
//			}
//			log.info("读取参数："+key +" 值为: "+ Property);
//		}
//
//	}
	
/*
	public void writeProperties(String filePath, String parameterName,
			String parameterValue) {
		Properties prop = new Properties();
		file = filePath;
		try {
			InputStream fis = new FileInputStream(file);
			// 从输入流中读取属性列表（键和元素对）
			prop.load(fis);
			// 调用 Hashtable 的方法 put。使用 getProperty 方法提供并行性。
			// 强制要求为属性的键和值使用字符串。返回值是 Hashtable 调用 put 的结果。
			OutputStream fos = new FileOutputStream(filePath);
			prop.setProperty(parameterName, parameterValue);
			// 以适合使用 load 方法加载到 Properties 表中的格式，
			// 将此 Properties 表中的属性列表（键和元素对）写入输出流
			prop.store(fos, "Update '" + parameterName + "' value");
		} catch (IOException e) {
			log.error("Visit " + filePath + " for updating "
					+ parameterName + " value error \n"+e.getMessage());
		}
	}
 */
	
	
}
