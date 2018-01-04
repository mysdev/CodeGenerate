package com.jl.model;

import com.jl.utils.CGUtil;

/**
 * @ClassName: ColumnData
 * @Description: ����Ϣ
 * @author: Jinlong He
 * @date: 2017��6��27�� ����2:38:40
 */
public class ColumnData {
	public static final String OPTION_REQUIRED = "required:true";
	public static final String OPTION_NUMBER_INSEX = "precision:2,groupSeparator:','";
	// DB����
	private String columnName;
	// DB��ע��
	private String columnComment;
	// DB����������
	private String columnType;
	// DB��󳤶�
	private Long columnCharMaxLength;
	// DB�Ƿ�Ϊ�� Y/N
	private Boolean columnNullable;
	// DB��ģ
	private Long columnScale;
	// DB����
	private Long columnPrecision;
	
	private String classMethod;//java������
	private String classParam;//java����
	private String classType;//java����
	private String validationType; //���������֧��mobile,phone,email,fax
	private String classLable; //�б�ǩ ע�Ϳո��ǰ���ַ�
	
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
	}
	
	public String getColumnName() {
		return columnName;
	}
	public void setColumnName(String columnName) {		
		this.columnName = columnName;
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
	
}
