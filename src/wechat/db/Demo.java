package wechat.db;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;

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
      ResultSetMetaData rsmd = null;
      Method method = new Method();
      ResultSet ret = method.selectAll("NowMovie");
      try {
		rsmd = ret.getMetaData();
		while (ret.next()) {
			String str = "";
			for(int i=0; i<rsmd.getColumnCount(); i++){
				str = str + ret.getString(i+1) + "\t";
			} 
			System.out.println(str);  
		}//显示数据  
      } catch (SQLException e) {
  		// TODO Auto-generated catch block
  		e.printStackTrace();
  	  }
    }
}
