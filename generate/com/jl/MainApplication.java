package com.jl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.VelocityContext;

import com.jl.model.ColumnData;
import com.jl.model.TableData;
import com.jl.utils.CGUtil;
import com.jl.utils.DBUtil;
import com.jl.utils.PropertiesUtil;
import com.jl.utils.VelocityEngineParser;

/**
 * @ClassName: MainApplication
 * @Description: �Զ���������
 * @author: Jinlong He
 * @date: 2017��6��16�� ����9:45:06
 */
public class MainApplication {
	
	private static final Log log = LogFactory.getLog(MainApplication.class);
	
	
	
	public static void main(String[] arg){		
		log.info("***************   CODE GENERATE START   ********************");			
		MainApplication ma = new MainApplication();
		
		String tables = PropertiesUtil.Util(null).readValue("table_info");
		if(tables==null || tables.length()==0){
			log.error("error: table_info can not be null!");
			System.exit(0);
		}
		tables = tables.replaceAll("��", ",");
		tables = tables.replaceAll("��", ";");
		String[] ts = tables.split(";");
		for(int i=0 ; i<ts.length; i++){
			if(ts[i]!=null && ts[i].length()>0){
				String[] t = ts[i].split(",");
				System.out.println(t[1]);
				ma.doCodeGenerate(t[0], (t.length<2|| t[1]==null || t[1].length()==0)?"��"+t[0]:t[1]);
			}
		}
		log.info("***************   CODE GENERATE OVER   ********************");	
	}
	
	public boolean doCodeGenerate(String tableName, String businessName){
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		
		String codes = PropertiesUtil.Util(null).readValue("template_info");
		if(codes==null || codes.length()<5){
			log.error("error: template_info can not be null!");
			System.exit(0);
		}
		codes = codes.replaceAll("��", ",");
		codes = codes.replaceAll("��", ";");
		String[] template = codes.split(";");
		for(int i=0; i<template.length; i++){
			String[] ts = template[i].split(",");
			if(ts==null || ts.length!=3){
				log.error("error: template_info can not read("+template[i]+")");
				continue;
			}
			Map<String, String> mapDao = new HashMap<String, String>();
			mapDao.put("templateFile", ts[0]);
			mapDao.put("filePath", ts[1]);
			mapDao.put("fileName", ts[2]);		
			list.add(mapDao);
		}
//		Map<String, String> mapDao = new HashMap<String, String>();
//		mapDao.put("templateFile", "DaoTemplate.ftl");
//		mapDao.put("filePath", "/model/dao");
//		mapDao.put("fileName", "Mapper.java");		
//		list.add(mapDao);
//		
//		Map<String, String> mapEntity = new HashMap<String, String>();
//		mapEntity.put("templateFile", "EntityTemplate.ftl");
//		mapEntity.put("filePath", "/model/entity");
//		mapEntity.put("fileName", ".java");
//		list.add(mapEntity);
//		
//		Map<String, String> mapXml = new HashMap<String, String>();
//		mapXml.put("templateFile", "MapperTemplate.xml");
//		mapXml.put("filePath", "/model/mapper");
//		mapXml.put("fileName", "Mapper.xml");
//		list.add(mapXml);
//		
//		Map<String, String> mapSvr = new HashMap<String, String>();
//		mapSvr.put("templateFile", "ServiceTemplate.ftl");
//		mapSvr.put("filePath", "/service");
//		mapSvr.put("fileName", "Service.java");
//		list.add(mapSvr);
//		
//		Map<String, String> mapImpl = new HashMap<String, String>();
//		mapImpl.put("templateFile", "ServiceImplTemplate.ftl");
//		mapImpl.put("filePath", "/service/impl");
//		mapImpl.put("fileName", "ServiceImpl.java");
//		list.add(mapImpl);
//		
//		
//		Map<String, String> mapController = new HashMap<String, String>();
//		mapController.put("templateFile", "ControllerTemplate.ftl");
//		mapController.put("filePath", "/controller");
//		mapController.put("fileName", "Controller.java");
//		list.add(mapController);
		
		
		return createFile(tableName, businessName, list);
	}
	
	/** 
	* @Title: createFile 
	* @Description: ���ɴ���
	* @param tableName
	* @param businessName
	* @param list
	* @return  boolean    �������� 
	* @throws 
	*/
	public boolean createFile(String tableName, String businessName, List<Map<String, String>> list){
		TableData td = makeTableGenerate(tableName, businessName);
		if(td==null || td.getClassName()==null){
			log.info("get table info from db is error!");
			return false;
		}
		VelocityContext vc = makeVelocityContext(td);
		for(Map<String, String> map : list){
			VelocityEngineParser.writerPage(vc, map.get("templateFile"), map.get("filePath"), td.getClassName()+map.get("fileName"));
		}
		return true;
	}
	
	/** 
	* @Title: makeVelocityContext 
	* @Description: ƴװ����
	* @param td
	* @return  VelocityContext    �������� 
	* @throws 
	*/
	public VelocityContext makeVelocityContext(TableData td){
		SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd�� HHʱmm��");
		
		VelocityContext context = new VelocityContext();
		context.put("datetime", format.format(System.currentTimeMillis()));
		context.put("author", PropertiesUtil.Util(null).readValue("author"));
		context.put("email", PropertiesUtil.Util(null).readValue("email"));
		context.put("tableName", td.getTableName());	//����
		context.put("className", td.getClassName());	//����
		context.put("entityName", td.getEntityName());	//ʵ����
		context.put("pathName", td.getPathName());	//·����		
		context.put("businessName", td.getBusinessName());	//ҵ������
		if(td.getColumnData()!=null && td.getColumnData().size()>0){
			context.put("keyColumn", td.getColumnData().get(0)); //key
		}		
//		context.put("tableKey", td.getTableKey());	//��KEY	W
//		context.put("classKey", td.getClassKey());	//��KEY
//		context.put("classKeyType", td.getClassKeyType());	//KEY����		
		context.put("columnList", td.getColumnData()); //����Ϣ
		String tableItem = "";
		for(ColumnData c : td.getColumnData()){
			tableItem += (c.getColumnName()+", ");
		}
		if(tableItem.endsWith(", ")){
			context.put("columnItem", tableItem.substring(0, tableItem.length()-2));
		}
		//TODO ���ڸ���Ϊ��������
		
		return context;
	}
	
	
	/** 
	* @Title: makeTableGenerate 
	* @Description: ��װ����Ϣ 
	* @param tableName ������
	* @param businessName  ҵ������
	* @return TableData  �������� 
	* @throws 
	*/
	public TableData makeTableGenerate(String tableName, String businessName){
		TableData table = new TableData();
		table.setTableName(tableName);
		table.setBusinessName(businessName);
		table.setColumnData(getColumnByTableName(tableName));
//		if(table.getColumnData()!=null && table.getColumnData().size()>0 
//				&& table.getColumnData().get(0)!=null && table.getColumnData().get(0).getColumnName()!=null
//				//&& table.getColumnData().get(0).getColumnName().toLowerCase().endsWith("id")
//				){
//			//����KEY�ֶ�
//			table.setTableKey(table.getColumnData().get(0).getColumnName());
//			table.setClassKey(table.getColumnData().get(0).getClassParam());
//			table.setClassKeyType(table.getColumnData().get(0).getClassType());
//		}
		return table;
	}
	
	
	
	/** 
	* @Title: mssqlDataType 
	* @Description: MSSQL��������
	* @param dtype
	* @return  String    �������� 
	* @throws 
	*/
	private String mssqlDataType(Short dtype){
		if(dtype==null){	return null;	}
		if(dtype.intValue()==34){	return "image";	}
		if(dtype.intValue()==35){	return "text";	}
		if(dtype.intValue()==36){	return "uniqueidentifier";	}
		if(dtype.intValue()==48){	return "tinyint";	}
		if(dtype.intValue()==52){	return "smallint";	}
		if(dtype.intValue()==56){	return "int";	}
		if(dtype.intValue()==58){	return "smalldatetime";	}
		if(dtype.intValue()==59){	return "real";	}
		if(dtype.intValue()==60){	return "money";	}
		if(dtype.intValue()==61){	return "datetime";	}
		if(dtype.intValue()==62){	return "float";	}
		if(dtype.intValue()==98){	return "sql_variant";	}
		if(dtype.intValue()==99){	return "ntext";	}
		if(dtype.intValue()==104){	return "bit";	}
		if(dtype.intValue()==106){	return "decimal";	}
		if(dtype.intValue()==108){	return "numeric";	}
		if(dtype.intValue()==122){	return "smallmoney";	}
		if(dtype.intValue()==127){	return "bigint";	}
		if(dtype.intValue()==165){	return "varbinary";	}
		if(dtype.intValue()==167){	return "varchar";	}
		if(dtype.intValue()==173){	return "binary";	}
		if(dtype.intValue()==175){	return "char";	}
		if(dtype.intValue()==189){	return "timestamp";	}
		if(dtype.intValue()==231){	return "sysname";	}
		if(dtype.intValue()==231){	return "nvarchar";	}
		if(dtype.intValue()==239){	return "nchar";	}
		return null;

	}
	
	
	/** 
	* @Title: getColumnByTableName 
	* @Description: ��ȡ
	* @param tableName
	* @return  List<ColumnData>    �������� 
	* @throws 
	*/
	public List<ColumnData> getColumnByTableName(String tableName){
		tableName = tableName.toUpperCase();
		DBUtil db = new DBUtil();
		String sql = "";
		boolean isMSsql = false;
		if("mysql".equals(db.getDbtype())){
			sql = "select column_name ,data_type,column_comment,0 as data_precision,0 as data_scale, is_nullable as nullable, character_maximum_length as data_length from information_schema.columns where UPPER(table_name) =  '"
					+ tableName + "' ";
			if(db.getDbname()!=null && db.getDbname().length()>0){
				sql +=( "and table_schema =  '" + db.getDbname() + "'");
			}
		}else if("oracle".equals(db.getDbtype())){
			sql = "SELECT T1.COLUMN_NAME,T1.DATA_TYPE,T2.COMMENTS as column_comment,T1.DATA_PRECISION,T1.DATA_SCALE,T1.DATA_LENGTH,T1.NULLABLE"+
					  " FROM USER_TAB_COLS T1, USER_COL_COMMENTS T2 "+
					 "WHERE T1.TABLE_NAME = T2.TABLE_NAME "+
					   "AND T1.COLUMN_NAME = T2.COLUMN_NAME "+
					   "AND T1.TABLE_NAME = upper('"+tableName+"') ORDER BY T1.SEGMENT_COLUMN_ID";
		}else if("mssql".equals(db.getDbtype())){
			isMSsql = true;
			sql = "select b.[name] as 'column_name', b.[system_type_id] as 'data_type', CONVERT(varchar(100), c.[value]) as 'column_comment', "
					+" b.[precision] as 'data_precision',b.[scale] as 'data_scale', b.[is_nullable] as 'nullable', b.[max_length] as 'data_length'  from sys.tables a  "
					+" left join sys.columns b on a.object_id=b.object_id  "
					+" left join sys.extended_properties c on a.object_id=c.major_id  "
					+" where a.name='"+tableName+"' and ( (c.minor_id<>0 and b.column_id=c.minor_id ) "
					+" or c.minor_id is null)  and a.schema_id=(select schema_id from sys.schemas where name='dbo') ORDER BY b.column_id asc";
		}
		List<ColumnData> ret = new ArrayList<ColumnData>();
		List<Map<String, Object>> list = db.QueryTableToListMapObject(sql);
		if(list!=null && list.size()>0){
			for(Map<String, Object> map : list){
				ColumnData cd = new ColumnData();
				cd.setColumnName((String)map.get("column_name"));//����
				if(!isMSsql){
					cd.setColumnType((String)map.get("data_type"));//��������
				}else{
					cd.setColumnType(mssqlDataType((Short)map.get("data_type")));//��������
				}
				cd.setColumnComment((String)map.get("column_comment"));//ע��
				if(map.containsKey("data_precision") && map.get("data_precision")!=null){
					//���� 
					if(map.get("data_precision") instanceof Long){
						//mysql
						cd.setColumnPrecision((Long)map.get("data_precision"));
					}else if(map.get("data_precision") instanceof Short){
						//mssql
						cd.setColumnPrecision(0l+(Short)map.get("data_precision"));
					}else {
						//oracle
						cd.setColumnPrecision(Long.parseLong(""+(java.math.BigDecimal)map.get("data_precision"))); 
					}
				}
				if(map.containsKey("data_scale") && map.get("data_scale")!=null){
					//��Χ 
					if(map.get("data_scale") instanceof Long){
						//mysql
						cd.setColumnScale((Long)map.get("data_scale"));
					}else if(map.get("data_precision") instanceof Short){
						//mssql
						cd.setColumnScale(0l+(Short)map.get("data_scale"));
					}else {
						//oracle
						cd.setColumnScale(Long.parseLong(""+(java.math.BigDecimal)map.get("data_scale"))); 
					}
				}
				if(map.containsKey("data_length") && map.get("data_length")!=null){
					//�ֶγ���  
					if(map.get("data_length") instanceof java.math.BigInteger){
						//mysql
						cd.setColumnCharMaxLength(Long.parseLong(""+(java.math.BigInteger)map.get("data_length"))); 
						//cd.setColumnCharMaxLength((Long)map.get("data_length"));
					}else if(map.get("data_precision") instanceof Short){
						//mssql
						cd.setColumnCharMaxLength(0l+(Short)map.get("data_length"));
					}else {
						//oracle
						cd.setColumnCharMaxLength(Long.parseLong(""+(java.math.BigDecimal)map.get("data_length"))); 
					}
				}
				if(map.containsKey("nullable") && map.get("nullable")!=null){
					if(map.get("nullable") instanceof Boolean){
						//mssql
						cd.setColumnNullable((Boolean)map.get("nullable"));
					}else {
						cd.setColumnNullable(((String)map.get("nullable")).toLowerCase().startsWith("y")?true:false);//�ɿ�
					}
				}
				
				cd.setClassType(CGUtil.getJavaTypeFromDBType(cd.getColumnType(), cd.getColumnPrecision(), cd.getColumnScale())); //java����
				//cd.setClassParam(CGUtil.underline2Camel(cd.getColumnName()));	 //setColumnName�Զ�����				
//				for(String key : map.keySet()){
//					System.out.print(" "+cd.getClassName());
//				}
				ret.add(cd);
			}
		}		
		return ret;
	}
	

}
