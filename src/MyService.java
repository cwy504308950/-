import java.io.*;
import java.net.Socket;
import java.sql.*;
import java.util.HashMap;
//��ΪҪ���ж���������������߳�ʹ�ó����жദ����ͬʱִ��
public class MyService extends Thread {
         private   Socket s ;    
         //����HashMap
         private HashMap<String , Socket> hm ;
         public void setHashMap(HashMap hm) {
        	 this.hm = hm;
         }
         public void setSocket(Socket s) {
			this.s = s ;
		}
	public void run() {
			// ����Connection����
			Connection con = null;
			Statement stmt = null;
			ResultSet rs  = null;
			// ����������
			String jdbc = "com.mysql.jdbc.Driver";
			// URLָ��Ҫ���ʵ����ݿ���mydata
			String url = "jdbc:mysql://127.0.0.1:3306/qqlogin?characterEncoding=UTF-8&useSSL=false&serverTimezone=GMT&nullNamePatternMatchesAll=true";
			// MySQL����ʱ���û���
			String user = "root";
			// MySQL����ʱ������
			String password = "123456";
		try {			
	  //�����û���Ϣ
		    //��λ ���� ����	
			InputStream is = s.getInputStream();
			InputStreamReader iss = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(iss);
			
			String uandp = br.readLine();
			
			//�����
			System.out.println(uandp);
			//����û���������
			String 	u = uandp.split("%")[0];
			 String p = uandp.split("%")[1];
			
			//�������ݸ��ͻ���
			   //��λ ���� ����
			    OutputStream  os = s.getOutputStream();
			    OutputStreamWriter oss = new OutputStreamWriter(os);
			    PrintWriter pw = new PrintWriter(oss,true); 
					    
	 //�����ݿ�����֤�û����       
		    //��������
				   Class.forName(jdbc);
				  System.out.println("�������ݿ��С���������");	  
		     //������ݿ�
				   con = DriverManager.getConnection(url,user,password);
				   System.out.println(" ʵ����Statement����...");				   
		     //��ѯ
				 stmt = con.createStatement();
				 String sql;
				 sql =  "SELECT * from user where username = '"+u+"' and password = '" +p+"' " ;
				  rs = stmt.executeQuery(sql);
				  System.out.println("���ݻ�ȡ���");
				  	    
	//����û����������ݿ�洢�����Ƿ�һ��
			  if(rs.next()) {
				  //������ȷ��Ϣ���ͻ���
					 pw.println("ok");	
					  
				 //�������û����� name
					 for(Socket so : hm.values()) {
						 //��λ
						 OutputStream tos = so.getOutputStream();
						 //����
						 OutputStreamWriter tosw = new OutputStreamWriter(tos);
					     PrintWriter tpw = new PrintWriter(tosw,true);
					     
					     tpw.println("add%"+u);
					 } 
					  
				 //�������˵����ַ��͸��Լ�
					 for(String str : hm.keySet()) {
						 pw.println("add%"+str);
					 }
					 
				//�����˵��û�����Socket ����HashMap
					 hm.put(u, s);
					  
			//���ϻ�ȡ�ͻ�����Ϣ
				    while(true) {
				    	//bug
				    	//��������Ϣ�ȿͻ�����Ϣ��ǰ��ֹ
				    	String message = br.readLine();
				    	System.out.println(message);
				    	//������˳���Ϣ�����û���HashMap���Ƴ�
				    	if(message.equals("{exit}"))
				    	{	hm.remove(u);
				    	//֪ͨ������ �û�����
					    	for(Socket ts : hm.values()) {
					    	OutputStream tos = ts.getOutputStream();
					    	OutputStreamWriter tosw = new OutputStreamWriter(tos);
					    	PrintWriter tpw = new PrintWriter(tosw,true);
					    	
					    	tpw.println("exit%"+u);
					      }
				    	}
				    	
		    //ת����Ϣ
				   //���յ�����
				    	String to = message.split("%")[0];
				    	String mess = message.split("%")[1];
				    	
				    	System.out.println("-ת�������е���Ϣ�����"+mess);
				   //��ȡĿ��Socket
				         Socket ss=	hm.get(to);
				   //��λ ���� ����
				    	OutputStream mo = ss.getOutputStream();
				    	OutputStreamWriter mow = new OutputStreamWriter(mo);
				    	PrintWriter mowp = new PrintWriter(mow,true);
				    	
				    	mowp.println("mess%"+mess);
				         }
			      }	 else { 
				  //���ʹ��󵽿ͻ���
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
