import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import javax.swing.*;

public class Register extends JFrame implements ActionListener  {
        JTextField txtUser = new JTextField();  //输入框组件
	    JPasswordField txtPass = new JPasswordField();//密码框组件
	    JPasswordField txtPass1 = new JPasswordField();//密码框组件 
	    JCheckBox check = new JCheckBox("同意");
	public Register() {
		// TODO 自动生成的构造函数存根
		this.setSize(270, 200);
		//new 组件
		JLabel labregister = new JLabel("注册");
		labregister.setFont(new java.awt.Font("宋体", 0, 20));
		JLabel labuser = new JLabel("用户名：");
		JLabel labpassword = new JLabel("密码：");
		JLabel labpassword1 = new JLabel("确认密码：");
		
	  
		JButton yes = new JButton("确定注册");
		JButton no = new JButton("取消");
		JButton gologin = new JButton("登录");
		
		//小布局
		JPanel panInput0 = new JPanel();
		panInput0.setLayout(new FlowLayout());
		panInput0.add(labregister);
	     
	     //小布局
		JPanel panInput = new JPanel();
		panInput.setLayout(new GridLayout(3, 2));
		
		panInput.add(labuser);
		panInput.add(txtUser);
		
		panInput.add(labpassword);
		panInput.add(txtPass);
		
		panInput.add(labpassword1);
		panInput.add(txtPass1);	
		
		//小布局
		JPanel panInput1 = new JPanel();
		panInput1.setLayout(new FlowLayout());
		panInput1.add(yes);
		panInput1.add(no);
		panInput1.add(gologin);
		
		JPanel panInput11 = new JPanel();
		panInput11.setLayout(new GridLayout(2, 1));
		panInput11.add(check);
		panInput11.add(panInput1);
		
		//整体布局
		this.setLayout(new BorderLayout());
		this.add(panInput0,BorderLayout.NORTH);
		this.add(panInput,BorderLayout.CENTER);
		this.add(panInput11,BorderLayout.SOUTH);
		
		//注册事件
		yes.addActionListener(this);
		no.addActionListener(this);
		gologin.addActionListener(this);	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		if(e.getActionCommand().equals("确定注册")) {
			String u = txtUser.getText();
			String p1 = txtPass.getText();
			String p2 = txtPass1.getText();
			
			System.out.println(u);
			System.out.println(p1);
			System.out.println(p2);
			if(p1.equals("")||p2.equals("")||u.equals("")) {
				JOptionPane.showMessageDialog(this,"账号和密码不能为空");
			}else {
				if(p1.equals(p2)) {
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
			
				System.out.println("开始入库操作。。。。");
					try {
				 //到数据库中验证用户身份       
					      //加载驱动
							   Class.forName(jdbc);
							  System.out.println("连接数据库中。。。。。");  
					     //获得数据库
							   con = DriverManager.getConnection(url,user,password);
							   System.out.println(" 实例化Statement对象...");							   
					     //查询
							 stmt = con.createStatement();
						     String	 sql =  "SELECT * from user where username = '"+u+"' " ;
						     rs = stmt.executeQuery(sql);
							  if(rs.next()) {
								  JOptionPane.showMessageDialog(this,"用户名重复");								 
							  }	 else {								 
									 String sql1 =  "insert into user(username,password) values('"+u+"','"+p1+"')" ;
									 stmt.executeUpdate(sql1);
									 System.out.println("数据获取完成");
									 JOptionPane.showMessageDialog(this,"注册成功");
							  }
					     }catch(Exception e1) {e1.printStackTrace();}
					
							finally {
								try {
									rs.close();
									stmt.close();
									con.close();
								}catch(Exception e1) {}
							  }		
							}else {
								JOptionPane.showMessageDialog(this,"两次密码不一样");
							        }	
	          }
		}
		if(e.getActionCommand().equals("注册")) {
			System.out.println("用户点击取消注册");
			Login w = new Login();
			w.setLocation(500, 150);	//设置窗口出现在屏幕中的位置
			w.setVisible(true); 
			this.setVisible(false);  //注册界面消失
		}
		if(e.getActionCommand().equals("登录")) {
			System.out.println("用户点击去登录");
			Login w = new Login();
			w.setLocation(500, 150);	//设置窗口出现在屏幕中的位置
			w.setVisible(true); 
			this.setVisible(false);  //注册界面消失
		}
	}
	

}
