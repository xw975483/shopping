package com.taotao.service;

import com.common.pojo.EUDataGridResult;
import com.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface TbContentService {
	
	public EUDataGridResult getContentCategoryList(int page,int rows,Long categoryId);
	
	public TaotaoResult saveContent(TbContent tbContent);
}
