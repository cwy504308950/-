import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

import javax.swing.*;

/*按钮事件的响应
1.实现接口
2.注册事件
3.实现响应
*/
public class Login extends JFrame implements ActionListener {
	static JTextField txtUser = new JTextField();  //输入框组件
    static JPasswordField txtPass = new JPasswordField();//密码框组件
   
    Login(){
    	this.setSize(250,125); 
    	this.setTitle("魔晶1.0");
    	this.setLocation(500, 300);
    //new组件
        JLabel labUer = new JLabel("用户名");
        //字体类型设置  labUer.setFont(new Font("宋体", Font.BOLD, 30));
        JLabel labPass = new JLabel("密码");   
        JButton btLogin = new JButton("登录");
        JButton btReg = new JButton("注册");
        JButton btCanel =  new JButton("取消");  
        
    //鼠标注册事件   三个按钮
      
        btLogin.addActionListener(this);
        btReg.addActionListener(this);
        btCanel.addActionListener(this);  
        
        //布置输入面板（输入框和密码框）
        JPanel panInput = new JPanel();
        panInput.setLayout(new GridLayout(2, 2));  //设置一个两行两列的面板
        
        panInput.add(labUer);
        panInput.add(txtUser);
        
        panInput.add(labPass);
        panInput.add(txtPass);
        
        //用FlowLayout 面板排版三个按钮
        JPanel panButton =  new JPanel();
        panButton.setLayout(new FlowLayout());
        
        panButton.add(btLogin);
        panButton.add(btReg);
        panButton.add(btCanel);
        
        //布置整体窗口
        this.setLayout(new BorderLayout());
        
        this.add(panInput,BorderLayout.CENTER);
        this.add(panButton,BorderLayout.SOUTH);
        
        //设置默认账号密码
        txtUser.setText("aaa");
         txtPass.setText("123");
    }
  
    public static void main(String args[]) {
       Login w = new Login(); 
					  /*     w.setSize(250,125); 
					   	w.setLocation(500, 300);
					   	//new组件
					       JLabel labUer = new JLabel("用户名");
					       //字体类型设置  labUer.setFont(new Font("宋体", Font.BOLD, 30));
					       JLabel labPass = new JLabel("密码");   
					       JButton btLogin = new JButton("登录");
					       JButton btReg = new JButton("注册");
					       JButton btCanel =  new JButton("取消");  
					       
					         //鼠标注册事件   三个按钮
					     
					       btLogin.addActionListener(w);
					       btReg.addActionListener(w);
					       btCanel.addActionListener(w);  
					       
					       //布置输入面板（输入框和密码框）
					       JPanel panInput = new JPanel();
					       panInput.setLayout(new GridLayout(2, 2));  //设置一个两行两列的面板
					       
					       panInput.add(labUer);
					       panInput.add(txtUser);
					       
					       panInput.add(labPass);
					       panInput.add(txtPass);
					       
					       //用FlowLayout 面板排版三个按钮
					       JPanel panButton =  new JPanel();
					       panButton.setLayout(new FlowLayout());
					       
					       panButton.add(btLogin);
					       panButton.add(btReg);
					       panButton.add(btCanel);
					       
					       //布置整体窗口
					       w.setLayout(new BorderLayout());
					       
					       w.add(panInput,BorderLayout.CENTER);
					       w.add(panButton,BorderLayout.SOUTH);
					       
					       //设置默认账号密码
					       txtUser.setText("aaa");
					        txtPass.setText("123");
					*/
        w.setVisible(true);
	}

   
     
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO 自动生成的方法存根
	
		if(e.getActionCommand().equals("登录")){
		  
		   try {
			    String user1 = txtUser.getText();
			    String pass = txtPass.getText();	
			    if(user1.equals("")||pass.equals("")) {
				   JOptionPane.showMessageDialog(this, "用户名或密码错误不能为空");
				   }
   
		 //发送给服务器验证
			   //定位 建立 操作
			   Socket s = new Socket("127.0.0.1",100);	       
			   OutputStream os = s.getOutputStream();
			   PrintWriter pw = new PrintWriter(os,true);
			   //封装用户输入的账号密码
			   pw.println(user1+"%"+pass);
			   
			   
	     //接受服务器的返回数据 
			   //定位 建立 操作
			   InputStream is = s.getInputStream();
			   InputStreamReader iss = new InputStreamReader(is);
			   BufferedReader br = new BufferedReader(iss);
			    
			   String  yorn = br.readLine();
			   
	     //显示主窗体
			   if(yorn.equals("ok")) {
				   //密码正确显示回话框
				   QQMain w =new QQMain();
				    w.setSocket(s,user1);//将端口连接传送给回话框代码继续使用
					w.setLocation(500, 150);	//设置窗口出现在屏幕中的位置
					w.setTitle(user1+"对话窗口");
					w.setVisible(true); 
					this.setVisible(false);  //登录界面消失
			   }else {
				   //密码不对弹窗提示
				   JOptionPane.showMessageDialog(this, "对不起，用户名或密码错误");
			   }			   		   
		   }catch(Exception e1) {}  
		}
		//待完成 ------Ok
		if(e.getActionCommand().equals("注册")) {  
			Register w = new Register();
			w.setLocation(500, 150);
			w.setVisible(true); 
			this.setVisible(false);
			
			System.out.println("用户注册了");
			}
		if(e.getActionCommand().equals("取消"))  {  
			this.setVisible(false);
			System.out.println("用户取消了");
			}	
	}  
}
