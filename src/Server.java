import java.net.*;
import java.sql.*;
import java.util.HashMap;

import com.mysql.jdbc.Statement;

import java.io.*;
public class Server {

	public static void main(String[] args) {
		try { 
				//�������������˴�ŵļ���
				HashMap<String, Socket> hm = new HashMap<String,Socket>();
				
				//�����������ڶ˿�800������ , ��������
				ServerSocket ss = new ServerSocket(100);
				
				//�����ڶ˿ڽ���
				while(true) {	
					System.out.println("���������ڶ˿�800������......");
					Socket s = ss.accept();  //�õ��ڼ����˿ڵ��׽��ֶ���  
					
					MyService m = new MyService();	
					m.setSocket(s);//���׽��ֶ˿���Ϣ���뵽��������	
					m.setHashMap(hm);//��Hashmap�����������
					m.start();	
					/* Server ��Ҫ�����ڶ˿ڽ�������������
					 * MyServer ��Ҫ���ܿͻ��˵�����
					   *   �������඼Ҫִ���������߳�
					 * 	*/		
				}	
			    
			}catch(Exception e) {e.printStackTrace();}	   
	}
}
	    
	    
	      
	

