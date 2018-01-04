package ${package}#if($!packageExt)${packageExt}#end;

import java.util.List;
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
	 * @param ${keyColumn.classParam} 实体标识
	 * @return Integer
	 */
	Integer drop${className}By${keyColumn.classMethod}(${keyColumn.classType} ${keyColumn.classParam});
	
	/**
	 * @Title: query${className}By${keyColumn.classMethod}
	 * @Description:根据实体标识查询${businessName}
	 * @param ${keyColumn.classParam} 实体标识
	 * @return ${className}
	 */
	${className} query${className}By${keyColumn.classMethod}(${keyColumn.classType} ${keyColumn.classParam});
	 
	/**
	 * @Title: query${className}ForPage
	 * @Description: 根据${businessName}属性与分页信息分页查询${businessName}信息
	 * @param pagenum 页 
	 * @param pagesize 页大小 
	 * @param sort 排序
	 * @param ${entityName} 实体
	 * @return List<${className}>
	 */
	Map<String, Object> query${className}ForPage(Integer pagenum, Integer pagesize, String sort, ${className} ${entityName});
	 
	 /**
	 * @Title: query${className}ByProperty
	 * @Description:根据属性查询${businessName}
	 * @return List<${className}>
	 */
	 List<${className}> query${className}ByProperty(Map<String, Object> map);	 
	 
}
