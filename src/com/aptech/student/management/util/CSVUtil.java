package com.aptech.student.management.util;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

public class CSVUtil {
	private FileReader fileReader = null;
	private BufferedReader bufferedReader = null;
	private boolean suppressHeaders = false;
	private boolean ignoreCheckSize = false;
	private boolean exitIfMatchFirstEOF = false;
	private String delimited = ";";
	private String endOfFile = null;
	private List<String> headers;
	private List<String> values = null;
	private String strHeader = "";
	private String strCurrentLine = null;
	private String strLine = null;
	private static CSVUtil instance;

	private CSVUtil() {

	}

	public static CSVUtil getInstance() {
		if (instance == null) {
			instance = new CSVUtil();
		}
		return instance;
	}
	public void setDelimited(String delimited) {
		this.delimited = delimited;
	}

	public void setStrHeader(String strHeader) {
		this.strHeader = strHeader;
	}

	public int getColumnCount() {
		return this.headers.size();
	}

	public int getCurrentLineColumnCount() {
		return this.values.size();
	}

	public int findColumn(String field) {
		for (int i = 0; i < this.headers.size(); i++) {
			if (field.equalsIgnoreCase(this.headers.get(i))) {
				return i;
			}
		}
		return -1;
	}

	public void parseLine(String strLine) {
		if (!this.delimited.equals("")) {
			this.values = StringUtil.toStringList(strLine, this.delimited);
		}
	}

	public void openCSVFile(String path, String delimited, int ignoreRows, String endOfFile, int bufferSize)
			throws Exception {
		this.openCSVFile(path, delimited, ignoreRows, endOfFile, bufferSize, 0L);
	}

	public void openCSVFile(String path, String delimited, int ignoreRows, String endOfFile, int bufferSize,
			long lgnCharacterSkip) throws Exception {
		try {
			this.fileReader = new FileReader(path);
			this.bufferedReader = new BufferedReader(this.fileReader, bufferSize);
			this.bufferedReader.skip(lgnCharacterSkip);
			this.strCurrentLine = null;
			if (this.delimited.equals("")) {
				this.delimited = delimited;
			}
			this.endOfFile = endOfFile;
			this.parseHeader(ignoreRows);		
		} catch (Exception e) {
			this.safeCloseCSVFile();
			throw e;
		}
	}

	public void openCSVFile(String path, int bufferSize) throws Exception {
		this.openCSVFile(path, ";", 0, "", bufferSize);
	}

	public void safeCloseCSVFile() {
		if (this.headers != null) {
			this.headers.clear();
		}
		safeClose(this.bufferedReader);
		safeClose(this.fileReader);
	}

	public static void safeClose(Reader reader) {
		try {
			if (reader != null) {
				reader.close();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void parseHeader(int ignoreRows) throws Exception {
		int index;
		if (ignoreRows > 0) {
			for (index = 0; index < ignoreRows; index++) {
				this.strLine = this.bufferedReader.readLine();
				if (this.strLine == null) {
					break;
				}
			}
		}
		if (this.strHeader != null && !this.strHeader.equals("")) {
			this.strLine = this.strHeader;
		} else {
			for (this.strLine = this.bufferedReader.readLine(); this.strLine != null
					&& this.strLine.trim().equals(""); this.strLine = this.bufferedReader.readLine()) {
			}
		}
		if (this.strLine != null && !this.delimited.equals("")
				&& (this.endOfFile.equals("") || !this.strLine.startsWith(this.endOfFile))) {
			this.strHeader = StringUtil.nvl(this.strLine, "");
			this.headers = StringUtil.toStringList(this.strHeader, this.delimited);
			if (this.suppressHeaders) {
				for (index = 0; index < this.headers.size(); ++index) {
					this.headers.set(index, "COLUMN" + index);
				}
			} else {
				this.strLine = this.bufferedReader.readLine();
			}

			for (index = 0; index < this.headers.size(); ++index) {
				this.headers.set(index, this.headers.get(index).toUpperCase());
			}
		}
	}

	public void parseValues() throws Exception {
		this.parseLine(this.strLine);
		if (this.headers != null && this.values != null && !this.ignoreCheckSize
				&& this.values.size() != this.headers.size()) {
			throw new Exception("Number of columns does not match header");
		}
	}

	public boolean next() throws Exception {
		if (this.strLine != null && (this.endOfFile.equals("") || !this.strLine.startsWith(this.endOfFile))
				&& (!this.exitIfMatchFirstEOF || !this.strLine.equals(this.endOfFile))) {
			if (!this.strLine.trim().equals("")) {
				this.parseValues();
				this.strCurrentLine = this.strLine;
				this.strLine = this.bufferedReader.readLine();
				return true;
			} else {
				this.strLine = this.bufferedReader.readLine();
				return this.next();
			}
		} else {
			return false;
		}
	}

	public String getString(int intIndex) {
		return intIndex < 0 ? null : StringUtil.nvl(this.values.get(intIndex), "");
	}

	public String getString(String strField) {
		return this.getString(this.findColumn(strField));
	}

	public String getLine() {
		return this.strCurrentLine;
	}

	public String getHeader() {
		return this.strHeader;
	}
	
	
	public List<String> getHeaders() {
		return headers;
	}

	public void setHeaders(List<String> headers) {
		this.headers = headers;
	}

	public List<String> getValues() {
		return values;
	}

	public void setValues(List<String> values) {
		this.values = values;
	}

	public static void main(String[] args) {
		CSVUtil csvUtil = new CSVUtil();
		try {
			csvUtil.openCSVFile("D:/data.csv", "",0, "", 8196);
			csvUtil.next();
				System.out.println(csvUtil.getValues());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
