import java.util.*;

public class DotCom {
	private ArrayList<String> locationCells;
	private String name;
	
	public void setName(String n){//ս�����Ʒ���
		name=n;
	}

	public void setLocationCells(ArrayList<String> loc) {//��GameHelper���placeDotCom�������ɵ�ս������λ�ø���String��ArrayList---locationCells
		locationCells=loc;
		
	}

	public String checkYourself(String userInput) {//�ж�����������
		String result = "miss";
		int index=locationCells.indexOf(userInput);
		if (index >=0){
			locationCells.remove(index);//���������ɾ����Ԫ�ط�ֹ������������
		
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
