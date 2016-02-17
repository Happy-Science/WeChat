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
import wechat.function.movie.NowMovie;
import wechat.function.movie.RecentMoive;
import wechat.function.weather.NowWeather;  
	  
	public class PushManage { 
		String inputString = "";
	      
	    public String PushManageXml(InputStream is) throws JDOMException {  
	  
	        String returnStr = ""; // 反回Servlet字符串  
	        String toName = ""; // 开发者微信号  
	        String fromName = ""; // 发送方帐号（一个OpenID）  
	        String type = ""; // 请求类型  
	        String con = ""; // 消息内容(接收)  
	        String event = ""; // 自定义按钮事件请求  
	        String eKey = ""; // 事件请求key值  
	          
	        try {  
	  
	            SAXBuilder sax = new SAXBuilder();  
	            Document doc = sax.build(is);  
	            // 获得文件的根元素  
	            Element root = doc.getRootElement();  
	  
	            // 获得根元素的第一级子节点  
	            List list = root.getChildren();  
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
	                    eKey = first.getValue().trim();  
	                }  
	            }  
	           
	          
	        if (type.equals("event")) {     //此为事件  
	            if (event.equals("subscribe")) {// 此为 关注事件  
	                  
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
	        				returnStr = getBackXMLTypeText(toName,fromName,"*查询正在热映（即将上映）的电影请输入：正在热映（即将上映）+查询数量 【例如：正在热映10】"
	    		            		+ "\n*查询天气请输入：地名+天气【例如：北京天气】");
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
	        				returnStr = getBackXMLTypeText(toName,fromName,"*查询正在热映（即将上映）的电影请输入：正在热映（即将上映）+查询数量 【例如：正在热映10】"
	    		            		+ "\n*查询天气请输入：地名+天气【例如：北京天气】");
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
	            		returnStr = getBackXMLTypeText(toName,fromName,"输入的城市名称或格式有误。");;
	            	} 
	        	} else { // 此为 文本信息  
		            returnStr = getBackXMLTypeText(toName,fromName,"*查询正在热映（即将上映）的电影请输入：正在热映（即将上映）+查询数量 【例如：正在热映10】"
		            		+ "\n*查询天气请输入：地名+天气【例如：北京天气】");
		        }
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
	        rootXML.addContent(new Element("Content").setText("你输入："+ inputString + ".\n" + content));  
	  
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
	        mXML.addContent(new Element("Description").setText(content));  
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
