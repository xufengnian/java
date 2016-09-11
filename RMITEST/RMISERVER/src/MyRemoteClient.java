//在服务器端，该源码可以没有，如果有，是为了在服务器端测试，地址为127.0.0.1
import java.rmi.*;
public class MyRemoteClient {
	public static void main(String[] args){
		new MyRemoteClient().go();
	}

	public void go() {
		// TODO Auto-generated method stub
		try{
			MyRemote service = (MyRemote) Naming.lookup("rmi://127.0.0.1/RemoteHello");//客户端从服务器端查询名为RemoteHello的服务，服务器端返回一个stub对象，让客户端自行操作
			String s = service.sayHello();//令s为调用服务器端的sayHello()方法的结果
			System.out.println(s);//输出s
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
