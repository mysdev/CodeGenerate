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
	private String className;	//类名   	表名转类名-下划线驼峰变大小写驼峰
	private String entityName;	//实体名  	类名首字母小写
	private String businessName;//业务名称  	中文
	private String pathName;	//路径名称	 类名全小写 URL
	private String webPath;		//web路径	
	private List<ColumnData> columnList;	//列信息
	private ColumnData keyColumn; //列的第一行
	
	private List<ColumnData> linkList; //外链列表
	
	private String columnItem;
	
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
		setClassName(CGUtil.tableName2ClassName(tableName));
		if(businessName==null){
			setBusinessName("表"+tableName);
		}
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
	public List<ColumnData> getColumnList() {
		return columnList;
	}
	public void setColumnList(List<ColumnData> columnList) {
		keyColumn = null;
		columnItem = null; 
		this.columnList = columnList;
		if(columnList!=null){
			keyColumn = columnList.get(0);
			String tableItem = "";
			for(ColumnData c : columnList){
				tableItem += (c.getColumnName()+", ");
			}
			if(tableItem.endsWith(", ")){
				columnItem = tableItem.substring(0, tableItem.length()-2);
			}
		}
	}
	public String getBusinessName() {
		return businessName;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public String getPathName() {
		return pathName;
	}
	public void setPathName(String pathName) {
		this.pathName = pathName;
	}
	public ColumnData getKeyColumn() {
		return keyColumn;
	}
	public void setKeyColumn(ColumnData keyColumn) {
		this.keyColumn = keyColumn;
	}
	public String getWebPath() {
		return webPath;
	}
	public void setWebPath(String webPath) {
		this.webPath = webPath;
	}
	public String getColumnItem() {
		return columnItem;
	}
	public void setColumnItem(String columnItem) {
		this.columnItem = columnItem;
	}
	public List<ColumnData> getLinkList() {
		return linkList;
	}
	public void setLinkList(List<ColumnData> linkList) {
		this.linkList = linkList;
	}
}
