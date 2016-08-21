//未完全注释，等待下次
import java.util.*;
import java.io.*;
//将游戏范围限定在7*7的网格之中，0到6代表行，a到g代表列。坐标组合方式略奇怪：列在前行在后。从a0~g6分别是0到48
public class GameHelper {
	private static final String alphabet = "abcdefg";
	private int gridLength = 7;//设置网格长度
	private int gridSize = 49;//设置网格总数目
	private int[] grid = new int [gridSize];//声明网格所在数组
	private int comCount=0;//战舰编号从0开始

	public String getUserInput(String prompt) {//显示提示信息，获取玩家输入，并将玩家输入字符全部转换成小写
		String inputLine=null;
		System.out.print(prompt+" ");
		try{
			BufferedReader is =new BufferedReader(new InputStreamReader(System.in));
			inputLine=is.readLine();
			if (inputLine.length()==0)
				return null;
		} catch (IOException e){
			System.out.println("IOException: "+e);
					}
		return inputLine.toLowerCase();
	}
	public ArrayList<String> placeDotCom(int comSize) {//设置一个战舰的生成坐标，comSize为战舰长度，即战舰生命值
		ArrayList<String> alphaCells = new ArrayList<String> ();//声明一个字符串型ArrayList，保存一艘战舰的位置坐标
		String [] alphacoords = new String [comSize];//声明一个长度为 战舰长度 的字符串型数组用于存放战舰坐标
		String temp = null;//用于保存单个战舰的其中一个坐标，由英文字母和数字组成如（a1,b2等）
		int[] coords = new int[comSize];//存储单艘战舰的位置随机数，方便之后转换成字符型坐标
		int attempts = 0;//尝试生成战舰坐标次数
		boolean success = false;//声明boolean型变量success表示生成是否成功
		int location = 0;//战舰初始位置，默认从7*7方格的0号位置开始
		
		comCount++;//战舰有横有纵的关键，战舰编号从1开始，偶数编号战舰为纵，奇数编号战舰为横
		int incr = 1;//战舰坐标增量，在7*7的方格中横的战舰，下一个坐标增量为1；纵的战舰，下一个坐标增量为7
		if ((comCount % 2) == 1){
			incr = gridLength;
		}
		
		while(!success & attempts < 200){//若战舰未生成且常识次数小于200，则开始循环
			location = (int) (Math.random()*gridSize);//一艘战舰坐标的初始随机值，范围在0到48之间
			
			int x=0;//记录在生成某一艘战舰的 第几个位置（本例中由于战舰的长度是3，故x范围是从0到2）
				success=true;//初始坐标生成啦，先将生成状态改为true，随后对下一个坐标进行判定
				while (success && x < comSize){//初始坐标成功生成且未达到战舰长度时开始循环
					if(grid[location] == 0){//判断生成的战舰坐标是否已经被使用过
						coords[x++] = location;//存储位置						
						location +=incr;//初始值开始添加增量生成下一个位置坐标
						if (location >= gridSize){//如果坐标大于网格区域则生成失败，防止下越界
							success = false;
						}
						if (x > 0 && (location % gridLength == 0)){//如果生成的新位置（非第一个位置坐标）为7的倍数，则生成失败（说明横向战舰跨行了）
							success = false;//嗯，其实该判定是有问题的，如果想生成a0,a1,a2（即a这一列的纵向战舰）就会生成失败，但这确实是防止右越界的办法
						}
					}
					else{//如果新生成的战舰坐标和之前的战舰重复则生成失败
						
						success = false;
					}
				}	
		
		}
		int x = 0;//将x重新置零，方便处理每一个战舰坐标
		int row = 0;
		int column = 0;
		//初始化行列信息，将数字型坐标位置信息转换成字符型	
		while (x < comSize){
			grid[coords[x]] = 1;//将已经生成的战舰坐标置一表明该位置已被使用
			row = (int) (coords[x] / gridLength);//行信息直接由  战舰坐标除以7取商
			column = coords[x] % gridLength;//列信息先提取 战舰坐标除以7取余数的数值 作为下面charAt函数的索引值
			temp = String.valueOf(alphabet.charAt(column));//利用临时字符串temp存储转换成字符型的列信息
			
			alphaCells.add(temp.concat(Integer.toString(row)));//temp字符串加上 转换成字符型的行信息，并添加到字符串型ArrayList类alphaCells中，保存一艘战舰的位置坐标
			x++;//x递增，处理下一个坐标信息
			System.out.println("coords " +x+ " = "+ alphaCells.get(x-1));//该行可注释：为了方便测试而显示战舰所在位置
		}
		return alphaCells;//该方法结束，返回一艘战舰的3个坐标
	}
}
