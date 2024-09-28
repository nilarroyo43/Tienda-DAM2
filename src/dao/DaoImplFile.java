package dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

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
			Product p1 = new Product(name, am, available, stock);
			inventorio.add(p1);

			miLinea = br.readLine();
		}
		fr.close();
		br.close();
		return inventorio;

	}

	@Override
	public boolean writeInventory(List<Product> inventario) {
		File f = new File(
				System.getProperty("user.dir") + File.separator + "files" + File.separator + "inventory_exported.txt");
		try {
		
			FileWriter fw = new FileWriter(f, true);
			PrintWriter pw = new PrintWriter(fw);
			int index = 1;
			for (Product product : inventario) {
				StringBuilder ProductLine = new StringBuilder(index + ";Product:" + product.getName() + ";"
						+ "wholeseler Price:" + product.getWholesalerPrice().getValue() + ";" + "Available:"
						+ product.isAvailable() + ";" + "Stock:" + product.getStock() + ";");
				pw.write(ProductLine.toString());
				fw.write("\n");
				index++;
			}
			pw.close();
			fw.close();
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
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
}
