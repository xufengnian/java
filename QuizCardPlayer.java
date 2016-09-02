/*�򵥵�һ�����ʻش��С����ͨ�����ж�ȡ�ı��ļ���ÿһ��������ʹ���������ɣ��м���/�ֿ���
 �û��ȼ���Ԥ��д���������������ı��ļ���������ɺ󣬵��show question��ť��ʾ��һ������
 Ȼ��ť���ֱ��show answer�������ť��ʾ�������Ӧ�Ĵ�
 Ȼ��ť���ֱ��next question�������ť������һ������...�Դ����ƣ�ֱ��û�������ˣ���ʾThat was last card����ť������ 
 */

import java.io.*;
import javax.swing.*;
import java.util.*;
import java.awt.*;
import java.awt.event.*;

class QuizCard{//����һ�»ش��С�������
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
	private JTextArea display;//����������ʾ����
	private JTextArea answer;//�������δʹ��
	private ArrayList<QuizCard> cardList;//����һ��QuizCard�����ArrayList
	private QuizCard currentCard;//����һ��QuizCard������Ϊ��ǰ���ʴ��
	private int currentCardIndex;//����ȷ����ǰQuizCard���±꣬���cardListʹ��
	private JFrame frame;//������������
	private JButton nextButton;//������ť
	private boolean isShowAnswer;//����һ�������ͱ���ȷ���Ƿ���Ҫ��ʾ��
	
	public static void main (String[] args){
		QuizCardPlayer reader = new QuizCardPlayer();
		reader.go();
	}

	public void go() {
		// TODO Auto-generated method stub
		frame = new JFrame("Quiz Card Player");//��������ܣ���������������Ϊ��Quiz Card Player��
		JPanel mainPanel = new JPanel();//������������������
		Font bigFont = new Font("sanserif",Font.BOLD,24);//����bigFont�����ʽ������Ϊsanserif���Ӵ֣�24��
		
		display = new JTextArea(10,20);//display����Ϊ�и�Ϊ10���ֳ�Ϊ20���ı���
		display.setFont(bigFont);//display��������ʹ��bigFont
		
		display.setLineWrap(true);//�����ı����Ļ��в��ԡ��������Ϊ true�����еĳ��ȴ���������Ŀ��ʱ�������С��������Ϊ false����ʼ�ղ����С�
		display.setEditable(false);//����ָ���� boolean ��������ָʾ�� TextComponent �Ƿ�Ӧ��Ϊ�ɱ༭�ġ�
		
		JScrollPane qScroller = new JScrollPane(display);//���������󶨵�display���γ�һ���µ�����qScroller
		qScroller.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);//���ù�������ֱ�������
		qScroller.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);//����ˮƽ�����ϲ���
		nextButton = new JButton("show Question");//��ʼ��button�ϵ�������show Question
		mainPanel.add(qScroller);//��������qScroller���
		mainPanel.add(nextButton);//��������nextButton���
		nextButton.addActionListener(new NextCardListener());//nextButton���ע�����������һ����������NextCardListener����
		
		JMenuBar menuBar = new JMenuBar();//�����˵���
		JMenu fileMenu = new JMenu("File");//�½�һ���˵�ѡ����ΪFile
		JMenuItem loadMenuItem = new JMenuItem("Load card set");//�½�һ���˵��������ΪLoad card set
		loadMenuItem.addActionListener(new OpenMenuListener());//loadMenuItemע���������
		fileMenu.add(loadMenuItem);//File�˵������Load card set����
		menuBar.add(fileMenu);//�˵������File�˵�ѡ��
		frame.setJMenuBar(menuBar);//�������������menuBarΪ�˵���
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);//�Ѵ��ڹرյ�ͬʱ��������
		frame.getContentPane().add(BorderLayout.CENTER, mainPanel);//���˵�������CENTER��
		frame.setSize(640, 500);//��ܴ�СΪ640*500
		frame.setVisible(true);//��ܿɼ�
	}
	public class NextCardListener implements ActionListener{//NextCardListener��������
		public void actionPerformed(ActionEvent ev){
			if (isShowAnswer){//���isShowAnswer����ֵΪtrue���������Ѿ������ˣ���û���֣���ʱ��ťӦΪshowAnswer
				display.setText(currentCard.getAnswer());//display��������ʾΪ��ǰQuizCard��Answer
				nextButton.setText("Next Card");//��ť���ֱ�ΪNext Card
				isShowAnswer = false;//isShowAnswer����ֵ��Ϊfalse,Ϊ����Next Card��showAnswer��ť�������
			}else{//���isShowAnswer����ֵΪfalse��Ҫô��ʾ���Ǵ𰸣�Ҫô�������һ������ʹ��Ѿ���ʾ����
				if(currentCardIndex < cardList.size()){//��ʾ���Ǵ𰸣���ť�����ʾ��һ������
					showNextCard();
				}else{//���һ������ʹ��Ѿ���ʾ����
					display.setText("That was last card");//��ʾThat was last card
					nextButton.setEnabled(false);//��ť��ɲ�����
				}
			}
		}
		
	}
	public class OpenMenuListener implements ActionListener{//OpenMenuListener
		public void actionPerformed(ActionEvent ev){
			JFileChooser fileOpen = new JFileChooser();//�ļ�ѡ�����
			fileOpen.showOpenDialog(frame);//�ļ��򿪶Ի���
			loadFile(fileOpen.getSelectedFile());//loadFile�������ش��ļ�
		}
		
	}
	private void loadFile(File file){
		cardList = new ArrayList<QuizCard> ();
		try{//ͨ�����������Ӹ�Ч�ض�ȡ�ļ���ÿһ��
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while ((line = reader.readLine()) != null){//�����в�Ϊ�գ����Խ������������ʴ��
				makeCard(line);
			}
			reader.close();
		}catch(Exception ex) {
			System.out.println("couldn't read the card file");//�ļ����ǿɶ�ȡ���ļ�ʱ����ʾ
			ex.printStackTrace();
		}
	}
	private void makeCard(String lineToParse){//��1���������ʴ��ķ���
		String[] result = lineToParse.split("/");//����result��֪����1�е��ַ���/�ָ��
		QuizCard card = new QuizCard(result[0],result[1]);//���ָ��������һ���ָ�question����һ���ָ�answer
		cardList.add(card);//cardList�������card
		System.out.println("made a card");//ÿ�ɹ���ȡ1�����ֲ���ɷָ�����ʾmade a card
	}
	private void showNextCard(){//��һ��card
		currentCard = cardList.get(currentCardIndex);//��cardList�л�ȡ��ǰcard
		currentCardIndex++;//�±��һ
		display.setText(currentCard.getQuestion());//display����ʾ��һ������
		nextButton.setText("Show Answer");//��ť���Show Answer
		isShowAnswer = true;
	}
}
/*
 ����ʹ𰸵�Դ�ļ�����������
who am i?/xfn
why hhm like eclipse?/Because he think it's very cool
how are you?/I'm fine,thank you and you?
*/