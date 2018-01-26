package com.jl.utils;

import java.beans.BeanInfo;
import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: ClassUtil
 * @Description: ��ʵ�����
 * @author: Jinlong He
 * @date: 2017��4��11�� ����1:57:46
 */
public class ClassUtil {

	/**
	 * ��ȡ��ʵ��������ֵ
	 * 
	 * @param clazz
	 *            ����
	 * @param includeParentClass
	 *            �Ƿ�������������ֵ
	 * @return ����.������=��������
	 * @author: li chao
	 */
	@SuppressWarnings("rawtypes")
	public static List<Field> getClassFields(Class clazz, boolean includeParentClass) {
		List<Field> list = new ArrayList<Field>();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!Modifier.isFinal(field.getModifiers()))
				list.add(field);
		}
		if (includeParentClass)
			getParentClassFields(list, clazz.getSuperclass());
		return list;
	}

	/**
	 * ��ȡ��ʵ���ĸ��������ֵ
	 * 
	 * @param map
	 *            ��ʵ��������ֵMap
	 * @param clazz
	 *            ����
	 * @return ����.������=��������
	 * @author: li chao
	 */
	@SuppressWarnings("rawtypes")
	private static List<Field> getParentClassFields(List<Field> list, Class clazz) {
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			if (!Modifier.isFinal(field.getModifiers()))
				list.add(field);
			;
		}
		if (clazz.getSuperclass() == null) {
			return list;
		}
		getParentClassFields(list, clazz.getSuperclass());
		return list;
	}

	/**
	 * ��ȡ��ʵ���ķ���
	 * 
	 * @param clazz
	 *            ����
	 * @param includeParentClass
	 *            �Ƿ��������ķ���
	 * @return List
	 * @author: li chao
	 */
	@SuppressWarnings("rawtypes")
	public static List<Method> getMothds(Class clazz, boolean includeParentClass) {
		List<Method> list = new ArrayList<Method>();
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			list.add(method);
		}
		if (includeParentClass) {
			getParentClassMothds(list, clazz.getSuperclass());
		}
		return list;
	}

	/**
	 * ��ȡ��ʵ���ĸ���ķ���
	 * 
	 * @param list
	 *            ��ʵ���ķ���List
	 * @param clazz
	 *            ����
	 * @return List
	 * @author: li chao
	 */
	@SuppressWarnings("rawtypes")
	private static List<Method> getParentClassMothds(List<Method> list, Class clazz) {
		Method[] methods = clazz.getDeclaredMethods();
		for (Method method : methods) {
			list.add(method);
		}
		if (clazz.getSuperclass() == Object.class) {
			return list;
		}
		getParentClassMothds(list, clazz.getSuperclass());
		return list;
	}

	/**
	 * 
	 * @Title: isNull
	 * @Description: �ж϶����Ƿ�Null
	 * @param obj
	 * @return boolean
	 * @author: li chao
	 * @throws IllegalAccessException
	 * @throws IllegalArgumentException
	 */
	public static boolean isNull(Object obj) throws IllegalArgumentException, IllegalAccessException {
		Class<? extends Object> clazz = obj.getClass();
		List<Field> fields = getClassFields(clazz, false);
		boolean isnull = true;
		for (Field field : fields) {
			field.setAccessible(true);
			Object obj1 = field.get(obj);
			if (null != obj1) {
				return false;
			}
		}
		return isnull;
	}

	/**
	 * 
	 * @Title: getClassFieldName
	 * @Description:��ȡ����ֵ��������
	 * @param obj
	 * @param isNull
	 * @return
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 *             boolean
	 * @author: li chao
	 */
	public static List<String> getClassFieldName(Object obj, boolean isNull)
			throws IllegalArgumentException, IllegalAccessException {
		Class<? extends Object> clazz = obj.getClass();
		List<String> list = new ArrayList<String>();
		List<Field> fields = getClassFields(clazz, false);
		for (Field field : fields) {
			if (isNull) {
				list.add(field.getName());
			} else {
				field.setAccessible(true);
				Object obj1 = field.get(obj);
				if (obj1 != null) {
					list.add(field.getName());
				}
			}
		}
		return list;
	}
	
	
	/** 
	* @Title: beanIsNull 
	* @Description: ͨ��BEAN��getter�������ж�bean�Ƿ�Ϊ��
	* @param bean
	* @return
	* @throws IllegalAccessException
	* @throws IllegalArgumentException
	* @throws InvocationTargetException
	* @throws IntrospectionException  boolean    �������� 
	* @throws 
	* @author Jinlong He
	*/
	public static <T> boolean beanIsNull(T bean) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException, IntrospectionException{
		if (bean == null) {
			return true;
		}
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			if (!key.equals("class")) {
				Method getter = property.getReadMethod();
				Object value = getter.invoke(bean);
				if(null != value ){
					return false;
				}
			}
		}		
		return true;		
	}
	

	/** 
	* @Title: transBean2Map 
	* @Description: ��BEANͨ��getter��¶�������ݷ�װ��MAP 
	* @param obj 
	* @param hasNull �������Ƿ��װ
	* @return
	* @throws IntrospectionException
	* @throws IllegalAccessException
	* @throws IllegalArgumentException
	* @throws InvocationTargetException  Map<String,Object>    �������� 
	* @throws 
	* @author Jinlong He
	*/
	public static Map<String, Object> transBean2Map(Object bean, boolean hasNull)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (bean == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		BeanInfo beanInfo = Introspector.getBeanInfo(bean.getClass());
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			// ����class����
			if (!key.equals("class")) {
				// �õ�property��Ӧ��getter����
				Method getter = property.getReadMethod();
				Object value = getter.invoke(bean);
				if (value == null && !hasNull) {
					continue;
				}
				map.put(key, value);
			}
		}
		return map;
	}
	
	/** 
	* @Title: transMap2Bean 
	* @Description: ��ʼ������ʵ�壬��ͨ��setter��MAP��ȡֵ����󷵻���ʵ��.ע��IntegerתByte,LongתInteger�ȷǷ�ת��ʱ������
	* @param map
	* @param class1
	* @return
	* @throws InstantiationException
	* @throws IllegalAccessException
	* @throws IntrospectionException  T    �������� 
	* @throws 
	* @author Jinlong He
	*/
	public static <T> T transMap2Bean(Map<String, Object> map, Class<T> class1)
			throws InstantiationException, IllegalAccessException, IntrospectionException {
		BeanInfo beanInfo = Introspector.getBeanInfo(class1);
		T bean = class1.newInstance();
		PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
		for (PropertyDescriptor property : propertyDescriptors) {
			String key = property.getName();
			if (map.containsKey(key)) {
				Object value = map.get(key);
				Method setter = property.getWriteMethod();
				try {
					setter.invoke(bean, value);
				} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
					System.out.println("error: put"+value.getClass().getName()+" to "+setter.getParameterTypes()[0].getName());
				}
			}
		}
		return bean;
	}
	/**
	 * 
	* @Title: transList2Map 
	* @Description: ����תmap
	* @param list
	* @param hasNull
	* @return
	* @throws IntrospectionException
	* @throws IllegalAccessException
	* @throws IllegalArgumentException
	* @throws InvocationTargetException List<Map<String,Object>>
	* @author: li chao
	 */
	public static <T> List<Map<String, Object>> transList2Map(List<T> list, boolean hasNull)
			throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		if (list == null) {
			return null;
		}
		List<Map<String, Object>> listtemp = new ArrayList<Map<String, Object>>();
		for (Object object : list) {
			Map<String, Object> map = new HashMap<String, Object>();
			BeanInfo beanInfo = Introspector.getBeanInfo(object.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();
				// ����class����
				if (!key.equals("class")) {
					// �õ�property��Ӧ��getter����
					Method getter = property.getReadMethod();
					Object value = getter.invoke(object);
					if (value == null && !hasNull) {
						continue;
					}
					map.put(key, value);
				}
			}
			listtemp.add(map);
		}
		return listtemp;
	}
	
	/**
	 * 
	* @Title: underline2Camel 
	* @Description:  Map���»���ת�շ�
	* @param map
	* @param checknull true  false�����˵�Ϊnull ��""
	* @return Map<String,Object>
	* @author: li chao
	 */
	public static Map<String, Object> underline2Camel(Map<String, Object> map,boolean checknull) {
		Map<String, Object> newMap = new HashMap<String, Object>();
		Set<String> keys = map.keySet();
		for (String key : keys) {
			String newKey = underline2Camel(key);
			if(!checknull){
				if(null == map.get(key) || "".equals(map.get(key))){
					continue;
				}
				newMap.put(newKey, map.get(key));
			}else
				newMap.put(newKey, map.get(key));
		}
		return newMap;
	}
	

	/**
	 * Map���»���ת�շ�
	 * 
	 * @return
	 */
	public static List<Map<String, Object>> underline2Camel(List<Map<String, Object>> list,boolean checknull) {
		List<Map<String, Object>> newlsit = new ArrayList<Map<String, Object>>();
		for (Map<String, Object> map : list) {
			newlsit.add(underline2Camel(map,checknull));
		}
		return newlsit;
	}

	/**
	 * �ַ������»���ת�շ�
	 * 
	 * @param underline
	 * @return
	 */
	public static String underline2Camel(String underline) {
		Pattern pattern = Pattern.compile("[_]\\w");
		String camel = underline.toLowerCase();
		Matcher matcher = pattern.matcher(camel);
		while (matcher.find()) {
			String w = matcher.group().trim();
			camel = camel.replace(w, w.toUpperCase().replace("_", ""));
		}
		return camel;
	}

	public static final char UNDERLINE = '_';

	
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
	
}
