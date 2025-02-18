package dao;

import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.bson.Document;
import org.bson.types.ObjectId;

import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import model.Amount;
import model.Employee;
import model.Product;

public class DaoImplMongoDB implements Dao {
	MongoCollection<Document> collection_Invetory;
	MongoCollection<Document> collection_History;
	MongoCollection<Document> collection_Employee;
	ObjectId id;

	@Override
	public void connect() {
		String uri = "mongodb://localhost:27017";
		MongoClientURI mongoClientURI = new MongoClientURI(uri);
		MongoClient mongoClient = new MongoClient(mongoClientURI);

		MongoDatabase mongoDatabase = mongoClient.getDatabase("shop");
		collection_Invetory = mongoDatabase.getCollection("inventory");
		collection_History = mongoDatabase.getCollection("historical_inventory");
		collection_Employee = mongoDatabase.getCollection("users");
	}

	@Override
	public Employee getEmployee(int employeeid, String password) {
		Employee employee = null;
		Document document = collection_Employee.find(and(eq("employeeId", employeeid), eq("password", password))).first();
		if (document != null) {
			System.out.println("Document read by id and password: " + document.toJson());
			employee = new Employee();
			employee.setEmployeeId(employeeid);
		}
		return employee;
	}

	@Override
	public List<Product> getInventory() throws IOException {
		List<Product> productList = new ArrayList<>();
		// read n documents
		// Read the first '5' documents
		Iterable<Document> documents = collection_Invetory.find();

		int count = 0;
		for (Document doc : documents) {
			System.out.println("document read on list " + (++count) + ": " + doc.toJson());
			Product product = new Product();
			Document wholesalerPrice = (Document) doc.get("wholesalerPrice");
	        product.setId(doc.getInteger("id"));  
	        product.setName(doc.getString("name"));
	        product.setWholesalerPrice(new Amount(wholesalerPrice.getDouble("value"),wholesalerPrice.getString("currency")));
	        product.setStock(doc.getInteger("stock"));
	        product.setPubliPrice(new Amount(wholesalerPrice.getDouble("value"),wholesalerPrice.getString("currency")));
	        productList.add(product);
		}
		return productList;
	}

	@Override
	public void disconnect() {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean writeInventory(List<Product> inventario) throws IOException {
		 ZonedDateTime currentDateTime = ZonedDateTime.now();
		 DateTimeFormatter formatter = DateTimeFormatter.ISO_DATE_TIME;
		String formattedDateTime = currentDateTime.format(formatter);
		boolean export = false;
		for(Product product : inventario) {
			Document document = new Document("_id", new ObjectId())
					.append("id", product.getId()).append("name", product.getName())
					.append("wholesalerPrice", new Document("value", product.getWholesalerPrice().getValue()).append("currency", "€"))
					.append("aviable", product.getAvailable()).append("stock", product.getStock()).append("created_at", formattedDateTime);
			collection_History.insertOne(document);
			System.out.println("document inserted" + document);
			export = true;
		}
		return export;
	}

	@Override
	public boolean addProduct(Product producto) throws IOException {
		boolean created = false;
		
			Document document = new Document("_id", new ObjectId())
					.append("id", producto.getId()).append("name", producto.getName())
					.append("wholesalerPrice", new Document("value", producto.getWholesalerPrice().getValue()).append("currency", "€"))
					.append("aviable", producto.getAvailable()).append("stock", producto.getStock());
			collection_Invetory.insertOne(document);
			System.out.println("document inserted" + document);
			created = true;
		
		return created;
	}
	

	@Override
	public boolean updateProduct(String name, int stock) throws IOException {
		boolean updated = false;
		UpdateResult result = collection_Invetory.updateOne(eq("name", name),
				(set("stock", stock)));
		System.out.println("document updated by name" + result.toString());
		if(result != null) {
			updated = true;
		}
		return updated;
	}

	@Override
	public boolean deleteProduct(Product producto) throws IOException {
		boolean deleted = false;
		DeleteResult result = null;
		result = collection_Invetory.deleteOne(eq("id", producto.getId()));
		System.out.println("deleted "+ result);
		if (result != null) {
			deleted = true;
		}
		return deleted;
	}

}
