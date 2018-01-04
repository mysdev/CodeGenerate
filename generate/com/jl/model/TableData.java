package com.jl.model;

import java.util.List;

import com.jl.utils.CGUtil;

/**
 * @ClassName: TableData
 * @Description: ����Ϣ
 * @author: Jinlong He
 * @date: 2017��6��27�� ����2:38:29
 */
public class TableData {
	
	private String tableName;	//����  
	private String className;	//���� ����ת����
	private String entityName;	//ʵ���� ��������ĸСд
	private String businessName;	//ҵ������ 
	private String pathName;//·������ ����ȫСд
	private List<ColumnData> columnData;	//����Ϣ
	
//	PRIVATE STRING TABLEKEY;	//��KEY	
//	PRIVATE STRING CLASSKEY;	//��KEY
//	PRIVATE STRING CLASSKEYTYPE;	//KEY����
	
	
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
		setClassName(CGUtil.tableName2ClassName(tableName));
	}
	
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
		setEntityName(className.substring(0, 1).toLowerCase() + this.className.substring(1, className.length()));
		setPathName(className.toLowerCase());
	}
	public String getEntityName() {
		return entityName;
	}
	public void setEntityName(String entityName) {
		this.entityName = entityName;
	}
	public List<ColumnData> getColumnData() {
		return columnData;
	}
	public void setColumnData(List<ColumnData> columnData) {
		this.columnData = columnData;
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
//	public String getTableKey() {
//		return tableKey;
//	}
//	public void setTableKey(String tableKey) {
//		this.tableKey = tableKey;
//	}
//	public String getClassKey() {
//		return classKey;
//	}
//	public void setClassKey(String classKey) {
//		this.classKey = classKey;
//	}
//	public String getClassKeyType() {
//		return classKeyType;
//	}
//	public void setClassKeyType(String classKeyType) {
//		this.classKeyType = classKeyType;
//	}
	public String getPathName() {
		return pathName;
	}
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}
	

}
