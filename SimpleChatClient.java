//�ǳ��򵥵�����������ǿͻ��˲��֣����ڱ�����û�з������������û�л���
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleChatClient {
	JTextField outgoing;//�ı�������
	PrintWriter writer;//����PrintWriterʵ��writer
	Socket sock;//����Socketʵ��sock
	
	public void go(){
		JFrame frame = new JFrame("Ludicrously Simple Chat Client");//���ÿ��windows����������
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�Ѵ��ڹرյ�ͬʱ��������
		JPanel mainPanel = new JPanel();//���������
		outgoing = new JTextField(20);//outgoing�ı����ֳ�Ϊ20���и�1
		JButton sendButton = new JButton("Send");//����Send��ť
		sendButton.addActionListener(new SendButtonListener());//��SendButton��ťע����������������Ĳ�����SendButtonListener���һ��ʵ��
		mainPanel.add(outgoing);//��������outgoing
		mainPanel.add(sendButton);//��������sendButton
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);//mainPanel����frame���BorderLayout���ֹ�������CENTER��
		setUpNetworking();//����setUpNetworking����
		frame.setSize(400, 500);//���ÿ�ܴ�СΪ400*500
		frame.setVisible(true);//���ó���ɼ�
	}
	
	private void setUpNetworking(){
		try{
			sock = new Socket("127.0.0.1",5555);//���ͻ��˶Է���������Socket���ӣ��󶨱���127.0.0.1:5555�˿������ԣ�����5555�˿��Ƿ�ռ�ã�linux�ն�������netstat -an|grep '5555'����
			writer = new PrintWriter(sock.getOutputStream());//�������ӵ�Socket��PrintWriter,PrintWriter���ַ�������������ת����������ת��Դ��Socket���ַ������sock.getOutputStream()
			System.out.println("networking established");//�����޷��������֣�����޻���
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	public class SendButtonListener implements ActionListener{//Send��ť�ļ�����
		public void actionPerformed(ActionEvent ev){
			try{
				writer.println(outgoing.getText());//�����ı�������ֵ�������
				writer.flush();//ǿ������д��
			}catch(Exception ex){
				ex.printStackTrace();				
			}
			outgoing.setText("");//������ɺ��ı������
			outgoing.requestFocus();//�ı��������ý��㣬����Ϊ��������ı���������˸
		}		
	}
	public static void main(String[] args){
		new SimpleChatClient().go();
	}

}
