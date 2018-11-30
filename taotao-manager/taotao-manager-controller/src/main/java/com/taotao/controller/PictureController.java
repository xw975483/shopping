package com.taotao.controller;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.common.utils.JsonUtils;
import com.taotao.service.PictureUpload;

@Controller
public class PictureController {

	@Autowired
	private PictureUpload pictureupload;
	
	@RequestMapping("/pic/upload")
	@ResponseBody
	public String pictureUpload(MultipartFile uploadFile){
		
		Map map = pictureupload.pictureUp(uploadFile);
		String json = JsonUtils.objectToJson(map);
		return json;
	}
}
