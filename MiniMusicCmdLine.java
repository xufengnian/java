//�����javaС����ʹ�������в������ı����������������߷�Χ����0��127֮�䣬��һ�����������趨�������ڶ������������趨����
import javax.sound.midi.*;//����midi��
public class MiniMusicCmdLine {
	public static void main(String[] args){
		MiniMusicCmdLine mini = new MiniMusicCmdLine();
		if (args.length < 2){//��������еĲ�������������������С��2������ʾ����ȱʧ
			System.out.println("Don't forget the instrument and note args");
		}else{//������1�Ͳ���2ǿ��ת�������ͣ����ڷ�����ȡ
			int instrument = Integer.parseInt(args[0]);
			int note = Integer.parseInt(args[1]);
			mini.play(instrument,note);
		}
	}
	
	public void play(int instrument,int note){//���һ������Ϊ�������ͣ��ڶ�������Ϊ����
		
		try {
			Sequencer player = MidiSystem.getSequencer();//ȡ�ó����� MidiSystem��ʵ��getSequencer��Sequencer�൱��CD������
			player.open();//��ȡ�õ�getSequencerʵ����
			Sequence seq = new Sequence(Sequence.PPQ,4);//Sequence�൱�ڵ���CD���趨�������������ٶȵĶ�ʱ���ͣ��侫�������壨���ģ�ÿ�ķ�������ʾ��
			Track track =seq.createTrack();//ȡ����������Track����ʵ�൱�ڿ�ʼ����һ�׸�����
			
			MidiEvent event = null;//�൱�ڳ�ʼ������
			//MidiEvent����Ƶ�¼�������ִ�еĹ����൱�࣬�ȿ��Լ�¼������������ʲô������ʹ���ĸ�Ƶ�������ߺ���������ֵ
			//Ҳ���Ա�ʾ����������ָ��
			//MIDI��Message��MidiEvent�Ĺؼ�
			ShortMessage first = new ShortMessage();//�½�һ��������Ϣ
			first.setMessage(192,1,instrument,0);//192���ı��������࣬Ƶ��Ϊ1����������Ϊ����Ĳ���instrument������Ϊ0
			MidiEvent changeInstrument = new MidiEvent(first,1);//�ڵ�1�����ģ�����changeInstrument�����Ϣ
			track.add(changeInstrument);//���������changeInstrument�����Ϣ
			
			
			ShortMessage a = new ShortMessage();//�½�һ��������Ϣ
			a.setMessage(144,1,note,100);//144�������׽��м�¼��Ƶ��Ϊ1������Ϊ����Ĳ���note������Ϊ100
			MidiEvent noteOn = new MidiEvent(a,1);//�ڵ�1�����ģ�����noteOn�����Ϣ
			track.add(noteOn);//���������noteOn�����Ϣ
			
			ShortMessage b = new ShortMessage();//�½�һ��������Ϣ
			b.setMessage(128,1,note,100);//128���ر����׼�¼��Ƶ��Ϊ1������Ϊ����Ĳ���note������Ϊ100
			MidiEvent noteOff = new MidiEvent(b,16);//�ڵ�16�����ģ�����noteOff�����Ϣ
			track.add(noteOff);//���������noteOff�����Ϣ
			
			player.setSequence(seq);//Track��¼����������Ϣ,���Խ���Sequencer������
			player.start();//Sequencer��ʼ����
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

/*
�򵥽���һ����ShortMessage��ShortMessage�ж���������࣬������ͬ�Ĺ��캯����������һ�����캯���ṹ���£�������setMessage�趨�ľ���������캯��
ShortMessage(int Command,int Channel,int Data1,int Data2)
��һ������Command�����ڱ�ʾ�����������Ϣ��������ֻ��Ҫ����3�����ɣ�192��������������࣬144�������׼�¼�򿪣�128����ر�
�ڶ�������Channel�����ڱ�ʾƵ����Ϣ��ÿ��Ƶ������ͬ�������ߣ������ò�������Ϊ1���ɣ�����Ҫ�޸�Ƶ��
����������Data1�����ݵ�һ������Command�Ĳ�ͬ�����岻ͬ������һ������Ϊ192���޸�����ָ�����Data1������õ��������࣬��Χ0~127�������Ӧ��ϵ��MIDI������ű�
	����һ������Command��144��128����Data1�����������ߣ���ΧҲ��0~127
���ĸ�����Data2��ͬ�����ݵ�һ������Command�Ĳ�ͬ�����岻ͬ������һ������Ϊ192���޸�����ָ�����Data2ֱ������Ϊ0����
	����һ������Command��144��128����Data2����������������ΧҲ��0~127��0������������100���
*/

/*
�ٽ���һ����MidiEvent
���캯������
MidiEvent(MidiMessage message, long tick) 
��һ������message��ʹ��MidiMessage������ʵ�����������ShortMessage������ʵ��
�ڶ�������tick����������һ�������������Message
*/

// ���ڡ�Could not open/create prefs root node Software\JavaSoft\Prefs at root 0x80000002������
/*
1.win+R����regedit��
2.�ҵ�HKEY_LOCAL_MACHINE \ SOFTWARE \ JavaSoft��
3.�Ҽ�JavaSoft�������Ҽ�������Ȩ��Ϊ��ȫ���Ʊ���ԣ���Ȼ��win10��ʧ���ˣ�Ȼ����Գ�����ע���ֱ���½�HKEY_LOCAL_MACHINE\Software\JavaSoft\Prefs �
 */
