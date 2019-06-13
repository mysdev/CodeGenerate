package ${package}#if($!packageExt)${packageExt}#end;

import java.util.Map;

import ${package}.model.entity.${className};

/**
 * @ClassName: ${className}
 * @Description: ${businessName}服务接口
 * @author: $!{author}
 * @email: mailto:$!{email}
 * @date: ${datetime}
 */
public interface ${className}Service {

    /**
	 * @Title: add${className}
	 * @Description:添加${businessName}
	 * @param ${entityName} 实体
	 * @return Integer
	 */
	${className} add${className}(${className} ${entityName});
	
	/**
	 * @Title modify${className}
	 * @Description:修改${businessName}
	 * @param ${entityName} 实体
	 * @return Integer
	 */
	Integer modify${className}(${className} ${entityName});
	
	/**
	 * @Title: drop${className}By${keyColumn.classMethod}
	 * @Description:删除${businessName}
	 * @param ${keyColumn.classParam} ${businessName}标识
	 * @return Integer
	 */
	Integer drop${className}By${keyColumn.classMethod}(${keyColumn.classType} ${keyColumn.classParam});
	
	/**
	 * @Title: query${className}By${keyColumn.classMethod}
	 * @Description:根据${businessName}标识查询${businessName}
	 * @param ${keyColumn.classParam} ${businessName}标识
	 * @return ${className}
	 */
	${className} query${className}By${keyColumn.classMethod}(${keyColumn.classType} ${keyColumn.classParam});
	 
	/**
	 * @Title: query${className}s
	 * @Description: 根据${businessName}属性查询${businessName}信息 *offset与pagesize为空时查询全量
	 * @param offset 起始位置
	 * @param pagesize 数据大小 
	 * @param sort 排序 如 id,asc;name,desc
	 * @param ${entityName} 实体
	 * @return List<${className}>
	 */
	Map<String, Object> query${className}s(Integer offset, Integer pagesize, String sort, ${className} ${entityName});
		 
}
