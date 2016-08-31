//MIDI���ֺ�pro�棬�ص㣺�Զ�����150����С��ɫ��ͬ�ľ��Σ���ÿ����1�����Σ�����һ���������
import javax.sound.midi.*;
import javax.swing.*;
import java.awt.*;

public class MiniMusicPlayer {
	static JFrame f = new JFrame("My First Music Video!");//�½���������ܣ��ұ�����������ʾ"My First Music Video!"
	static MyDrawPanel m1;//����1������壬����API�ļ���֪��MyDrawPanel�̳���JPanel,JPanel�ּ̳��� java.awt.Container;
	
	public static void main (String[] args){
		MiniMusicPlayer mini = new MiniMusicPlayer();
		mini.go();
	}
	
	public void setUpGui() {
		// TODO Auto-generated method stub
		m1 = new MyDrawPanel();
		f.setContentPane(m1);//Դ��JFrame�ķ�����public void setContentPane(Container contentPane)���� contentPane ���ԡ��˷����ɹ��췽�����á�	
		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�Ѵ��ڹرյ�ͬʱ��������
		f.setBounds(30, 30, 300, 300);//Դ��JFrame�ķ���������frame��������꣨���������Ե�����Ļ���Ͻ�Ϊԭ��ģ��Լ����Ŀ�Ⱥ͸߶�
		f.setVisible(true);//����frameΪ�ɼ�
	}
	public void go() {//������
		// TODO Auto-generated method stub
		setUpGui();//�����ɳ����frame����
		try {//Sequencer����˵�����MiniMusicCmdLine.java
			Sequencer sequencer = MidiSystem.getSequencer();//���1��������ʵ��
			sequencer.open();
			sequencer.addControllerEventListener(m1, new int[] {127});//��Sequencerע���¼���ע��ķ���ȡ��MyDrawPanelʵ��m1�ʹ�����Ҫ�������¼���int����,����������ֻ��Ҫ127�¼�
			Sequence seq = new Sequence(Sequence.PPQ,4);
			Track track = seq.createTrack();
			/*
			 ����һ��Sequencer��addControllerEventListener����
			 int[] addControllerEventListener(ControllerEventListener listener,int[] controllers)
                                 
			 ע��һ���ؼ��¼����������Ա��� sequencer �����������һ�ֻ�������͵Ŀ��Ƹ����¼�ʱ����֪ͨ�������� controllers ����ָ�����ò���Ӧ����һ�� MIDI �ؼ��ŵ����顣
			 ��ÿ�����Ӧ���� 0 �� 127��������֮���һ��������μ� MIDI 1.0 �淶����������Ϳؼ���Ӧ�ı�š���
			 ������
			listener - Ҫ��ӵ���ע���������б��еĿؼ��¼�������
			controllers - Ϊ���������֪ͨ�� MIDI �ؼ���
			���أ�
			����Ľ������ָ�������������� MIDI �ؼ���
			
			�ص�������sequencer.addControllerEventListener(m1, new int[] {127});��ʵ����m1ע���˱��Ϊ127���Զ���ControllerEvent(176),һ��m1�з���127�¼������Ϸ���
			 */
			
			int r = 0;//������ʼֵ����Ϊ0
			for (int i = 0;i < 600;i += 4){//600/4=150��������150�����Σ���150������
				r = (int) (Math.random()*127);//�������0~127��������ΪMessage�ĵ�������������������
				track.add(makeEvent(144,1,r,100,i));//144��Ƶ��1����r������������100����i�����Ŀ�ʼ��¼
				track.add(makeEvent(176,1,127,0,i));//176��Ƶ��1���������Ϊ127���¼����ڱ���������׽������0����i�����Ŀ�ʼ��������144һ�����ľ���Ϊ���ڿ�ʼ��¼ʱ�����¼�������
				track.add(makeEvent(128,1,r,100,i+2));//128��Ƶ��1����r������������100����i+2�����Ľ�����¼���ĳ�i�ƺ���Ӱ�죿��
			}
			sequencer.setSequence(seq);//Track��¼����������Ϣ,���Խ���Sequencer������
			sequencer.setTempoInBPM(120);//�����ٶȣ���ÿ���ӵ�����Ϊ��λ���������0.5s����һ������
			sequencer.start();//Sequencer��ʼ����
		}catch(Exception ex){
			ex.printStackTrace();
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

	class MyDrawPanel extends JPanel implements ControllerEventListener{
		boolean msg = false;//�����¼��ı�־��ֻ�в����¼��Ż�Ϊ�棨��ν�Ĳ����¼�����������1�����ģ�
		public void controlChange(ShortMessage event){
			msg = true;//�����¼�ʱ����Ϊ��
			repaint();//�����ػ����
		}
		
		public void paintComponent(Graphics g){//������JPanel�б��뱻��д�ĳ��󷽷������������������
			if(msg) {//����Ҳ�����������������ػ棬����Ҫ�ж��Ƿ���ControllerEvent����
				//Graphics2D g2 = (Graphics2D) g;
				
				int r = (int)(Math.random()*255);
				int gr = (int)(Math.random()*255);
				int b = (int)(Math.random()*255);
				//��RGB��ɫ�ռ���Ϊ��ɫ��׼��3�߾�ȡ0~255��������
				g.setColor(new Color(r,gr,b));
				
				int ht = (int)((Math.random()*120)+10);
				int width = (int)((Math.random()*120)+10);
				
				int x = (int)((Math.random()*40)+10);
				int y = (int)((Math.random()*40)+10);
				//�������ɾ��ε����꣨Frame���Ͻ�Ϊԭ�㣩,�Լ����εĿ�͸ߣ��⼸�����־��������
				g.fillRect(x, y, width, ht);
				msg = false;
			}
		}

	}
	

}
/*
 �򵥽���һ�±������߼���ϵ
 �Ȼ���GUI����
 ���һ��������
 ��¼��Ŀ������Ϣ
 ÿ��¼һ��������Ϣ�ʹ���һ�����Ϊ127���¼�
 ���¼�һ���������͵���JPanel���repaint������ʼ���»��ƾ��Σ������ƾ��ε�Ҫ�������paintComponent����
 ��˵�������ɽ��ģ����ɽ��ĵ�ͬʱ�����¼�,����׽֮��ʼ���Ʒ���Ҫ��ľ��Σ�֪�����Ĳ�������Ϊֹ
 */
