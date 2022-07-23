package com.aptech.student.management.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.mysql.jdbc.CallableStatement;
import com.mysql.jdbc.Statement;

public class DatabaseUtil {
	private final static String DRIVER = "com.mysql.jdbc.Driver";
	private final static String DBURL = "jdbc:mysql://localhost:3306/student_management?useSSL=false&useUnicode=true&characterEncoding=UTF-8";
	private final static String USERNAME = "root";
	private final static String PASSWORD = "123abc@A";
	private static DatabaseUtil INSTANCE;
	private Connection connection;

	private DatabaseUtil() {
	
	}

	public static DatabaseUtil getInstance() {
		if (INSTANCE == null) {
			INSTANCE = new DatabaseUtil();
		}
		return INSTANCE;
	}

	public Connection getConnection() {
		try {
			Class.forName(DRIVER);
			connection = DriverManager.getConnection(DBURL, USERNAME, PASSWORD);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return connection;
	}

	public void closeConnection(Connection con) {

		try {
			if (con != null) {
				con.close();
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void closeObject(CallableStatement obj) {
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void closeObject(Statement obj) {
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void closeObject(ResultSet obj) {
        try {
            if (obj != null) {
                obj.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void closeObject(Connection obj) {
        try {
            if (obj != null && !obj.isClosed()) {
                if (!obj.getAutoCommit()) {
                    obj.rollback();
                }

                obj.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
	
	public static void main(String[] args) {
		Connection connection = DatabaseUtil.getInstance().getConnection();
		if (connection != null) {
			System.out.println("Ket noi thanh cong");
		} else {
			System.out.println("Ket noi that bai");
		}
	}
}
