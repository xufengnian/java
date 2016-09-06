//���������ķ������˴��룬ʵ�ʲ���ʱ��ҪͬSimpleChatClientͬʱ����������δ���ư汾���о����˺ܶ๦��
import java.io.*;
import java.net.*;
import java.util.*;

public class SimpleChatServer {
	ArrayList  clientOutputStreams;
	
	public class ClientHandler implements Runnable{//������ClientHandler���̳�Runnable�ӿ�
		BufferedReader reader;//����������reader������Ч��ȡ�ͻ�����Ϣ
		Socket sock;//����Socketʵ��sock
		
		public ClientHandler(Socket clientSocket){//�������캯����������Socket���͵�clientSocket
			try{
				sock = clientSocket;//��clientSocket����ֱ�Ӹ�ֵ��ʵ������sock
				InputStreamReader isReader = new InputStreamReader(sock.getInputStream());//��Դ��sock�Ķ�������������Ϊ����Դ������ת�����ַ�����
				reader = new BufferedReader(isReader);//����һ��ת�����ַ���ӵ���������ͳһ�������Ч��
			}catch(Exception ex){
				ex.printStackTrace();
			}
		}
		
		public void run(){//����Runnable�ӿ��б��븲�ǵĳ��󷽷�run()
			String message;
			try{
				while ((message = reader.readLine()) != null){//messageȡ������ÿһ�е�ֵ
					System.out.println("(Server)read " + message);//ÿ��һ����ʾ������read��ʲômessage
					tellEveryone(message);//����tellEveryone��������message
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
		clientOutputStreams = new ArrayList();//��clientOutputStreams���÷��Ͷ���
		try{
			ServerSocket serverSock = new ServerSocket(5555);//�÷�����Ӧ�ó���ʼ��������5555�˿ڵĿͻ�������
			
			while(true){
				Socket clientSocket = serverSock.accept();//����ServerSocket���accept�������ȴ��û�Socket����ʱ�����ţ��˷�������һ��Socket(��ͬ��5555�˿ڣ����ںͿͻ���5555�˿���ϵ)�������÷������ȴ������ͻ�������
				PrintWriter writer = new PrintWriter(clientSocket.getOutputStream());//�������ӵ��ͻ���clientSocket��PrintWriter���ɽ����Կͻ��˵��ַ�����ת��Ϊ����������
				clientOutputStreams.add(writer);//��ת����ɵ�writer��ӵ�clientOutputStreams
				
				Thread t = new Thread(new ClientHandler(clientSocket));//�½�clientHandler�̣߳���������Ϊ����̬
				t.start();//�������߳�
				System.out.println("got a connection");//�����߳�ʱ����ӡһ�仰
			}		
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
	
	public void tellEveryone(String message){
		Iterator it = clientOutputStreams.iterator();//����һ������������it�����ڱ���ArrayList������clientOutputStreams����
		while(it.hasNext()){//����������
			try{
				PrintWriter writer = (PrintWriter) it.next();//������һ�����ӵ��ͻ��˵�Socket
				writer.println(message);//�ͳ�����
				writer.flush();//ǿ���ͳ�����
			}catch(Exception ex){
				ex.printStackTrace();
			}		
	}
		
	}

}
