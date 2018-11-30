package com.taotao.service.impl;

import java.util.List;
import java.util.Map;

import javax.sound.midi.MidiChannel;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.common.utils.JsonUtils;
import com.taotao.mapper.TbItemParamItemMapper;
import com.taotao.pojo.TbItemParamItem;
import com.taotao.pojo.TbItemParamItemExample;
import com.taotao.pojo.TbItemParamItemExample.Criteria;
import com.taotao.service.ItemParamView;

@Service
public class ItemParamViewImpl implements ItemParamView {

	@Autowired
	private TbItemParamItemMapper itemMapper;
	
	public String getItemParams(Long itemId) {
		TbItemParamItemExample itemExample = new TbItemParamItemExample();
		Criteria criteria = itemExample.createCriteria();
		criteria.andItemIdEqualTo(itemId);
		List<TbItemParamItem> list = itemMapper.selectByExampleWithBLOBs(itemExample);
		//System.out.println(list);
		if(list == null || list.size() == 0){
			return "";
		}
		TbItemParamItem paramItem = list.get(0);
		String paramData = paramItem.getParamData();
		System.out.println(paramData);
		List<Map> map1 = JsonUtils.jsonToList(paramData, Map.class);
		StringBuffer sb = new StringBuffer();
		sb.append("<table cellpadding=\"0\" cellspacing=\"1\" width=\"100%\" border=\"1\" class=\"Ptable\">\n");
		sb.append("		<tbody>\n");
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
		return sb.toString();
	}

}
