package com.aptech.student.management.services;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.aptech.student.management.model.Student;
import com.aptech.student.management.util.DatabaseUtil;

public class StudentService {
	private static StudentService instance;
	private final static Logger LOG = LogManager.getLogger(StudentService.class);

	private StudentService() {

	}

	public static StudentService getInstance() {
		if (instance == null) {
			instance = new StudentService();
		}
		return instance;
	}

	public List<Student> getStudents(String keyword) {
		List<Student> students = new ArrayList<>();
		Connection con = DatabaseUtil.getInstance().getConnection();
		try {
			String sql = "";
			if (keyword != null && !keyword.equals("")) {
				sql = "select * from student where concat(id,name,palace) like ? order by id desc";
			} else {
				sql = "select * from student order by id desc";
			} 
			PreparedStatement statement = con.prepareStatement(sql);
			if (keyword != null && !keyword.equals("")) {
				statement.setString(1, "%" + keyword + "%");
			}
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				long id = rs.getLong(1);
				String name = rs.getString(2);
				String palace = rs.getString(3);
				boolean gender = rs.getBoolean(4);
				String dof = rs.getString(5);
				float math = rs.getFloat(6);
				float physical = rs.getFloat(7);
				float chemistry = rs.getFloat(8);
				Student student = new Student(id, name, palace, gender, dof, math, physical, chemistry);
				students.add(student);
			}
		} catch (Exception e) {
			LOG.error("Lỗi khi thực hiện lấy danh sách học sinh ", e);
		} finally {
			DatabaseUtil.getInstance().closeConnection(con);
		}
		return students;
	}
	
	public List<Student> filterStudents(String keyword) {
		List<Student> students = new ArrayList<>();
		Connection con = DatabaseUtil.getInstance().getConnection();
		try {
			//String sql = "select * from student where id = ? (or name like '%?%' or palace like '%?%') order by id desc";
			String sql = "select * from student where concat(id,name,palace) like '%?%' order by id desc";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, keyword);
			ResultSet rs = statement.executeQuery();
			while (rs.next()) {
				long id = rs.getLong(1);
				String name = rs.getString(2);
				String palace = rs.getString(3);
				boolean gender = rs.getBoolean(4);
				String dof = rs.getString(5);
				float math = rs.getFloat(6);
				float physical = rs.getFloat(7);
				float chemistry = rs.getFloat(8);
				Student student = new Student(id, name, palace, gender, dof, math, physical, chemistry);
				students.add(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			DatabaseUtil.getInstance().closeConnection(con);
		}
		return students;
	}

	public boolean update(Student student) {
		boolean output = false;
		Connection con = DatabaseUtil.getInstance().getConnection();
		try {
			con.setAutoCommit(false);
			String sql = "update student set name= ?,gender = ?, date_of_birth = ?, math = ?, chemistry = ?, physical = ? where id = ?";
			PreparedStatement statement = con.prepareStatement(sql);
			statement.setString(1, student.getName());
			statement.setBoolean(2, student.isGender());
			statement.setString(3, student.getDateOfBirth());
			statement.setFloat(4, student.getMath());
			statement.setFloat(5, student.getChemistry());
			statement.setFloat(6, student.getPhysical());
			statement.setLong(7, student.getId());
			int result = statement.executeUpdate();
			con.commit();
			output = result == 1 ? true : false;
			statement.close();
		} catch (Exception e) {
			try {
				con.rollback();
			} catch (Exception e1) {
				// TODO: handle exception
				e1.printStackTrace();
			}
			e.printStackTrace();
		} finally {
			DatabaseUtil.getInstance().closeConnection(con);
		}
		return output;
	}

	public boolean delete(Long studentId) {
		Connection connection = DatabaseUtil.getInstance().getConnection();
		boolean result;
		try {
			String sql = "Delete from student where id = ?";
			PreparedStatement preparedStatement = connection.prepareStatement(sql);
			preparedStatement.setLong(1, studentId);
			int out = preparedStatement.executeUpdate();
			result = out == 1 ? true : false;
			preparedStatement.close();
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		} finally {
			DatabaseUtil.getInstance().closeConnection(connection);
		}
		return result;
	}
}
