package wechat.function.food;

import java.io.IOException;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class FoodList {

	public ArrayList<Object> getFoodList(String foodName){
		ArrayList<Object> foodList = new ArrayList<Object>();
		Document doc;
		String [] title = new String[5];
		String [] content = new String[5];
		String [] picUrl = new String[5];
		String [] url = new String[5];
		try {
			doc = Jsoup.connect("http://so.meishi.cc/index.php?q=" + foodName).timeout(10000).get();
			int size = doc.select("div.search2015_cpitem").size();
			for(int i=0; i<5&&5<=size; i++){
				//title
				title[i] = doc.select("a.cpn").get(i).text();
				//content
				String discribe = "";
				String temp[]=doc.select("span.i2").get(i).text().split("/");
				for(int j=0; j<temp.length; j++){
					discribe = discribe + "\n" + temp[j].trim();
				}
				content[i] = "由网友<" + doc.select("a.authorn").get(i).text() + ">提供" + 
						discribe;
				//picUrl
				picUrl[i] = doc.select("a.img").get(i).child(0).attr("src");
				//url
				url[i] = doc.select("a.img").get(i).attr("href");
			}
			foodList.add(title);
			foodList.add(content);
			foodList.add(picUrl);
			foodList.add(url);
		} catch (IOException e) {
			// TODO Auto-generated catch block

		}
		return foodList;
	}
}
