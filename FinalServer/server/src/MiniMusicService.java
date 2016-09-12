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
				Sequencer sequencer = MidiSystem.getSequencer();//���1��������ʵ��
				sequencer.open();
				sequencer.addControllerEventListener(myPanel, new int[] {127});//��Sequencerע���¼���ע��ķ���ȡ��MyDrawPanelʵ��m1�ʹ�����Ҫ�������¼���int����,����������ֻ��Ҫ127�¼�
				Sequence seq = new Sequence(Sequence.PPQ,4);
				Track track = seq.createTrack();			
				
				for (int i = 0;i < 100;i += 4){//600/4=150��������150�����Σ���150������
					int rNum = (int) (Math.random()*127);//�������0~127��������ΪMessage�ĵ�������������������
					track.add(makeEvent(144,1,rNum,100,i));//144��Ƶ��1����r������������100����i�����Ŀ�ʼ��¼
					track.add(makeEvent(176,1,127,0,i));//176��Ƶ��1���������Ϊ127���¼����ڱ���������׽������0����i�����Ŀ�ʼ��������144һ�����ľ���Ϊ���ڿ�ʼ��¼ʱ�����¼�������
					track.add(makeEvent(128,1,rNum,100,i+2));//128��Ƶ��1����r������������100����i+2�����Ľ�����¼���ĳ�i�ƺ���Ӱ�죿��
				}
				sequencer.setSequence(seq);//Track��¼����������Ϣ,���Խ���Sequencer������
				sequencer.setTempoInBPM(120);//�����ٶȣ���ÿ���ӵ�����Ϊ��λ���������0.5s����һ������
				sequencer.start();//Sequencer��ʼ����
			}catch(Exception ex){
				ex.printStackTrace();}		
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
		public Dimension getPreferredSize(){
			return new Dimension(300,300);
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
