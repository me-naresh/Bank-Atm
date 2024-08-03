import java.sql.*;
public class DatabaseConnection2{
	public static Connection getConnection(){
		Connection cn = null;  ///why null
		try{
			Class.forName("com.mysql.cj.jdbc.Driver");
			//connection
			String url = "jdbc:mysql://localhost:3306/atm";
			String username="root";
			String password="";
			cn=DriverManager.getConnection(url,username,password);//why cn
			System.out.println("Connected");
		}catch(Exception e){
			System.out.println("Connection failed....>");
			e.printStackTrace();
		}
		return cn;// why return cn
	}
	
}