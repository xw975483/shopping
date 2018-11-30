package com.taotao.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.pojo.EUDataGridResult;
import com.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;
import com.taotao.service.ItemParamService;

@Controller
@RequestMapping("/item/param")
public class ItemParamController {
	@Autowired
	private ItemParamService itemParamService;
	
	@RequestMapping("/query/itemcatid/{itemCatId}")
	@ResponseBody
	public TaotaoResult getItemParamByCid(@PathVariable("itemCatId") long itemCatId){
		TaotaoResult result = itemParamService.getItemParamById(itemCatId);
		return result;
	}
	
/*	/save/cid
	paramDate   字符串
	返回值 taotaoresult*/
	
	@RequestMapping("/save/{cid}")
	@ResponseBody
	public TaotaoResult insertTbItemParam(@PathVariable("cid") Long cid, String paramData){
		TbItemParam tbItemParam = new TbItemParam();
		tbItemParam.setItemCatId(cid);
		tbItemParam.setParamData(paramData);
		System.out.println(paramData);
		TaotaoResult result = itemParamService.insertIntoItemParam(tbItemParam);
		return result;
	}
	
	@RequestMapping(value="/list",method=RequestMethod.GET)
	@ResponseBody
	public EUDataGridResult getItemParamList(int page,int rows){
		System.out.println(1);
		EUDataGridResult result = itemParamService.getItemParam(page, rows);
		System.out.println(result);
		return result;
	}
}
