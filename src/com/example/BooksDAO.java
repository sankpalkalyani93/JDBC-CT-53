package com.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Scanner;

public class BooksDAO {
	private static Scanner scan = new Scanner(System.in);
	private static Books books = new Books();
	
	public static Connection establishConnection() throws Exception {
		Class.forName("com.mysql.cj.jdbc.Driver");
		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/BasicDB", "dbuser", "Squ@d123");
		return con;
	}
	
	public static void createABook() throws Exception {
		Connection con = establishConnection();
		System.out.println("Enter book id : ");
		int id = scan.nextInt();
		
		System.out.println("Enter book name : ");
		String name = scan.next();
		
		books.setId(id);
		books.setName(name);
		
		String query = "insert into books1 values(?,?)";
		PreparedStatement ps = con.prepareStatement(query);
		ps.setInt(1, books.getId());
		ps.setString(2, books.getName());
		
		int affectedRows = ps.executeUpdate();
		if(affectedRows > 0) {
			System.out.println("Record inserted successfully : " + affectedRows);
		}
	}
	
	public static void displayAllBooks() throws Exception {
		ArrayList<Books> booksList = new ArrayList<>();
		
		Connection con = establishConnection();
		String query = "select * from books1";
		Statement st = con.createStatement();
		
		ResultSet rs = st.executeQuery(query);
		while(rs.next()) {
			Books book = new Books();
			book.setId(rs.getInt(1));
			book.setName(rs.getString(2));
			booksList.add(book);
			//System.out.println(rs.getInt(1) + " : " + rs.getString(2));
		}
		//System.out.println(booksList);
		for(Books b : booksList) {
			System.out.println(b.getId() + " : " + b.getName());
		}
	}
	
	public static void updateABook() throws Exception {
		
		Connection con = establishConnection(); 
		System.out.println("Enter the id to update : ");
		books.setId(scan.nextInt());
		
		String query = "update books1 set name = ? where id = ?";
		PreparedStatement ps = con.prepareStatement(query);
		
		System.out.println("Enter new book name value : ");
		books.setName(scan.next());
		
		ps.setString(1, books.getName());
		ps.setInt(2, books.getId());
		
		int rowAffected = ps.executeUpdate();
		if(rowAffected > 0) {
			System.out.println("Record updated successfully");
		}
		else {
			System.out.println("No such record found : ");
		}
	}
	
	public static void deleteABook() throws Exception {
		
		Connection con = establishConnection(); 
		System.out.println("Enter the id to delete : ");
		books.setId(scan.nextInt());
		
		String query = "delete from books1 where id = ?";
		PreparedStatement ps = con.prepareStatement(query);
		
		ps.setInt(1, books.getId());
		
		int rowsAffected = ps.executeUpdate();
		if(rowsAffected > 0) {
			System.out.println("Record deleted successfully");
		}
		else {
			System.out.println("No such record found : ");
		}
	}
	
	public static void main(String [] args) throws Exception{
		
		int choice = 0;
		do {
			
			System.out.println("***** MENU ****");
			System.out.println("1. Create a book");
			System.out.println("2. Display all books");
			System.out.println("3. Update a book");
			System.out.println("4. Delete a book");
			System.out.println("5. Exit");
			
			System.out.println("Enter your choice : ");
			choice = scan.nextInt();
			
			switch(choice){
			
				case 1: 
					createABook();
					break;
					
				case 2: 
					displayAllBooks();
					break;
					
				case 3:
					updateABook();
					break;
					
				case 4:
					deleteABook();
					break;
					
				case 5:
					System.exit(0);
			
				default : 
					System.out.println("Invalid choice");
			}
		}while(choice != 5);
	}
}
