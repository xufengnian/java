//����ս���򵥰�С��Ϸ
//������Ϸ�������0��6֮�������3�����֣����ÿ������һ�����֣���ǡ�����������ɵ������������ʾhit,��֮��ʾmiss.
//3������hit,����Ϸ��������ʾkill������ʾ��Ҳ²�Ĵ���
import java.io.*;//����io�⣬���ڴ����������
public class SimpleDotComGame {//��Ϸ������������
	public static void main(String[] args){
		int numOfGuess=0;//��Ҳ²���
		GameHelper helper=new GameHelper();//������Ϸ�����������ں���ҽ����������������������
		
		SimpleDotCom theDotCom=new SimpleDotCom();//������Ϸ������󣬸����ж�����������ֵ�����ת�����ж����������ж����
		int randomNum=(int) (Math.random()*5);//���ɳ�ʼ���������0��4
		
		int[] locations={randomNum,randomNum+1,randomNum+2};//����3�����������������
		theDotCom.setlocationCells(locations);//����set������3�����������theDotCom��
		boolean isAlive=true;//����isAlive��������ѭ�������ж�
		
		while(isAlive==true){//true���������δ������Ϸ��ս��δkill
			String guess=helper.getUserInput("enter a number");//��GameHelper����ʾ����ʾ��ϢΪ��enter a number��
			String result=theDotCom.CheckYourself(guess);//����theDotCom���CheckYourself�����ж���ҵ���������
			numOfGuess++;//����������ֺ󣬲²�����һ
			if(result.equals("kill")){//�����ؽ��Ϊkill,˵��ս���ѳ�û����ʾ��ҵĲ²���
				isAlive=false;
				System.out.println("You took "+numOfGuess+" guesses");
			}
		}
	}

}

class SimpleDotCom {//��Ϸ�߼������ж���
	int[] locationCells;//�����������ڴ��ս������λ��
	int numOfHits=0;//����int�������ս��������Ĵ���
	
	public void setlocationCells (int[] locs ){//set������������鸳��ս��λ������
		locationCells=locs;		
	}
	
	public String CheckYourself (String UserGuess){//�ж�
		int guess=Integer.parseInt(UserGuess);//ǿ�ƽ���������ַ�������ת����������
		String result="miss";//Ĭ���ж�Ϊmiss
		for (int cell :locationCells){//��ǿ��forѭ�����൱��for(int cell,cell<locationCells.length(),cell++)������ս������λ��
			if(cell==guess){//���ս������λ�ú���Ҳ²�һ�£���ʾ���hit����������һ
				result="hit";
				numOfHits++;
				break;
			}
		}
		if (numOfHits==locationCells.length)//����һ���������ս�����ȣ���ʾkill
		{
			result="kill";
		}
		System.out.println(result);
		return result;//���ؽ��
	}
}

class GameHelper {//��ʾ��ʾ��Ϣ�����������������
	public String getUserInput (String prompt){//����Ҫ��ʾ��������ʾ����
		String inputLine=null;//���������������
		System.out.print(prompt+" ");
		try{//���������������֣���δ���룬���׳��쳣������
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
//bug:���������3������ͬһ�������꣬ս�����û���߼�����Ӧ�ü�ʱɾ�������е�����
//level up:��һ����7*7�Ķ�ά������������ɶ��ս�������к�����
