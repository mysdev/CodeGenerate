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
		${entityName}.set$!{keyColumn.classMethod}(null);
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
	 * @param ${keyColumn.classParam} ${businessName}标识
	 * @param userID 操作人员标识
	 * @return Integer
	 */
	@Override
	@Transactional(readOnly = false)
	public Integer drop${className}By${keyColumn.classMethod}(${keyColumn.classType} ${keyColumn.classParam}, Long userID){
		return ${entityName}Mapper.drop${className}By${keyColumn.classMethod}(${keyColumn.classParam});
	}
	
	/**
	 * @Title: query${className}By${keyColumn.classMethod}
	 * @Description:根据实体标识查询${businessName}
	 * @param ${keyColumn.classParam} ${businessName}标识
	 * @return ${className}
	 */
	@Override
	public ${className} query${className}By${keyColumn.classMethod}(${keyColumn.classType} ${keyColumn.classParam}){
		return ${entityName}Mapper.query${className}By${keyColumn.classMethod}(${keyColumn.classParam});
	}
	 
	/**
	 * @Title: query${className}s
	 * @Description: 根据${businessName}属性查询${businessName}信息 *offset与pagesize为空时查询全量
	 * @param offset 起始位置
	 * @param pagesize 数据大小
	 * @param sort 排序
	 * @param ${entityName} 实体
	 * @return List<${className}>
	 */
	@Override
	public Map<String, Object> query${className}s(Integer offset, Integer pagesize, String sort, ${className} ${entityName}){
		HashMap<String, Object> returnMap = new HashMap<String, Object>();
		PageBounds pageBounds = pageService.getPageBounds(offset, pagesize, sort, true, false);
		List<${className}> entityList = ${entityName}Mapper.query${className}s(pageBounds, ${entityName});
		
		PageList<${className}> pagelist = (PageList<${className}>) entityList;
		returnMap.put(Constant.PAGELIST, entityList);
		returnMap.put(Constant.PAGINATOR, pagelist.getPaginator());		
		return returnMap;
	}
	 
#foreach($item in $!{linkList})
#if(!${item.isBaseColumn})

	/**
	 * @Title: query${className}By${item.classMethod}
	 * @Description:根据${item.columnComment}查询${businessName}
	 * @param ${item.classParam} ${item.columnComment}
	 * @return List<${className}>
	 */
	@Override
	public List<${className}> query${className}By${item.classMethod}(${item.classType} ${item.classParam}){
		return ${entityName}Mapper.query${className}By${item.classMethod}(${item.classParam});
	}

#end
#end
}
