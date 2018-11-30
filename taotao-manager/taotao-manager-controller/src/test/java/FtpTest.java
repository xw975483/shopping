import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.junit.Test;

import com.common.utils.FtpUtil;

public class FtpTest {
	@Test
	public void testFtp1() throws IOException{
		//创建一个ftpclient对象
		FTPClient ftpClient = new FTPClient();
		//创建连接，默认端口是21
		ftpClient.connect("192.168.43.100", 21);
		//登录ftp服务器，使用用户名和密码
		ftpClient.login("ftpuser", "xw19960929");
		//读取文件
		FileInputStream fileIn = new FileInputStream(new File("D:\\w.jpg"));
		//设置上传路径
		ftpClient.changeWorkingDirectory("/home/ftpuser/www/images");
		//修改上传格式
		ftpClient.setFileType(FTP.BINARY_FILE_TYPE);
		
		ftpClient.enterLocalPassiveMode();
		//上传文件，第一个参数是服务器端文档的名称，第二个参数是inputstream
		ftpClient.storeFile("02.jpg", fileIn);
		//关闭连接
		ftpClient.logout();
	}
	
	@Test
	public void test2() throws IOException{
		FileInputStream fileIn = new FileInputStream(new File("D:\\w.jpg"));
		
		boolean b = FtpUtil.uploadFile("192.168.43.100", 21, "ftpuser", "xw19960929",
				"/home/ftpuser/www/images", "/2018/11/15", "hee3332211.jpg", fileIn);
		
		System.out.println(b);
		
	}
}
