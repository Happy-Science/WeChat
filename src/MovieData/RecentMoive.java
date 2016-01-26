package MovieData;

import java.io.IOException;
import java.util.ArrayList;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class RecentMoive {
	public ArrayList<Object> getMovieData(int movieNum){
		ArrayList<Object> movieList = new ArrayList<Object>();
		Document doc;
		String [] movieName = new String[movieNum];
		String [] movieDate = new String[movieNum];
		try {
			doc = Jsoup.connect("http://movie.mtime.com/recent").timeout(10000).get();
			String thisDate = "";
			for(int i=0; i<movieNum; i++){
				String strName = doc.select("h3.pt15 > a").get(i).text(); //
				String strDate = doc.select("span.date").get(i).text(); //

				if(!strDate.equals("")){
					thisDate = strDate;
				}
				
				if(strName.contains(" ")){
					movieName[i] = strName.substring(0, strName.indexOf(" "));
				}
				
				if(strDate.equals("")){
					movieDate[i] = thisDate;
				}else{
					movieDate[i] = strDate;
				}
			}
			movieList.add(0,movieName);
			movieList.add(1,movieDate);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return movieList;
	}
}
