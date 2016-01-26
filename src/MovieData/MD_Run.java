package MovieData;

import java.util.ArrayList;

//每日获取2次影讯，保存在服务端，提高响应速度
public class MD_Run {
	public static void main(String args[]){
		int movieNum = 10;
		ArrayList<Object> movieList = new ArrayList<Object>();
/////////////////即将上映/////////////////////////
		RecentMoive rm = new RecentMoive();
		movieList = rm.getMovieData(movieNum);
		String[] movieName = (String[])(movieList.get(0));
		String[] movieScore = (String[])(movieList.get(1));
		System.out.println("——————即将上映——————");
		for(int i=0; i<movieName.length; i++){
			System.out.println((i+1) + ".电影名称：" + movieName[i]);
			System.out.println("上映时间：" + movieScore[i]);
			System.out.println();
		}

/////////////////正在热映/////////////////////////
//		NowMovie nm = new NowMovie();
//		movieList = nm.getMovieData(movieNum, "kunming");
//		String[] movieName = (String[])(movieList.get(0));
//		String[] movieScore = (String[])(movieList.get(1));
//		
//		System.out.println("——————正在热映——————");
//		for(int i=0; i<movieName.length; i++){
//			System.out.println((i+1) + ".电影名称：" + movieName[i]);
//			System.out.println("豆瓣评分：" + movieScore[i]);
//			System.out.println();
//		}
		
		
	}
}
