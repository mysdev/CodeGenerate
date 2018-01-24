package ${package}#if($!packageExt)${packageExt}#end;

import java.util.Map;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jing.config.web.exception.NotFoundException;
import com.jing.config.web.exception.ParameterException;
import java.beans.IntrospectionException;
import java.lang.reflect.InvocationTargetException;
import com.jing.config.validation.BeanValidator;
import ${package}.model.entity.${className};
import ${package}.service.${className}Service;
import com.jing.utils.ClassUtil;

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
@Api(description="${businessName}")
public class ${className}Controller{

	@Autowired
	BeanValidator beanValidator;
	
	@Autowired
	private ${className}Service ${entityName}Service;

	
	@ApiOperation(value = "新增 添加${businessName}信息", notes = "添加${businessName}信息")
	@RequestMapping(value = "/${pathName}", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
	public Object add${className}(HttpServletResponse response,
			@ApiParam(value = "${entityName}") @RequestBody ${className} ${entityName}) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		${entityName}.set$!{keyColumn.classMethod}(null);
		${entityName}Service.add${className}(${entityName});
		return ${entityName};
	}
	
	
	@ApiOperation(value = "更新 根据${businessName}标识更新${businessName}信息", notes = "根据${businessName}标识更新${businessName}信息")
	@RequestMapping(value = "/${pathName}/{$!{keyColumn.classParam}:.+}", method = RequestMethod.PUT)
	public Object modify${className}ById(HttpServletResponse response,
			@PathVariable $!{keyColumn.classType} $!{keyColumn.classParam},
			@ApiParam(value = "${entityName}", required = true) @RequestBody ${className} ${entityName}
			) throws IllegalArgumentException, IllegalAccessException, NoSuchFieldException, SecurityException {
		${className} temp${className} = ${entityName}Service.query${className}By${keyColumn.classMethod}($!{keyColumn.classParam});
		${entityName}.set$!{keyColumn.classMethod}($!{keyColumn.classParam});
		if(null == temp${className}){
			throw new NotFoundException("${businessName}");
		}
		return ${entityName}Service.modify${className}(${entityName});
	}

	@ApiOperation(value = "删除 根据${businessName}标识删除${businessName}信息", notes = "根据${businessName}标识删除${businessName}信息")
	@RequestMapping(value = "/${pathName}/{$!{keyColumn.classParam}:.+}", method = RequestMethod.DELETE)
	public Object drop${className}By${keyColumn.classMethod}(HttpServletResponse response, @PathVariable $!{keyColumn.classType} $!{keyColumn.classParam}) {
		${className} ${entityName} = ${entityName}Service.query${className}By${keyColumn.classMethod}($!{keyColumn.classParam});
		if(null == ${entityName}){
			throw new NotFoundException("${businessName}");
		}
		return ${entityName}Service.drop${className}By${keyColumn.classMethod}($!{keyColumn.classParam});
	}
	
	@ApiOperation(value = "查询 根据${businessName}标识查询${businessName}信息", notes = "根据${businessName}标识查询${businessName}信息")
	@RequestMapping(value = "/${pathName}/{$!{keyColumn.classParam}:.+}", method = RequestMethod.GET)
	public Object query${className}ById(HttpServletResponse response,
			@PathVariable $!{keyColumn.classType} $!{keyColumn.classParam}) {
		${className} ${entityName} = ${entityName}Service.query${className}By${keyColumn.classMethod}($!{keyColumn.classParam});
		if(null == ${entityName}){
			throw new NotFoundException("${businessName}");
		}
		return ${entityName};
	}
	
	@ApiOperation(value = "查询 根据${businessName}属性查询${businessName}信息列表", notes = "根据${businessName}属性查询${businessName}信息列表")
	@RequestMapping(value = "/${pathName}", method = RequestMethod.GET)
	public Object query${className}List(HttpServletResponse response,
			${className} ${entityName}) throws IntrospectionException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {		
		return ${entityName}Service.query${className}ByProperty(ClassUtil.transBean2Map(${entityName}, false));
	}
	
	@ApiOperation(value = "查询分页 根据${businessName}属性分页查询${businessName}信息列表", notes = "根据${businessName}属性分页查询${businessName}信息列表")
	@RequestMapping(value = "/${pathName}s", method = RequestMethod.GET)
	public Object query${className}Page(HttpServletResponse response,
			@RequestParam(value = "pageNo", required = false) Integer pagenum,
			@RequestParam(value = "pageSize", required = false) Integer pagesize, 
			@RequestParam(value = "sort", required = false) String sort, ${className} ${entityName}) {				
		return ${entityName}Service.query${className}ForPage(pagenum, pagesize, sort, ${entityName});
	}

}
