package com.aptech.student.management.util;

import java.util.ArrayList;
import java.util.List;

public class StringUtil {
	public static List<String> toStringList(String source, String sperator) {
		List<String> list = new ArrayList<>();
		if (source.length() > 0) {
			int lastIndex;
			int index;
			for (lastIndex = 0; (index = source.indexOf(sperator, lastIndex)) >= 0; lastIndex = index
					+ sperator.length()) {
				list.add(source.substring(lastIndex, index).trim());
			}

			if (lastIndex <= source.length()) {
				list.add(source.substring(lastIndex, source.length()).trim());
			}
		}
		return list;
	}

	public static String nvl(Object objInput, String strNullValue) {
		return objInput != null && !((String) objInput).equals("") ? objInput.toString() : strNullValue;
	}
}
