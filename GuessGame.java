//������С��Ϸ
//������Ϸ������1��0��9���������3�������������0��9����������в²⣬һ��������Ϸ���������������µ��������������ȥ
public class GuessGame {
	//����3��Player����
	Player p1;
	Player p2;
	Player p3;
	
	public void StartGame(){
		//����3���������Player�࣬��ʵҲ����ֱ��Player p1=new Player();
		p1=new Player();
		p2=new Player();
		p3=new Player();
		
	//�½�3����������3��Player�²��������ʼֵ��0	
	int guessp1=0;
	int guessp2=0;
	int guessp3=0;
	
	//�½�3������������3λPlayer�²�����󣬳�ʼֵΪfalse
	boolean p1isright=false;
	boolean p2isright=false;
	boolean p3isright=false;
	
	//���������
	int targetnumber=(int) (Math.random()*10);//ǿ��ת�����������Ҫע�� (Math.random()*10)�����Ų���ʡ�ԣ���������������Ϊ0�����ϵ���
	System.out.println("I think a number from 0 to 9...");
	
	while(true){
		//����Player���Guess��������������3λPlayer�²������
		p1.Guess();
		p2.Guess();
		p3.Guess();
		
		//��3λPlayer���ɵ����ֱַ���²�����
		guessp1=p1.number;
		System.out.println("Player1 guess number is "+guessp1);
		
		guessp2=p2.number;
		System.out.println("Player2 guess number is "+guessp2);
		
		guessp3=p3.number;
		System.out.println("Player3 guess number is "+guessp3);
		
		//�Ƚ�3λPlayer�Ĳ²��Ƿ���ȷ������ȷ������²�����Ϊtrue
		if (guessp1==targetnumber){
			p1isright=true;
		}
		if (guessp2==targetnumber){
			p2isright=true;
		}
		if (guessp3==targetnumber){
			p3isright=true;
		}
		
		//ֻҪ3λ����1λ�²�����ȷ���ɽ���if
		if(p1isright||p2isright||p3isright){
			System.out.println("Let's check 3 players's result...");
			System.out.println("Player1's number is right? "+p1isright);
			System.out.println("Player2's number is right? "+p2isright);
			System.out.println("Player3's number is right? "+p3isright);
			System.out.println("Game Over!");
			break;//����whileѭ��
		}
		else{
			System.out.println("Game Continue...");//�²������ȷ������Ϸ����
		}
		
	}
	
	}
	
	

}

class Player{//�˴�������public
	int number=0;//��ҳ�ʼ�²�������Ϊ0
	public void Guess(){
		number=(int) (Math.random()*10);//�������²��������ǳ��Բ²���
	}
	
}

class GameLauncher{
	public static void main(String[] args){
		//��������ڣ��½�GuessGame����
		GuessGame game=new GuessGame();
		//����StartGame��������Ϸ��ʼ
		game.StartGame();
	}
	
}
