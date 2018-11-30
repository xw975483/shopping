import java.util.List;

import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.taotao.mapper.TbItemMapper;
import com.taotao.pojo.TbItem;
import com.taotao.pojo.TbItemExample;

public class PageHelpTest {
	
	@Test
	public void test1(){
		ApplicationContext app = new ClassPathXmlApplicationContext("classpath:spring/applicationContext-*.xml");
		TbItemMapper tbItemMapper = app.getBean(TbItemMapper.class);
		TbItemExample example = new TbItemExample();
		PageHelper.startPage(1, 10);
		List<TbItem> byExample = tbItemMapper.selectByExample(example);
		for (TbItem tbItem : byExample) {
			System.out.println(tbItem.getTitle());
		}
		PageInfo<TbItem> pageInfo = new PageInfo<>(byExample);
		int firstPage = pageInfo.getFirstPage();
		System.out.println(firstPage);
		int pageSize = pageInfo.getPageSize();
		System.out.println(pageSize);
		long total = pageInfo.getTotal();
		System.out.println(total);
		
	}

}
