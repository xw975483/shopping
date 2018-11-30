package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.pojo.EUTreeNode;
import com.taotao.mapper.TbItemCatMapper;
import com.taotao.pojo.TbItemCat;
import com.taotao.pojo.TbItemCatExample;
import com.taotao.pojo.TbItemCatExample.Criteria;
import com.taotao.service.TbItemCatService;

@Service
public class TbItemCatServiceImpl implements TbItemCatService {
	
	@Autowired
	private TbItemCatMapper tbItemCatMapper;
	

	public List<EUTreeNode> getItemCatList(Long id) {
		
		TbItemCatExample example = new TbItemCatExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(id);
		List<TbItemCat> list = tbItemCatMapper.selectByExample(example);
		
		List<EUTreeNode> result = new ArrayList<>();
		for (TbItemCat ll : list) {
			EUTreeNode treeNode = new EUTreeNode();
			treeNode.setId(ll.getId());
			treeNode.setState(ll.getIsParent()?"closed":"open");
			treeNode.setText(ll.getName());
			result.add(treeNode);
		}
		return result;
	}

}
