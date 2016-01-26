package MovieData;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class NowMovie {
	public ArrayList<Object> getMovieData(int movieNum, String cityString){
		ArrayList<Object> movieList = new ArrayList<Object>();
		Document doc;
		String [] movieName = new String[movieNum];
		String [] movieScore = new String[movieNum];
		try {
			doc = Jsoup.connect("http://movie.douban.com/nowplaying/" + cityString + "/").timeout(10000).get();
			for(int i=0; i<movieNum; i++){
				String strName = doc.select("ul.lists > li").get(i).attr("data-title"); //
				String strScore = doc.select("ul.lists > li").get(i).attr("data-score"); //
				
				movieName[i] = strName;
				movieScore[i] = strScore;
			}
			movieList.add(0,movieName);
			movieList.add(1,movieScore);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return movieList;
	}
}
