package com.example.demo.util;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


/*
 * public String upload(MultipartFile file,History history)
 * public String multiUpload(MultipartFile[] files,History history)
 * public String save(MultipartFile file,History history)
 * public String generatePicPathByDataPath(History history)
 * public void createFolder(String s)
 * public void deleteFolder(String email)
 * public void deleteFile(History history)
 * public void deleteFile(String dataPath, String picPath)
 * public void deleteDir(File file)
 * 
 * */

@Service
public class FileService {
	
	private static String folderPath = "public/";
	
	public String save(MultipartFile file, String path) {
//		String filePath = folderPath + getRandomStringService.getRandomString(6)+file.getOriginalFilename();
		if (!file.isEmpty()) {    
            try {
                BufferedOutputStream out = new BufferedOutputStream( new FileOutputStream(new File(path) ) );    
//                System.out.println(file.getName());  
                out.write(file.getBytes());    
                out.flush();    
                out.close();    
            } catch (FileNotFoundException e) {    
                e.printStackTrace();    
                return "上传失败," + e.getMessage();    
            } catch (IOException e) {    
                e.printStackTrace();    
                return "上传失败," + e.getMessage();    
            }
            return "上传成功";
    
        } else {    
            return "上传失败，因为文件是空的.";    
        }    
	}

	public void createFolder(String s) {
		File file=new File(folderPath+s);
		if(!file.exists()){//如果文件夹不存在
			file.mkdir();//创建文件夹
		}
		File file_upload=new File(folderPath+s);
		if(!file_upload.exists()){//如果文件夹不存在
			file_upload.mkdir();//创建文件夹
		}
		
	}
	
	public void deleteFolder(String email) {
		File file = new File(folderPath+email);
		File file_upload=new File(folderPath+email);
		deleteDir(file);
		deleteDir(file_upload);
    }
	
	public void deleteFile(String path) {
		if(path != null) {
			File file = new File(path);
			file.delete();
		}
	}

	
	public void deleteDir(File file){
        //判断是否为文件夹
        if(file.isDirectory()){
            //获取该文件夹下的子文件夹
            File[] files = file.listFiles();
            //循环子文件夹重复调用delete方法
            for (int i = 0; i < files.length; i++) {
                deleteDir(files[i]);
            }
        }
        //若为空文件夹或者文件删除，File类的删除方法
        file.delete();
    }

	
	
}
