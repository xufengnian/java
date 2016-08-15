
public class GuessGame {
	Player p1;
	Player p2;
	Player p3;
	
	public void StartGame(){
		p1=new Player();
		p2=new Player();
		p3=new Player();
		
	int guessp1=0;
	int guessp2=0;
	int guessp3=0;
	
	boolean p1isright=false;
	boolean p2isright=false;
	boolean p3isright=false;
	
	int targetnumber=(int) Math.random()*10;
	System.out.println("I think a number from 0 to 9...");
	
	while(true){
		p1.Guess();
		p2.Guess();
		p3.Guess();
		
		guessp1=p1.number;
		System.out.println("Player1 guess number is"+guessp1);
		
		guessp2=p2.number;
		System.out.println("Player2 guess number is"+guessp2);
		
		guessp3=p3.number;
		System.out.println("Player3 guess number is"+guessp3);
		
		if (guessp1==targetnumber){
			p1isright=true;
		}
		if (guessp2==targetnumber){
			p2isright=true;
		}
		if (guessp3==targetnumber){
			p3isright=true;
		}
		
		if(p1isright||p2isright||p3isright){
			System.out.println("Let's check 3 players's result...");
			System.out.println("Player1's number is right?"+p1isright);
			System.out.println("Player2's number is right?"+p2isright);
			System.out.println("Player3's number is right?"+p3isright);
			System.out.println("Game Over!");
			break;
		}
		else{
			System.out.println("Game Continue...");
		}
		
	}
	
	}
	
	

}

class Player{
	int number=0;
	public void Guess(){
		number=(int) Math.random()*10;		
	}
	
}

class GameLauncher{
	public static void main(String[] args){
		GuessGame game=new GuessGame();
		game.StartGame();
	}
	
}
