package com.taotao.portal.service.impl;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.common.pojo.TaotaoResult;
import com.common.utils.HttpClientUtil;
import com.common.utils.JsonUtils;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemDesc;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.portal.pojo.ItemInfo;
import com.taotao.portal.pojo.ItemPojo;
import com.taotao.portal.service.ItemService;
/**
 * 商品基本信息
 * @author xw
 *
 */
@Service
public class ItemServiceImpl implements ItemService {
	@Value("${REST_BASE_URL}")
	private String REST_BASE_URL;
	@Value("${ITEM_INFO_URL}")
	private String ITEM_INFO_URL;
	@Value("${ITEM_DESC_URL}")
	private String ITEM_DESC_URL;
	@Value("${ITEM_PARAM_URL}")
	private String ITEM_PARAM_URL;
	@Override
	public ItemInfo getItemById(Long itemId) {
		try {
			String json = HttpClientUtil.doGet(REST_BASE_URL+ITEM_INFO_URL+itemId);
			if(!StringUtils.isBlank(json)){
				TaotaoResult taotaoResult = TaotaoResult.formatToPojo(json, ItemInfo.class);
				if(taotaoResult.getStatus() == 200){
					ItemInfo itemInfo = (ItemInfo) taotaoResult.getData();
					return itemInfo;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//调用服务取商品描述
	public String getItemDescById(Long itemId) {
		try {
			String json = HttpClientUtil.doGet(REST_BASE_URL+ITEM_DESC_URL+itemId);
			TaotaoResult result = TaotaoResult.formatToPojo(json, TbItemDesc.class);
			if(result.getStatus()== 200){
				TbItemDesc itemDesc = (TbItemDesc) result.getData();
				String stringResult = itemDesc.getItemDesc();
				return stringResult;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	//调用服务取商品参数
	public String getItemParamById(Long itemId) {
		try {
			String string = HttpClientUtil.doGet(REST_BASE_URL+ITEM_PARAM_URL+itemId);
			TaotaoResult taotaoResult = TaotaoResult.formatToPojo(string, TbItemParamItem.class);
			System.out.println(taotaoResult);
			if (taotaoResult.getStatus()==200) {
				TbItemParamItem itemParamItem = (TbItemParamItem) taotaoResult.getData();
				String paramData = itemParamItem.getParamData();
				
				List<Map> map1 = JsonUtils.jsonToList(paramData, Map.class);
				StringBuffer sb = new StringBuffer();
				sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"0\" class=\"Ptable\">\n");
				sb.append("      <tbody>\n");
				for(Map m1:map1){
					sb.append("		<tr>\n" );
					sb.append("			<th class=\"tdTitle\" colspan=\"2\">"+m1.get("group")+"</th>\n");
					sb.append("		</tr>\n" );
					List<Map> l1 = (List<Map>) m1.get("params");
					for (Map m2 : l1) {
						sb.append("		<tr>\n");
						sb.append("			<td class=\"tdTitle\">"+m2.get("k")+"</td>\n");
						sb.append("			<td>"+m2.get("v")+"</td>\n");
						sb.append("		</tr>\n");
					}
				}
				sb.append("		</tbody>\n");
				sb.append("</table>"  );
				System.out.println(sb.toString());
				return sb.toString();
			}
		} catch (Exception e) {
			// TODO: handle exception
		}
		return "";
	}

}
