//未完全注释，等待下次
import java.util.*;
import java.io.*;
//将游戏范围限定在7*7的网格之中
public class GameHelper {
	private static final String alphabet = "abcdefg";
	private int gridLength = 7;//设置网格长度
	private int gridSize = 49;//设置网格总数目
	private int[] grid = new int [gridSize];//声明网格所在数组
	private int comCount=0;

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
		ArrayList<String> alphaCells = new ArrayList<String> ();//声明一个字符串型ArrayList
		String [] alphacoords = new String [comSize];//声明一个长度为 战舰长度 的字符串型数组用于存放战舰坐标
		String temp = null;
		int[] coords = new int[comSize];
		int attempts = 0;
		boolean success = false;//声明boolean型变量success表示生成是否成功
		int location = 0;
		
		comCount++;
		int incr = 1;
		if ((comCount % 2) == 1){
			incr = gridLength;
		}
		
		while(!success & attempts < 200){
			location = (int) (Math.random()*gridSize);
			
			int x=0;
				success=true;
				while (success && x < comSize){
					if(grid[location] == 0){
						coords[x++] = location;
						location +=incr;
						if (location >= gridSize){
							success = false;
						}
						if (x > 0 && (location % gridLength == 0)){
							success = false;
						}
					}
					else{
						success = false;
					}
				}	
		
		}
		int x = 0;
		int row = 0;
		int column = 0;
		
		while (x < comSize){
			grid[coords[x]] = 1;
			row = (int) (coords[x] / gridLength);
			column = coords[x] % gridLength;
			temp = String.valueOf(alphabet.charAt(column));
			
			alphaCells.add(temp.concat(Integer.toString(row)));
			x++;
			System.out.println("coords " +x+ " = "+ alphaCells.get(x-1));
		}
		return alphaCells;
	}
}
