<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE mapper PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN" "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
<mapper namespace="${package}.model.dao.${className}Mapper">

<!-- Result Map-->
<resultMap id="${className}ResultMap" type="${package}.model.entity.${className}">
#set($i=0)
#foreach($item in $!{columnList})
#if($i==0)
	<id column="$!item.columnName" property="$!item.classParam"/>
#else
	<result column="$!item.columnName" property="$!item.classParam"/>
#end
#set($i=$i+1)
#end
</resultMap>

<sql id="sql_${entityName}_item">
	${columnItem}
</sql>
       
<!-- 查询条件 无WHERE -->
<sql id="sql_${entityName}_query_nowhere">
	<trim prefix=" " prefixOverrides="AND|OR">
#foreach($item in $!{columnList})
#if($!item.classType == 'String')
		<if test="${entityName}.${item.classParam} != null and ${entityName}.${item.classParam}.length>0">AND $!item.columnName like concat(#{${entityName}.${item.classParam}}, '%') </if>
#end
#if($!item.classType == 'Integer')
		<if test="${entityName}.${item.classParam} != null">AND $!item.columnName = #{${entityName}.${item.classParam}} </if>
#end
#end
	</trim>
</sql>
   
<!-- 插入 ${businessName} -->
<insert id="add${className}" parameterType="${package}.model.entity.${className}" >
#if($!{keyColumn.classType} == 'Integer' or $!{keyColumn.classType} == 'Long')
	<selectKey keyProperty="$!{keyColumn.classParam}" order="AFTER" resultType="java.lang.${keyColumn.classType}">
		SELECT LAST_INSERT_ID()
	</selectKey>
#end 	
 	INSERT INTO ${tableName} 
	<trim prefix=" (" suffix=")" suffixOverrides=",">
#foreach($item in $!{columnList})
#if($!{item.isInsertColumn})
		$!{item.columnName},
#elseif($!{item.isUpdateColumn})
		$!{item.columnName},
#else		
		<if test="$!item.classParam != null"> $!item.columnName,</if>
#end
#end
	</trim>	 	
	<trim prefix="values (" suffix=")" suffixOverrides=",">
#foreach($item in $!{columnList})
#if($!{item.isInsertColumn})
		now(),
#elseif($!{item.isUpdateColumn})
		now(),
#else
		<if test="$!item.classParam != null"> #{$!item.classParam},</if>
#end
#end
	</trim>
</insert>

<!-- 根据标识，修改 ${businessName} -->  
<update id="modify${className}" parameterType="${package}.model.entity.${className}" >
  	UPDATE ${tableName} SET
	<trim suffixOverrides=",">
#set($i=0)
#foreach($item in $!{columnList})
#if($i!=0)	
#if($!{item.isUpdateColumn})
		$!item.columnName = now(),
#elseif($!{item.isInsertColumn} or $!{item.isInsertUserColumn})
#else
		<if test="${item.classParam} != null"> $!item.columnName = #{${item.classParam}},</if>
#end
#end	
#set($i=$i+1)
#end
	</trim>
	WHERE ${keyColumn.columnName} = #{${keyColumn.classParam}}
</update>
 
<!-- 删除 ${businessName} -->
<delete id="drop${className}By${keyColumn.classMethod}" parameterType="java.lang.${keyColumn.classType}">
	DELETE FROM ${tableName} WHERE ${keyColumn.columnName} = #{${keyColumn.classParam}}
</delete> 

<!-- 根据标识查询 ${businessName} -->
<select id="query${className}By${keyColumn.classMethod}"  resultMap="${className}ResultMap" parameterType="java.lang.${keyColumn.classType}">
	SELECT 
	<include refid="sql_${entityName}_item"/>
	FROM ${tableName} WHERE ${keyColumn.columnName} = #{${keyColumn.classParam}}
</select>

<!-- 根据${businessName}属性与分页信息查询${businessName}信息 -->
<select id="query${className}s"  resultMap="${className}ResultMap" > 
	SELECT 
	<include refid="sql_${entityName}_item"/>
	FROM ${tableName} 
	<where>
		<include refid="sql_${entityName}_query_nowhere"/>
	</where>
</select>
#foreach($item in $!{linkList})
#if(!${item.isBaseColumn})

<!-- 根据${item.columnComment}查询${businessName} -->
<select id="query${className}By${item.classMethod}" resultMap="${className}ResultMap" parameterType="java.lang.${item.classType}">
	SELECT 
	<include refid="sql_${entityName}_item"/>
	FROM ${tableName} where ${item.columnName} = #{${item.classParam}}
</select>

#end
#end
</mapper>   
