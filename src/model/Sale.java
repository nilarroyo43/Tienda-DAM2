package model;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Sale {
	Cliente clients;
	ArrayList<Product> products;
	private Amount amount;
	private LocalDateTime dateTime;
	
	
	public Sale(Cliente clients, ArrayList<Product> products, Amount amount,LocalDateTime dateTime ) {
		super();
		this.clients = clients;
		this.products = products;
		this.amount = amount;
		this.setDateTime(dateTime);
	}
	
	public Cliente getClient() {
		return clients;
	}
	public void setClient(Cliente clients) {
		this.clients = clients;
	}
	public ArrayList<Product> getProducts() {
		return products;
	}
	public void setProducts(ArrayList<Product> products) {
		this.products = products;
	}
	public Amount getAmount() {
		return amount;
	}
	public void setAmount(Amount amount) {
		this.amount = amount;
	}
	public LocalDateTime getDateTime() {
		return dateTime;
	}
	
	public void setDateTime(LocalDateTime dateTime) {
		this.dateTime = dateTime;
	}
	public String getFormattedDateTime() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-YYYY HH:mm:ss");
		return dateTime.format(formatter);	
	}
	
	@Override
	public String toString() {
	    StringBuilder boughtProducts = new StringBuilder();
	    
	    for (Product product : products) {
	        if (product != null) {
	            boughtProducts.append(product.getName()).append(", ");
	        }
	    }
	    
	    String productsString = boughtProducts.toString();
	    
	    return "Venta --> Client = " + clients.getName() + " | Products = " + productsString + " | Amount = " + amount;
	}
	
}
