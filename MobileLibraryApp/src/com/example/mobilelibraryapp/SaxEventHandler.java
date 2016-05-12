package com.example.mobilelibraryapp;

import com.example.mobilelibraryapp.ParsedXMLData;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import android.util.Log;

public class SaxEventHandler extends DefaultHandler 
{
	private final static String TAG = "ParseXML";

	// flags to help keep track of open tags
	private boolean xxxTag = false;
	private boolean yyyTag = false;
	private boolean zzz1Tag = false;

	private ParsedXMLData xmlData = new ParsedXMLData();

	public ParsedXMLData getParsedData() {
		return this.xmlData;
	}

	// called when start of XML document encountered
	@Override
	public void startDocument() throws SAXException {
		this.xmlData = new ParsedXMLData();
	}

	// called when end of XML document encountered
	@Override
	public void endDocument() throws SAXException {
		// Nothing to do
	}

	// called on start tags <...>
	@Override
	public void startElement(String namespaceURI, String localName,
			String qName, Attributes attrs) throws SAXException {
		Log.d(TAG, "(startElement) Namespace URI: " + namespaceURI
				+ " localName: " + localName + " qName: " + qName
				+ " attrs: \n"
				+ displayAttributes(attrs));
		if (localName.equals("xml")) {
			this.xxxTag = true;
		} else if (localName.equals("Author")) {
			this.yyyTag = true;
		} else if (localName.equals("zzz1")) {
			this.zzz1Tag = true;
		} else if (localName.equals("Name")) {
			// Extract an Attribute
			String attrVal = attrs.getValue("zzz2-attribute");
			int i = Integer.parseInt(attrVal);
			xmlData.setInt(i);
		}
	}

	// called on end tags </...>
	@Override
	public void endElement(String namespaceURI, String localName, String qName)
			throws SAXException {
		Log.d(TAG, "(endElement) Namespace URI: " + namespaceURI
				+ " localName: " + localName + " qName: " + qName);
		if (localName.equals("xml")) {
			this.xxxTag = false;
		} else if (localName.equals("Author")) {
			this.yyyTag = false;
		} else if (localName.equals("zzz1")) {
			this.zzz1Tag = false;
		} else if (localName.equals("Name")) {
			// do nothing
		}
	}

	// called on <tag>characters</tag>
	@Override
	public void characters(char ch[], int start, int length) {
		if (this.zzz1Tag) {
			xmlData.setString(new String(ch, start, length));
		}
	}

	// neatly display attribute values from XML tag
	private StringBuilder displayAttributes(Attributes attrs) {
		int attributeCount = attrs.getLength();
		StringBuilder s = new StringBuilder("");

		for (int k = 0; k < attributeCount; ++k)
			s.append(attrs.getLocalName(k) + ": " + attrs.getValue(k) + "\n");

		return s;
	}
	
	
	
}
