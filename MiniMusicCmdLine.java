//多参数java小程序，使用命令行参数来改变乐器和音符，两者范围均是0到127之间，第一个参数用于设定乐器，第二个参数用于设定音符
import javax.sound.midi.*;//导入midi包
public class MiniMusicCmdLine {
	public static void main(String[] args){
		MiniMusicCmdLine mini = new MiniMusicCmdLine();
		if (args.length < 2){//检测命令行的参数个数，若参数个数小于2个则提示参数缺失
			System.out.println("Don't forget the instrument and note args");
		}else{//将参数1和参数2强制转换成整型，便于方法读取
			int instrument = Integer.parseInt(args[0]);
			int note = Integer.parseInt(args[1]);
			mini.play(instrument,note);
		}
	}
	
	public void play(int instrument,int note){//令第一个参数为乐器类型，第二个参数为音符
		
		try {
			Sequencer player = MidiSystem.getSequencer();//取得抽象类 MidiSystem的实例getSequencer，Sequencer相当于CD播放器
			player.open();//将取得的getSequencer实例打开
			Sequence seq = new Sequence(Sequence.PPQ,4);//Sequence相当于单曲CD，设定音序器，基于速度的定时类型，其精度在脉冲（节拍）每四分音符表示。
			Track track =seq.createTrack();//取得音序器的Track，其实相当于开始创作一首歌曲了
			
			MidiEvent event = null;//相当于初始化乐谱
			//MidiEvent即音频事件，可以执行的功能相当多，既可以记录单个音符是由什么乐器，使用哪个频道，音高和音量的数值
			//也可以表示更换乐器的指令
			//MIDI的Message是MidiEvent的关键
			ShortMessage first = new ShortMessage();//新建一个乐谱信息
			first.setMessage(192,1,instrument,0);//192，改变乐器种类，频道为1，乐器类型为传入的参数instrument，音量为0
			MidiEvent changeInstrument = new MidiEvent(first,1);//在第1个节拍，启动changeInstrument这个信息
			track.add(changeInstrument);//乐谱中添加changeInstrument这个信息
			
			
			ShortMessage a = new ShortMessage();//新建一个乐谱信息
			a.setMessage(144,1,note,100);//144，打开乐谱进行记录，频道为1，音高为传入的参数note，音量为100
			MidiEvent noteOn = new MidiEvent(a,1);//在第1个节拍，启动noteOn这个信息
			track.add(noteOn);//乐谱中添加noteOn这个信息
			
			ShortMessage b = new ShortMessage();//新建一个乐谱信息
			b.setMessage(128,1,note,100);//128，关闭乐谱记录，频道为1，音高为传入的参数note，音量为100
			MidiEvent noteOff = new MidiEvent(b,16);//在第16个节拍，启动noteOff这个信息
			track.add(noteOff);//乐谱中添加noteOff这个信息
			
			player.setSequence(seq);//Track记录好了曲谱信息,可以交给Sequencer播放了
			player.start();//Sequencer开始播放
		}catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}

/*
简单解释一下类ShortMessage，ShortMessage有多个参数种类，数量不同的构造函数，其中有一个构造函数结构如下，本例的setMessage设定的就是这个构造函数
ShortMessage(int Command,int Channel,int Data1,int Data2)
第一个参数Command，用于表示命令的类型信息，本例中只需要记下3个即可：192代表更换乐器种类，144代表乐谱记录打开，128代表关闭
第二个参数Channel，用于表示频道信息，每个频道代表不同的演奏者，本例该参数设置为1即可，不需要修改频道
第三个参数Data1，根据第一个参数Command的不同而意义不同，若第一个参数为192（修改乐器指令），则Data1代表采用的乐器种类，范围0~127，具体对应关系搜MIDI乐器编号表
	若第一个参数Command是144或128，则Data1代表发出的音高，范围也是0~127
第四个参数Data2，同样根据第一个参数Command的不同而意义不同，若第一个参数为192（修改乐器指令），则Data2直接设置为0即可
	若第一个参数Command是144或128，则Data2代表发出的音量，范围也是0~127，0几乎听不见，100差不多
*/

/*
再解释一下类MidiEvent
构造函数如下
MidiEvent(MidiMessage message, long tick) 
第一个参数message，使用MidiMessage创建的实例，本题就是ShortMessage创建的实例
第二个参数tick，代表在哪一个节拍启动这个Message
*/

// 关于“Could not open/create prefs root node Software\JavaSoft\Prefs at root 0x80000002”出错
/*
1.win+R代开regedit；
2.找到HKEY_LOCAL_MACHINE \ SOFTWARE \ JavaSoft；
3.右键JavaSoft，单击右键，更改权限为完全控制便可以；（然而win10下失败了，然后可以尝试在注册表直接新建HKEY_LOCAL_MACHINE\Software\JavaSoft\Prefs 项）
 */
