package model;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@XmlRootElement(name = "product")
@XmlType(propOrder = {"id", "name", "available", "wholesalerPrice", "publicPrice", "stock"})
public class Product {
    private static int totalProducts = 0; 
    private int id;
    private String name;
    private Amount wholesalerPrice;
    private Amount publiPrice;
    private int stock;
    private boolean available  = true;

    public Product() {
        this.id = ++totalProducts;
    }

    public Product(String name, Amount wholesalerPrice, int stock) {
        this();  // Llamamos al constructor sin parámetros para que se asigne el ID
        this.name = name;
        this.wholesalerPrice = wholesalerPrice;
        this.stock = stock;
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
        return "Product{id=" + id + ", name='" + name + "', wholesalerPrice=" + wholesalerPrice.getValue() + 
                 ", stock=" + stock + "}";
    }
}
