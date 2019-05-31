package com.jl.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.jl.CGConfig;

/**
 * @ClassName: CGUtil
 * @Description: TODO
 * @author: Jinlong He
 * @date: 2017年6月4日 上午10:26:52
 */
public class CGUtil {
	
	public static final String UNDERLINE = "_";
	
	/**
	 * 字符串的下划线转驼峰
	 * @param underline
	 * @return
	 */
	public static String underline2Camel(String underline) {
		Pattern pattern = Pattern.compile("[_]\\w");
		String camel = underline.toLowerCase();
		Matcher matcher = pattern.matcher(camel);
		while (matcher.find()) {
			String w = matcher.group().trim();
			camel = camel.replace(w, w.toUpperCase().replace(UNDERLINE, ""));
		}
		return camel;
	}

	
	/** 
	* @Title: camelToUnderline 
	* @Description: 驼峰格式转下划线 
	* @param param
	* @return  String    返回类型 
	* @throws 
	*/
	public static String camel2Underline(String param) {
		if (param == null || "".equals(param.trim())) {
			return "";
		}
		int len = param.length();
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++) {
			char c = param.charAt(i);
			if (Character.isUpperCase(c)) {
				sb.append(UNDERLINE);
				sb.append(Character.toLowerCase(c));
			} else {
				sb.append(c);
			}
		}
		return sb.toString();
	}

	/**
	 * @Title: tableNameToClassName @Description: 表名转换成类名
	 * 首字符串小于2个字符时忽略，其它位置1个字符忽略 @param tableName @return String 返回类型 @throws
	 */
	public static String tableName2ClassName(String tableName) {
		tableName = tableName.toLowerCase();
		String[] names = tableName.split("_");
		StringBuffer sbf = new StringBuffer();
		if (names.length > 1) {
			// 有下划线
			for (int i = 0; i < names.length; i++) {
				if (i == 0 && names[0].length() < 3) {
					continue; // 忽略头字符不够3位的信息
				}
				if (names[i].length() > 1) {
					// 非头字符，只要长度不为1
					sbf.append(names[i].substring(0, 1).toUpperCase());
					sbf.append(names[i].substring(1, names[i].length()).toLowerCase());
				}
			}
			if (sbf.toString().length() > 0) {
				return sbf.toString();
			}
			tableName = tableName.replaceAll("_", "");// 全不符合规范，去掉下划线
		}
		// 无下划线或者字符串不符合规范-首字母大写
		return tableName.substring(0, 1).toUpperCase() + tableName.substring(1, tableName.length());
	}

	/**
	 * @Title: getJavaTypeFromDBType 
	 * @Description: DB类型转换为java类型 
	 * @param dataType DB类型 
	 * @param precision 精度
	 * @param scale 范围
	 * @return String 返回类型
	 * @throws
	 */
	public static String getJavaTypeFromDBType(String dataType, Long precision, Long scale) {
		dataType = dataType.toLowerCase();
		if (dataType.contains("char") || dataType.contains("text"))
			dataType = "String";
		else if (dataType.contains("bit"))
			dataType = "Boolean";
		else if (dataType.contains("bigint"))
			dataType = "Long";
		else if (dataType.contains("int"))
			dataType = "Integer";
		else if (dataType.contains("float"))
			dataType = "java.lang.Float";
		else if (dataType.contains("double"))
			dataType = "Double";
		else if (dataType.contains("number")) {
			if (scale != null && scale.longValue() > 0)
				dataType = "java.math.BigDecimal";
			else if (precision != null && precision.longValue() > 6)
				dataType = "Long";
			else
				dataType = "Integer";
		} else if (dataType.contains("decimal"))
			dataType = "java.math.BigDecimal";
		else if (dataType.contains("datetime"))
			dataType = "Date";
		else if (dataType.contains("date"))
			dataType = "Date";
		else if (dataType.contains("time"))
			dataType = "java.sql.Timestamp";
		else if (dataType.contains("clob"))
			dataType = "java.sql.Clob";
		else {
			dataType = "java.lang.Object";
		}
		return dataType;
	}

	/** 
	* @Title: packagePath2WindowPath 
	* @Description:  linux路径转win
	* @param packagePath
	* @return  String    返回类型 
	* @throws 
	*/
	public static String packagePath2WindowPath(String packagePath) {
		return packagePath.replaceAll(".", "/");
	}

	/** 
	* @Title: windowPath2PackagePath 
	* @Description: win路径转linux
	* @param packagePath
	* @return  String    返回类型 
	* @throws 
	*/
	public static String windowPath2PackagePath(String packagePath) {
		return packagePath.replaceAll("/", ".");
	}

	/**
	 * @Title: getProjectPath 
	 * @Description: 获取系统路径 
	 * @return String 返回类型 
	 * @throws
	 */
	public static String getProjectSystemPath() {
		String path = System.getProperty("user.dir").replace("\\", "/") + "/";
		return path;
	}
	
	/** 
	* @Title: isInsertCloumn 
	* @Description: 是否新增时间字段
	* @param cloumnName
	* @return  boolean    返回类型 
	* @throws 
	*/
	public static boolean isInsertCloumn(String cloumnName){
		if(CGConfig.insertCloumn!=null && CGConfig.insertCloumn.length()>0 && CGConfig.insertCloumn.toLowerCase().equals(cloumnName.toLowerCase())){
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
	public static boolean isUpdateCloumn(String cloumnName){
		if(CGConfig.updateCloumn!=null && CGConfig.updateCloumn.length()>0 && CGConfig.updateCloumn.toLowerCase().equals(cloumnName.toLowerCase())){
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
		if(CGConfig.baseCloumn!=null && CGConfig.baseCloumn.length()>0){
			if((","+CGConfig.baseCloumn.toLowerCase()+",").indexOf(","+cloumnName.toLowerCase()+",")!=-1){
				return true;
			}
		}
		return false;
	}

}
