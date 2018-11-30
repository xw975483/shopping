package com.taotao.service;

import com.common.pojo.EUDataGridResult;
import com.common.pojo.TaotaoResult;
import com.taotao.pojo.TbItemParam;

public interface ItemParamService {

	public TaotaoResult getItemParamById(Long cid);
	
	public TaotaoResult insertIntoItemParam(TbItemParam tbItemParam);
	
	public EUDataGridResult getItemParam(int page,int rows);
}
