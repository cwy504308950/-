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
        JTextField txtUser = new JTextField();  //��������
	    JPasswordField txtPass = new JPasswordField();//��������
	    JPasswordField txtPass1 = new JPasswordField();//�������� 
	    JCheckBox check = new JCheckBox("ͬ��");
	public Register() {
		// TODO �Զ����ɵĹ��캯�����
		this.setSize(270, 200);
		//new ���
		JLabel labregister = new JLabel("ע��");
		labregister.setFont(new java.awt.Font("����", 0, 20));
		JLabel labuser = new JLabel("�û�����");
		JLabel labpassword = new JLabel("���룺");
		JLabel labpassword1 = new JLabel("ȷ�����룺");
		
	  
		JButton yes = new JButton("ȷ��ע��");
		JButton no = new JButton("ȡ��");
		JButton gologin = new JButton("��¼");
		
		//С����
		JPanel panInput0 = new JPanel();
		panInput0.setLayout(new FlowLayout());
		panInput0.add(labregister);
	     
	     //С����
		JPanel panInput = new JPanel();
		panInput.setLayout(new GridLayout(3, 2));
		
		panInput.add(labuser);
		panInput.add(txtUser);
		
		panInput.add(labpassword);
		panInput.add(txtPass);
		
		panInput.add(labpassword1);
		panInput.add(txtPass1);	
		
		//С����
		JPanel panInput1 = new JPanel();
		panInput1.setLayout(new FlowLayout());
		panInput1.add(yes);
		panInput1.add(no);
		panInput1.add(gologin);
		
		JPanel panInput11 = new JPanel();
		panInput11.setLayout(new GridLayout(2, 1));
		panInput11.add(check);
		panInput11.add(panInput1);
		
		//���岼��
		this.setLayout(new BorderLayout());
		this.add(panInput0,BorderLayout.NORTH);
		this.add(panInput,BorderLayout.CENTER);
		this.add(panInput11,BorderLayout.SOUTH);
		
		//ע���¼�
		yes.addActionListener(this);
		no.addActionListener(this);
		gologin.addActionListener(this);	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
	
		if(e.getActionCommand().equals("ȷ��ע��")) {
			String u = txtUser.getText();
			String p1 = txtPass.getText();
			String p2 = txtPass1.getText();
			
			System.out.println(u);
			System.out.println(p1);
			System.out.println(p2);
			if(p1.equals("")||p2.equals("")||u.equals("")) {
				JOptionPane.showMessageDialog(this,"�˺ź����벻��Ϊ��");
			}else {
				if(p1.equals(p2)) {
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
			
				System.out.println("��ʼ��������������");
					try {
				 //�����ݿ�����֤�û����       
					      //��������
							   Class.forName(jdbc);
							  System.out.println("�������ݿ��С���������");  
					     //������ݿ�
							   con = DriverManager.getConnection(url,user,password);
							   System.out.println(" ʵ����Statement����...");							   
					     //��ѯ
							 stmt = con.createStatement();
						     String	 sql =  "SELECT * from user where username = '"+u+"' " ;
						     rs = stmt.executeQuery(sql);
							  if(rs.next()) {
								  JOptionPane.showMessageDialog(this,"�û����ظ�");								 
							  }	 else {								 
									 String sql1 =  "insert into user(username,password) values('"+u+"','"+p1+"')" ;
									 stmt.executeUpdate(sql1);
									 System.out.println("���ݻ�ȡ���");
									 JOptionPane.showMessageDialog(this,"ע��ɹ�");
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
								JOptionPane.showMessageDialog(this,"�������벻һ��");
							        }	
	          }
		}
		if(e.getActionCommand().equals("ע��")) {
			System.out.println("�û����ȡ��ע��");
			Login w = new Login();
			w.setLocation(500, 150);	//���ô��ڳ�������Ļ�е�λ��
			w.setVisible(true); 
			this.setVisible(false);  //ע�������ʧ
		}
		if(e.getActionCommand().equals("��¼")) {
			System.out.println("�û����ȥ��¼");
			Login w = new Login();
			w.setLocation(500, 150);	//���ô��ڳ�������Ļ�е�λ��
			w.setVisible(true); 
			this.setVisible(false);  //ע�������ʧ
		}
	}
	

}
