package com.taotao.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.common.utils.FtpUtil;
import com.common.utils.IDUtils;
import com.taotao.service.PictureUpload;

@Service
public class PictureUploadImpl implements PictureUpload {
	
	@Value("${FTPHostAdd}")
	private String FTPHostAdd;
	
	@Value("${FTPPort}")
	private Integer FTPPort;
	
	@Value("${FTPname}")
	private String FTPname;

	@Value("${FTPpasswd}")
	private String FTPpasswd;
	
	@Value("${FTPBase_Path}")
	private String FTPBase_Path;
	
	@Value("${FTPBase_URL}")
	private String FTPBase_URL;
	

	public Map pictureUp(MultipartFile uploadFile) {
		HashMap map = new HashMap<>();
		
		try{
		
			String oldName = uploadFile.getOriginalFilename();
			
			InputStream stream = uploadFile.getInputStream();
			
			//uploadFile.transferTo(new File("D:\\qwe.png"));
			
			String newName = IDUtils.genImageName();
			newName = newName+oldName.substring(oldName.lastIndexOf("."));
			
			String filePath =  new DateTime().toString("/yyyy/MM/dd");
			
			
			/*FTPClient ftpClient = new FTPClient();
			//创建连接，默认端口是21
			ftpClient.connect(FTPHostAdd, FTPPort);
			//登录ftp服务器，使用用户名和密码
			ftpClient.login(FTPname, FTPpasswd);
			ftpClient.changeWorkingDirectory(FTPBase_Path);
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
			
			ftpClient.enterLocalPassiveMode();
			boolean flag = ftpClient.storeFile(newName, stream);
			
			ftpClient.logout();*/
			
			boolean flag = FtpUtil.uploadFile(FTPHostAdd, FTPPort, FTPname, FTPpasswd, FTPBase_Path, 
				filePath, newName, stream);
			
			/*System.out.println(FTPHostAdd);
			System.out.println(FTPPort);
			System.out.println(FTPname);
			System.out.println(FTPpasswd);
			System.out.println(FTPBase_Path);
			System.out.println(filePath);
			System.out.println(newName);
			System.out.println(flag);*/
			
			if(!flag){
				map.put("error", 1);
				map.put("message", "文件上传失败");
				return map;
			}
			map.put("error", 0);
			map.put("url", FTPBase_URL+filePath+"/"+newName);
			
			return map;
			
		}catch(Exception e){
			map.put("error", 1);
			map.put("message", "文件上传异常");
			return map;
		}
	}

}
