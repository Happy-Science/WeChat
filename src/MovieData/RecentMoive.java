package MovieData;

import java.io.File;
import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

public class RecentMoive {
	public void getMovieData(){
		Document doc;
		String [] movieName = new String[10];
		String [] movieDate = new String[10];
		try {
			doc = Jsoup.connect("http://movie.mtime.com/recent").get();
			//Element movieName = doc.select("class.px14").first(); 
//			Elements movieName = doc.getElementsByClass("px14");
			for(int i=0; i<10; i++){
				String strName = doc.select("h3.pt15 > a").get(i).text(); //
				String strDate = doc.select("span.date").get(i).text(); //
				if(strName.contains(" ")){
					movieName[i] = strName.substring(0, strName.indexOf(" "));
					movieDate[i] = strDate;
					if(strDate.equals("")){
						strDate = "暂无";
					}
					System.out.println("电影名称：" + movieName[i]);
					System.out.println("上映日期：" + strDate);
					System.out.println();

				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
	}
}
