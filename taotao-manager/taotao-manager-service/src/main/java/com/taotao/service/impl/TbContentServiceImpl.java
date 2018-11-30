package com.taotao.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.common.pojo.EUDataGridResult;
import com.common.pojo.TaotaoResult;
import com.common.utils.HttpClientUtil;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbContentMapper;
import com.taotao.pojo.TbContent;
import com.taotao.pojo.TbContentExample;
import com.taotao.pojo.TbContentExample.Criteria;
import com.taotao.service.TbContentService;
@Service
public class TbContentServiceImpl implements TbContentService {
	
	@Autowired
	private TbContentMapper contentMapper;
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${REST_CONTENT_URL}")
	private String REST_CONTENT_URL;

	public EUDataGridResult getContentCategoryList(int page, int rows, Long categoryId) {
		TbContentExample example = new TbContentExample();
		Criteria createCriteria = example.createCriteria();
		createCriteria.andCategoryIdEqualTo(categoryId);
		PageHelper.startPage(page, rows);
		List<TbContent> list = contentMapper.selectByExample(example);
		
		PageInfo<TbContent> info = new PageInfo<>(list);
		EUDataGridResult result = new EUDataGridResult();
		result.setTotal(info.getTotal());
		result.setRows(list);
		return result;
	}

	public TaotaoResult saveContent(TbContent tbContent) {
		tbContent.setCreated(new Date());
		tbContent.setUpdated(new Date());
		contentMapper.insert(tbContent);
		
		//这里添加缓存的同步逻辑，需要调用rest工程的服务
		try{
			HttpClientUtil.doGet(REST_BASE_URL+REST_CONTENT_URL+tbContent.getCategoryId());
		}catch (Exception e) {
			e.printStackTrace();
		}
		
		return TaotaoResult.ok();
	}

	
}
