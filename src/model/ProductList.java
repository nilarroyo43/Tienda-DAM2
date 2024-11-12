package model;

import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement(name = "products")
public class ProductList {
    private int total;
    private List<Product> products = new ArrayList<>();

    @XmlAttribute
    public int getTotal() {
        return products.size();  // Devuelve el número total de productos
    }

    public void setTotal(int total) {
        this.total = total;
    }

    @XmlElement(name = "product")
    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
