package wechat.db;

import java.sql.ResultSet;
//import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;

public class Demo {
	static String sql = null;  
    static DBConnector db1 = null;  

    public static void main(String[] args) {  
//        sql = "select * from NowMovie";//SQL语句  
//        db1 = new DBConnector(sql);//创建DB对象  
//  
//        try {  
//            ret = db1.pst.executeQuery();//执行语句，得到结果集  
//            while (ret.next()) {  
//                String movieId = ret.getString(1);  
//                String movieName = ret.getString(2);  
//                String score = ret.getString(3);  
//                String time = ret.getString(4);  
//                System.out.println(movieId + "\t" + movieName + "\t" + score + "\t" + time );  
//            }//显示数据  
//            ret.close();  
//            db1.close();//关闭连接  
//        } catch (SQLException e) {  
//            e.printStackTrace();  
//        }  
//    	ResultSetMetaData rsmd = null;
    	//String sql = "select * from NowMovie";//SQL语句
    	String sql = "select * from NowMovie order by movieId desc limit 15";
    	DBConnector db1 = new DBConnector(sql);//创建DB对象  
    	ResultSet ret = null;
    	try {  
    		ret = db1.pst.executeQuery();//执行语句，得到结果集    
         	//rsmd = ret.getMetaData();
         	ArrayList<Object> movieList = new ArrayList<Object>();
         	String [] movieName = new String[15];
    		String [] movieScore = new String[15];
    		int num = 14;
         	while (ret.next()) {
//         		String str = "";
//         		for(int i=0; i<rsmd.getColumnCount(); i++){
//         			str = str + ret.getString(i+1) + "\t";
//         		} 
//         		System.out.println(str);  
         		movieName[num] = ret.getString(2);
         		movieScore[num] = ret.getFloat(3) + "";
         		num--;
         	}//显示数据 
         	movieList.add(0,movieName);
			movieList.add(1,movieScore);
			for(int i=0; i<15; i++){
				System.out.println(movieName[i]+","+movieScore[i]);
			}
    	} catch (SQLException e) {  
    		e.printStackTrace();  
    	}finally { 
      		db1.close(); 
    	}
    }
}
