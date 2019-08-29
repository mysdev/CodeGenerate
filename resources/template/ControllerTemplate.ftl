package ${package}#if($!packageExt)${packageExt}#end;

import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hnzc.common.exception.NotFoundException;
import ${package}.controller.vo.RowsResp;
import ${package}.model.entity.${className};
import ${package}.service.${className}Service;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * @ClassName: ${className}Controller
 * @Description: ${businessName}HTTP接口
 * @author: $!{author}
 * @email: mailto:$!{email}
 * @date: ${datetime}
 */
@RestController
@Api(tags="${className}")
public class ${className}Controller{
	
	@Autowired
	private ${className}Service ${entityName}Service;

	/**
	 * @description 添加${businessName}信息
	 * @param response
	 * @param userID 操作人员标识
	 * @param ${entityName} ${businessName}
	 * @return ${className}
	 */
	@RequestMapping(value = "/${pathName}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public ${className} add${className}(HttpServletResponse response, @RequestHeader("user-id") Long userID,
			@ApiParam(value = "${entityName}") @RequestBody ${className} ${entityName}){		
		${entityName}.setCreateId(userID);
		${entityName}.setModifiedId(userID);
		return ${entityName}Service.add${className}(${entityName});
	}
	
	/**
	 * @description 根据${businessName}标识更新${businessName}信息
	 * @param response
	 * @param userID 操作人员标识
	 * @param $!{keyColumn.classParam} ${businessName}标识
	 * @param ${entityName} ${businessName}
	 * @return RowsResp
	 */
	@RequestMapping(value = "/${pathName}/{$!{keyColumn.classParam}:.+}", method = RequestMethod.PUT)
	public RowsResp modify${className}ById(HttpServletResponse response, @RequestHeader("user-id") Long userID,
			@PathVariable $!{keyColumn.classType} $!{keyColumn.classParam},	@ApiParam(value = "${entityName}", required = true) @RequestBody ${className} ${entityName}	){
		${className} temp${className} = ${entityName}Service.query${className}By${keyColumn.classMethod}($!{keyColumn.classParam});
		${entityName}.set$!{keyColumn.classMethod}($!{keyColumn.classParam});
		if(null == temp${className}){
			throw new NotFoundException("${businessName}未找到");
		}
		${entityName}.setModifiedId(userID);
		return new RowsResp(${entityName}Service.modify${className}(${entityName}));
	}
	
	/**
	 * @description 根据${businessName}标识查询${businessName}信息
	 * @param response
	 * @param $!{keyColumn.classParam} ${businessName}标识
	 * @return ${className}
	 */
	@RequestMapping(value = "/${pathName}/{$!{keyColumn.classParam}:.+}", method = RequestMethod.GET)
	public ${className} query${className}ById(HttpServletResponse response,
			@PathVariable $!{keyColumn.classType} $!{keyColumn.classParam}){
		${className} temp${className} = ${entityName}Service.query${className}By${keyColumn.classMethod}($!{keyColumn.classParam});		
		if(null == temp${className}){
			throw new NotFoundException("${businessName}未找到");
		}		
		return temp${className};
	}

  /**
	 * @description 根据${businessName}标识删除${businessName}信息
	 * @param response
	 * @param $!{keyColumn.classParam} ${businessName}标识
	 * @return RowsResp
	 */
	@ApiOperation(value = "删除 根据${businessName}标识删除${businessName}信息", notes = "根据${businessName}标识删除${businessName}信息")
	@RequestMapping(value = "/${pathName}/{$!{keyColumn.classParam}:.+}", method = RequestMethod.DELETE)
	public RowsResp drop${className}By${keyColumn.classMethod}(HttpServletResponse response, @RequestHeader("user-id") Long userID, @PathVariable $!{keyColumn.classType} $!{keyColumn.classParam}) {
		${className} ${entityName} = ${entityName}Service.query${className}By${keyColumn.classMethod}($!{keyColumn.classParam});
		if(null == ${entityName}){
			throw new NotFoundException("${businessName}未找到");
		}
		return new RowsResp(${entityName}Service.drop${className}By${keyColumn.classMethod}($!{keyColumn.classParam}, userID));
	}
		
	/**
	 * @description 根据${businessName}属性查询${businessName}信息列表 *注 当offset与pageSize均为空时返回全量数据
	 * @param response
	 * @param _offset 偏移量，为空时取0 *与数据大小同时为空时返回全量数据
	 * @param _pageSize 数据大小，为空时取默认值 *与偏移量同时为空时返回全量数据
	 * @param _sort 排序 如 id,asc;name,desc
	 * @param ${entityName} ${businessName}属性
	 * @return Map<String, Object>
	 */
	@RequestMapping(value = "/${pathName}s", method = RequestMethod.GET)
	public Map<String, Object> query${className}s(HttpServletResponse response,
			@RequestParam(value = "_offset", required = false) Integer _offset,
			@RequestParam(value = "_pageSize", required = false) Integer _pageSize, 
			@RequestParam(value = "_sort", required = false) String _sort, ${className} ${entityName}) {
		return ${entityName}Service.query${className}s(_offset, _pageSize, _sort, ${entityName});
	}
	
#foreach($item in $!{linkList})
#if(!${item.isBaseColumn})

  /**
	 * @Title: query${className}By${item.classMethod}
	 * @Description:根据${item.columnComment}查询${businessName}
	 * @param ${item.classParam} ${item.columnComment}
	 * @return List<${className}>
	 */
	@RequestMapping(value = "/${item.classParam}/{${item.classParam}:.+}/${pathName}s", method = RequestMethod.GET)
	List<${className}> query${className}By${item.classMethod}(@PathVariable ${item.classType} ${item.classParam}){
		return ${entityName}Service.query${className}By${item.classMethod}(${item.classParam});
	}
#end
#end

}
