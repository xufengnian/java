//第一个java GUI 程序，生成一个300*300的按钮，上面显示click me，当按下按钮后显示I have been clicked
import javax.swing.*;//导入swing包用于生成GUI界面
import java.awt.event.*;//导入awt包，用于事件的监听和注册

public class SimpleGui implements ActionListener{//实现ActionListener这个接口，默认的事件监听接口，表示SimpleGui是个ActionListener，有事件需要监听
	JButton button=new JButton();//创建实例变量button
	
	public static void main (String[] args){
		SimpleGui gui = new SimpleGui();
		gui.go();
	}

	public void go() {
		JFrame frame = new JFrame();//创建frame和button，即创建框架窗口和按钮，J指的是java
		button.setText("click me");//设置Button，上面写着click me
		// TODO Auto-generated method stub
		
		button.addActionListener(this);//即向按钮注册，button成为事件源，可以发出事件
		//其中的this就是含事件监听接口的SimpleGui
		
		frame.getContentPane().add(button);//把button加到frame的pane面板上
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//把窗口关闭的同时结束程序
		frame.setSize(300, 300);//设定frame大小
		frame.setVisible(true);//设定frame为可见
		
	}
		
	

	@Override//重写ActionListener接口的actionPerformed方法
	public void actionPerformed(ActionEvent e) {//按钮button会以	ActionEvent作为参数调用此方法，把事件信息带给监听者
		button.setText("I have been clicked");
		// TODO Auto-generated method stub
		
	}

}
