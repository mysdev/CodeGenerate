package ${package}#if($!packageExt)${packageExt}#end;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.jing.utils.Constant;
import com.jing.utils.paginator.domain.PageBounds;
import com.jing.utils.paginator.domain.PageList;
import com.jing.utils.paginator.domain.PageService;


import ${package}.model.entity.${className};
import ${package}.model.dao.${className}Mapper;
import ${package}.service.${className}Service;

/**
 * @ClassName: ${className}
 * @Description: ${businessName}服务实现类
 * @author: $!{author}
 * @email: mailto:$!{email}
 * @date: ${datetime}
 */
@Service("${entityName}Service")
@Transactional(readOnly=true)
public class  ${className}ServiceImpl implements ${className}Service {

	@Autowired
    private ${className}Mapper ${entityName}Mapper;   
    
	@Autowired
	private PageService pageService; // 分页器
	
	
	/**
	 * @Title: add${className}
	 * @Description:添加${businessName}
	 * @param ${entityName} 实体
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public ${className} add${className}(${className} ${entityName}){
		int ret = ${entityName}Mapper.add${className}(${entityName});
		if(ret>0){
			return ${entityName};
		}
		return null;
	}
	
	/**
	 * @Title modify${className}
	 * @Description:修改${businessName}
	 * @param ${entityName} 实体
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer modify${className}(${className} ${entityName}){
		return ${entityName}Mapper.modify${className}(${entityName});
	}
	
	/**
	 * @Title: drop${className}By${keyColumn.classMethod}
	 * @Description:删除${businessName}
	 * @param ${keyColumn.classParam} 实体标识
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer drop${className}By${keyColumn.classMethod}(${keyColumn.classType} ${keyColumn.classParam}){
		return ${entityName}Mapper.drop${className}By${keyColumn.classMethod}(${keyColumn.classParam});
	}
	
	/**
	 * @Title: query${className}By${keyColumn.classMethod}
	 * @Description:根据实体标识查询${businessName}
	 * @param ${keyColumn.classParam} 实体标识
	 * @return ${className}
	 */
	@Override
	public ${className} query${className}By${keyColumn.classMethod}(${keyColumn.classType} ${keyColumn.classParam}){
		return ${entityName}Mapper.query${className}By${keyColumn.classMethod}(${keyColumn.classParam});
	}                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                                   
	 
	/**
	 * @Title: query${className}ForPage
	 * @Description: 根据${businessName}属性与分页信息分页查询${businessName}信息
	 * @param pagenum 页 
	 * @param pagesize 页大小 
	 * @param sort 排序
	 * @param ${entityName} 实体
	 * @return List<${className}>
	 */
	@Override
	public Map<String, Object> query${className}ForPage(Integer pagenum, Integer pagesize, String sort, ${className} ${entityName}){
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageBounds pageBounds = pageService.getPageBounds(pagenum, pagesize, null, true, false);
		pageBounds.setOrdersByJson(sort, ${className}.class);
		List<${className}> entityList = ${entityName}Mapper.query${className}ForPage(pageBounds, ${entityName});
		if(null!=sort && sort.length()>0){
			pageBounds.setOrdersByJson(sort, ${className}.class);
		}
		if (!entityList.isEmpty()) {
			PageList<${className}> pagelist = (PageList<${className}>) entityList;
			returnMap.put(Constant.PAGELIST, entityList);
			returnMap.put(Constant.PAGINATOR, pagelist.getPaginator());
		}
		return returnMap;
	}
	 
	/**
	 * @Title: query${className}ByProperty
	 * @Description:根据属性查询${businessName}
	 * @return List<${className}>
	 */
	@Override
	public List<${className}> query${className}ByProperty(Map<String, Object> map){
		return ${entityName}Mapper.query${className}ByProperty(map);
	}


}
