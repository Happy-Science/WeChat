package wechat.function.weather;

import net.sf.json.JSONObject;

//地区编号查询服务端数据库，没有的由地区拼音通过 API查询，并存入服务端数据库。
//http://www.weather.com.cn/data/sk/101010100.html
//http://apistore.baidu.com/microservice/cityinfo?cityname=
//http://apistore.baidu.com/microservice/weather?cityname=%E5%8C%97%E4%BA%AC
public class Weather_Run {
	public static void main(String args[]){
		NowWeather nw = new NowWeather();
		JSONObject  json = new JSONObject();
		String cityName = "昆";
		json = nw.getData(cityName);
		
		String errorNum = json.getString("errNum");
		String errorMsg = json.getString("errMsg");
		String city="";
		String weather="";
		String temp="";
		String high_tmp="";
		String low_tmp="";
		String windDirection="";
    	String windSpeed="";
    	String sunRiseTime="";
    	String sunSetTime="";
    	
    	if(errorNum.equals("0")){
    		city = json.getJSONObject("retData") .getString("city");
    		weather = json.getJSONObject("retData") .getString("weather");
    		temp = json.getJSONObject("retData") .getString("temp");
    		high_tmp = json.getJSONObject("retData") .getString("h_tmp");
    		low_tmp = json.getJSONObject("retData") .getString("l_tmp");
    		windDirection = json.getJSONObject("retData") .getString("WD");
        	windSpeed = json.getJSONObject("retData") .getString("WS");
        	sunRiseTime = json.getJSONObject("retData") .getString("sunrise");
        	sunSetTime = json.getJSONObject("retData") .getString("sunset");
    	} else {
    		System.out.println("错误：" + errorMsg);
    	} 
    	System.out.println("城市：" + city);
    	System.out.println("天气：" + weather);
    	System.out.println("当前温度：" + temp);
    	System.out.println("气温变化：" + low_tmp + "~" + high_tmp);
    	System.out.println("风向：" + windDirection);
    	System.out.println("风速：" + windSpeed);
    	System.out.println("日出时间：" + sunRiseTime);
    	System.out.println("日落时间：" + sunSetTime);
	}
}
