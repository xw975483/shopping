package com.taotao.search.service.impl;

import java.util.List;

import org.apache.solr.client.solrj.SolrServer;
import org.apache.solr.common.SolrInputDocument;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.pojo.TaotaoResult;
import com.common.utils.ExceptionUtil;
import com.taotao.search.mapper.ItemMapper;
import com.taotao.search.pojo.ItemPojo;
import com.taotao.search.service.ItemService;
@Service
public class ItemServiceImpl implements ItemService {
	
	@Autowired
	private ItemMapper itemMapper;
	@Autowired
	private SolrServer solrServer;

	@Override
	public TaotaoResult importAllItems() {
		try {
			List<ItemPojo> list = itemMapper.getItemList();
			for (ItemPojo itemPojo : list) {
				SolrInputDocument document = new SolrInputDocument();
				document.addField("id", itemPojo.getId());
				document.addField("item_title", itemPojo.getTitle());
				document.addField("item_sell_point", itemPojo.getSell_point());
				document.addField("item_price", itemPojo.getPrice());
				document.addField("item_image", itemPojo.getImage());
				document.addField("item_category_name", itemPojo.getCategory_name());
				document.addField("item_desc", itemPojo.getItem_des());
				
				solrServer.add(document);
			}
			
			solrServer.commit();
		} catch (Exception e) {
			e.printStackTrace();
			return TaotaoResult.build(500, ExceptionUtil.getStackTrace(e));
		}
		
		return TaotaoResult.ok();
	}
	
	
}
