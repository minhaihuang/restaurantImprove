package Util;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class JdbcUtils {

    private static final String driverName;
    private static final String url;
    private static final String userName;
    private static final String passWord;
    
    //从配置文件中获取驱动包的名称，数据库名称，用户名，密码等信息
    //用static代码块包装，使其在程序一开始运行的时候便能够完成初始化
    static{
	//用流来获取配置文件
	InputStream is=null;
	//读取配置文件
	try{	
	    Properties prop=new Properties();

	    is=JdbcUtils.class.getClassLoader().getResourceAsStream(
			"Util/confige.properties");
	   
	    prop.load(is);
	    
	    //初始化常量
	    driverName=prop.getProperty("driverName");
	     url=prop.getProperty("url");
	    userName=prop.getProperty("userName");
	    passWord=prop.getProperty("passWord");
	
	  //加载jdbc驱动包，因为只需加载一次，所以不必在方法中声明
	    Class.forName(driverName);
	} catch (IOException e) {
	   throw new RuntimeException("加载配置文件失败",e);
	} catch (ClassNotFoundException e) {
	    throw new RuntimeException("加载jdbc驱动包失败",e);
	}finally{
	   CloseUtil.close(is);
	}
    }
  
    //创建获取连接的方法
    public static Connection createConnection() throws SQLException{
	return DriverManager.getConnection(url,userName,passWord);
    }
    
    public static Connection createConnection(String userName,String passWord) throws SQLException{
    	return DriverManager.getConnection(url,userName,passWord);
    }
    
    //创建更新数据库的方法
    public static int executeUpdate(Connection conn,String sql,Object... obj) throws SQLException {
	//包装要执行的sql语句
	PreparedStatement ps=null;;
	try {
	    ps = conn.prepareStatement(sql);
	    
	        //从外部传递参数，完善sql语句
		for(int i=0;i<obj.length;i++){
		    ps.setObject(i+1, obj[i]);//此方法与众不同，下标从1开始。
		}
	
		//执行sql语句,并返回值
		return ps.executeUpdate();
	}finally{
	    CloseUtil.close(ps);
	}
    }
    
    /**
     * 创建查询数据库的方法
     * @throws SQLException 
     */
    public static ResultSet executeQuery(Connection conn,String sql,Object... obj) throws SQLException{
	//包装要执行的sql语句
	PreparedStatement ps=conn.prepareStatement(sql);
	
	//从外部传递参数，完善sql语句
	for(int i=0;i<obj.length;i++){
	    ps.setObject(i+1, obj[i]);//此方法与众不同，下标从1开始。
	}
	
	return ps.executeQuery();
    }
    
    /**
     * 封装需要自己创建连接的更新数据库的方法
     * @throws SQLException 
     */
    public static int executeUpdate(String sql,Object... obj) throws SQLException {
	Connection conn=null;
	
	
	    conn=createConnection();
	
	return executeUpdate(conn,sql,obj);
    }
    
    /**
     * 封装需要自己创建连接的查询数据库的方法
     * @throws SQLException 
     */
    
    public static ResultSet executeQuery(String sql,Object... obj) throws SQLException{
	Connection conn=createConnection();
	
	return executeQuery(conn,sql,obj);
    }
    
    /**
     * 自定义关闭的工具
     */
    public static void close(AutoCloseable... obj){
  	for(int i=0;i<obj.length;i++){
  	    if(obj[i]!=null){
  		try {
  		    obj[i].close();
  		} catch (Exception e) {
  		    System.out.println("关闭流失败");
  		}
  	    }
  	    
  	}
      }
    
    /**
     * 自定义一个关闭所有的方法
     */
    
    public static void closeAll(ResultSet rs){
	 Connection conn=null;
	 Statement st=null;
	try {
	    st=rs.getStatement();
	    conn=rs.getStatement().getConnection();
	} catch (SQLException e) {
	   System.out.println("操作数据库失败"+e.getMessage());
	}finally{
	    close(rs,st,conn);
	}
	
    }
    
    /**
     * 自定义一个回滚的类
     */
    public static void rollBack(Connection conn){
	
	try {
	    conn.rollback();
	} catch (SQLException e) {
	   System.out.println("回滚失败");
	}
    }
}