import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.net.Socket;

import javax.swing.*;

/*��ť�¼�����Ӧ
1.ʵ�ֽӿ�
2.ע���¼�
3.ʵ����Ӧ
*/
public class Login extends JFrame implements ActionListener {
	static JTextField txtUser = new JTextField();  //��������
    static JPasswordField txtPass = new JPasswordField();//��������
   
    Login(){
    	this.setSize(250,125); 
    	this.setTitle("ħ��1.0");
    	this.setLocation(500, 300);
    //new���
        JLabel labUer = new JLabel("�û���");
        //������������  labUer.setFont(new Font("����", Font.BOLD, 30));
        JLabel labPass = new JLabel("����");   
        JButton btLogin = new JButton("��¼");
        JButton btReg = new JButton("ע��");
        JButton btCanel =  new JButton("ȡ��");  
        
    //���ע���¼�   ������ť
      
        btLogin.addActionListener(this);
        btReg.addActionListener(this);
        btCanel.addActionListener(this);  
        
        //����������壨�����������
        JPanel panInput = new JPanel();
        panInput.setLayout(new GridLayout(2, 2));  //����һ���������е����
        
        panInput.add(labUer);
        panInput.add(txtUser);
        
        panInput.add(labPass);
        panInput.add(txtPass);
        
        //��FlowLayout ����Ű�������ť
        JPanel panButton =  new JPanel();
        panButton.setLayout(new FlowLayout());
        
        panButton.add(btLogin);
        panButton.add(btReg);
        panButton.add(btCanel);
        
        //�������崰��
        this.setLayout(new BorderLayout());
        
        this.add(panInput,BorderLayout.CENTER);
        this.add(panButton,BorderLayout.SOUTH);
        
        //����Ĭ���˺�����
        txtUser.setText("aaa");
         txtPass.setText("123");
    }
  
    public static void main(String args[]) {
       Login w = new Login(); 
					  /*     w.setSize(250,125); 
					   	w.setLocation(500, 300);
					   	//new���
					       JLabel labUer = new JLabel("�û���");
					       //������������  labUer.setFont(new Font("����", Font.BOLD, 30));
					       JLabel labPass = new JLabel("����");   
					       JButton btLogin = new JButton("��¼");
					       JButton btReg = new JButton("ע��");
					       JButton btCanel =  new JButton("ȡ��");  
					       
					         //���ע���¼�   ������ť
					     
					       btLogin.addActionListener(w);
					       btReg.addActionListener(w);
					       btCanel.addActionListener(w);  
					       
					       //����������壨�����������
					       JPanel panInput = new JPanel();
					       panInput.setLayout(new GridLayout(2, 2));  //����һ���������е����
					       
					       panInput.add(labUer);
					       panInput.add(txtUser);
					       
					       panInput.add(labPass);
					       panInput.add(txtPass);
					       
					       //��FlowLayout ����Ű�������ť
					       JPanel panButton =  new JPanel();
					       panButton.setLayout(new FlowLayout());
					       
					       panButton.add(btLogin);
					       panButton.add(btReg);
					       panButton.add(btCanel);
					       
					       //�������崰��
					       w.setLayout(new BorderLayout());
					       
					       w.add(panInput,BorderLayout.CENTER);
					       w.add(panButton,BorderLayout.SOUTH);
					       
					       //����Ĭ���˺�����
					       txtUser.setText("aaa");
					        txtPass.setText("123");
					*/
        w.setVisible(true);
	}

   
     
	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO �Զ����ɵķ������
	
		if(e.getActionCommand().equals("��¼")){
		  
		   try {
			    String user1 = txtUser.getText();
			    String pass = txtPass.getText();	
			    if(user1.equals("")||pass.equals("")) {
				   JOptionPane.showMessageDialog(this, "�û��������������Ϊ��");
				   }
   
		 //���͸���������֤
			   //��λ ���� ����
			   Socket s = new Socket("127.0.0.1",100);	       
			   OutputStream os = s.getOutputStream();
			   PrintWriter pw = new PrintWriter(os,true);
			   //��װ�û�������˺�����
			   pw.println(user1+"%"+pass);
			   
			   
	     //���ܷ������ķ������� 
			   //��λ ���� ����
			   InputStream is = s.getInputStream();
			   InputStreamReader iss = new InputStreamReader(is);
			   BufferedReader br = new BufferedReader(iss);
			    
			   String  yorn = br.readLine();
			   
	     //��ʾ������
			   if(yorn.equals("ok")) {
				   //������ȷ��ʾ�ػ���
				   QQMain w =new QQMain();
				    w.setSocket(s,user1);//���˿����Ӵ��͸��ػ���������ʹ��
					w.setLocation(500, 150);	//���ô��ڳ�������Ļ�е�λ��
					w.setTitle(user1+"�Ի�����");
					w.setVisible(true); 
					this.setVisible(false);  //��¼������ʧ
			   }else {
				   //���벻�Ե�����ʾ
				   JOptionPane.showMessageDialog(this, "�Բ����û������������");
			   }			   		   
		   }catch(Exception e1) {}  
		}
		//����� ------Ok
		if(e.getActionCommand().equals("ע��")) {  
			Register w = new Register();
			w.setLocation(500, 150);
			w.setVisible(true); 
			this.setVisible(false);
			
			System.out.println("�û�ע����");
			}
		if(e.getActionCommand().equals("ȡ��"))  {  
			this.setVisible(false);
			System.out.println("�û�ȡ����");
			}	
	}  
}
