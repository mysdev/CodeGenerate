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
import com.jl.utils.ClassUtil;
import com.jl.utils.DBUtil;
import com.jl.utils.FileUtil;
import com.jl.utils.VelocityEngineParser;

/**
 * @ClassName: MainApplication
 * @Description: 自动代码主类
 * @author: Jinlong He
 * @date: 2017年6月16日 上午9:45:06
 */
public class MainApplication {
	
	private static final Log log = LogFactory.getLog(MainApplication.class);
	
	private static SimpleDateFormat format = new SimpleDateFormat("yyyy年MM月dd日 HH时mm分");
	
	/**
	 * @fieldName: tableList
	 * @fieldType: List<TableData>
	 * @Description: 读取到的库表信息
	 */
	private static List<TableData> tableList = new ArrayList<TableData>();
	
	/**
	 * @fieldName: velocityList
	 * @fieldType: List<VelocityContext>
	 * @Description: 变量数据
	 */
	private static List<VelocityContext> velocityList = new ArrayList<VelocityContext>();
	
	
	/** 
	* @Title: main 
	* @Description: 程序主入口
	* @param arg  void    返回类型 
	* @throws 
	*/
	public static void main(String[] arg){		
		log.info("***************   CODE GENERATE START   ********************");	
		System.out.println(CGConfig.getConfigAsJsonString());
		
		//加载待处理表信息
		String tables = CGConfig.table_info;
		if(tables==null || tables.length()==0){
			readAllTableFromDB();
		}else{
			tables = tables.replaceAll("，", ",");
			tables = tables.replaceAll("；", ";");
			String[] ts = tables.split(";");
			for(int i=0 ; i<ts.length; i++){
				if(ts[i]!=null && ts[i].length()>0){
					String[] t = ts[i].split(",");
					TableData td = new TableData();
					td.setTableName(t[0]);
					if(t.length>1 && t[1]!=null && t[1].length()>0){
						td.setBusinessName(t[1]);
					}
				}
			}
		}		
		
		if(tableList.size()==0){
			log.error("error: can not read table !");
			System.exit(0);
		}
		//加载表格列信息
		loadTableColumnFromDB();
		if(tableList.size()==0){
			log.error("error: can not read table column!");
			System.exit(0);
		}
		
		//整体数据文件生成
		createTotalFile();
		
		//后台数据文件生成
		createServiceFile();
		
		//前台数据文件生成
		createWebFile();
		
		log.info("***************   CODE GENERATE OVER   ********************");	
	}
	
	
	/** 
	* @Title: createWebFile 
	* @Description: 前端代码生成
	* @return  Boolean    返回类型 
	* @throws 
	*/
	private static Boolean createWebFile() {
		if(CGConfig.templateInfoWeb!=null && CGConfig.templateInfoWeb.length()>5){
			if(velocityList.size()==0){
				//转换表数据到velocity
				changTableToVelocity();
			}
			String webPackage = CGConfig.business_package_web;
			if(webPackage!=null && webPackage.length()>0 && !webPackage.startsWith("/")){
				webPackage = "/"+webPackage;
			}
			String template = CGConfig.templateInfoWeb.replaceAll("，", ",");
			template = template.replaceAll("；", ";");
			String[] templates = template.split(";");
			for(int i=0; i<templates.length; i++){
				String[] ts = templates[i].split(",");
				if(ts==null || ts.length!=3){
					log.error("error: template_info_web can not read file ("+templates[i]+")");
					continue;
				}
				for(VelocityContext context: velocityList){
					//参数配置
					context.put("webPackage", webPackage); //包结构
					if(ts[1]!=null && ts[1].length()>0){
						if(!ts[1].startsWith("/")){
							ts[1] = "/"+ts[1];
						}
						context.put("webPackageExt", ts[1]); //子级目录
					}else{
						context.put("webPackageExt", null); //子级目录
					}					
					VelocityEngineParser.writerPage(context, ts[0], ts[1], context.get("className")+ts[2], webPackage);
				}				
			}
		}
		//处理Velocity与jQuery $符号冲突问题 		
		repalceJquery();
		return true;
	}
	
	private static void repalceJquery(){
		String webPath = CGConfig.projectSystemPath();
		if(CGConfig.source_root_package!=null && CGConfig.source_root_package.length()>0 && !CGConfig.source_root_package.startsWith("/")){
			webPath +="/";
		}
		if(CGConfig.source_root_package!=null){
			webPath+=CGConfig.source_root_package;
		}
		if(CGConfig.business_package_web!=null && CGConfig.business_package_web.length()>0 && !CGConfig.business_package_web.startsWith("/")){
			webPath += "/";
		}
		if(CGConfig.business_package_web!=null){
			webPath+=CGConfig.business_package_web;
		}
		System.out.println(webPath);
		List<String> fileList = FileUtil.readFileInDir(webPath);
		if(fileList!=null && fileList.size()>0){
			for(String file : fileList){
				FileUtil.replaceFileByLines(file, "\\(\\$\\)", "\\$");
			}			
		}
		
	}


	/** 
	* @Title: createServiceFile 
	* @Description: 后台代码生成 
	* @throws 
	*/
	private static Boolean createServiceFile() {
		if(CGConfig.templateInfo!=null && CGConfig.templateInfo.length()>5){
			if(velocityList.size()==0){
				//转换表数据到velocity
				changTableToVelocity();
			}
			//后台配置以com.jing.*
			String sysPath = ("/" + CGConfig.business_package.replaceAll("\\.", "/")); //一级目录
			
			String template = CGConfig.templateInfo.replaceAll("，", ",");
			template = template.replaceAll("；", ";");
			String[] templates = template.split(";");
			for(int i=0; i<templates.length; i++){
				String[] ts = templates[i].split(",");
				if(ts==null || ts.length!=3){
					log.error("error: template_info can not read file ("+templates[i]+")");
					continue;
				}
				for(VelocityContext context: velocityList){
					//参数配置
					context.put("package", CGConfig.business_package); //包结构
					if(ts[1]!=null && ts[1].length()>0){
						if(!ts[1].startsWith("/")){
							ts[1] = "/"+ts[1];
						}
						context.put("packageExt", ts[1].replaceAll("/", "\\.")); //子级目录
					}else{
						context.put("packageExt", null); //子级目录
					}					
					VelocityEngineParser.writerPage(context, ts[0], ts[1], context.get("className")+ts[2], sysPath);
				}				
			}
		}
		return true;	
	}


	
	
	/** 
	* @Title: createTotalFile 
	* @Description: 整体信息文件生成
	* @return  boolean    返回类型 
	* @throws 
	*/
	public static boolean createTotalFile(){
		if(CGConfig.templateInfoTotal!=null && CGConfig.templateInfoTotal.length()>5 && tableList.size()>0){
			VelocityContext context = new VelocityContext();
			context.put("tableList", tableList);
			String webPackage = CGConfig.business_package_web;
			if(webPackage!=null && webPackage.length()>0 && !webPackage.startsWith("/")){
				webPackage = "/"+webPackage;
			}
			context.put("webPackage", webPackage);
			String template = CGConfig.templateInfoTotal.replaceAll("，", ",");
			template = template.replaceAll("；", ";");
			String[] templates = template.split(";");
			for(int i=0; i<templates.length; i++){
				String[] ts = templates[i].split(",");
				if(ts==null || ts.length!=3){
					log.error("error: template_info_total can not read file ("+templates[i]+")");
					continue;
				}
				Map<String, String> mapDao = new HashMap<String, String>();
				mapDao.put("templateFile", ts[0]);
				mapDao.put("filePath", ts[1]);
				mapDao.put("fileName", ts[2]);		
				VelocityEngineParser.writerPage(context, ts[0], ts[1], ts[2], CGConfig.business_package_total);
			}
		}
		return true;		
	}
	
	
	/** 
	* @Title: changTableToVelocity 
	* @Description: 管理VelocityContext属性，包括全局配置以及映射表格属性
	* @throws 
	*/
	private static void changTableToVelocity() {
		for(TableData table : tableList){
			VelocityContext context = new VelocityContext();
			//全局参数映射
			context.put("datetime", format.format(System.currentTimeMillis()));
			context.put("author", CGConfig.author);
			context.put("email", CGConfig.email);			
			//表格属性自动反射映射
			try {
				Map<String, Object> tmap = ClassUtil.transBean2Map(table, true);
				for(String key : tmap.keySet()){
					context.put(key, tmap.get(key));
				}
				velocityList.add(context);
			} catch (Exception e) {
				log.error("error: can not chang table column to Velocity Context!"+table.getTableName());
			}			
		}		
	}


	
	/** 
	* @Title: loadTableColumnFromDB 
	* @Description: 加载表格的列信息
	* @throws 
	*/
	private static void loadTableColumnFromDB() {
		for(int i=tableList.size()-1; i>=0; i--){
			 List<ColumnData> colData = getColumnByTableName(tableList.get(i).getTableName());
			 if(colData!=null && colData.size()>0){
				 tableList.get(i).setColumnList(colData);
			 }else{
				 log.error("error: can not read table column from ->"+tableList.get(i).getTableName());
				 tableList.remove(i);
			 }
		}
	}
	
	
	/** 
	* @Title: getColumnByTableName 
	* @Description: 获取表格列信息
	* @param tableName
	* @return  List<ColumnData>    返回类型 
	* @throws 
	*/
	public static List<ColumnData> getColumnByTableName(String tableName){
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
				cd.setColumnName((String)map.get("column_name"));//列名
				if(!isMSsql){
					cd.setColumnType((String)map.get("data_type"));//数据类型
				}else{
					cd.setColumnType(mssqlDataType((Short)map.get("data_type")));//数据类型
				}
				cd.setColumnComment((String)map.get("column_comment"));//注释
				if(map.containsKey("data_precision") && map.get("data_precision")!=null){
					//精度 
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
					//范围 
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
					//字段长度  
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
						cd.setColumnNullable(((String)map.get("nullable")).toLowerCase().startsWith("y")?true:false);//可空
					}
				}
				
				cd.setClassType(CGUtil.getJavaTypeFromDBType(cd.getColumnType(), cd.getColumnPrecision(), cd.getColumnScale())); //java类型
				//cd.setClassParam(CGUtil.underline2Camel(cd.getColumnName()));	 //setColumnName自动处理				
//				for(String key : map.keySet()){
//					System.out.print(" "+cd.getClassName());
//				}
				ret.add(cd);
			}
		}		
		return ret;
	}
	
	
	
	
	/** 
	* @Title: readAllTableFromDB 
	* @Description: 从库中读取库表及备注信息
  	* @return void    返回类型 
	* @throws 
	*/
	private static void readAllTableFromDB(){
		DBUtil db = new DBUtil();
		List<Map<String, Object>> tList = null;
		if(db.getDbtype().equals("mysql")){
			tList= db.QueryTableToListMapObject("Select table_name tableName,TABLE_COMMENT tableComment from INFORMATION_SCHEMA.TABLES Where table_schema = '"
					+db.getDbname()+"' and table_name like 'tw_%'  order by table_name asc");
		}
		// TODO 暂时只实现了mysql
		
		if(tList!=null && tList.size()>0){
			for(Map<String, Object> map : tList){
				if(map!=null && map.containsKey("tablename")){
					TableData td = new TableData();
					td.setTableName((String)map.get("tablename"));
					String tn = (String)map.get("tablecomment");
					if(tn!=null && tn.length()>0){
						td.setBusinessName(tn);
					}
					tableList.add(td);
				}
			}
		}else{			
			log.error("error: table_info can not be null!");			
		}
	}
	

	/** 
	* @Title: mssqlDataType 
	* @Description: MSSQL数据类型
	* @param dtype
	* @return  String    返回类型 
	* @throws 
	*/
	private static String mssqlDataType(Short dtype){
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
}
