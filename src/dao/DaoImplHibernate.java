package dao;

import model.Amount;
import model.Employee;
import model.Product;
import model.ProductHistory;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class DaoImplHibernate implements Dao {
	private SessionFactory sessionFactory;

	public DaoImplHibernate() {
		sessionFactory = new Configuration().configure().buildSessionFactory();
	}

	@Override
	public void connect() {

	}

	@Override
	public void disconnect() {

	}

	@Override
	public List<Product> getInventory() {
		try (Session session = sessionFactory.openSession()) {
			List<Product> inventory = session.createQuery("FROM Product", Product.class).list();
			for (Product product : inventory) {
				product.setWholesalerPrice(new Amount(product.getPrice(), "€"));
				product.setPublicPrice(product.getWholesalerPrice());
			}
			System.out.println(inventory);
			return inventory;

		}
	}

	@Override
	public boolean writeInventory(List<Product> inventario) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			LocalDateTime currentDateTime = LocalDateTime.now();
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
			String formattedDateTime = currentDateTime.format(formatter);
			for (Product product : inventario) {
				ProductHistory history = new ProductHistory(product, formattedDateTime);
				session.save(history);
			}
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean addProduct(Product producto) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.save(producto);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean updateProduct(String name, int stock) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			Product product = session.createQuery("FROM Product WHERE name = :name", Product.class)
					.setParameter("name", name).uniqueResult();
			if (product != null) {
				product.setStock(stock);
				session.update(product);
				transaction.commit();
				return true;
			}
			return false;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public boolean deleteProduct(Product producto) {
		Transaction transaction = null;
		try (Session session = sessionFactory.openSession()) {
			transaction = session.beginTransaction();
			session.delete(producto);
			transaction.commit();
			return true;
		} catch (Exception e) {
			if (transaction != null)
				transaction.rollback();
			e.printStackTrace();
			return false;
		}
	}

	@Override
	public Employee getEmployee(int employeeid, String password) {
		// TODO Auto-generated method stub
		return null;
	}
}
