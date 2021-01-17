package com.SQLite.Statement;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.ihec.bankSys.*;

public class SQLiteJDBCDriverConnection {
	//var for connect with DB
	static Connection conn;
	private static String url = "jdbc:sqlite:D:/JAVA Training 2019/Workspace2/Bank_System/DB/BSDB.db";
	
	//method return boolean value for check the DB at first
	public static String connect() {
		String value = null;
        try { 
            conn = DriverManager.getConnection(url);
            value ="OK";
        } catch (SQLException e) {
            value = e.getMessage();
        } finally {
            try {
                if (conn != null) {
                    conn.close();
                }
            } catch (SQLException ex) {
            	value = ex.getMessage();
            }
        }
        return value;
    }
	
	//method return class object for search about customer by Account number
	public static CustomInfo searchByAccountNo(String AccountNo){
        String sql = "select * from CustomerInfo where AccountNo = "+ AccountNo +" limit 1";
        
        try 
        {
        	Connection c = DriverManager.getConnection(url);
        	Statement stmt = c.createStatement();
        	ResultSet rs = stmt.executeQuery(sql);
        	CustomInfo CI = new CustomInfo();
        	CI.set_accountNo((int)rs.getInt(1));
            CI.set_fullName(rs.getString(2));
            CI.set_amount(Double.parseDouble(rs.getString(3)));
            c.close();
            return CI;
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            return null;
        }
    }
	
	//method return string value as flag for update
	public static String updateByAccountNo(CustomInfo CI){
		
        String sql = "update CustomerInfo set Amount = "+Double.toString(CI.get_amount())+" where AccountNo = "+Integer.toString(CI.get_accountNo());
        
        try 
        {
        	Connection c = DriverManager.getConnection(url);
        	PreparedStatement pstmt = c.prepareStatement(sql);
        	//pstmt.setString(1,Double.toString(CI.get_amount()));
        	//pstmt.setString(2,Integer.toString(CI.get_accountNo()));
        	pstmt.executeUpdate();
        	pstmt.close();
        	c.close();
            return "Done";
        } catch (SQLException e) {
        	return e.getMessage();
        }
    }

	//method for add record to history in the DB
	public static void AddToHistory(int UserId, String op, String am)
	{
		DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
		Date date = new Date();
		String dt = dateFormat.format(date);
		
		String sql = "insert into History (UserId, Operation, Amount, DT)\r\n" + 
						"values ("+Integer.toString(UserId)+ 
						", '"+ op +"', " + am +
						", '" + dt +"')";
		try 
        {
        	Connection c = DriverManager.getConnection(url);
        	PreparedStatement pstmt = c.prepareStatement(sql);
        	//pstmt.setString(1,Double.toString(CI.get_amount()));
        	//pstmt.setString(2,Integer.toString(CI.get_accountNo()));
        	pstmt.executeUpdate();
        	pstmt.close();
        	c.close();

        } catch (SQLException e)
		{
        	return;
}		
	}
}
