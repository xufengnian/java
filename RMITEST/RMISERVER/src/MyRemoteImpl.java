import java.rmi.*;
import java.rmi.server.*;
//������ʵ��MyRemote�ӿڣ����̳�UnicastRemoteObject��
public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {

	public MyRemoteImpl() throws RemoteException {		
		// TODO Auto-generated constructor stub
		//����Ĺ��캯����������Ŀ�ľ���Ϊ���׳��̳�UnicastRemoteObject���һ��С����֢��RemoteException�쳣
		//����ʲô������д
	}

	@Override
	public String sayHello() throws RemoteException {//�̳���MyRemote�ӿڣ���ô����ʵ�����еĳ��󷽷�������throws RemoteException�ⲿ����Ϊ�ڽӿ����������ˣ�������ʵ����ʡ��
		// TODO Auto-generated method stub
		return "Server says :'Hello Boy!'";
	}
	
	public static void main(String[] args){ 
		try{//��RMI registryע��
			MyRemote service = new MyRemoteImpl();//����MyRemote���͵����ã����ݵ��� MyRemoteImpl����
			Naming.rebind("RemoteHello", service);//������������ͻ��˿����ֲ�ѯregistry��������RMI registryע�ᣬRMI�Ὣstub����������stub����registry
		}catch(Exception ex){ex.printStackTrace();}
	}

}
