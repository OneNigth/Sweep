package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

//数据库连接
public class DB {
//	 public static final String url = "jdbc:mysql://127.0.0.1/histry";

//	 public static final String url = "jdbc:mysql://./histry.sql";
//	    public static final String name = "com.mysql.jdbc.Driver";  

	    public static final String name = "net.ucanaccess.jdbc.UcanaccessDriver";  
//	    public static final String user = "root";  
//	    public static final String password = "123456";  
//	  
	    public Connection conn = null;  
	    public PreparedStatement pst = null;  
	  
	    public DB(String sql) {  
	        try {  
	            Class.forName(name);//指定连接类型  
//	            conn = DriverManager.getConnection(url, user, password);//获取连接  
	            conn = DriverManager
						.getConnection("jdbc:ucanaccess://./histry.mdb");
	            pst = conn.prepareStatement(sql);//准备执行语句  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	    }  
	  
	    public void close() {  
	        try {  
	            this.conn.close();  
	            this.pst.close();  
	        } catch (SQLException e) {  
	            e.printStackTrace();  
	        }  
	    }  
}
