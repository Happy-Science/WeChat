package wechat.function.movie;

//import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

//import org.jsoup.Connection;
//import org.jsoup.HttpStatusException;
//import org.jsoup.Jsoup;
//import org.jsoup.nodes.Document;

import wechat.db.DBConnector;

public class NowMovie {
	public ArrayList<Object> getMovieData(int movieNum, String cityString){
		ArrayList<Object> movieList = new ArrayList<Object>();
//		Document doc;
//		String [] movieName = new String[movieNum];
//		String [] movieScore = new String[movieNum];
//		String url = "http://movie.douban.com/nowplaying/kunming/";
//		try {
//			try{
//			Connection con = Jsoup.connect(url).timeout(30000);	  
//			con.header("Accept-Encoding", "gzip, deflate");   
//			con.header("Connection", "keep-alive");   
//			con.header("Host", url);   
//			doc=con.get(); 
//			//doc = Jsoup.connect(url + URLEncoder.encode(cityString, "UTF-8")).timeout(10000).get();
//			for(int i=0; i<movieNum; i++){
//				String strName = doc.select("ul.lists > li").get(i).attr("data-title"); //
//				String strScore = doc.select("ul.lists > li").get(i).attr("data-score"); //
//				
//				movieName[i] = strName;
//				movieScore[i] = strScore;
//			}
//			movieList.add(0,movieName);
//			movieList.add(1,movieScore);
//			} catch (HttpStatusException e){
//				movieName[0] = "服务器异常！";
//				movieScore[0] = e.getMessage();
//				movieList.add(0,movieName);
//				movieList.add(1,movieScore);
//			}
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			movieName[0] = "服务器异常！";
//			movieScore[0] = e.getMessage();
//			movieList.add(0,movieName);
//			movieList.add(1,movieScore);
//		}
		
		String sql = "select * from NowMovie order by movieId desc limit 15";
    	DBConnector db1 = new DBConnector(sql);//创建DB对象  
    	ResultSet ret = null;
    	try {  
    		ret = db1.pst.executeQuery();//执行语句，得到结果集 
         	String [] movieName = new String[15];
    		String [] movieScore = new String[15];
    		String [] movieName1 = new String[movieNum];
    		String [] movieScore1 = new String[movieNum];
    		int num = 14;
         	while (ret.next()) {
         		movieName[num] = ret.getString(2);
     			movieScore[num] = ret.getFloat(3) + "";
     			num--;
         	}//显示数据 
         	for(int i=0; i<movieNum; i++){
         		movieName1[i] = movieName[i];
         		movieScore1[i] = movieScore[i];
         	}
         	movieList.add(0,movieName1);
         	movieList.add(1,movieScore1);
//         	for(int i=0; i<15; i++){
//         		System.out.println(movieName[i]+","+movieScore[i]);
//         	}
    	} catch (SQLException e) {  
    		e.printStackTrace();  
    	}finally { 
    		db1.close(); 
    	}
		return movieList;
	}
}
