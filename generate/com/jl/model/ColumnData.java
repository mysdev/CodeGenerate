﻿package com.jl.model;

import com.jl.utils.CGUtil;

/**
 * @ClassName: ColumnData
 * @Description: 列信息
 * @author: Jinlong He
 * @date: 2017年6月27日 下午2:38:40
 */
public class ColumnData {
	public static final String OPTION_REQUIRED = "required:true";
	public static final String OPTION_NUMBER_INSEX = "precision:2,groupSeparator:','";
	// DB列名
	private String columnName;
	// DB列注释
	private String columnComment;
	// DB列数据类型
	private String columnType;
	// DB最大长度
	private Long columnCharMaxLength;
	// DB是否为空 Y/N
	private Boolean columnNullable;
	// DB规模
	private Long columnScale;
	// DB精度
	private Long columnPrecision;	
	
	
	private String classMethod;//java方法名
	private String classParam;//java参数
	private String classType;//java类型	
	private String classLable; //列标签 注释空格符前面字符
	private String validationType; //检查类型暂支持mobile,phone,email,fax
	
	
	/**
	 * @fieldName: isInsertColumn
	 * @fieldType: Boolean
	 * @Description: 是否写入时间
	 */
	private Boolean isInsertColumn = false;
	/**
	 * @fieldName: isUpdateColumn
	 * @fieldType: Boolean
	 * @Description: 是否更新时间
	 */
	private Boolean isUpdateColumn = false;
	
	/**
	 * @fieldName: isInsertUserColumn
	 * @fieldType: Boolean
	 * @Description: 是否写入人员
	 */
	private Boolean isInsertUserColumn = false;
	/**
	 * @fieldName: isUpdateUserColumn
	 * @fieldType: Boolean
	 * @Description: 是否更新人员
	 */
	private Boolean isUpdateUserColumn = false;
	/**
	 * @fieldName: isBaseColumn
	 * @fieldType: Boolean
	 * @Description: 是否基本字段
	 */
	private Boolean isBaseColumn = false;
	
	private void checkValidationType(String columnName){
		if(columnName==null) return;
		String temp = columnName.toLowerCase();
		if(temp.endsWith("mobile")){
			validationType = "mobile";
		}else if(temp.endsWith("phone")){
			validationType = "phone";
		}else if(temp.endsWith("email")){
			validationType = "email";
		}else if(temp.endsWith("fax")){
			validationType = "fax";
		}		
//		this.setIsBaseColumn(CGUtil.isBaseCloumnCloumn(columnName));
//		if(classType!=null && "Date".equals(classType)){
//			this.setIsInsertColumn(CGUtil.isInsertCloumn(columnName));
//			this.setIsInsertColumn(CGUtil.isUpdateCloumn(columnName));
//		}
	}
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {		
		this.columnName = columnName;
		this.setIsBaseColumn(CGUtil.isBaseCloumnCloumn(columnName));
		//if(classType!=null && "Date".equals(classType)){
			this.setIsInsertColumn(CGUtil.isInsertCloumn(columnName));
			this.setIsInsertUserColumn(CGUtil.isInsertUserCloumn(columnName));
			this.setIsUpdateColumn(CGUtil.isUpdateCloumn(columnName));
			this.setIsUpdateUserColumn(CGUtil.isUpdateUserCloumn(columnName));
		//}
		checkValidationType(columnName);
		classParam = CGUtil.underline2Camel(columnName);	
		classMethod = classParam.substring(0, 1).toUpperCase()+classParam.substring(1);
	}
	public String getColumnComment() {
		return columnComment;
	}
	public void setColumnComment(String columnComment) {
		this.columnComment = columnComment;
		if(columnComment!=null && columnComment.indexOf(" ")!=-1){
			setClassLable(columnComment.substring(0, columnComment.indexOf(" ")));
		}else{
			setClassLable(columnComment);
		}
		
	}
	public String getColumnType() {
		return columnType;
	}
	public void setColumnType(String columnType) {
		this.columnType = columnType;
	}
	public Long getColumnCharMaxLength() {
		return columnCharMaxLength;
	}
	public void setColumnCharMaxLength(Long columnCharMaxLength) {
		this.columnCharMaxLength = columnCharMaxLength;
	}
	public Boolean getColumnNullable() {
		return columnNullable;
	}
	public void setColumnNullable(Boolean columnNullable) {
		this.columnNullable = columnNullable;
	}
	public Long getColumnScale() {
		return columnScale;
	}
	public void setColumnScale(Long columnScale) {
		this.columnScale = columnScale;
	}
	public Long getColumnPrecision() {
		return columnPrecision;
	}
	public void setColumnPrecision(Long columnPrecision) {
		this.columnPrecision = columnPrecision;
	}
	public String getClassParam() {
		return classParam;
	}
	public void setClassParam(String classParam) {
		this.classMethod = classParam.substring(0, 1).toUpperCase()+classParam.substring(1);
		this.classParam = classParam;
	}
	public String getClassType() {
		return classType;
	}
	public void setClassType(String classType) {
		this.classType = classType;
//		if(columnName!=null){
//			this.setIsBaseColumn(CGUtil.isBaseCloumnCloumn(columnName));
//			if("Date".equals(classType)){
//				this.setIsInsertColumn(CGUtil.isInsertCloumn(columnName));
//				this.setIsInsertColumn(CGUtil.isUpdateCloumn(columnName));
//			}	
//		}
	}
	public String getClassMethod() {
		return classMethod;
	}
	public void setClassMethod(String classMothod) {
		this.classParam = classMothod.substring(0, 1).toLowerCase()+classMothod.substring(1);
		this.classMethod = classMothod;
	}
	public String getValidationType() {
		return validationType;
	}
	public void setValidationType(String validationType) {
		this.validationType = validationType;
	}

	public String getClassLable() {
		return classLable;
	}

	public void setClassLable(String classLable) {
		this.classLable = classLable;
	}

	public Boolean getIsInsertColumn() {
		return isInsertColumn;
	}

	public void setIsInsertColumn(Boolean isInsertColumn) {
		this.isInsertColumn = isInsertColumn;
	}

	public Boolean getIsUpdateColumn() {
		return isUpdateColumn;
	}

	public void setIsUpdateColumn(Boolean isUpdateColumn) {
		this.isUpdateColumn = isUpdateColumn;
	}

	public Boolean getIsBaseColumn() {
		return isBaseColumn;
	}

	public void setIsBaseColumn(Boolean isBaseColumn) {
		this.isBaseColumn = isBaseColumn;
	}

	public Boolean getIsInsertUserColumn() {
		return isInsertUserColumn;
	}

	public void setIsInsertUserColumn(Boolean isInsertUserColumn) {
		this.isInsertUserColumn = isInsertUserColumn;
	}

	public Boolean getIsUpdateUserColumn() {
		return isUpdateUserColumn;
	}

	public void setIsUpdateUserColumn(Boolean isUpdateUserColumn) {
		this.isUpdateUserColumn = isUpdateUserColumn;
	}
	
}
