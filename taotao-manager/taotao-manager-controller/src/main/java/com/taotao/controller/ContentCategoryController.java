package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.pojo.EUTreeNode;
import com.common.pojo.TaotaoResult;
import com.taotao.service.ContentCategoryService;

@Controller
@RequestMapping("/content/category")
public class ContentCategoryController {

	@Autowired
	private ContentCategoryService categoryService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> gEuTreeNodes(@RequestParam(value="id" ,defaultValue="0") Long parantId){
		List<EUTreeNode> list = categoryService.getContentCategory(parantId);
		return list;
	}
	
	@RequestMapping("/create")
	@ResponseBody
	public TaotaoResult insertContent(Long parentId,String name){
		TaotaoResult category = categoryService.insertContentCategory(parentId, name);
		return category;
	}
	
	@RequestMapping("/delete")
	@ResponseBody
	public TaotaoResult deleteContentCategory(Long id){
		TaotaoResult result = categoryService.deleteContenrNode(id);
		return result;
	}
	
	@RequestMapping("/update")
	@ResponseBody
	public  TaotaoResult updateContentCategory(Long id,String name){
		TaotaoResult result = categoryService.updateContentCategory(id, name);
		
		return result;
	}
}
