package com.aptech.student.management.util;

import javax.swing.JFrame;

import com.aptech.student.management.services.LoginService;

public class AuthenticationUtil {
	
	private static AuthenticationUtil instance;
	
	private AuthenticationUtil () {
		
	}
	
	public static AuthenticationUtil getInstance() {
		if (instance == null) {
			instance = new AuthenticationUtil();
		}
		return instance;
	}
	
	public void checkLogin(JFrame frame) {
		if (LoginService.getInstance().getCurrentUser() == null) {
			frame.setVisible(true);
			return;
		}
	}
}
