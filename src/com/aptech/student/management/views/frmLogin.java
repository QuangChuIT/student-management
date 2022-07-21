package com.aptech.student.management.views;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import com.aptech.student.management.services.LoginService;
import com.aptech.student.management.util.MessageSourceUtil;

import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;

public class frmLogin extends JFrame implements ActionListener{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JTextField txtUsername;
	private JPasswordField txtPassword;
	private JButton btnLogin, btnReset;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					frmLogin frame = new frmLogin();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public frmLogin() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 575, 360);
		contentPane = new JPanel();
		contentPane.setBounds(new Rectangle(0, 0, 30, 30));
		contentPane.setFont(new Font("Tahoma", Font.PLAIN, 15));
		setTitle("Đăng nhập hệ thống");
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setLocationRelativeTo(null);
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		JLabel lblNewLabel = new JLabel("ĐĂNG NHẬP");
		lblNewLabel.setBounds(5, 5, 526, 25);
		lblNewLabel.setForeground(Color.GREEN);
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Tahoma", Font.PLAIN, 20));
		contentPane.add(lblNewLabel);
		
		JLabel lblNewLabel_1 = new JLabel("Tài khoản");
		lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_1.setBounds(69, 87, 83, 25);
		contentPane.add(lblNewLabel_1);
		
		txtUsername = new JTextField();
		txtUsername.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtUsername.setBounds(158, 77, 313, 50);
		contentPane.add(txtUsername);
		txtUsername.setColumns(10);
		
		JLabel lblNewLabel_2 = new JLabel("Mật khẩu");
		lblNewLabel_2.setFont(new Font("Tahoma", Font.PLAIN, 15));
		lblNewLabel_2.setBounds(69, 151, 83, 34);
		contentPane.add(lblNewLabel_2);
		
		txtPassword = new JPasswordField();
		txtPassword.setFont(new Font("Tahoma", Font.PLAIN, 16));
		txtPassword.setBounds(158, 146, 313, 50);
		contentPane.add(txtPassword);
		
		btnLogin = new JButton("Đăng nhập");
		btnLogin.addActionListener(this);
		btnLogin.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnLogin.setBounds(158, 221, 127, 35);
		contentPane.add(btnLogin);
		
		btnReset = new JButton("Làm mới");
		btnReset.setFont(new Font("Tahoma", Font.PLAIN, 15));
		btnReset.setBounds(353, 221, 118, 34);
		btnReset.addActionListener(this);
		contentPane.add(btnReset);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		Object cmd = e.getSource();
		if (cmd.equals(btnLogin)) {
			this.btnLoginClick();
		}
		if (cmd.equals(btnReset)) {
			this.btnResetClick();
		}
	}
	
	private void btnLoginClick() {
		String username = this.txtUsername.getText();
		String password = String.valueOf(this.txtPassword.getPassword());
		if (username.equals("") || password.equals("")) {
			JOptionPane.showMessageDialog(contentPane, MessageSourceUtil.getInstance().getProperty("required_username_password"));
			return;	
		}
		LoginService.getInstance().login(username, password);
		if (LoginService.getInstance().getCurrentUser() != null) {
			JOptionPane.showMessageDialog(contentPane, MessageSourceUtil.getInstance().getProperty("login_success"));
			new frmMain().setVisible(true);
			this.setVisible(false);
		} else {
			JOptionPane.showMessageDialog(contentPane, MessageSourceUtil.getInstance().getProperty("login_fail"));
		}
	}

	private void btnResetClick () {
		this.txtUsername.setText("");
		this.txtPassword.setText("");
	}
}
