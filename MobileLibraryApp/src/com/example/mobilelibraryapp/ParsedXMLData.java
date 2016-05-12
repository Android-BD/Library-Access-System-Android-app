package com.example.mobilelibraryapp;

public class ParsedXMLData
{

	private String s = null; // holds string sought from XML
	private int i = 0; // holds integer sought from XML

	public String getString() {
		return s;
	}

	public void setString(String extractedString) {
		this.s = extractedString;
	}

	public int getInt() {
		return i;
	}

	public void setInt(int extractedInt) {
		this.i = extractedInt;
	}

	public String toString() {
		return "String from XML = " + this.s + "\n" + "Int from XML = "
				+ this.i;
	}
	
	
}
