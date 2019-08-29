package ${package}#if($!packageExt)${packageExt}#end;


import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.hnzc.common.utils.paginator.domain.PageBounds;
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
	 * @param ${entityName} ${businessName}实体
	 * @return Integer
	 */
	Integer add${className}(${className} ${entityName});
	
	/**
	 * @Title modify${className}
	 * @Description:修改${businessName}
	 * @param ${entityName} ${businessName}实体
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
	 * @Description: 根据${businessName}属性查询${businessName}信息
	 * @param pageBounds 定位信息
	 * @param ${entityName} ${businessName}
	 * @return List<${className}>
	 */
	List<${className}> query${className}s(PageBounds pageBounds, @Param("${entityName}") ${className} ${entityName});
	 
#foreach($item in $!{linkList})
#if(!${item.isBaseColumn})

  /**
	 * @Title: query${className}By${item.classMethod}
	 * @Description:根据${item.columnComment}查询${businessName}
	 * @param ${item.classParam} ${item.columnComment}
	 * @return List<${className}>
	 */
	List<${className}> query${className}By${item.classMethod}(${item.classType} ${item.classParam});

#end
#end
}
