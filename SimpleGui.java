//��һ��java GUI ��������һ��300*300�İ�ť��������ʾclick me�������°�ť����ʾI have been clicked
import javax.swing.*;//����swing����������GUI����
import java.awt.event.*;//����awt���������¼��ļ�����ע��

public class SimpleGui implements ActionListener{//ʵ��ActionListener����ӿڣ�Ĭ�ϵ��¼������ӿڣ���ʾSimpleGui�Ǹ�ActionListener�����¼���Ҫ����
	JButton button=new JButton();//����ʵ������button
	
	public static void main (String[] args){
		SimpleGui gui = new SimpleGui();
		gui.go();
	}

	public void go() {
		JFrame frame = new JFrame();//����frame��button����������ܴ��ںͰ�ť��Jָ����java
		button.setText("click me");//����Button������д��click me
		// TODO Auto-generated method stub
		
		button.addActionListener(this);//����ťע�ᣬbutton��Ϊ�¼�Դ�����Է����¼�
		//���е�this���Ǻ��¼������ӿڵ�SimpleGui
		
		frame.getContentPane().add(button);//��button�ӵ�frame��pane�����
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�Ѵ��ڹرյ�ͬʱ��������
		frame.setSize(300, 300);//�趨frame��С
		frame.setVisible(true);//�趨frameΪ�ɼ�
		
	}
		
	

	@Override//��дActionListener�ӿڵ�actionPerformed����
	public void actionPerformed(ActionEvent e) {//��ťbutton����	ActionEvent��Ϊ�������ô˷��������¼���Ϣ����������
		button.setText("I have been clicked");
		// TODO Auto-generated method stub
		
	}

}
