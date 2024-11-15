package dao.jaxb;

import java.io.File;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;

import model.Product;
import model.ProductList;

public class JaxbMarshaller {
	public boolean init(ProductList inventory) {
		try {
			JAXBContext context = JAXBContext.newInstance(ProductList.class);
			Marshaller marshaller = context.createMarshaller();
			System.out.println("marshalling... ");
			ProductList products = inventory;
			LocalDate currentDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedDate = currentDate.format(formatter);
			File f = new File("jaxb/invetory_" + formattedDate + ".xml");
			marshaller.marshal(products, f);
			JOptionPane.showMessageDialog(null, "Archivo exportado", "Exported", JOptionPane.PLAIN_MESSAGE);
			return true;
		} catch (JAXBException e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "No se a podido exportar el archivo", "ERROR",
					JOptionPane.ERROR_MESSAGE);
			return false;

		}
	}
}
