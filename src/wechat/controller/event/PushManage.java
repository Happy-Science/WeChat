package wechat.controller.event;

	import java.io.IOException;  
	import java.io.InputStream;  
	import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;  
	import java.util.List;  
	import org.jdom2.Document;  
	import org.jdom2.Element;  
	import org.jdom2.JDOMException;  
	import org.jdom2.input.SAXBuilder;  
	import org.jdom2.output.XMLOutputter;

import net.sf.json.JSONObject;
import wechat.function.LOL.PlayerInfo;
import wechat.function.food.FoodList;
import wechat.function.movie.NowMovie;
import wechat.function.movie.RecentMoive;
import wechat.function.stock.StockImg;
import wechat.function.stock.StockMsg;
import wechat.function.weather.NowWeather;  
	  
	public class PushManage { 
		String inputString = "";
	      
	    @SuppressWarnings("unchecked")
		public String PushManageXml(InputStream is) throws JDOMException {  
	  
	        String returnStr = ""; // 反回Servlet字符串  
	        String toName = ""; // 开发者微信号  
	        String fromName = ""; // 发送方帐号（一个OpenID）  
	        String type = ""; // 请求类型  
	        String con = ""; // 消息内容(接收)  
	        String event = ""; // 自定义按钮事件请求  
	        String location_X = ""; // 自定义按钮事件请求 
	        String location_Y = ""; // 自定义按钮事件请求 
	        String defaultStr = "1.查询正在热映（即将上映）的电影请输入：正在热映（即将上映）+查询数量 【例如：正在热映10】"
            		+ "\n2.查询天气请输入：地名+'天气'【例如：北京天气】"
					+ "\n2.查询菜谱请输入：'菜谱'+菜名【例如：菜谱红烧牛肉】"
					+ "\n4.查询LOL输入：'lol'+服务器名+'&'+游戏ID【例如：lol艾欧尼亚&玩家名】"
					+ "\n5.查询股票信息 输入：'股票'+股票代码【例如：sh123456】(查询大盘指数需在股票代码前加上's_')"
					+ "\n6.查询股票K线图 输入：'分时线（日K线、周K线、月K线）'+股票代码【例如：日K线sh123456】";
	        try {  
	  
	            SAXBuilder sax = new SAXBuilder();  
	            Document doc = sax.build(is);  
	            // 获得文件的根元素  
	            Element root = doc.getRootElement();  
	  
	            // 获得根元素的第一级子节点  
	            List<?> list = root.getChildren();  
	            for (int j = 0; j < list.size(); j++) {  
	                // 获得结点  
	                Element first = (Element) list.get(j);  
	  
	                if (first.getName().equals("ToUserName")) {  
	                    toName = first.getValue().trim();  
	                } else if (first.getName().equals("FromUserName")) {  
	                    fromName = first.getValue().trim();  
	                } else if (first.getName().equals("MsgType")) {  
	                    type = first.getValue().trim();  
	                } else if (first.getName().equals("Content")) {  
	                    con = first.getValue().trim();  
	                    inputString = con;
	                } else if (first.getName().equals("Event")) {  
	                    event = first.getValue().trim();  
	                } else if (first.getName().equals("EventKey")) {  
	                }  
	                else if (first.getName().equals("Location_X")) {  
	                	location_X = first.getValue().trim();
	                }
	                else if (first.getName().equals("Location_Y")) {  
	                	location_Y = first.getValue().trim();
	                }
	            }  
	           
	          
	        if (type.equals("event")) {     //此为事件  
	            if (event.equals("subscribe")) {// 此为 关注事件  
	            	returnStr = getBackXMLTypeText(toName,fromName, "感谢您关注【平静的海】.\n微信号：jamuklee");
	            } else if (event.equals("unsubscribe")) { //此为取消关注事件   
	            	
	            }   
	        } else if (type.equals("text")) { // 此为 文本信息  
	        	if(con.length()<=6 && con.length()>=4 && con.contains("映")){
	        		if(con.length() == 4){
	        			if (con.equals("即将上映")) { // 此为 文本信息  
	        				int movieNum = 8;
	        				ArrayList<Object> movieList = new ArrayList<Object>();
	        				RecentMoive rm = new RecentMoive();
	        				movieList = rm.getMovieData(movieNum);
	        				String[] movieName = (String[])(movieList.get(0));
	        				String[] movieScore = (String[])(movieList.get(1));
	        				String re = "";
	        				re = "———即将上映———";
	        				for(int i=0; i<movieName.length; i++){
	        					re = re + "\n(" + (i+1) + ").电影名称：" + movieName[i] +
	        					"\n上映时间：" + movieScore[i];
	        				}
	        				returnStr = getBackXMLTypeText(toName,fromName, re);  
	        			} else if (con.equals("正在热映")) { // 此为 文本信息  
	        				int movieNum = 8;
	        				ArrayList<Object> movieList = new ArrayList<Object>();
	        				NowMovie nm = new NowMovie();
	        				String re = "";
	        				movieList = nm.getMovieData(movieNum, "kunming");//昆明
	        				String[] movieName = (String[])(movieList.get(0));
	        				String[] movieScore = (String[])(movieList.get(1));
	        				re = "———正在热映———";
	        				for(int i=0; i<movieName.length; i++){
	        					re = re + "\n(" + (i+1) + ").电影名称：" + movieName[i] +
			        					"\n豆瓣评分：" + movieScore[i];
	        				}
	        				returnStr = getBackXMLTypeText(toName,fromName, re);   
	        			} else {
	        				returnStr = getBackXMLTypeText(toName,fromName,defaultStr);
	        			}
	        		}else{
	        			String left = con.substring(0, 4);
	        			String right = con.substring(4, con.length());
	        			if (left.equals("即将上映")) { // 此为 文本信息  
	        				int movieNum = Integer.parseInt(right);
	        				ArrayList<Object> movieList = new ArrayList<Object>();
	        				RecentMoive rm = new RecentMoive();
	        				movieList = rm.getMovieData(movieNum);
	        				String[] movieName = (String[])(movieList.get(0));
	        				String[] movieDate = (String[])(movieList.get(1));
	        				String re = "";
	        				re = "———即将上映———";
	        				for(int i=0; i<movieName.length; i++){
		        			re = re + "\n(" + (i+1) + ").电影名称：" + movieName[i] +
		        					"\n上映时间：" + movieDate[i];
	        				}
	        				returnStr = getBackXMLTypeText(toName,fromName, re);  
	        			} else if (left.equals("正在热映")) { // 此为 文本信息  
	        				int movieNum = Integer.parseInt(right);;
	        				ArrayList<Object> movieList = new ArrayList<Object>();
	        				NowMovie nm = new NowMovie();
	        				String re = "";
	        				if(movieNum<16){
	        					movieList = nm.getMovieData(movieNum, "kunming");//昆明
	        					String[] movieName = (String[])(movieList.get(0));
	        					String[] movieScore = (String[])(movieList.get(1));
	        					re = "———正在热映———";
	        					for(int i=0; i<movieName.length; i++){
	        						re = re + "\n(" + (i+1) + ").电影名称：" + movieName[i] +
	        								"\n豆瓣评分：" + movieScore[i];
	        					}
	        					returnStr = getBackXMLTypeText(toName,fromName, re + "\n（数据来自云服务器）");  
	        				}else {
	        					returnStr = getBackXMLTypeText(toName,fromName, "查询数目不可大于15");
	        				}
	        			} else {
	        				returnStr = getBackXMLTypeText(toName,fromName,defaultStr);
	        			}
	        		}
	        	} else if(con.contains("天气")){
	        		NowWeather nw = new NowWeather();
	        		JSONObject  json = new JSONObject();
	        		String cityName = con.substring(0, con.length()-2);
	        		json = nw.getData(cityName);
	        		
	        		String errorNum = json.getString("errNum");
	        		String rs="今日天气\n";
	            	
	            	if(errorNum.equals("0")){
		        		String city = json.getJSONObject("retData") .getString("city");
		        		String weather = json.getJSONObject("retData") .getString("weather");
		        		String temp = json.getJSONObject("retData") .getString("temp");
		        		String high_tmp = json.getJSONObject("retData") .getString("h_tmp");
		        		String low_tmp = json.getJSONObject("retData") .getString("l_tmp");
		        		String windDirection = json.getJSONObject("retData") .getString("WD");
		            	String windSpeed = json.getJSONObject("retData") .getString("WS");
		            	String sunRiseTime = json.getJSONObject("retData") .getString("sunrise");
		            	String sunSetTime = json.getJSONObject("retData") .getString("sunset");
		            	rs = rs + city + " " + weather + " " + temp + "℃"
		            			+ "\n" + low_tmp + "℃~" + high_tmp + "℃" 
		            			+ "\n" + windDirection + " " + windSpeed
		            			+ "\n日出：" + sunRiseTime 
		            			+ "\n日落：" + sunSetTime;
		            	returnStr = getBackXMLTypeText(toName,fromName,rs);
	            	} else {
	            		returnStr = getBackXMLTypeText(toName,fromName,"输入的城市名称或格式有误。");
	            	} 
	        	} else if(con.length()>2 && con.substring(0,2).equals("菜谱")){
	        		FoodList fl = new FoodList();
	        		ArrayList<Object> foodList = new ArrayList<Object>();
	        		String foodName = con.substring(2,con.length());
	        		foodList = fl.getFoodList(foodName);
	        		returnStr = getBackXMLTypeImg(toName, fromName, foodName,(String [])foodList.get(0), (String [])foodList.get(1), (String [])foodList.get(2), (String [])foodList.get(3));
	        	} else if(con.length()>3 && con.substring(0,3).equals("lol")){
	        		PlayerInfo pi = new PlayerInfo();
	        		ArrayList<Object> infoList = new ArrayList<Object>();
	        		String serverName = con.substring(3,con.indexOf("&"));
	        		String playerName = con.substring(con.indexOf("&")+1,con.length());
	        		infoList = pi.getPlayerInfo(serverName, playerName);
	        		String str = "===基本信息===";
	        		str = str + "\n服务器：" + serverName + "\n玩家ID：" + playerName + "\n战斗力：" + infoList.get(0);
	        		str = str + "\n===匹配模式===";
	        		for(int i=1; i<((ArrayList<Object>)infoList.get(1)).size(); i++){
	        			String [] title = (String[])((ArrayList<Object>)infoList.get(1)).get(0);
	        			String [] moshi = (String[])((ArrayList<Object>)infoList.get(1)).get(i);
	        			for(int j=0; j<title.length; j++){
	        				str = str + "\n" + title[j] + "：" + moshi[j];
	        			}
	        		}
	        		str = str + "\n===排位模式===";
	        		for(int i=1; i<((ArrayList<Object>)infoList.get(2)).size(); i++){
	        			String [] title = (String[])((ArrayList<Object>)infoList.get(2)).get(0);
	        			String [] moshi = (String[])((ArrayList<Object>)infoList.get(2)).get(i);
	        			for(int j=0; j<title.length; j++){
	        				str = str + "\n" + title[j] + "：" + moshi[j];
	        			}
	        		}
	        		str = str + "\n===最近比赛===";
	        		String[] recent = (String[])infoList.get(3);
	        		for(int i=1; i<recent.length; i++){
	        				str = str + "\n" + recent[i];
	        		}
	        		returnStr = getBackXMLTypeText(toName,fromName,str);
	        	}else if(con.contains("股票")){
	        		StockMsg smsg = new StockMsg();
	        		String str = smsg.getData(con.substring(2,con.length()));
	        		returnStr = getBackXMLTypeText(toName,fromName,str);
	        	}else if(con.contains("线s")){
	        		StockImg sisg = new StockImg();
	        		String str [] = sisg.getData(con.substring(0,1),con.substring(3,con.length())).split(",");
	        		returnStr = getBackXMLTypeImg(toName,fromName,str[0],"",str[2],str[2]);
	        	}else {// 此为 文本信息  
		            returnStr = getBackXMLTypeText(toName,fromName,defaultStr);
		        }
	        } else if(type.equals("location")){
	        	returnStr = getBackXMLTypeText(toName,fromName,"你发送的是位置信息,经度:"+location_Y+",纬度:"+location_X);
	        }
	        }catch (IOException e) {  
	            //异常  
	        	returnStr = getBackXMLTypeText(toName,fromName,"异常！" + e.getMessage());
	        }
	        
	          
	        return returnStr;  
	    }  
	      
	  
	    /** 
	     * 编译文本信息 
	     *  
	     * @author xiaowu 
	     * @since 2013-9-27 
	     * @param toName 
	     * @param FromName 
	     * @param content 
	     * @return 
	     */  
	    private String getBackXMLTypeText(String toName, String fromName,  
	            String content) {  
	  
	        String returnStr = "";  
	  
	        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");  
	        String times = format.format(new Date());  
	  
	        Element rootXML = new Element("xml");  
	  
	        rootXML.addContent(new Element("ToUserName").setText(fromName));  
	        rootXML.addContent(new Element("FromUserName").setText(toName));  
	        rootXML.addContent(new Element("CreateTime").setText(times));  
	        rootXML.addContent(new Element("MsgType").setText("text"));  
	        rootXML.addContent(new Element("Content").setText(content));  
	  
	        Document doc = new Document(rootXML);  
	  
	        XMLOutputter XMLOut = new XMLOutputter();  
	        returnStr = XMLOut.outputString(doc);  
	  
	        return returnStr;  
	    }  
	  
	    /** 
	     * 编译图片信息(单图模式) 
	     *  
	     * @author xiaowu 
	     * @since 2013-9-27 
	     * @param toName 
	     * @param FromName 
	     * @param content 
	     * @return 
	     */  
		private String getBackXMLTypeImg(String toName, String fromName,  
	            String title, String content, String url, String pUrl) {  
	  
	        String returnStr = "";  
	  
	        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");  
	        String times = format.format(new Date());  
	  
	        Element rootXML = new Element("xml");  
	  
	        rootXML.addContent(new Element("ToUserName").setText(fromName));  
	        rootXML.addContent(new Element("FromUserName").setText(toName));  
	        rootXML.addContent(new Element("CreateTime").setText(times));  
	        rootXML.addContent(new Element("MsgType").setText("news"));  
	        rootXML.addContent(new Element("ArticleCount").setText("1"));  
	  
	        Element fXML = new Element("Articles");  
	        Element mXML = null;  
	  
	        mXML = new Element("item");  
	        mXML.addContent(new Element("Title").setText(title));  
	        //mXML.addContent(new Element("Description").setText(content));  
	        mXML.addContent(new Element("PicUrl").setText(pUrl));  
	        mXML.addContent(new Element("Url").setText(url));
	        fXML.addContent(mXML);  
	        rootXML.addContent(fXML);  
	  
	        Document doc = new Document(rootXML);  
	  
	        XMLOutputter XMLOut = new XMLOutputter();  
	        returnStr = XMLOut.outputString(doc);  
	  
	        return returnStr;  
	    }  
	    /** 
	     * 编译图片信息(无图模式) 
	     *  
	     * @author xiaowu 
	     * @since 2013-9-27 
	     * @param toName 
	     * @param FromName 
	     * @param content 
	     * @return 
	     */  
	    @SuppressWarnings("unused")
		private String getBackXMLTypeImg(String toName, String fromName,  
	            String title, String content, String url) {  
	  
	        String returnStr = "";  
	  
	        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");  
	        String times = format.format(new Date());  
	  
	        Element rootXML = new Element("xml");  
	  
	        rootXML.addContent(new Element("ToUserName").setText(fromName));  
	        rootXML.addContent(new Element("FromUserName").setText(toName));  
	        rootXML.addContent(new Element("CreateTime").setText(times));  
	        rootXML.addContent(new Element("MsgType").setText("news"));  
	        rootXML.addContent(new Element("ArticleCount").setText("1"));  
	  
	        Element fXML = new Element("Articles");  
	        Element mXML = null;  
	  
	        //String url = "";  
	        String ss = "";  
	        mXML = new Element("item");  
	        mXML.addContent(new Element("Title").setText(title));  
	        mXML.addContent(new Element("Description").setText(content));  
	        mXML.addContent(new Element("PicUrl").setText(ss));  
	        mXML.addContent(new Element("Url").setText(url));  
	        fXML.addContent(mXML);  
	        rootXML.addContent(fXML);  
	  
	        Document doc = new Document(rootXML);  
	  
	        XMLOutputter XMLOut = new XMLOutputter();  
	        returnStr = XMLOut.outputString(doc);  
	  
	        return returnStr;  
	    }  
	    //多图模式
	    private String getBackXMLTypeImg(String toName, String fromName, String foodName,  
	            String[] title, String[] content, String[] pUrl, String[] url) {  
	  
	        String returnStr = "";  
	  
	        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");  
	        String times = format.format(new Date());  
	  
	        Element rootXML = new Element("xml");  
	  
	        rootXML.addContent(new Element("ToUserName").setText(fromName));  
	        rootXML.addContent(new Element("FromUserName").setText(toName));  
	        rootXML.addContent(new Element("CreateTime").setText(times));  
	        rootXML.addContent(new Element("MsgType").setText("news"));  
	        rootXML.addContent(new Element("ArticleCount").setText("6"));  
	  
	        Element fXML = new Element("Articles");  
	        Element mXML = null;  
	  
	        for(int i=0; i<title.length; i++){
	        	mXML = new Element("item");  
	        	mXML.addContent(new Element("Title").setText(title[i]));  
	        	mXML.addContent(new Element("Description").setText(content[i]));  
	        	mXML.addContent(new Element("PicUrl").setText(pUrl[i]));  
	        	mXML.addContent(new Element("Url").setText(url[i]));  
	        	fXML.addContent(mXML);
	        }
	        //其他
	        mXML = new Element("item");  
        	mXML.addContent(new Element("Title").setText("查看更多"));  
        	mXML.addContent(new Element("Description").setText("点击查看更多菜谱"));  
        	mXML.addContent(new Element("PicUrl").setText(""));  
        	mXML.addContent(new Element("Url").setText("http://so.meishi.cc/index.php?q=" + foodName));  
        	fXML.addContent(mXML);
	        rootXML.addContent(fXML);  
	  
	        Document doc = new Document(rootXML);  
	  
	        XMLOutputter XMLOut = new XMLOutputter();  
	        returnStr = XMLOut.outputString(doc);  
	  
	        return returnStr;  
	    }
	  
	    /** 
	     * 编译音乐信息 
	     *  
	     * @author xiaowu 
	     * @since 2013-9-27 
	     * @param toName 
	     * @param FromName 
	     * @param content 
	     * @return 
	     */  
	    @SuppressWarnings("unused")  
	    private String getBackXMLTypeMusic(String toName, String fromName,  
	            String content) {  
	  
	        String returnStr = "";  
	  
	        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");  
	        String times = format.format(new Date());  
	  
	        Element rootXML = new Element("xml");  
	  
	        rootXML.addContent(new Element("ToUserName").setText(fromName));  
	        rootXML.addContent(new Element("FromUserName").setText(toName));  
	        rootXML.addContent(new Element("CreateTime").setText(times));  
	        rootXML.addContent(new Element("MsgType").setText("music"));  
	  
	        Element mXML = new Element("Music");  
	  
	        mXML.addContent(new Element("Title").setText("音乐"));  
	        mXML.addContent(new Element("Description").setText("音乐让人心情舒畅！"));  
	        mXML.addContent(new Element("MusicUrl").setText(content));  
	        mXML.addContent(new Element("HQMusicUrl").setText(content));  
	  
	        rootXML.addContent(mXML);  
	  
	        Document doc = new Document(rootXML);  
	  
	        XMLOutputter XMLOut = new XMLOutputter();  
	        returnStr = XMLOut.outputString(doc);  
	  
	        return returnStr;  
	    }  
	}  
