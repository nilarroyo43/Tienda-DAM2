package dao;

import java.io.IOException;
import java.util.List;
import dao.jaxb.JaxbUnMarshaller;
import dao.jaxb.JaxbMarshaller;
import model.Employee;
import model.Product;
import model.ProductList;

public class DaoImplJaxb implements Dao {
    static JaxbUnMarshaller jaxbUnMarsh = new JaxbUnMarshaller();
    static JaxbMarshaller jaxbMarsh = new JaxbMarshaller();
    @Override
    public void connect() {
    }

    @Override
    public Employee getEmployee(int employeeId, String password) {
        return null;
    }

    @Override
    public List<Product> getInventory() throws IOException {
        List<Product> products = jaxbUnMarsh.init();
        if (products != null) {
            for (Product product : products) {
                product.setPublicPrice(product.getWholesalerPrice()); 
            }
        }
        return products;
    }


    @Override
    public void disconnect() {
    }

    @Override
    public boolean writeInventory(List<Product> inventory) throws IOException {
    	ProductList inventario  = new ProductList();
    	inventario.setProducts(inventory);
    	if(jaxbMarsh.init(inventario)) {
    		return true;
    	}else {
        return false;
    	}
    }

	@Override
	public boolean addProduct(Product producto) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	

	@Override
	public boolean deleteProduct(Product producto) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean updateProduct(String name, int stock) throws IOException {
		// TODO Auto-generated method stub
		return false;
	}
}
