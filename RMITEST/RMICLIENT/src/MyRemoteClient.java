//�ͻ���Դ�룬��ַӦ��Ϊ������ʵ�ʵ�ַ������Ϊ192.168.223.128
import java.rmi.*;
public class MyRemoteClient {
	public static void main(String[] args){
		new MyRemoteClient().go();
	}

	public void go() {
		// TODO Auto-generated method stub
		try{
			MyRemote service = (MyRemote) Naming.lookup("rmi://192.168.223.128/RemoteHello");//�ͻ��˴ӷ������˲�ѯ��ΪRemoteHello�ķ��񣬷������˷���һ��stub�����ÿͻ������в���
			String s = service.sayHello();//��sΪ���÷������˵�sayHello()�����Ľ��
			System.out.println(s);//���s
		}catch(Exception ex){
			ex.printStackTrace();
		}
	}
}
