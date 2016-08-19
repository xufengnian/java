//击沉战舰简单版小游戏
//规则：游戏随机生成0到6之间的连续3个数字，玩家每次输入一个数字，若恰好输入了生成的随机数，则显示hit,反之显示miss.
//3个数都hit,则游戏结束，显示kill，并显示玩家猜测的次数
import java.io.*;//引入io库，便于处理玩家输入
public class SimpleDotComGame {//游戏主函数所在类
	public static void main(String[] args){
		int numOfGuess=0;//玩家猜测数
		GameHelper helper=new GameHelper();//生成游戏帮助对象，用于和玩家交互，并处理玩家输入数字
		
		SimpleDotCom theDotCom=new SimpleDotCom();//生成游戏处理对象，负责判断玩家输入数字的类型转换和判定，并生成判定结果
		int randomNum=(int) (Math.random()*5);//生成初始随机数，从0到4
		
		int[] locations={randomNum,randomNum+1,randomNum+2};//声明3个连续随机数的数组
		theDotCom.setlocationCells(locations);//调用set方法将3个随机数交给theDotCom类
		boolean isAlive=true;//声明isAlive变量用于循环结束判定
		
		while(isAlive==true){//true表明玩家仍未结束游戏，战舰未kill
			String guess=helper.getUserInput("enter a number");//让GameHelper类显示的提示信息为“enter a number”
			String result=theDotCom.CheckYourself(guess);//调用theDotCom类的CheckYourself用于判定玩家的输入数字
			numOfGuess++;//玩家输入数字后，猜测数加一
			if(result.equals("kill")){//若返回结果为kill,说明战舰已沉没，显示玩家的猜测数
				isAlive=false;
				System.out.println("You took "+numOfGuess+" guesses");
			}
		}
	}

}

class SimpleDotCom {//游戏逻辑处理判定类
	int[] locationCells;//声明数组用于存放战舰所在位置
	int numOfHits=0;//声明int变量存放战舰被打击的次数
	
	public void setlocationCells (int[] locs ){//set方法将随机数组赋予战舰位置数组
		locationCells=locs;		
	}
	
	public String CheckYourself (String UserGuess){//判定
		int guess=Integer.parseInt(UserGuess);//强制将玩家输入字符型数据转换成数字型
		String result="miss";//默认判定为miss
		for (int cell :locationCells){//加强型for循环，相当于for(int cell,cell<locationCells.length(),cell++)，遍历战舰所在位置
			if(cell==guess){//如果战舰所在位置和玩家猜测一致，显示结果hit，击中数加一
				result="hit";
				numOfHits++;
				break;
			}
		}
		if (numOfHits==locationCells.length)//若玩家击中数等于战舰长度，显示kill
		{
			result="kill";
		}
		System.out.println(result);
		return result;//返回结果
	}
}

class GameHelper {//显示提示信息，处理玩家输入数字
	public String getUserInput (String prompt){//将想要提示的文字显示出来
		String inputLine=null;//保存玩家输入数字
		System.out.print(prompt+" ");
		try{//读入玩家输入的数字，若未输入，则抛出异常并捕获
			BufferedReader is =new BufferedReader(new InputStreamReader(System.in));
			inputLine=is.readLine();
			if (inputLine.length()==0)
				return null;
		} catch (IOException e){
			System.out.println("IOException: "+e);
					}
		return inputLine;
	}
}
//bug:若玩家连续3次输入同一击中坐标，战舰会沉没，逻辑错误。应该及时删除被击中的数字
//level up:下一步在7*7的二维数组中随机生成多个战舰，且有横有纵
