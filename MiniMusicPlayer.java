//MIDI音乐盒pro版，特点：自动生成150个大小颜色不同的矩形，且每生成1个矩形，播放一个随机音符
import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class MiniMusicPlayer {
	static JFrame f = new JFrame("My First Music Video!");//新建程序主框架，且标题栏部分显示"My First Music Video!"
	static MyDrawPanel m1;//生成1个主面板，查阅API文件得知：MyDrawPanel继承自JPanel,JPanel又继承了 java.awt.Container;
	
	public static void main (String[] args){
		MiniMusicPlayer mini = new MiniMusicPlayer();
		mini.go();
	}
	
	public void setUpGui() {
		// TODO Auto-generated method stub
		m1 = new MyDrawPanel();
		f.setContentPane(m1);//源自JFrame的方法，public void setContentPane(Container contentPane)设置 contentPane 属性。此方法由构造方法调用。	
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//把窗口关闭的同时结束程序
		f.setBounds(30, 30, 300, 300);//源自JFrame的方法，设置frame组件的坐标（该坐标是以电脑屏幕左上角为原点的）以及面板的宽度和高度
		f.setVisible(true);//设置frame为可见
	}
	public void go() {//主方法
		// TODO Auto-generated method stub
		setUpGui();//先生成程序的frame窗口
		try {//Sequencer部分说明详见MiniMusicCmdLine.java
			Sequencer sequencer = MidiSystem.getSequencer();//获得1个发音器实例
			sequencer.open();
			sequencer.addControllerEventListener(m1, new int[] {127});//向Sequencer注册事件，注册的方法取用MyDrawPanel实例m1和代表想要监听的事件的int数组,本例中我们只需要127事件
			Sequence seq = new Sequence(Sequence.PPQ,4);
			Track track = seq.createTrack();
			/*
			 解释一下Sequencer的addControllerEventListener方法
			 int[] addControllerEventListener(ControllerEventListener listener,int[] controllers)
                                 
			 注册一个控件事件侦听器，以便在 sequencer 处理所请求的一种或多种类型的控制更改事件时接收通知。类型由 controllers 参数指定，该参数应包含一个 MIDI 控件号的数组。
			 （每个编号应该是 0 到 127（包括）之间的一个数。请参见 MIDI 1.0 规范中与各种类型控件对应的编号。）
			 参数：
			listener - 要添加到已注册侦听器列表中的控件事件侦听器
			controllers - 为其请求更改通知的 MIDI 控件号
			返回：
			其更改将报告给指定侦听器的所有 MIDI 控件号
			
			回到本例的sequencer.addControllerEventListener(m1, new int[] {127});其实是向m1注册了编号为127的自定义ControllerEvent(176),一旦m1中发生127事件，马上发现
			 */
			
			int r = 0;//音符初始值设置为0
			for (int i = 0;i < 600;i += 4){//600/4=150，即绘制150个矩形，响150个音符
				r = (int) (Math.random()*127);//随机生成0~127的数字作为Message的第三个参数，音符参数
				track.add(makeEvent(144,1,r,100,i));//144，频道1，第r个音符，音量100，第i个节拍开始记录
				track.add(makeEvent(176,1,127,0,i));//176，频道1，触发编号为127的事件用于被监听器捕捉，音量0，第i个节拍开始触发（和144一个节拍就是为了在开始记录时出发事件被捕）
				track.add(makeEvent(128,1,r,100,i+2));//128，频道1，第r个音符，音量100，第i+2个节拍结束记录（改成i似乎无影响？）
			}
			sequencer.setSequence(seq);//Track记录好了曲谱信息,可以交给Sequencer播放了
			sequencer.setTempoInBPM(120);//设置速度，以每分钟的拍数为单位。本题就是0.5s生成一个矩形
			sequencer.start();//Sequencer开始播放
		}catch(Exception ex){
			ex.printStackTrace();
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
/*
 简单解释一下本例的逻辑关系
 先绘制GUI界面
 获得一个发音器
 记录曲目节拍信息
 每记录一个节拍信息就触发一个编号为127的事件
 该事件一旦被触发就调动JPanel类的repaint方法开始重新绘制矩形，而绘制矩形的要求则根据paintComponent方法
 简单说：先生成节拍，生成节拍的同时触发事件,被捕捉之后开始绘制符合要求的矩形，知道节拍不在生成为止
 */
