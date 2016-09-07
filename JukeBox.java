//简单的按字母排序小程序，排序的文件夹是类目录下的SongList.txt。在SongList.txt中，每一行分4个部分，以/号分割开
//排序的依据就是这四部分中第一部分的字母
import java.util.*;
import java.io.*;

class Song implements Comparable<Song>{//主类继承Comparable接口
	String title;
	String artist;
	String rating;
	String bpm;
	public int compareTo(Song s){//Comparable接口必需重写的方法compareTo
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
	
	public String toString(){//每个类中都有的一个方法，决定了当对象被System.out.println(ObjectName)时，输出的内容
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
		Collections.sort(songList);//单参数sort()方法，对含主类对象集合的ArrayList进行排序
		System.out.println(songList);//输出含主类Song对象的集合songList，输出内容由Song类中的toString()方法决定
	}
	void getSongs(){
		try{
			File file = new File("SongList.txt");//类的主文件夹下先新建一个SongList.txt文件，每一行格式大致为：sdfs/sdd/dddd/88
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
		
		Song nextSong = new Song(tokens[0],tokens[1],tokens[2],tokens[3]);//分割后的结果，根据Song类的构造函数Song()，分别赋值给Song类下的4个实例变量，形成一个新的对象
		songList.add(nextSong);//将生成的新对象添加到SongList中
	}

}
