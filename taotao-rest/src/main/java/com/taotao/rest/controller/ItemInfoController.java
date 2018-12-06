package com.taotao.rest.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.pojo.TaotaoResult;
import com.taotao.rest.service.ItemInfoService;

@Controller
@RequestMapping("/item")
public class ItemInfoController {

	@Autowired
	private ItemInfoService infoService;
	
	@RequestMapping("/info/{itemId}")
	@ResponseBody
	public TaotaoResult getItemInfo(@PathVariable Long itemId) {
		TaotaoResult result = infoService.getItemInfo(itemId);
		return result;
	}
	
	@RequestMapping("/desc/{itemId}")
	@ResponseBody
	public TaotaoResult getItemDesc(@PathVariable Long itemId) {
		TaotaoResult itemDesc = infoService.getItemDesc(itemId);
		return itemDesc;
	}
	
	@RequestMapping("/param/{itemId}")
	@ResponseBody
	public TaotaoResult getItemParam(@PathVariable Long itemId) {
		TaotaoResult itemParams = infoService.getItemParams(itemId);
		return itemParams;
	}
}
