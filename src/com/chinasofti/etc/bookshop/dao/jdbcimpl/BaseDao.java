package com.chinasofti.etc.bookshop.dao.jdbcimpl;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BaseDao {
	protected Connection conn;
	protected PreparedStatement pstmt;
	protected ResultSet rs;
	protected int result;

	static {
		try {
			Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("�������ݿ�����ʧ��");
		}
	}

	/**
	 * �������ݿ�����
	 */
	public void getConn() {
		String url = "jdbc:sqlserver://localhost:1433;dataBaseName=bookshop";
		String user = "sa";
		String password = "123456";
		try {
			conn = DriverManager.getConnection(url, user, password);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("���ݿ�����ʧ��,�������/���ݿ���/��½�˻������룡");
		}
	}

	/**
	 * �ر��������ݿ�����
	 */
	public void closeAll() {
		try {
			if (pstmt != null && !pstmt.isClosed()) {
				pstmt.close();
			}
			if (rs != null && !rs.isClosed()) {
				rs.close();
			}
			if (conn != null && !conn.isClosed()) {
				conn.close();
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("���ݿ�ر������쳣");
		}
	}

	/**
	 * ��ѯ���ݿ�
	 */
	public void doQuery(String sql, Object... params) {
		try {
			pstmt = conn.prepareStatement(sql);
			if (pstmt != null) {
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}
			rs = pstmt.executeQuery();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.out.println("��ѯ���ݿ��쳣��");
		}
	}

	/**
	 * �������ݿ⣺��ɾ��
	 */
	public void doOperate(String sql, Object... params) {
		try {
			pstmt = conn.prepareStatement(sql);
			if (pstmt != null) {
				for (int i = 0; i < params.length; i++) {
					pstmt.setObject(i + 1, params[i]);
				}
			}
			result = pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * �жϱ��Ƿ����
	 */
	boolean existTable(String tableName){
		DatabaseMetaData meta;
		try {
			meta = (DatabaseMetaData) conn.getMetaData();
			ResultSet rs = meta.getTables(null, null, tableName, null);
			if (rs.next()) {
				return true;
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}
	
	/**
	 * SQLServer���ݿ��ʼ����������������м�¼
	 */
	public void doInit() {
		// TODO Auto-generated method stub
		getConn();
		
		String sql = null;
		if (!existTable("Admins")) {
			// ��ʼ����Books
			sql = "create table Admins(adminId int primary key identity(1,1), adminName nvarchar(20), adminPassword nvarchar(100))";
			doOperate(sql);
			sql = "insert into Admins values('admin', '123456')";
			doOperate(sql);
			System.out.println("====��ʼ����Admins�ɹ�===");
		}else {
			System.out.println("Admins���Ѵ���");
		}
		
		if (!existTable("Customers")) {
			// ��ʼ����Customers
			sql = "create table Customers(customerId int primary key identity(1,1), customerNo nvarchar(100), customerName nvarchar(100), customerPassword nvarchar(100), customerAddress nvarchar(100))";
			doOperate(sql);
			sql = "insert into Customers values('80000001','zhangsan', 'zhangsan', '�Ͳ��ŵ�')";
			doOperate(sql);
			sql = "insert into Customers values('80000002','lisi', 'lisi', '����������')";
			doOperate(sql);
			sql = "insert into Customers values('80000003','wangwu', 'wangwu', 'ɽ������ѧ')";
			doOperate(sql);
			System.out.println("====��ʼ����Customers�ɹ�===");

		}else {
			System.out.println("Customers���Ѵ���");
		}
		
		if (!existTable("Books")) {
			// ��ʼ����Books
			sql = "create table Books(bookId int primary key identity(1,1), bookIsbn nvarchar(100), bookName nvarchar(100), bookPrice money, bookAuthor nvarchar(100), bookPublisher nvarchar(100), bookStore int, bookImage nvarchar(100))";
			doOperate(sql);
			sql = "insert into Books values('ISBN-11-11', '���������', 10, '�����', '�廪��ѧ������', 500, 'a.gif')";
			doOperate(sql);
			sql = "insert into Books values('ISBN-22-22', 'C#�����ŵ���ͨ', 20, '����ΰ', '��е������', 432, 'b.gif')";
			doOperate(sql);
			sql = "insert into Books values('ISBN-33-33', '�㷨����', 30, '��ѩ��', '���ӳ�����', 68, 'c.gif')";
			doOperate(sql);
			sql = "insert into Books values('ISBN-44-44', 'C�������', 25, '̷��ǿ', '�廪��ѧ������', 88, 'c.gif')";
			doOperate(sql);
			sql = "insert into Books values('ISBN-55-55', 'ARMǶ��ʽ', 8, '����', '���ӳ�����', 35, 'd.gif')";
			doOperate(sql);
			sql = "insert into Books values('ISBN-66-66', '���ݿ����', 45, '����', '���ӳ�����', 78, 'b.gif')";
			doOperate(sql);
			System.out.println("====��ʼ����Books�ɹ�===");
		}else {
			System.out.println("Books���Ѵ���");
		}
		
		if (!existTable("Employees")) {
			// ��ʼ����Employees
			sql = "create table Employees(employeeId int primary key identity(1,1), employeeNo nvarchar(100), employeeName nvarchar(100), employeeRole nvarchar(100), employeeSex nvarchar(100))";
			doOperate(sql);
			sql = "insert into Employees values('00001', 'Ա��A', '�ͻ�Ա', '��')";
			doOperate(sql);
			sql = "insert into Employees values('00002', 'Ա��B', '�ͻ�Ա', 'Ů')";
			doOperate(sql);
			sql = "insert into Employees values('00003', 'Ա��C', '�Ƶ���', '��')";
			doOperate(sql);
			System.out.println("====��ʼ����Employees�ɹ�===");

		}else {
			System.out.println("Employee���Ѵ���");
		}
		
		if (!existTable("CartItems")) {
			// ��ʼ����CartItems
			sql = "create table CartItems(cartItemId int primary key identity(1,1), bookId int, bookNum int, subTotal money, bookPrice money, customerId int)";
			doOperate(sql);
			System.out.println("====��ʼ����CartItems�ɹ�===");

		}else {
			System.out.println("CartItems���Ѵ���");
		}
		
		if (!existTable("Orders")) {
			// ��ʼ����Orders
			sql = "create table Orders(orderId int primary key identity(1,1), orderNo nvarchar(45), orderMaker nvarchar(45), recMoneyId int, customerId int, date date, total money)";
			doOperate(sql);
			System.out.println("====��ʼ����Orders�ɹ�===");

		}else {
			System.out.println("Orders���Ѵ���");
		}
		
		if (!existTable("OrderDetails")) {
			// ��ʼ����OrderDetails
			sql = "create table OrderDetails(orderDetailId int primary key identity(1,1), orderId int, bookId int, bookPrice money, bookNum int, subTotal money)";
			doOperate(sql);
			System.out.println("====��ʼ����OrderDetails�ɹ�===");

		}else {
			System.out.println("OrderDetails���Ѵ���");
		}
		closeAll();

	}
}
