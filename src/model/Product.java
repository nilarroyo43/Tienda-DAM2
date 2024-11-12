package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "product")
@XmlType(propOrder = {"id", "name", "wholesalerPrice", "stock"})
public class Product {
    private int id;
    private String name;
    private Amount wholesalerPrice;
    private int stock;
    private boolean aviable;
    private int totalProducts = 0;  

        public Product() {
    }

 
    public Product(String name, Amount wholesalerPrice, int stock) {
    	this.id = totalProducts;  
        this.name = name;
        this.wholesalerPrice = wholesalerPrice;
        this.stock = stock;
        this.aviable = true;
    }

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

    public int getAviable() {
        return stock;
    }

    public void setAviable(boolean aviable) {
        this.aviable = aviable;
    }

    @Override
    public String toString() {
        return id + "Product{name=" + name + ", wholesalerPrice=" + wholesalerPrice + 
               ", stock=" + stock + "}";
    }
}
