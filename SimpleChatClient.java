//非常简单的聊天程序，这是客户端部分，由于本部分没有服务器程序，因此没有回显
import javax.swing.*;
import java.net.*;
import java.io.*;
import java.awt.*;
import java.awt.event.*;

public class SimpleChatClient {
	JTextField outgoing;//文本框区域
	PrintWriter writer;//声明PrintWriter实例writer
	Socket sock;//声明Socket实例sock
	
	public void go(){
		JFrame frame = new JFrame("Ludicrously Simple Chat Client");//设置框架windows标题栏文字
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//把窗口关闭的同时结束程序
		JPanel mainPanel = new JPanel();//程序主面板
		outgoing = new JTextField(20);//outgoing文本框字长为20，行高1
		JButton sendButton = new JButton("Send");//声明Send按钮
		sendButton.addActionListener(new SendButtonListener());//将SendButton按钮注册给监听器，监听的参数是SendButtonListener类的一个实例
		mainPanel.add(outgoing);//主面板添加outgoing
		mainPanel.add(sendButton);//主面板添加sendButton
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);//mainPanel放在frame框架BorderLayout布局管理器的CENTER区
		setUpNetworking();//调用setUpNetworking方法
		frame.setSize(400, 500);//设置框架大小为400*500
		frame.setVisible(true);//设置程序可见
	}
	
	private void setUpNetworking(){
		try{
			sock = new Socket("127.0.0.1",5555);//（客户端对服务器建立Socket连接）绑定本机127.0.0.1:5555端口做测试，至于5555端口是否被占用？linux终端下输入netstat -an|grep '5555'即可
			writer = new PrintWriter(sock.getOutputStream());//建立链接到Socket的PrintWriter,PrintWriter是字符到二进制数据转换的桥梁，转换源是Socket的字符输出流sock.getOutputStream()
			System.out.println("networking established");//由于无服务器部分，因此无回显
		}catch(IOException ex){
			ex.printStackTrace();
		}
	}
	
	public class SendButtonListener implements ActionListener{//Send按钮的监听器
		public void actionPerformed(ActionEvent ev){
			try{
				writer.println(outgoing.getText());//发送文本框的文字到服务器
				writer.flush();//强制立刻写入
			}catch(Exception ex){
				ex.printStackTrace();				
			}
			outgoing.setText("");//发送完成后，文本框清空
			outgoing.requestFocus();//文本框继续获得焦点，表现为光标仍在文本框区域闪烁
		}		
	}
	public static void main(String[] args){
		new SimpleChatClient().go();
	}

}
