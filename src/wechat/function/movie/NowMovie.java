package wechat.function.movie;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.ArrayList;

import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class NowMovie {
	public ArrayList<Object> getMovieData(int movieNum, String cityString){
		ArrayList<Object> movieList = new ArrayList<Object>();
		Document doc;
		String [] movieName = new String[movieNum];
		String [] movieScore = new String[movieNum];
		String url = "http://movie.douban.com/nowplaying/kunming/";
		try {
			try{
			Connection con = Jsoup.connect(url).timeout(5000);	  
			con.header("Accept-Encoding", "gzip, deflate");   
			con.header("Connection", "keep-alive");   
			con.header("Host", url);   
			doc=con.get(); 
			//doc = Jsoup.connect(url + URLEncoder.encode(cityString, "UTF-8")).timeout(10000).get();
			for(int i=0; i<movieNum; i++){
				String strName = doc.select("ul.lists > li").get(i).attr("data-title"); //
				String strScore = doc.select("ul.lists > li").get(i).attr("data-score"); //
				
				movieName[i] = strName;
				movieScore[i] = strScore;
			}
			movieList.add(0,movieName);
			movieList.add(1,movieScore);
			} catch (HttpStatusException e){
				movieName[0] = "服务器异常！";
				movieScore[0] = e.getMessage();
				movieList.add(0,movieName);
				movieList.add(1,movieScore);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			movieName[0] = "服务器异常！";
			movieScore[0] = e.getMessage();
			movieList.add(0,movieName);
			movieList.add(1,movieScore);
		}
		return movieList;
	}
}
