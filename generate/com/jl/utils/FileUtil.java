package com.jl.utils;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @ClassName: FileUtil
 * @Description: 文件操作工具类
 * @author: Jinlong He
 * @date: 2018年1月26日 下午5:24:27
 */
public class FileUtil {
	
	public static void main(String[] arg){	
//		
//		 List<String> fileList = readFileInDir("D:\\workspace\\CodeGenerate\\src\\clock\\model");
//		 for (String f1 : fileList) { 
//	     	System.out.println(f1);  
//	     }
//		 replaceFileByLines(fileList.get(0), "(\\$)", "\\$");
//		 
//		 readFileByLines(fileList.get(0));
	}

	public static List<String> readFileInDir(String fileDir) {  
        List<String> fileList = new ArrayList<String>();  
        File file = new File(fileDir);  
        if(!file.isDirectory()){
        	return fileList;
        }
        File[] files = file.listFiles();// 获取目录下的所有文件或文件夹  
        if (files == null) {// 如果目录为空，直接退出  
            return fileList;  
        }  
        // 遍历，目录下的所有文件  
        for (File f : files) {  
            if (f.isFile()) {             	
            	fileList.add(f.getAbsolutePath());
            } else if (f.isDirectory()) {
                fileList.addAll(readFileInDir(f.getAbsolutePath()));  
            }  
        }        
        return fileList;
    }  
	
	
	 /** 
     * 以行为单位读取文件，常用于读面向行的格式化文件 
     */  
    public static void readFileByLines(String fileName) {  
        File file = new File(fileName);  
        BufferedReader reader = null;  
        try {  
            System.out.println("以行为单位读取文件内容，一次读一整行：");  
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            int line = 1;  
            // 一次读入一行，直到读入null为文件结束  
            while ((tempString = reader.readLine()) != null) {  
                // 显示行号  
            	//tempString = new String(tempString.getBytes("UTF-8"), "UTF-8");
                System.out.println("line " + line + ": " + tempString);  
                line++;  
            }  
            reader.close();  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
    }  
    

    public static void replaceFileByLines(String fileName, String oldString, String newString){  
        File file = new File(fileName);  
        boolean isRep = false;
     // tmpfile为缓存文件，代码运行完毕后此文件将重命名为源文件名字。  
        File tmpfile = new File(file.getParentFile().getAbsolutePath()  + "\\" + file.getName() + ".tmp");  
        BufferedWriter writer = null;
        BufferedReader reader = null;  
        try {  
        	Pattern pattern = Pattern.compile(".*("+oldString+").*");
//            System.out.println("以行为单位替换文件内容，一次读一整行："+oldString+" -> "+newString);  
            reader = new BufferedReader(new FileReader(file));  
            writer = new BufferedWriter(new FileWriter(tmpfile));
            String tempString = null;  
            int line = 1;  
            // 一次读入一行，直到读入null为文件结束  
            while ((tempString = reader.readLine()) != null) {  
                // 显示行号  
                //System.out.println("line " + line + ": " + tempString);  
                //if(tempString.contains(oldString.replaceAll("\\\\", ""))){
         		if(pattern.matcher(tempString).matches()){
//                	System.out.println("FROM:"+tempString+  "    "+ oldString);
                	tempString = tempString.replaceAll(oldString, newString);
//                	System.out.println("TO:"+tempString);
                	isRep = true;
                }
                writer.write(tempString + "\n");
                line++;  
            }  
            reader.close(); 
            writer.flush();
            writer.close();
            
            
            if (isRep) {  
                file.delete();  
                tmpfile.renameTo(new File(file.getAbsolutePath()));  
            } else{  
                tmpfile.delete();  
            }            
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            if (reader != null) {  
                try {  
                    reader.close();  
                } catch (IOException e1) {  
                }  
            }  
            if (writer != null) {  
                try {  
                	writer.close();  
                } catch (IOException e1) {  
                }  
            }  
        }  
    }  
}
