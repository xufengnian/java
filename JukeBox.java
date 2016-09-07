//�򵥵İ���ĸ����С����������ļ�������Ŀ¼�µ�SongList.txt����SongList.txt�У�ÿһ�з�4�����֣���/�ŷָ
//��������ݾ������Ĳ����е�һ���ֵ���ĸ
import java.util.*;
import java.io.*;

class Song implements Comparable<Song>{//����̳�Comparable�ӿ�
	String title;
	String artist;
	String rating;
	String bpm;
	public int compareTo(Song s){//Comparable�ӿڱ�����д�ķ���compareTo
		return title.compareTo(s.getTitle());
	}
	Song(String t,String a,String r,String b){
		title = t;
		artist = a;
		rating = r;
		bpm = b;		
	}
	public String getTitle() {
		return title;
	}
	public String getArtist(){
		return artist;
	}
	public String getRating(){
		return rating;
	}
	public String getBpm(){
		return bpm;
	}
	
	public String toString(){//ÿ�����ж��е�һ�������������˵�����System.out.println(ObjectName)ʱ�����������
		return title;
	}
}

public class JukeBox  {
	ArrayList<Song> songList = new ArrayList<Song>();
	public static void main(String[] args){
		new JukeBox().go();
	}
	public void go(){
		getSongs();
		System.out.println(songList);
		Collections.sort(songList);//������sort()�������Ժ�������󼯺ϵ�ArrayList��������
		System.out.println(songList);//���������Song����ļ���songList�����������Song���е�toString()��������
	}
	void getSongs(){
		try{
			File file = new File("SongList.txt");//������ļ��������½�һ��SongList.txt�ļ���ÿһ�и�ʽ����Ϊ��sdfs/sdd/dddd/88
			BufferedReader reader = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = reader.readLine())!=null){
				addSong(line);
			}
			}catch(Exception ex){
				ex.printStackTrace();
		}
	}
	void addSong(String lineToParse){
		String[] tokens = lineToParse.split("/");
		
		Song nextSong = new Song(tokens[0],tokens[1],tokens[2],tokens[3]);//�ָ��Ľ��������Song��Ĺ��캯��Song()���ֱ�ֵ��Song���µ�4��ʵ���������γ�һ���µĶ���
		songList.add(nextSong);//�����ɵ��¶�����ӵ�SongList��
	}

}
