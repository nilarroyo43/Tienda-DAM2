package model;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Column;
import java.time.LocalDateTime;

@Entity
@Table(name = "historical_inventory")
public class ProductHistory {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "available")
	private boolean available;

	@Column(name = "created_at")
	private String createdAt;

	@Column(name = "id_product")
	private int productId;

	@Column(name = "name")
	private String name;

	@Column(name = "price")
	private double price;

	@Column(name = "stock")
	private int stock;

	public ProductHistory() {
	}

	public ProductHistory(Product product,String createdAt) {
		this.productId = product.getId();
		this.name = product.getName();
		this.price = product.getWholesalerPrice().getValue();
		this.stock = product.getStock();
		this.available = product.getAvailable();
		this.createdAt = createdAt;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isAvailable() {
		return available;
	}

	public void setAvailable(boolean available) {
		this.available = available;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public int getProductId() {
		return productId;
	}

	public void setProductId(int productId) {
		this.productId = productId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getStock() {
		return stock;
	}

	public void setStock(int stock) {
		this.stock = stock;
	}

	@Override
	public String toString() {
		return "ProductHistory{" + "id=" + id + ", available=" + available + ", createdAt=" + createdAt + ", productId="
				+ productId + ", name='" + name + '\'' + ", price=" + price + ", stock=" + stock + '}';
	}
}
