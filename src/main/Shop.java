package main;

import model.Product;
import model.Sale;
import model.Amount;
import model.Cliente;
import model.Employee;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import dao.DaoImplFile;
import dao.DaoImplHibernate;
import dao.DaoImplJDBC;
import dao.DaoImplJaxb;
import dao.DaoImplMongoDB;
import dao.DaoImplXml;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime; // Importa la clase LocalDateTime

public class Shop {
	private Amount cash = new Amount(100.00, "€");

	public int numberProducts;
	// private ArrayList<Sale> sales;
	// public DaoImplFile shopDao = new DaoImplFile();
	// public DaoImplJaxb shopDao = new DaoImplJaxb();
	// public DaoImplXml shopDao = new DaoImplXml();
	// public DaoImplJDBC shopDao = new DaoImplJDBC();
	//public DaoImplHibernate shopDao = new DaoImplHibernate();
	public DaoImplMongoDB shopDao = new DaoImplMongoDB();
	public List<Product> inventory;

	// int sale_num = 0;

	// final static double TAX_RATE = 1.04;

	public Shop() throws IOException {
		cash = new Amount(150.0, "€");
		shopDao.connect();
		this.readInvetory();

	}

	/*
	 * public static void main(String[] args) throws IOException { Shop shop = new
	 * Shop(); System.out.println(shop.inventory);
	 * 
	 * }
	 * 
	 * /* Scanner scanner = new Scanner(System.in); int opcion = 0; boolean exit =
	 * false; shop.initSession(); do {
	 * System.out.println("===========================");
	 * System.out.println("Menu principal miTienda.com");
	 * System.out.println("===========================");
	 * System.out.println("1) Contar caja");
	 * System.out.println("2) Añadir producto");
	 * System.out.println("3) Añadir stock");
	 * System.out.println("4) Marcar producto proxima caducidad");
	 * System.out.println("5) Ver inventario"); System.out.println("6) Venta");
	 * System.out.println("7) Ver ventas"); System.out.println("8) Salir programa");
	 * System.out.println("9) Ver ventas totales");
	 * System.out.println("10) Eliminar producto");
	 * System.out.println("Seleccione una opción:"); opcion = scanner.nextInt();
	 * 
	 * switch (opcion) { case 1: shop.showCash(); break;
	 * 
	 * case 2: shop.addProduct(); break;
	 * 
	 * case 3: shop.addStock(); break;
	 * 
	 * case 4: shop.setExpired(); break;
	 * 
	 * case 5: shop.showInventory(); break;
	 * 
	 * case 6: shop.sale(); break;
	 * 
	 * case 7: shop.showSales(); break;
	 * 
	 * case 8: exit = true; break;
	 * 
	 * case 9: shop.showSalesValue(); break; case 10: shop.deleteProduct(); break;
	 * 
	 * }
	 * 
	 * } while (!exit); }
	 */

	/**
	 * load initial inventory to shop
	 * 
	 * @throws IOException
	 */

	public void readInvetory() throws IOException {
		List<Product> inventario = shopDao.getInventory();
		if (inventario != null) {
			this.setInvetory(inventario);
			for (Product p : inventario) {
				System.out.println(p);
			}
		} else {
			System.out.println("error al cargar el invetario");
		}
	}

	public void setInvetory(List<Product> list) {
		this.inventory = (ArrayList<Product>) list;

	}

	public boolean writeInventory() throws IOException {
		boolean exported = shopDao.writeInventory(inventory);
		if (exported) {
			return true;
		} else {
			return false;
		}
	}

	/*
	 * 
	 * /** show current total cash
	 */
	private void showCash() {
		System.out.println("Dinero actual: ");
		System.out.println(cash);
	}

	/**
	 * add a new product to inventory getting data from console
	 */
	/*
	 * public void addProduct() { if (isInventoryFull()) {
	 * System.out.println("No se pueden añadir más productos"); return; }
	 * 
	 * Scanner scanner = new Scanner(System.in); System.out.print("Nombre: ");
	 * String name = scanner.nextLine();
	 * 
	 * boolean product = productExists(name);
	 * 
	 * if (!product) { System.out.print("Precio mayorista: "); double
	 * wholesalerPrice = scanner.nextDouble(); System.out.print("Stock: "); int
	 * stock = scanner.nextInt();
	 * 
	 * inventory.add(new Product(name, new Amount(wholesalerPrice, "€"), stock));
	 * numberProducts++; } else {
	 * System.out.println("El producto ya existe en el inventario."); } }
	 * 
	 * /** add stock for a specific product
	 */
	public Amount getCash() {
		return cash;
	}

	public boolean addProductSql(Product product) throws IOException {
		boolean added = shopDao.addProduct(product);
		if (added) {
			return true;
		} else {
			return false;
		}

	}

	public boolean updateStockSql(Product product) throws IOException {
		boolean updated = shopDao.updateProduct(product.getName(), product.getStock());
		if (updated) {
			return true;
		} else {
			return false;
		}

	}

	public boolean deleteProductSql(Product product) throws IOException {
		boolean deleted = shopDao.deleteProduct(product);
		if (deleted) {
			return true;
		} else {
			return false;
		}
	}

	public void addStock() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Seleccione un nombre de producto: ");
		String name = scanner.next();
		Product product = findProduct(name);

		if (product != null) {
			System.out.print("Seleccione la cantidad a añadir: ");
			int stock = scanner.nextInt();
			int newStock = product.getStock() + stock;
			product.setStock(newStock);
			System.out.println("El stock del producto " + name + " ha sido actualizado a " + product.getStock());
		} else {
			System.out.println("No se ha encontrado el producto con nombre " + name);
		}
	}


	/*
	 * private void setExpired() { Scanner scanner = new Scanner(System.in);
	 * System.out.print("Seleccione un nombre de producto: "); String name =
	 * scanner.next();
	 * 
	 * Product product = findProduct(name);
	 * 
	 * if (product != null) { product.expire();
	 * System.out.println("El precio de venta al público del producto " + name +
	 * " ha sido actualizado a " + product.getPublicPrice()); } }
	 * 
	 * /** show all inventory
	 */

	public void showInventory() {
		System.out.println("---Contenido actual de la tienda---");
		System.out.print("========================================");
		System.out.println();
		for (Product product : inventory) {
			if (product != null) {
				System.out.print(product);
			} else {
				break;
			}
			System.out.println();
		}
		System.out.println();
	}

	public void showInventorySale() {
		System.out.println("\n---Contenido actual de la tienda---");
		System.out.print("========================================");
		System.out.println();
		for (Product product : inventory) {
			if (product != null) {
				System.out.print(product);
			} else {
				break;
			}
			System.out.println();
		}
		System.out.println();
	}

	// make a sale of products to a client
	/*
	 * public void sale() { Scanner sc = new Scanner(System.in);
	 * System.out.println("Realizar venta, escribir nombre cliente"); Cliente client
	 * = new Cliente(sc.next(), 456, new Amount(50.00, "€"));
	 * 
	 * Amount totalAmount = new Amount(0.0, "€"); String name = ""; int product_num
	 * = 0; ArrayList<Product> products = new ArrayList<Product>(); int quantity;
	 * 
	 * // Guardar la fecha y hora actual LocalDateTime dateTime =
	 * LocalDateTime.now();
	 * 
	 * while (!name.equals("0")) { sc = new Scanner(System.in); showInventorySale();
	 * 
	 * System.out.
	 * println("Introduce el nombre del producto, escribir 0 para terminar:"); name
	 * = sc.nextLine();
	 * 
	 * if (name.equals("0")) { break; }
	 * 
	 * Product product = findProduct(name); boolean productAvailable = false;
	 * 
	 * if (product != null && product.isAvailable()) {
	 * System.out.println("Introduce la cantidad deseada:"); quantity =
	 * sc.nextInt();
	 * 
	 * if (product.getStock() == 0) { product.setAviable(false);
	 * System.out.println("Producto sin stock."); } else if (quantity >
	 * product.getStock()) { product.setAviable(false);
	 * System.out.println("Cantidad mayor al stock disponible del producto.");
	 * break; } else { productAvailable = true; products.add(product_num, product);
	 * product_num++; totalAmount.setValue(totalAmount.getValue() +
	 * (product.getPublicPrice().getValue() * quantity));
	 * product.setStock(product.getStock() - quantity);
	 * System.out.println("Producto añadido con éxito"); } }
	 * 
	 * /* if (!productAvailable) { System.out.println("Producto no encontrado"); } }
	 * 
	 * if (totalAmount.getValue() > 0) { totalAmount.setValue(totalAmount.getValue()
	 * TAX_RATE);
	 * 
	 * // Guardar la venta con la fecha y hora actual sales.add(sale_num, new
	 * Sale(client, products, totalAmount, dateTime)); sale_num++;
	 * 
	 * cash.setValue(cash.getValue() + totalAmount.getValue());
	 * System.out.println("Venta realizada con éxito, total: " + totalAmount);
	 * boolean deber = client.pay(totalAmount); if (!deber) { // lo que debe el
	 * cliente System.out.println("el cliente " + client.getName() + " debe " +
	 * (client.getCantidad().getValue() * -1.00 ) + "€"); }else
	 * {System.out.println("el cliente queda con " + client.getCantidad().getValue()
	 * + " en su cuenta");} } else { System.out.println("Venta no realizada."); } }
	 */
	/**
	 * show all sales
	 */

	/*
	 * public void showSales() { System.out.println("Lista de ventas:"); for (Sale
	 * sale : sales) { if (sale != null) { System.out.println(sale);
	 * System.out.println(sale.getFormattedDateTime());
	 * 
	 * } } System.out.
	 * println("Desea añadir la inforamcion de las ventas en un archivo aparte?");
	 * System.out.println("S/N"); Scanner scanner = new Scanner(System.in); String
	 * add = scanner.next(); if (add.equalsIgnoreCase("s")) { createFile(); } }
	 * 
	 * /** delete a product from inventory
	 */

	public void deleteProduct() {
		Scanner scanner = new Scanner(System.in);
		System.out.print("Nombre: ");
		String name = scanner.nextLine();

		boolean product = productExists(name);

		if (product) {
			Product prod = findProduct(name);
			if (prod != null) {
				for (int i = 0; i < inventory.size(); i++) {
					if (inventory.get(i) != null && inventory.get(i).getName().equalsIgnoreCase(name)) {
						inventory.remove(i);
						System.out.println("El stock del producto " + name + " ha sido eliminado ");
					}
				}
			} else {
				System.out.println("El producto no existe en el inventario.");
			}
		}
	}

	/**
	 * show total sales value
	 */
	/*
	 * private void showSalesValue() { Amount totalSales = new Amount(0.0, "€");
	 * System.out.println("Precio total de ventas: "); for (Sale sale : sales) { if
	 * (sale != null) { totalSales.setValue(totalSales.getValue() +
	 * sale.getAmount().getValue()); } } System.out.println(totalSales); }
	 * 
	 * /** add a product to inventory
	 */

	public void addProduct(Product product) {
		if (isInventoryFull()) {
			System.out.println("No se pueden añadir más productos, se ha alcanzado el máximo de " + inventory.size());
			return;
		}
		inventory.add(numberProducts, product);
		numberProducts++;
	}

	/**
	 * check if inventory is full or not
	 */

	public boolean isInventoryFull() {
		return numberProducts == 10;
	}

	/**
	 * find product by name
	 */
	public Product findProduct(String name) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) != null && inventory.get(i).getName().equalsIgnoreCase(name)) {
				return inventory.get(i);
			}
		}
		return null;
	}

	public boolean productExists(String name) {
		for (int i = 0; i < inventory.size(); i++) {
			if (inventory.get(i) != null && inventory.get(i).getName().equalsIgnoreCase(name)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * public void createFile() {
	 * 
	 * File f = new File( System.getProperty("user.dir") + File.separator + "files"
	 * + File.separator + "sales_yyyy-mm-dd.txt"); try { FileWriter fw; fw = new
	 * FileWriter(f, true); PrintWriter pw = new PrintWriter(fw);
	 * 
	 * int index = 1;
	 * 
	 * for (Sale sale : sales) { StringBuilder firstSaleLine = new StringBuilder(
	 * index + ";Client=" + sale.getClient() + ";Date=" +
	 * sale.getFormattedDateTime() + ";"); pw.write(firstSaleLine.toString());
	 * fw.write("\n"); StringBuilder productLine = new StringBuilder(); for (Product
	 * product : sale.getProducts()) { productLine.append(product.getName() + "," +
	 * product.getPublicPrice() + ";"); } StringBuilder secondSaleLine = new
	 * StringBuilder(index + ";Products=" + productLine + ";");
	 * pw.write(secondSaleLine.toString()); fw.write("\n"); StringBuilder
	 * amountSaleLine = new StringBuilder(index + ";Amount=" + sale.getAmount() +
	 * ";"); pw.write(amountSaleLine.toString()); fw.write("\n"); index++;
	 * 
	 * } pw.close(); fw.close();
	 * 
	 * } catch (IOException e) { e.printStackTrace(); }
	 * 
	 * }
	 * 
	 * public void initSession() { boolean finish = false; Employee employee = new
	 * Employee(); do { Scanner scanner = new Scanner(System.in);
	 * System.out.println("escribe el numero de empelado:"); int user =
	 * scanner.nextInt(); System.out.println("escribe la contraseña del empelado:");
	 * String pass = scanner.next(); finish = employee.logEmployee(user, pass); if
	 * (finish == false) { System.out.println("numero o contraseña incorrecta"); } }
	 * while (!finish); System.out.println("sesion iniciada"); }
	 */
}
