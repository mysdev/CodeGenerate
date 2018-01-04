package com.jl.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CGUtil {
	
	public static final String UNDERLINE = "_";
	
	/**
	 * �ַ������»���ת�շ�
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
	* @Description: �շ��ʽת�»��� 
	* @param param
	* @return  String    �������� 
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
	 * @Title: tableNameToClassName @Description: ����ת��������
	 * ���ַ���С��2���ַ�ʱ���ԣ�����λ��1���ַ����� @param tableName @return String �������� @throws
	 */
	public static String tableName2ClassName(String tableName) {
		tableName = tableName.toLowerCase();
		String[] names = tableName.split("_");
		StringBuffer sbf = new StringBuffer();
		if (names.length > 1) {
			// ���»���
			for (int i = 0; i < names.length; i++) {
				if (i == 0 && names[0].length() < 3) {
					continue; // ����ͷ�ַ�����3λ����Ϣ
				}
				if (names[i].length() > 1) {
					// ��ͷ�ַ���ֻҪ���Ȳ�Ϊ1
					sbf.append(names[i].substring(0, 1).toUpperCase());
					sbf.append(names[i].substring(1, names[i].length()).toLowerCase());
				}
			}
			if (sbf.toString().length() > 0) {
				return sbf.toString();
			}
			tableName = tableName.replaceAll("_", "");// ȫ�����Ϲ淶��ȥ���»���
		}
		// ���»��߻����ַ��������Ϲ淶-����ĸ��д
		return tableName.substring(0, 1).toUpperCase() + tableName.substring(1, tableName.length());
	}

	/**
	 * @Title: getJavaTypeFromDBType @Description: DB����ת��Ϊjava���� @param dataType
	 * DB���� @param precision ���� @param scale ��Χ @return String �������� @throws
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
	* @Description:  linux·��תwin
	* @param packagePath
	* @return  String    �������� 
	* @throws 
	*/
	public static String packagePath2WindowPath(String packagePath) {
		return packagePath.replaceAll(".", "/");
	}

	/** 
	* @Title: windowPath2PackagePath 
	* @Description: win·��תlinux
	* @param packagePath
	* @return  String    �������� 
	* @throws 
	*/
	public static String windowPath2PackagePath(String packagePath) {
		return packagePath.replaceAll("/", ".");
	}

	/**
	 * @Title: getProjectPath @Description: ��ȡϵͳ·�� @return String �������� @throws
	 */
	public static String getProjectSystemPath() {
		String path = System.getProperty("user.dir").replace("\\", "/") + "/";
		return path;
	}

}
