package com.taotao.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.rest.pojo.CatNode;
import com.taotao.rest.pojo.CatResult;
import com.taotao.rest.service.ItemCatService;

@Service
public class ItemCatServiceImpl implements ItemCatService {

	@Autowired
	private TbItemCatMapper itemCatMapper;
	
	public CatResult getCatResult() {
		CatResult catResult = new CatResult();
		catResult.setData(getCatList(0));
		return catResult;
	}
	
	private List<?> getCatList(long parentid){
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentid);
		//根据id值返回集合数据
		List<TbItemCat> atList = itemCatMapper.selectByExample(example);
		List list = new ArrayList<>();
		int count = 0;
		for (TbItemCat tbItemCat : atList) {
			if(tbItemCat.getIsParent()){
				CatNode node = new CatNode();
				if(tbItemCat.getParentId() == 0){
					node.setName("<a href='/products/"+tbItemCat.getId()+".html'>"+tbItemCat.getName()+"</a>");
				}else{
					node.setName(tbItemCat.getName());
				}
				node.setUrl("/products/"+tbItemCat.getId()+".html");
				
				node.setItem(getCatList(tbItemCat.getId()));
				list.add(node);	
				count++;
				if(parentid == 0 &&count>=14){
					 break;
				}
				 
			}else{
				list.add("/products/"+tbItemCat.getId()+".html|"+tbItemCat.getName());
			}
		}
		return list;
	}
}
