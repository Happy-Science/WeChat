package wechat.function.LOL;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import net.sf.json.JSONObject;

public class PlayerInfo {
	//infoList中，第一项为 战斗力，第二项为匹配模式（ArrayList<Object>），第三项为排位模式（ArrayList<Object>）
	//第四项为最近比赛（String[]）。
	public ArrayList<Object> getPlayerInfo(String serverName, String playerName){
		//处理服务器信息
		String [][] dianServerList = {{"电信一","艾欧尼亚"},{"电信二","祖安"},{"电信三","诺克萨斯"},{"电信四","班德尔城"}
		,{"电信五","皮尔特沃夫"},{"电信六","战争学院"},{"电信七","巨神峰"},{"电信八","雷瑟守备"},{"电信九","裁决之地"}
		,{"电信十","黑色玫瑰"},{"电信十一","暗影岛"},{"电信十二","钢铁烈阳"},{"电信十三","水晶之痕"},{"电信十四","均衡教派"}
		,{"电信十五","影流"},{"电信十六","守望之海"},{"电信十七","征服之海"},{"电信十八","卡拉曼达"},{"电信十九","皮城警备"}};
		String [][] wangServerList = {{"网通一","比尔吉沃特"},{"网通二","德玛西亚"},{"网通三","弗雷尔卓德"},{"网通四","无畏先锋"}
		,{"网通五","恕瑞玛"},{"网通六","扭曲丛林"},{"网通七","巨龙之巢"}};
		String [][] eduServerList = {{"教育一","教育网专区"}};
		for(int i=0; i<dianServerList.length; i++){
			if(serverName.equals(dianServerList[i][1])){
				serverName = dianServerList[i][0];
				break;
			}
		}
		for(int i=0; i<wangServerList.length; i++){
			if(serverName.equals(dianServerList[i][1])){
				serverName = dianServerList[i][0];
				break;
			}
		}
		for(int i=0; i<eduServerList.length; i++){
			if(serverName.equals(dianServerList[i][1])){
				serverName = dianServerList[i][0];
				break;
			}
		}
		ArrayList<Object> infoList = new ArrayList<Object>();
		Document doc;
		String zdl = "";
		try {
			doc = Jsoup.connect("http://lolbox.duowan.com/playerDetail.php?serverName=" + serverName + "&playerName=" + playerName).timeout(10000).get();
			//战斗力
			zdl = doc.select("div.fighting").get(0).child(1).child(0).child(0).text();
			infoList.add(zdl);
			//匹配模式
			int size1 = doc.select("div.mod-tabs-content").get(0).child(0).child(0).children().size();//匹配类型数（包含title）
			int size2 = doc.select("div.mod-tabs-content").get(0).child(0).child(0).child(0).children().size();//匹配信息列数
			ArrayList<Object> pipeiList = new ArrayList<Object>();
			for(int i=0; i<size1; i++){
				String[] type = new String[size2] ;
				for(int j=0; j<size2; j++){
					type[j] = doc.select("div.mod-tabs-content").get(0).child(0).child(0).child(i).child(j).text();
				}
				pipeiList.add(type);
			}
			infoList.add(pipeiList);
			//排位模式
			int size3 = doc.select("div.mod-tabs-content").get(1).child(0).child(0).children().size();//排位类型数（包含title）
			int size4 = doc.select("div.mod-tabs-content").get(1).child(0).child(0).child(0).children().size();//排位信息列数
			ArrayList<Object> paiweiList = new ArrayList<Object>();
			for(int i=0; i<size3; i++){
				String[] type = new String[size4-1];
				type[0] = "";
				for(int j=0; j<size4-1; j++){
					if(j==1 && type[0].equals("5v5单双排")){
						JSONObject  json = new JSONObject();
						URLConnection connection = null;
						try {
							connection = new URL("http://lolbox.duowan.com/ajaxGetWarzone.php?serverName=" + serverName + "&playerName=" + playerName).openConnection();
							connection.connect();
							InputStream fin = connection.getInputStream();
							BufferedReader br = new BufferedReader(new InputStreamReader(fin));
							StringBuffer buffer = new StringBuffer();
							String r = null;
							while ((r = br.readLine()) != null) {
								buffer.append(r);
							}
							json = JSONObject.fromObject(buffer.toString());  
							type[1] = json.getString("tier") + json.getString("rank");
							type[2] = json.getString("league_points");
						} catch (IOException e) {
							e.printStackTrace();
						}
						j = 2;
						continue;
					}
					type[j] = doc.select("div.mod-tabs-content").get(1).child(0).child(0).child(i).child(j).text();
				}
				paiweiList.add(type);
			}
			infoList.add(paiweiList);
			//最近比赛
			int recentNum = doc.select("div.recent").get(0).child(1).child(0).children().size();
			String [] recent = new String[recentNum];
			for(int i=1; i<recentNum; i++){
				recent[i] = "英雄：" + doc.select("div.recent").get(0).child(1).child(0).child(i).child(0).child(0).attr("title")
					+ "\n模式：" + doc.select("div.recent").get(0).child(1).child(0).child(i).child(1).text()
					+ "\n结果：" + doc.select("div.recent").get(0).child(1).child(0).child(i).child(2).child(0).text()
					+ "\n时间：" + doc.select("div.recent").get(0).child(1).child(0).child(i).child(3).text();
			}
			infoList.add(recent);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			infoList.add("获取信息错误！");
		}
		return infoList;
	}
}
