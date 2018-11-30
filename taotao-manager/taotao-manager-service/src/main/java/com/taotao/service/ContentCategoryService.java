package com.taotao.service;

import java.util.List;

import com.common.pojo.EUDataGridResult;
import com.common.pojo.EUTreeNode;
import com.common.pojo.TaotaoResult;

public interface ContentCategoryService {

	public List<EUTreeNode> getContentCategory(Long parantId);
	
	public TaotaoResult insertContentCategory(Long parentId,String text);
	
	public TaotaoResult deleteContenrNode(Long id);
	
	public TaotaoResult updateContentCategory(Long id,String name);
	
	
}
