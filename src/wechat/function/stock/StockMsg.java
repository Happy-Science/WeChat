package wechat.function.stock;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
public class StockMsg {
	public String getData(String stockCode){
		String result = "";
		String str = "";
		String [] content = null;
		try {
			URL url = new URL("http://hq.sinajs.cn/list=" + stockCode);   
	        HttpURLConnection conn = (HttpURLConnection) url.openConnection();    
	        conn.setRequestProperty("contentType", "GBK");   
	        conn.setConnectTimeout(5 * 1000);   
	        conn.setRequestMethod("GET");   
	        InputStream inStream = conn.getInputStream();   
	           
	        BufferedReader in = new BufferedReader(new InputStreamReader(inStream, "GBK"));   
	        StringBuffer buffer = new StringBuffer();   
	        String line = "";   
	        while ((line = in.readLine()) != null){   
	          buffer.append(line);   
	        }   
	        str = buffer.toString();   
	        str = str.substring(str.indexOf("\"")+1,str.lastIndexOf("\""));
	        content = str.split(",");
		} catch (IOException e) {
			e.printStackTrace();
		}
		if(stockCode.equals("s_sh000001") || stockCode.equals("s_sz399001")){
			 if(content.length == 6){
				 result = "指数名称：" + content[0] + "\n当前点数：" +  content[1] 
						 + "\n当前价格：" + content[2] + "\n涨跌率：" + content[3] 
						+ "\n成交量（手）：" + content[4] + "\n成交额（万元）：" + content[5];
			 }
		}else {
			if(content.length >= 32){
				 result = "股票名字：" + content[0] + "\n今日开盘价：" +  content[1] 
						 + "\n昨日收盘价：" + content[2] + "\n当前价格：" + content[3] 
						+ "\n今日最高价：" + content[4] + "\n今日最低价：" + content[5]
						+ "\n日期：" + content[30] + "\n时间：" + content[31];
			 }
		}
		return result;
	}
}
