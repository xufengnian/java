//创建远程接口
import java.rmi.Remote;
import java.rmi.RemoteException;

public interface MyRemote extends Remote{//继承自java.rmi.Remote，接口之间的继承用的也是extends
	public String sayHello() throws RemoteException;
	/*
	 此处有多个要点：
	 由于是远程调用，该接口中声明的所有方法都需要抛出RemoteException异常
	 接口中的方法自带public和abstract修饰符，可以省略，既然是抽象方法，那么接口中无需实现，继承该接口的类必须实现
	 方法的参数和返回值只能是8大primitive主数据类型或Serializable类型
	 */
}
