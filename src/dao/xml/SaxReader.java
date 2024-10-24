package dao.xml;

import java.util.ArrayList;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import model.Amount;
import model.Product;

public class SaxReader extends DefaultHandler {
	ArrayList<Product> products;
	Product product;
	String value;
	String parsedElement;

	public ArrayList<Product> getProducts() {
		return products;
	}

	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}

	@Override
	public void startDocument() throws SAXException {
		this.products = new ArrayList<>();
	}

	@Override
	public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
		this.parsedElement = qName;
		switch (qName) {
		case "product":
			this.product = new Product(attributes.getValue("name") != null ? attributes.getValue("name") : "empty",
					null, true, 0);
			break;
		case "wholesalerPrice":
			String currency = attributes.getValue("currency");
			break;
		}

	}

	@Override
	public void characters(char[] ch, int start, int length) throws SAXException {
		value = new String(ch, start, length);
		switch (parsedElement) {
		case "product":
			break;
		case "wholesalerPrice":
			try {
				this.product.setWholesalerPrice(Amount.valueOf(value)); 
																		
			} catch (NumberFormatException e) {
				System.err.println("Error al convertir wholesalerPrice: " + value);
			}
			break;
		case "stock":
			this.product.setStock(Integer.valueOf(value));
			break;
		}
	}

	@Override
	public void endElement(String uri, String localName, String qName) throws SAXException {
		
		if (qName.equals("product")) {
			this.products.add(product);
		}
		this.parsedElement = "";
	}

	@Override
	public void endDocument() throws SAXException {
		printDocument();
	}

	private void printDocument() {
		for (Product p : products) {
			System.out.println(p.toString());
		}
	}

}
