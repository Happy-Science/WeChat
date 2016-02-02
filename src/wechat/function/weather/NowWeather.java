package wechat.function.weather;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

import net.sf.json.JSONObject;

public class NowWeather {
	 
	public JSONObject getData(String cityName){
		JSONObject  json = new JSONObject();
		URLConnection connection = null;
		try {
			connection = new URL("http://apistore.baidu.com/microservice/weather?cityname=" + cityName).openConnection();
			connection.connect();

			InputStream fin = connection.getInputStream();
			BufferedReader br = new BufferedReader(new InputStreamReader(fin));
			StringBuffer buffer = new StringBuffer();
			String r = null;
			while ((r = br.readLine()) != null) {
				buffer.append(r);
			}
			json = JSONObject.fromObject(buffer.toString());  
		} catch (IOException e) {
			e.printStackTrace();
		}
		return json;
	}
}
