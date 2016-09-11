//客户端源码，地址应该为服务器实际地址，本例为192.168.223.128
import java.rmi.*;
public class MyRemoteClient {
	public static void main(String[] args){
		new MyRemoteClient().go();
	}

	public void go() {
		// TODO Auto-generated method stub
		try{
			MyRemote service = (MyRemote) Naming.lookup("rmi://192.168.223.128/RemoteHello");//客户端从服务器端查询名为RemoteHello的服务，服务器端返回一个stub对象，让客户端自行操作
			String s = service.sayHello();//令s为调用服务器端的sayHello()方法的结果
			System.out.println(s);//输出s
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
