//�ڷ������ˣ���Դ�����û�У�����У���Ϊ���ڷ������˲��ԣ���ַΪ127.0.0.1
import java.rmi.*;
public class MyRemoteClient {
	public static void main(String[] args){
		new MyRemoteClient().go();
	}

	public void go() {
		// TODO Auto-generated method stub
		try{
			MyRemote service = (MyRemote) Naming.lookup("rmi://127.0.0.1/RemoteHello");//�ͻ��˴ӷ������˲�ѯ��ΪRemoteHello�ķ��񣬷������˷���һ��stub�����ÿͻ������в���
			String s = service.sayHello();//��sΪ���÷������˵�sayHello()�����Ľ��
			System.out.println(s);//���s
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
