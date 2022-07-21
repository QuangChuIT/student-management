package com.aptech.student.management.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Date;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aptech.student.management.model.User;
import com.aptech.student.management.util.DatabaseUtil;

public class LoginService {
	private User currentUser = null;
	private static LoginService instance;
	private static final Logger LOG = LogManager.getLogger(LoginService.class);
	private LoginService() {

	}

	public static LoginService getInstance() {
		if (instance == null) {
			instance = new LoginService();
		}
		return instance;
	}

	public void login(String username, String password) {
		Connection connection = DatabaseUtil.getInstance().getConnection();
		try {
			LOG.info("Username {} password {}", username, password);
			String sql = "select * from user where username = ? and password = ?";
			PreparedStatement statement = connection.prepareStatement(sql);
			statement.setString(1, username);
			statement.setString(2, password);
			ResultSet resultSet = statement.executeQuery();
			while (resultSet.next()) {
				long id = resultSet.getLong("id");
				String uname = resultSet.getString("username");
				String pass = resultSet.getString("password");
				String mobile = resultSet.getString("mobile");
				String email = resultSet.getString("email");
				boolean status = resultSet.getBoolean("status");
				Date lastLoginDate = resultSet.getDate("last_login_date");
				int loginAttempCount = resultSet.getInt("login_attemp_count");
				Date createdDate = resultSet.getDate("created_date");
				Date modified_date = resultSet.getDate("modified_date");
				this.currentUser = new User(id, uname, pass, mobile, email, status, lastLoginDate, loginAttempCount,
						createdDate, modified_date);
			}
			resultSet.close();
			statement.close();
		} catch (Exception e) {
			LOG.error("Error when login", e);
		} finally {
			DatabaseUtil.getInstance().closeConnection(connection);
		}
	}

	public User getCurrentUser() {
		return this.currentUser;
	}
}
