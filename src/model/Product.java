package model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "product")
@XmlType(propOrder = { "id", "name", "available", "wholesalerPrice", "publicPrice", "stock" })
@Entity
@Table(name = "inventory")
public class Product {
	private static int totalProducts = 0;
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Transient
	private Amount wholesalerPrice;
	
	@Column(name = "wholesalerPrice")
	private double price;

	@Transient
	private Amount publiPrice;

	@Column(name = "stock")
	private int stock;

	@Column(name = "available")
	private boolean available = true;

	public Product() {
		this.id = ++totalProducts;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public Amount getPubliPrice() {
		return publiPrice;
	}

	public void setPubliPrice(Amount wholesalerPrice) {
		this.publiPrice = new Amount((wholesalerPrice.getValue() * 2), "€");
	}

	public Product(String name, Amount wholesalerPrice, int stock) {
		this(); // Llamamos al constructor sin parámetros para que se asigne el ID
		this.name = name;
		this.wholesalerPrice = wholesalerPrice;
		this.publiPrice = new Amount((wholesalerPrice.getValue() * 2), "€");
		this.stock = stock;
		this.price = wholesalerPrice.getValue();
	}

	@XmlAttribute
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	@XmlAttribute
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPublicPrice(Amount wholesalerPrice) {
		double value = wholesalerPrice.getValue() * 2;
		this.publiPrice = new Amount(value, wholesalerPrice.getCurrency());
	}

	@XmlElement
	public Amount getPublicPrice() {
		return publiPrice;
	}

	@XmlElement
	public boolean getAvailable() {
		return available;
	}

	public void setAvailable(boolean aviable) {
		this.available = aviable;
	}

	@XmlElement
	public Amount getWholesalerPrice() {
		return wholesalerPrice;
	}

	public void setWholesalerPrice(Amount wholesalerPrice) {
		this.wholesalerPrice = wholesalerPrice;
	}

	@XmlElement
	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "Product{id=" + id + ", name='" + name + "', wholesalerPrice=" + wholesalerPrice + ", publicPrice="+ publiPrice + ", stock="
				+ stock + "}";
	}
}
