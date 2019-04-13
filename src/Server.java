import java.net.*;
import java.sql.*;
import java.util.HashMap;

import com.mysql.jdbc.Statement;

import java.io.*;
public class Server {

	public static void main(String[] args) {
		try { 
				//声明所有在线人存放的集合
				HashMap<String, Socket> hm = new HashMap<String,Socket>();
				
				//建立服务器在端口800处监听 , 建立连接
				ServerSocket ss = new ServerSocket(100);
				
				//持续在端口接受
				while(true) {	
					System.out.println("服务器正在端口800处监听......");
					Socket s = ss.accept();  //得到在监听端口的套接字对象  
					
					MyService m = new MyService();	
					m.setSocket(s);//将套接字端口信息传入到服务器中	
					m.setHashMap(hm);//将Hashmap传入服务器中
					m.start();	
					/* Server 需要持续在端口接受新来的数据
					 * MyServer 需要接受客户端的请求
					   *   故两个类都要执行所有用线程
					 * 	*/		
				}	
			    
			}catch(Exception e) {e.printStackTrace();}	   
	}
}
	    
	    
	      
	

