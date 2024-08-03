import java.util.*;
import java.sql.*;

public class BankAtm{
	static int id;
	static Scanner sc = new Scanner(System.in);
	public static void main(String[] args) throws SQLException,NumberFormatException {
		
		// DatabaseConnection2 dbc2 = new DatabaseConnection2();
		Connection cn = DatabaseConnection2.getConnection();
		Statement stat=cn.createStatement();
		System.out.println("enter your choice:");
		System.out.println(" ");
		System.out.println("1:Make Account ");
		System.out.println("2:Already  have  account");
		System.out.println(" ");
		int choice =Integer.parseInt(sc.nextLine());
		switch(choice){
			case 1:
				MakeAccount(stat );
				break;
			
			case 2:
				Login(stat);
				break;
		}
	}
		
	static void MakeAccount(Statement stat ) throws SQLException, NumberFormatException{	
		System.out.println(" Enter your name: ");
		String name =sc.nextLine();
		System.out.println("Enter your card number: ");
		int card =Integer.parseInt(sc.nextLine());
		System.out.println("Enter pin you want to set ");
		int pin =Integer.parseInt(sc.nextLine());
		//Statement stat=conn.createStatement();
		String sql = "INSERT INTO account (Name ,Card_number,Pin) VALUES('"+name+"','"+card+"','"+pin+"')";
		stat.executeUpdate(sql);	
		System.out.println("your account create sucessfully.");
	}
				
				
	static void Login(Statement stat ) throws SQLException,NumberFormatException {
		System.out.println("Enter your card number:");
		int card = Integer.parseInt(sc.nextLine());
		String sql = "SELECT Card_number FROM account";
		ResultSet  rs= stat.executeQuery(sql);
		while (rs.next()){
			int user_card = rs.getInt("Card_number");
			if(user_card==card){
				System.out.println("");
				System.out.println("**** welcome to our bank ***");
				AfterCardNumber(stat);
			}			
			else{
				  System.out.println("invalid card number ");
			}
		}		   
	}
	
	static  void AfterCardNumber( Statement stat)throws SQLException,NumberFormatException{
		//Statement stat=conn.createStatement();
		System.out.println("");
		System.out.println("Enter your id :");
		id = Integer.parseInt(sc.nextLine());
		String sql ="SELECT Pin FROM account WHERE id ='"+id+"'";  //Pin is column value  or not
		ResultSet rs = stat.executeQuery(sql);   // rsultSet rs
		int user_pin = 0;
		while(rs.next()) {
			 user_pin = rs.getInt("Pin"); 
		}
			int attempt = 3;	
			for(int i =0; i<attempt ; attempt--){	
				System.out.println("enter your pin number ");
				int pin =Integer.parseInt(sc.nextLine());
				
				if( user_pin == pin ){
					DisplayMenu(stat);
				}
				else{
					System.out.println("inavalid pin number :");
				}
				System.out.println("left amount :"+attempt);
				
			}
				System.out.println("your account is block now  please contact the nearest bank ");
				System.exit(0);
	}
	static  void DisplayMenu(Statement stat)throws SQLException,NumberFormatException{
		//Statement stat=conn.createStatement();
		String sql ="SELECT Name FROM account WHERE id ='"+id+"'";  
		ResultSet rs = stat.executeQuery(sql); 
		while(rs.next()) {
			String name = rs.getString("Name"); 
			System.out.println("");
			System.out.println("    Hii  " +name+"");
		
		}
			System.out.println("1:balance enquiry");
			System.out.println("2:deposite balance");
			System.out.println("3:withdraw blance");
			System.out.println("4:Bank Statement");
			System.out.println("5: pin change");
			System.out.println("6: exit");
			System.out.println("");
			System.out.println("Enter your choice");
			int choice=Integer.parseInt(sc.nextLine());
			while(true){
				switch(choice){
					case 1:
						BalanceDisplay(stat );
						break;
		  
					case 2:
						Deposite( stat);
						break;
		  
					case 3:
						Withdraw(stat );
						break;
		  
					case 4:
						BankStatement(stat);
						break;
		  
					case 5:
						PinChange(stat);
						break;
		  
					case 6:
						System.exit(0);
						break;
					default:	
						System.out.println("Invalid choice");
				}  
					System.exit(0);
			}
	  
	}
	
	
	static void BalanceDisplay( Statement stat )throws SQLException,NumberFormatException{
		String sql = "SELECT Balance FROM  transaction WHERE id='"+id+"'";
		ResultSet rs =stat.executeQuery(sql);
		 int u_balance=0;
		while(rs.next()){
			 u_balance=rs.getInt("Balance");
		}	
			System.out.println("your current balance is :"+u_balance);
		
		 DisplayMenu(stat);
	}
	
	
	static void Deposite( Statement stat)throws SQLException,NumberFormatException{
		System.out.println("enter the amount"); 
		int amount = Integer.parseInt(sc.nextLine());
		String sql3 = "SELECT Card_number FROM account WHERE id ='"+id+"'";
		ResultSet rs = stat.executeQuery(sql3);
		int Card_number= 0;
		while(rs.next()){
			Card_number = rs.getInt("Card_number");
		}
		
		String sql1= "SELECT Balance FROM account WHERE  id='"+id+"'";
		rs =stat.executeQuery(sql1);
		int u_balance = 0;
		while(rs.next()){
			u_balance = rs.getInt("Balance");			 
		}
		
		int new_amount = u_balance + amount;
		String my = "INSERT INTO transaction (Card_number, Deposite, Balance) VALUES ('"+Card_number+"', '"+amount+"','"+new_amount+"')";
	    stat.executeUpdate(my);
		
		String sql2= "UPDATE account SET Balance ='"+new_amount+"' WHERE id='"+id+"'";
		stat.executeUpdate(sql2);
		System.out.println("your balance deposite sucessfully");
		DisplayMenu(stat);
	}	 
	  
    static void Withdraw( Statement stat)throws SQLException,NumberFormatException{
		System.out.println("enter the amount"); 
		int amount = Integer.parseInt(sc.nextLine());
		String sql3 = "SELECT Card_number FROM account WHERE id ='"+id+"'";
		ResultSet rs = stat.executeQuery(sql3);
		int Card_number= 0;
		while(rs.next()){
			Card_number = rs.getInt("Card_number");
		}
		String sql1= "SELECT Balance FROM account WHERE  id='"+id+"'";
		rs =stat.executeQuery(sql1);
		int u_balance = 0;
		while(rs.next()){
			u_balance = rs.getInt("Balance");			 
		}
		int new_amount = u_balance - amount;
		String my = "INSERT INTO transaction (Card_number, Withdraw, Balance) VALUES ('"+Card_number+"', '"+amount+"','"+new_amount+"')";
	    stat.executeUpdate(my);
			
		String sql2= "UPDATE account SET Balance ='"+new_amount+"' WHERE id='"+id+"'";
		stat.executeUpdate(sql2);
		System.out.println("your balance Withdraw sucessfully  ");
		 DisplayMenu(stat);
	}
		
    static void BankStatement(Statement stat)throws SQLException,NumberFormatException{
	  	System.out.println("                  Bank Statement!!!");
		System.out.println("");
		String sql = "SELECT * FROM transaction WHERE id ='"+id+"'";
		ResultSet rs = stat.executeQuery(sql);
		System.out.println ("Transaction Date	Account Number	  Deposite Amount		Withdraw Amount		Balance");
		while(rs.next()){
			int Acc_no = rs.getInt("Card_number");
			Timestamp TransactionDate = rs.getTimestamp("Transaction_date");
			int WithdrawAmount = rs.getInt("Withdraw");
			int DepositeAmount = rs.getInt("Deposite");
			int  Balance = rs.getInt("Balance");
			System.out.println(""+TransactionDate+ "	 "+Acc_no+"	 		"+DepositeAmount+"		             "+WithdrawAmount+"		           "+Balance+" ");
		}
		 DisplayMenu(stat);
	}
	   
	   
    static void PinChange(Statement stat)throws SQLException,NumberFormatException{
		System.out.println("enter your old pin");
		int old_pin= Integer.parseInt(sc.nextLine());
		String sql = "SELECT Pin FROM account WHERE id ='"+id+"'";
		ResultSet rs = stat.executeQuery(sql);
		while(rs.next()){
			int ActualPin= rs.getInt("Pin"); 
		    if(old_pin==ActualPin){
				System.out.println("entr your new pin"); // end bracket of while
				int new_pin = Integer.parseInt(sc.nextLine());
				System.out.println("Enter again to conform"); // end bracket of while
				int conform_pin = Integer.parseInt(sc.nextLine());
				if(new_pin==conform_pin){
						String sql1 = "UPDATE account SET pin = '"+conform_pin+"' WHERE id ='"+id+"'";
						stat.executeUpdate(sql1);
						System.out.println("your pin change sucessfully !!");
						break;
					
				}
				else{
					System.out.println("conform password not match ");
					break;
				}
		   }
		   System.out.println("password not match");
			   
		}
		 DisplayMenu(stat);
	}
	
	static void exit(){
		System.exit(0);
	}
} 
	 

