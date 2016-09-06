//�ǳ��򵥵�����������ǿͻ��˲��֣����ڱ�����û�з������������û�л���
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;


public class SimpleChatClient {
	JTextField outgoing;//�ͻ��˷�����Ϣ���ı�������
	PrintWriter writer;//����PrintWriterʵ��writer
	Socket sock;//����Socketʵ��sock
	JTextArea incoming;//���շ���������Ϣ���ı�������
	BufferedReader reader;//���������������Ϣ��ȡЧ��
	
	public void go(){
		JFrame frame = new JFrame("Ludicrously Simple Chat Client");//���ÿ��windows����������
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�Ѵ��ڹرյ�ͬʱ��������
		JPanel mainPanel = new JPanel();//���������
		outgoing = new JTextField(20);//outgoing�ı����ֳ�Ϊ20���и�1
		incoming = new JTextArea(15,50);//����incoming�ı����ֳ�15���и�Ϊ50
		incoming.setLineWrap(true);//�����ı����Ļ��в��ԡ��������Ϊ true�����еĳ��ȴ���������Ŀ��ʱ�������С��������Ϊ false����ʼ�ղ����С�
		incoming.setWrapStyleWord(true);//��ȡ���з�ʽ������ı���Ҫ���У����������Ϊ true�����еĳ��ȴ���������Ŀ��ʱ�����ڵ��ʱ߽磨���հף������С��������Ϊ false�������ַ��߽紦���С�
		incoming.setEditable(false);//����imcoming����Ϊ���ɱ༭״̬
		JScrollPane qScroller = new JScrollPane(incoming);//����������ӵ�incoming����
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//����qScroller������Ϊ��ֱ������ã�ˮƽ���򲻿���
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		JButton sendButton = new JButton("Send");//����Send��ť
		sendButton.addActionListener(new SendButtonListener());//��SendButton��ťע����������������Ĳ�����SendButtonListener���һ��ʵ��
		mainPanel.add(outgoing);//��������outgoing
		mainPanel.add(qScroller);
		mainPanel.add(sendButton);//��������sendButton
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);//mainPanel����frame���BorderLayout���ֹ�������CENTER��
		setUpNetworking();//����setUpNetworking����
		
		Thread readerThread = new Thread(new IncomingReader());//�½�readerThread���̣�������תΪ����̬
		readerThread.start();//����readerThread����
		
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
	public class IncomingReader implements Runnable{//IncomingReader�࣬�̳�Runnable�ӿ�
		public void run(){//�̳�Runnable�ӿڱ���ʵ�ֵĳ��󷽷�run()�������̸߳���ʲô
			String message;//�½��ַ��ͱ���message
			try{
				while ((message = reader.readLine()) != null){//message��reader���������ж�ȡ
					System.out.println("read " + message);//ÿ��һ����ʾread��ʲômessage
					incoming.append(message + "\n");//incoming�ı������������message
				}
			}catch(Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	public static void main(String[] args){
		new SimpleChatClient().go();
	}

}
