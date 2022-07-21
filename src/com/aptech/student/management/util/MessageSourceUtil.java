package com.aptech.student.management.util;

import java.text.NumberFormat;
import java.util.Locale;
import java.util.ResourceBundle;

public class MessageSourceUtil {
	private static MessageSourceUtil instance;
	private ResourceBundle bundle = null;

	private MessageSourceUtil() {
		Locale locale = new Locale("vi", "VN");
		bundle = ResourceBundle.getBundle("messages", locale);
	}

	public static MessageSourceUtil getInstance() {
		if (instance == null) {
			instance = new MessageSourceUtil();
		}
		return instance;
	}

	public String getProperty(String key) {
		return this.bundle.getString(key);
	}

	public String getProperty(String key, Object ...args) {
		String format = String.format(this.bundle.getString(key), args);
		return format;
	}
	
	public static void main(String[] args) {
		NumberFormat numberFormat = NumberFormat.getInstance(new Locale("vi", "VN"));
		System.out.println(numberFormat.format(2000000));
		
		System.out.println(MessageSourceUtil.getInstance().getProperty("confirm_delete", new Object[] {12345}));
	}
}
