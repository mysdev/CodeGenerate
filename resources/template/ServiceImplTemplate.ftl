package ${package}#if($!packageExt)${packageExt}#end;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hnzc.common.utils.paginator.Constant;
import com.hnzc.common.utils.paginator.domain.PageBounds;
import com.hnzc.common.utils.paginator.domain.PageList;
import com.hnzc.common.utils.paginator.domain.PageService;
import com.hnzc.common.utils.character.StringUtil;

#if($!{keyColumn.classType} == 'String')
import java.util.UUID;
#end


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
	private static final Logger logger = LoggerFactory.getLogger(${className}ServiceImpl.class);
	
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
#if($!{keyColumn.classType} == 'String')
		${entityName}.set$!{keyColumn.classMethod}(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
#end
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
	 * @param offset 起始位置
	 * @param pagesize 页大小 
	 * @param sort 排序
	 * @param ${entityName} 实体
	 * @return List<${className}>
	 */
	@Override
	public Map<String, Object> query${className}ForPage(Integer offset, Integer pagesize, String sort, ${className} ${entityName}){
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		if(offset==null && pagesize==null) {
			returnMap.put("data", ${entityName}Mapper.query${className}ByProperty(StringUtil.reqSortToMysqlSort(sort), ${entityName}));
		}
		PageBounds pageBounds = pageService.getPageBounds(offset, pagesize, null, true, false);
		if(null!=sort && sort.length()>0){
			pageBounds.setOrdersByJson(sort);
		}
		List<${className}> entityList = ${entityName}Mapper.query${className}ForPage(pageBounds, ${entityName});
		
		PageList<${className}> pagelist = (PageList<${className}>) entityList;
		returnMap.put(Constant.PAGELIST, entityList);
		returnMap.put(Constant.PAGINATOR, pagelist.getPaginator());
		
		return returnMap;
	}
	 

}
