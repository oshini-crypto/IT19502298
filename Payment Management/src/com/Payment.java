package com;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class Payment {
	public Connection connect()
	{ 
	 Connection con = null; 
	 
	 try 
	 { 
	 Class.forName("com.mysql.cj.jdbc.Driver"); 
	 con= DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/paf", "root", ""); 
	 //For testing
	 System.out.print("Successfully connected"); 
	 } 
	 catch(Exception e) 
	 { 
	 e.printStackTrace(); 
	 } 
	 
	 return con; 
		}



	public String insertPayment(String CustomerName, String AccountNumber, String ProductName, String PaymentAmount) {
		
		 String output = "";
		 
		 try {
		
		Connection con = connect();
		if (con == null) 
		{ 
		return "Error while connecting to the database"; 
		}
		
		// create a prepared statement
		String query = " insert into payment(`paymentID`,`customerName`,`accountNumber`,`productName`,`amount`)" + " values (?, ?, ?, ?, ?)";
		PreparedStatement preparedStmt = con.prepareStatement(query); 
		// binding values
		 preparedStmt.setInt(1, 0);
		 preparedStmt.setString(2, CustomerName);
		 preparedStmt.setString(3, AccountNumber);
		 preparedStmt.setString(4, ProductName);
		 preparedStmt.setDouble(5, Double.parseDouble(PaymentAmount));
		
		System.out.println(CustomerName);
		 
		 preparedStmt.execute(); 
		 con.close(); 
		 
		 String newPayments = readPayments();
		 output =  "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}"; 
		 } 

		catch (Exception e) 
		 { 
			output = "{\"status\":\"error\", \"data\": \"Error while inserting the payment.\"}";  
		 System.err.println(e.getMessage()); 
		 } 
		return output; 
		}


	public String readPayments()
	{ 
			 String output = ""; 
			try
			 { 
			 Connection con = connect(); 
			 if (con == null) 
			 { 
			 return "Error while connecting to the database for reading."; 
			 } 
			 // Prepare the html table to be displayed
			 output = "<table border='1'><tr><th>Customer Name</th>" +"<th>Account Number</th><th>Product Name</th>"+ "<th>Payment Amount</th>" + "<th>Update</th><th>Remove</th></tr>"; 
			 String query = "select * from payment";
			 java.sql.Statement stmt = con.createStatement(); 
			 ResultSet rs = stmt.executeQuery(query); 
			 // iterate through the rows in the result set
			 while (rs.next()) 
			 { 
				 String paymentID = Integer.toString(rs.getInt("paymentID"));
				 String customerName = rs.getString("customerName");
				 String accountNumber = rs.getString("accountNumber");
				 String productName = rs.getString("productName");
				 String amount = Double.toString(rs.getDouble("amount")); 
			 // Add a row into the html table
			 output += "<tr><td><input id='hidpaymentIDUpdate' name='hidpaymentIDUpdate' type='hidden' value='" + paymentID + "'>"+ customerName + "</td>";
			 output += "<td>" + accountNumber + "</td>"; 
			 output += "<td>" + productName + "</td>"; 
			 
			 output += "<td>" + amount + "</td>";
			 // buttons
			 output += "<td><input name='btnUpdate' " + " type='button' value='Update' class =' btnUpdate btn btn-secondary'data-paymentid='" + paymentID + "'></td>"+ "<td><form method='post' action='Payment.jsp'>"+ "<input name='btnRemove' " + " type='button' value='Remove' class='btnRemove btn btn-danger' data-paymentid='" + paymentID + "'>"+ "<input name='hidPaymentIDDelete' type='hidden' " + " value='" + paymentID + "'>" + "</form></td></tr>"; 
			 } 
			 con.close(); 
			 // Complete the html table
			 output += "</table>"; 
			 } 
			catch (Exception e) 
			 { 
			 output = "Error while reading the payments."; 
			 System.err.println(e.getMessage()); 
			 } 
			return output; 
		}

	public String deletePayment(String paymentID)
	{ 
	 String output = ""; 
	try
	 { 
	 Connection con = connect(); 
	 if (con == null) 
	 { 
	 return "Error while connecting to the database for deleting."; 
	 } 
	 // create a prepared statement
	 String query = "delete from payment where paymentID=?"; 
	 PreparedStatement preparedStmt = con.prepareStatement(query); 
	 // binding values
	 preparedStmt.setInt(1, Integer.parseInt(paymentID)); 
	 
	 // execute the statement
	 preparedStmt.execute(); 
	 con.close(); 
	 String newPayments = readPayments();
	 output =  "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}"; 
	 } 

	catch (Exception e) 
	 { 
		output = "{\"status\":\"error\", \"data\": \"Error while deleting the payment.\"}";  
	 System.err.println(e.getMessage()); 
	 } 
	return output; 
		}

	public String updatePayment(String PaymentID, String CustomerName, String AccountNumber, String ProductName, String PaymentAmount)
	//4
	{
	String output = "";
	try {
	Connection conn = connect();
	if (conn == null) {
	return "Error while connecting to the database for updating.";
	}

	// create a prepared statement
	String query = "UPDATE payment SET customerName=?,accountNumber=?,productName=?,amount=?WHERE paymentID=?";
	PreparedStatement preparedStmt = conn.prepareStatement(query);
	//binding values
	preparedStmt.setString(1, CustomerName);
	 preparedStmt.setString(2, AccountNumber);
	 preparedStmt.setString(3, ProductName);
	 preparedStmt.setDouble(4, Double.parseDouble(PaymentAmount));
	 preparedStmt.setInt(5, Integer.parseInt(PaymentID));
	//execute the statement
	preparedStmt.execute();
	conn.close();
	String newPayments = readPayments();
	 output =  "{\"status\":\"success\", \"data\": \"" + newPayments + "\"}"; 
	 } 

	catch (Exception e) 
	 { 
		output = "{\"status\":\"error\", \"data\": \"Error while Updating the payment.\"}";  
	
	System.err.println(e.getMessage());
	}
	return output;
	}


}

