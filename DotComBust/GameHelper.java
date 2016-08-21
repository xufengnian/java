//δ��ȫע�ͣ��ȴ��´�
import java.util.*;
import java.io.*;
//����Ϸ��Χ�޶���7*7������֮�У�0��6�����У�a��g�����С�������Ϸ�ʽ����֣�����ǰ���ں󡣴�a0~g6�ֱ���0��48
public class GameHelper {
	private static final String alphabet = "abcdefg";
	private int gridLength = 7;//�������񳤶�
	private int gridSize = 49;//������������Ŀ
	private int[] grid = new int [gridSize];//����������������
	private int comCount=0;//ս����Ŵ�0��ʼ

	public String getUserInput(String prompt) {//��ʾ��ʾ��Ϣ����ȡ������룬������������ַ�ȫ��ת����Сд
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
	public ArrayList<String> placeDotCom(int comSize) {//����һ��ս�����������꣬comSizeΪս�����ȣ���ս������ֵ
		ArrayList<String> alphaCells = new ArrayList<String> ();//����һ���ַ�����ArrayList������һ��ս����λ������
		String [] alphacoords = new String [comSize];//����һ������Ϊ ս������ ���ַ������������ڴ��ս������
		String temp = null;//���ڱ��浥��ս��������һ�����꣬��Ӣ����ĸ����������磨a1,b2�ȣ�
		int[] coords = new int[comSize];//�洢����ս����λ�������������֮��ת�����ַ�������
		int attempts = 0;//��������ս���������
		boolean success = false;//����boolean�ͱ���success��ʾ�����Ƿ�ɹ�
		int location = 0;//ս����ʼλ�ã�Ĭ�ϴ�7*7�����0��λ�ÿ�ʼ
		
		comCount++;//ս���к����ݵĹؼ���ս����Ŵ�1��ʼ��ż�����ս��Ϊ�ݣ��������ս��Ϊ��
		int incr = 1;//ս��������������7*7�ķ����к��ս������һ����������Ϊ1���ݵ�ս������һ����������Ϊ7
		if ((comCount % 2) == 1){
			incr = gridLength;
		}
		
		while(!success & attempts < 200){//��ս��δ�����ҳ�ʶ����С��200����ʼѭ��
			location = (int) (Math.random()*gridSize);//һ��ս������ĳ�ʼ���ֵ����Χ��0��48֮��
			
			int x=0;//��¼������ĳһ��ս���� �ڼ���λ�ã�����������ս���ĳ�����3����x��Χ�Ǵ�0��2��
				success=true;//��ʼ�������������Ƚ�����״̬��Ϊtrue��������һ����������ж�
				while (success && x < comSize){//��ʼ����ɹ�������δ�ﵽս������ʱ��ʼѭ��
					if(grid[location] == 0){//�ж����ɵ�ս�������Ƿ��Ѿ���ʹ�ù�
						coords[x++] = location;//�洢λ��						
						location +=incr;//��ʼֵ��ʼ�������������һ��λ������
						if (location >= gridSize){//������������������������ʧ�ܣ���ֹ��Խ��
							success = false;
						}
						if (x > 0 && (location % gridLength == 0)){//������ɵ���λ�ã��ǵ�һ��λ�����꣩Ϊ7�ı�����������ʧ�ܣ�˵������ս�������ˣ�
							success = false;//�ţ���ʵ���ж���������ģ����������a0,a1,a2����a��һ�е�����ս�����ͻ�����ʧ�ܣ�����ȷʵ�Ƿ�ֹ��Խ��İ취
						}
					}
					else{//��������ɵ�ս�������֮ǰ��ս���ظ�������ʧ��
						
						success = false;
					}
				}	
		
		}
		int x = 0;//��x�������㣬���㴦��ÿһ��ս������
		int row = 0;
		int column = 0;
		//��ʼ��������Ϣ��������������λ����Ϣת�����ַ���	
		while (x < comSize){
			grid[coords[x]] = 1;//���Ѿ����ɵ�ս��������һ������λ���ѱ�ʹ��
			row = (int) (coords[x] / gridLength);//����Ϣֱ����  ս���������7ȡ��
			column = coords[x] % gridLength;//����Ϣ����ȡ ս���������7ȡ��������ֵ ��Ϊ����charAt����������ֵ
			temp = String.valueOf(alphabet.charAt(column));//������ʱ�ַ���temp�洢ת�����ַ��͵�����Ϣ
			
			alphaCells.add(temp.concat(Integer.toString(row)));//temp�ַ������� ת�����ַ��͵�����Ϣ������ӵ��ַ�����ArrayList��alphaCells�У�����һ��ս����λ������
			x++;//x������������һ��������Ϣ
			System.out.println("coords " +x+ " = "+ alphaCells.get(x-1));//���п�ע�ͣ�Ϊ�˷�����Զ���ʾս������λ��
		}
		return alphaCells;//�÷�������������һ��ս����3������
	}
}
