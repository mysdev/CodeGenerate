package com.jl.model;

import java.util.List;

import com.jl.utils.CGUtil;

/**
 * @ClassName: TableData
 * @Description: 表信息
 * @author: Jinlong He
 * @date: 2017年6月27日 下午2:38:29
 */
public class TableData {
	
	private String tableName;	//表名  
	private String className;	//类名 表名转类名
	private String entityName;	//实体名 类名首字母小写
	private String businessName;	//业务名称 
	private String pathName;//路径名称 类名全小写
	private List<ColumnData> columnData;	//列信息
	
//	PRIVATE STRING TABLEKEY;	//表KEY	
//	PRIVATE STRING CLASSKEY;	//类KEY
//	PRIVATE STRING CLASSKEYTYPE;	//KEY类型
	
	
	
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
