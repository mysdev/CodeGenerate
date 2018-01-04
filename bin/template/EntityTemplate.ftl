package ${package}#if($!packageExt)${packageExt}#end;

import java.io.Serializable;
import java.util.Date;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.hibernate.validator.constraints.Range;


/**
 * @ClassName: ${className}
 * @Description: ${businessName}实体类
 * @author: $!{author}
 * @email: mailto:$!{email}
 * @date: ${datetime}
 */
public class ${className} implements Serializable {
	private static final long serialVersionUID = 1L;
#set ($i=0)
#foreach($item in $!{columnList})

#if(!${item.columnNullable} && $i!=0)
#if($item.classType == 'String')
	@NotBlank(message = "{org.hibernate.validator.constraints.NotBlank.message}")
#if($item.columnCharMaxLength)
	@Length(min=1, max=$item.columnCharMaxLength, message="{org.hibernate.validator.constraints.Length.message}")
#end
#else
	@NotNull(message="{javax.validation.constraints.NotNull.message}")
#end
#else
#if($item.classType == 'String' && $item.columnCharMaxLength)
	@Length(min=0, max=$item.columnCharMaxLength, message="{org.hibernate.validator.constraints.Length.message}")
#end
#end
#if($item.validationType == 'mobile')
	@Pattern(regexp="1[34578]\\d{9}", message="{validator.phone.message}")
#elseif($item.validationType == 'phone')
	@Pattern(regexp="((\\d{3,4}-)[0-9]{7,8}(-[0-9]{0,4})?)", message="{javax.validation.constraints.Pattern.message}")
#elseif($item.validationType == 'fax')
	@Pattern(regexp="((\\d{3,4}-)[0-9]{7,8})", message="{javax.validation.constraints.Pattern.message}")
#elseif($item.validationType == 'email')
	@Email(message="{org.hibernate.validator.constraints.Email.message}")
#end
	private ${item.classType} ${item.classParam};	//${tableName}:${item.columnName}  $!{item.columnComment}  
#set($i=$i+1)
#end

#foreach($item in $!{columnList})
	/**
	* @DatabasetableColumnName: ${tableName}:${item.columnName}
	* @Description: 获取属性        $!{item.columnComment}
	* @return: ${item.classType}
	*/
	public ${item.classType} get${item.classMethod}(){
		return ${item.classParam};	
	}
	
	/**
	* @DatabasetableColumnName: ${tableName}:${item.columnName}
	* @Description: 设置属性        $!{item.columnComment}
	* @return: ${item.classType}
	*/
	public void set${item.classMethod}(${item.classType} ${item.classParam}){
		this.${item.classParam} = ${item.classParam};	
	}
	
#end
	
	
	
	
}

