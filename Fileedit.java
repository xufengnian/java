//比较有意思的一个音乐节拍小程序，一共16个节拍，循环播放，用户可以设定每一节拍演奏的乐器（0个到n个均可），播放，停止以及节奏的加快和减慢
//Fileedit版添加serializlt按钮和restore按钮，考察对象序列化和反序列化，前者记录当前乐谱乐器选择情况，后者还原之前的乐器选择情况
import javax.sound.midi.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Fileedit {
	JPanel mainPanel;//主面板区域
	ArrayList<JCheckBox> checkboxList;//把checkbox（复选框）储存在ArrayList中
	Sequencer sequencer;//发音器
	Sequence sequence;//来一个单曲CD
	Track track;//乐谱
	JFrame theFrame;//主框架
	
	String[] instrumentNames = {"Bass Drum","Closed Hi-Hat","Open Hi-Hat","Acoustic Snare","Crash Cymbal","Head Clap","High Tom",
			"Hi Bongo","Maracas","Whistle","Low Conga","Cowbell","Vibraslap","Low-mid Tom","High Agogo","Open Hi Conga"};
	//设置采用的乐器名字数组
	int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};//设置与上文乐器名对应的MIDI编号数组
	
	public static void main(String[] args){
		new Fileedit().buildGUI();//绘制图形界面
	}

	public void buildGUI() {
		// TODO Auto-generated method stub
		theFrame = new JFrame("Cyber BeatBox");//设置框架windows标题栏文字
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//把窗口关闭的同时结束程序
		BorderLayout layout = new BorderLayout();//设置名称为Layout,类型为BorderLayout的布局管理器
		JPanel background = new JPanel(layout);//声明背景面板，采用BorderLayout的布局管理器
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));//设定面板上摆设组件离Frame上下左右的边界距离
		
		checkboxList = new ArrayList<JCheckBox>();//声明类型为JCheckBox的一组checkboxList
		Box buttonBox = new Box(BoxLayout.Y_AXIS);//声明一块 名为buttonBox的区域，放置乐器选择情况。该区域布局管理器为BoxLayout，且放置组件的顺序沿y轴，从上到下
		
		JButton start = new JButton("Start");//声明一个名为start的按钮，按下则开始播放乐谱信息
		start.addActionListener(new MyStartListener());//注册该按钮给监听器，监听器参数是MyStartListener类的一个实例
		buttonBox.add(start);//buttonBox区域添加start按钮
		
		JButton stop = new JButton("Stop");//声明一个名为start的按钮，按下则停止播放乐谱信息
		stop.addActionListener(new MyStopListener());//注册该按钮给监听器，监听器参数是MyStopListener类的一个实例
		buttonBox.add(stop);//buttonBox区域添加stop按钮
		
		JButton upTempo  = new JButton("Tempo Up");//声明一个名为Tempo Up的按钮，按下则节拍速度提升3%
		upTempo.addActionListener(new MyUpTempoListener());//注册该按钮给监听器，监听器参数是MyUpTempoListener类的一个实例
		buttonBox.add(upTempo);//buttonBox区域添加Tempo Up按钮
		
		JButton downTempo  = new JButton("Tempo Down");//声明一个名为Tempo Downt的按钮，按下则节拍速度降低3%
		downTempo.addActionListener(new MyDownTempoListener());//注册该按钮给监听器，监听器参数是MyDownTempoListener类的一个实例
		buttonBox.add(downTempo);//buttonBox区域添加Tempo Down按钮
		
		JButton serializelt = new JButton("serializelt");//声明一个名为serializelt的按钮，按下则记下当前选择的乐器情况，将其保存为Checkbox.ser文件
		serializelt.addActionListener(new MySendListener());//注册该按钮给监听器，监听器参数是MySendListener类的一个实例
		buttonBox.add(serializelt);//buttonBox区域添加serializelt按钮
		
		JButton restore = new JButton("restore");//声明一个名为restore的按钮，按下则跳出文件选择对话框，选择*.ser(序列化文件)并还原
		restore.addActionListener(new MyReadInListener());//注册该按钮给监听器，监听器参数是MyReadInListener类的一个实例
		buttonBox.add(restore);//buttonBox区域添加restore按钮
		
		Box nameBox = new Box(BoxLayout.Y_AXIS);//声明一块 名为nameBox的区域，放置乐器名称。该区域布局管理器为BoxLayout，且放置组件的顺序沿y轴，从上到下
		for(int i = 0;i < 16;i++){//依次将instrumentNames[]数组中的乐器名，设置为标签名，添加到nameBox区域
			nameBox.add(new Label(instrumentNames[i]));
		}
		
		background.add(BorderLayout.EAST,buttonBox);//buttonBox放在background面板BorderLayout的EAST区
		background.add(BorderLayout.WEST,nameBox);//buttonBox放在background面板BorderLayout的WEST区
		
		theFrame.getContentPane().add(background);//把background面板添加到主框架windows上
		
		GridLayout grid = new GridLayout(16,16);//声明一个16*16的网格布局管理器
		grid.setVgap(1);//设置网格管理器各个组件垂直间距为1
		grid.setHgap(2);//设置网格管理器各个组件水平间距为2
		mainPanel = new JPanel(grid);//将grid添加到mainPanel上
		background.add(BorderLayout.CENTER,mainPanel);////mainPanel放在background面板BorderLayout的CENTER区
		
		for(int i = 0;i < 256;i++){//生成256个checkBox复选框，添加到ArrayList和mainPanel的16*16的网格管理器，默认状态是未选中
			JCheckBox c = new JCheckBox();
			c.setSelected(false);
			checkboxList.add(c);
			mainPanel.add(c);
			}
		setUpMidi();//调用发音器的方法，初始化发音器
		
		theFrame.setBounds(50, 50, 300, 300);//源自JFrame的方法，设置theFrame组件的坐标（该坐标是以电脑屏幕左上角为原点的）以及面板的宽度和高度
		theFrame.pack();//自动调整theFrame的大小，以适合其子组件的首选大小和布局。
		theFrame.setVisible(true);//设置theFrame为可见
	}
	
	public void setUpMidi(){
		try{
			sequencer = MidiSystem.getSequencer();//获得1个发音器实例
			sequencer.open();			
			sequence  = new Sequence(Sequence.PPQ,4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(120);
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void buildTrackAndStart(){
		int[] trackList = null;//创建出16个元素的数组来存储某一项乐器的值，若该节应该要演奏，其值会是关键字的值，否则为0
		
		sequence.deleteTrack(track);
		track = sequence.createTrack();//重新建立track
		
		for (int i = 0;i < 16; i++){//依次读取每一行（乐器）的选择情况，即对每个乐器都执行1次
			trackList = new int[16];
			
			int key = instruments[i];//确定该行使用乐器的MIDI编号
			
			for (int j = 0;j < 16;j++) {//对每一拍执行一次
				JCheckBox jc = (JCheckBox) checkboxList.get(j+(16*i));//确定256个checkBox中，当前checkBox的编号
				if (jc.isSelected()){
					trackList[j] = key;//若该checkBox被选中，则将该行使用乐器的MIDI编号赋予trackList[16]数组
				}else{
					trackList[j] = 0;
				}				
			}
			makeTracks(trackList);//将每一行的trackList[16]作为makeTracks方法的参数
			track.add(makeEvent(176,1,127,0,16));//创建此乐器事件并添加到track上
		}
		track.add(makeEvent(192,9,1,0,15));//确保第16拍有事件，不然beatbox不会重复播放
		try{		
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);//循环播放当前乐谱的秘密
			sequencer.start();
			sequencer.setTempoInBPM(120);			
		}catch(Exception e){e.printStackTrace();}
	}
	public class MyStartListener implements ActionListener{//start按钮的监听器
		public void actionPerformed(ActionEvent a){
			buildTrackAndStart();//一旦按下start按钮，则buildTrackAndStart()方法开始执行
		}
	}
	
	public class MyStopListener implements ActionListener{//stop按钮的监听器
		public void actionPerformed(ActionEvent a){			
			sequencer.stop();//一旦按下stop按钮，则停止发声
		}
	}
	
	public class MyUpTempoListener implements ActionListener{//TempoUp按钮的监听器
		public void actionPerformed(ActionEvent a){
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor * 1.03));//一旦按下TempoUp按钮，则节拍加快3%
		}
	}
	
	public class MyDownTempoListener implements ActionListener{//TempoDown按钮的监听器
		public void actionPerformed(ActionEvent a){
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor * 0.97));//一旦按下TempoDown按钮，则节拍减慢3%
		}
	}
	
	public void makeTracks(int[] list){//
		for (int i =0;i < 16;i++){//每16节拍为一曲
			int key = list[i];
			if (key != 0){//在数组元素不为0，即有乐器MIDI编号时
				track.add(makeEvent(144,9,key,100,i));//在第i拍开始乐谱记录，频道为9，音符恰好取对应乐器MIDI编号，音量为100
				track.add(makeEvent(128,9,key,100,i+1));//在第i+1拍结束乐谱记录，频道为9，音符恰好取对应乐器MIDI编号，音量为100			
			}//由于音符恰好取对应乐器MIDI编号，因此不能选MIDI编号太小的乐器，且注意此时是频道9
			//探索了一下，不知正确与否？频道9，似乎有个特点：第三个参数同时决定音符和当前乐器的MIDI编号，
			//所以makeEvent(144,9,key,100,i)的第三个参数key，说明用第key个编号的MIDI乐器演奏第key个音符
			//又由于第5个参数决定了响起该音符的节拍数，因此，同一列的乐器若被选中,是一起演奏的
			//每来一个tracklist就开始演奏了，其实后面乐器慢一些只不过CPU速度太快，听不出来
		}
	}
	
	public class MySendListener implements ActionListener{//serializelt按钮的监听器
		public void actionPerformed(ActionEvent a){
			boolean[] checkboxState =new boolean[256];//新建容量为256的布尔型数组checkboxState用于存放256个checkbox的状态
			for (int i = 0;i < 256;i++){//根据checkboxList元素的状态，将其状态写入checkboxState[]数组
				JCheckBox check = (JCheckBox) checkboxList.get(i);//从checkboxList里逐一选出每个checkbox，还原其JCheckBox对象特性
				if(check.isSelected()){
					checkboxState[i] = true;
				}
			}
			try{
				FileOutputStream fileStream = new FileOutputStream(new File("Checkbox.ser"));//新建文件输出流，若该输出流不存在，则新建输出流并命名为Checkbox.ser，若存在Checkbox.ser的文件则直接覆盖
				ObjectOutputStream os = new ObjectOutputStream(fileStream);//新建对象输出流，并将其绑定在文件输出流上
				os.writeObject(checkboxState);//对象输出流里写入的对象是checkboxState				
			}catch(Exception ex){//补充：生成的Checkbox.ser文件位于该类的主文件夹下
				ex.printStackTrace();
			}
		}
	}
	
	public class MyReadInListener implements ActionListener{//restore按钮的监听器
		public void actionPerformed(ActionEvent a){
			boolean[] checkboxState = null;//先将checkboxState数组清空
			try{
				JFileChooser fileOpen = new JFileChooser();//新建文件选择模块
				fileOpen.showOpenDialog(theFrame);//将文件打开对话框显示在theFrame框架上
				FileInputStream fileIn = new FileInputStream(fileOpen.getSelectedFile());//新建文件输入流，输入源是上文文件打开对话框选择的文件
				ObjectInputStream is = new ObjectInputStream(fileIn);//对象输入流绑定文件输入流
				checkboxState = (boolean[]) is.readObject();//从对象输入流读取对象，由于读取的对象是boolean数组型，故只需在等号右边加上(boolean[])，强制转换一下即可					
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			for(int i = 0;i < 256;i++){//根据checkboxState的值还原每个checkbox的状态
				JCheckBox check = (JCheckBox) checkboxList.get(i);
				if(checkboxState[i]){
					check.setSelected(true);
				}else{
					check.setSelected(false);
				}
			}
			sequencer.stop();//一旦用户点下restore按钮，发音器停止当前曲谱播放
			buildTrackAndStart();//发音器播放还原后的曲谱
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
}
