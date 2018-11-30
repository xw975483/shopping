package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.pojo.EUDataGridResult;
import com.common.pojo.TaotaoResult;
import com.common.utils.IDUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemDescMapper;
import com.taotao.mapper.TbItemMapper;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemExample;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.service.TbItemService;

@Service
public class TbItemServiceImpl implements TbItemService {
	
	@Autowired
	private TbItemMapper tbItemMapper;
	@Autowired
	private TbItemDescMapper descMapper;
	@Autowired
	private TbItemParamItemMapper itemParamItemMapper;

	public TbItem getOneTbItemById(Long id) {
		TbItem tbItem2 = tbItemMapper.selectByPrimaryKey(id);
		
		return tbItem2;
	}

	public EUDataGridResult getItemList(int page, int rows) {
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(page, rows);
		List<TbItem> list = tbItemMapper.selectByExample(example);
		System.err.println(list);
		PageInfo<TbItem> pageInfo = new PageInfo<>(list);
		long total = pageInfo.getTotal();
		EUDataGridResult result = new EUDataGridResult();
		result.setTotal(total);
		result.setRows(list);
		return result;
	}

	public TaotaoResult createItem(TbItem tbItem,String desc,String itemParam) throws Exception {
		long itemId = IDUtils.genItemId();
		tbItem.setId(itemId);
		tbItem.setStatus((byte)1);//状态
		tbItem.setCreated(new Date());
		tbItem.setUpdated(new Date());
		
		tbItemMapper.insert(tbItem);
		
		TaotaoResult result = addDesc(itemId, desc);
		if(result.getStatus() !=200){
			throw new Exception();
		}
		result = insertItemParam(itemId, itemParam);
		if(result.getStatus() !=200){
			throw new Exception();
		}
		return TaotaoResult.ok();
	}

	//添加商品描述的方法
	private TaotaoResult addDesc(Long itemId,String desc) {
		TbItemDesc tbItemDesc = new TbItemDesc();
		tbItemDesc.setItemId(itemId);
		tbItemDesc.setItemDesc(desc);
		tbItemDesc.setCreated(new Date());
		tbItemDesc.setUpdated(new Date());
		TaotaoResult taotaoResult = new TaotaoResult();
		descMapper.insert(tbItemDesc);
		return taotaoResult.ok();
	}
	
	//添加商品参数的方法
	public TaotaoResult insertItemParam(Long itemId,String itemParam){
		TbItemParamItem itemParamItem = new TbItemParamItem();
		itemParamItem.setItemId(itemId);
		itemParamItem.setParamData(itemParam);
		itemParamItem.setCreated(new Date());
		itemParamItem.setUpdated(new Date());
		itemParamItemMapper.insert(itemParamItem);
		return TaotaoResult.ok();
	}
}
