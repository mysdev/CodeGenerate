package ${package}#if($!packageExt)${packageExt}#end;

import com.hnzc.common.utils.BaseEntity;

/**
 * @ClassName: ${className}
 * @Description: ${businessName}实体类
 * @author: $!{author}
 * @email: mailto:$!{email}
 * @date: ${datetime}
 */
public class ${className} extends BaseEntity {
	private static final long serialVersionUID = 1L;
	
#set ($i=0)
#foreach($item in $!{columnList})
#if(!${item.isBaseColumn})
	private ${item.classType} ${item.classParam};	//${tableName}:${item.columnName}  $!{item.columnComment}
#end
#set($i=$i+1)
#end


#foreach($item in $!{columnList})
#if(!${item.isBaseColumn})
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
#end
	
	
	
	
}

