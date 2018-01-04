package ${package}#if($!packageExt)${packageExt}#end;


import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.jing.utils.paginator.domain.PageBounds;
import ${package}.model.entity.${className};

/**
 * @ClassName: ${className}Mapper
 * @Description: ${businessName}映射
 * @author: $!{author}
 * @email: mailto:$!{email}
 * @date: ${datetime}
 */
@Mapper
public interface ${className}Mapper {

	/**
	 * @Title: add${className}
	 * @Description:添加${businessName}
	 * @param ${entityName} 实体
	 * @return Integer
	 */
	Integer add${className}(${className} ${entityName});
	
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
	 * @param pageBounds 分页信息
	 * @param ${entityName} 实体
	 * @return List<${className}>
	 */
	List<${className}> query${className}ForPage(PageBounds pageBounds, @Param("${entityName}") ${className} ${entityName});
	 
	 /**
	  * @Title: query${className}ByProperty
	  * @Description:根据属性查询${businessName}
	  * @return List<${className}>
	  */
	 List<${className}> query${className}ByProperty(@Param("${entityName}") Map<String, Object> map);
	 
	 
	 
}
