//简单聊天程序的服务器端代码，实际测试时需要同SimpleChatClient同时启动，还是未完善版本，感觉差了很多功能
import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleChatServer {
	ArrayList  clientOutputStreams;
	
	public class ClientHandler implements Runnable{//声明类ClientHandler，继承Runnable接口
		BufferedReader reader;//声明缓冲区reader，更高效读取客户端消息
		Socket sock;//声明Socket实例sock
		
		public ClientHandler(Socket clientSocket){//声明构造函数，参数是Socket类型的clientSocket
			try{
				sock = clientSocket;//将clientSocket参数直接赋值给实例变量sock
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());//将源自sock的二进制输入流作为输入源，将其转换成字符数据
				reader = new BufferedReader(isReader);//将上一步转换的字符添加到缓冲区，统一处理提高效率
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		public void run(){//覆盖Runnable接口中必须覆盖的抽象方法run()
			String message;
			try{
				while ((message = reader.readLine()) != null){//message取缓冲区每一行的值
					System.out.println("(Server)read " + message);//每读一行显示服务器read了什么message
					tellEveryone(message);//调用tellEveryone方法传递message
				}
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
	}
	
	public static void main(String[] args){
		new SimpleChatServer().go();
	}
	
	public void go(){
		clientOutputStreams = new ArrayList();//将clientOutputStreams引用泛型对象
		try{
			ServerSocket serverSock = new ServerSocket(5555);//让服务器应用程序开始监听来自5555端口的客户端请求
			
			while(true){
				Socket clientSocket = serverSock.accept();//调用ServerSocket类的accept方法，等待用户Socket连接时闲置着，此方法返回一个Socket(不同于5555端口，用于和客户端5555端口联系)，可以让服务器等待其他客户端连接
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());//建立连接到客户端clientSocket的PrintWriter，可将来自客户端的字符数据转换为二进制数据
				clientOutputStreams.add(writer);//将转换完成的writer添加到clientOutputStreams
				
				Thread t = new Thread(new ClientHandler(clientSocket));//新建clientHandler线程，将其设置为就绪态
				t.start();//启动该线程
				System.out.println("got a connection");//启动线程时，打印一句话
			}		
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void tellEveryone(String message){
		Iterator it = clientOutputStreams.iterator();//声明一个迭代器对象it，用于遍历ArrayList中所有clientOutputStreams对象
		while(it.hasNext()){//遍历迭代器
			try{
				PrintWriter writer = (PrintWriter) it.next();//建立下一个链接到客户端的Socket
				writer.println(message);//送出数据
				writer.flush();//强制送出数据
			}catch(Exception ex){
				ex.printStackTrace();
			}		
	}
		
	}

}
