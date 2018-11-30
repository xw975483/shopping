package com.taotao.service;

import com.common.pojo.EUDataGridResult;
import com.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItem;

public interface TbItemService {
	
	public TbItem getOneTbItemById(Long id);

	public EUDataGridResult getItemList(int page,int rows);
	
	public TaotaoResult createItem(TbItem tbItem,String desc,String itemParam) throws Exception;
}
