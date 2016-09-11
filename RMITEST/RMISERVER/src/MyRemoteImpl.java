import java.rmi.*;
import java.rmi.server.*;
//定义类实现MyRemote接口，并继承UnicastRemoteObject类
public class MyRemoteImpl extends UnicastRemoteObject implements MyRemote {

	public MyRemoteImpl() throws RemoteException {		
		// TODO Auto-generated constructor stub
		//该类的构造函数，声明的目的就是为了抛出继承UnicastRemoteObject类的一个小后遗症，RemoteException异常
		//里面什么都不用写
	}

	@Override
	public String sayHello() throws RemoteException {//继承了MyRemote接口，那么必须实现其中的抽象方法，至于throws RemoteException这部分因为在接口中声明过了，所以其实可以省略
		// TODO Auto-generated method stub
		return "Server says :'Hello Boy!'";
	}
	
	public static void main(String[] args){ 
		try{//向RMI registry注册
			MyRemote service = new MyRemoteImpl();//声明MyRemote类型的引用，操纵的是 MyRemoteImpl对象
			Naming.rebind("RemoteHello", service);//帮服务命名（客户端靠名字查询registry），并向RMI registry注册，RMI会将stub做交换并把stub加入registry
		}catch(Exception ex){ex.printStackTrace();}
	}

}
