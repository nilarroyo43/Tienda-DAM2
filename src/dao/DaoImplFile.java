package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;

import model.Amount;
import model.Employee;
import model.Product;
import model.Sale;

public class DaoImplFile implements Dao {

	private ArrayList<Sale> sales;

	@Override
	public ArrayList<Product> getInventory() throws IOException {
		ArrayList<Product> inventorio = new ArrayList<>();
		File f = new File(System.getProperty("user.dir") + File.separator + "files/inputInventory.txt");

		FileReader fr = new FileReader(f);
		BufferedReader br = new BufferedReader(fr);

		String miLinea = br.readLine();

		while (miLinea != null) {
			String[] partes1 = miLinea.split(";");
			String name = "";
			Double wholesalerPrice = 0.00;
			Amount am = new Amount(0.00, "€");
			boolean available = true;
			int stock = 0;

			for (int i = 0; i < partes1.length; i++) {
				String[] partes2 = partes1[i].split(":");
				switch (i) {
				case 0:
					name = partes2[1];

					break;
				case 1:
					wholesalerPrice = Double.parseDouble(partes2[1]);
					am = new Amount(wholesalerPrice, "€");

					break;
				case 2:
					available = Boolean.parseBoolean(partes2[1]);

					break;
				case 3:
					stock = Integer.parseInt(partes2[1]);

					break;
				default:
					break;
				}

			}
			Product p1 = new Product(name, am, stock);
			inventorio.add(p1);

			miLinea = br.readLine();
		}
		fr.close();
		br.close();
		return inventorio;

	}

	@Override
	public boolean writeInventory(List<Product> inventario) throws IOException {
		File newFolder = new File(System.getProperty("user.dir") + File.separator + "files");
		if (!newFolder.exists()) {
			newFolder.mkdir();
		}
		LocalDate currentDate = LocalDate.now();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
		String formattedDate = currentDate.format(formatter);
		File f = new File(newFolder, "invetory_" + formattedDate + ".txt");
		if (!f.exists()) {
			f.createNewFile();
		} else {
			 JOptionPane.showMessageDialog( null, "No se a podido exportar el archivo debido a que ya existe", "ERROR", JOptionPane.ERROR_MESSAGE);
			return false;
		}
		FileWriter fw = new FileWriter(f, true);
		PrintWriter pw = new PrintWriter(fw);
		int index = 1;
		for (Product product : inventario) {
			StringBuilder ProductLine = new StringBuilder(
					index + ";Product:" + product.getName() + ";" + "Stock:" + product.getStock() + ";");
			pw.write(ProductLine.toString());
			fw.write("\n");
			index++;
		}
		StringBuilder totalLine = new StringBuilder("Numero total de productos:" + (index-1) + ";");
		pw.write(totalLine.toString());
		pw.close();
		fw.close();
		  JOptionPane.showMessageDialog(null, "Archivo exportado", "Exported", JOptionPane.PLAIN_MESSAGE);
		return true;
	}

	@Override
	public Employee getEmployee(int employeeid, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public void connect() {
		// TODO Auto-generated method stub

	}


	@Override
	public boolean deleteProduct(Product producto) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addProduct(Product producto) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateProduct(String name, int stock) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	
}
