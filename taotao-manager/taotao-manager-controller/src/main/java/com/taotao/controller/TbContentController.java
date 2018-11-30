package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.pojo.EUDataGridResult;
import com.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;
import com.taotao.service.TbContentService;

@Controller
@RequestMapping("/content")
public class TbContentController {

	@Autowired
	private TbContentService contentService;
	
	@RequestMapping("/query/list")
	@ResponseBody
	public EUDataGridResult getContentList(int page, int rows, Long categoryId){
		EUDataGridResult result = contentService.getContentCategoryList(page, rows, categoryId);
		return result;
	}
	
	@RequestMapping("/save")
	@ResponseBody
	public TaotaoResult saveContent(TbContent content){
		TaotaoResult result = contentService.saveContent(content);
		return result; 
		
	}
}
