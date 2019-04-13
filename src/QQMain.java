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


//�ͻ���Ҫ���ϵĽ��� ���������������ݹ�run()
public class QQMain extends JFrame implements ActionListener ,Runnable,WindowListener {
	//���� ��¼������׽��ֶ��� ��֤����һ���˿�
	private Socket s;
	private String name1;
	public void setSocket(Socket value,String name) {
		s = value; 
		name1 = name;
	//���������߳�
		//�����ڹ��캯���У���������ʱ ���캯�������У�����ʱSocket��û�д��ݹ������߳������� �п����Ҳ���s  �ʷ���SetSocket��
	    Thread t = new Thread(this);
        t.start();
	  }
   JTextField txtMass = new JTextField();
   JTextArea txtConten = new JTextArea();
   JComboBox cmbUser =  new JComboBox();
   
	QQMain() {	
	      this.setSize(300,400);
                
      //new���     
	      JButton btnSend =  new JButton("����");
	      //���ù�����
	      JScrollPane spConten =  new JScrollPane(txtConten);
	      
	   //ע���¼�
	      btnSend.addActionListener(this);
	      this.addWindowListener(this);
	      
	      //����С����
	      JPanel panSmall = new JPanel();
	      panSmall.setLayout(new GridLayout(1, 2));
	      panSmall.add(cmbUser);
	      panSmall.add(btnSend);
	        
	      //���ô󻭰�
	      JPanel panBig = new JPanel();
	      panBig.setLayout(new GridLayout(2, 1));
	      panBig.add(txtMass);
	      panBig.add(panSmall);
	            
	      //���ô���
	      this.setLayout(new BorderLayout());
	      
	      this.add(spConten,BorderLayout.CENTER);
	      this.add(panBig,BorderLayout.SOUTH );
	       
	     
	      //��ʾ��ȥ�������¼
	      try {
	    	  File f = new File("D:/java/LoginInterface/�����¼.txt");
	    	  
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
		if(e.getActionCommand().equals("����")) {
			
		txtConten.append(name1+":"+"\n"+txtMass.getText()+"\n");
		
	  //��txtMass�����ݴ��������¼�ļ�
		try {
			//��λ����λ��
			//File f = new File("D:/java/LoginInterface/�����¼.txt");
			//�����ܵ�
			//����д����ʽ��֮ǰ�ļ��е�����
			PrintWriter pw = new PrintWriter(new FileOutputStream("D:/java/LoginInterface/�����¼.txt",true));
		    //FileWriter fw = new FileWriter(f);
            // PrintWriter pw  = new PrintWriter(fw);	   
		    //�ܵ�����	
		    pw.printf(name1+":"+"\n"+txtMass.getText()+"\n");
		    pw.close();
		}catch(Exception e1) {}
			
	  //���͸������û���Ϣ
			try {
				OutputStream lo = s.getOutputStream();
				OutputStreamWriter low = new OutputStreamWriter(lo);
				 PrintWriter pw = new  PrintWriter(low,true);
				 
				 pw.println(cmbUser.getSelectedItem()+"%"+name1+"                         "+txtMass.getText());
			}catch(Exception e1) {e1.printStackTrace();}

		//������������
		    txtMass.setText("");	
		}
	}
	
	//�����߳�
	@Override
	public void run() {
		try {
			InputStream is = s.getInputStream();
			InputStreamReader isr = new InputStreamReader(is);
			BufferedReader br = new BufferedReader(isr);
	//�������ܷ�������Ϣ
			while(true) {
				//bug
				//��������Ϣ�ȿͻ�����Ϣ��ǰ��ֹ	
				String message = br.readLine();		
				System.out.println("��ȡ������������ϢΪ��"+message);	
				//�ָ����ܵ�����Ϣ�������ж����û����߻�������
				String type = message.split("%")[0];
				String mess = message.split("%")[1];
				//������
				System.out.println("--�յ�������Ϊ"+type);
				System.out.println("--�յ�������Ϊ"+mess);
				
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
	
	//���ڹرգ�
	@Override
	public void windowClosing(WindowEvent e) {
		//���ڹر������������exit ������֪ͨ��֪�����û�
		try {
			OutputStream os = s.getOutputStream();
			OutputStreamWriter osw = new OutputStreamWriter(os);
			PrintWriter pw = new PrintWriter(osw,true);
			//�˳�			
			pw.println("{exit}");
			System.exit(0);
		 System.out.println("����û����˳��ɹ��˳��ɹ�");
		}catch(Exception e1) {}	
	}
	@Override
	public void windowOpened(WindowEvent e) {
		// TODO �Զ����ɵķ������		
	}
	@Override
	public void windowClosed(WindowEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	@Override
	public void windowIconified(WindowEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	@Override
	public void windowDeiconified(WindowEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	@Override
	public void windowActivated(WindowEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	@Override
	public void windowDeactivated(WindowEvent e) {
		// TODO �Զ����ɵķ������
		
	}
	

}
