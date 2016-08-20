//击沉战舰小游戏豪华版
//规则：在7*7的范围中（a0~a6~~~g0~g6），游戏随机生成3个占据3个位置的战舰，可横可纵（横的尾数连续如a1,a2,a3;纵的首字母连续如b2,c2,d2）
//玩家每次输入一个坐标，击中某个战舰显示hit，否则显示miss,连续击中同一战舰3次显示kill（并显示被击沉战舰的名称）,若3个战舰同时被击沉,则游戏结束并显示玩家尝试次数
import java.util.*;//导入util包,为了引入下方的ArrayList
public class DotComBust {//游戏主类，用于创建DotCom,	取得玩家的输入，开始游戏
	private GameHelper helper = new GameHelper();//创建游戏帮助类的对象
	private ArrayList<DotCom> dotComsList=new ArrayList<DotCom> ();//这是一个以DotCom类对象为元素的特殊数组
	private int numOfGuesses=0;//将玩家猜测数设置为0
	
	private void SetUpGame(){
		DotCom one=new DotCom();//创建3个Dotcom对象表示战舰，并添加到 以DotCom类对象为元素的特殊数组dotComsList中
		one.setName("Pets.com");
		DotCom two=new DotCom();
		two.setName("eToys.com");
		DotCom three=new DotCom();
		three.setName("Go2.com");
		dotComsList.add(one);
		dotComsList.add(two);
		dotComsList.add(three);
		
		System.out.println("You goat is to sink three dot coms.");//给出提示信息
		System.out.println("Pets.com,eToys.com,Go2.com");
		System.out.println("Try to sink them all in the fewest number of guesses");
		
		for (DotCom dotComToSet : dotComsList){//创建一个dotComToSet对象用于遍历  以DotCom类对象为元素的特殊数组dotComsList
			ArrayList<String> newLocation =helper.placeDotCom(3);//调用GameHelper类的placeDotCom方法随机生成3个战舰的位置坐标
			dotComToSet.setLocationCells(newLocation);//使用set方法，将生成的战舰坐标保存到字符串型数组中
		}
	}
private void startPlaying() {
	while(!dotComsList.isEmpty()){//游戏开始，只要战舰数量不为0，则提示玩家继续输入坐标（注输入格式类似a1,b2，切记不能有空格）
		String userGuess = helper.getUserInput("Enter a guess");
		checkUserGuess(userGuess);//调用checkUserGuess方法判定玩家输入
	}
	finishGame();//战舰数量为0则结束游戏
}

private void checkUserGuess(String userGuess) {//判定玩家输入
	numOfGuesses++;//玩家每输入一次，猜测数加一
	String result = "miss";//默认为打不中
	for (DotCom dotComToTest :dotComsList){//创建一个dotComToTest对象用于遍历  以DotCom类对象为元素的特殊数组dotComsList(战舰坐标所在数组)
		result = dotComToTest.checkYourself(userGuess);//根据checkYourself方法返回值判定玩家输入
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

private void finishGame() {//游戏结束，提示结束信息和玩家游戏猜测数
	System.out.println("All Dot Coms are dead!");
	System.out.println("you take "+numOfGuesses+" guesses.");
	}
	
public static void main (String[] args){//主函数入口，生成game对象，初始化游戏并开始
	DotComBust game=new DotComBust();
	game.SetUpGame();
	game.startPlaying();
}

}


