package com.taotao.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.pojo.EUDataGridResult;
import com.common.pojo.EUTreeNode;
import com.common.pojo.TaotaoResult;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
import com.taotao.pojo.TbContentExample;
import com.taotao.service.ContentCategoryService;

@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {

	@Autowired
	private TbContentCategoryMapper categoryMapper;
	
	
	public List<EUTreeNode> getContentCategory(Long parantId) {
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parantId);
		List<TbContentCategory> list = categoryMapper.selectByExample(example);
		List<EUTreeNode> resultlist = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EUTreeNode node = new EUTreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			resultlist.add(node);
		}
		return resultlist;
	}

	public TaotaoResult insertContentCategory(Long parentId, String text) {
		TbContentCategory tbContentCategory = new TbContentCategory();
		tbContentCategory.setIsParent(false);
		tbContentCategory.setName(text);
		tbContentCategory.setParentId(parentId);
		tbContentCategory.setSortOrder(1);
		tbContentCategory.setStatus(1);
		tbContentCategory.setCreated(new Date());
		tbContentCategory.setUpdated(new Date());
		categoryMapper.insert(tbContentCategory);
		//判断父节点的isparent属性
		TbContentCategory parentC = categoryMapper.selectByPrimaryKey(parentId);
		if(!parentC.getIsParent()){
			parentC.setIsParent(true);
			categoryMapper.updateByPrimaryKey(parentC);
		}
		return TaotaoResult.ok(tbContentCategory);
	}

	public TaotaoResult deleteContenrNode(Long id) {
		
		TbContentCategory category = categoryMapper.selectByPrimaryKey(id);
		Long parentId = category.getParentId();
		System.out.println(parentId+"--"+id);
		categoryMapper.deleteByPrimaryKey(id);
		TbContentCategoryExample example = new TbContentCategoryExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andParentIdEqualTo(parentId);
		List<TbContentCategory> list = categoryMapper.selectByExample(example);
		if (list == null) {
			TbContentCategory parentContent = categoryMapper.selectByPrimaryKey(parentId);
			parentContent.setIsParent(false);
			categoryMapper.updateByPrimaryKey(parentContent);
		}
		return TaotaoResult.ok();
	}

	public TaotaoResult updateContentCategory(Long id, String name) {
		TbContentCategory category = categoryMapper.selectByPrimaryKey(id);
		category.setName(name);
		categoryMapper.updateByPrimaryKey(category);
		
		return TaotaoResult.ok();
	}

	
}
