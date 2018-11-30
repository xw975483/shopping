package com.taotao.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.common.pojo.EUTreeNode;
import com.taotao.service.TbItemCatService;

@Controller
@RequestMapping("/item/cat")
public class TbItemCatController {

	@Autowired
	private TbItemCatService tbItemCatService;
	
	@RequestMapping("/list")
	@ResponseBody
	public List<EUTreeNode> getCatList(@RequestParam(value="id",defaultValue="0") Long id){
		List<EUTreeNode> list = tbItemCatService.getItemCatList(id);
		return list;
	}
}
