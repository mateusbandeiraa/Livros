package persistence;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;
public class HibernateUtil {
	private static final SessionFactory sessionFactory;

	static {
		try {
			AnnotationConfiguration cfg = new AnnotationConfiguration().configure("mysql_hibernate.cfg.xml");
			cfg.setProperty("hibernate.connection.url", "jdbc:" + System.getenv("CLEARDB_DATABASE_URL"));
			cfg.setProperty("hibernate.connection.username", "bf9fe421e4d5e0");
			cfg.setProperty("hibernate.connection.password", System.getenv("DB_PASSWORD"));
			sessionFactory = cfg.buildSessionFactory();
		} catch (Throwable ex) {

			System.err.println("Initial SessionFactory creation failed." + ex);
			throw new ExceptionInInitializerError(ex);
		}
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
}