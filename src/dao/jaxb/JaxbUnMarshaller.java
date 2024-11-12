package dao.jaxb;

import java.io.File;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Unmarshaller;
import model.Product;
import model.ProductList;

public class JaxbUnMarshaller {

    public List<Product> init() {
        ProductList products = null;
        File file = new File("jaxb/inputInventory.xml");
        if (!file.exists()) {
            System.out.println("El archivo no existe: " + file.getAbsolutePath());
            return null;
        }
        try {
            JAXBContext context = JAXBContext.newInstance(ProductList.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            System.out.println("Unmarshalling...");
            products = (ProductList) unmarshaller.unmarshal(file);
        } catch (JAXBException e) {
            System.out.println("Error during unmarshalling: " + e.getMessage());
            e.printStackTrace();
        }
        if (products == null) {
            System.out.println("Error unmarshalling");
            return null;
        } else {
            return products.getProducts();
        }
    }
}
