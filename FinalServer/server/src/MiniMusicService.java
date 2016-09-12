import javax.sound.midi.*;
import java.io.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class MiniMusicService implements Service{
	MyDrawPanel myPanel;
	
	public JPanel getGuiPanel(){
		JPanel mainPanel = new JPanel();
		myPanel = new MyDrawPanel();
		JButton playItButton = new JButton("Play it");
		playItButton.addActionListener(new PlayItListener());
		mainPanel.add(myPanel);
		mainPanel.add(playItButton);
		return mainPanel;
	}
	public class PlayItListener implements ActionListener{
		public void actionPerformed(ActionEvent ev){
			try{
				Sequencer sequencer = MidiSystem.getSequencer();//获得1个发音器实例
				sequencer.open();
				sequencer.addControllerEventListener(myPanel, new int[] {127});//向Sequencer注册事件，注册的方法取用MyDrawPanel实例m1和代表想要监听的事件的int数组,本例中我们只需要127事件
				Sequence seq = new Sequence(Sequence.PPQ,4);
				Track track = seq.createTrack();			
				
				for (int i = 0;i < 100;i += 4){//600/4=150，即绘制150个矩形，响150个音符
					int rNum = (int) (Math.random()*127);//随机生成0~127的数字作为Message的第三个参数，音符参数
					track.add(makeEvent(144,1,rNum,100,i));//144，频道1，第r个音符，音量100，第i个节拍开始记录
					track.add(makeEvent(176,1,127,0,i));//176，频道1，触发编号为127的事件用于被监听器捕捉，音量0，第i个节拍开始触发（和144一个节拍就是为了在开始记录时出发事件被捕）
					track.add(makeEvent(128,1,rNum,100,i+2));//128，频道1，第r个音符，音量100，第i+2个节拍结束记录（改成i似乎无影响？）
				}
				sequencer.setSequence(seq);//Track记录好了曲谱信息,可以交给Sequencer播放了
				sequencer.setTempoInBPM(120);//设置速度，以每分钟的拍数为单位。本题就是0.5s生成一个矩形
				sequencer.start();//Sequencer开始播放
			}catch(Exception ex){
				ex.printStackTrace();}		
		}
	}
	public MidiEvent makeEvent(int cmd, int chl, int one, int two, int tick) {//makeEvent方法相当于构造函数setMessage和MidiEvent合体
		// TODO Auto-generated method stub
		MidiEvent event = null;//乐谱信息初始化
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(cmd, chl, one,two);//此处设置Message信息，相当于告诉乐谱这一个或这一段节拍，我需要干什么（开始演奏，换乐器，结束等等）
			event = new MidiEvent(a,tick);//设置一下这段信息从哪个节拍开始
			}catch(Exception e){ }
		return event;//返回这一段乐谱记录的信息
	}

	class MyDrawPanel extends JPanel implements ControllerEventListener{
		boolean msg = false;//捕获事件的标志，只有捕获事件才会为真（所谓的捕获事件就是生成了1个节拍）
		public void controlChange(ShortMessage event){
			msg = true;//捕获事件时设置为真
			repaint();//调用重绘程序
		}
		public Dimension getPreferredSize(){
			return new Dimension(300,300);
		}
		
		public void paintComponent(Graphics g){//抽象类JPanel中必须被重写的抽象方法，用于生成随机矩形
			if(msg) {//由于也有其他东西会引发重绘，所以要判断是否由ControllerEvent引发
				//Graphics2D g2 = (Graphics2D) g;
				
				int r = (int)(Math.random()*255);
				int gr = (int)(Math.random()*255);
				int b = (int)(Math.random()*255);
				//以RGB颜色空间作为颜色标准，3者均取0~255间的随机数
				g.setColor(new Color(r,gr,b));
				
				int ht = (int)((Math.random()*120)+10);
				int width = (int)((Math.random()*120)+10);
				
				int x = (int)((Math.random()*40)+10);
				int y = (int)((Math.random()*40)+10);
				//设置生成矩形的坐标（Frame左上角为原点）,以及矩形的宽和高，这几个数字均是随机数
				g.fillRect(x, y, width, ht);
				msg = false;
			}
		}

	}
}
