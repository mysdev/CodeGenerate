package com.jing.system.model.entity;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;


/**
 * @ClassName: UserGroup
 * @Description: 用户组实体类
 * @author: Jinlong He
 * @email: mailto:jinlong_he@126.com
 * @date: 2018年01月03日 16时26分
 */
public class UserGroup implements Serializable {
	private static final long serialVersionUID = 1L;

	private Integer teamId;	//ts_user_group:team_id  用户组标识  

	@NotBlank(message = "{org.hibernate.validator.constraints.NotBlank.message}")
	@Length(min=1, max=128, message="{org.hibernate.validator.constraints.Length.message}")
	private String teamName;	//ts_user_group:team_name  用户组名称  

	private Integer parentTeam;	//ts_user_group:parent_team  父级组标识  

	@NotNull(message="{javax.validation.constraints.NotNull.message}")
	private Integer status;	//ts_user_group:status  状态0正常  

	@Length(min=0, max=32, message="{org.hibernate.validator.constraints.Length.message}")
	private String createdBy;	//ts_user_group:created_by  创建人员  

	private Date createdDate;	//ts_user_group:created_date  创建时间  

	@Length(min=0, max=32, message="{org.hibernate.validator.constraints.Length.message}")
	private String updatedBy;	//ts_user_group:updated_by  修订人员  

	private Date updatedDate;	//ts_user_group:updated_date  修订时间  

	/**
	* @DatabasetableColumnName: ts_user_group:team_id
	* @Description: 获取属性        用户组标识
	* @return: Integer
	*/
	public Integer getTeamId(){
		return teamId;	
	}
	
	/**
	* @DatabasetableColumnName: ts_user_group:team_id
	* @Description: 设置属性        用户组标识
	* @return: Integer
	*/
	public void setTeamId(Integer teamId){
		this.teamId = teamId;	
	}
	
	/**
	* @DatabasetableColumnName: ts_user_group:team_name
	* @Description: 获取属性        用户组名称
	* @return: String
	*/
	public String getTeamName(){
		return teamName;	
	}
	
	/**
	* @DatabasetableColumnName: ts_user_group:team_name
	* @Description: 设置属性        用户组名称
	* @return: String
	*/
	public void setTeamName(String teamName){
		this.teamName = teamName;	
	}
	
	/**
	* @DatabasetableColumnName: ts_user_group:parent_team
	* @Description: 获取属性        父级组标识
	* @return: Integer
	*/
	public Integer getParentTeam(){
		return parentTeam;	
	}
	
	/**
	* @DatabasetableColumnName: ts_user_group:parent_team
	* @Description: 设置属性        父级组标识
	* @return: Integer
	*/
	public void setParentTeam(Integer parentTeam){
		this.parentTeam = parentTeam;	
	}
	
	/**
	* @DatabasetableColumnName: ts_user_group:status
	* @Description: 获取属性        状态0正常
	* @return: Integer
	*/
	public Integer getStatus(){
		return status;	
	}
	
	/**
	* @DatabasetableColumnName: ts_user_group:status
	* @Description: 设置属性        状态0正常
	* @return: Integer
	*/
	public void setStatus(Integer status){
		this.status = status;	
	}
	
	/**
	* @DatabasetableColumnName: ts_user_group:created_by
	* @Description: 获取属性        创建人员
	* @return: String
	*/
	public String getCreatedBy(){
		return createdBy;	
	}
	
	/**
	* @DatabasetableColumnName: ts_user_group:created_by
	* @Description: 设置属性        创建人员
	* @return: String
	*/
	public void setCreatedBy(String createdBy){
		this.createdBy = createdBy;	
	}
	
	/**
	* @DatabasetableColumnName: ts_user_group:created_date
	* @Description: 获取属性        创建时间
	* @return: Date
	*/
	public Date getCreatedDate(){
		return createdDate;	
	}
	
	/**
	* @DatabasetableColumnName: ts_user_group:created_date
	* @Description: 设置属性        创建时间
	* @return: Date
	*/
	public void setCreatedDate(Date createdDate){
		this.createdDate = createdDate;	
	}
	
	/**
	* @DatabasetableColumnName: ts_user_group:updated_by
	* @Description: 获取属性        修订人员
	* @return: String
	*/
	public String getUpdatedBy(){
		return updatedBy;	
	}
	
	/**
	* @DatabasetableColumnName: ts_user_group:updated_by
	* @Description: 设置属性        修订人员
	* @return: String
	*/
	public void setUpdatedBy(String updatedBy){
		this.updatedBy = updatedBy;	
	}
	
	/**
	* @DatabasetableColumnName: ts_user_group:updated_date
	* @Description: 获取属性        修订时间
	* @return: Date
	*/
	public Date getUpdatedDate(){
		return updatedDate;	
	}
	
	/**
	* @DatabasetableColumnName: ts_user_group:updated_date
	* @Description: 设置属性        修订时间
	* @return: Date
	*/
	public void setUpdatedDate(Date updatedDate){
		this.updatedDate = updatedDate;	
	}
	
	
	
	
	
}

