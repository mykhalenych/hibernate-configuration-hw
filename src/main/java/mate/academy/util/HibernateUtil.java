package mate.academy.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import mate.academy.model.Movie;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    private static final SessionFactory sessionFactory = buildSessionFactory();

    private HibernateUtil() {
    }

    private static SessionFactory buildSessionFactory() {
        try {
            Properties properties = new Properties();
            try (InputStream is = HibernateUtil.class.getClassLoader()
                    .getResourceAsStream("application.properties")) {
                properties.load(is);
            } catch (IOException e) {
                throw new RuntimeException("Can't read application.properties file", e);
            }

            return new Configuration()
                    .configure()
                    .addAnnotatedClass(Movie.class)
                    .setProperties(properties)
                    .buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}
