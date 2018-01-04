package com.jl.utils;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.util.Properties;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

import com.jl.CGConfig;

public class VelocityEngineParser {
	private static VelocityEngine ve;
	private static String CONTENT_ENCODING = "UTF-8";
	private static final Log log = LogFactory.getLog(VelocityEngineParser.class);

	private static boolean isReplace = true; //�����ļ�

	static {
		try {
			CONTENT_ENCODING = CGConfig.system_encoding;
			String templateBasePath = CGConfig.projectSystemPath() + CGConfig.template_path;
			;
			Properties properties = new Properties();
			properties.setProperty("resource.loader", "file");
			properties.setProperty("file.resource.loader.description", "Velocity File Resource Loader");
			properties.setProperty("file.resource.loader.path", templateBasePath);
			properties.setProperty("file.resource.loader.cache", "true");
			properties.setProperty("file.resource.loader.modificationCheckInterval", "30");
			properties.setProperty("runtime.log.logsystem.class", "org.apache.velocity.runtime.log.Log4JLogChute");
			properties.setProperty("runtime.log.logsystem.log4j.logger", "org.apache.velocity");
			properties.setProperty("directive.set.null.allowed", "true");
			VelocityEngine velocityEngine = new VelocityEngine();
			velocityEngine.init(properties);
			ve = velocityEngine;
		} catch (Exception e) {
			log.error(e);
		}
	}

	/** 
	* @Title: writerPage 
	* @Description: 
	* @param context ����
	* @param templateFile	ģ��
	* @param filePath ��չ·��
	* @param fileName �ļ�����
	* @return  boolean    �������� 
	* @throws 
	*/
	public static boolean writerPage(VelocityContext context, String templateFile, String filePath, String fileName) {
		String sysPath = CGConfig.projectSystemPath();
		sysPath += CGConfig.source_root_package;
		sysPath += ("/" + CGConfig.business_package.replaceAll("\\.", "/"));
		if (filePath != null && filePath.trim().length() > 0) {
			filePath = filePath.trim();
			if (!filePath.startsWith("/")) {
				filePath = "/" + filePath;
			}
		}
		if (filePath != null) {
			sysPath += filePath;
			context.put("packageExt", filePath.replaceAll("/", "\\."));
		}
		//��������
		context.put("package", CGConfig.business_package);
		
		
		try {
			File file = new File(sysPath +"/"+ fileName);
			if (!file.exists()) {
				new File(file.getParent()).mkdirs();
			} else if (isReplace) {
				log.info("file  existence:" + file.getAbsolutePath());
			}

			Template template = ve.getTemplate(templateFile, CONTENT_ENCODING);
			FileOutputStream fos = new FileOutputStream(file);
			BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(fos, CONTENT_ENCODING));
			template.merge(context, writer);
			writer.flush();
			writer.close();
			fos.close();
			log.info("file create:" + file.getAbsolutePath());
		} catch (Exception e) {
			log.error(e);
			return false;
		}
		return true;
	}
}
