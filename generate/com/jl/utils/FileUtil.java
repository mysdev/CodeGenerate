package com.jl.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * @ClassName: FileUtil
 * @Description: �ļ�����������
 * @author: Jinlong He
 * @date: 2018��1��26�� ����5:24:27
 */
public class FileUtil {
	
	public static void main(String[] arg){
		 List<String> fileList = readFileInDir("D:\\workspace\\CodeGenerate\\src\\clock");
		 for (String f1 : fileList) { 
	     	System.out.println(f1);  
	     } 
		 
		 readFileByLines(fileList.get(0));
	}

	private static List<String> readFileInDir(String fileDir) {  
        List<String> fileList = new ArrayList<String>();  
        File file = new File(fileDir);  
        if(!file.isDirectory()){
        	return fileList;
        }
        File[] files = file.listFiles();// ��ȡĿ¼�µ������ļ����ļ���  
        if (files == null) {// ���Ŀ¼Ϊ�գ�ֱ���˳�  
            return fileList;  
        }  
        // ������Ŀ¼�µ������ļ�  
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
     * ����Ϊ��λ��ȡ�ļ��������ڶ������еĸ�ʽ���ļ� 
     */  
    public static void readFileByLines(String fileName) {  
        File file = new File(fileName);  
        BufferedReader reader = null;  
        try {  
            System.out.println("����Ϊ��λ��ȡ�ļ����ݣ�һ�ζ�һ���У�");  
            reader = new BufferedReader(new FileReader(file));  
            String tempString = null;  
            int line = 1;  
            // һ�ζ���һ�У�ֱ������nullΪ�ļ�����  
            while ((tempString = reader.readLine()) != null) {  
                // ��ʾ�к�  
            	tempString = new String(tempString.getBytes("UTF-8"), "UTF-8");
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
}
