import java.util.*;

public class DotCom {
	private ArrayList<String> locationCells;
	private String name;
	
	public void setName(String n){//战舰名称方法
		name=n;
	}

	public void setLocationCells(ArrayList<String> loc) {//将GameHelper类的placeDotCom方法生成的战舰坐标位置赋予String型ArrayList---locationCells
		locationCells=loc;
		
	}

	public String checkYourself(String userInput) {//判定玩家命中情况
		String result = "miss";
		int index=locationCells.indexOf(userInput);
		if (index >=0){
			locationCells.remove(index);//玩家命中则删除该元素防止连续输入作弊
		
			if (locationCells.isEmpty()){
				result = "kill";
				System.out.println("Oh! You sunk "+name+"  :(");
			}else{
				result="hit";
					}
		}
		return result;
	}
}
