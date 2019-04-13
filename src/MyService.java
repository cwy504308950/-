import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.HashMap;
//因为要运行多个服务器所有用线程使得程序中多处代码同时执行
public class MyService extends Thread {
         private   Socket s ;    
         //接受HashMap
         private HashMap<String , Socket> hm ;
         public void setHashMap(HashMap hm) {
        	 this.hm = hm;
         }
         public void setSocket(Socket s) {
			this.s = s ;
		}
	public void run() {
			// 声明Connection对象
			Connection con = null;
			Statement stmt = null;
			ResultSet rs  = null;
			// 驱动程序名
			String jdbc = "com.mysql.jdbc.Driver";
			// URL指向要访问的数据库名mydata
			String url = "jdbc:mysql://127.0.0.1:3306/qqlogin?characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT&nullNamePatternMatchesAll=true";
			// MySQL配置时的用户名
			String user = "root";
			// MySQL配置时的密码
			String password = "123456";
		try {			
	  //接受用户信息
		    //定位 建立 操作	
			InputStream is = s.getInputStream();
			InputStreamReader iss = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(iss);
			
			String uandp = br.readLine();
			
			//检验点
			System.out.println(uandp);
			//拆分用户名和密码
			String 	u = uandp.split("%")[0];
			 String p = uandp.split("%")[1];
			
			//返回数据给客户端
			   //定位 建立 操作
			    OutputStream  os = s.getOutputStream();
			    OutputStreamWriter oss = new OutputStreamWriter(os);
			    PrintWriter pw = new PrintWriter(oss,true); 
					    
	 //到数据库中验证用户身份       
		    //加载驱动
				   Class.forName(jdbc);
				  System.out.println("连接数据库中。。。。。");	  
		     //获得数据库
				   con = DriverManager.getConnection(url,user,password);
				   System.out.println(" 实例化Statement对象...");				   
		     //查询
				 stmt = con.createStatement();
				 String sql;
				 sql =  "SELECT * from user where username = '"+u+"' and password = '" +p+"' " ;
				  rs = stmt.executeQuery(sql);
				  System.out.println("数据获取完成");
				  	    
	//检查用户输入与数据库存储数据是否一致
			  if(rs.next()) {
				  //发送正确信息到客户端
					 pw.println("ok");	
					  
				 //向其他用户发送 name
					 for(Socket so : hm.values()) {
						 //定位
						 OutputStream tos = so.getOutputStream();
						 //建立
						 OutputStreamWriter tosw = new OutputStreamWriter(tos);
					     PrintWriter tpw = new PrintWriter(tosw,true);
					     
					     tpw.println("add%"+u);
					 } 
					  
				 //将其他人的名字发送给自己
					 for(String str : hm.keySet()) {
						 pw.println("add%"+str);
					 }
					 
				//将本人的用户名和Socket 存入HashMap
					 hm.put(u, s);
					  
			//不断获取客户端信息
				    while(true) {
				    	//bug
				    	//服务器信息比客户端信息提前终止
				    	String message = br.readLine();
				    	System.out.println(message);
				    	//如果是退出信息，将用户从HashMap中移除
				    	if(message.equals("{exit}"))
				    	{	hm.remove(u);
				    	//通知所有人 用户下线
					    	for(Socket ts : hm.values()) {
					    	OutputStream tos = ts.getOutputStream();
					    	OutputStreamWriter tosw = new OutputStreamWriter(tos);
					    	PrintWriter tpw = new PrintWriter(tosw,true);
					    	
					    	tpw.println("exit%"+u);
					      }
				    	}
				    	
		    //转发信息
				   //拆开收到内容
				    	String to = message.split("%")[0];
				    	String mess = message.split("%")[1];
				    	
				    	System.out.println("-转发过程中的信息情况："+mess);
				   //获取目标Socket
				         Socket ss=	hm.get(to);
				   //定位 建立 操作
				    	OutputStream mo = ss.getOutputStream();
				    	OutputStreamWriter mow = new OutputStreamWriter(mo);
				    	PrintWriter mowp = new PrintWriter(mow,true);
				    	
				    	mowp.println("mess%"+mess);
				         }
			      }	 else { 
				  //发送错误到客户端
				         pw.println("err");}	  		
		   }catch(Exception e) {e.printStackTrace();}
		
						finally {
							try {
								rs.close();
								stmt.close();
								con.close();
							}
							catch(Exception e) {}
						}
		
	}
}
