package wechat.db;

import java.sql.ResultSet;
import java.sql.SQLException;

public class Method {

	public ResultSet selectAll(String tableName){
		ResultSet ret = null;
		String sql = "select * from " + tableName;//SQL语句
		DBConnector db1 = new DBConnector(sql);//创建DB对象  
		try {  
            ret = db1.pst.executeQuery();//执行语句，得到结果集    
            db1.close();//关闭连接  
        } catch (SQLException e) {  
            e.printStackTrace();  
        }
		return ret;
	}
	
	public int insert(String sql){
		int ret = 0;
		DBConnector db1 = new DBConnector(sql);
		try {  
            ret = db1.pst.executeUpdate();
            db1.close();//关闭连接  
		} catch (SQLException e) {  
            e.printStackTrace();  
        }
		return ret;
	}
}
