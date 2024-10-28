package dao.xml;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.swing.JOptionPane;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Result;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;

import model.Product;

public class DomWriter {
	private Document document;

	public DomWriter() {
		try {
			DocumentBuilderFactory factory = DocumentBuilderFactory.newDefaultInstance();
			DocumentBuilder builder;
			builder = factory.newDocumentBuilder();
			document = builder.newDocument();
		} catch (ParserConfigurationException e) {
			System.out.println("ERROR generating document");
		}
	}

	public void generateDocument(List<Product> inventario) {
		// PARENT NODE
		// root node
		int total = 0;
		for (Product pdct_invet : inventario) {
			total++;
		}

		Element products = document.createElement("products");
		products.setAttribute("total", String.valueOf(total));
		document.appendChild(products);

		int i = 1;
		for (Product pr_invetario : inventario) {
			Element product = document.createElement("product");
			product.setAttribute("id", String.valueOf(i));
			products.appendChild(product);

			Element name = document.createElement("name");
			name.setTextContent(pr_invetario.getName());
			product.appendChild(name);
			// FINAL NODES
			// child into product with attribute and content
			Element price = document.createElement("price");
			price.setAttribute("currency", "€");
			price.setTextContent(String.valueOf(pr_invetario.getWholesalerPrice().getValue()));
			product.appendChild(price);

			// child into product with 2 attributes and content
			Element stock = document.createElement("stock");
			stock.setTextContent(String.valueOf(pr_invetario.getStock()));
			product.appendChild(stock);
			i++;
		}

		generateXml();
	}

	private String parse(int total) {
		// TODO Auto-generated method stub
		return null;
	}

	private void generateXml() {
		boolean eror = false;
		try {
			TransformerFactory factory = TransformerFactory.newInstance();
			Transformer transformer = factory.newTransformer();
			Source source = new DOMSource(document);
			// añadir la fecha al tituo del archivo
			LocalDate currentDate = LocalDate.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
			String formattedDate = currentDate.format(formatter);
			File f = new File("files/invetory_" + formattedDate + ".xml");
			// File file = new File("files/productsOutput.xml");
			FileWriter fw = new FileWriter(f);
			PrintWriter pw = new PrintWriter(fw);
			Result result = new StreamResult(pw);
			transformer.transform(source, result);
		} catch (IOException e) {
			eror = true;
			System.out.println("Error when creating writter file");
			JOptionPane.showMessageDialog(null, "Error al encontrar la carpeta donde exportar el archivo", "ERROR", JOptionPane.ERROR_MESSAGE);

		} catch (TransformerException e) {
			eror = true;
			System.out.println("Error transforming the document");
			JOptionPane.showMessageDialog(null, "Error al transforamr el documento", "ERROR",
					JOptionPane.ERROR_MESSAGE);

		}
		if (eror == false) {
			JOptionPane.showMessageDialog(null, "Archivo exportado", "Exported", JOptionPane.PLAIN_MESSAGE);
		}
	}
}
