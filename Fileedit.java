//�Ƚ�����˼��һ�����ֽ���С����һ��16�����ģ�ѭ�����ţ��û������趨ÿһ���������������0����n�����ɣ������ţ�ֹͣ�Լ�����ļӿ�ͼ���
//Fileedit�����serializlt��ť��restore��ť������������л��ͷ����л���ǰ�߼�¼��ǰ��������ѡ����������߻�ԭ֮ǰ������ѡ�����
import javax.sound.midi.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Fileedit {
	JPanel mainPanel;//���������
	ArrayList<JCheckBox> checkboxList;//��checkbox����ѡ�򣩴�����ArrayList��
	Sequencer sequencer;//������
	Sequence sequence;//��һ������CD
	Track track;//����
	JFrame theFrame;//�����
	
	String[] instrumentNames = {"Bass Drum","Closed Hi-Hat","Open Hi-Hat","Acoustic Snare","Crash Cymbal","Head Clap","High Tom",
			"Hi Bongo","Maracas","Whistle","Low Conga","Cowbell","Vibraslap","Low-mid Tom","High Agogo","Open Hi Conga"};
	//���ò��õ�������������
	int[] instruments = {35,42,46,38,49,39,50,60,70,72,64,56,58,47,67,63};//������������������Ӧ��MIDI�������
	
	public static void main(String[] args){
		new Fileedit().buildGUI();//����ͼ�ν���
	}

	public void buildGUI() {
		// TODO Auto-generated method stub
		theFrame = new JFrame("Cyber BeatBox");//���ÿ��windows����������
		theFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�Ѵ��ڹرյ�ͬʱ��������
		BorderLayout layout = new BorderLayout();//��������ΪLayout,����ΪBorderLayout�Ĳ��ֹ�����
		JPanel background = new JPanel(layout);//����������壬����BorderLayout�Ĳ��ֹ�����
		background.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));//�趨����ϰ��������Frame�������ҵı߽����
		
		checkboxList = new ArrayList<JCheckBox>();//��������ΪJCheckBox��һ��checkboxList
		Box buttonBox = new Box(BoxLayout.Y_AXIS);//����һ�� ��ΪbuttonBox�����򣬷�������ѡ������������򲼾ֹ�����ΪBoxLayout���ҷ��������˳����y�ᣬ���ϵ���
		
		JButton start = new JButton("Start");//����һ����Ϊstart�İ�ť��������ʼ����������Ϣ
		start.addActionListener(new MyStartListener());//ע��ð�ť����������������������MyStartListener���һ��ʵ��
		buttonBox.add(start);//buttonBox�������start��ť
		
		JButton stop = new JButton("Stop");//����һ����Ϊstart�İ�ť��������ֹͣ����������Ϣ
		stop.addActionListener(new MyStopListener());//ע��ð�ť����������������������MyStopListener���һ��ʵ��
		buttonBox.add(stop);//buttonBox�������stop��ť
		
		JButton upTempo  = new JButton("Tempo Up");//����һ����ΪTempo Up�İ�ť������������ٶ�����3%
		upTempo.addActionListener(new MyUpTempoListener());//ע��ð�ť����������������������MyUpTempoListener���һ��ʵ��
		buttonBox.add(upTempo);//buttonBox�������Tempo Up��ť
		
		JButton downTempo  = new JButton("Tempo Down");//����һ����ΪTempo Downt�İ�ť������������ٶȽ���3%
		downTempo.addActionListener(new MyDownTempoListener());//ע��ð�ť����������������������MyDownTempoListener���һ��ʵ��
		buttonBox.add(downTempo);//buttonBox�������Tempo Down��ť
		
		JButton serializelt = new JButton("serializelt");//����һ����Ϊserializelt�İ�ť����������µ�ǰѡ���������������䱣��ΪCheckbox.ser�ļ�
		serializelt.addActionListener(new MySendListener());//ע��ð�ť����������������������MySendListener���һ��ʵ��
		buttonBox.add(serializelt);//buttonBox�������serializelt��ť
		
		JButton restore = new JButton("restore");//����һ����Ϊrestore�İ�ť�������������ļ�ѡ��Ի���ѡ��*.ser(���л��ļ�)����ԭ
		restore.addActionListener(new MyReadInListener());//ע��ð�ť����������������������MyReadInListener���һ��ʵ��
		buttonBox.add(restore);//buttonBox�������restore��ť
		
		Box nameBox = new Box(BoxLayout.Y_AXIS);//����һ�� ��ΪnameBox�����򣬷����������ơ������򲼾ֹ�����ΪBoxLayout���ҷ��������˳����y�ᣬ���ϵ���
		for(int i = 0;i < 16;i++){//���ν�instrumentNames[]�����е�������������Ϊ��ǩ������ӵ�nameBox����
			nameBox.add(new Label(instrumentNames[i]));
		}
		
		background.add(BorderLayout.EAST,buttonBox);//buttonBox����background���BorderLayout��EAST��
		background.add(BorderLayout.WEST,nameBox);//buttonBox����background���BorderLayout��WEST��
		
		theFrame.getContentPane().add(background);//��background�����ӵ������windows��
		
		GridLayout grid = new GridLayout(16,16);//����һ��16*16�����񲼾ֹ�����
		grid.setVgap(1);//����������������������ֱ���Ϊ1
		grid.setHgap(2);//��������������������ˮƽ���Ϊ2
		mainPanel = new JPanel(grid);//��grid��ӵ�mainPanel��
		background.add(BorderLayout.CENTER,mainPanel);////mainPanel����background���BorderLayout��CENTER��
		
		for(int i = 0;i < 256;i++){//����256��checkBox��ѡ����ӵ�ArrayList��mainPanel��16*16�������������Ĭ��״̬��δѡ��
			JCheckBox c = new JCheckBox();
			c.setSelected(false);
			checkboxList.add(c);
			mainPanel.add(c);
			}
		setUpMidi();//���÷������ķ�������ʼ��������
		
		theFrame.setBounds(50, 50, 300, 300);//Դ��JFrame�ķ���������theFrame��������꣨���������Ե�����Ļ���Ͻ�Ϊԭ��ģ��Լ����Ŀ�Ⱥ͸߶�
		theFrame.pack();//�Զ�����theFrame�Ĵ�С�����ʺ������������ѡ��С�Ͳ��֡�
		theFrame.setVisible(true);//����theFrameΪ�ɼ�
	}
	
	public void setUpMidi(){
		try{
			sequencer = MidiSystem.getSequencer();//���1��������ʵ��
			sequencer.open();			
			sequence  = new Sequence(Sequence.PPQ,4);
			track = sequence.createTrack();
			sequencer.setTempoInBPM(120);
		}catch(Exception e){e.printStackTrace();}
	}
	
	public void buildTrackAndStart(){
		int[] trackList = null;//������16��Ԫ�ص��������洢ĳһ��������ֵ�����ý�Ӧ��Ҫ���࣬��ֵ���ǹؼ��ֵ�ֵ������Ϊ0
		
		sequence.deleteTrack(track);
		track = sequence.createTrack();//���½���track
		
		for (int i = 0;i < 16; i++){//���ζ�ȡÿһ�У���������ѡ�����������ÿ��������ִ��1��
			trackList = new int[16];
			
			int key = instruments[i];//ȷ������ʹ��������MIDI���
			
			for (int j = 0;j < 16;j++) {//��ÿһ��ִ��һ��
				JCheckBox jc = (JCheckBox) checkboxList.get(j+(16*i));//ȷ��256��checkBox�У���ǰcheckBox�ı��
				if (jc.isSelected()){
					trackList[j] = key;//����checkBox��ѡ�У��򽫸���ʹ��������MIDI��Ÿ���trackList[16]����
				}else{
					trackList[j] = 0;
				}				
			}
			makeTracks(trackList);//��ÿһ�е�trackList[16]��ΪmakeTracks�����Ĳ���
			track.add(makeEvent(176,1,127,0,16));//�����������¼�����ӵ�track��
		}
		track.add(makeEvent(192,9,1,0,15));//ȷ����16�����¼�����Ȼbeatbox�����ظ�����
		try{		
			sequencer.setSequence(sequence);
			sequencer.setLoopCount(Sequencer.LOOP_CONTINUOUSLY);//ѭ�����ŵ�ǰ���׵�����
			sequencer.start();
			sequencer.setTempoInBPM(120);			
		}catch(Exception e){e.printStackTrace();}
	}
	public class MyStartListener implements ActionListener{//start��ť�ļ�����
		public void actionPerformed(ActionEvent a){
			buildTrackAndStart();//һ������start��ť����buildTrackAndStart()������ʼִ��
		}
	}
	
	public class MyStopListener implements ActionListener{//stop��ť�ļ�����
		public void actionPerformed(ActionEvent a){			
			sequencer.stop();//һ������stop��ť����ֹͣ����
		}
	}
	
	public class MyUpTempoListener implements ActionListener{//TempoUp��ť�ļ�����
		public void actionPerformed(ActionEvent a){
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor * 1.03));//һ������TempoUp��ť������ļӿ�3%
		}
	}
	
	public class MyDownTempoListener implements ActionListener{//TempoDown��ť�ļ�����
		public void actionPerformed(ActionEvent a){
			float tempoFactor = sequencer.getTempoFactor();
			sequencer.setTempoFactor((float)(tempoFactor * 0.97));//һ������TempoDown��ť������ļ���3%
		}
	}
	
	public void makeTracks(int[] list){//
		for (int i =0;i < 16;i++){//ÿ16����Ϊһ��
			int key = list[i];
			if (key != 0){//������Ԫ�ز�Ϊ0����������MIDI���ʱ
				track.add(makeEvent(144,9,key,100,i));//�ڵ�i�Ŀ�ʼ���׼�¼��Ƶ��Ϊ9������ǡ��ȡ��Ӧ����MIDI��ţ�����Ϊ100
				track.add(makeEvent(128,9,key,100,i+1));//�ڵ�i+1�Ľ������׼�¼��Ƶ��Ϊ9������ǡ��ȡ��Ӧ����MIDI��ţ�����Ϊ100			
			}//��������ǡ��ȡ��Ӧ����MIDI��ţ���˲���ѡMIDI���̫С����������ע���ʱ��Ƶ��9
			//̽����һ�£���֪��ȷ���Ƶ��9���ƺ��и��ص㣺����������ͬʱ���������͵�ǰ������MIDI��ţ�
			//����makeEvent(144,9,key,100,i)�ĵ���������key��˵���õ�key����ŵ�MIDI���������key������
			//�����ڵ�5����������������������Ľ���������ˣ�ͬһ�е���������ѡ��,��һ�������
			//ÿ��һ��tracklist�Ϳ�ʼ�����ˣ���ʵ����������һЩֻ����CPU�ٶ�̫�죬��������
		}
	}
	
	public class MySendListener implements ActionListener{//serializelt��ť�ļ�����
		public void actionPerformed(ActionEvent a){
			boolean[] checkboxState =new boolean[256];//�½�����Ϊ256�Ĳ���������checkboxState���ڴ��256��checkbox��״̬
			for (int i = 0;i < 256;i++){//����checkboxListԪ�ص�״̬������״̬д��checkboxState[]����
				JCheckBox check = (JCheckBox) checkboxList.get(i);//��checkboxList����һѡ��ÿ��checkbox����ԭ��JCheckBox��������
				if(check.isSelected()){
					checkboxState[i] = true;
				}
			}
			try{
				FileOutputStream fileStream = new FileOutputStream(new File("Checkbox.ser"));//�½��ļ����������������������ڣ����½������������ΪCheckbox.ser��������Checkbox.ser���ļ���ֱ�Ӹ���
				ObjectOutputStream os = new ObjectOutputStream(fileStream);//�½����������������������ļ��������
				os.writeObject(checkboxState);//�����������д��Ķ�����checkboxState				
			}catch(Exception ex){//���䣺���ɵ�Checkbox.ser�ļ�λ�ڸ�������ļ�����
				ex.printStackTrace();
			}
		}
	}
	
	public class MyReadInListener implements ActionListener{//restore��ť�ļ�����
		public void actionPerformed(ActionEvent a){
			boolean[] checkboxState = null;//�Ƚ�checkboxState�������
			try{
				JFileChooser fileOpen = new JFileChooser();//�½��ļ�ѡ��ģ��
				fileOpen.showOpenDialog(theFrame);//���ļ��򿪶Ի�����ʾ��theFrame�����
				FileInputStream fileIn = new FileInputStream(fileOpen.getSelectedFile());//�½��ļ�������������Դ�������ļ��򿪶Ի���ѡ����ļ�
				ObjectInputStream is = new ObjectInputStream(fileIn);//�������������ļ�������
				checkboxState = (boolean[]) is.readObject();//�Ӷ�����������ȡ�������ڶ�ȡ�Ķ�����boolean�����ͣ���ֻ���ڵȺ��ұ߼���(boolean[])��ǿ��ת��һ�¼���					
			}catch(Exception ex) {
				ex.printStackTrace();
			}
			for(int i = 0;i < 256;i++){//����checkboxState��ֵ��ԭÿ��checkbox��״̬
				JCheckBox check = (JCheckBox) checkboxList.get(i);
				if(checkboxState[i]){
					check.setSelected(true);
				}else{
					check.setSelected(false);
				}
			}
			sequencer.stop();//һ���û�����restore��ť��������ֹͣ��ǰ���ײ���
			buildTrackAndStart();//���������Ż�ԭ�������
		}
	}
	public MidiEvent makeEvent(int cmd, int chl, int one, int two, int tick) {//makeEvent�����൱�ڹ��캯��setMessage��MidiEvent����
		// TODO Auto-generated method stub
		MidiEvent event = null;//������Ϣ��ʼ��
		try {
			ShortMessage a = new ShortMessage();
			a.setMessage(cmd, chl, one,two);//�˴�����Message��Ϣ���൱�ڸ���������һ������һ�ν��ģ�����Ҫ��ʲô����ʼ���࣬�������������ȵȣ�
			event = new MidiEvent(a,tick);//����һ�������Ϣ���ĸ����Ŀ�ʼ
			}catch(Exception e){ }
		return event;//������һ�����׼�¼����Ϣ
	}
}
