//猜数字小游戏
//规则：游戏先生成1个0到9的随机数，3个玩家轮流生成0到9的随机数进行猜测，一旦猜中游戏结束，否则生成新的随机数继续猜下去
public class GuessGame {
	//创建3个Player对象
	Player p1;
	Player p2;
	Player p3;
	
	public void StartGame(){
		//创建3个对象基于Player类，其实也可以直接Player p1=new Player();
		p1=new Player();
		p2=new Player();
		p3=new Player();
		
	//新建3个整数保存3个Player猜测的数，初始值是0	
	int guessp1=0;
	int guessp2=0;
	int guessp3=0;
	
	//新建3个布尔数保存3位Player猜测的正误，初始值为false
	boolean p1isright=false;
	boolean p2isright=false;
	boolean p3isright=false;
	
	//生成随机数
	int targetnumber=(int) (Math.random()*10);//强制转换随机数，需要注意 (Math.random()*10)的括号不能省略，否则所有生成数为0，左结合导致
	System.out.println("I think a number from 0 to 9...");
	
	while(true){
		//调用Player类的Guess方法，用于生成3位Player猜测的数字
		p1.Guess();
		p2.Guess();
		p3.Guess();
		
		//将3位Player生成的数字分别赋予猜测数字
		guessp1=p1.number;
		System.out.println("Player1 guess number is "+guessp1);
		
		guessp2=p2.number;
		System.out.println("Player2 guess number is "+guessp2);
		
		guessp3=p3.number;
		System.out.println("Player3 guess number is "+guessp3);
		
		//比较3位Player的猜测是否正确，若正确，则将其猜测结果置为true
		if (guessp1==targetnumber){
			p1isright=true;
		}
		if (guessp2==targetnumber){
			p2isright=true;
		}
		if (guessp3==targetnumber){
			p3isright=true;
		}
		
		//只要3位中有1位猜测结果正确，可进此if
		if(p1isright||p2isright||p3isright){
			System.out.println("Let's check 3 players's result...");
			System.out.println("Player1's number is right? "+p1isright);
			System.out.println("Player2's number is right? "+p2isright);
			System.out.println("Player3's number is right? "+p3isright);
			System.out.println("Game Over!");
			break;//跳出while循环
		}
		else{
			System.out.println("Game Continue...");//猜测均不正确，则游戏继续
		}
		
	}
	
	}
	
	

}

class Player{//此处不能有public
	int number=0;//玩家初始猜测数，置为0
	public void Guess(){
		number=(int) (Math.random()*10);//玩家随机猜测数，覆盖初试猜测数
	}
	
}

class GameLauncher{
	public static void main(String[] args){
		//主函数入口，新建GuessGame对象
		GuessGame game=new GuessGame();
		//调用StartGame方法，游戏开始
		game.StartGame();
	}
	
}
