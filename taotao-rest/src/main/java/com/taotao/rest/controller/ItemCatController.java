package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.utils.JsonUtils;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;

@Controller
public class ItemCatController {

	@Autowired
	private ItemCatService itemCatService;
	
	@RequestMapping(value="/itemcat/list",produces="text/json;charset=utf-8")
	@ResponseBody
	public String getItemCatList(String callback){
		CatResult result = itemCatService.getCatResult();
		String json = JsonUtils.objectToJson(result);
		String re = callback+"("+json+");";
		return re;
	}
}
