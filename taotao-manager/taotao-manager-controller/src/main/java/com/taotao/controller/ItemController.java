package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.taotao.service.ItemParamView;

@Controller
public class ItemController {

	@Autowired
	private ItemParamView itemParamView;
	
	@RequestMapping("/showitem/param/{itemId}")
	public String itemView(@PathVariable("itemId") Long itemId,Model model){
		String itemParams = itemParamView.getItemParams(itemId);
		model.addAttribute("itemParams", itemParams);
		return "item";
	}
}
