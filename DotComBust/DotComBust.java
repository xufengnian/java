//����ս��С��Ϸ������
//������7*7�ķ�Χ�У�a0~a6~~~g0~g6������Ϸ�������3��ռ��3��λ�õ�ս�����ɺ���ݣ����β��������a1,a2,a3;�ݵ�����ĸ������b2,c2,d2��
//���ÿ������һ�����꣬����ĳ��ս����ʾhit��������ʾmiss,��������ͬһս��3����ʾkill������ʾ������ս�������ƣ�,��3��ս��ͬʱ������,����Ϸ��������ʾ��ҳ��Դ���
import java.util.*;//����util��,Ϊ�������·���ArrayList
public class DotComBust {//��Ϸ���࣬���ڴ���DotCom,	ȡ����ҵ����룬��ʼ��Ϸ
	private GameHelper helper = new GameHelper();//������Ϸ������Ķ���
	private ArrayList<DotCom> dotComsList=new ArrayList<DotCom> ();//����һ����DotCom�����ΪԪ�ص���������
	private int numOfGuesses=0;//����Ҳ²�������Ϊ0
	
	private void SetUpGame(){
		DotCom one=new DotCom();//����3��Dotcom�����ʾս��������ӵ� ��DotCom�����ΪԪ�ص���������dotComsList��
		one.setName("Pets.com");
		DotCom two=new DotCom();
		two.setName("eToys.com");
		DotCom three=new DotCom();
		three.setName("Go2.com");
		dotComsList.add(one);
		dotComsList.add(two);
		dotComsList.add(three);
		
		System.out.println("You goat is to sink three dot coms.");//������ʾ��Ϣ
		System.out.println("Pets.com,eToys.com,Go2.com");
		System.out.println("Try to sink them all in the fewest number of guesses");
		
		for (DotCom dotComToSet : dotComsList){//����һ��dotComToSet�������ڱ���  ��DotCom�����ΪԪ�ص���������dotComsList
			ArrayList<String> newLocation =helper.placeDotCom(3);//����GameHelper���placeDotCom�����������3��ս����λ������
			dotComToSet.setLocationCells(newLocation);//ʹ��set�����������ɵ�ս�����걣�浽�ַ�����������
		}
	}
private void startPlaying() {
	while(!dotComsList.isEmpty()){//��Ϸ��ʼ��ֻҪս��������Ϊ0������ʾ��Ҽ����������꣨ע�����ʽ����a1,b2���мǲ����пո�
		String userGuess = helper.getUserInput("Enter a guess");
		checkUserGuess(userGuess);//����checkUserGuess�����ж��������
	}
	finishGame();//ս������Ϊ0�������Ϸ
}

private void checkUserGuess(String userGuess) {//�ж��������
	numOfGuesses++;//���ÿ����һ�Σ��²�����һ
	String result = "miss";//Ĭ��Ϊ����
	for (DotCom dotComToTest :dotComsList){//����һ��dotComToTest�������ڱ���  ��DotCom�����ΪԪ�ص���������dotComsList(ս��������������)
		result = dotComToTest.checkYourself(userGuess);//����checkYourself��������ֵ�ж��������
		if(result.equals("hit")){
			break;
		}
		if(result.equals("kill")){
			dotComsList.remove(dotComToTest);
			break;
		}
	}
	System.out.println(result);
}

private void finishGame() {//��Ϸ��������ʾ������Ϣ�������Ϸ�²���
	System.out.println("All Dot Coms are dead!");
	System.out.println("you take "+numOfGuesses+" guesses.");
	}
	
public static void main (String[] args){//��������ڣ�����game���󣬳�ʼ����Ϸ����ʼ
	DotComBust game=new DotComBust();
	game.SetUpGame();
	game.startPlaying();
}

}


