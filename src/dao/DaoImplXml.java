package dao;

import java.io.File;
import java.io.IOException;
import java.util.List;

import javax.xml.parsers.ParserConfigurationException;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.SAXException;

import dao.xml.SaxReader;
import model.Employee;
import model.Product;

public class DaoImplXml implements Dao {
	public SaxReader sax = new SaxReader();
	@Override
	public void connect() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Employee getEmployee(int employeeid, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Product> getInventory() throws IOException {
		List<Product> inventario = null;
				SAXParserFactory factory = SAXParserFactory.newInstance();
				SAXParser parser;
				try {
					parser = factory.newSAXParser();
					File file = new File ("files/inputInvetory.xml");
					SaxReader saxReader = new SaxReader();
					parser.parse(file, saxReader);
					inventario = saxReader.getProducts();
					
				} catch (ParserConfigurationException | SAXException e) {
					System.out.println("ERROR creating the parser");
				} catch (IOException e) {
					System.out.println("ERROR file not found");
				}
				return inventario;
	}

	@Override
	public boolean writeInventory(List<Product> inventario) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub
		
	}

}
