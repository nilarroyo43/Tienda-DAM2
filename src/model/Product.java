package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "product")
@XmlType(propOrder = {"id", "name", "aviable", "wholesalerPrice", "stock"})
public class Product {
    private static int totalProducts = 0; 
    
    private int id;
    private String name;
    private Amount wholesalerPrice;
    private int stock;
    private boolean aviable;

    public Product() {
       
        this.id = ++totalProducts;
    }

    public Product(String name, Amount wholesalerPrice, int stock) {
        this();  // Llamamos al constructor sin parámetros para que se asigne el ID
        this.name = name;
        this.wholesalerPrice = wholesalerPrice;
        this.stock = stock;
        this.aviable = true;
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

    @XmlElement
    public boolean getAviable() {
        return aviable;
    }

    public void setAviable(boolean aviable) {
        this.aviable = aviable;
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
        return "Product{id=" + id + ", name='" + name + "', wholesalerPrice=" + wholesalerPrice + ", stock=" + stock + "}";
    }
}
