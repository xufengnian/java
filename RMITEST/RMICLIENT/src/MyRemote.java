//����Զ�̽ӿ�
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyRemote extends Remote{//�̳���java.rmi.Remote���ӿ�֮��ļ̳��õ�Ҳ��extends
	public String sayHello() throws RemoteException;
	/*
	 �˴��ж��Ҫ�㣺
	 ������Զ�̵��ã��ýӿ������������з�������Ҫ�׳�RemoteException�쳣
	 �ӿ��еķ����Դ�public��abstract���η�������ʡ�ԣ���Ȼ�ǳ��󷽷�����ô�ӿ�������ʵ�֣��̳иýӿڵ������ʵ��
	 �����Ĳ����ͷ���ֵֻ����8��primitive���������ͻ�Serializable����
	 */
}
