/*简单的一个提问回答板小程序，通过逐行读取文本文件（每一行由问题和答案两部分组成，中间以/分开）
 用户先加载预先写好且满足条件的文本文件，加载完成后，点击show question按钮显示第一个问题
 然后按钮文字变成show answer，点击按钮显示与问题对应的答案
 然后按钮文字变成next question，点击按钮加载下一个问题...以此类推，直到没有问题了，显示That was last card，按钮不可用 
 */

import java.io.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

class QuizCard{//定义一下回答板小程序的类
	private String question;
	private String answer;
	public QuizCard(String q,String a){
		question = q;
		answer = a;
	}
	public String getQuestion(){
		return question;
	}
	public String getAnswer(){
		return answer;
	}
}

public class QuizCardPlayer {
	private JTextArea display;//声明文字显示区域
	private JTextArea answer;//本题程序未使用
	private ArrayList<QuizCard> cardList;//声明一组QuizCard对象的ArrayList
	private QuizCard currentCard;//声明一个QuizCard对象，作为当前的问答板
	private int currentCardIndex;//用于确定当前QuizCard的下标，配合cardList使用
	private JFrame frame;//程序主框架面板
	private JButton nextButton;//声明按钮
	private boolean isShowAnswer;//声明一个布尔型变量确定是否需要显示答案
	
	public static void main (String[] args){
		QuizCardPlayer reader = new QuizCardPlayer();
		reader.go();
	}

	public void go() {
		// TODO Auto-generated method stub
		frame = new JFrame("Quiz Card Player");//声明主框架，并将标题栏设置为“Quiz Card Player”
		JPanel mainPanel = new JPanel();//声明程序的主面板区域
		Font bigFont = new Font("sanserif",Font.BOLD,24);//设置bigFont字体格式，字体为sanserif，加粗，24号
		
		display = new JTextArea(10,20);//display设置为行高为10，字长为20的文本域
		display.setFont(bigFont);//display区的字体使用bigFont
		
		display.setLineWrap(true);//设置文本区的换行策略。如果设置为 true，则当行的长度大于所分配的宽度时，将换行。如果设置为 false，则始终不换行。
		display.setEditable(false);//设置指定的 boolean 变量，以指示此 TextComponent 是否应该为可编辑的。
		
		JScrollPane qScroller = new JScrollPane(display);//将滚动栏绑定到display区形成一个新的整体qScroller
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//设置滚动栏垂直方向滚动
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//设置水平方向上不动
		nextButton = new JButton("show Question");//初始化button上的文字是show Question
		mainPanel.add(qScroller);//主面板添加qScroller组件
		mainPanel.add(nextButton);//主面板添加nextButton组件
		nextButton.addActionListener(new NextCardListener());//nextButton组件注册给监听器，一旦触发建立NextCardListener对象
		
		JMenuBar menuBar = new JMenuBar();//声明菜单栏
		JMenu fileMenu = new JMenu("File");//新建一个菜单选项名为File
		JMenuItem loadMenuItem = new JMenuItem("Load card set");//新建一个菜单子项，名字为Load card set
		loadMenuItem.addActionListener(new OpenMenuListener());//loadMenuItem注册给监听器
		fileMenu.add(loadMenuItem);//File菜单项添加Load card set子项
		menuBar.add(fileMenu);//菜单栏添加File菜单选项
		frame.setJMenuBar(menuBar);//程序主框架设置menuBar为菜单栏
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//把窗口关闭的同时结束程序
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);//主菜单设置在CENTER区
		frame.setSize(640, 500);//框架大小为640*500
		frame.setVisible(true);//框架可见
	}
	public class NextCardListener implements ActionListener{//NextCardListener监听器类
		public void actionPerformed(ActionEvent ev){
			if (isShowAnswer){//如果isShowAnswer变量值为true，即问题已经出现了，答案没出现，此时按钮应为showAnswer
				display.setText(currentCard.getAnswer());//display区文字显示为当前QuizCard的Answer
				nextButton.setText("Next Card");//按钮文字变为Next Card
				isShowAnswer = false;//isShowAnswer变量值变为false,为了让Next Card和showAnswer按钮交替出现
			}else{//如果isShowAnswer变量值为false，要么显示的是答案，要么到了最后一个问题和答案已经显示过了
				if(currentCardIndex < cardList.size()){//显示的是答案，按钮变成显示下一个问题
					showNextCard();
				}else{//最后一个问题和答案已经显示过了
					display.setText("That was last card");//提示That was last card
					nextButton.setEnabled(false);//按钮变成不可用
				}
			}
		}
		
	}
	public class OpenMenuListener implements ActionListener{//OpenMenuListener
		public void actionPerformed(ActionEvent ev){
			JFileChooser fileOpen = new JFileChooser();//文件选择对象
			fileOpen.showOpenDialog(frame);//文件打开对话框
			loadFile(fileOpen.getSelectedFile());//loadFile方法加载打开文件
		}
		
	}
	private void loadFile(File file){
		cardList = new ArrayList<QuizCard> ();
		try{//通过缓冲区更加高效地读取文件的每一行
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null){//若该行不为空，则尝试将该行制作成问答板
				makeCard(line);
			}
			reader.close();
		}catch(Exception ex) {
			System.out.println("couldn't read the card file");//文件不是可读取的文件时，提示
			ex.printStackTrace();
		}
	}
	private void makeCard(String lineToParse){//将1行制作成问答板的方法
		String[] result = lineToParse.split("/");//声明result熟知，将1行的字符以/分割开来
		QuizCard card = new QuizCard(result[0],result[1]);//将分割的两部分一部分给question另外一部分给answer
		cardList.add(card);//cardList数组添加card
		System.out.println("made a card");//每成功读取1行文字并完成分割则显示made a card
	}
	private void showNextCard(){//下一张card
		currentCard = cardList.get(currentCardIndex);//从cardList中获取当前card
		currentCardIndex++;//下标加一
		display.setText(currentCard.getQuestion());//display区显示下一个问题
		nextButton.setText("Show Answer");//按钮变成Show Answer
		isShowAnswer = true;
	}
}
/*
 问题和答案的源文件可以是这样
who am i?/xfn
why hhm like eclipse?/Because he think it's very cool
how are you?/I'm fine,thank you and you?
*/