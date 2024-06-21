package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Scanner;

public class StatementDemo {
	
	private static Scanner scan = new Scanner(System.in);
	
	public static Connection establishConnection() throws Exception{
		//load the drivers
		Class.forName("com.mysql.cj.jdbc.Driver");
		System.out.println("Drivers loaded");
	
		// establish connection
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BasicDB", "dbuser", "Squ@d123");
		System.out.println("Connection established");
		return con;
	}
	
	public static void fetchById(int id) throws Exception {
		
		Connection con = establishConnection();
		
		String query = "select * from books1 where id = ?";
		PreparedStatement ps = con.prepareStatement(query);
			
		ps.setInt(1, id);
		ResultSet rs = ps.executeQuery();
		
		// fetch the result		
		while(rs.next()) {
			System.out.println(rs.getInt(1) + " : " + rs.getString(2));

			System.out.println("Enter new book name : ");
			String bookName = scan.next();
			
			String query1 = "update books1 set name = ? where id = ?";
			PreparedStatement ps2 = con.prepareStatement(query1);
			
			ps2.setString(1, bookName);
			ps2.setInt(2, id);
			
			int rowsAffected = ps2.executeUpdate();
			if (rowsAffected > 0) {
				System.out.println("Rows updated : " + rowsAffected);
			}
		}
		
		
	}
	
	public static void fetchAllData() throws Exception{
	
		Connection con = establishConnection();
				
				// fire queries
				Statement st = con.createStatement();
				String query = "select * from books1";
				
				// fetch the result
				ResultSet rs = st.executeQuery(query);
				
				while(rs.next()) {
					System.out.println(rs.getInt(1) + " : " + rs.getString(2));
				}
				
				// close the connection/ other objects
				rs.close();
				st.close();
				con.close();
	}
	
	public static void insertData() throws Exception{
		Connection con = establishConnection();
		
		System.out.println("Enter id : ");
		int id = scan.nextInt();
		
		System.out.println("Enter name : ");
		String name = scan.next();
		
		String query = "insert into books1 values(?, ?)";
		//Statement st = con.createStatement();
		//Statement st = con.prepareStatement(query);
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, id);
		ps.setString(2, name);
		
		int affectedRows = ps.executeUpdate();
		if(affectedRows > 0) {
			System.out.println("Total rows affected : " + affectedRows);
		}
		
	}
	
	public static void deleteById(int id) throws Exception{
		
		Connection con = establishConnection();
		String query = "delete from books1 where id = ?";
		
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, id);
		
		int affectedRows = ps.executeUpdate();
		if(affectedRows > 0) {
			System.out.println("Record deleted successfully");
		}
		
	}
	
	public static void main(String [] args) throws Exception{
		
		int choice = 0;
		
		do { 
			System.out.println("CRUD Menu");
			System.out.println("1. Fecth All data ");
			System.out.println("2. Insert ");
			System.out.println("3. Update");
			System.out.println("4. Delete");
			System.out.println("5. Exit");
			System.out.println("Enter choice : ");
			choice = scan.nextInt();
			
			switch(choice) {
			
			case 1: 
				fetchAllData();
				break;
				
			case 2:
				insertData();
				break;
			
			case 3: 
				System.out.println("Enter the id to update : ");
				int id = scan.nextInt();
				fetchById(id);
				break;
			
			case 4: 
				System.out.println("Enter the id to delete : ");
				int id1 = scan.nextInt();
				deleteById(id1);
				break;
				
			case 5:
				System.exit(0);
				
			default:
				System.out.println("Invalid choice");
			}
		
		}while(choice != 5);
	}
}
