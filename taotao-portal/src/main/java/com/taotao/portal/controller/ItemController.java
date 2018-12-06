package com.taotao.portal.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.pojo.TbItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.service.ItemService;

/**
 * 商品详情页面展示
 * @author xw
 *
 */
@Controller
public class ItemController {
	@Autowired
	private ItemService itemService; 

	@RequestMapping("/item/{itemId}")
	public String shouItem(@PathVariable Long itemId,Model model){
		ItemInfo itemById = itemService.getItemById(itemId);
		model.addAttribute("item", itemById);
		return "item";
	}
	
	@RequestMapping(value="/item/desc/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemDesc(@PathVariable Long itemId){
		String result = itemService.getItemDescById(itemId);
		return result;
	}
	
	@RequestMapping(value="/item/param/{itemId}",produces=MediaType.TEXT_HTML_VALUE+";charset=utf-8")
	@ResponseBody
	public String getItemParam(@PathVariable Long itemId){
		System.out.println(itemId);
		String result = itemService.getItemParamById(itemId);
		return result;
	}
}
