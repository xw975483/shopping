package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.pojo.EUDataGridResult;
import com.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;
import com.taotao.service.TbItemService;

@Controller
public class TbItemController {

	@Autowired
	private TbItemService tbItemService;
	
	@RequestMapping("/item/{id}")
	@ResponseBody
	public TbItem getOneById(@PathVariable("id") Long id){
		TbItem tbItem = tbItemService.getOneTbItemById(id);
		return tbItem;
	}
	
	@RequestMapping("/")
	public String getindex(){
		return "index";
	}
	
	@RequestMapping("{page}")
	public String showPage(@PathVariable("page") String page){
		return page;
	}
	
	@RequestMapping("/item/list")
	@ResponseBody
	public EUDataGridResult getItemList(int page,int rows){
		EUDataGridResult result = tbItemService.getItemList(page, rows);
		System.out.println(result);
		return result;
	}
	
	//使用pojo接收表单中的内容
	@RequestMapping(value="/item/save",method=RequestMethod.POST)
	@ResponseBody
	public TaotaoResult createItem(TbItem tbItem,String desc,String itemParams) throws Exception{
		System.out.println("-----1-----");
		System.out.println(tbItem);
		System.out.println(desc);
		System.out.println(itemParams);
		TaotaoResult item = tbItemService.createItem(tbItem,desc,itemParams);
		//System.out.println(item);
		return item;
	}
}
