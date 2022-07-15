package com.aptech.student.management.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidatorUtil {
	public static boolean validatePassword(String password) {
		Pattern p = Pattern.compile("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\\\d)(?=.*[@$!%*?&])[A-Za-z\\\\d@$!%*?&]{6,15}");
		Matcher matcher = p.matcher(password);
		return matcher.matches();
	}
	
	public static boolean validate(String value, String regex) {
		Pattern p = Pattern.compile(regex);
		Matcher matcher = p.matcher(value);
		return matcher.matches();
	}
}
