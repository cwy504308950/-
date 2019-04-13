import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.io.*;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.*;
import javax.xml.crypto.Data;


//客户端要不断的接受 服务器发送来数据故run()
public class QQMain extends JFrame implements ActionListener ,Runnable,WindowListener {
	//接受 登录界面的套接字对象 保证都在一个端口
	private Socket s;
	private String name1;
	public void setSocket(Socket value,String name) {
		s = value; 
		name1 = name;
	//启动接受线程
		//若放在构造函数中，创建对象时 构造函数就运行，而这时Socket还没有传递过来，线程启动后 有可能找不到s  故放在SetSocket中
	    Thread t = new Thread(this);
        t.start();
	  }
   JTextField txtMass = new JTextField();
   JTextArea txtConten = new JTextArea();
   JComboBox cmbUser =  new JComboBox();
   
	QQMain() {	
	      this.setSize(300,400);
                
      //new组件     
	      JButton btnSend =  new JButton("发送");
	      //设置滚动条
	      JScrollPane spConten =  new JScrollPane(txtConten);
	      
	   //注册事件
	      btnSend.addActionListener(this);
	      this.addWindowListener(this);
	      
	      //布置小画板
	      JPanel panSmall = new JPanel();
	      panSmall.setLayout(new GridLayout(1, 2));
	      panSmall.add(cmbUser);
	      panSmall.add(btnSend);
	        
	      //布置大画板
	      JPanel panBig = new JPanel();
	      panBig.setLayout(new GridLayout(2, 1));
	      panBig.add(txtMass);
	      panBig.add(panSmall);
	            
	      //布置窗体
	      this.setLayout(new BorderLayout());
	      
	      this.add(spConten,BorderLayout.CENTER);
	      this.add(panBig,BorderLayout.SOUTH );
	       
	     
	      //显示过去的聊天记录
	      try {
	    	  File f = new File("D:/java/LoginInterface/聊天记录.txt");
	    	  
	    	  FileReader fw = new FileReader(f);
	    	  BufferedReader bf = new BufferedReader(fw);
	    	  while(bf.ready()) {
	    		  txtConten.append(bf.readLine()+"\n");		
	    	  }
	      }catch(Exception e) {}
	}
	@Override
	public void actionPerformed(ActionEvent e) {
	  //txtMass ---->txtConten
		if(e.getActionCommand().equals("发送")) {
			
		txtConten.append(name1+":"+"\n"+txtMass.getText()+"\n");
		
	  //将txtMass的内容存入聊天记录文件
		try {
			//定位数据位置
			//File f = new File("D:/java/LoginInterface/聊天记录.txt");
			//建立管道
			//这样写不格式化之前文件中的内容
			PrintWriter pw = new PrintWriter(new FileOutputStream("D:/java/LoginInterface/聊天记录.txt",true));
		    //FileWriter fw = new FileWriter(f);
            // PrintWriter pw  = new PrintWriter(fw);	   
		    //管道操作	
		    pw.printf(name1+":"+"\n"+txtMass.getText()+"\n");
		    pw.close();
		}catch(Exception e1) {}
			
	  //发送给在线用户信息
			try {
				OutputStream lo = s.getOutputStream();
				OutputStreamWriter low = new OutputStreamWriter(lo);
				 PrintWriter pw = new  PrintWriter(low,true);
				 
				 pw.println(cmbUser.getSelectedItem()+"%"+name1+"                         "+txtMass.getText());
			}catch(Exception e1) {e1.printStackTrace();}

		//清空输入框内容
		    txtMass.setText("");	
		}
	}
	
	//接受线程
	@Override
	public void run() {
		try {
			InputStream is = s.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
	//持续接受服务器信息
			while(true) {
				//bug
				//服务器信息比客户端信息提前终止	
				String message = br.readLine();		
				System.out.println("获取到服务器的信息为："+message);	
				//分隔接受到的信息，用来判断是用户上线还是下线
				String type = message.split("%")[0];
				String mess = message.split("%")[1];
				//测试码
				System.out.println("--收到的类型为"+type);
				System.out.println("--收到的内容为"+mess);
				
				if(type.equals("add")) {
					cmbUser.addItem(mess);
				}
				
				if(type.equals("exit")) {
					cmbUser.removeItem(mess);
				}
				
				if(type.equals("mess")) {
					txtConten.append(mess+"\n");
				}
					
			}	 
		} catch (IOException e) {}
		
	}	
	
	//窗口关闭！
	@Override
	public void windowClosing(WindowEvent e) {
		//窗口关闭向服务器传递exit 将下线通知告知其他用户
		try {
			OutputStream os = s.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			PrintWriter pw = new PrintWriter(osw,true);
			//退出			
			pw.println("{exit}");
			System.exit(0);
		 System.out.println("这个用户名退出成功退出成功");
		}catch(Exception e1) {}	
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO 自动生成的方法存根		
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO 自动生成的方法存根
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO 自动生成的方法存根
		
	}
	

}
